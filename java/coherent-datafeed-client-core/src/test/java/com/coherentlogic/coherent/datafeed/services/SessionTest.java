package com.coherentlogic.coherent.datafeed.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import com.coherentlogic.coherent.datafeed.exceptions.InvalidApplicationSessionException;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.reuters.rfa.common.Handle;

/**
 * Unit test for the {@link Session} class.
 *
 * @deprecated
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class SessionTest {

//    private static final String
//        NAME = "name",
//        DSE1 = "dse1",
//        DSE2 = "dse2",
//        FOO = "foo",
//        BAR = "bar";
//
//    private Session session = null;
//
//    private Handle handle = null;
//
//    private Map<Handle, Map<String, DirectoryEntry>>
//        directoryEntryCache = null;
////    private Map<Handle, DictionaryEntry> dictionaryEntryCache = null;
//    private Map<Handle, MarketPrice> marketPriceEntryCache = null;
//    private Map<Handle, MarketMaker> marketMakerEntryCache = null;
//    private Map<Handle, MarketByOrder> marketByOrderEntryCache = null;
//    private Map<Handle, TS1DefEntry> ts1DefEntryMap = null;
//    private Map<Handle, TimeSeriesEntries> timeSeriesEntryMap = null;
//
//    private Map<String, DirectoryEntry> nameToDirectoryMap = null;
//
//    @Before
//    public void setUp() throws Exception {
//
//        directoryEntryCache = mock (Map.class);
//        dictionaryEntryCache = mock (Map.class);
//        ts1DefEntryMap = mock (Map.class);
//        nameToDirectoryMap = new HashMap<String, DirectoryEntry> ();
//        timeSeriesEntryMap = mock (Map.class);
//
//        session = new Session (
//            directoryEntryCache,
//            dictionaryEntryCache,
//            ts1DefEntryMap,
//            timeSeriesEntryMap
//        );
//        handle = mock (Handle.class);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        session = null;
//        directoryEntryCache = null;
//        dictionaryEntryCache = null;
//        marketPriceEntryCache = null;
//        ts1DefEntryMap = null;
//        nameToDirectoryMap = null;
//        handle = null;
//    }

//    @Test(expected=NullPointerRuntimeException.class)
//    public void putDirectoryWithNullHandle () {
//
//        DirectoryEntry directoryEntry =
//            new DirectoryEntry();
//
//        session.putDirectory(null, directoryEntry);
//    }
//
//    @Test(expected=NullPointerRuntimeException.class)
//    public void putDirectoryWithNullDirectoryServiceEntry () {
//
//        Handle handle = mock (Handle.class);
//
//        session.putDirectory(handle, null);
//    }
//
//    @Test(expected=InvalidApplicationSessionException.class)
//    public void putDirectoryWhereHandleReturnsANullMap () {
//
//        Handle handle = mock (Handle.class);
//
//        DirectoryEntry directoryEntry =
//            new DirectoryEntry();
//
//        directoryEntry.setName(NAME);
//
//        session.putDirectory(handle, directoryEntry);
//    }
//
//    @Test
//    public void putDirectory () {
//
//        Handle handle = mock (Handle.class);
//
//        DirectoryEntry directoryEntry =
//            new DirectoryEntry();
//
//        when (
//            directoryEntryCache.get(handle)
//        ).thenReturn(
//            nameToDirectoryMap
//        );
//
//        directoryEntry.setName(NAME);
//
//        session.putDirectory(handle, directoryEntry);
//
//        assertEquals(1, nameToDirectoryMap.size());
//    }
// -------------------------------------------------------------
//    @Test(expected=NullPointerRuntimeException.class)
//    public void putDictionaryWithNullHanlde () {
//
//        Handle handle = null;
//
//        DictionaryEntry dictionaryEntry =
//            new DictionaryEntry (null, null);
//        
//        session.putDictionary(handle, dictionaryEntry);
//    }
//
//    @Test(expected=NullPointerRuntimeException.class)
//    public void putDictionaryWithNullDictionaryServiceEntry () {
//
//        Handle handle = mock (Handle.class);
//
//        DictionaryEntry dictionaryEntry = null;
//
//        session.putDictionary(handle, dictionaryEntry);
//    }
//
//    @Test
//    public void putDictionary () {
//
//        Handle handle = mock (Handle.class);
//
//        DictionaryEntry dictionaryEntry =
//            new DictionaryEntry (null, null);
//
//        session.putDictionary(handle, dictionaryEntry);
//    }
//
//    @Test(expected=NullPointerRuntimeException.class)
//    public void findDictionaryServiceEntryWithNullHandle () {
//
//        Handle handle = null;
//
//        session.findDictionaryServiceEntry(handle);
//    }
//
//    @Test
//    public void findDictionaryServiceEntry () {
//
//        DictionaryEntry dictionaryEntry =
//            new DictionaryEntry (null, null);
//
//        Handle handle = mock (Handle.class);
//
//        when (
//            dictionaryEntryCache.get(handle)
//        ).thenReturn(
//            dictionaryEntry
//        );
//
//        DictionaryEntry result =
//            session.findDictionaryServiceEntry(handle);
//
//        assertNotNull(result);
//    }
//
//    /**
//     * @see Session#getAllDirectoryNames(Map, Set)
//     */
//    @Test(expected=NullPointerRuntimeException.class)
//    public void getAllDirectoryNamesWithNullDirectoryEntryMap () {
//
//        Set<String> results = new HashSet<String> ();
//
//        session.getAllDirectoryNames(null, results);
//    }
//
//    /**
//     * @see Session#getAllDirectoryNames(Map, Set)
//     */
//    @Test
//    public void getAllDirectoryNamesWithEmptyDirectoryEntryMap () {
//
//        Map<String, DirectoryEntry> directoryEntryMap =
//            new HashMap<String, DirectoryEntry> ();
//
//        Set<String> results = new HashSet<String> ();
//
//        session.getAllDirectoryNames(directoryEntryMap, results);
//
//        assertEquals(0, results.size());
//    }
//
//    /**
//     * @see Session#getAllDirectoryNames(Map, Set)
//     */
//    @Test
//    public void getAllDirectoryNamesWithValidDirectoryEntryMap () {
//
//        Map<String, DirectoryEntry> directoryEntryMap =
//            new HashMap<String, DirectoryEntry> ();
//
//        DirectoryEntry directoryEntry =
//            new DirectoryEntry();
//
//        directoryEntryMap.put(NAME, directoryEntry);
//
//        Set<String> results = new HashSet<String> ();
//
//        session.getAllDirectoryNames(directoryEntryMap, results);
//
//        assertEquals(1, results.size());
//    }
//
//    @Test(expected=NullPointerRuntimeException.class)
//    public void getDirectoryServiceEntryCache () {
//        session.getDirectoryServiceEntryCache(null);
//    }
//
//    @Test
//    public void getDirectoryServiceEntryCacheWithEmptyDEC () {
//
//        when (directoryEntryCache.get(any (Handle.class))).thenReturn(null);
//
//        Map<String, DirectoryEntry> results =
//            session.getDirectoryServiceEntryCache(handle);
//
//        assertNotNull (results);
//        assertEquals (0, results.size());
//    }
//
//    @Test(expected=NullPointerRuntimeException.class)
//    public void putDictionaryWithNullHandle () {
//
//        DictionaryEntry dictionaryEntry =
//            new DictionaryEntry (DSE1, DSE1);
//
//        session.putDictionary(null, dictionaryEntry);
//    }
//
//    @Test
//    public void allDictionariesAreLoadedEmptyDictionaries () {
//        boolean result = session.allDictionariesAreLoaded();
//
//        assertTrue (result);
//    }
//
//    boolean allDictionariesAreLoadedOneDictionary (
//        DictionaryEntry dictionaryEntry) {
//        // The dictionaryEntryCache is configured as a mock object so in this
//        // test we need an actual instance of a Map.
//        dictionaryEntryCache = new HashMap<Handle, DictionaryEntry> ();
//
//        session = new Session (
//            directoryEntryCache,
//            dictionaryEntryCache,
//            ts1DefEntryMap,
//            timeSeriesEntryMap
//        );
//
//        session.putDictionary(handle, dictionaryEntry);
//
//        boolean result = session.allDictionariesAreLoaded();
//
//        return result;
//    }

