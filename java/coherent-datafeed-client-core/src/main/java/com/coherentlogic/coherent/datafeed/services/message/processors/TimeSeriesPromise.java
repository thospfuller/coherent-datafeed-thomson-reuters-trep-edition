package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.concurrent.CompletableFuture;

import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.reuters.rfa.common.Handle;

/**
 * A promise that includes the TimeSeries as well as the handle which is associated with it.
 *
 * @see TransformTimeSeriesMessageProcessor (IMPORTANT)
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesPromise extends CompletableFuture<TimeSeries> {

    private final Handle handle;

    public TimeSeriesPromise(Handle handle) {
        super();
        this.handle = handle;
    }

    public Handle getHandle() {
        return handle;
    }
}
