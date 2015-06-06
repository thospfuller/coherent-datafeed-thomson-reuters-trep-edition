package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when a timeout has expired. The cause of this
 * exception will typically be an instance of {@link InterruptedException}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeoutExpiredException extends FatalRuntimeException {

    private static final long serialVersionUID = 6687879785867278430L;

    public TimeoutExpiredException(String msg) {
        super(msg);
    }

    public TimeoutExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