//    @Test
//    public void allDictionariesAreLoadedOneDictionaryExpectingFalse () {
//
//        DictionaryEntry dictionaryEntry =
//            new DictionaryEntry (DSE1, DSE1);
//
//        boolean result = allDictionariesAreLoadedOneDictionary (
//            dictionaryEntry);
//
//        assertFalse (result);
//    }
//
//    @Test
//    public void allDictionariesAreLoadedOneDictionaryExpectingTrue () {
//
//        DictionaryEntry dictionaryEntry =
//            new DictionaryEntry (DSE1, DSE1);
//
//        dictionaryEntry.setLoaded(true);
//
//        boolean result = allDictionariesAreLoadedOneDictionary (
//            dictionaryEntry);
//
//        assertTrue (result);
//    }
//
//    @Test(expected=NullPointerRuntimeException.class)
//    public void findDictionaryServiceEntryWithNullDictionaryName () {
//        session.findDictionaryServiceEntry((String) null);
//    }
//
//    @Test
//    public void findDictionaryServiceEntryWithNonExistingDictionaryName () {
//        DictionaryEntry result =
//            session.findDictionaryServiceEntry("this doesn't exist");
//
//        assertNull(result);
//    }
//
//    @Test
//    public void findDictionaryServiceEntryWithExistingDictionaryName () {
//
//        Map<Handle, Map<String, DirectoryEntry>> directoryEntryCache =
//            mock (Map.class);
//        Map<Handle, DictionaryEntry> dictionaryEntryCache =
//            new HashMap<Handle, DictionaryEntry> ();
//        Map<Handle, MarketPrice> marketPriceEntryCache = mock (Map.class);
//        Map<Handle, TS1DefEntry> ts1DefEntryMap = mock (Map.class);
//        Map<Handle, TimeSeriesEntries> timeSeriesEntryMap = mock (Map.class);
//
//        nameToDirectoryMap = new HashMap<String, DirectoryEntry> ();
//        timeSeriesEntryMap = mock (Map.class);
//
//        Session session = new Session (
//            directoryEntryCache,
//            dictionaryEntryCache,
//            ts1DefEntryMap,
//            timeSeriesEntryMap
//        );
//
//        Handle handle = mock (Handle.class);
//
//        DictionaryEntry dictionaryEntry = new DictionaryEntry(FOO, BAR);
//
//        session.putDictionary(handle, dictionaryEntry);
//
//        DictionaryEntry result =
//            session.findDictionaryServiceEntry(BAR);
//
//        assertNotNull(result);
//    }
}
