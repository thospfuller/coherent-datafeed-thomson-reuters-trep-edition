package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception which is re-thrown when an exception is thrown from a call to
 * the EventQueue's <i>dispatch</i> method.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class EventQueueDispatchException extends FatalRuntimeException {

    private static final long serialVersionUID = 1L;

    public EventQueueDispatchException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EventQueueDispatchException(String msg) {
        super(msg);
    }
}
