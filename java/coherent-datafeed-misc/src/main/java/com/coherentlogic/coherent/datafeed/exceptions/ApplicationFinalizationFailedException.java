package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the application fails to stop.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class ApplicationFinalizationFailedException
    extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public ApplicationFinalizationFailedException(String msg) {
        super(msg);
    }

    public ApplicationFinalizationFailedException(
        String msg,
        Throwable cause
    ) {
        super(msg, cause);
    }
}
