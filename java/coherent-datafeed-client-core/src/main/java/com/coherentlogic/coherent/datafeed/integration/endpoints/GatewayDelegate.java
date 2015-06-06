package com.coherentlogic.coherent.datafeed.integration.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.gateway.MessagingGatewaySupport;

/**
 * The purpose of the RFA GatewayDelegate is to continue the integration
 * workflow; it achieves this by exposing the <i>send</i> method.
 * 
 * @see org.springframework.integration.jms.ChannelPublishingJmsMessageListener
 *  -- the GatewayDelegate is implemented in this class (as well as others --
 *  for example, the MarshallingWebServiceInboundGateway has this as well.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class GatewayDelegate extends MessagingGatewaySupport {

    private static final Logger log =
        LoggerFactory.getLogger(GatewayDelegate.class);

    @Override
    public String getComponentType () {
        return EventDrivenEndpoint.RFA_EVENT_DRIVEN_ENDPOINT;
    }

    @Override
    public void send (Object object) {
        try {
            super.send (object);
        } catch (RuntimeException runtimeException) {
            log.error("Unable to send the object: " + object, runtimeException);
            throw runtimeException;
        }
    }
}
