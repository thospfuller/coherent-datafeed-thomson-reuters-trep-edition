package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.infinispan.Cache;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.beans.CachedMarketPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.common.Handle;

/**
 * Updates the market price information.
 *
 * @see ExampleUtil.java
 *
 * @deprecated Refactoring of this project means this class is no longer needed.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UpdateMarketPriceTransformer
    extends UpdatableTransformer<CachedMarketPrice, MarketPrice> {

    public UpdateMarketPriceTransformer(
        Cache<Handle, CachedMarketPrice> queryCache,
        RFABeanAdapter<MarketPrice> marketPriceAdapter
    ) {
        super(queryCache, marketPriceAdapter);
    }
}
