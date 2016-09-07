package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * Thrown when an adapter is unable to convert an OMM message into an RFABean.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class AdaptFailedException extends FatalRuntimeException {

    private static final long serialVersionUID = -964332207845525228L;

    public AdaptFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AdaptFailedException(String msg) {
        super(msg);
    }
}
