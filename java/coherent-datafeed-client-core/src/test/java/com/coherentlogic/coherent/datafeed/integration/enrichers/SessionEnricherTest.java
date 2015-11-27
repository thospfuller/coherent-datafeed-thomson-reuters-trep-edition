package com.coherentlogic.coherent.datafeed.integration.enrichers;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.services.Session;

/**
 * Unit test for the {@link SessionEnricher} class.
 *
 * @todo Can we ever have a null dacsId?
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class SessionEnricherTest {

    private static final String DACS_ID = "FubarCo_jpublic";

    private Message<String> message = null;

    private SessionEnricher sessionEnricher = null;

    @Before
    public void setUp() throws Exception {
        message = (Message<String>) mock (Message.class);
        sessionEnricher = new SessionEnricher(new Session (
            null, null, null, null, null, null));
    }

    @After
    public void tearDown() throws Exception {
        message = null;
        sessionEnricher = null;
    }

    @Test
    public void testAddSessionToMessageHeaders() {

        when (message.getPayload()).thenReturn(DACS_ID);

        Message<Session> result = sessionEnricher.
            addDacsIdToSession(message);

        assertNotNull (result);

        Session payload = result.getPayload();

        assertNotNull (payload);
    }
}
