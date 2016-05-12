package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the application id is invalid (ie. null or
 * an empty string).
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class InvalidApplicationIdException extends FatalRuntimeException {

    private static final long serialVersionUID = -7323571199844035282L;

    public InvalidApplicationIdException(String msg) {
        super(msg);
    }

    public InvalidApplicationIdException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
