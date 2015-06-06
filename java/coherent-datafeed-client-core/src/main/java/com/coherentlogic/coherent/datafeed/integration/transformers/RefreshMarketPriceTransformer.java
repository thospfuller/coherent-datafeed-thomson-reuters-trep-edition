package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.infinispan.Cache;

import com.coherentlogic.coherent.datafeed.adapters.MarketPriceAdapter;
import com.coherentlogic.coherent.datafeed.beans.CachedMarketPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.common.Handle;

/**
 * This transformer takes REFRESH_RESP events and transfers the market price
 * values to the domain model.
 *
 * Events of type REFRESH_RESP are sent initially from Thomson Reuters and
 * contain the initial snapshot of data pertaining to a specific RIC. Data
 * updates are then sent as UPDATE_RESP events, which are processed differently
 * since this is a subset of the initial snapshot.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RefreshMarketPriceTransformer
    extends RefreshableTransformer<CachedMarketPrice, MarketPrice> {

    public RefreshMarketPriceTransformer(
        Cache<Handle, CachedMarketPrice> queryCache,
        MarketPriceAdapter marketPriceAdapter
    ) {
        super(queryCache, marketPriceAdapter);
    }
}
