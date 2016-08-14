package com.coherentlogic.coherent.datafeed.db.integration.client;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_PRICE_DAO;
import static com.coherentlogic.coherent.datafeed.misc.Constants.TIME_SERIES_DAO;

import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeEvent;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeListener;
import com.coherentlogic.coherent.datafeed.builders.ElektronQueryBuilder;
import com.coherentlogic.coherent.datafeed.db.integration.dao.MarketPriceDAO;
import com.coherentlogic.coherent.datafeed.db.integration.dao.TimeSeriesDAO;
import com.coherentlogic.coherent.datafeed.domain.AttribInfo;
import com.coherentlogic.coherent.datafeed.domain.EventType;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants;
import com.coherentlogic.coherent.datafeed.domain.Sample;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.coherentlogic.coherent.datafeed.services.ServiceName;
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
@ImportResource({"classpath*:spring/jpa-beans.xml"})
public class ElektronQueryBuilderDBExampleApplication implements CommandLineRunner, MarketPriceConstants {

    private static final Logger log = LoggerFactory.getLogger(ElektronQueryBuilderDBExampleApplication.class);

    @Autowired
    private ApplicationContext applicationContext;

    private ElektronQueryBuilder elektronQueryBuilder;

    private MarketPriceDAO marketPriceDAO;

    private TimeSeriesDAO timeSeriesDAO;

    public ElektronQueryBuilderDBExampleApplication () {
    }

