package com.coherentlogic.coherent.datafeed.services;

import static org.mockito.Mockito.mock;

import java.util.Map;

import org.infinispan.Cache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidDacsIdException;
import com.coherentlogic.coherent.datafeed.exceptions.SessionFinalizationFailedException;
import com.coherentlogic.coherent.datafeed.factories.SessionFactory;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMConsumer;

/**
 * Unit test for the AuthenticationService class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class AuthenticationServiceTest {

    private OMMConsumer ommConsumer = null;

    private AuthenticationService authenticationService = null;

    @Before
    public void setUp() throws Exception {

        Cache<Handle, Session> sessionCache = mock (Cache.class);
        Cache<Handle, DictionaryEntry> dictionaryCache =
            mock (Cache.class);

        Map<Handle, Map<String, DirectoryEntry>> directoryMap = mock(Map.class);
        Map<Handle, DictionaryEntry> dictionaryMap = mock(Map.class);
        Map<Handle, MarketPrice> marketPriceMap = mock(Map.class);
        Map<Handle, MarketMaker> marketMakerMap = mock(Map.class);
        Map<Handle, MarketByOrder> marketByOrderMap = mock(Map.class);
        Map<Handle, TS1DefEntry> ts1DefEntryMap = mock(Map.class);
        Map<Handle, TimeSeriesEntries> timeSeriesEntriesMap = mock(Map.class);

        ommConsumer = mock (OMMConsumer.class);

        SessionFactory sessionFactory = new SessionFactory(
            directoryMap,
            dictionaryMap,
            ts1DefEntryMap,
            timeSeriesEntriesMap
        );

        authenticationService = new AuthenticationService(
            null,
            null,
            ommConsumer,
            null,
            null,
            sessionCache,
            sessionFactory
        );
    }

    @After
    public void tearDown() throws Exception {
        authenticationService = null;
        ommConsumer = null;
    }

    /**
     * Note that login has not been called first, so we expect this to fail.
     */
    @Test(expected=SessionFinalizationFailedException.class)
    public void testLogout() {
        authenticationService.logout();
    }

    @Test(expected=InvalidDacsIdException.class)
    public void loginWithNullDacsId () {
        authenticationService.login(null);
    }

    @Test(expected=InvalidDacsIdException.class)
    public void loginWithBlankDacsId () {
        authenticationService.login("");
    }
}
