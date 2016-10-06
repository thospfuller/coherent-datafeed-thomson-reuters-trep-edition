package com.coherentlogic.coherent.datafeed.rproject.integration.client;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;
import com.coherentlogic.coherent.data.model.core.lifecycle.Startable;
import com.coherentlogic.coherent.data.model.core.lifecycle.Stoppable;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeEvent;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeListener;
import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.coherentlogic.coherent.datafeed.builders.ElektronQueryBuilder;
import com.coherentlogic.coherent.datafeed.domain.EventType;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketByPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.RDMFieldDictionaryConstants;
import com.coherentlogic.coherent.datafeed.domain.RICBeanSpecification;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.coherentlogic.coherent.datafeed.domain.StatusResponseBean;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.coherentlogic.coherent.datafeed.exceptions.ApplicationInitializationFailedException;
import com.coherentlogic.coherent.datafeed.exceptions.UnknownPeriodException;
import com.coherentlogic.coherent.datafeed.exceptions.UpdateFailedException;
import com.coherentlogic.coherent.datafeed.services.ServiceName;
import com.coherentlogic.rproject.integration.dataframe.adapters.RemoteAdapter;
import com.coherentlogic.rproject.integration.dataframe.builders.JDataFrameBuilder;
import com.coherentlogic.rproject.integration.dataframe.domain.Column;
import com.coherentlogic.rproject.integration.dataframe.domain.JDataFrame;
import com.reuters.ts1.TS1Constants;

import net.coherentlogic.coherent.datafeed.examples.ExampleRICS;

/**
 * An example application that authenticates, executes a query, and gets the
 * data.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="support@coherentlogic.com">Support</a>
 */
@SpringBootApplication
@ComponentScan(basePackages="com.coherentlogic.coherent.datafeed")
@ImportResource({
    "classpath*:spring/jmx-beans.xml",
    "classpath*:spring/hornetq-beans.xml",
    "classpath*:spring/marketprice-jms-workflow-beans.xml",
    "classpath*:spring/marketbyprice-jms-workflow-beans.xml",
    "classpath*:spring/marketbyorder-jms-workflow-beans.xml",
    "classpath*:spring/marketmaker-jms-workflow-beans.xml",
    "classpath*:spring/rproject-beans.xml",
})
//@EnableAutoConfiguration
public class ElektronQueryBuilderClient implements RDMFieldDictionaryConstants, Startable, Stoppable {

    private static final Logger log = LoggerFactory.getLogger(ElektronQueryBuilderClient.class);

    public static final String ADD_MARKET_PRICE_TO_QUEUE_CHANNEL = "addMarketPriceToQueueChannel",
        ADD_MARKET_BY_PRICE_TO_QUEUE_CHANNEL = "addMarketByPriceToQueueChannel",
        ADD_MARKET_BY_ORDER_TO_QUEUE_CHANNEL = "addMarketByOrderToQueueChannel",
        ADD_MARKET_MAKER_TO_QUEUE_CHANNEL = "addMarketMakerToQueueChannel",
        MARKET_PRICE_CONSUMER = "marketPriceConsumer",
        MARKET_BY_PRICE_CONSUMER = "marketByPriceConsumer",
        MARKET_BY_ORDER_CONSUMER = "marketByOrderConsumer",
        MARKET_MAKER_CONSUMER = "marketMakerConsumer",
        JSON_ADAPTER = "jsonAdapter",
        TIME_SERIES_JSON_GENERATOR = "timeSeriesJSONGenerator", RFA_CLIENT = "com.reuters.rfa.common.Client",
        DAILY = "daily", WEEKLY = "weekly", MONTHLY = "monthly";

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    private ElektronQueryBuilder elektronQueryBuilder;

    @Autowired
    @Qualifier(ADD_MARKET_PRICE_TO_QUEUE_CHANNEL)
    private MessageChannel addMarketPriceToQueueChannel;

    @Autowired
    @Qualifier(ADD_MARKET_BY_PRICE_TO_QUEUE_CHANNEL)
    private MessageChannel addMarketByPriceToQueueChannel;

    @Autowired
    @Qualifier(ADD_MARKET_BY_ORDER_TO_QUEUE_CHANNEL)
    private MessageChannel addMarketByOrderToQueueChannel;

    @Autowired
    @Qualifier(ADD_MARKET_MAKER_TO_QUEUE_CHANNEL)
    private MessageChannel addMarketMakerToQueueChannel;

