package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class InvalidStateException extends FatalRuntimeException {

    public InvalidStateException(String msg) {
        super(msg);
    }

    public InvalidStateException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
