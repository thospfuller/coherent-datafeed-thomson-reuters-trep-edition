package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when a method has not been implemented.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MethodNotSupportedException extends FatalRuntimeException {

    private static final long serialVersionUID = -9080754021997829446L;

    public MethodNotSupportedException() {
        super("This method is not supported.");
    }

    public MethodNotSupportedException(String msg) {
        super(msg);
    }

    public MethodNotSupportedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
