package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that indicates that there was a problem when executing an add
 * operation.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class AddFailedException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public AddFailedException(String msg) {
        super(msg);
    }

    public AddFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
