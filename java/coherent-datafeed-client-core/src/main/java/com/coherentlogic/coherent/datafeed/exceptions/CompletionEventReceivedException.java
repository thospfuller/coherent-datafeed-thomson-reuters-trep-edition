package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception which is thrown when an event of type
 * {@link com.reuters.rfa.common.Event.COMPLETION_EVENT} is received.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class CompletionEventReceivedException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public CompletionEventReceivedException(String message) {
        super(message);
    }

    public CompletionEventReceivedException(String message, Throwable cause) {
        super(message, cause);
    }
}
