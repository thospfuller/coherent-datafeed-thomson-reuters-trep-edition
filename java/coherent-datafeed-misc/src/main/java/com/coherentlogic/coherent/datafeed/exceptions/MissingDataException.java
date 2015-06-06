package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that indicates that data was missing at some point in an
 * integration workflow.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class MissingDataException extends IntegrationException {

    private static final long serialVersionUID = 1L;

    public MissingDataException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MissingDataException(String msg) {
        super(msg);
    }
}
