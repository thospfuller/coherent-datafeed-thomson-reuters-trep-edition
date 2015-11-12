package com.coherentlogic.coherent.datafeed.misc;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.exceptions.SessionNotFoundException;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Event;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

public class SessionUtils {

    private static final Logger log =
        LoggerFactory.getLogger(SessionUtils.class);

    /**
     * @todo Consider moving this into a helper class however note that we can't
     *  move this to the misc module without including a circular reference to
     *  the core package.
     */
    public static void assertNotNull (
        com.coherentlogic.coherent.datafeed.services.Session session) {
        if (session == null)
            throw new SessionNotFoundException("The session was not found "
                + "in the message headers.");
    }

    /**
     * @todo Unit test this class.
     */
    public static Session getSession (Message<OMMItemEvent> itemEventMessage) {

        MessageHeaders headers = itemEventMessage.getHeaders();

        Session session = (Session) headers.get(SESSION);

        Utils.assertNotNull ("session", session);

        return session;
    }

    /**
     * @return The cache associated with the given handle.
     */
    public static Session getSession (
        Handle handle,
        Cache<Handle, Session> sessionCache
    ) {
        Utils.assertNotNull("handle", handle);

        Session session = sessionCache.get(handle);

        assertNotNull (session);

        log.info ("handle: " + handle + ", session: " + session);

        return session;
    }

    /**
     * Method gets the handle from the event and then returns the session
     * associated with that handle.
     */
    public static Session getSession (
        Event event,
        Cache<Handle, Session> sessionCache
    ) {
        Utils.assertNotNull("event", event);

        Handle handle = event.getHandle();

        return getSession(handle, sessionCache); 
    }

    /**
     * Method gets the event from the message payload and then calls the
     * {@link #getSession(Event)}.
     */
    public static Session getSession (
        Message<Event> message,
        Cache<Handle, Session> sessionCache
    ) {

        Utils.assertNotNull("message", message);

        Event event = message.getPayload();

        log.info("event: " + event);

        return getSession (event, sessionCache);
    }
}
