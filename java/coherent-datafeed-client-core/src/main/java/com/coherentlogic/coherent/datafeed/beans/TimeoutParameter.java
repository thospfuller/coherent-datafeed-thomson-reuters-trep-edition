package com.coherentlogic.coherent.datafeed.beans;

/**
 * This class is used in the Spring Integration workflow and represents the
 * single timeout parameter that some methods require -- for example see the
 * getNextUpdateAsJSON method.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeoutParameter {

    private final Long timeout;

    public TimeoutParameter (Long timeout) {
        this.timeout = timeout;
    }

    public TimeoutParameter (String timeout) {
        this (Long.valueOf(timeout));
    }

    public Long getTimeout() {
        return timeout;
    }
}
