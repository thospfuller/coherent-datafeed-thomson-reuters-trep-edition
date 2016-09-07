package com.coherentlogic.coherent.datafeed.beans;

import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;

/**
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketByOrderQueryParameters extends AbstractQuery<MarketByOrder[]> {

    private static final long serialVersionUID = -4049102953592195180L;

    public MarketByOrderQueryParameters(String serviceName, SessionBean sessionBean, MarketByOrder[] item) {
        super(serviceName, sessionBean, item);
    }
}
