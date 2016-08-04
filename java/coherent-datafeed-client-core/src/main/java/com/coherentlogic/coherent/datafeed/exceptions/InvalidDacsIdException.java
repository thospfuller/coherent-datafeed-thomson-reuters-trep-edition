package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the dacs id is invalid (ie. null or an empty
 * string).
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class InvalidDacsIdException extends FatalRuntimeException {

    private static final long serialVersionUID = 3459927469243469849L;

    public InvalidDacsIdException(String msg) {
        super(msg);
    }

    public InvalidDacsIdException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
