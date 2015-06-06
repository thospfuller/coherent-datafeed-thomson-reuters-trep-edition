package com.coherentlogic.coherent.datafeed.integration.endpoints;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.history.TrackableComponent;
import org.springframework.integration.support.MessageBuilder;

import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Event;

/**
 * An endpoint which receives messages from an RFA client, converts them to
 * Spring Integration Messages, and then starts the integration workflow.
 *
 * Note that events are routed by their type, so this service will extract the
 * event type from the event, convert the value into a String, and then put the
 * result in the message header keyed using the Constants.EVENT_TYPE.
 *
 * @see org.springframework.integration.jms.JmsMessageDrivenEndpoint
 *
 @todo Combine this class with the other EDE so that there is a common base
 *  class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class EventDrivenEndpoint extends AbstractEndpoint
    implements Client, TrackableComponent {

    private final AtomicLong eventCounter = new AtomicLong (0);

    private static final Logger log =
        LoggerFactory.getLogger(EventDrivenEndpoint.class);

    public static final long ONE = 1L;

    static final String
        RFA_EVENT_DRIVEN_ENDPOINT = "rfa:event-driven-endpoint",
        SEND_FAILED_MESSAGE = "Send failed -- see exception details.";

    /**
     * Used to send the message.
     */
    private final GatewayDelegate gatewayDelegate;

    public EventDrivenEndpoint () {
        gatewayDelegate = new GatewayDelegate ();
    }

    /**
     * Constructor that takes an instance of {@link GatewayDelegate}. Note that
     * this constructor has package scope as setting this to public *may* cause
     * Proguard to have problems obfuscating this class.
     */
    EventDrivenEndpoint (GatewayDelegate gatewayDelegate) {
        this.gatewayDelegate = gatewayDelegate;
    }

    /**
     * Callback for RFA events which takes the event, converts it into a Spring
     * Integration message and continues the workflow.
     */
    @Override
    public void processEvent(Event event) {

        log.info("Received event number " + eventCounter.addAndGet(ONE)
            + " at time " + System.currentTimeMillis() + "; event: " + event);

        Message<Event> message = MessageBuilder
            .withPayload(event)
            .build();

        try {
            gatewayDelegate.send(message);
            // We'll eventually remove this catch and just let the exception go
            // but for now we will log the exception and re-throw it.
        } catch (RuntimeException runtimeException) {
            String text = "Unable to send the message " + message;
            log.error(text, runtimeException);
            throw runtimeException;
        }
    }

    @Override
    protected void doStart() {
        gatewayDelegate.start();
    }

    @Override
    protected void doStop() {
        gatewayDelegate.stop();
    }

    @Override
    public String getComponentType() {
        return RFA_EVENT_DRIVEN_ENDPOINT;
    }

    @Override
    public void setShouldTrack(boolean shouldTrack) {
        gatewayDelegate.setShouldTrack(shouldTrack);
    }

    public void setComponentName(String componentName){
        this.gatewayDelegate.setComponentName(componentName);
    }

    public void setRequestChannel(MessageChannel requestChannel){
        this.gatewayDelegate.setRequestChannel(requestChannel);
    }

    public void setReplyChannel(MessageChannel replyChannel){
        this.gatewayDelegate.setReplyChannel(replyChannel);
    }

    public void setErrorChannel(MessageChannel errorChannel){
        this.gatewayDelegate.setErrorChannel(errorChannel);
    }
}
