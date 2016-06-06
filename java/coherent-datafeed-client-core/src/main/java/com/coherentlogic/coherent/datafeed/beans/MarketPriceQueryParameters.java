package com.coherentlogic.coherent.datafeed.beans;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.common.Handle;

/**
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketPriceQueryParameters extends AbstractQuery<MarketPrice[]> {

    private static final long serialVersionUID = -4774057903925958972L;

    public MarketPriceQueryParameters(
        String serviceName,
        Handle loginHandle,
        SessionBean sessionBean,
        MarketPrice[] items
    ) {
        super(serviceName, loginHandle, sessionBean, items);
    }
}
