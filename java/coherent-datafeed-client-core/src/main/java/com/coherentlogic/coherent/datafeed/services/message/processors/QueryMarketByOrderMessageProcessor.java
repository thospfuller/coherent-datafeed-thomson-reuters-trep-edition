package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.services.CacheableQueryableService;

/**
 * Message processor implementation that delegates calls to the {@link
 * #marketByOrderService}'s query method and returns the list of handles to the
 * caller.
 *
 * Note that we use this to shield the user from working with the service
 * directly. We accomplish this by having a gateway send messages to an instance
 * of this class, which in turn invokes methods on the actual service
 * implementation.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class QueryMarketByOrderMessageProcessor extends AbstractQueryMessageProcessor<MarketByOrder> {

    public QueryMarketByOrderMessageProcessor(
        CacheableQueryableService<MarketByOrder> queryableService,
        Map<String, MarketByOrder> objectCache
    ) {
        super(queryableService, objectCache);
    }
    //implements MessageProcessorSpecification <QueryParameters, Map<String, MarketByOrder>> {

//    private static final Logger log = LoggerFactory.getLogger(QueryMarketByOrderMessageProcessor.class);
//
//    private final MarketByOrderServiceSpecification marketByOrderService;
//
//    private final Map<String, MarketByOrder> marketByOrderCache;
//
//    public QueryMarketByOrderMessageProcessor(
//        MarketByOrderServiceSpecification marketByOrderService,
//        Map<String, MarketByOrder> marketByOrderCache
//    ) {
//        this.marketByOrderService = marketByOrderService;
//        this.marketByOrderCache = marketByOrderCache;
//    }
//
//    /**
//     * @todo Unit test this method.
//     */
//    @Override
////    @Transactional
//    public Message<Map<String, MarketByOrder>> process(
//        Message<QueryParameters> message) {
//
//        Message<Map<String, MarketByOrder>> result = null;
//
//        QueryParameters parameters = message.getPayload();
//
//        log.info("parameters: " + parameters);
//
//        String serviceName = parameters.getServiceName();
//
//        Handle loginHandle = parameters.getLoginHandle();
//
//        String[] items = parameters.getItem();
//
//        marketByOrderService.query(ServiceName.valueOf(serviceName), loginHandle, items);
//
//        MessageHeaders headers = message.getHeaders();
//
//        result =
//            MessageBuilder
//                .withPayload(marketByOrderCache)
//                .copyHeaders(headers)
//                .build();
//
//        return result;
//    }
}
