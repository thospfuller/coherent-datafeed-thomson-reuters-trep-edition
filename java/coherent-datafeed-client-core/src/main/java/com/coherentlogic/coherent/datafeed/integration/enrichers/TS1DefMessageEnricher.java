package com.coherentlogic.coherent.datafeed.integration.enrichers;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.common.Event;

/**
 * This class is used to enrich the message returned from the RFA api by using
 * the message payload handle to find the associated session; this session is
 * then added to the message headers under the Constants.SESSION key.
 *
 * @todo Consolidate all / most of these enrichers.
 * @todo Remove the ts1DefCache and replace it with a general cache.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefMessageEnricher implements EnricherSpecification {

    private static final Logger log = LoggerFactory.getLogger(TS1DefMessageEnricher.class);

    /**
     * @todo This method could be moved to a base class.
     */
    public Message<Event> enrich (Message<Event> message) {

        log.debug("enrich: method begins; message: " + message);

        Event event = message.getPayload();

        SessionBean sessionBean = (SessionBean) event.getClosure();

        Message<Event> enrichedMessage = null;

        enrichedMessage =
            MessageBuilder
                .fromMessage(message)
                .setHeader(SESSION, sessionBean)
                .build ();

        log.debug("enrich: method ends; enrichedMessage: " + enrichedMessage);

        return enrichedMessage;
    }
}
