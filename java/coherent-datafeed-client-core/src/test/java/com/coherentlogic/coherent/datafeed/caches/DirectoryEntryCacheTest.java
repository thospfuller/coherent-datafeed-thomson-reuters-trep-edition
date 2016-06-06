package com.coherentlogic.coherent.datafeed.caches;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidApplicationSessionException;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;

/**
 * Unit test for the {@link DirectoryEntryCache} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DirectoryEntryCacheTest {

    private static final String NAME = "name";

    private Map<Handle, Map<String, DirectoryEntry>> directoryEntryMap = null;

    private Map<String, DirectoryEntry> nameToDirectoryMap = null;

    private DirectoryEntryCache directoryEntryCache = null;

    @Before
    public void setUp() throws Exception {

        directoryEntryMap = mock (Map.class);

        nameToDirectoryMap = new HashMap<String, DirectoryEntry> ();

        directoryEntryCache = new DirectoryEntryCache (directoryEntryMap);
    }

    @After
    public void tearDown() throws Exception {
        directoryEntryCache = null;
        nameToDirectoryMap = null;
        directoryEntryMap = null;
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void putDirectoryWithNullHandle () {

        DirectoryEntry directoryEntry = new DirectoryEntry();

        directoryEntryCache.putDirectory(null, directoryEntry);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void putDirectoryWithNullDirectoryServiceEntry () {

        Handle handle = mock (Handle.class);

        directoryEntryCache.putDirectory(handle, null);
    }

    @Test(expected=InvalidApplicationSessionException.class)
    public void putDirectoryWhereHandleReturnsANullMap () {

        Handle handle = mock (Handle.class);

        DirectoryEntry directoryEntry = new DirectoryEntry();

        directoryEntry.setName(NAME);

        directoryEntryCache.putDirectory(handle, directoryEntry);
    }

    @Test
    public void putDirectory () {

        Handle handle = mock (Handle.class);

        DirectoryEntry directoryEntry = new DirectoryEntry();

        when (directoryEntryCache.get(handle)).thenReturn(nameToDirectoryMap);

        directoryEntry.setName(NAME);

        directoryEntryCache.putDirectory(handle, directoryEntry);

        assertEquals(1, nameToDirectoryMap.size());
    }

    /**
     * @see Session#getAllDirectoryNames(Map, Set)
     */
    @Test(expected=NullPointerRuntimeException.class)
    public void getAllDirectoryNamesWithNullDirectoryEntryMap () {

        Set<String> results = new HashSet<String> ();

        directoryEntryCache.getAllDirectoryNames(null, results);
    }

    /**
     * @see Session#getAllDirectoryNames(Map, Set)
     */
    @Test
    public void getAllDirectoryNamesWithEmptyDirectoryEntryMap () {

        Map<String, DirectoryEntry> directoryEntryMap = new HashMap<String, DirectoryEntry> ();

        Set<String> results = new HashSet<String> ();

        directoryEntryCache.getAllDirectoryNames(directoryEntryMap, results);

        assertEquals(0, results.size());
    }

    /**
     * @see Session#getAllDirectoryNames(Map, Set)
     */
    @Test
    public void getAllDirectoryNamesWithValidDirectoryEntryMap () {

        Map<String, DirectoryEntry> directoryEntryMap = new HashMap<String, DirectoryEntry> ();

        DirectoryEntry directoryEntry = new DirectoryEntry();

        directoryEntryMap.put(NAME, directoryEntry);

        Set<String> results = new HashSet<String> ();

        directoryEntryCache.getAllDirectoryNames(directoryEntryMap, results);

        assertEquals(1, results.size());
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void getDirectoryServiceEntryCache () {
        directoryEntryCache.getDirectoryServiceEntryCache(null);
    }

    @Test
    public void getDirectoryServiceEntryCacheWithEmptyDEC () {

        Handle handle = mock (Handle.class);

        when (directoryEntryCache.get(any (Handle.class))).thenReturn(null);

        Map<String, DirectoryEntry> results = directoryEntryCache.getDirectoryServiceEntryCache(handle);

        assertNotNull (results);
        assertEquals (0, results.size());
    }
}
