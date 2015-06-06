package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the application fails to initialize.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class ClientNotInitializedException
    extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public ClientNotInitializedException(String msg) {
        super(msg);
    }

    public ClientNotInitializedException(
        String msg,
        Throwable cause
    ) {
        super(msg, cause);
    }
}
