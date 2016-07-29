package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.MarketMakerAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.reuters.rfa.common.Handle;

/**
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UpdateMarketMakerMessageProcessor extends AbstractUpdateRFABeanMessageProcessor<MarketMaker> {

    public UpdateMarketMakerMessageProcessor (
        MarketMakerAdapter marketMakerAdapter,
        Map<Handle, String> ricCache,
        Map<String, MarketMaker> marketMakerCache
    ) {
        super (marketMakerAdapter, ricCache, marketMakerCache);
    }

//    extends AbstractMarketMakerMessageProcessor {
//
//    private static final Logger log =
//        LoggerFactory.getLogger(UpdateMarketMakerMessageProcessor.class);
//
//    private final MarketMakerAdapter marketMakerAdapter;
//
//    private final Map<Handle, String> ricCache;
//
//    private final Map<String, MarketMaker> marketMakerCache;
//
//    public UpdateMarketMakerMessageProcessor (
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
//     * @todo We don't necessarily need to clone the market price altogether --
//     *  rather we could simply update whatever data point requires an update and
//     *  then have the domain object issue the update via the property change
//     *  listener. This would be beneficial in the sense that we could then
//     *  attach any number of listeners to the domain class, one of which could
//     *  propagate that update as a single event or another which could send the
//     *  entire instance as an update.
//     */
//    @Override
//    public Message<MarketMaker> process(Message<OMMItemEvent> message) {
//
//        log.info("updateMarketMakerMessageProcessor.process: method " +
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
//        String ric = ricCache.get(handle);
//
//        MarketMaker currentMarketMaker = marketMakerCache.get(ric);
//
//        assertNotNull("currentMarketMaker", currentMarketMaker);
//
//        log.info ("Updating the following marketMaker: " + currentMarketMaker);
//
//        marketMakerAdapter.adapt(ommMsg, currentMarketMaker);
//
//        Message<MarketMaker> result = MessageBuilder
//            .withPayload(currentMarketMaker)
//            .copyHeaders(headers).build();
//
//        log.info("updateMarketMakerMessageProcessor.process: method " +
//            "ends; result: " + result);
//
//        return result;
//    }
}
