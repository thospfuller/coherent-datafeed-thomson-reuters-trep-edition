package com.coherentlogic.coherent.datafeed.services;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;

/**
 * The specification that defines the methods that market by order services must
 * provide.
 *
 * @TODO: Refactor this and the MarketPriceServiceSpecification so that they're
 *  either inheriting from a base interface, or the interfaces are merged and
 *  only one is used.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface MarketByOrderServiceSpecification {

    void query (ServiceName serviceName, SessionBean sessionBean, String... rics);
}
