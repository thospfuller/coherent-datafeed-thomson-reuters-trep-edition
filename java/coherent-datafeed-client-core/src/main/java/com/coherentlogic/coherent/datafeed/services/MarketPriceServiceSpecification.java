package com.coherentlogic.coherent.datafeed.services;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;

/**
 * The specification that defines the methods that market price services must
 * provide.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface MarketPriceServiceSpecification {

    void query (ServiceName serviceName, SessionBean sessionBean, String... rics);
}
