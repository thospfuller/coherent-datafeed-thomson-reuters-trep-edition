package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketByPrice;
import com.reuters.rfa.common.Handle;

/**
 * Message processor that handles refresh messages from the Thomson Reuters
 * Enterprise Platform (TREP).
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RefreshMarketByPriceMessageProcessor extends AbstractRefreshRFABeanMessageProcessor<MarketByPrice> {

    public RefreshMarketByPriceMessageProcessor(
        RFABeanAdapter<MarketByPrice> objectAdapter,
        Map<Handle, String> ricCache,
        Map<String, MarketByPrice> objectCache
    ) {
        super(objectAdapter, ricCache, objectCache);
    }
}
