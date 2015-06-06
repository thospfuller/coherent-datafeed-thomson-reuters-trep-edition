package com.coherentlogic.coherent.datafeed.services;

import com.coherentlogic.coherent.datafeed.domain.StatusResponse;


/**
 * This interface represents the specification for services that provide
 * methods for working with {@link StatusResponse} events.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface StatusResponseServiceSpecification
    extends AsynchronouslyUpdatableSpecification<StatusResponse> {

    String getNextUpdateAsJSON(Long timeout);

    String getNextUpdateAsJSON(String timeout);
}
