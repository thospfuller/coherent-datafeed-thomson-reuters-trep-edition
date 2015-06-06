package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.transformer.AbstractPayloadTransformer;

import com.coherentlogic.coherent.datafeed.adapters.TimeSeriesAdapter;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;

/**
 * Transformer converts an instance of {@link OmmItemEvent} into an instance of
 * {@link TimeSeries}. 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesTransformer extends
    AbstractPayloadTransformer<TimeSeriesEntries, TimeSeries> {

    private static final Logger log =
        LoggerFactory.getLogger(TimeSeriesTransformer.class);

    private final TimeSeriesAdapter timeSeriesAdapter;

    public TimeSeriesTransformer(TimeSeriesAdapter timeSeriesAdapter) {
        super();
        this.timeSeriesAdapter = timeSeriesAdapter;
    }

    /**
     * TODO:
     * 0.) On completion event for the specific time series request then
     *     proceed.
     * 1.) Get the TimeSeriesEntry from the cache given the handle.
     * 2.) We need to save partial data since this update is not yet complete.
     *     So add the data to a list or something, then once this has completed
     *     we will build the TimeSeries and sent it to the queue.
     * 3.) 
     *
     * @see com.reuters.ts1.TS1DefDb
     */
    @Override
    protected TimeSeries transformPayload(
        TimeSeriesEntries timeSeriesEntries) throws Exception {

        TimeSeries timeSeries = timeSeriesAdapter.adapt(timeSeriesEntries);

        log.info("transformPayload: method ends; returning the timeSeries: " +
            timeSeries);

        return timeSeries;
    }
}
