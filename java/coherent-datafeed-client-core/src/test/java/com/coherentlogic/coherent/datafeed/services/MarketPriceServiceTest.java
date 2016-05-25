package com.coherentlogic.coherent.datafeed.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.infinispan.Cache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.exceptions.MissingDataException;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.coherentlogic.coherent.datafeed.factories.MarketPriceFactory;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.EventQueue;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.common.InterestSpec;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMPool;
import com.reuters.rfa.session.omm.OMMConsumer;

/**
 * Unit test for the {@link MarketPriceService} class.
 *
 * @todo This suite of tests is not complete so we need to add tests.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketPriceServiceTest {

    private static final String RIC = "FOO.BAR";

    private OMMConsumer ommConsumer = null;

    private OMMPool pool = null;

    private MarketPriceService marketPriceService = null;

    private Handle handle = null;

    private Cache<Handle, String> ricCache;

    private Cache<String, MarketPrice> marketPriceCache;

    @Before
    public void setUp() throws Exception {

        ommConsumer = mock (OMMConsumer.class);

        EventQueue eventQueue = mock (EventQueue.class);

        pool = mock (OMMPool.class);

        OMMEncoder encoder = mock (OMMEncoder.class);

        RequestMessageBuilderFactory requestMessageBuilderFactory = 
            new RequestMessageBuilderFactory (
                ommConsumer, eventQueue, pool, encoder);

        Client client = mock (Client.class);

        ricCache = mock (Cache.class);
        marketPriceCache = mock (Cache.class);

        marketPriceService = new MarketPriceService (
            requestMessageBuilderFactory,
            client,
            ricCache,
            marketPriceCache
        );

        handle = mock (Handle.class);
    }

    @After
    public void tearDown() throws Exception {
        ommConsumer = null;
        pool = null;
        marketPriceService = null;
        handle = null;
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void queryPassingNullString() {
        marketPriceService.query(handle, (String)null);
    }

    /**
     * Note the handle returned will be null and hence the method should throw
     * an exception.
     */
    @Test(expected=NullPointerRuntimeException.class)
    public void queryPassingValidString() {
        marketPriceService.query(handle, RIC);
    }

    /**
     * Note the handle returned will be null and hence the method should throw
     * an exception.
     */
    @Test
    public void queryPassingValidStringShouldPass() {

        OMMMsg ommMsg = mock (OMMMsg.class);
        when(pool.acquireMsg()).thenReturn(ommMsg);

        OMMAttribInfo attribInfo = mock (OMMAttribInfo.class);
        when (pool.acquireAttribInfo()).thenReturn(attribInfo);

        when(
            ommConsumer.registerClient(
                any(EventQueue.class),
                any(InterestSpec.class),
                any(Client.class),
                any(Object.class)
            )
        ).thenReturn(handle);

        List<Handle> handles = marketPriceService.query(
            Constants.dELEKTRON_DD,
            handle,
            RIC
        );

        assertNotNull(handles);
        assertEquals(1, handles.size());
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void queryPassingNullStringArray() {
        Handle handle = mock (Handle.class);
        marketPriceService.query(
            Constants.dELEKTRON_DD,
            handle,
            (String[])null
        );
    }

    @Test(expected=MissingDataException.class)
    public void queryPassingEmptyStringArray() {
        Handle handle = mock (Handle.class);
        marketPriceService.query(
            Constants.dELEKTRON_DD,
            handle,
            new String[] {}
        );
    }
}
