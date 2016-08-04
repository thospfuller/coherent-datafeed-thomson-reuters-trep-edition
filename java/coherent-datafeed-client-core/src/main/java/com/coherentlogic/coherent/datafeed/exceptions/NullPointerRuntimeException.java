package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception which is thrown when the reference to an object is null.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class NullPointerRuntimeException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public NullPointerRuntimeException(String msg) {
        super(msg);
    }

    public NullPointerRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
