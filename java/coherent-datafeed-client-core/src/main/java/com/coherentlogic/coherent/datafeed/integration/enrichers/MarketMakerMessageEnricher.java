package com.coherentlogic.coherent.datafeed.integration.enrichers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.reuters.rfa.common.Event;

/**
 * This class is used to enrich the message returned from the RFA api by using
 * the message payload handle to find the associated session; this session is
 * then added to the message headers under the Constants.SESSION key.
 *
 * @TODO: This should be made generic as it could be shared between the market
 *  price and market by order integration workflows.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketMakerMessageEnricher implements EnricherSpecification {

    private static final Logger log =
        LoggerFactory.getLogger(MarketMakerMessageEnricher.class);

    /**
     * @todo This method could be moved to a base class.
     */
    public Message<Event> enrich (Message<Event> message) {

        log.debug("debug: method begins; message: " + message);

        Message<Event> enrichedMessage =
            MessageBuilder
                .fromMessage(message)
                .build ();

        log.debug("enrich: method ends; enrichedMessage: " + enrichedMessage);

        return enrichedMessage;
    }
}
