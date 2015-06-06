package com.coherentlogic.coherent.datafeed.misc;

import static com.coherentlogic.coherent.datafeed.misc.SessionUtils.getSession;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.infinispan.Cache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Event;
import com.reuters.rfa.common.Handle;

public class SessionUtilsTest {

    private Event event = null;

    private Handle handle = null;

    private Cache<Handle, Session> sessionCache = null;

    @Before
    public void setUp() throws Exception {

        event = mock (Event.class);

        handle = mock (Handle.class);

        sessionCache = (Cache<Handle, Session>)
            mock (Cache.class);
    }

    @After
    public void tearDown() throws Exception {
        event = null;
        handle = null;
        sessionCache = null;
    }

    private void configureSessionCacheBehavior () {
        when (
            sessionCache.get(
                any(
                    Handle.class
                )
            )
        ).thenReturn(
            new Session (
                null,
                null,
                null,
                null,
                null
            )
        );
    }

    @Test
    public void testGetSessionHandle() {

        configureSessionCacheBehavior ();

        Session session = getSession(handle, sessionCache);
        assertNotNull (session);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void testGetSessionWithNullHandle() {
        getSession((Handle)null, sessionCache);
    }

    @Test
    public void testGetSessionEvent() {

        configureSessionCacheBehavior ();

        when (
            event.getHandle()
        ).thenReturn(
            handle
        );

        Session session = getSession(event, sessionCache);
        assertNotNull (session);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void testGetSessionWithNullEvent() {
        getSession((Event)null, sessionCache);
    }

    @Test
    public void testGetSessionMessageOfEvent() {

        configureSessionCacheBehavior ();

        when (
            event.getHandle()
        ).thenReturn(
            handle
        );

        MessageBuilder<Event> builder = MessageBuilder.withPayload(event);

        Message<Event> message = builder.build();

        Session session = getSession(message, sessionCache);

        assertNotNull (session);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void testGetSessionWithMessageEvent() {
        getSession((Message<Event>)null, sessionCache);
    }
}
