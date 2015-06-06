package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.datafeed.domain.StatusResponse;

/**
 * Factory class for creating {@link StatusResponse} objects with a unique id
 * and timestamp. This factory is used when we need to pipe status response
 * messages to a database.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DefaultStatusResponseFactory
    implements Factory<StatusResponse> {

    @Override
    public StatusResponse getInstance() {
        return new StatusResponse();
    }
}
