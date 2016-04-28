package com.coherentlogic.coherent.datafeed.services.message.processors;

import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.coherentlogic.coherent.datafeed.services.AsynchronouslyUpdatableSpecification;

/**
 * A type-specific implementation of {@link
 * GetNextUpdateAsJSONMessageProcessor}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetNextTimeSeriesUpdateAsJSONMessageProcessor
    extends GetNextUpdateAsJSONMessageProcessor<TimeSeries> {

    public GetNextTimeSeriesUpdateAsJSONMessageProcessor(
            AsynchronouslyUpdatableSpecification<TimeSeries>
                asynchronouslyUpdatableSpecification) {
        super(asynchronouslyUpdatableSpecification);
    }
}