    private final Map<String, Integer> periodMap;

    /* If you see this:
     *
     * HornetQException[errorCode=2 message=Cannot connect to server(s). Tried with all available servers.]
     *
     * -- then the solution is to not autowire the bean but get it directly from the application context once everything
     * has been started.
     */
    private MessageConsumer
        marketPriceMessageConsumer,
        marketByPriceMessageConsumer,
        marketMakerMessageConsumer,
        marketByOrderMessageConsumer;

    @Autowired
    @Qualifier(JSON_ADAPTER)
    private BasicAdapter<SerializableBean<?>, String> jsonAdapter;

    @Autowired
    @Qualifier(JSON_ADAPTER)
    private BasicAdapter<TimeSeries, String> timeSeriesJSONGenerator;

    public ElektronQueryBuilderClient() {
        this (new HashMap<String, Integer> (3));
    }

    public ElektronQueryBuilderClient(Map<String, Integer> periodMap) {

        this.periodMap = periodMap;

        periodMap.put(DAILY, TS1Constants.DAILY_PERIOD);
        periodMap.put(WEEKLY, TS1Constants.WEEKLY_PERIOD);
        periodMap.put(MONTHLY, TS1Constants.MONTHLY_PERIOD);
    }

    @Override
    public void start() {

        try {

            verifyRFADependencyIsPresent ();

            elektronQueryBuilder = applicationContext.getBean(ElektronQueryBuilder.class);
            marketPriceMessageConsumer = (MessageConsumer) applicationContext.getBean(MARKET_PRICE_CONSUMER);
            marketByPriceMessageConsumer = (MessageConsumer) applicationContext.getBean(MARKET_BY_PRICE_CONSUMER);
            marketByOrderMessageConsumer = (MessageConsumer) applicationContext.getBean(MARKET_BY_ORDER_CONSUMER);
            marketMakerMessageConsumer = (MessageConsumer) applicationContext.getBean(MARKET_MAKER_CONSUMER);
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            log.error("start: method threw an exception.", t);
            throw t;
        }
    }

    void verifyRFADependencyIsPresent () {
        try {
            Class.forName(RFA_CLIENT);
        } catch (ClassNotFoundException cnfe) {

            String exceptionText = "Unable to instantiate the class " + RFA_CLIENT + " -- this is most likely due "
                + "to the absence of the RFA dependency.";

            RuntimeException aife = new ApplicationInitializationFailedException(exceptionText, cnfe);

            log.error(exceptionText, aife);

            throw aife;
        }
    }

    @Override
    public void stop() {
        applicationContext.stop();
        applicationContext.close();
    }

