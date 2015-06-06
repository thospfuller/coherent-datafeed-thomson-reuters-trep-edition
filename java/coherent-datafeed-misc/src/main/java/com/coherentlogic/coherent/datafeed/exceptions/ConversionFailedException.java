package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when a conversion has failed.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class ConversionFailedException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public ConversionFailedException(String msg) {
        super(msg);
    }

    public ConversionFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
