package com.coherentlogic.coherent.datafeed.exceptions;


/**
 * An exception which is thrown when a value is out of bounds.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class ValueOutOfBoundsException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public ValueOutOfBoundsException(String msg) {
        super(msg);
    }

    public ValueOutOfBoundsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
