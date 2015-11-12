package com.coherentlogic.coherent.datafeed.integration.enrichers;

import org.springframework.messaging.Message;

import com.reuters.rfa.common.Event;

/**
 * A specification for classes that enrich Spring Integration Messages.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface EnricherSpecification {

    Message<Event> enrich (Message<Event> message);
}
