package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.MarketByOrderAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.reuters.rfa.common.Handle;

/**
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UpdateMarketByOrderMessageProcessor extends AbstractUpdateRFABeanMessageProcessor<MarketByOrder> {

    public UpdateMarketByOrderMessageProcessor (
        MarketByOrderAdapter marketByOrderAdapter,
        Map<Handle, String> ricCache,
        Map<String, MarketByOrder> marketByOrderCache
    ) {
        super(marketByOrderAdapter, ricCache, marketByOrderCache);
    }

//    extends AbstractMarketByOrderMessageProcessor {
//
//    private static final Logger log =
//        LoggerFactory.getLogger(UpdateMarketByOrderMessageProcessor.class);
//
//    private final MarketByOrderAdapter marketByOrderAdapter;
//
//    private final Map<Handle, String> ricCache;
//
//    private final Map<String, MarketByOrder> marketByOrderCache;
//
//    public UpdateMarketByOrderMessageProcessor (
//        MarketByOrderAdapter marketPriceAdapter,
//        Map<Handle, String> ricCache,
//        Map<String, MarketByOrder> marketByOrderCache
//    ) {
//        this.marketByOrderAdapter = marketPriceAdapter;
//        this.ricCache = ricCache;
//        this.marketByOrderCache = marketByOrderCache;
//    }
//
//    /**
//     * @todo We don't necessarily need to clone the market price altogether --
//     *  rather we could simply update whatever data point requires an update and
//     *  then have the domain object issue the update via the property change
//     *  listener. This would be beneficial in the sense that we could then
//     *  attach any number of listeners to the domain class, one of which could
//     *  propagate that update as a single event or another which could send the
//     *  entire instance as an update.
//     */
//    @Override
//    public Message<MarketByOrder> process(Message<OMMItemEvent> message) {
//
//        log.info("updateMarketByOrderMessageProcessor.process: method " +
//            "begins; message: " + message);
//
//        MessageHeaders headers = message.getHeaders();
//
//        OMMItemEvent itemEvent = message.getPayload();
//
//        OMMMsg ommMsg = itemEvent.getMsg();
//
//        Handle handle = itemEvent.getHandle();
//
////        Session session = getSession(message);
//
//        String ric = ricCache.get(handle);
//        MarketByOrder currentMarketByOrder = marketByOrderCache.get(ric);
//
//        assertNotNull("currentMarketByOrder", currentMarketByOrder);
//
//        log.info ("Updating the currentMarketByOrder: " + currentMarketByOrder);
//
//        MarketByOrder updatedMarketByOrder =
//            (MarketByOrder) SerializationUtils.clone(currentMarketByOrder);
//
//        marketByOrderAdapter.adapt(ommMsg, updatedMarketByOrder);
//
////        session.putMarketByOrder(handle, updatedMarketByOrder);
//
//        Message<MarketByOrder> result = MessageBuilder
//            .withPayload(updatedMarketByOrder)
//            .copyHeaders(headers).build();
//
//        log.info("updateMarketByOrderMessageProcessor.process: method " +
//            "ends; result: " + result);
//
//        return result;
//    }
}
