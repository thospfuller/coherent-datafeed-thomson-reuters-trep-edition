package com.coherentlogic.coherent.datafeed.services;

import java.util.concurrent.CompletableFuture;

import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.reuters.rfa.common.Handle;

/**
 * The specification that defines the methods that time series services must
 * provide.
 *
 * Note that we do not call the {@link TimeSeriesService} directly as this would
 * not provide us with the complete set of functionality to load all of the rics
 * involved in a query. Instead we call a gateway into the Spring Integration
 * work flow, which is represented by this interface, which will query the
 * Thomson Reuters time series service and return the results.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface TimeSeriesGatewaySpecification {

    /**
     * Method queries the time series service passing the loginHandle, Reuters
     * Instrument Code (RIC), for the specified period.
     */
    CompletableFuture<TimeSeries> getTimeSeriesFor(
        String serviceName,
        Handle loginHandle,
        String ric,
        int period
    );

    CompletableFuture<TimeSeries> getTimeSeriesFor(
        String serviceName,
        Handle loginHandle,
        String ric,
        String period
    );
}