    public static void main (String[] unused) throws InterruptedException {

        try {

            SpringApplicationBuilder builder =
                new SpringApplicationBuilder (ElektronQueryBuilderDBExampleApplication.class);

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

        marketPriceDAO = (MarketPriceDAO) applicationContext.getBean(MARKET_PRICE_DAO);
        timeSeriesDAO = (TimeSeriesDAO) applicationContext.getBean(TIME_SERIES_DAO);

        SessionBean sessionBean = elektronQueryBuilder.newSessionBean(dacsId);

        sessionBean.getStatusResponse().addPropertyChangeListener(
            event -> {
                System.out.println("statusResponse.event: " + event);
            }
        );

        elektronQueryBuilder.login(sessionBean);

//        queryTimeSeriesService("TRI.N");
//        queryTimeSeriesService("MSFT.O");
//        queryTimeSeriesService("BFb.N");

        queryMarketPriceService ();

//        queryMarketMakerService ();
//
//        queryMarketByOrderService ();

        log.info("...done!");

        // NOTE: If we end too soon this may be part of the reason why we're
        //       seeing an ommMsg with the final flag set -- one way of proving
        //       this would be to wait and then see if we consistently can
        //       login.
    }

    public void queryTimeSeriesService (String ric) throws InterruptedException, ExecutionException, TimeoutException {

        TimeSeries timeSeries = elektronQueryBuilder.getTimeSeriesFor(
            Constants.ELEKTRON_DD, ric, TS1Constants.WEEKLY_PERIOD);

        AttribInfo attribInfo = timeSeries.getAttribInfo();

        System.out.print("timeSeries: " + timeSeries + ", attribInfo: " + attribInfo + ", sampleSize: "
            + timeSeries.getSamples().size() + "\n\n\n");

        DateFormat formatter = new SimpleDateFormat("yyyy MMM dd   HH:mm");

        String TABS = "                    ";

        boolean closeAdded = false;

        for (String nextHeader : timeSeries.getHeaders()) {
            if (!closeAdded) {
                closeAdded = true;
                System.out.print(nextHeader + TABS);
            } else {
                System.out.print(nextHeader + "            ");
            }
        }

        for (Sample sample : timeSeries.getSamples()) {

            long timeMillis = sample.getDate();

            Calendar calendar = Calendar.getInstance();

            calendar.setTimeInMillis(timeMillis);

            String date = formatter.format(calendar.getTime());

            System.out.print("\n" + date);

            for (String nextPoint : sample.getPoints())
                System.out.print ("            " + nextPoint);
        }
    }

    public void queryMarketByOrderService () {

        AtomicLong aggregateUpdateCtr = new AtomicLong (0);
        AtomicLong statusUpdateCtr = new AtomicLong (0);
        AtomicLong marketByOrderOrderCtr = new AtomicLong (0);

        for (String nextRic : ExampleRICS.rics) {

            /* We *MUST* acquire the MarketByOrder instance from the Spring container because we are using AOP and if we
             * simply create the class directly by calling the ctor, none of the property change events will fire when
             * an update is applied and the same applies to the StatusResponse.
             */
            MarketByOrder marketByOrder = elektronQueryBuilder.newMarketByOrder(nextRic);

            marketByOrder.addAggregatePropertyChangeListener(
                new AggregatePropertyChangeListener<MarketByOrder> () {

                    @Override
                    public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent<MarketByOrder> event) {

                        long ctr = aggregateUpdateCtr.incrementAndGet();

                        if (ctr % 1000 == 0) {

                            System.out.println("marketByOrder.aggregateUpdate [" + ctr + "] begins for "
                                + "marketByOrder: " + marketByOrder);

                            event
                                .getPropertyChangeEventMap()
                                .forEach((key, value) -> {
                                    System.out.println("- key: " + key + ", value: " + value);
                                }
                            );

                            System.out.println("aggregateUpdate ends.");
                        }
                    }
                }
            );

            StatusResponse statusResponse = marketByOrder.getStatusResponse();

            statusResponse.addAggregatePropertyChangeListener(
                event -> {

                    long ctr = statusUpdateCtr.incrementAndGet();

//                    if (ctr % 1000 == 0) {

                        System.out.println("marketByOrder.statusResponse.aggregateUpdate [" + ctr + "] begins for "
                            + "marketByOrder: " + marketByOrder);

                        event
                            .getPropertyChangeEventMap()
                            .forEach((key, value) -> {
                                System.out.println("- key: " + key + ", value: " + value);
                            }
                        );

                        System.out.println("aggregateUpdate ends.");
//                    }
                }
            );

            marketByOrder.addOrderEventListener(
                orderEvent -> {
                    if (orderEvent.getEventType() == EventType.instantiated) {

                        System.out.println("Adding an order due to orderEvent: " + orderEvent);

                        MarketByOrder.Order order = orderEvent.getOrder();

                        order.addAggregatePropertyChangeListener(
                            new AggregatePropertyChangeListener<MarketByOrder.Order> () {
                                @Override
                                public void onAggregatePropertyChangeEvent(
                                    AggregatePropertyChangeEvent<MarketByOrder.Order> event) {

                                    long ctr = marketByOrderOrderCtr.incrementAndGet();

                                    if (ctr % 1000 == 0) {

                                        System.out.println("marketByOrder.order.aggregateUpdate begins: " +
                                            marketByOrder);

                                        event
                                            .getPropertyChangeEventMap()
                                            .forEach((key, value) -> {
                                                System.out.println("- key: " + key + ", value: " + value);
                                            }
                                        );

                                        System.out.println("aggregateUpdate ends.");
                                    }
                                }
                            }
                        );
                    }
                }
            );

            elektronQueryBuilder.query(ServiceName.dELEKTRON_DD, marketByOrder);
        }
    }

