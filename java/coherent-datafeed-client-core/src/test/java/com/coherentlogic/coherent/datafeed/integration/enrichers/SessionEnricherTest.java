package com.coherentlogic.coherent.datafeed.integration.enrichers;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.reuters.rfa.common.Event;

/**
 * Unit test for the {@link SessionEnricher} class.
 *
 * @todo Can we ever have a null dacsId?
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class SessionEnricherTest {

    private Message<Event> message = null;

    private SessionEnricher sessionEnricher = null;

    @Before
    public void setUp() throws Exception {
        message = (Message<Event>) mock (Message.class);
        sessionEnricher = new SessionEnricher();
    }

    @After
    public void tearDown() throws Exception {
        message = null;
        sessionEnricher = null;
    }

    @Test
    public void testAddSessionToMessageHeaders() {

        Event event = mock (Event.class);

        when (event.getClosure()).thenReturn(new SessionBean ());

        when (message.getPayload()).thenReturn(event);

        Message<Event> result = sessionEnricher.enrich(message);

        assertNotNull ("result", result);

        assertNotNull ("session (bean) header.", result.getHeaders().get(SESSION));
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void testAddNullSessionToMessageHeaders() {

        Event event = mock (Event.class);

        when (event.getClosure()).thenReturn(null);

        when (message.getPayload()).thenReturn(event);

        Message<Event> result = sessionEnricher.enrich(message);
    }
}
