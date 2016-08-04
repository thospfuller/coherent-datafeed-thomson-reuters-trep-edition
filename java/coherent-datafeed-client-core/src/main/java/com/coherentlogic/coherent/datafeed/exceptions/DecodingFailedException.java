package com.coherentlogic.coherent.datafeed.exceptions;

import com.coherentlogic.coherent.data.model.core.exceptions.ConversionFailedException;

/**
 * An exception that is thrown when decoding, for example a time series, has
 * failed.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DecodingFailedException extends ConversionFailedException {

    private static final long serialVersionUID = 1L;

    public DecodingFailedException(String msg) {
        super(msg);
    }

    public DecodingFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