    public static ElektronQueryBuilderClient initialize () {

        log.info("initialize: method begins.");

        ConfigurableApplicationContext applicationContext = null;

        try {
            SpringApplicationBuilder builder =
                new SpringApplicationBuilder (ElektronQueryBuilderClient.class);

            applicationContext = builder
                .web(false)
                .headless(true)
                .registerShutdownHook(true)
                .run(new String[] {});

        } catch (Throwable thrown) {

            log.error("An exception was thrown while trying to get the application context.", thrown);

            throw new RuntimeException ("There was a problem when trying to get a reference to the application "
                + "context. This is fatal and your best option at this point is to contact support@coherentlogic.com "
                + "for further assistance.", thrown);
        }

        ElektronQueryBuilderClient result =
            (ElektronQueryBuilderClient) applicationContext.getBean(ElektronQueryBuilderClient.class);

        log.info("initialize: method ends: " + result);

        return result;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public ElektronQueryBuilderClient login (String dacsId) {

        log.info("login: method begins; dacsId: " + dacsId);

        SessionBean sessionBean = elektronQueryBuilder.newSessionBean(dacsId);

        sessionBean.getStatusResponse().addAggregatePropertyChangeListener(
            event -> {

                String beginText = "login[dacsId: " + dacsId + "].statusResponse.aggregateUpdate begins.";
                String endText   = "login[dacsId: " + dacsId + "].statusResponse.aggregateUpdate ends.";

                System.out.println(beginText);
                log.info(beginText);

                event
                    .getPropertyChangeEventMap()
                    .forEach((key, value) -> {
                        System.out.println("- key: " + key + ", value: " + value);
                    }
                );

                System.out.println(endText);
                log.info(endText);
            }
        );

        elektronQueryBuilder.login(sessionBean);

        log.info("login: method ends.");

        return this;
    }

    public void logout () {

        log.info("logout: method begins.");

        elektronQueryBuilder.logout();

        log.info("logout: method ends.");
    }

    ElektronQueryBuilderClient attachTopLevelStatusResponseListeners (
        StatusResponseBean statusResponseBean,
        String typeName,
        String ric
    ) {
        log.info("attachListeners: method begins; statusResponseBean: " + statusResponseBean + ", typeName: " + typeName
            + ", ric: " + ric);

        StatusResponse statusResponse = statusResponseBean.getStatusResponse();

        statusResponse.addAggregatePropertyChangeListener(
            event -> {

                String beginText = typeName + "[ric: " + ric + "].statusResponse.aggregateUpdate begins.";
                String endText   = typeName + "[ric: " + ric + "].statusResponse.aggregateUpdate ends.";

                System.out.println(beginText);
                log.info(beginText);

                event
                    .getPropertyChangeEventMap()
                    .forEach((key, value) -> {

                        String kvp = "- key: " + key + ", value: " + value;

                        System.out.println(kvp);
                        log.info(kvp);
                    }
                );

                System.out.println(endText);
                log.info(endText);
            }
        );

        log.info("attachListeners[" + ric + " / " +typeName + "]: method ends.");

        return this;
    }

    ElektronQueryBuilderClient addOrderListener (
        String ric,
        SerializableBean parent,
        SerializableBean order,
        MessageChannel targetChannel
    ) {

        order.addAggregatePropertyChangeListener(
            new AggregatePropertyChangeListener () {
                @Override
                public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent event) {

                    try {
                        SerializableBean clonedParent = (SerializableBean) parent;
    
                        targetChannel.send(
                            MessageBuilder.withPayload(clonedParent).build()
                        );
                    } catch (Throwable t) {
                        log.error("ric: " + ric + " some problem occurred.", t);
                        t.printStackTrace(System.err);
                        System.exit(-9999);
                    }
                }
            }
        );

        return this;
    }

    public ElektronQueryBuilderClient queryMarketPrice (ServiceName serviceName, String ric) {

        log.info("queryMarketPrice: method begins; ric: " + ric);

        MarketPrice marketPrice = elektronQueryBuilder.newMarketPrice(ric);

        marketPrice.addAggregatePropertyChangeListener(
            new AggregatePropertyChangeListener<MarketPrice> () {

                @Override
                public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent<MarketPrice> event) {

                    MarketPrice marketPrice = event.getSource ();

                    MarketPrice clone = (MarketPrice) marketPrice.clone();

                    addMarketPriceToQueueChannel.send(
                        MessageBuilder.withPayload(clone).build()
                    );
                }
            }
        );

        attachTopLevelStatusResponseListeners(marketPrice, MarketPrice.class.getName(), marketPrice.getRic())
            .elektronQueryBuilder.query(serviceName, marketPrice);

        log.info("query[" + ric + "]: method ends.");

        return this;
    }

    public ElektronQueryBuilderClient queryMarketPrice (ServiceName serviceName, String... rics) {

        for (String nextRic : rics)
            queryMarketPrice (serviceName, nextRic);

        return this;
    }

    public ElektronQueryBuilderClient queryMarketByPrice (ServiceName serviceName, String ric) {

        log.info("queryMarketByPrice: method begins; ric: " + ric);

        MarketByPrice marketByPrice = elektronQueryBuilder.newMarketByPrice(ric);

        marketByPrice.addAggregatePropertyChangeListener(
            new AggregatePropertyChangeListener<MarketByPrice> () {

                @Override
                public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent<MarketByPrice> event) {

                    MarketByPrice marketByPrice = event.getSource ();

                    MarketByPrice clone = (MarketByPrice) marketByPrice.clone();

                    addMarketByPriceToQueueChannel.send(
                        MessageBuilder.withPayload(clone).build()
                    );
                }
            }
        );

        marketByPrice.addOrderEventListener(
            orderEvent -> {
                if (orderEvent.getEventType() == EventType.instantiated) {

                    MarketByPrice.Order order = orderEvent.getOrder ();

                    addOrderListener(ric, marketByPrice, order, addMarketByPriceToQueueChannel);
                } else {
                    log.info("The mbp order with key: " + orderEvent.getKey() + " was " + orderEvent.getEventType() +
                        " (orderEvent: " + orderEvent + ")");
                }
            }
        );

        attachTopLevelStatusResponseListeners(marketByPrice, MarketByPrice.class.getName(), ric)
            .elektronQueryBuilder
            .query(serviceName, marketByPrice);

