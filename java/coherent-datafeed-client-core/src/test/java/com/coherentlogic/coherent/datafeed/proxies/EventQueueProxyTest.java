package com.coherentlogic.coherent.datafeed.proxies;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.exceptions.EventQueueDispatchException;
import com.reuters.rfa.common.DeactivatedException;
import com.reuters.rfa.common.DispatchQueueInGroupException;
import com.reuters.rfa.common.EventQueue;

/**
 * Unit test for the EventQueueProxy class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class EventQueueProxyTest {

    private EventQueueProxy proxy = null;

    private EventQueue queue = null;

    @Before
    public void setUp() throws Exception {
        queue = mock (EventQueue.class);
        proxy = new EventQueueProxy(queue);
    }

    @After
    public void tearDown() throws Exception {
        proxy = null;
        queue = null;
    }

    @Test(expected=EventQueueDispatchException.class)
    public void testDispatchWhereQueueThrowsDeactivatedException()
        throws DeactivatedException, DispatchQueueInGroupException {

        when(
            queue.dispatch(
                anyLong()
            )
        ).thenThrow(new DeactivatedException ());

        proxy.dispatch(1000L);
    }

    @Test(expected=EventQueueDispatchException.class)
    public void testDispatchWhereQueueThrowsDispatchQueueInGroupException()
        throws DeactivatedException, DispatchQueueInGroupException {

        when(
            queue.dispatch(
                anyLong()
            )
        ).thenThrow(new DispatchQueueInGroupException ());

        proxy.dispatch(1000L);
    }

    /* Note that at the moment we don't have a testDispatchNoExceptionThrown
     * test since all the dispatch method does is delegate to the EventQueue's
     * dispatch method so there's not really much point testing this logic.
     */
}
