package com.coherentlogic.coherent.datafeed.beans;

import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.reuters.rfa.common.Handle;

/**
 * A {@link CachedObject} that holds instances of {@link MarketMaker}.
 *
 * @deprecated Classed that extend from CachedObject are no longer used.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class CachedMarketMaker extends CachedObject<MarketMaker> {

    private static final long serialVersionUID = -2005243168855967688L;

    public CachedMarketMaker(Handle handle) {
        super(handle);
    }
}
