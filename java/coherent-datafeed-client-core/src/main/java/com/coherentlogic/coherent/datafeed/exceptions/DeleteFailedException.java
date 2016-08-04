package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that indicates that there was a problem when executing a delete
 * operation.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DeleteFailedException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public DeleteFailedException(String msg) {
        super(msg);
    }

    public DeleteFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
