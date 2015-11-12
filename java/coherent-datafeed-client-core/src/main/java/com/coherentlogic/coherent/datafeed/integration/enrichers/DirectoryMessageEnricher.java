package com.coherentlogic.coherent.datafeed.integration.enrichers;

import static com.coherentlogic.coherent.datafeed.misc.SessionUtils.getSession;
import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.integration.support.MessageBuilder;

import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Event;
import com.reuters.rfa.common.Handle;

/**
 * This class is used to enrich the message returned from the RFA api by using
 * the directory handle to find the associated session; this session is then
 * added to the message headers under the Constants.SESSION key.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DirectoryMessageEnricher extends AbstractMessageEnricher {

    private static final Logger log =
        LoggerFactory.getLogger(DirectoryMessageEnricher.class);

    public DirectoryMessageEnricher(
        Cache<Handle, Session> directoryCache
    ) {
        super(directoryCache);
    }

    public Message<Event> enrich (Message<Event> message) {

        log.info("enrich: method begins; message: " + message);

        Session session = getSession (message, getSessionCache());

        Message<Event> enrichedMessage =
            MessageBuilder
                .fromMessage(message)
                .setHeader(SESSION, session)
                .build ();

        log.info("enrich: method ends; enrichedMessage: " +
            enrichedMessage);

        return enrichedMessage;
    }
}
