package com.coherentlogic.coherent.datafeed.services;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.reuters.rfa.common.Handle;

/**
 * The specification that defines the methods that market maker services must
 * provide.
 *
 * @TODO: Refactor this and the MarketMakerServiceSpecification so that they're
 *  either inheriting from a base interface, or the interfaces are merged and
 *  only one is used.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface MarketMakerServiceSpecification {

    Map<String, MarketMaker> query(
        ServiceName serviceName,
        Handle loginHandle,
        String... items
    );
}
