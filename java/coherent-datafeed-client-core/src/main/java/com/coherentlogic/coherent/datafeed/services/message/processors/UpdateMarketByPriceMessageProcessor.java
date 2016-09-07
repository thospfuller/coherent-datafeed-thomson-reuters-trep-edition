package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.MarketByPriceAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketByPrice;
import com.reuters.rfa.common.Handle;

/**
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UpdateMarketByPriceMessageProcessor extends AbstractUpdateRFABeanMessageProcessor<MarketByPrice> {

    public UpdateMarketByPriceMessageProcessor (
        MarketByPriceAdapter marketByPriceAdapter,
        Map<Handle, String> ricCache,
        Map<String, MarketByPrice> marketByPriceCache
    ) {
        super(marketByPriceAdapter, ricCache, marketByPriceCache);
    }
}
