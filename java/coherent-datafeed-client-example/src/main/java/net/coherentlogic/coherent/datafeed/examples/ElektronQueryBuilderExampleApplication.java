package net.coherentlogic.coherent.datafeed.examples;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;

import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeEvent;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeListener;
import com.coherentlogic.coherent.datafeed.builders.ElektronQueryBuilder;
import com.coherentlogic.coherent.datafeed.domain.AttribInfo;
import com.coherentlogic.coherent.datafeed.domain.EventType;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketByPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.RDMFieldDictionaryConstants;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.coherentlogic.coherent.datafeed.services.ServiceName;
import com.reuters.ts1.TS1Constants;

import joinery.DataFrame;
import joinery.DataFrame.PlotType;

/**
 * An example application that authenticates, executes a query, and gets the
 * data.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="support@coherentlogic.com">Support</a>
 */
@SpringBootApplication
//@EnableAutoConfiguration
@ComponentScan(basePackages="com.coherentlogic.coherent.datafeed")
//@ImportResource({"classpath*:spring/jmx-beans.xml"}) // When disabled see also
// GlobalConfig.getPerformanceMonitorService
public class ElektronQueryBuilderExampleApplication implements CommandLineRunner, RDMFieldDictionaryConstants {

    private static final Logger log = LoggerFactory.getLogger(ElektronQueryBuilderExampleApplication.class);

    @Autowired
    private ApplicationContext applicationContext;

    private ElektronQueryBuilder elektronQueryBuilder;

    public ElektronQueryBuilderExampleApplication () {
    }

    public static void main (String[] unused) throws InterruptedException {

        try {

            SpringApplicationBuilder builder =
                new SpringApplicationBuilder (ElektronQueryBuilderExampleApplication.class);

            builder
                .web(false)
                .headless(false)
                .registerShutdownHook(true)
                .run(unused);

        } catch (Throwable thrown) {
            log.error("ExampleApplication.main caught an exception.", thrown);
        }

        Thread.sleep(Long.MAX_VALUE);

        System.exit(-9999);
    }

