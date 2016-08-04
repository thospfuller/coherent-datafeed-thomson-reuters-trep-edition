package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that all integration-related exceptions should inherit from.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class IntegrationException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public IntegrationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IntegrationException(String msg) {
        super(msg);
    }
}
