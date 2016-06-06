package com.coherentlogic.coherent.datafeed.caches;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.reuters.rfa.common.Handle;

public class DictionaryEntryCacheTest {

    private static final String
        NAME = "name",
        DSE1 = "dse1",
        FOO = "foo",
        BAR = "bar";

    private Map<Handle, DictionaryEntry> dictionaryEntryMap = null;

    private Map<String, DirectoryEntry> nameToDirectoryMap = null;

    private DictionaryEntryCache dictionaryEntryCache = null;

    @Before
    public void setUp() throws Exception {

        dictionaryEntryMap = new HashMap<Handle, DictionaryEntry> ();

        nameToDirectoryMap = new HashMap<String, DirectoryEntry> ();

        dictionaryEntryCache = new DictionaryEntryCache (dictionaryEntryMap);
    }

    @After
    public void tearDown() throws Exception {

    	dictionaryEntryMap = null;

        nameToDirectoryMap = null;

        dictionaryEntryCache = null;
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void putDictionaryWithNullHanlde () {

        Handle handle = null;

        DictionaryEntry dictionaryEntry =
            new DictionaryEntry (null, null);

        dictionaryEntryCache.putDictionary(handle, dictionaryEntry);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void putDictionaryWithNullDictionaryServiceEntry () {

        Handle handle = mock (Handle.class);

        DictionaryEntry dictionaryEntry = null;

        dictionaryEntryCache.putDictionary(handle, dictionaryEntry);
    }

    @Test
    public void putDictionary () {

        Handle handle = mock (Handle.class);

        DictionaryEntry dictionaryEntry =
            new DictionaryEntry (null, null);

        dictionaryEntryCache.putDictionary(handle, dictionaryEntry);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void findDictionaryServiceEntryWithNullHandle () {

        Handle handle = null;

        dictionaryEntryCache.findDictionaryServiceEntry(handle);
    }

    @Test
    public void findDictionaryServiceEntry () {

        DictionaryEntry dictionaryEntry = new DictionaryEntry (null, null);

        Handle handle = mock (Handle.class);

        dictionaryEntryCache.put(handle, dictionaryEntry);

        DictionaryEntry result = dictionaryEntryCache.findDictionaryServiceEntry(handle);

        assertNotNull(result);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void putDictionaryWithNullHandle () {

        DictionaryEntry dictionaryEntry = new DictionaryEntry (DSE1, DSE1);

        dictionaryEntryCache.putDictionary(null, dictionaryEntry);
    }

    @Test
    public void allDictionariesAreLoadedEmptyDictionaries () {

        boolean result = dictionaryEntryCache.allDictionariesAreLoaded();

        assertTrue (result);
    }

    boolean allDictionariesAreLoadedOneDictionary (DictionaryEntry dictionaryEntry) {

    	Handle handle = mock (Handle.class);

        dictionaryEntryCache.putDictionary(handle, dictionaryEntry);

        boolean result = dictionaryEntryCache.allDictionariesAreLoaded();

        return result;
    }

    @Test
    public void allDictionariesAreLoadedOneDictionaryExpectingFalse () {

        DictionaryEntry dictionaryEntry = new DictionaryEntry (DSE1, DSE1);

        boolean result = allDictionariesAreLoadedOneDictionary (dictionaryEntry);

        assertFalse (result);
    }

    @Test
    public void allDictionariesAreLoadedOneDictionaryExpectingTrue () {

        DictionaryEntry dictionaryEntry = new DictionaryEntry (DSE1, DSE1);

        dictionaryEntry.setLoaded(true);

        boolean result = allDictionariesAreLoadedOneDictionary (dictionaryEntry);

        assertTrue (result);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void findDictionaryServiceEntryWithNullDictionaryName () {
        dictionaryEntryCache.findDictionaryServiceEntry((String) null);
    }

    @Test
    public void findDictionaryServiceEntryWithNonExistingDictionaryName () {

        DictionaryEntry result = dictionaryEntryCache.findDictionaryServiceEntry("this doesn't exist");

        assertNull(result);
    }

    @Test
    public void findDictionaryServiceEntryWithExistingDictionaryName () {

        Handle handle = mock (Handle.class);

        DictionaryEntry dictionaryEntry = new DictionaryEntry(FOO, BAR);

        dictionaryEntryCache.putDictionary(handle, dictionaryEntry);

        DictionaryEntry result = dictionaryEntryCache.findDictionaryServiceEntry(BAR);

        assertNotNull(result);
    }
}
