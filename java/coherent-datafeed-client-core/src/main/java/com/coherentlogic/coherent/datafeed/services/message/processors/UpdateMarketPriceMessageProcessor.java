package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.adapters.MarketPriceAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 */
public class UpdateMarketPriceMessageProcessor extends AbstractMarketPriceMessageProcessor {

    private static final Logger log =
        LoggerFactory.getLogger(UpdateMarketPriceMessageProcessor.class);

    private final MarketPriceAdapter marketPriceAdapter;

    private final Map<Handle, String> ricCache;

    private final Map<String, MarketPrice> marketPriceCache;

    public UpdateMarketPriceMessageProcessor (
        MarketPriceAdapter marketPriceAdapter,
        Map<Handle, String> ricCache,
        Map<String, MarketPrice> marketPriceCache
    ) {
        this.marketPriceAdapter = marketPriceAdapter;
        this.ricCache = ricCache;
        this.marketPriceCache = marketPriceCache;
    }

    /**
     *
     */
    @Override
    public Message<MarketPrice> process(Message<OMMItemEvent> message) {

        log.debug("updateMarketPriceMessageProcessor.process: method begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        OMMItemEvent itemEvent = message.getPayload();

        OMMMsg ommMsg = itemEvent.getMsg();

        Handle handle = itemEvent.getHandle();

        String ric = ricCache.get(handle);

        MarketPrice currentMarketPrice = marketPriceCache.get(ric);

        assertNotNull("currentMarketPrice", currentMarketPrice);

        log.debug ("Updating the following marketPrice: " + currentMarketPrice);

        marketPriceAdapter.adapt(ommMsg, currentMarketPrice);

        Message<MarketPrice> result = MessageBuilder
            .withPayload(currentMarketPrice)
            .copyHeaders(headers).build();

        log.debug("updateMarketPriceMessageProcessor.process: method ends; result: " + result);

        return result;
    }
}
