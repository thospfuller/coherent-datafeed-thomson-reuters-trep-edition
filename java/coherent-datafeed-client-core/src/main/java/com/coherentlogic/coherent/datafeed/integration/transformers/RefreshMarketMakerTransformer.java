package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.infinispan.Cache;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.beans.CachedMarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.reuters.rfa.common.Handle;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RefreshMarketMakerTransformer
    extends RefreshableTransformer<CachedMarketMaker, MarketMaker> {

    public RefreshMarketMakerTransformer(
        Cache<Handle, CachedMarketMaker> queryCache,
        RFABeanAdapter<MarketMaker> marketMakerAdapter
    ) {
        super(queryCache, marketMakerAdapter);
    }
}
