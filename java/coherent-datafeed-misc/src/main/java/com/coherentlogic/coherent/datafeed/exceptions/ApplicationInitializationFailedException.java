package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the application fails to initialize.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class ApplicationInitializationFailedException
    extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public ApplicationInitializationFailedException(String msg) {
        super(msg);
    }

    public ApplicationInitializationFailedException(
        String msg,
        Throwable cause
    ) {
        super(msg, cause);
    }
}
