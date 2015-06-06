package com.coherentlogic.coherent.datafeed.services.message.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.support.MessageBuilder;

import com.coherentlogic.coherent.datafeed.adapters.MarketPriceAdapter;
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
public class RefreshMarketPriceMessageProcessor
    extends AbstractMarketPriceMessageProcessor {

    private static final Logger log =
        LoggerFactory.getLogger(RefreshMarketPriceMessageProcessor.class);

    private final MarketPriceAdapter marketPriceAdapter;

    public RefreshMarketPriceMessageProcessor (
        MarketPriceAdapter marketPriceAdapter
    ) {
        this.marketPriceAdapter = marketPriceAdapter;
    }

    /**
     * @todo Should refresh events for existing market price objects cause an
     *  exception to be thrown? See assertNull below.
     */
    @Override
    public Message<MarketPrice> process(Message<OMMItemEvent> message) {

        log.info("refreshMarketPriceMessageProcessor.process: method " +
            "begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        Session session = getSession(message);

        OMMItemEvent itemEvent = message.getPayload();

        Handle handle = itemEvent.getHandle();

        log.debug("Refresh begins for the itemEvent with the handle " + handle);

        OMMMsg ommMsg = itemEvent.getMsg();

        MarketPrice marketPrice = session.getMarketPrice(handle);

        /* TODO: This is not really valid -- consider the following 
         *       The user disconnects the Ethernet cable and waits a few moments
         *       -- when the user reconnects the Ethernet cable a refresh will
         *       take place however the market price will not be null, so this
         *       should not be an exception. 
         */
//        assertNull("marketPrice (expected: " +
//            marketPriceAdapter.adapt(ommMsg) + ") ", marketPrice);

        if (marketPrice == null) {
            marketPrice = marketPriceAdapter.adapt(ommMsg);
            log.info("The marketPrice was null so a new instance was " +
                "created for this refresh.");
        } else {
            marketPriceAdapter.adapt(ommMsg, marketPrice);
            log.info("The marketPrice was not null so an existing instance " +
                "was refreshed.");
        }

        session.putMarketPrice(handle, marketPrice);

        Message<MarketPrice> result = MessageBuilder
            .withPayload(marketPrice)
            .copyHeaders(headers).build();

        log.info("refreshMarketPriceMessageProcessor.process: method " +
            "ends; result: " + result);

        return result;
    }
}