    @Override
    public void run(String... args) throws Exception {

        String dacsId = System.getenv(DACS_ID);

        elektronQueryBuilder = applicationContext.getBean(ElektronQueryBuilder.class);

        SessionBean sessionBean = elektronQueryBuilder.newSessionBean(dacsId);

        sessionBean.getStatusResponse().addPropertyChangeListener(
            event -> {
                System.out.println("statusResponse.event: " + event);
            }
        );

        elektronQueryBuilder.login(sessionBean);

        final TimeSeriesJFrame timeSeriesJFrame = new TimeSeriesJFrame ();

        timeSeriesJFrame
            .getGoButton()
            .addActionListener(
                event -> {

                    String ric = timeSeriesJFrame.getRicTextField().getText();

                    System.out.println("ric: " + ric);

                    try {
                        queryTimeSeriesService(ric);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            );

        timeSeriesJFrame.setVisible(true);

        queryMarketByPriceService ();
        queryMarketByOrderService ();
        queryMarketMakerService ();
        queryMarketPriceService ();

        log.info("...done!");

        // NOTE: If we end too soon this may be part of the reason why we're
        //       seeing an ommMsg with the final flag set -- one way of proving
        //       this would be to wait and then see if we consistently can
        //       login.
    }

    /**
     * TRI.N, MSFT.O, GOOG.O, AMZN.O, BFb.N
     */
    public void queryTimeSeriesService (String ric)
        throws InterruptedException, ExecutionException, TimeoutException, ParseException {

        TimeSeries timeSeries = elektronQueryBuilder.getTimeSeriesFor(
            Constants.ELEKTRON_DD,
            ric,
            TS1Constants.MONTHLY_PERIOD
        );

        timeSeries.sortSamplesByDate();

        AttribInfo attribInfo = timeSeries.getAttribInfo();

        System.out.print("timeSeries: " + timeSeries + ", attribInfo: " + attribInfo + ", sampleSize: "
            + timeSeries.getSamples().size() + "\n\n\n");

        List<String> headers = timeSeries.getHeaders();

        DataFrame<Object> dataFrame = new DataFrame<Object> ();

        for (String nextHeader : headers) {

            if (!nextHeader.equals(Constants.BIG_VOLUME)
                && !nextHeader.equals(Constants.BIG_DATE)
                && !nextHeader.equals(RDMFieldDictionaryConstants.BID) // Bid format is not correct so we're ignoring it.
            ) {
                List<? extends Object> nextValues = toBigDecimalList (timeSeries.getValuesForHeader(nextHeader));

                // JOINERY NOTE:
                // If we don't cast then we end up with a data frame that has only column names.
                dataFrame.add((Object)nextHeader, (List<Object>)nextValues);

            } else if (Constants.BIG_DATE.equals(nextHeader)) {

                List<? extends Object> dates = toDateList (timeSeries.getValuesForHeader(Constants.BIG_DATE));

                dataFrame.add((Object) Constants.BIG_DATE, (List<Object>) dates);
            }
        }

        dataFrame.plot(PlotType.LINE);
        dataFrame.show();
    }

    static List<Long> toLongList (List<String> values) {

        List<Long> results = new ArrayList<Long> (values.size());

        try {
            for (String next : values) {
                results.add(Long.valueOf(next));
            }
        } catch (Throwable thrown) {
            System.out.println ("Exception thrown for values: " + values);
            thrown.printStackTrace(System.err);
        }

        return results;
    }

    static List<BigDecimal> toBigDecimalList (List<String> values) throws ParseException {

        List<BigDecimal> results = new ArrayList<BigDecimal> (values.size());

        for (String next : values) {

            if (next == null)
                results.add(null);
            else {
                next = NumberFormat.getNumberInstance(java.util.Locale.US).parse(next).toString();
                results.add(new BigDecimal (next));
            }
        }

        return results;
    }

//  DateFormat formatter = new SimpleDateFormat("yyyy MMM dd   HH:mm");
//
//  String TABS = "\t\t\t\t\t";
//
//  boolean closeAdded = false;
//
//  for (String nextHeader : timeSeries.getHeaders()) {
//      if (!closeAdded) {
//          closeAdded = true;
//          System.out.print(nextHeader + TABS);
//      } else {
//          System.out.print(nextHeader + "\t\t\t");
//      }
//  }
//
//  for (Sample sample : timeSeries.getSamples()) {
//
//      long timeMillis = sample.getDate();
//
//      Calendar calendar = Calendar.getInstance();
//
//      calendar.setTimeInMillis(timeMillis);
//
//      String date = formatter.format(calendar.getTime());
//
//      System.out.print("\n" + date);
//
//      for (String nextPoint : sample.getPoints())
//          System.out.print ("\t\t\t" + nextPoint);
//  }

    static List<Date> toDateList (List<String> values) {

        List<Date> results = new ArrayList<Date> (values.size());

        try {

            for (String next : values) {

                long timeMillis = Long.valueOf(next);

                Calendar calendar = Calendar.getInstance();

                calendar.setTimeInMillis(timeMillis);

                results.add(calendar.getTime());
            }
        } catch (Throwable thrown) {
            System.out.println ("Exception thrown for values: " + values);
            thrown.printStackTrace(System.err);
            throw (thrown);
        }

        return results;
    }

    public void queryMarketByOrderService () {

        AtomicLong aggregateUpdateCtr = new AtomicLong (0);
        AtomicLong statusUpdateCtr = new AtomicLong (0);

//        for (String nextRic : new String[] {"AOO.BR"}) {
        for (String nextRic : new String[] {"BRIC.MI"}) {

            /* We *MUST* acquire the MarketByOrder instance from the Spring container because we are using AOP and if we
             * simply create the class directly by calling the ctor, none of the property change events will fire when
             * an update is applied and the same applies to the StatusResponse.
             */
            MarketByOrder marketByOrder = elektronQueryBuilder.newMarketByOrder(nextRic);

            marketByOrder.addAggregatePropertyChangeListener(
                new AggregatePropertyChangeListener () {

                    @Override
                    public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent event) {

                        System.out.println("marketByOrder.aggregateUpdate [" + aggregateUpdateCtr.incrementAndGet()
                            + "] begins for marketByOrder: " + marketByOrder);

                        event
                            .getPropertyChangeEventMap()
                            .forEach((key, value) -> {
                                System.out.println("- key: " + key + ", value: " + ((PropertyChangeEvent)value).getNewValue());
                            }
                        );

                        System.out.println("aggregateUpdate ends.");
                    }
                }
            );

            StatusResponse statusResponse = marketByOrder.getStatusResponse();

            statusResponse.addAggregatePropertyChangeListener(
                event -> {

                    System.out.println("marketByOrder.statusResponse.aggregateUpdate [" +
                        statusUpdateCtr.incrementAndGet() + "] begins for marketByOrder: " + marketByOrder);

                    event
                        .getPropertyChangeEventMap()
                        .forEach((key, value) -> {
                            System.out.println("- key: " + key + ", value: " + ((PropertyChangeEvent)value).getNewValue());
                        }
                    );

                    System.out.println("aggregateUpdate ends.");
                }
            );

            marketByOrder.addOrderEventListener(
                orderEvent -> {
                    if (orderEvent.getEventType() == EventType.instantiated) {

                        System.out.println("Adding an order due to orderEvent: " + orderEvent);

                        MarketByOrder.Order order = orderEvent.getOrder();

                        order.addAggregatePropertyChangeListener(
                            new AggregatePropertyChangeListener () {
                                @Override
                                public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent event) {

                                    System.out.println("marketByOrder.order.aggregateUpdate begins: " + marketByOrder);

                                    event
                                        .getPropertyChangeEventMap()
                                        .forEach((key, value) -> {
                                            System.out.println("- key: " + key + ", value: " + ((PropertyChangeEvent)value).getNewValue());
                                        }
                                    );

                                    System.out.println("aggregateUpdate ends.");
                                }
                            }
                        );
                    }
                }
            );

            elektronQueryBuilder.query(ServiceName.dELEKTRON_DD, marketByOrder);
        }
    }

