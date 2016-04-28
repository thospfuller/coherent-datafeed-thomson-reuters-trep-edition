package com.coherentlogic.coherent.datafeed.jms.examples;

import static com.coherentlogic.coherent.datafeed.misc.Constants.AUTHENTICATION_SERVICE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;
import static com.coherentlogic.coherent.datafeed.misc.Constants.FRAMEWORK_EVENT_LISTENER_ADAPTER;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_MAKER_SERVICE_GATEWAY;

import java.util.List;

import javax.jms.JMSException;

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
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants;
import com.coherentlogic.coherent.datafeed.listeners.FrameworkEventListener;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MarketMakerServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.PauseResumeService;
import com.coherentlogic.coherent.datafeed.services.Session;
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
public class MarketMakerExample implements CommandLineRunner, MarketPriceConstants {

    private static final Logger log =
        LoggerFactory.getLogger(MarketMakerExample.class);

    @Autowired
    private AbstractApplicationContext applicationContext;

    public static void main (String[] unused) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder (MarketMakerExample.class);

        builder
            .web(false)
            .headless(false)
            .run(unused);
    }

    public void run (String... unused) throws JMSException {

//        AbstractApplicationContext applicationContext =
//            new ClassPathXmlApplicationContext (
//                DEFAULT_APP_CTX_PATH);
//
//        applicationContext.registerShutdownHook();

        final MainUI mainUI = applicationContext.getBean(MainUI.class);

        new Thread (
            new Runnable () {
                @Override
                public void run() {
                    mainUI.mainFrame.pack();
                    mainUI.mainFrame.setVisible(true);
                }
            }
        ).start();

        AuthenticationServiceSpecification authenticationService =
            (AuthenticationServiceSpecification) applicationContext.getBean(
                AUTHENTICATION_SERVICE);

        final FrameworkEventListenerAdapter frameworkEventListenerAdapter =
            (FrameworkEventListenerAdapter)
                applicationContext.getBean(FRAMEWORK_EVENT_LISTENER_ADAPTER);

        MarketMakerServiceSpecification marketMakerService = (MarketMakerServiceSpecification)
            applicationContext.getBean(MARKET_MAKER_SERVICE_GATEWAY);

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

        List<Handle> itemHandles = marketMakerService.query(
            Constants.dELEKTRON_DD,
            loginHandle,
            "GOOG.O",
            "MSFT.O",
            "ODFL.OQ",
            "LKQ.OQ"
        );

        for (Handle nextHandle : itemHandles)
            log.debug("nextHandle: " + nextHandle);

        long ctr = 0;

        final long wait = 5 * 1000;

//        while (true) {
//
//            Object marketMaker = marketMakerService.getNextUpdate(wait);
//
//            if (ctr % 50 == 0) {
//                System.out.println ("next[" + ctr + "]: " + marketMaker);
//            }
//
//            ctr++;
//        }
    }
}
