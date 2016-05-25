package com.coherentlogic.coherent.datafeed.beans;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.common.Handle;

/**
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketPriceQueryParameters extends AbstractQuery<MarketPrice[]> {

    private static final long serialVersionUID = -5137334825680018632L;

    public MarketPriceQueryParameters(String serviceName, Handle loginHandle, MarketPrice[] item) {
        super(serviceName, loginHandle, item);
    }
}
