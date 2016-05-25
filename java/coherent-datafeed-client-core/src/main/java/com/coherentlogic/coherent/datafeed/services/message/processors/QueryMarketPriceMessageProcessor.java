package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.services.CacheableQueryableService;

/**
 * Message processor implementation that delegates calls to the {@link
 * #marketPriceService}'s query method and returns the list of handles to the
 * caller.
 *
 * Note that we use this to shield the user from working with the service
 * directly. We accomplish this by having a gateway send messages to an instance
 * of this class, which in turn invokes methods on the actual service
 * implementation.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class QueryMarketPriceMessageProcessor extends AbstractQueryMessageProcessor<MarketPrice> {
    //implements MessageProcessorSpecification <MarketPriceQueryParameters, Map<String, MarketPrice>> {

    public QueryMarketPriceMessageProcessor(
        CacheableQueryableService<MarketPrice> queryableService,
        Map<String, MarketPrice> objectCache
    ) {
        super(queryableService, objectCache);
    }

//    private static final Logger log = LoggerFactory.getLogger(QueryMarketPriceMessageProcessor.class);
//
//    private final MarketPriceService marketPriceService;
//
//    private final Map<String, MarketPrice> marketPriceCache;
//
//    public QueryMarketPriceMessageProcessor(
//        MarketPriceService marketPriceService,
//        Map<String, MarketPrice> marketPriceCache
//    ) {
//        this.marketPriceService = marketPriceService;
//        this.marketPriceCache = marketPriceCache;
//    }
//
//    /**
//     * @todo Unit test this method.
//     * @TODO Need to properly throw the exception from the assertNotNull (right now it ends up in the log file but it
//     *  should be returned to the method caller. 
//     */
//    @Override
//    public Message<Map<String, MarketPrice>> process(Message<MarketPriceQueryParameters> message) {
//
//        Message<Map<String, MarketPrice>> result = null;
//
//        MarketPriceQueryParameters parameters = message.getPayload();
//
//        log.info("parameters: " + parameters);
//
//        String serviceName = parameters.getServiceName();
//
//        Handle loginHandle = parameters.getLoginHandle();
//
//        MarketPrice[] items = parameters.getItem();
//
//        for (MarketPrice nextMarketPrice : items) {
//
//            String ric = nextMarketPrice.getRic();
//
//            assertNotNull ("ric", ric);
//
//            marketPriceCache.put(ric, nextMarketPrice);
//
//            marketPriceService.query(
//                ServiceName.valueOf(serviceName),
//                loginHandle,
//                ric
//            );
//        }
//
//        MessageHeaders headers = message.getHeaders();
//
//        result =
//            MessageBuilder
//                .withPayload(marketPriceCache)
//                .copyHeaders(headers)
//                .build();
//
//        return result;
//    }
}
