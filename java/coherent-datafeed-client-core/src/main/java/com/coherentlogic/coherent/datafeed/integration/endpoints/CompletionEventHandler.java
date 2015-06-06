package com.coherentlogic.coherent.datafeed.integration.endpoints;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.beans.CachedEntry;
import com.reuters.rfa.common.Event;
import com.reuters.rfa.common.Handle;

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
@Transactional
public class CompletionEventHandler {

    private static final Logger log =
        LoggerFactory.getLogger(CompletionEventHandler.class);

    private final Cache<Handle, ? extends CachedEntry> sessionCache;

    /**
     * This ctor is here as a solution for a problem that manifested from
     * setting up the db-int module. Once an actual transaction manager was
     * put in place any class annotated as @Transactional will have logic
     * weaved into it by CGLIB -- in order to do this however there needs to be
     * a default ctor (this is a requirement of CGLIB).
     */
    public CompletionEventHandler () {
        this.sessionCache = null;
    }

    public CompletionEventHandler(
        Cache<Handle, ? extends CachedEntry> sessionCache
    ) {
        this.sessionCache = sessionCache;
    }

    /**
     * Note that if this method is renamed, the ProGuard configuration will need
     * to change as well.
     *
     * @param message
     */
    public void onCompletionEventReceived (Message<Event> message) {

        Event event = message.getPayload();

        Handle handle = event.getHandle();

        String text = null;

        if (sessionCache.containsKey(handle)) {
            CachedEntry cachedObject =
                (CachedEntry) sessionCache.remove(handle);

            text = "The sessionCache contained the handle " + handle + ", which " +
                "was removed; this pointed to the following: " + cachedObject;

        } else {
            text = "The sessionCache does not contain the handle " + handle + ".";
        }
        log.info(text);
    }
}
