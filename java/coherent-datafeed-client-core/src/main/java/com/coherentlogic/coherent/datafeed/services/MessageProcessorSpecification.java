package com.coherentlogic.coherent.datafeed.services;

import org.springframework.messaging.Message;

/**
 * Specification for classes that will process messages from the Spring
 * Integration workflow. This method will process the message and then return
 * either another message or null.
 *
 * The intention is that this interface be used by services to facilitate the
 * connection between Spring Integration and the service.
 *
 * @param <X> The parameter type.
 * @param <Y> The return type.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface MessageProcessorSpecification<X, Y> {

    Message<Y> process (Message<X> message);
}
