package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when a null reference is expected however a
 * non-null reference already exists. This is useful when we have a situation
 * such as a market price refresh event where the handle returns a reference to
 * an existing MarketPrice instance. Realistically this should not be possible
 * however if it happens we need to know about it.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class NonNullReferenceExpectedException extends FatalRuntimeException {

    private static final long serialVersionUID = 7636942567999711403L;

    public NonNullReferenceExpectedException(String msg) {
        super(msg);
    }

    public NonNullReferenceExpectedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
