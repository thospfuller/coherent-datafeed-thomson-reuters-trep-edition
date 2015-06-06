package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the application has received an event that
 * it does not know how to handle.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class UnexpectedEventReceivedException extends FatalRuntimeException {

    private static final long serialVersionUID = 1289290921192151719L;

    public UnexpectedEventReceivedException(String msg) {
        super(msg);
    }

    public UnexpectedEventReceivedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
