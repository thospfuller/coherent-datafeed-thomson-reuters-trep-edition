package com.coherentlogic.coherent.datafeed.redis.integration.client;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_APP_CTX_PATH;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.coherentlogic.coherent.datafeed.client.Client;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MarketPriceServiceSpecification;
import com.reuters.rfa.common.Handle;

/**
 * A client that requests data from Thomson Reuters and publishes this data to
 * an instance of Redis.
 *
 * @todo Figure out a better name for this class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AdvancedClient extends Client {

    static final String ADVANCED_CLIENT = "advancedClient",
        MARKET_PRICE_DAO = "marketPriceDAO";

    public static void main (String[] unused) {

        AbstractApplicationContext applicationContext =
            new ClassPathXmlApplicationContext (DEFAULT_APP_CTX_PATH);

        AdvancedClient advancedClient = new AdvancedClient();

        advancedClient.setApplicationContext(applicationContext);

        advancedClient.start();

        AuthenticationServiceSpecification authenticationService =
            advancedClient.getAuthenticationService();

        authenticationService.login("CoherentLogic_Fuller");

        Handle handle = authenticationService.getHandle();

        MarketPriceServiceSpecification marketPriceService =
            advancedClient.getMarketPriceService();

        marketPriceService.query(
            Constants.dIDN_RDF,
            handle,
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

//        while (true) {
//            MarketPrice nextUpdate = marketPriceService.getNextUpdate();
//
//            System.out.println("serializedMarketPrice.tradeTime: " +
//            nextUpdate.getTradeTimeMillis());

//            Long result = (Long) marketPriceDAO.save(nextUpdate);
//
//            if ((result % 1000) == 0)
//                System.out.println("serializedMarketPrice.uniqueId: " +
//                    result);
//        }
    }
}
