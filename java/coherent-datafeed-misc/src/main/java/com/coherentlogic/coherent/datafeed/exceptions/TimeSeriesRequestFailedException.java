package com.coherentlogic.coherent.datafeed.exceptions;

import com.coherentlogic.coherent.data.model.core.exceptions.ConversionFailedException;

/**
 * An exception that is thrown when a request for time series data has failed. This is typically NOT for requesting a
 * RIC which does not exist -- rather the RIC is valid but an exception was thrown internally, for example when a time-
 * -out exception is wrapped and re-thrown.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesRequestFailedException extends ConversionFailedException {

    private static final long serialVersionUID = 7108058659188257856L;

    public TimeSeriesRequestFailedException(String msg) {
        super(msg);
    }

    public TimeSeriesRequestFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
