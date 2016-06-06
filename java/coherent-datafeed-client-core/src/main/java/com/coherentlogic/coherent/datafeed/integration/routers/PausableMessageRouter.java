package com.coherentlogic.coherent.datafeed.integration.routers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.coherentlogic.coherent.datafeed.services.FlowInverterService;

/**
 * A message router that can be paused when another thread needs to complete a
 * task
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class PausableMessageRouter extends AbstractMessageRouter {

    private final FlowInverterService pauseResumeService;

    private final MessageChannel successChannel;

    private final MessageChannel failChannel;

    public PausableMessageRouter(
        MessageChannel successChannel,
        MessageChannel failChannel
    ) {
        this (new FlowInverterService(), successChannel, failChannel);
    }

    public PausableMessageRouter(
        FlowInverterService pauseResumeService,
        MessageChannel successChannel,
        MessageChannel failChannel) {
        this.pauseResumeService = pauseResumeService;
        this.successChannel = successChannel;
        this.failChannel = failChannel;
    }

    @Override
    protected Collection<MessageChannel> determineTargetChannels(
        Message<?> message) {

        Collection<MessageChannel> result = new ArrayList<MessageChannel> ();

        pauseResumeService.reset();

        // Method returns when either the RFA thread calls resume or the timeout
        // expires; if RFA (TR) indicates the login has failed or the timeout
        // expires, success will be false and the failChannel will be returned.
        boolean success = pauseResumeService.pause();

        if (success)
            result.add(successChannel);
        else
            result.add(failChannel);

        return result;
    }

    public void reset () {
        pauseResumeService.reset();
    }
}
