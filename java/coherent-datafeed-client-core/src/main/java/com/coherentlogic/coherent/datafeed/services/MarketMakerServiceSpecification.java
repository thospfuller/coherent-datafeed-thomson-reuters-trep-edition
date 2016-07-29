package com.coherentlogic.coherent.datafeed.services;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;

/**
 * The specification that defines the methods that market maker services must
 * provide.
 *
 * @TODO: Refactor this and the MarketMakerServiceSpecification so that they're
 *  either inheriting from a base interface, or the interfaces are merged and
 *  only one is used.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface MarketMakerServiceSpecification {

    void query(
        ServiceName serviceName,
        SessionBean sessionBean,
        String... items
    );
}
