package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.support.MessageBuilder;

import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Unit test for the {@link AbstractMarketPriceMessageProcessor} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AbstractMarketPriceMessageProcessorTest {

    private final Session session = new Session (null, null, null, null, null);

    private final OMMItemEvent itemEvent = mock (OMMItemEvent.class);

    private AbstractMarketPriceMessageProcessor processor = null;

    @Before
    public void setUp() throws Exception {
        // We need an instance of AbstractMarketPriceMessageProcessor so we'll
        // use a class which extends from this.
        processor = new RefreshMarketPriceMessageProcessor(null);
    }

    @After
    public void tearDown() throws Exception {
        MessageHeaders headers = null;
        processor = null;
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void testGetSessionWithNullSession () {
        Message<OMMItemEvent> message = MessageBuilder
            .withPayload(itemEvent)
            .setHeader(SESSION, null)
            .build();

        processor.getSession(message);
    }

    @Test
    public void testGetSessionWithNonNullSession () {
        Message<OMMItemEvent> message = MessageBuilder
            .withPayload(itemEvent)
            .setHeader(SESSION, session)
            .build();

        Session session = processor.getSession(message);

        assertEquals(this.session,  session);
    }
    
}
