package com.coherentlogic.coherent.datafeed.examples;

import static com.coherentlogic.coherent.datafeed.misc.Constants.AUTHENTICATION_ENTRY_POINT;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_APP_CTX_PATH;
import static com.coherentlogic.coherent.datafeed.misc.Constants.FRAMEWORK_EVENT_LISTENER_ADAPTER;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_PRICE_SERVICE_GATEWAY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STATUS_RESPONSE_SERVICE_GATEWAY;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapter;
import com.coherentlogic.coherent.datafeed.client.ui.MainUI;
import com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants;
import com.coherentlogic.coherent.datafeed.listeners.FrameworkEventListener;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MarketPriceServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.PauseResumeService;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.coherentlogic.coherent.datafeed.services.StatusResponseServiceSpecification;
import com.reuters.rfa.common.Handle;

/**
 * An example application that authenticates, executes a query, and gets the
 * data.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketPriceExample implements MarketPriceConstants {

    private static final Logger log =
        LoggerFactory.getLogger(MarketPriceExample.class);

    public static void main (String[] unused)
        throws JMSException, InterruptedException {

        AbstractApplicationContext applicationContext =
            new ClassPathXmlApplicationContext (
                DEFAULT_APP_CTX_PATH);

        final MainUI mainUI = applicationContext.getBean(MainUI.class);

        mainUI.mainFrame.pack();
        mainUI.mainFrame.setVisible(true);

        applicationContext.registerShutdownHook();

        final StatusResponseServiceSpecification statusResponseService =
            (StatusResponseServiceSpecification) applicationContext.
                getBean(STATUS_RESPONSE_SERVICE_GATEWAY);

        final AuthenticationServiceSpecification authenticationService =
            (AuthenticationServiceSpecification) applicationContext.getBean(
                AUTHENTICATION_ENTRY_POINT);

        final FrameworkEventListenerAdapter frameworkEventListenerAdapter =
            (FrameworkEventListenerAdapter)
                applicationContext.getBean(FRAMEWORK_EVENT_LISTENER_ADAPTER);

        final MarketPriceServiceSpecification marketPriceService =
            (MarketPriceServiceSpecification)
            applicationContext.getBean(MARKET_PRICE_SERVICE_GATEWAY);

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

        String dacsId = System.getenv(DACS_ID);

        Handle loginHandle = authenticationService.login(dacsId);

        log.info("main thread: " + Thread.currentThread());

        boolean result = pauseResumeService.pause();

        log.info("result: " + result);

        queryMarketPriceService (
            statusResponseService,
            marketPriceService,
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

    static void queryMarketPriceService (
        final StatusResponseServiceSpecification statusResponseService,
        final MarketPriceServiceSpecification marketPriceService,
        final Handle loginHandle
    ) {
        List<Handle> itemHandles = marketPriceService.query(
            Constants.dELEKTRON_DD,
            loginHandle,
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
            "SWM.N",
            "ERICb.F",
            "ERICb.DE",
            "ERICb.D",
            "ERICb.BE",
            "ERICa.ST",
            "ERICa.F",
            "ERICa.DE",
            "ERICa.BE",
            "ERIC.W",
            "ERIC.PH",
            "ERIC.P",
            "ERIC.OQ",
            "ERIC.MW",
            "ERIC.DF",
            "ERIC.C",
            "ERIC.A",
            "DRICqf.BO",
            "BRICUSDNAV.DE",
            "BRICGBPNAV.DE",
            "BRICDX.MI",
            "BRIC.S",
            "BRIC.MI",
            "BRIC.AS",
            "ARICqf.BO",
            "ARIC.F",
            "ALRIC.PA1",
            ".VBRICUTR",
            "ERIC.O",
            "RIC.A",
            "ALRIC.PA",
            "ARIC.BO",
            "BRIC.L",
            "DRIC.BO",
            "AAT.N",
            "ABV.N",
            "ABVc.N",
            "ABX.N",
            "ACAS.O",
            "ACC.N",
            "ADGE.A",
            "AEL.N",
            "AEO.N",
            "AEP.N",
            "AEP_pa.N",
            "AFA.N",
            "AFE.N",
            "AFF.N",
            "AFG.N",
            "AFQ.N",
            "AFW.N",
            "AGM.N",
            "AGMa.N",
            "AGNC.O",
            "AGNCP.O",
            "AIG.N",
            "ALN.A",
            "AM.N",
            "AMID.N",
            "AMNB.O",
            "AMOV.O",
            "AMRB.O",
            "AMS.A",
            "AMSC.O",
            "AMSWA.O",
            "AMT.N",
            "AMWD.O",
            "AMX.N",
            "ANAT.O",
            "APEI.O",
            "APP.A",
            "AQQ.A",
            "ARC.N",
            "ARCT.O",
            "ARII.O",
            "ANAT.O",
            "APEI.O",
            "APP.A",
            "AQQ.A",
            "ARC.N",
            "ARCT.O",
            "ARII.O",
            "ARL.N",
            "ARSD.N",
            "ASEI.O",
            "ASI.N",
            "ASP.N",
            "ATAX.O",
            "AUQ.N",
            "AVD.N",
            "AVF.N",
            ".DAXBRIC",
            ".DAXBRICGB",
            ".DAXBRICGBN",
            ".DAXBRICGBP",
            ".TRXFLDAFPU", // Equity
            ".TRXFLDAFPUMAT" // Equity
        );

        log.info ("The query is complete, now we will wait " +
            "for replies; itemHandles: " + itemHandles);

        long ctr = 0;

        while (true) {
//            String nextStatusResponseUpdate = statusResponseService.getNextUpdateAsJSON("2000");
//
//            log.info ("nextStatusResponseUpdate[" + ctr + "]: " +
//                nextStatusResponseUpdate);

            String nextMarketPriceUpdate =
                marketPriceService.getNextUpdateAsJSON(2500L);
            log.info ("nextMarketPriceUpdate[" + ctr + "]: " +
                nextMarketPriceUpdate);
            System.out.println ("nextMarketPriceUpdate[" + ctr + "]: " +
                nextMarketPriceUpdate);

            ctr++;
        }
    }
}
