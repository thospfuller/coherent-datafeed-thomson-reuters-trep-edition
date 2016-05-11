package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class ApplicationAlreadyRunningException extends FatalRuntimeException {

    private static final long serialVersionUID = -2124514974806600860L;

    public ApplicationAlreadyRunningException(String msg) {
        super(msg);
    }

    public ApplicationAlreadyRunningException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
