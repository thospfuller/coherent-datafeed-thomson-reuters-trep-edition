package com.coherentlogic.coherent.datafeed.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.adapters.DirectoryEntryAdapter;
import com.coherentlogic.coherent.datafeed.caches.DirectoryEntryCache;
import com.coherentlogic.coherent.datafeed.domain.ActionType;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidDataTypeException;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMap;
import com.reuters.rfa.omm.OMMMapEntry;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMTypes;

/**
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DirectoryServiceLoaderTest {

    private static final String
        NAME_A = "directoryEntryA",
        NAME_B = "directoryEntryB";

    private DirectoryServiceLoader directoryServiceLoader = null;
    private DirectoryEntryAdapter serviceEntryAdapter = null;

    private List<DirectoryEntry> directoryEntryList = null;

    private DirectoryEntry directoryEntryA = null;
    private DirectoryEntry directoryEntryB = null;

    private Map<Handle, Map<String, DirectoryEntry>> directoryEntryMap = null;

    private Map<String, DirectoryEntry> directoryServiceEntryMap = null;

    private DirectoryEntryCache directoryEntryCache = null;

    @Before
    public void setUp() throws Exception {

        directoryEntryMap = new HashMap<Handle, Map<String, DirectoryEntry>> ();// mock (Map.class);

        directoryEntryA = new DirectoryEntry();
        directoryEntryB = new DirectoryEntry();
        serviceEntryAdapter = mock (DirectoryEntryAdapter.class);

        directoryEntryList = new ArrayList<DirectoryEntry> (5);

        directoryServiceEntryMap = new HashMap<String, DirectoryEntry> ();

        directoryEntryCache = new DirectoryEntryCache (directoryEntryMap);

        directoryServiceLoader = new DirectoryServiceLoader (
            directoryEntryCache,
            serviceEntryAdapter
        );
    }

    @After
    public void tearDown() throws Exception {
        directoryEntryA = null;
        directoryEntryB = null;
        serviceEntryAdapter = null;

        directoryEntryMap = null;

        directoryServiceLoader = null;
        directoryEntryList = null;
        directoryServiceEntryMap = null;
        directoryEntryCache = null;
    }

    @Test(expected=InvalidDataTypeException.class)
    public void getServiceMapWithWrongType () {

        OMMMsg msg = mock (OMMMsg.class);

        when (msg.getDataType()).thenReturn(OMMTypes.ARRAY);

        directoryServiceLoader.getServiceMap(msg);
    }

    @Test
    public void getServiceMap () {

        OMMMsg msg = mock (OMMMsg.class);
        OMMMap map = mock (OMMMap.class);

        when (msg.getDataType()).thenReturn(OMMTypes.MAP);
        when (msg.getPayload()).thenReturn(map);

        OMMMap result = directoryServiceLoader.getServiceMap(msg);

        assertNotNull (result);
        assertEquals (map, result);
    }

    private void loadDirectoryEntryList () {
        directoryEntryList.add(directoryEntryA);
        directoryEntryList.add(directoryEntryB);

        directoryEntryA.setActionType(ActionType.ADD);
        directoryEntryB.setActionType(ActionType.ADD);

        directoryEntryA.setName(NAME_A);
        directoryEntryB.setName(NAME_B);
    }

    @Test
    public void testExecuteActionOnWithTwoParams() {

        loadDirectoryEntryList ();

        Map<String, DirectoryEntry> directoryServiceEntryCache = new HashMap<String, DirectoryEntry> (2);

        directoryServiceLoader.executeActionOn(
            directoryEntryList,
            directoryServiceEntryCache
        );

        int size = directoryServiceEntryCache.size();

        assertEquals(2, size);
    }

    @Test
    public void testExecuteActionOnWithFourParams() {

        loadDirectoryEntryList ();

        OMMMsg msg = mock (OMMMsg.class);
        OMMMap serviceMap = mock (OMMMap.class);
        Handle handle = mock (Handle.class);

        Iterator<OMMMapEntry> iterator = mock (Iterator.class);
        OMMMapEntry mapEntry = mock (OMMMapEntry.class);

        when (serviceMap.iterator()).thenReturn(iterator);
        stub (iterator.hasNext()).toReturn(true).toReturn(false);

        when (iterator.next()).thenReturn(mapEntry);
        when (mapEntry.getDataType()).thenReturn(OMMTypes.FILTER_LIST);

        when (
            serviceEntryAdapter.adapt(
                any (
                    OMMMsg.class
                )
            )
        ).thenReturn(
            directoryEntryList
        );

        directoryEntryCache.put (handle, directoryServiceEntryMap);

        directoryServiceLoader.executeActionOn(msg, serviceMap, handle);

        Map<String, DirectoryEntry> result = directoryEntryCache.getDirectoryServiceEntryCache(handle);

        assertNotNull (result);
        assertEquals (2, result.size());
    }
}
