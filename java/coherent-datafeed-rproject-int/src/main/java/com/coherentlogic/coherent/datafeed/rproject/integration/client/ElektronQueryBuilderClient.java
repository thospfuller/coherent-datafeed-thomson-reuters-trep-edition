package com.coherentlogic.coherent.datafeed.rproject.integration.client;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;

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

import com.coherentlogic.coherent.data.model.core.lifecycle.Startable;
import com.coherentlogic.coherent.data.model.core.lifecycle.Stoppable;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeEvent;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeListener;
import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.coherentlogic.coherent.datafeed.builders.ElektronQueryBuilder;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.coherentlogic.coherent.datafeed.exceptions.UpdateFailedException;
import com.coherentlogic.coherent.datafeed.services.ServiceName;

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
    "classpath*:spring/rproject-beans.xml",
})
public class ElektronQueryBuilderClient implements MarketPriceConstants, Startable, Stoppable {

    private static final Logger log = LoggerFactory.getLogger(ElektronQueryBuilderClient.class);

    public static final String ADD_MARKET_PRICE_TO_QUEUE_CHANNEL = "addMarketPriceToQueueChannel",
        MARKET_PRICE_CONSUMER = "marketPriceConsumer", JSON_ADAPTER = "jsonAdapter";

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    private ElektronQueryBuilder elektronQueryBuilder;

    @Autowired
    @Qualifier(ADD_MARKET_PRICE_TO_QUEUE_CHANNEL)
    private MessageChannel addMarketPriceToQueueChannel;

    /* If you see this:
     *
     * HornetQException[errorCode=2 message=Cannot connect to server(s). Tried with all available servers.]
     *
     * -- then the solution is to not autowire the bean but get it directly from the application context once everything
     * has been started.
     */
    private MessageConsumer marketPriceMessageConsumer;

    @Autowired
    @Qualifier(JSON_ADAPTER)
    private BasicAdapter<MarketPrice, String> jsonAdapter;

    @Override
    public void start() {
        elektronQueryBuilder = applicationContext.getBean(ElektronQueryBuilder.class);
        marketPriceMessageConsumer = (MessageConsumer) applicationContext.getBean(MARKET_PRICE_CONSUMER);
    }

    @Override
    public void stop() {
        applicationContext.stop();
        applicationContext.close();
    }

    public static ElektronQueryBuilderClient initialize () {

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

        return applicationContext.getBean(ElektronQueryBuilderClient.class);
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

    public ElektronQueryBuilderClient query (String ric) {

        log.info("query: method begins; ric: " + ric);

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

        StatusResponse statusResponse = marketPrice.getStatusResponse();

        statusResponse.addAggregatePropertyChangeListener(
            event -> {

                String beginText = "marketPrice[ric: " + ric + "].statusResponse.aggregateUpdate begins.";
                String endText   = "marketPrice[ric: " + ric + "].statusResponse.aggregateUpdate ends.";

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

        elektronQueryBuilder.query(ServiceName.dELEKTRON_DD, marketPrice);

        log.info("query[" + ric + "]: method ends.");

        return this;
    }

    public MarketPrice getNextMarketPriceUpdate (Long timeout) {

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

    public String getNextMarketPriceUpdateAsJson (Long timeout) {

        log.info("getNextMarketPriceUpdateAsJson: method begins; timeout: " + timeout);

        MarketPrice nextUpdate = getNextMarketPriceUpdate(timeout);

        String result = jsonAdapter.adapt(nextUpdate);

        log.info("getNextMarketPriceUpdateAsJson: method ends; result: " + result);

        return result;
    }

    public static void main (String[] unused) throws InterruptedException {

        String dacsId = System.getenv(DACS_ID);

        ElektronQueryBuilderClient client = ElektronQueryBuilderClient.initialize();

        client.start();

        AtomicLong nextUpdateCtr = new AtomicLong (0);

        new Thread (
            () -> {
                while (true) {
                    String next = client.getNextMarketPriceUpdateAsJson(5000L);
                    System.out.println("next["+ nextUpdateCtr.incrementAndGet() +"]: " + next);
                }
            }
        ).start ();

        client.login (dacsId);

        for (String nextRic : ExampleRICS.rics)
            client.query(nextRic);

        Thread.sleep(Long.MAX_VALUE);

        System.exit(-9999);
    }
}
