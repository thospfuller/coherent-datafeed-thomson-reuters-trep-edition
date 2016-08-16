package com.coherentlogic.coherent.datafeed.jms.examples;

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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeEvent;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeListener;
import com.coherentlogic.coherent.datafeed.builders.ElektronQueryBuilder;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.coherentlogic.coherent.datafeed.exceptions.UpdateFailedException;
import com.coherentlogic.coherent.datafeed.services.ServiceName;

/**
 * An example application that authenticates, executes a query, and gets the data.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
// DISABLED BECAUSE THIS CLASS WILL BE PICKED UP BY SPRING BOOT AND WE ONLY WANT ONE TO BE RUNNING (IN PARTICULAR IN
// THE R PACKAGE.
//@SpringBootApplication
//@ComponentScan(basePackages="com.coherentlogic.coherent.datafeed")
//@ImportResource({"classpath*:spring/hornetq-beans.xml", "classpath*:spring/marketprice-jms-workflow-beans.xml"})
public class MarketPriceJMSExample implements CommandLineRunner, MarketPriceConstants {

    private static final Logger log = LoggerFactory.getLogger(MarketPriceJMSExample.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("addMarketPriceToQueueChannel")
    private MessageChannel addMarketPriceToQueueChannel;

    private ElektronQueryBuilder elektronQueryBuilder;

    public static void main (String[] unused) {

//        SpringApplicationBuilder builder = new SpringApplicationBuilder (MarketPriceJMSExample.class);
//
//        builder
//            .web(false)
//            .headless(false)
//            .registerShutdownHook(true)
//            .run(unused);
    }

    @Override
    public void run(String... args) throws Exception {

//        String dacsId = System.getenv(DACS_ID);
//
//        elektronQueryBuilder = applicationContext.getBean(ElektronQueryBuilder.class);
//
//        SessionBean sessionBean = elektronQueryBuilder.newSessionBean(dacsId);
//
//        sessionBean.getStatusResponse().addPropertyChangeListener(
//            event -> {
//                System.out.println("statusResponse.event: " + event);
//            }
//        );
//
//        elektronQueryBuilder.login(sessionBean);
//
//        AtomicLong nextUpdateCtr = new AtomicLong (0);
//
//        final MessageConsumer messageConsumer = (MessageConsumer) applicationContext.getBean("marketPriceConsumer");
//
//        new Thread (
//            () -> {
//                while (true) {
//                    MarketPrice next = getNextUpdate (messageConsumer, 5000L);
//                    System.out.println("mpjmse.next["+ nextUpdateCtr.incrementAndGet() +"]: " + next);
//                }
//            }
//        ).start ();
//
//        queryMarketPriceService ();
//
//        log.info("...done!");
//
//        // NOTE: If we end too soon this may be part of the reason why we're
//        //       seeing an ommMsg with the final flag set -- one way of proving
//        //       this would be to wait and then see if we consistently can
//        //       login.
    }

    public void queryMarketPriceService () {

        AtomicLong statusUpdateCtr = new AtomicLong (0);

        for (String nextRic : rics) {

            /* We *MUST* acquire the MarketPrice instance from the Spring container because we are using AOP and if we
             * simply create the class directly by calling the ctor, none of the property change events will fire when
             * an update is applied and the same applies to the StatusResponse.
             */
            MarketPrice marketPrice = elektronQueryBuilder.newMarketPrice(nextRic);

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

    public MarketPrice getNextUpdate (MessageConsumer messageConsumer, Long timeout) {

        log.info("getNextUpdate: method begins; timeout: " + timeout);

        Message nextMessage;

        try {
            nextMessage = messageConsumer.receive(timeout);
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

        log.info("getNextUpdate: method ends; result: " + result);

        return result;
    }

    public static final String[] rics = {
        "LCOc1",
        "GOOG.O",
        "MSFT.O",
        "ODFL.OQ",
        "LKQ.OQ",
        "MDVN.OQ",
        "BFb.N",
        "KO.N",
        ".TRXFLDAFPUM11", // Equity
        "OIBR.K",
        "SWM.N"
    };
}
