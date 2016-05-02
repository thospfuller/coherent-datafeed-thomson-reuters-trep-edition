package com.coherentlogic.coherent.datafeed.jms.examples;

import static com.coherentlogic.coherent.datafeed.misc.Constants.AUTHENTICATION_ENTRY_POINT;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;
import static com.coherentlogic.coherent.datafeed.misc.Constants.FRAMEWORK_EVENT_LISTENER_ADAPTER;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_BY_ORDER_SERVICE_GATEWAY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STATUS_RESPONSE_SERVICE_GATEWAY;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;

import com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapter;
import com.coherentlogic.coherent.datafeed.client.ui.MainUI;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.listeners.FrameworkEventListener;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MarketByOrderServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.PauseResumeService;
import com.coherentlogic.coherent.datafeed.services.ServiceName;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.coherentlogic.coherent.datafeed.services.StatusResponseServiceSpecification;
import com.reuters.rfa.common.Handle;

/**
 * An example application that authenticates, executes a query, and gets the
 * data.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages="com.coherentlogic.coherent.datafeed")
public class MarketByOrderExample implements CommandLineRunner {

    private static final Logger log =
        LoggerFactory.getLogger(MarketByOrderExample.class);

    @Autowired
    private AbstractApplicationContext applicationContext;

    public static void main (String[] unused) throws Exception {

        SpringApplicationBuilder builder = new SpringApplicationBuilder (MarketByOrderExample.class);

        builder
            .web(false)
            .headless(false)
            .run(unused);
    }

    @Override
    public void run (String... unused) {

        applicationContext.registerShutdownHook();

        MainUI mainUI = applicationContext.getBean(MainUI.class);

        mainUI.mainFrame.pack();
        mainUI.mainFrame.setVisible(true);

        final StatusResponseServiceSpecification statusResponseService =
            (StatusResponseServiceSpecification) applicationContext.
                getBean(STATUS_RESPONSE_SERVICE_GATEWAY);

        final AuthenticationServiceSpecification authenticationService =
            (AuthenticationServiceSpecification) applicationContext.getBean(
            AUTHENTICATION_ENTRY_POINT);

        final FrameworkEventListenerAdapter frameworkEventListenerAdapter =
            (FrameworkEventListenerAdapter)
                applicationContext.getBean(FRAMEWORK_EVENT_LISTENER_ADAPTER);

        final MarketByOrderServiceSpecification marketByOrderService =
            (MarketByOrderServiceSpecification)
            applicationContext.getBean(MARKET_BY_ORDER_SERVICE_GATEWAY);

        final PauseResumeService pauseResumeService = new PauseResumeService ();

        frameworkEventListenerAdapter.addInitialisationSuccessfulListeners (
            new FrameworkEventListener() {
                @Override
                public void onEventReceived(Session session) {
                    pauseResumeService.resume(true);
                }
            }
        );

        frameworkEventListenerAdapter.addInitialisationFailedListeners (
            new FrameworkEventListener () {
                @Override
                public void onEventReceived(Session session) {
                    pauseResumeService.resume(false);
                }
        });

        // This needs to be set in the operating system environment variables.
        String dacsId = System.getenv(DACS_ID);

        Handle loginHandle = authenticationService.login(dacsId);

        log.info("main thread: " + Thread.currentThread());

        boolean result = pauseResumeService.pause();

        log.info("result: " + result);

        queryMarketByOrderService (
            statusResponseService,
            marketByOrderService,
            loginHandle
        );

        log.info("...done!");

        applicationContext.close();

        System.exit(-9999);
        // NOTE: If we end too soon this may be part of the reason why we're
        //       seeing an ommMsg with the final flag set -- one way of proving
        //       this would be to wait and then see if we consistently can
        //       login.
    }

    static void queryMarketByOrderService (
        final StatusResponseServiceSpecification statusResponseService,
        final MarketByOrderServiceSpecification marketByOrderService,
        final Handle loginHandle
    ) {
        Map<String, MarketByOrder> results = marketByOrderService.query(
            ServiceName.dELEKTRON_DD, loginHandle, "ANZ.AX");

//        log.info ("The query is complete, now we will wait " +
//            "for replies; itemHandles: " + itemHandles);

        long ctr = 0;

//        while (true) {
//
//            String nextMarketPriceUpdate =
//                marketByOrderService.getNextUpdateAsJSON(2500L);
//            log.info ("nextMarketPriceUpdate[" + ctr + "]: " +
//                nextMarketPriceUpdate);
//            System.out.println ("nextMarketPriceUpdate[" + ctr + "]: " +
//                nextMarketPriceUpdate);
//
//            ctr++;
//        }
    }
}
