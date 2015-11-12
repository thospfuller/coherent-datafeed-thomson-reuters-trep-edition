package com.coherentlogic.coherent.datafeed.integration.routers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.reuters.rfa.omm.OMMMsg;

/**
 * A router that inspects the OMMMsg's final flag -- if the flag is set to
 * true, then this indicates that no more messages will be sent and so we want
 * the workflow to route to the error channel, otherwise the workflow can
 * continue on the success channel.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class RouteOMMMsgByFinalFlag extends AbstractMessageRouter {

    private final MessageChannel
        successMessageChannel,
        errorMessageChannel;

    public RouteOMMMsgByFinalFlag(
        MessageChannel successMessageChannel,
        MessageChannel errorMessageChannel
    ) {
        super();
        this.successMessageChannel = successMessageChannel;
        this.errorMessageChannel = errorMessageChannel;
    }

    @Override
    protected Collection<MessageChannel> determineTargetChannels(
        Message<?> message) {

        OMMMsg msg = (OMMMsg) message.getPayload();

        List<MessageChannel> results = new ArrayList<MessageChannel> ();

        if (msg.isFinal())
            results.add(errorMessageChannel);
        else
            results.add(successMessageChannel);

        return results;
    }
}
