package com.coherentlogic.coherent.datafeed.client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.DictionaryServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MarketPriceServiceSpecification;
import com.reuters.rfa.common.Handle;

/**
 * Integration test for the {@link Client} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Ignore
public class ClientTest {

    private static final String DEFAULT_DACS_ID = "defaultDacsId";

    private static final String[] DEFAULT_RICS = {
        "MSFT.O", "GOOG.O"
    };

    private static final int DEFAULT_SIZE = 2;

    private Client client = null;

    private AuthenticationServiceSpecification authenticationService = null;

    private MarketPriceServiceSpecification marketPriceService = null;

    private Handle loginHandle = null;

    @Before
    public void setUp() throws Exception {
        client = new Client ();
        client.start();

        authenticationService = client.getAuthenticationService();

        SessionBean sessionBean = new SessionBean ();

        sessionBean.setDacsId(DEFAULT_DACS_ID);

        Handle loginHandle = authenticationService.login(sessionBean);

        DictionaryServiceSpecification dictionaryService =
            (DictionaryServiceSpecification) client.getDictionaryService();

        dictionaryService.loadDictionaries(
            Constants.IDN_RDF, sessionBean, "RWFFld", "RWFEnum");

        marketPriceService = client.getMarketPriceService();
    }

    @After
    public void tearDown() throws Exception {
        authenticationService.logout();
        client.stop();
        authenticationService = null;
        marketPriceService = null;
        client = null;
        loginHandle = null;
    }

    /**
     * This test only asks for the first two refresh events because if we ask
     * for more updates, then the test won't run properly when the market is
     * closed because updates won't be sent.
     */
    @Test
    public void getMarketPricesForValidRICS() {
        List<String> marketPrices =
            new ArrayList<String> ();

//        Map<String, MarketPrice> result = marketPriceService.query(
//            ServiceName.dELEKTRON_DD,
//            loginHandle,
//            DEFAULT_RICS
//        );

        assertEquals (DEFAULT_SIZE, marketPrices.size());
    }

    /**
     * This test performs a query using an invalid RIC.
     *
     * TODO: This test needs to be re-written since we'll need to inspect the status response in order to see if TR
     * rejected the request.
     *
     * [ARSD.N] statusResponse: StatusResponse [
     *     code=NOT_FOUND, streamState=CLOSED, dataState=SUSPECT, text=The record could not be found]
     *
     * @throws InterruptedException 
     */
    @Test
    public void getMarketPricesForInvalidRICS()
        throws InterruptedException {

//        Map<String, MarketPrice> result = marketPriceService.query(ServiceName.dELEKTRON_DD, loginHandle, "foo.bar");
        // Note that if this test just ends here that RFA will still try to send
        // events to be processed -- in particular status events, since the ric
        // provided here is invalid.
//        Thread.sleep(5000);
    }
}
