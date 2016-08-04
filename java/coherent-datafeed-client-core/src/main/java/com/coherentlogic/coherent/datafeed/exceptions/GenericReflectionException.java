package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception the wraps a parent reflection-specific exception.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class GenericReflectionException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public GenericReflectionException(String msg) {
        super(msg);
    }

    public GenericReflectionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
