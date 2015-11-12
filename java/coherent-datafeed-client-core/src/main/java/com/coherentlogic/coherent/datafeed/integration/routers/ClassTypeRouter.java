package com.coherentlogic.coherent.datafeed.integration.routers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.router.AbstractMessageRouter;

/**
 * A router that uses the payload class type as the key and looks up the
 * corresponding value in the {@link #messageChannels} map and returns the
 * result.
 *
 * If no messageChannel is found in the map the {@link #errorChannel} is
 * returned.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class ClassTypeRouter extends AbstractMessageRouter {

    private static final Logger log =
        LoggerFactory.getLogger(ClassTypeRouter.class);

    private final Map<Class<?>, MessageChannel> messageChannels;

    private final MessageChannel errorChannel;

    public ClassTypeRouter(
        Map<Class<?>, MessageChannel> messageChannels,
        MessageChannel errorChannel
    ) {
        this.messageChannels = messageChannels;
        this.errorChannel = errorChannel;
    }

    @Override
    protected Collection<MessageChannel> determineTargetChannels(
        Message<?> message
    ) {
        Object payload = message.getPayload();

        Class<?> clazz = payload.getClass();

        MessageChannel messageChannel = messageChannels.get(clazz);

        if (messageChannel == null)
            messageChannel = errorChannel;

        log.info("Adding the following channel: " + messageChannel);

        List<MessageChannel> messageChannels = new ArrayList<MessageChannel>();

        messageChannels.add(messageChannel);

        return messageChannels;
    }
}
