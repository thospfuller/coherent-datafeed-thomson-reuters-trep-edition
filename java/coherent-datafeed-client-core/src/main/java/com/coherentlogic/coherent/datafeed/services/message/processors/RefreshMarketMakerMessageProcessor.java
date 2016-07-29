    package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.reuters.rfa.common.Handle;

/**
 * Message processor that handles refresh messages from the Thomson Reuters
 * Enterprise Platform (TREP).
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RefreshMarketMakerMessageProcessor extends AbstractRefreshRFABeanMessageProcessor<MarketMaker> {

    public RefreshMarketMakerMessageProcessor(
        RFABeanAdapter<MarketMaker> objectAdapter,
        Map<Handle, String> ricCache,
        Map<String, MarketMaker> objectCache
    ) {
        super(objectAdapter, ricCache, objectCache);
    }

//    private static final Logger log =
//        LoggerFactory.getLogger(RefreshMarketMakerMessageProcessor.class);
//
//    private final MarketMakerAdapter marketMakerAdapter;
//
//    private final Map<Handle, String> ricCache;
//
//    private final Map<String, MarketMaker> marketMakerCache;
//
//    public RefreshMarketMakerMessageProcessor (
//        MarketMakerAdapter marketMakerAdapter,
//        Map<Handle, String> ricCache,
//        Map<String, MarketMaker> marketMakerCache
//    ) {
//        this.marketMakerAdapter = marketMakerAdapter;
//        this.ricCache = ricCache;
//        this.marketMakerCache = marketMakerCache;
//    }
//
//    /**
//     * @todo Should refresh events for existing market price objects cause an
//     *  exception to be thrown? See assertNull below.
//     */
//    @Override
//    public Message<MarketMaker> process(Message<OMMItemEvent> message) {
//
//        log.debug("refreshMarketMakerMessageProcessor.process: method begins; message: " + message);
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
//        MarketMaker marketMaker = marketMakerCache.get(ric);
//
//        /* TODO: This is not really valid -- consider the following 
//         *       The user disconnects the Ethernet cable and waits a few moments
//         *       -- when the user reconnects the Ethernet cable a refresh will
//         *       take place however the market price will not be null, so this
//         *       should not be an exception. 
//         */
////        assertNull("marketMaker (expected: " +
////            marketMakerAdapter.adapt(ommMsg) + ") ", marketMaker);
//
//        if (marketMaker == null) {
//
//            marketMaker = marketMakerAdapter.adapt(ommMsg);
//
//            log.info("The marketMaker was null so a new instance was " +
//                "created for this refresh.");
//
//        } else {
//
//            marketMakerAdapter.adapt(ommMsg, marketMaker);
//
//            log.info("The marketMaker was not null so an existing instance " +
//                "was refreshed.");
//        }
//
//        Message<MarketMaker> result = MessageBuilder
//            .withPayload(marketMaker)
//            .copyHeaders(headers).build();
//
//        log.info("refreshMarketMakerMessageProcessor.process: method " +
//            "ends; result: " + result);
//
//        return result;
//    }
}
