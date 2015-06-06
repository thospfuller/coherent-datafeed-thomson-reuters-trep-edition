package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.common.Handle;

/**
 * The specification that defines the methods that market price services must
 * provide.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface MarketPriceServiceSpecification
    extends AsynchronouslyUpdatableSpecification<MarketPrice> {

    List<Handle> query(
        String serviceName,
        Handle loginHandle,
        String... items
    );

    List<Handle> query(
        String serviceName,
        Handle loginHandle,
        String item
    );
}
