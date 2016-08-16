package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * Thrown when the period is not one of daily, weekly, or monthly.
 *
 * @see {@link com.reuters.ts1.TS1Constants#MONTHLY_PERIOD}
 * @see {@link com.reuters.ts1.TS1Series} The period is set here.
 * 
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UnknownPeriodException extends FatalRuntimeException {

    private static final long serialVersionUID = -6754254085989483522L;

    public UnknownPeriodException(String msg) {
        super(msg);
    }

    public UnknownPeriodException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
