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
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class QueryMarketPriceMessageProcessor extends AbstractQueryMessageProcessor<MarketPrice> {

    public QueryMarketPriceMessageProcessor(
        CacheableQueryableService<MarketPrice> queryableService,
        Map<String, MarketPrice> objectCache
    ) {
        super(queryableService, objectCache);
    }
}
