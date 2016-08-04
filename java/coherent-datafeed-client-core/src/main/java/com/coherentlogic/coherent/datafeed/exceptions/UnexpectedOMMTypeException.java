package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the type of an OMM object differs from what
 * is expected.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class UnexpectedOMMTypeException extends FatalRuntimeException {

    private static final long serialVersionUID = -7107515936834324534L;

    public UnexpectedOMMTypeException(String msg) {
        super(msg);
    }

    public UnexpectedOMMTypeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