    public void queryMarketByPriceService () {

        AtomicLong aggregateUpdateCtr = new AtomicLong (0);
        AtomicLong statusUpdateCtr = new AtomicLong (0);

        // BRIC.MI causes an NPE.
        for (String nextRic : ExampleRICS.rics) { //new String[] {"DBSM.SI"})

            /* We *MUST* acquire the MarketByPrice instance from the Spring container because we are using AOP and if we
             * simply create the class directly by calling the ctor, none of the property change events will fire when
             * an update is applied and the same applies to the StatusResponse.
             */
            MarketByPrice marketByPrice = elektronQueryBuilder.newMarketByPrice(nextRic);

            marketByPrice.addAggregatePropertyChangeListener(
                new AggregatePropertyChangeListener () {

                    @Override
                    public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent event) {

                        System.out.println("marketByPrice.aggregateUpdate [" + aggregateUpdateCtr.incrementAndGet()
                            + "] begins for marketByPrice: " + marketByPrice);

                        event
                            .getPropertyChangeEventMap()
                            .forEach((key, value) -> {
                                System.out.println("- key: " + key + ", value: " + ((PropertyChangeEvent)value).getNewValue());
                            }
                        );

                        System.out.println("aggregateUpdate ends.");
                    }
                }
            );

            StatusResponse statusResponse = marketByPrice.getStatusResponse();

            statusResponse.addAggregatePropertyChangeListener(
                event -> {

                    System.out.println("marketByPrice.statusResponse.aggregateUpdate [" +
                        statusUpdateCtr.incrementAndGet() + "] begins for marketByPrice: " + marketByPrice);

                    event
                        .getPropertyChangeEventMap()
                        .forEach((key, value) -> {
                            System.out.println("- key: " + key + ", value: " + ((PropertyChangeEvent)value).getNewValue());
                        }
                    );

                    System.out.println("aggregateUpdate ends.");
                }
            );

            marketByPrice.addOrderEventListener(
                orderEvent -> {
                    if (orderEvent.getEventType() == EventType.instantiated) {

                        System.out.println("[" + nextRic + "] : Adding an order due to orderEvent: " + orderEvent);

                        MarketByPrice.Order order = orderEvent.getOrder ();

                        order.addAggregatePropertyChangeListener(
                            new AggregatePropertyChangeListener () {
                                @Override
                                public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent event) {

                                    System.out.println("marketByPrice.order.aggregateUpdate begins: " + marketByPrice);

                                    event
                                        .getPropertyChangeEventMap()
                                        .forEach((key, value) -> {
                                            System.out.println("- key: " + key + ", value: " + ((PropertyChangeEvent)value).getNewValue());
                                        }
                                    );

                                    System.out.println("aggregateUpdate ends.");
                                }
                            }
                        );
                    }
                }
            );

