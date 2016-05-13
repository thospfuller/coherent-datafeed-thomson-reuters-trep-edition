package com.coherentlogic.coherent.datafeed.adapters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.reuters.rfa.omm.OMMMap;
import com.reuters.rfa.omm.OMMMapEntry;

/**
 * Unit test for the {@link DirectoryEntryAdapter} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DirectoryServiceEntryAdapterTest {

    static final String NAME = "foobar";

    private DirectoryEntry entry = null;

    private DirectoryEntryAdapter adapter = null;

    private OMMMap map = null;
    private OMMMapEntry mapEntry = null;

    @Before
    public void setUp() throws Exception {

        entry = new DirectoryEntry ();

        TypedFactory<DirectoryEntry> directoryEntryFactory = mock(TypedFactory.class);

        // Test passes without this but it may be necessary in the future.
        // when(directoryEntryFactory.getInstance()).thenReturn(new DirectoryEntry ());

        adapter = new DirectoryEntryAdapter (directoryEntryFactory);

        map = mock (OMMMap.class);
        mapEntry = mock (OMMMapEntry.class);
    }

    @After
    public void tearDown() throws Exception {
        entry = null;
        adapter = null;
        map = null;
        mapEntry = null;
    }

//    @Test
//    public void testAdaptOMMMsgDirectoryServiceEntry() {
//
//        Iterator<?> iterator = new AbstractIterator () {
//            @Override
//            public Object next() {
//                return mapEntry;
//            }
//        };
//
//        when (map.iterator()).thenReturn(iterator);
//
//        when (mapEntry.getType()).thenReturn(OMMTypes.MAP_ENTRY);
//
//        OMMData data = mock (OMMData.class);
//        when (mapEntry.getKey()).thenReturn(data);
//        when (data.toString()).thenReturn(NAME);
//
//        OMMFilterList filterList = mock (OMMFilterList.class);
//
//        final OMMFilterEntry filterEntry = mock (OMMFilterEntry.class);
//
//        class FilterListIterator extends AbstractIterator {
//            @Override
//            public Object next() {
//                return null;
//            }
//        }
//
//        when (filterList.iterator()).thenReturn(new FilterListIterator ());
//
//        when (mapEntry.getData()).thenReturn(filterList);
//
//        when (mapEntry.getAction()).thenReturn(OMMMapEntry.Action.DELETE);
//
//        adapter.assignStates(map, entry);
//
//        assertEquals (NAME, entry.getName());
//        assertEquals (ActionType.DELETE, entry.getActionType());
//    }
}

abstract class AbstractIterator implements Iterator {

    private int ctr = -1;

    public AbstractIterator () {
        this.ctr = 1;
    }

    public AbstractIterator (int ctr) {
        this.ctr = ctr;
    }

    @Override
    public boolean hasNext() {
        return (0 < ctr--) ? true : false;
    }

    @Override
    public void remove() {
        throw new RuntimeException ("The remove method is not supported.");
        
    }
}
