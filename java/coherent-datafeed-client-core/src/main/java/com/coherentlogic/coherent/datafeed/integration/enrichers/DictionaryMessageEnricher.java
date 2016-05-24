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
 * the dictionary handle to find the associated session; this session is then
 * added to the message headers under the Constants.SESSION key.
 *
 * @TODO We should be able to combine these enrichers into one class with m
 *  instances.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DictionaryMessageEnricher extends AbstractMessageEnricher {

    private static final Logger log =
        LoggerFactory.getLogger(DictionaryMessageEnricher.class);

    private DictionaryMessageEnricher(
        Cache<Handle, Session> dictionaryCache
    ) {
        super(dictionaryCache);
    }

    public Message<Event> enrich (Message<Event> message) {

        log.debug("enrich: method begins; message: " + message);

        Session session = getSession(message, getSessionCache());

        Message<Event> enrichedMessage =
            MessageBuilder
                .fromMessage(message)
                .setHeader(SESSION, session)
                .build ();

        log.debug("enrich: method ends; enrichedMessage: " +
            enrichedMessage);

        return enrichedMessage;
    }
}
