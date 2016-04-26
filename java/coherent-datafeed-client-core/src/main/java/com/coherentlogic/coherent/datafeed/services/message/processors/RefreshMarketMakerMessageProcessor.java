package com.coherentlogic.coherent.datafeed.services.message.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.adapters.MarketMakerAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Message processor that handles refresh messages from the Thomson Reuters
 * Enterprise Platform (TREP).
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RefreshMarketMakerMessageProcessor
    extends AbstractMarketMakerMessageProcessor {

    private static final Logger log =
        LoggerFactory.getLogger(RefreshMarketMakerMessageProcessor.class);

    private final MarketMakerAdapter marketMakerAdapter;

    public RefreshMarketMakerMessageProcessor (
        MarketMakerAdapter marketMakerAdapter
    ) {
        this.marketMakerAdapter = marketMakerAdapter;
    }

    /**
     * @todo Should refresh events for existing market price objects cause an
     *  exception to be thrown? See assertNull below.
     */
    @Override
    public Message<MarketMaker> process(Message<OMMItemEvent> message) {

        log.info("refreshMarketMakerMessageProcessor.process: method " +
            "begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        Session session = getSession(message);

        OMMItemEvent itemEvent = message.getPayload();

        Handle handle = itemEvent.getHandle();

        log.debug("Refresh begins for the itemEvent with the handle " + handle);

        OMMMsg ommMsg = itemEvent.getMsg();

        MarketMaker marketMaker = session.getMarketMaker(handle);

        /* TODO: This is not really valid -- consider the following 
         *       The user disconnects the Ethernet cable and waits a few moments
         *       -- when the user reconnects the Ethernet cable a refresh will
         *       take place however the market price will not be null, so this
         *       should not be an exception. 
         */
//        assertNull("marketMaker (expected: " +
//            marketMakerAdapter.adapt(ommMsg) + ") ", marketMaker);

        if (marketMaker == null) {
            marketMaker = marketMakerAdapter.adapt(ommMsg);
            log.info("The marketPrice was null so a new instance was " +
                "created for this refresh.");
        } else {
        	marketMakerAdapter.adapt(ommMsg, marketMaker);
            log.info("The marketPrice was not null so an existing instance " +
                "was refreshed.");
        }

        session.putMarketMaker(handle, marketMaker);

        Message<MarketMaker> result = MessageBuilder
            .withPayload(marketMaker)
            .copyHeaders(headers).build();

        log.info("refreshMarketMakerMessageProcessor.process: method " +
            "ends; result: " + result);

        return result;
    }
}
