package com.coherentlogic.coherent.datafeed.exceptions;

import com.coherentlogic.coherent.data.model.core.exceptions.ConversionFailedException;

/**
 * An exception that is thrown when the conversion from TR RFA-specific logic to the CL time series fails.
 *
 * @deprecated This exception is not being used.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesConversionFailedException extends ConversionFailedException {

    private static final long serialVersionUID = 6452486823180887239L;

    public TimeSeriesConversionFailedException(String msg) {
        super(msg);
    }

    public TimeSeriesConversionFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
