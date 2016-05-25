package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.reuters.rfa.common.Handle;

/**
 * Message processor that handles refresh messages from the Thomson Reuters
 * Enterprise Platform (TREP).
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RefreshMarketByOrderMessageProcessor  extends AbstractRefreshRFABeanMessageProcessor<MarketByOrder> {

    public RefreshMarketByOrderMessageProcessor(
        RFABeanAdapter<MarketByOrder> objectAdapter,
        Map<Handle, String> ricCache,
        Map<String, MarketByOrder> objectCache
    ) {
        super(objectAdapter, ricCache, objectCache);
    }

//    extends AbstractMarketByOrderMessageProcessor {
//
//    private static final Logger log =
//        LoggerFactory.getLogger(RefreshMarketByOrderMessageProcessor.class);
//
//    private final MarketByOrderAdapter marketByOrderAdapter;
//
//    private final Map<Handle, String> ricCache;
//
//    private final Map<String, MarketByOrder> marketByOrderCache;
//
//    public RefreshMarketByOrderMessageProcessor (
//        MarketByOrderAdapter marketByOrderAdapter,
//        Map<Handle, String> ricCache,
//        Map<String, MarketByOrder> marketByOrderCache
//    ) {
//        this.marketByOrderAdapter = marketByOrderAdapter;
//        this.ricCache = ricCache;
//        this.marketByOrderCache = marketByOrderCache;
//    }
//
//    /**
//     * @todo Should refresh events for existing market price objects cause an
//     *  exception to be thrown? See assertNull below.
//     */
//    @Override
//    public Message<MarketByOrder> process(Message<OMMItemEvent> message) {
//
//        log.debug("refreshMarketByOrderMessageProcessor.process: method begins; message: " + message);
//
//        MessageHeaders headers = message.getHeaders();
//
//        OMMItemEvent itemEvent = message.getPayload();
//
//        Handle handle = itemEvent.getHandle();
//
//        log.debug("Refresh begins for the itemEvent with the handle " + handle);
//
//        OMMMsg ommMsg = itemEvent.getMsg();
//
//        String ric = ricCache.get(handle);
//
//        MarketByOrder marketByOrder = marketByOrderCache.get(ric);
//
//        assertNotNull ("marketByOrder", marketByOrder);
//
//        marketByOrderAdapter.adapt(ommMsg, marketByOrder);
//
//        log.debug("The marketByOrder with the ric " + ric + " was refreshed.");
//
//        Message<MarketByOrder> result = MessageBuilder
//            .withPayload(marketByOrder)
//            .copyHeaders(headers).build();
//
//        log.debug("refreshMarketByOrderMessageProcessor.process: method ends; result: " + result);
//
//        return result;
//    }
}
