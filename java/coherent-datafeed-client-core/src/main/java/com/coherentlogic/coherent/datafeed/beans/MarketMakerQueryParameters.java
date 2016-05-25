package com.coherentlogic.coherent.datafeed.beans;

import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.reuters.rfa.common.Handle;

/**
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketMakerQueryParameters extends AbstractQuery<MarketMaker[]> {

    private static final long serialVersionUID = -2906849392823013754L;

    public MarketMakerQueryParameters(String serviceName, Handle loginHandle, MarketMaker[] item) {
        super(serviceName, loginHandle, item);
    }
}
