package com.coherentlogic.coherent.datafeed.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.exceptions.UnexpectedEventReceivedException;

/**
 * At the moment we're ignoring COMPLETION_EVENTS when authenticating so this
 * service will intercept these messages and log them.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UnexpectedEventReceiveHandler {

    private static final Logger log =
        LoggerFactory.getLogger(UnexpectedEventReceiveHandler.class);

    public void handle (Object event) {
        throw new UnexpectedEventReceivedException (
            "An unexpected event was received: " + event);
    }
}