    public void queryMarketMakerService () {

        AtomicLong aggregateUpdateCtr = new AtomicLong (0);
        AtomicLong statusUpdateCtr = new AtomicLong (0);
        AtomicLong marketMakerOrderCtr = new AtomicLong (0);

        for (String nextRic : ExampleRICS.rics) {

            /* We *MUST* acquire the MarketMaker instance from the Spring container because we are using AOP and if we
             * simply create the class directly by calling the ctor, none of the property change events will fire when
             * an update is applied and the same applies to the StatusResponse.
             */
            MarketMaker marketMaker = elektronQueryBuilder.newMarketMaker(nextRic);

            marketMaker.addAggregatePropertyChangeListener(
                new AggregatePropertyChangeListener<MarketMaker> () {

                    @Override
                    public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent<MarketMaker> event) {

                        long ctr = aggregateUpdateCtr.incrementAndGet();

                        if (ctr % 1000 == 0) {

                            System.out.println("marketMaker.aggregateUpdate [" + ctr + "] begins for marketMaker: " +
                                marketMaker);
    
                            event
                                .getPropertyChangeEventMap()
                                .forEach((key, value) -> {
                                    System.out.println("- key: " + key + ", value: " + value);
                                }
                            );
    
                            System.out.println("aggregateUpdate ends.");
                        }
                    }
                }
            );

            StatusResponse statusResponse = marketMaker.getStatusResponse();

            statusResponse.addAggregatePropertyChangeListener(
                event -> {

                    long ctr = statusUpdateCtr.incrementAndGet();

//                    if (ctr % 1000 == 0) {

                        System.out.println("statusResponse.aggregateUpdate ["+ ctr +"] begins for " + "marketMaker: " +
                            marketMaker);

                        event
                            .getPropertyChangeEventMap()
                            .forEach((key, value) -> {
                                System.out.println("- key: " + key + ", value: " + value);
                            }
                        );

                        System.out.println("aggregateUpdate ends.");
//                    }
                }
            );

            marketMaker.addOrderEventListener(
                orderEvent -> {
                    if (orderEvent.getEventType() == EventType.instantiated) {
                        orderEvent
                        .getOrder()
                        .addAggregatePropertyChangeListener(
                            new AggregatePropertyChangeListener<MarketMaker.Order> () {
                                @Override
                                public void onAggregatePropertyChangeEvent(
                                    AggregatePropertyChangeEvent<MarketMaker.Order> event) {

                                    long ctr = marketMakerOrderCtr.incrementAndGet();

                                    if (ctr % 1000 == 0) {

                                        System.out.println("marketMaker.order[" + ctr + "].aggregateUpdate begins "
                                            + "for marketMaker: " + marketMaker);

                                        event
                                            .getPropertyChangeEventMap()
                                            .forEach((key, value) -> {
                                                System.out.println("- key: " + key + ", value: " + value);
                                            }
                                        );

                                        System.out.println("aggregateUpdate ends.");
                                    }
                                }
                            }
                        );
                    }
                }
            );

            elektronQueryBuilder.query(ServiceName.dELEKTRON_DD, marketMaker);
        }
    }

    public void queryMarketPriceService () throws IOException {

        AtomicLong aggregateUpdateCtr = new AtomicLong (0);

        for (String nextRic : ExampleRICS.rics) {

            /* We *MUST* acquire the MarketPrice instance from the Spring container because we are using AOP and if we
             * simply create the class directly by calling the ctor, none of the property change events will fire when
             * an update is applied and the same applies to the StatusResponse.
             */
            MarketPrice marketPrice = elektronQueryBuilder.newMarketPrice(nextRic);

            marketPrice.addAggregatePropertyChangeListener(
                new AggregatePropertyChangeListener<MarketPrice> () {

                    @Override
                    public void onAggregatePropertyChangeEvent(AggregatePropertyChangeEvent<MarketPrice> event) {

                        MarketPrice source = event.getSource();

                        System.out.println("[" + aggregateUpdateCtr.incrementAndGet() + "] Will persist the "
                            + "marketPrice with ric " + source.getRic());

                        marketPriceDAO.persist(source);

                        System.out.println("...done persisting the marketPrice with ric " + source.getRic());
                    }
                }
            );

            StatusResponse statusResponse = marketPrice.getStatusResponse();

            statusResponse.addAggregatePropertyChangeListener(
                event -> {

                    PropertyChangeEvent propertyChangeEvent =
                        (PropertyChangeEvent) event.getPropertyChangeEventMap().get("code");

                    System.out.println("nextRic: " + nextRic + ", propertyChangeEvent: "
                        + propertyChangeEvent.getNewValue());
                }
            );

            elektronQueryBuilder.query(ServiceName.dELEKTRON_DD, marketPrice);
        }
    }
}
