package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception which is thrown when the session cannot be terminated, or when
 * the session is in an invalid state when attempting to terminate the session
 * -- for example when the user has called logout, without ever calling login.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class SessionFinalizationFailedException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public SessionFinalizationFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SessionFinalizationFailedException(String msg) {
        super(msg);
    }
}
