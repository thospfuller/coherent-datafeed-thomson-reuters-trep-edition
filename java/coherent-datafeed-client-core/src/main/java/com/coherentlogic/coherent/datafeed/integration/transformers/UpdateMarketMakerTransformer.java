package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.infinispan.Cache;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.beans.CachedMarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.reuters.rfa.common.Handle;

/**
 * Updates the market maker information.
 *
 * @see ExampleUtil.java
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UpdateMarketMakerTransformer
    extends UpdatableTransformer<CachedMarketMaker, MarketMaker> {

    public UpdateMarketMakerTransformer(
        Cache<Handle, CachedMarketMaker> queryCache,
        RFABeanAdapter<MarketMaker> marketMakerAdapter
    ) {
        super(queryCache, marketMakerAdapter);
    }
}
