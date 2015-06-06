package com.coherentlogic.coherent.datafeed.services.message.processors;

import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.coherentlogic.coherent.datafeed.services.AsynchronouslyUpdatableSpecification;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetNextTimeSeriesUpdateMessageProcessor
    extends GetNextUpdateMessageProcessor<TimeSeries> {

    public GetNextTimeSeriesUpdateMessageProcessor(
        AsynchronouslyUpdatableSpecification<TimeSeries>
            asynchronouslyUpdatableSpecification
    ) {
        super(asynchronouslyUpdatableSpecification);
    }
}
