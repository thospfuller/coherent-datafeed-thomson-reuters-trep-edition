package com.coherentlogic.coherent.datafeed.services.message.processors;

import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Unit test for the {@link AbstractMarketPriceMessageProcessor} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Ignore
public class AbstractMarketPriceMessageProcessorTest {

    private final OMMItemEvent itemEvent = mock (OMMItemEvent.class);

    private AbstractRefreshRFABeanMessageProcessor<MarketPrice> processor = null;

    @Before
    public void setUp() throws Exception {
        // We need an instance of AbstractMarketPriceMessageProcessor so we'll
        // use a class which extends from this.
        processor = new RefreshMarketPriceMessageProcessor(null, null, null);
    }

    @After
    public void tearDown() throws Exception {
        MessageHeaders headers = null;
        processor = null;
    }

//    @Test(expected=NullPointerRuntimeException.class)
//    public void testGetSessionWithNullSession () {
//        Message<OMMItemEvent> message = MessageBuilder
//            .withPayload(itemEvent)
//            .setHeader(SESSION, null)
//            .build();
//
//        processor.getSession(message);
//    }
//
//    @Test
//    public void testGetSessionWithNonNullSession () {
//        Message<OMMItemEvent> message = MessageBuilder
//            .withPayload(itemEvent)
//            .setHeader(SESSION, session)
//            .build();
//
//        Session session = processor.getSession(message);
//
//        assertEquals(this.session,  session);
//    }
}
