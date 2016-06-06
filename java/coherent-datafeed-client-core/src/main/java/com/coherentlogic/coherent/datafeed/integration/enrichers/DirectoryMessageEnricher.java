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
 * the directory handle to find the associated session; this session is then
 * added to the message headers under the Constants.SESSION key.
 *
 * @deprecated This class can be combined with the other enrichers.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DirectoryMessageEnricher implements EnricherSpecification {

    private static final Logger log = LoggerFactory.getLogger(DirectoryMessageEnricher.class);

    public Message<Event> enrich (Message<Event> message) {

        log.debug("enrich: method begins; message: " + message);

        SessionBean session = (SessionBean) message.getPayload().getClosure();

        Message<Event> enrichedMessage =
            MessageBuilder.fromMessage(message)
                .setHeaderIfAbsent(SESSION, session)
                .build ();

        log.debug("enrich: method ends; enrichedMessage: " + enrichedMessage);

        return message;
    }
}
