package com.coherentlogic.coherent.datafeed.beans;

import com.coherentlogic.coherent.datafeed.domain.MarketByPrice;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;

/**
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketByPriceQueryParameters extends AbstractQuery<MarketByPrice[]> {

    private static final long serialVersionUID = -1632019075968036727L;

    public MarketByPriceQueryParameters(String serviceName, SessionBean sessionBean, MarketByPrice[] item) {
        super(serviceName, sessionBean, item);
    }
}
