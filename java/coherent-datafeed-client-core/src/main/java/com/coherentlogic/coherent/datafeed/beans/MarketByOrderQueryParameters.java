package com.coherentlogic.coherent.datafeed.beans;

import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.reuters.rfa.common.Handle;

/**
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketByOrderQueryParameters extends AbstractQuery<MarketByOrder[]> {

    private static final long serialVersionUID = -4049102953592195180L;

    public MarketByOrderQueryParameters(String serviceName, Handle loginHandle, MarketByOrder[] item) {
        super(serviceName, loginHandle, item);
    }
}
