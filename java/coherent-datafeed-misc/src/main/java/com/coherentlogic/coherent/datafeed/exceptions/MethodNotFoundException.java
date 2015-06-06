package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception which is thrown when a method is not found on an object.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class MethodNotFoundException extends GenericReflectionException {

    private static final long serialVersionUID = 1L;

    public MethodNotFoundException(String msg) {
        super(msg);
    }

    public MethodNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