//        marketByPrice.addOrderEventListener(
//            orderEvent -> {
//                if (orderEvent.getEventType() == EventType.instantiated) {
//
//                    System.out.println("[" + ric + "] : Adding an order due to orderEvent: " + orderEvent);
//
//                    MarketByPrice.Order order = orderEvent.getOrder ();
//
//                    order.addAggregatePropertyChangeListener(
//                        new AggregatePropertyChangeListener<MarketByPrice.Order> () {
//                            @Override
//                            public void onAggregatePropertyChangeEvent(
//                                AggregatePropertyChangeEvent<MarketByPrice.Order> event) {
//
//                                MarketByPrice.Order marketByPriceOrder = event.getSource ();
//
//                                MarketByPrice.Order clone = (MarketByPrice.Order) marketByPrice.clone();
//
//                                addMarketByPriceToQueueChannel.send(
//                                    MessageBuilder.withPayload(clone).build()
//                                );
//                            }
//                        }
//                    );
//                }
//            }
//        );

        log.info("queryMarketByPrice[" + ric + "]: method ends.");

        return this;
    }

    public ElektronQueryBuilderClient queryMarketByPrice (ServiceName serviceName, String... rics) {

        for (String nextRic : rics)
            queryMarketByPrice (serviceName, nextRic);

        return this;
    }

    public ElektronQueryBuilderClient queryMarketByOrder (ServiceName serviceName, String ric) {

        log.info("queryMarketByOrder: method begins; serviceName " + serviceName + ", ric: " + ric);

        MarketByOrder marketByOrder = elektronQueryBuilder.newMarketByOrder(ric);

        marketByOrder.addAggregatePropertyChangeListener(
            new AggregatePropertyChangeListener<MarketByOrder> () {

                @Override
                public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent<MarketByOrder> event) {

                    MarketByOrder marketByOrder = event.getSource ();

                    MarketByOrder clone = (MarketByOrder) marketByOrder.clone();

                    addMarketByOrderToQueueChannel.send(
                        MessageBuilder.withPayload(clone).build()
                    );
                }
            }
        );

        marketByOrder.addOrderEventListener(
            orderEvent -> {
                if (orderEvent.getEventType() == EventType.instantiated) {

                    MarketByOrder.Order order = orderEvent.getOrder ();

                    addOrderListener(ric, marketByOrder, order, addMarketByOrderToQueueChannel);
                } else {
                    log.info("The mbo order with key: " + orderEvent.getKey() + " was " + orderEvent.getEventType() +
                        " (orderEvent: " + orderEvent + ")");
                }
            }
        );

        attachTopLevelStatusResponseListeners(marketByOrder, MarketByOrder.class.getName(), marketByOrder.getRic())
            .elektronQueryBuilder
            .query(serviceName, marketByOrder);

        log.info("queryMarketByOrder[" + ric + "]: method ends.");

        return this;
    }

    public ElektronQueryBuilderClient queryMarketByOrder (ServiceName serviceName, String... rics) {

        for (String nextRic : rics)
            queryMarketByOrder (serviceName, nextRic);

        return this;
    }

    public ElektronQueryBuilderClient queryMarketMaker (ServiceName serviceName, String ric) {

        log.info("queryMarketMaker: method begins; ric: " + ric);

        MarketMaker marketMaker = elektronQueryBuilder.newMarketMaker(ric);

        marketMaker.addAggregatePropertyChangeListener(
            new AggregatePropertyChangeListener<MarketMaker> () {

                @Override
                public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent<MarketMaker> event) {

                    MarketMaker marketMaker = event.getSource ();

                    MarketMaker clone = (MarketMaker) marketMaker.clone();

                    addMarketMakerToQueueChannel.send(
                        MessageBuilder.withPayload(clone).build()
                    );
                }
            }
        );

        marketMaker.addOrderEventListener(
            orderEvent -> {
                if (orderEvent.getEventType() == EventType.instantiated) {

                    MarketMaker.Order order = orderEvent.getOrder ();

                    addOrderListener(ric, marketMaker, order, addMarketMakerToQueueChannel);
                } else {
                    log.info("The mm order with key: " + orderEvent.getKey() + " was " + orderEvent.getEventType() +
                        " (orderEvent: " + orderEvent + ")");
                }
            }
        );

        attachTopLevelStatusResponseListeners(marketMaker, MarketMaker.class.getName(), marketMaker.getRic())
            .elektronQueryBuilder
            .query(serviceName, marketMaker);

        log.info("queryMarketMaker[" + ric + "]: method ends.");

        return this;
    }

    public ElektronQueryBuilderClient queryMarketMaker (ServiceName serviceName, String... rics) {

        for (String nextRic : rics)
            queryMarketMaker (serviceName, nextRic);

        return this;
    }

    public MarketPrice getNextMarketPriceUpdateAsJavaObject (String timeout) {
        return getNextMarketPriceUpdateAsJavaObject(Long.valueOf(timeout));
    }

    public MarketPrice getNextMarketPriceUpdateAsJavaObject (Long timeout) {

        log.info("getNextMarketPriceUpdate: method begins; timeout: " + timeout);

        Message nextMessage;

        try {
            nextMessage = marketPriceMessageConsumer.receive(timeout);
        } catch (JMSException jmsException) {
            throw new UpdateFailedException ("The next update was not received due to an exception being "
                + "thrown while waiting to receive the next message.", jmsException);
        }

        MarketPrice result = null;

        if (nextMessage != null) {

            ObjectMessage nextObjectMessage = (ObjectMessage) nextMessage;

            try {
                result = (MarketPrice) nextObjectMessage.getObject();
                nextMessage.acknowledge();
            } catch (JMSException jmsException) {
                throw new UpdateFailedException ("The next object could not be converted to a market price.",
                    jmsException);
            }
        }

        log.info("getNextMarketPriceUpdate: method ends; result: " + result);

        return result;
    }

    public String getNextMarketPriceUpdateAsJson (String timeout) {
        return getNextMarketPriceUpdateAsJson(Long.valueOf(timeout));
    }

    public String getNextMarketPriceUpdateAsJson (Long timeout) {

        log.info("getNextMarketPriceUpdateAsJson: method begins; timeout: " + timeout);

        MarketPrice nextUpdate = getNextMarketPriceUpdateAsJavaObject(timeout);

        String result = jsonAdapter.adapt(nextUpdate);

        log.info("getNextMarketPriceUpdateAsJson: method ends; result: " + result);

        return result;
    }

    public MarketMaker getNextMarketMakerUpdateAsJavaObject (String timeout) {
        return getNextMarketMakerUpdateAsJavaObject(Long.valueOf(timeout));
    }

    public MarketMaker getNextMarketMakerUpdateAsJavaObject (Long timeout) {

        log.info("getNextMarketMakerUpdate: method begins; timeout: " + timeout);

        Message nextMessage;

        try {
            nextMessage = marketMakerMessageConsumer.receive(timeout);
        } catch (JMSException jmsException) {
            throw new UpdateFailedException ("The next update was not received due to an exception being "
                + "thrown while waiting to receive the next message.", jmsException);
        }

        MarketMaker result = null;

        if (nextMessage != null) {

            ObjectMessage nextObjectMessage = (ObjectMessage) nextMessage;

            try {
                result = (MarketMaker) nextObjectMessage.getObject();
                nextMessage.acknowledge();
            } catch (JMSException jmsException) {
                throw new UpdateFailedException ("The next object could not be converted to a market price.",
                    jmsException);
            }
        }

        log.info("getNextMarketMakerUpdate: method ends; result: " + result);

        return result;
    }

    public MarketByOrder getNextMarketByOrderUpdateAsJavaObject (String timeout) {
        return getNextMarketByOrderUpdateAsJavaObject(Long.valueOf(timeout));
    }

    public MarketByOrder getNextMarketByOrderUpdateAsJavaObject (Long timeout) {

        log.info("getNextMarketByOrderUpdate: method begins; timeout: " + timeout);

        Message nextMessage;

        try {
            nextMessage = marketByOrderMessageConsumer.receive(timeout);
        } catch (JMSException jmsException) {
            throw new UpdateFailedException ("The next update was not received due to an exception being "
                + "thrown while waiting to receive the next message.", jmsException);
        }

        MarketByOrder result = null;

        if (nextMessage != null) {

            ObjectMessage nextObjectMessage = (ObjectMessage) nextMessage;

            try {
                result = (MarketByOrder) nextObjectMessage.getObject();
                nextMessage.acknowledge();
            } catch (JMSException jmsException) {
                throw new UpdateFailedException ("The next object could not be converted to a market price.",
                    jmsException);
            }
        }

        log.info("getNextMarketByOrderUpdate: method ends; result: " + result);

        return result;
    }

    public MarketByPrice getNextMarketByPriceUpdateAsJavaObject (String timeout) {
        return getNextMarketByPriceUpdateAsJavaObject(Long.valueOf(timeout));
    }

    public MarketByPrice getNextMarketByPriceUpdateAsJavaObject (Long timeout) {

        log.info("getNextMarketByPriceUpdate: method begins; timeout: " + timeout);

        Message nextMessage;

        try {
            nextMessage = marketByPriceMessageConsumer.receive(timeout);
        } catch (JMSException jmsException) {
            throw new UpdateFailedException ("The next update was not received due to an exception being "
                + "thrown while waiting to receive the next message.", jmsException);
        }

        MarketByPrice result = null;

        if (nextMessage != null) {

            ObjectMessage nextObjectMessage = (ObjectMessage) nextMessage;

            try {
                result = (MarketByPrice) nextObjectMessage.getObject();
                nextMessage.acknowledge();
            } catch (JMSException jmsException) {
                throw new UpdateFailedException ("The next object could not be converted to a market price.",
                    jmsException);
            }
        }

        log.info("getNextMarketByPriceUpdate: method ends; result: " + result);

        return result;
    }

    public String getNextMarketByPriceUpdateAsJson (String timeout) {
        return getNextMarketByPriceUpdateAsJson(Long.valueOf(timeout));
    }

    public String getNextMarketByPriceUpdateAsJson (Long timeout) {

        log.info("getNextMarketByPriceUpdateAsJson: method begins; timeout: " + timeout);

        MarketByPrice nextUpdate = getNextMarketByPriceUpdateAsJavaObject(timeout);

        String result = jsonAdapter.adapt(nextUpdate);

        log.info("getNextMarketByPriceUpdateAsJson: method ends; result: " + result);

        return result;
    }

    protected TimeSeries getTimeSeriesFor (String serviceName, String ric, String period) {

        log.debug("getTimeSeriesFor: method begins; serviceName: " + serviceName + ", ric: " + ric + ", (String) "
            + "period: " + period);

        Integer actualPeriod = periodMap.get(period);

        if (actualPeriod == null)
            throw new UnknownPeriodException ("The period '" + period + "' is invalid -- only 'daily', 'weekly', and "
                + "'monthly' are valid values.");

        TimeSeries result = elektronQueryBuilder.getTimeSeriesFor(serviceName, ric, actualPeriod);

        log.debug("getTimeSeriesFor: method ends; result: " + result);

        return result;
    }

    protected TimeSeries getTimeSeriesFor (String serviceName, String ric, int period) {

        log.debug("getTimeSeriesFor: method begins; serviceName: " + serviceName + ", ric: " + ric + ", (int) period: "
            + period);

        TimeSeries result = elektronQueryBuilder.getTimeSeriesFor(serviceName, ric, period);

        log.debug("getTimeSeriesFor: method ends; result: " + result);

        return result;
    }

    public TimeSeries getTimeSeriesAsJavaObject (String serviceName, String ric, String period) {

        log.info("getTimeSeriesAsJavaObject: method begins; serviceName: " + serviceName + ", ric: " + ric
            + ", (String) period: " + period);

        TimeSeries result = getTimeSeriesFor (serviceName, ric, period);

        log.info("getTimeSeriesAsJavaObject: method ends; result: " + result);

        return result;
    }

    // "DATE","CLOSE","OPEN","HIGH","LOW","VOLUME","ASK","BID"
    public String getTimeSeriesAsJson (String serviceName, String ric, String period) {

        log.info("getTimeSeriesAsJson: method begins; serviceName: " + serviceName + ", ric: " + ric
            + ", (String) period: " + period);

        TimeSeries timeSeries = getTimeSeriesAsJavaObject (serviceName, ric, period);

        JDataFrameBuilder<String> jDataFrameBuilder = new JDataFrameBuilder<String>(
            new JDataFrame(),
            new RemoteAdapter()
        );

        List<String> headers = timeSeries.getHeaders();

        headers.forEach(
            header -> {

                List<String> points = timeSeries.getValuesForHeader(header);

                Column<String> column = new Column<String> (header, points);

                jDataFrameBuilder.addColumn(column);
            }
        );

        String result = jDataFrameBuilder.serialize();

        log.info("getTimeSeriesAsJson: method ends; result: " + result);

        return result;
    }

    private static final AtomicLong nextUpdateCtr = new AtomicLong (0);

    static void logUpdate (String type, RICBeanSpecification next) {

        if (next != null) {
            String message =
                "next." + type + "[" + next.getRic() + "]["+ nextUpdateCtr.incrementAndGet() +"]: " + next;

            System.out.println(message);
            // log.info(message);
        } else {
            System.out.println("next." + type + "["+ nextUpdateCtr.incrementAndGet() +"]: null");
        }
    }

    public static void main (String[] unused) throws InterruptedException {

        String dacsId = System.getenv(DACS_ID);

        ElektronQueryBuilderClient client = ElektronQueryBuilderClient.initialize();

        client.start();

        new Thread (
            () -> {
                while (true) {

                    MarketByPrice next = client.getNextMarketByPriceUpdateAsJavaObject(5000L);

                    logUpdate("mbp", next);
                }
            }
        ).start ();

        new Thread (
            () -> {
                while (true) {

                    MarketByOrder next = client.getNextMarketByOrderUpdateAsJavaObject(5000L);

                    logUpdate("mbo", next);
                }
            }
        ).start ();

        new Thread (
            () -> {
                while (true) {

                    MarketPrice next = client.getNextMarketPriceUpdateAsJavaObject(5000L);

                    logUpdate("mp", next);
                }
            }
        ).start ();

        new Thread (
            () -> {
                while (true) {

                    MarketMaker next = client.getNextMarketMakerUpdateAsJavaObject(5000L);

                    logUpdate("mm", next);
                }
            }
        ).start ();

        client.login (dacsId);

//        String nextRic = "MTI.MI"; // MTI.MI is causing an NPE for mbp.

        for (String nextRic : ExampleRICS.rics) {
            client
//                .queryMarketPrice(ServiceName.dELEKTRON_DD, nextRic)
                .queryMarketByPrice(ServiceName.dELEKTRON_DD, nextRic) // NW
                .queryMarketByOrder(ServiceName.dELEKTRON_DD, nextRic) // Works
                .queryMarketMaker(ServiceName.dELEKTRON_DD, nextRic); // Works
        }

//        client.queryMarketByPrice(ServiceName.ELEKTRON_DD, nextRic);

//        TimeSeries timeSeries = client.getTimeSeriesAsJavaObject(ServiceName.ELEKTRON_DD.toString(), "TRI.N", "monthly");
//
//        System.out.println("timeSeries.samples: " + timeSeries.getSamples().size());
//
//        String timeSeriesJson = client.getTimeSeriesAsJson(ServiceName.ELEKTRON_DD.toString(), "TRI.N", "daily");
//
//        System.out.println("timeSeriesJson: " + timeSeriesJson);

        Thread.sleep(Long.MAX_VALUE);

        System.exit(-9999);
    }
}

//statusResponseBean.addAggregatePropertyChangeListener(
//new AggregatePropertyChangeListener<MarketPrice> () {
//
//  @Override
//  public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent<MarketPrice> event) {
//
//      MarketPrice marketPrice = event.getSource ();
//
//      MarketPrice clone = (MarketPrice) marketPrice.clone();
//
//      addMarketPriceToQueueChannel.send(
//          MessageBuilder.withPayload(clone).build()
//      );
//  }
//}
//);

// ---------------------

//StatusResponse statusResponse = marketPrice.getStatusResponse();
//
//statusResponse.addAggregatePropertyChangeListener(
//  event -> {
//
//      String beginText = "marketPrice[ric: " + ric + "].statusResponse.aggregateUpdate begins.";
//      String endText   = "marketPrice[ric: " + ric + "].statusResponse.aggregateUpdate ends.";
//
//      System.out.println(beginText);
//      log.info(beginText);
//
//      event
//          .getPropertyChangeEventMap()
//          .forEach((key, value) -> {
//              System.out.println("- key: " + key + ", value: " + value);
//          }
//      );
//
//      System.out.println(endText);
//      log.info(endText);
//  }
//);