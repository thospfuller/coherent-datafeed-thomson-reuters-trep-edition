package com.coherentlogic.coherent.datafeed.beans;

import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.reuters.rfa.common.Handle;

/**
 * A {@link CachedObject} that holds instances of {@link MarketByOrder}.
 *
 * @deprecated Classed that extend from CachedObject are no longer used.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class CachedMarketByOrder extends CachedObject<MarketByOrder> {

    private static final long serialVersionUID = -5229473654894804233L;

    public CachedMarketByOrder(Handle handle) {
        super(handle);
    }
}
