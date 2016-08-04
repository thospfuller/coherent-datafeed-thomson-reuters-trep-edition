package com.coherentlogic.coherent.datafeed.proxies;

import static com.coherentlogic.coherent.datafeed.misc.Constants.
    DEFAULT_INT_RETURN_VALUE;

import com.coherentlogic.coherent.datafeed.exceptions.
    EventQueueDispatchException;
import com.reuters.rfa.common.DeactivatedException;
import com.reuters.rfa.common.DispatchQueueInGroupException;
import com.reuters.rfa.common.DispatchableNotificationClient;
import com.reuters.rfa.common.EventQueue;

/**
 * Proxy class for the {@link com.reuters.rfa.common.EventQueue EventQueue} that
 * converts all checked exceptions into runtime exceptions.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class EventQueueProxy {

    private static final String GENERIC_EXCEPTION_TEXT =
        "An exception was thrown from the call to the dispatch method.";

    private final EventQueue eventQueue;

    private final DispatchableNotificationClient dispatchableNotificationClient;

    private final Object closure;

    public EventQueueProxy(EventQueue eventQueue) {
        this (eventQueue, null);
    }

    public EventQueueProxy(
        EventQueue eventQueue,
        DispatchableNotificationClient dispatchableNotificationClient
    ) {
        this (eventQueue, dispatchableNotificationClient, null);
    }

    public EventQueueProxy(
        EventQueue eventQueue,
        DispatchableNotificationClient dispatchableNotificationClient,
        Object closure
    ) {
        this.eventQueue = eventQueue;
        this.dispatchableNotificationClient = dispatchableNotificationClient;
        this.closure = closure;

        if (dispatchableNotificationClient != null)
            eventQueue.registerNotificationClient(dispatchableNotificationClient, closure);
    }

    public void stop () {
        eventQueue.unregisterNotificationClient(dispatchableNotificationClient);
    }

    public EventQueue getEventQueue() {
        return eventQueue;
    }

    public DispatchableNotificationClient getDispatchableNotificationClient() {
        return dispatchableNotificationClient;
    }

    /**
     * Method delegates calls to the EventQueue's dispatch method and converts
     * any checked exceptions into runtime exceptions.
     *
     * @param waitMillisecs how long (in milliseconds) to wait for an event, or
     *  Dispatchable.INFINITE_WAIT or Dispatchable.NO_WAIT. 
     * @return The number of events left on the queue after or -1 if the timeout
     *  expired and there were no events to be dispatched. 
     *
     * @see com.reuters.rfa.common#EventQueue
     */
    public int dispatch(long waitMillisecs) {

        int result = DEFAULT_INT_RETURN_VALUE;

        try {
            result = eventQueue.dispatch(waitMillisecs);
        } catch (DeactivatedException deactivatedException) {
            throw new EventQueueDispatchException(
                GENERIC_EXCEPTION_TEXT, deactivatedException);
        } catch (DispatchQueueInGroupException dispatchQueueInGroupException) {
            throw new EventQueueDispatchException(
                GENERIC_EXCEPTION_TEXT, dispatchQueueInGroupException);
        }
        return result;
    }
}
