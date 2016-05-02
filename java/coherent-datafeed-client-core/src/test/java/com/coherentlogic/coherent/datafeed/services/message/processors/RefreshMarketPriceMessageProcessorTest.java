package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.adapters.MarketPriceAdapter;
import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Unit test for the {@link RefreshMarketPriceMessageProcessor} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RefreshMarketPriceMessageProcessorTest {

    private Handle handle = null;

    private OMMItemEvent event = null;

    private OMMMsg ommMsg = null;

    private Session session = null;

    private Map<Handle, MarketPrice> marketPriceEntryCache = null;

    private Map<Handle, MarketMaker> marketMakerEntryCache = null;

    private Map<Handle, MarketByOrder> marketByOrderEntryCache = null;

    private MarketPriceAdapter marketPriceAdapter = null;

    private MarketPrice marketPrice = null;

    private RefreshMarketPriceMessageProcessor processor = null;

    @Before
    public void setUp() throws Exception {

        handle = mock (Handle.class);

        event = mock (OMMItemEvent.class);

        ommMsg = mock (OMMMsg.class);

        when (event.getHandle()).thenReturn(handle);

        Map<Handle, Map<String, DirectoryEntry>> directoryEntryCache =
            mock (Map.class);
        Map<Handle, DictionaryEntry> dictionaryEntryCache =
            mock (Map.class);
        marketPriceEntryCache =
            mock (Map.class);
        marketMakerEntryCache =
            mock (Map.class);
        marketByOrderEntryCache =
            mock (Map.class);
        Map<Handle, TS1DefEntry> ts1DefEntryCache =
            mock (Map.class);
        Map<Handle, TimeSeriesEntries> timeSeriesEntryCache =
            mock (Map.class);

        session = new Session (
            directoryEntryCache,
            dictionaryEntryCache,
            ts1DefEntryCache,
            timeSeriesEntryCache
        );

        marketPriceAdapter = mock (MarketPriceAdapter.class);

        marketPrice = new MarketPrice ();

        processor = new RefreshMarketPriceMessageProcessor (marketPriceAdapter, null, null);
    }

    @After
    public void tearDown() throws Exception {
        handle = null;
        event = null;
        ommMsg = null;
        session = null;
        marketPriceEntryCache = null;
        marketPriceAdapter = null;
        marketPrice = null;
        processor = null;
    }

    private Message<OMMItemEvent> newMessage () {

        Message<OMMItemEvent> result =
            MessageBuilder
                .withPayload(event)
                .setHeader(SESSION, session)
                .build();

        return result;
    }

    private Message<MarketPrice> createMockObjects () {
        Message<OMMItemEvent> eventMessage = newMessage ();

        when (event.getHandle()).thenReturn(handle);
        when (event.getMsg()).thenReturn(ommMsg);

        when (marketPriceAdapter.adapt(ommMsg)).thenReturn(marketPrice);

        Message<MarketPrice> message = processor.process(eventMessage);

        return message;
    }

//    @Test
//    public void testProcessWithNonNullMarketPrice () {
//
//        session.putMarketPrice(handle, marketPrice);
//
//        Message<MarketPrice> message = createMockObjects();
//
//        assertNotNull (message.getPayload());
//        assertNotNull (message.getHeaders().get(SESSION));
//        // The marketPrice has already been added to the session so no point in
//        // checking for it here.
//        verify (marketPriceEntryCache, atLeastOnce()).put(handle, marketPrice);
//    }
//
//    @Test
//    public void testProcessWithNullMarketPrice () {
//        // Not needed in this test.
//        //session.putMarketPrice(handle, marketPrice);
//
//        Message<MarketPrice> message = createMockObjects();
//
//        assertNotNull (message.getPayload());
//        assertNotNull (message.getHeaders().get(SESSION));
//
//        // In this case the marketPrice should not exist in the session and
//        // hence the processor will have created a new one and added it using
//        // the handle as the key.
//        verify (marketPriceEntryCache, atLeastOnce()).put(handle, marketPrice);
//    }
}
