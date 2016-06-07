package com.coherentlogic.coherent.datafeed.beans;

import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.common.Handle;

/**
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketMakerQueryParameters extends AbstractQuery<MarketMaker[]> {

    private static final long serialVersionUID = -2244535172899622273L;

    public MarketMakerQueryParameters(String serviceName, SessionBean sessionBean, MarketMaker[] items) {
        super(serviceName, sessionBean, items);
    }
}
