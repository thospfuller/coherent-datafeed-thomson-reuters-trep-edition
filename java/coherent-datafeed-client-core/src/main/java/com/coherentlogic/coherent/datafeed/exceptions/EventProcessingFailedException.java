package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the event processing fails for any reason.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class EventProcessingFailedException extends FatalRuntimeException {


    private static final long serialVersionUID = 1L;

    public EventProcessingFailedException(String msg) {
        super(msg);
    }

    public EventProcessingFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
