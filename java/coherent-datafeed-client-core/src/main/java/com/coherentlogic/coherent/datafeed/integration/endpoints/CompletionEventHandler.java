package com.coherentlogic.coherent.datafeed.integration.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.reuters.rfa.common.Event;

/**
 * Completion events are sent when no other events will be received for a
 * particular interest.
 *
 * @todo When we receive this event, should we remove the entry in the
 *  sessionCache for the given handle?
 *  
 *  TBD: Can the handle be the login handle, for example?
 *       Can the handle be for market prices?
 *       Can the handle be for both? In this case we need to process both
 *       differently.
 *
 * @todo Should the sessionCache ever *not* contain the handle? My current POV is that
 *  it should always have the handle.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class CompletionEventHandler {

    private static final Logger log = LoggerFactory.getLogger(CompletionEventHandler.class);

    /**
     * Note that if this method is renamed, the ProGuard configuration will need
     * to change as well.
     *
     * @param message
     */
    public void onCompletionEventReceived (Message<Event> message) {

        Event event = message.getPayload();

        // TODO: Remove the entry from the data stream cache.
        log.warn("A completionEvent has been received but will not be handled at this time; event: " + event);
    }
}
