package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when an event's type is not known.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class UnknownEventTypeException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public UnknownEventTypeException(String msg) {
        super(msg);
    }

    public UnknownEventTypeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
