package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the data type is invalid -- for example we
 * expect an OMMTypes.MAP to be returned but what is actually returned is
 * something else.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class InvalidDataTypeException extends FatalRuntimeException {

    private static final long serialVersionUID = -7667462735193144220L;

    public InvalidDataTypeException(String msg) {
        super(msg);
    }

    public InvalidDataTypeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
