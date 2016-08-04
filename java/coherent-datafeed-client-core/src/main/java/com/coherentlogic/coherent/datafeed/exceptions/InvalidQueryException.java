package com.coherentlogic.coherent.datafeed.exceptions;


/**
 * An exception that is thrown when there's something wrong with the query.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class InvalidQueryException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidQueryException(String msg) {
        super(msg);
    }

    public InvalidQueryException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
