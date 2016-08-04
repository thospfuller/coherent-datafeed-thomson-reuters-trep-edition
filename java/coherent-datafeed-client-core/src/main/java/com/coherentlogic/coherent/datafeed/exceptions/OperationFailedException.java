package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when an operation, such as login or query, has
 * failed.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OperationFailedException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public OperationFailedException(String msg) {
        super(msg);
    }

    public OperationFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
