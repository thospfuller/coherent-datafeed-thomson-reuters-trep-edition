package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
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
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class QueryMarketMakerMessageProcessor extends AbstractQueryMessageProcessor<MarketMaker> {

    public QueryMarketMakerMessageProcessor(
        CacheableQueryableService<MarketMaker> queryableService,
        Map<String, MarketMaker> objectCache
    ) {
        super(queryableService, objectCache);
    }

//implements MessageProcessorSpecification <QueryParameters, Map<String, MarketMaker>> {
//
//    private static final Logger log = LoggerFactory.getLogger(QueryMarketMakerMessageProcessor.class);
//
//    private final MarketMakerServiceSpecification marketMakerService;
//
//    private final Map<String, MarketMaker> marketMakerCache;
//
//    public QueryMarketMakerMessageProcessor(
//        MarketMakerServiceSpecification marketMakerService,
//        Map<String, MarketMaker> marketMakerCache
//    ) {
//        this.marketMakerService = marketMakerService;
//        this.marketMakerCache = marketMakerCache;
//    }
//
//    /**
//     * @todo Unit test this method.
//     */
//    @Override
//    @Transactional
//    public Message<Map<String, MarketMaker>> process(
//        Message<QueryParameters> message) {
//
//        Message<Map<String, MarketMaker>> result = null;
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
//        marketMakerService.query(ServiceName.valueOf(serviceName), loginHandle, items);
//
//        MessageHeaders headers = message.getHeaders();
//
//        result =
//            MessageBuilder
//                .withPayload(marketMakerCache)
//                .copyHeaders(headers)
//                .build();
//
//        return result;
//    }
}
