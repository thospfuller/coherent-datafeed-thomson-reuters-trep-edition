package com.coherentlogic.coherent.datafeed.dispatchers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.exceptions.EventQueueDispatchException;
import com.reuters.rfa.common.DeactivatedException;
import com.reuters.rfa.common.DispatchQueueInGroupException;
import com.reuters.rfa.common.Dispatchable;
import com.reuters.rfa.common.DispatchableNotificationClient;
import com.reuters.rfa.common.EventQueue;

/**
 * 
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class DispatchableNotificationClientImpl implements
    DispatchableNotificationClient {

    private static final Logger log =
        LoggerFactory.getLogger(DispatchableNotificationClientImpl.class);

    private static final String GENERIC_EXCEPTION_MSG
        = "The event queue threw an exception.";

    private final EventQueue eventQueue;

    private final long timeout;

    /**
     * 
     * @param eventQueue
     * @param timeout
     */
    public DispatchableNotificationClientImpl(EventQueue eventQueue,
        long timeout) {
        this.eventQueue = eventQueue;
        this.timeout = timeout;
    }

    /**
     * 
     */
    @Override
    public void notify(Dispatchable dispatchable, Object closure) {
        log.debug("notify: method invoked; eventQueue: " + eventQueue);

        try {
            eventQueue.dispatch(timeout);
        } catch (DeactivatedException deactivatedException) {
            throw new EventQueueDispatchException(
                GENERIC_EXCEPTION_MSG, deactivatedException);
        } catch (DispatchQueueInGroupException dispatchQueueInGroupException) {
            throw new EventQueueDispatchException(
                GENERIC_EXCEPTION_MSG, dispatchQueueInGroupException);
        }
    }
}
