package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * A exception which is thrown when the cache returns a null reference when only
 * non-null references are ever valid.
 *
 * @deprecated This class is no longer being used.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class HandleNotFoundException extends IntegrationException {

    private static final long serialVersionUID = -9219222765076174513L;

    public HandleNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public HandleNotFoundException(String msg) {
        super(msg);
    }
}
