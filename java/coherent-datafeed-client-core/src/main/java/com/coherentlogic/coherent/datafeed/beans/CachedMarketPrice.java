package com.coherentlogic.coherent.datafeed.beans;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.common.Handle;

/**
 * A {@link CachedObject} that holds instances of {@link MarketPrice}.
 *
 * @deprecated Classed that extend from CachedObject are no longer used.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class CachedMarketPrice extends CachedObject<MarketPrice> {

    private static final long serialVersionUID = -5420243701653317375L;

    public CachedMarketPrice(Handle handle) {
        super(handle);
    }
}
