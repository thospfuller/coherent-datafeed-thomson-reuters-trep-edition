package com.coherentlogic.coherent.datafeed.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.adapters.DirectoryEntryAdapter;
import com.coherentlogic.coherent.datafeed.domain.ActionType;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidDataTypeException;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMap;
import com.reuters.rfa.omm.OMMMapEntry;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMTypes;

public class DirectoryServiceLoaderTest {

    private static final String
        NAME_A = "directoryEntryA",
        NAME_B = "directoryEntryB";

    private DirectoryServiceLoader directoryServiceLoader = null;
    private DirectoryEntryAdapter serviceEntryAdapter = null;

    private List<DirectoryEntry> directoryEntryList = null;

    private DirectoryEntry directoryEntryA = null;
    private DirectoryEntry directoryEntryB = null;

    private Map<String, DirectoryEntry> directoryServiceEntryCache = null;

    @Before
    public void setUp() throws Exception {
        directoryEntryA = new DirectoryEntry();
        directoryEntryB = new DirectoryEntry();
        serviceEntryAdapter = mock (DirectoryEntryAdapter.class);
        directoryServiceLoader =
            new DirectoryServiceLoader (serviceEntryAdapter);
        directoryEntryList =
                new ArrayList<DirectoryEntry> (2);
        directoryServiceEntryCache = new HashMap<String, DirectoryEntry> ();
    }

    @After
    public void tearDown() throws Exception {
        directoryEntryA = null;
        directoryEntryB = null;
        serviceEntryAdapter = null;
        directoryServiceLoader = null;
        directoryEntryList = null;
        directoryServiceEntryCache = null;
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

        Map<Handle, Map<String, DirectoryEntry>> directoryEntryCache =
            new HashMap<Handle, Map<String, DirectoryEntry>> ();

        OMMMsg msg = mock (OMMMsg.class);
        OMMMap serviceMap = mock (OMMMap.class);
        Handle handle = mock (Handle.class);

        Session session = new Session (
            directoryEntryCache, null, null, null, null);

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

        directoryServiceLoader.executeActionOn(
            msg, serviceMap, handle, session);

        Map<String, DirectoryEntry> directoryServiceEntryCache =
            session.getDirectoryServiceEntryCache(handle);

        assertNotNull (directoryServiceEntryCache);
        assertEquals (2, directoryServiceEntryCache.size());

        
    }
}
