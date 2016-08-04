package com.coherentlogic.coherent.datafeed.exceptions;

import org.springframework.jms.JmsException;

/**
 * An exception that wraps a Spring JmsException and is used to indicate that
 * there was a problem in the QuoteQueue.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class QuoteQueueException extends JmsException {

    private static final long serialVersionUID = 1L;

    public QuoteQueueException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public QuoteQueueException(String msg) {
        super(msg);
    }

    public QuoteQueueException(Throwable cause) {
        super(cause);
    }
}
