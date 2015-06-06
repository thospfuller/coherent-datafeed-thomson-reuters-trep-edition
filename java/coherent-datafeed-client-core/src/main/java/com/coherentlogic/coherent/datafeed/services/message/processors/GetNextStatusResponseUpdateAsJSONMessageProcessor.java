package com.coherentlogic.coherent.datafeed.services.message.processors;

import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.coherentlogic.coherent.datafeed.services.AsynchronouslyUpdatableSpecification;

/**
 * A type-specific implementation of {@link
 * GetNextUpdateAsJSONMessageProcessor}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetNextStatusResponseUpdateAsJSONMessageProcessor
    extends GetNextUpdateAsJSONMessageProcessor<StatusResponse> {

    public GetNextStatusResponseUpdateAsJSONMessageProcessor(
            AsynchronouslyUpdatableSpecification<StatusResponse>
                asynchronouslyUpdatableSpecification) {
        super(asynchronouslyUpdatableSpecification);
    }
}
