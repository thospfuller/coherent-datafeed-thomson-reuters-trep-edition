package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

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

    private final Map<Handle, String> ricCache;

    private final Map<String, MarketPrice> marketPriceCache;

    public RefreshMarketPriceMessageProcessor (
        MarketPriceAdapter marketPriceAdapter,
        Map<Handle, String> ricCache,
        Map<String, MarketPrice> marketPriceCache
    ) {
        this.marketPriceAdapter = marketPriceAdapter;
        this.ricCache = ricCache;
        this.marketPriceCache = marketPriceCache;
    }

    /**
     * @todo Should refresh events for existing market price objects cause an
     *  exception to be thrown? See assertNull below.
     */
    @Override
    public Message<MarketPrice> process(Message<OMMItemEvent> message) {

        log.debug("refreshMarketPriceMessageProcessor.process: method begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        OMMItemEvent itemEvent = message.getPayload();

        Handle handle = itemEvent.getHandle();

        String ric = ricCache.get(handle);

        MarketPrice marketPrice = marketPriceCache.get(ric);

        log.debug("Refresh begins for the itemEvent with the handle " + handle);

        OMMMsg ommMsg = itemEvent.getMsg();

        if (marketPrice == null) {
            marketPrice = marketPriceAdapter.adapt(ommMsg);
            log.debug("The marketPrice was null so a new instance was created for this refresh.");
        } else {
            marketPriceAdapter.adapt(ommMsg, marketPrice);
            log.debug("The marketPrice was not null so an existing instance was refreshed.");
        }

        Message<MarketPrice> result = MessageBuilder.withPayload(marketPrice).copyHeaders(headers).build();

        log.debug("refreshMarketPriceMessageProcessor.process: method ends; result: " + result);

        return result;
    }
}