            elektronQueryBuilder.query(ServiceName.dELEKTRON_DD, marketByPrice);
        }
    }

    public void queryMarketMakerService () {

        AtomicLong aggregateUpdateCtr = new AtomicLong (0);
        AtomicLong statusUpdateCtr = new AtomicLong (0);

        for (String nextRic : ExampleRICS.rics) {

            /* We *MUST* acquire the MarketMaker instance from the Spring container because we are using AOP and if we
             * simply create the class directly by calling the ctor, none of the property change events will fire when
             * an update is applied and the same applies to the StatusResponse.
             */
            MarketMaker marketMaker = elektronQueryBuilder.newMarketMaker(nextRic);

            marketMaker.addAggregatePropertyChangeListener(
                new AggregatePropertyChangeListener () {

                    @Override
                    public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent event) {

                        System.out.println("marketMaker.aggregateUpdate [" + aggregateUpdateCtr.incrementAndGet()
                            + "] begins for marketMaker: " + marketMaker);

                        event
                            .getPropertyChangeEventMap()
                            .forEach((key, value) -> {
                                System.out.println("- key: " + key + ", value: " + value);
                            }
                        );

                        System.out.println("aggregateUpdate ends.");
                    }
                }
            );

            StatusResponse statusResponse = marketMaker.getStatusResponse();

            statusResponse.addAggregatePropertyChangeListener(
                event -> {

                    System.out.println("statusResponse.aggregateUpdate ["+ statusUpdateCtr.incrementAndGet() +"] "
                        + "begins for " + "marketMaker: " + marketMaker);

                    event
                        .getPropertyChangeEventMap()
                        .forEach((key, value) -> {
                            System.out.println("- key: " + key + ", value: " + value);
                        }
                    );

                    System.out.println("aggregateUpdate ends.");
                }
            );

            marketMaker.addOrderEventListener(
                orderEvent -> {
                    if (orderEvent.getEventType() == EventType.instantiated) {
                        orderEvent
                        .getOrder()
                        .addAggregatePropertyChangeListener(
                            new AggregatePropertyChangeListener () {
                                @Override
                                public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent event) {

                                    System.out.println("marketMaker.order.aggregateUpdate begins for marketMaker: "
                                        + marketMaker);

                                    event
                                        .getPropertyChangeEventMap()
                                        .forEach((key, value) -> {
                                            System.out.println("- key: " + key + ", value: " + value);
                                        }
                                    );

                                    System.out.println("aggregateUpdate ends.");
                                }
                            }
                        );
                    }
                }
            );

            elektronQueryBuilder.query(ServiceName.dELEKTRON_DD, marketMaker);
        }
    }

    public void queryMarketPriceService () {

        AtomicLong aggregateUpdateCtr = new AtomicLong (0);
        AtomicLong statusUpdateCtr = new AtomicLong (0);

        for (String nextRic : ExampleRICS.rics) {

            /* We *MUST* acquire the MarketPrice instance from the Spring container because we are using AOP and if we
             * simply create the class directly by calling the ctor, none of the property change events will fire when
             * an update is applied and the same applies to the StatusResponse.
             */
            MarketPrice marketPrice = elektronQueryBuilder.newMarketPrice(nextRic);

            marketPrice.addAggregatePropertyChangeListener(
                new AggregatePropertyChangeListener () {

                    @Override
                    public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent event) {

                        System.out.println("marketPrice.aggregateUpdate [" + aggregateUpdateCtr.incrementAndGet() +
                            "] begins for: " + marketPrice);

                        event
                            .getPropertyChangeEventMap()
                            .forEach((key, value) -> {
                                System.out.println("- key: " + key + ", value: " + value);
                            }
                        );

                        System.out.println("aggregateUpdate ends.");
                    }
                }
            );

            StatusResponse statusResponse = marketPrice.getStatusResponse();

            statusResponse.addAggregatePropertyChangeListener(
                event -> {

                    System.out.println("marketPrice.statusResponse.aggregateUpdate ["+ statusUpdateCtr.incrementAndGet()
                        + "] begins for " + marketPrice);

                    event
                        .getPropertyChangeEventMap()
                        .forEach((key, value) -> {
                            System.out.println("- key: " + key + ", value: " + value);
                        }
                    );

                    System.out.println("aggregateUpdate ends.");
                }
            );

            elektronQueryBuilder.query(ServiceName.dELEKTRON_DD, marketPrice);
        }
    }
}