package net.coherentlogic.coherent.datafeed.examples;

import static com.coherentlogic.coherent.datafeed.misc.Constants.AUTHENTICATION_ENTRY_POINT;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;
import static com.coherentlogic.coherent.datafeed.misc.Constants.FRAMEWORK_EVENT_LISTENER_ADAPTER;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_BY_ORDER_SERVICE_GATEWAY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STATUS_RESPONSE_SERVICE_GATEWAY;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.support.AbstractApplicationContext;

import com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapterSpecification;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants;
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
//@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan(basePackages="com.coherentlogic.coherent.datafeed")
public class MarketByOrderExample implements CommandLineRunner, MarketPriceConstants {

    private static final Logger log =
        LoggerFactory.getLogger(MarketByOrderExample.class);

    @Autowired
    private AbstractApplicationContext applicationContext;

    public static void main (String[] unused) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder (MarketByOrderExample.class);

        builder
            .web(false)
            .headless(false)
            .run(unused);
    }

    @Override
    public void run(String... args) throws Exception {

        final StatusResponseServiceSpecification statusResponseService =
            (StatusResponseServiceSpecification) applicationContext.
                getBean(STATUS_RESPONSE_SERVICE_GATEWAY);

        final AuthenticationServiceSpecification authenticationService =
            (AuthenticationServiceSpecification) applicationContext.getBean(
                AUTHENTICATION_ENTRY_POINT);

        final FrameworkEventListenerAdapterSpecification frameworkEventListenerAdapter =
            (FrameworkEventListenerAdapterSpecification)
                applicationContext.getBean(FRAMEWORK_EVENT_LISTENER_ADAPTER);

        final MarketByOrderServiceSpecification marketByOrderService =
            (MarketByOrderServiceSpecification)
            applicationContext.getBean(MARKET_BY_ORDER_SERVICE_GATEWAY);

        final PauseResumeService pauseResumeService = new PauseResumeService ();

        frameworkEventListenerAdapter.addInitialisationSuccessfulListeners (
            Arrays.asList(
                new FrameworkEventListener() {
                    @Override
                    public void onEventReceived(Session session) {
                        pauseResumeService.resume(true);
                    }
                }
            )
        );

        frameworkEventListenerAdapter.addInitialisationFailedListeners (
            Arrays.asList(
                new FrameworkEventListener () {
                    @Override
                    public void onEventReceived(Session session) {
                        pauseResumeService.resume(false);
                    }
                }
            )
        );

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

        Thread.sleep(Long.MAX_VALUE);

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
        Map<String, MarketByOrder> marketByOrderMap = marketByOrderService.query(
            ServiceName.dELEKTRON_DD,
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

        AtomicLong ctr = new AtomicLong (0);

        marketByOrderMap.forEach(
            (String key, MarketByOrder value) -> {

                System.out.println("Adding an instance of PropertyChangeListener for the ric " + key +
                    " and marketByOrder " + value);

                value.addPropertyChangeListener(
                    event -> {

                        long currentCtr = ctr.incrementAndGet();

                        String text = "[ric: "+ key +"]; nextMarketByOrderUpdate[" + currentCtr + "]: " + event;

//                        if (currentCtr % 100 == 0) {
                            System.out.println (text);
//                        }
                    }
                );

                value.getOrders().forEach(
                    (String orderKey, MarketByOrder.Order order) -> {

                        System.out.println("Adding an instance of PropertyChangeListener for the orderKey " + orderKey +
                            " and marketByOrder.order " + order.getUniqueId());

                        AtomicLong orderCtr = new AtomicLong (0);

                        order.addPropertyChangeListener(
                            event -> {

                                long orderCtrValue = orderCtr.getAndIncrement();

                                if (orderCtrValue % 100 == 0) {
                                    String text = "[orderKey: "+ orderKey +"]; nextMarketByOrderOrderUpdate[" +
                                        orderCtrValue + "]: " + event;
                                    System.out.println(text);
                                }
                            }
                        );
                    }
                );
            }
        );
    }
}