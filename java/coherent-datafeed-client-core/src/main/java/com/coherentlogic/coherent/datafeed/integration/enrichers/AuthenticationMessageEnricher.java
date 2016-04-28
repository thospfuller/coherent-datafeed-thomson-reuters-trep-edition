package com.coherentlogic.coherent.datafeed.integration.enrichers;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.SessionUtils.getSession;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Event;
import com.reuters.rfa.common.Handle;

/**
 * This class is used to populate the Spring Integration message headers with
 * the session associated with the event's handle.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AuthenticationMessageEnricher extends AbstractMessageEnricher {

    private static final Logger log =
        LoggerFactory.getLogger(AuthenticationMessageEnricher.class);

    public AuthenticationMessageEnricher(
        Cache<Handle, Session> sessionCache
    ) {
        super(sessionCache);
    }

    @Transactional
    public Message<Event> enrich (Message<Event> message) {

        Message<Event> enrichedMessage = null;

        Cache<Handle, Session> cache = getSessionCache();

        /* Note that it is possible that this method is invoked before the
         * AuthenticationService.process method completes adding the session to
         * the cache and if this happens we'll end up with a NPE as the workflow
         * progresses. The solution is to sync here and in the
         * AuthenticationService process method.
         *
         * @TODO: Investigate using transactions and the cache lock method as an
         *  alternative.
         */
        synchronized (cache) {

            log.info("enrich: method begins; message: " + message);

            Session session = getSession(message, cache);

            enrichedMessage =
                MessageBuilder
                    .fromMessage(message)
                    .setHeader(SESSION, session)
                    .build ();

            log.info("enrich: method ends; enrichedMessage: " +
                enrichedMessage);
        }
        return enrichedMessage;
    }
}
