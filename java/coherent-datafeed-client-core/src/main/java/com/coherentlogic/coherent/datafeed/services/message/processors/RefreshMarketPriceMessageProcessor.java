package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.common.Handle;

/**
 * Message processor that handles refresh messages from the Thomson Reuters
 * Enterprise Platform (TREP).
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RefreshMarketPriceMessageProcessor extends AbstractRefreshRFABeanMessageProcessor<MarketPrice> {

    public RefreshMarketPriceMessageProcessor(
        RFABeanAdapter<MarketPrice> objectAdapter,
        Map<Handle, String> ricCache,
        Map<String, MarketPrice> objectCache
    ) {
        super(objectAdapter, ricCache, objectCache);
    }

//    private static final Logger log =
//        LoggerFactory.getLogger(RefreshMarketPriceMessageProcessor.class);
//
//    private final MarketPriceAdapter marketPriceAdapter;
//
//    private final Map<Handle, String> ricCache;
//
//    private final Map<String, MarketPrice> marketPriceCache;
//
//    public RefreshMarketPriceMessageProcessor (
//        MarketPriceAdapter marketPriceAdapter,
//        Map<Handle, String> ricCache,
//        Map<String, MarketPrice> marketPriceCache
//    ) {
//        this.marketPriceAdapter = marketPriceAdapter;
//        this.ricCache = ricCache;
//        this.marketPriceCache = marketPriceCache;
//    }
//
//    /**
//     * @todo Should refresh events for existing market price objects cause an
//     *  exception to be thrown? See assertNull below.
//     */
//    @Override
//    public Message<MarketPrice> process(Message<OMMItemEvent> message) {
//
//        log.debug("refreshMarketPriceMessageProcessor.process: method begins; message: " + message);
//
//        MessageHeaders headers = message.getHeaders();
//
//        OMMItemEvent itemEvent = message.getPayload();
//
//        Handle handle = itemEvent.getHandle();
//
//        String ric = ricCache.get(handle);
//
//        MarketPrice currentMarketPrice = marketPriceCache.get(ric);
//
//        log.debug("Refresh begins for the itemEvent with the handle " + handle);
//
//        OMMMsg ommMsg = itemEvent.getMsg();
//
//        assertNotNull("currentMarketPrice", currentMarketPrice);
//
//        marketPriceAdapter.adapt(ommMsg, currentMarketPrice);
//
//        log.debug("The marketPrice with the ric " + ric + " was refreshed.");
//
//        Message<MarketPrice> result = MessageBuilder.withPayload(currentMarketPrice).copyHeaders(headers).build();
//
//        log.debug("refreshMarketPriceMessageProcessor.process: method ends; result: " + result);
//
//        return result;
//    }
}
