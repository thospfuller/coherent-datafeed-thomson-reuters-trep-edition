package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that indicates that there was a problem when executing an update
 * operation.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UpdateFailedException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public UpdateFailedException(String msg) {
        super(msg);
    }

    public UpdateFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
