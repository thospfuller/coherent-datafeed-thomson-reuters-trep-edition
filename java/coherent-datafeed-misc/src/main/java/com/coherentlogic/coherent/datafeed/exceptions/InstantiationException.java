package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the process of instantiating a class fails.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class InstantiationException extends FatalRuntimeException {

    private static final long serialVersionUID = 8577715517104847975L;

    public InstantiationException(String msg) {
        super(msg);
    }

    public InstantiationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
