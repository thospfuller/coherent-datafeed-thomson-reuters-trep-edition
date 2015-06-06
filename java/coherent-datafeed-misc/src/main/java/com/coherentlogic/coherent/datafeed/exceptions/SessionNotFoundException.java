package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the session is null and a valid reference is
 * expected.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class SessionNotFoundException extends FatalRuntimeException {

    private static final long serialVersionUID = 6682937520946042979L;

    public SessionNotFoundException(String msg) {
        super(msg);
    }

    public SessionNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
