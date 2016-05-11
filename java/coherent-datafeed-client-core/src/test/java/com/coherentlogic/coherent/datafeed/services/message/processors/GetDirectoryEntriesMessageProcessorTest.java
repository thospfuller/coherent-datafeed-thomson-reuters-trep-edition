package com.coherentlogic.coherent.datafeed.services.message.processors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.domain.DirectoryEntries;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.reuters.rfa.common.Handle;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetDirectoryEntriesMessageProcessorTest {

    private GetDirectoryEntriesMessageProcessor processor = null;

    private Map<Handle, Map<Handle, DirectoryEntry>> directoryEntryCache;

    private Handle handleA = null, handleB = null, handleC = null;

    private DirectoryEntry directoryEntryA  = null, directoryEntryB = null,
        directoryEntryC = null;

    @Before
    public void setUp() throws Exception {

        handleA = mock (Handle.class);
        handleB = mock (Handle.class);
        handleC = mock (Handle.class);

        directoryEntryA  = new DirectoryEntry ();
        directoryEntryB = new DirectoryEntry ();
        directoryEntryC = new DirectoryEntry ();

        directoryEntryCache =
            new HashMap<Handle, Map<Handle, DirectoryEntry>> ();

        processor = new GetDirectoryEntriesMessageProcessor (directoryEntryCache);
    }

    @After
    public void tearDown() throws Exception {
        processor = null;
        handleA = null;
        handleB = null;
        handleC = null;
        directoryEntryA  = null;
        directoryEntryB = null;
        directoryEntryC = null;
    }

    /**
     * This test should add the single source to the target.
     */
    @Test
    public void testAddAll() {

        Set<Entry<Handle, DirectoryEntry>> source =
            new HashSet<Entry<Handle, DirectoryEntry>> ();

        Entry<Handle, DirectoryEntry> entry = mock (Entry.class);

        when (entry.getKey()).thenReturn(handleA);
        when (entry.getValue()).thenReturn(directoryEntryA);

        source.add(entry);

        Set<DirectoryEntry> target =
            new HashSet<DirectoryEntry> ();

        processor.addAll(source, target);

        assertEquals (1, target.size());
    }

    /**
     * This test should add the single source to the target.
     */
    @Test
    public void testAddAllWithAnEmptySource() {

        Set<Entry<Handle, DirectoryEntry>> source =
            new HashSet<Entry<Handle, DirectoryEntry>> ();

        Set<DirectoryEntry> target =
            new HashSet<DirectoryEntry> ();

        processor.addAll(source, target);

        assertEquals (0, target.size());
    }

    @Test
    public void getDirectoryEntries () {

        Map<Handle, DirectoryEntry> directoryEntryMap =
            new HashMap<Handle, DirectoryEntry> ();

        directoryEntryMap.put(handleB, directoryEntryB);
        directoryEntryMap.put(handleC, directoryEntryC);

        directoryEntryCache.put(handleA, directoryEntryMap);

        DirectoryEntries directoryEntries = processor.getDirectoryEntries ();

        assertNotNull (directoryEntries);

        Set<DirectoryEntry> directoryEntrySet =
            directoryEntries.getDirectoryEntries();

        assertEquals (2, directoryEntrySet.size());
    }
}
