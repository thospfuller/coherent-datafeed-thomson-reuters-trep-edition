package com.coherentlogic.coherent.datafeed.db.integration.client;

import static com.coherentlogic.coherent.datafeed.misc.Constants.AUTHENTICATION_ENTRY_POINT;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_APP_CTX_PATH;
import static com.coherentlogic.coherent.datafeed.misc.Constants.FRAMEWORK_EVENT_LISTENER_ADAPTER;
import static com.coherentlogic.coherent.datafeed.misc.Constants.LICENSE_INSTALLATION_SERVICE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_PRICE_SERVICE_GATEWAY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STATUS_RESPONSE_SERVICE_GATEWAY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.TIME_SERIES_DAO;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapter;
import com.coherentlogic.coherent.datafeed.client.Client;
import com.coherentlogic.coherent.datafeed.db.integration.dao.MarketPriceDAO;
import com.coherentlogic.coherent.datafeed.db.integration.dao.TimeSeriesDAO;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.listeners.FrameworkEventListener;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MarketPriceServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.PauseResumeService;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.coherentlogic.coherent.datafeed.services.StatusResponseServiceSpecification;
import com.reuters.rfa.common.Handle;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AdvancedClient extends Client {

    private static final Logger log =
            LoggerFactory.getLogger(AdvancedClient.class);

    static final String ADVANCED_CLIENT = "advancedClient",
        MARKET_PRICE_DAO = "marketPriceDAO";

    public static void main (String[] unused) {
        try {
            doMain (unused);
        } catch (Throwable thrown) {
            log.error("An exception was thrown from the doMain method.",
                thrown);
        }
    }

    public static void doMain (String[] unused) throws Throwable {

         AbstractApplicationContext applicationContext =
            new ClassPathXmlApplicationContext (
                DEFAULT_APP_CTX_PATH);

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

//        Thread.currentThread().sleep(15000L);

        log.info("result: " + result);

        marketPriceService.query(
            Constants.dIDN_RDF,
            loginHandle,
            "GOOG.O",
            "MSFT.O",
            "ODFL.OQ",
            "LKQ.OQ",
            "MDVN.OQ",
            "BFb.N",
            "KO.N",
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
            ".TRXFLDAFPUMAT", // Equity
            ".TRXFLDAFPUM11" // Equity
        );

        MarketPriceDAO marketPriceDAO = (MarketPriceDAO)
            applicationContext.getBean(MARKET_PRICE_DAO);

        TimeSeriesDAO timeSeriesDAO = (TimeSeriesDAO)
                applicationContext.getBean(TIME_SERIES_DAO);

        long ctr = 0;

        while (ctr < 115) {
            MarketPrice nextUpdate = marketPriceService.getNextUpdate(2500L);

            log.info("ctr[" + ctr +
                "], serializedMarketPrice.tradeTime: " + nextUpdate);

            marketPriceDAO.persist(nextUpdate);

            ctr++;

            if ((ctr % 1000) == 0)
                log.info("ctr: " + ctr + ", id: " + nextUpdate.getId());
        }

//        applicationContext.close();
//
//        System.exit(-9999);
    }
}
