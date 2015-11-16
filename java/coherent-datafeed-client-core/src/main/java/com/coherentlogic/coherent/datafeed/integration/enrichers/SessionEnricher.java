package com.coherentlogic.coherent.datafeed.integration.enrichers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.services.Session;

/**
 * A service that adds a new {@link Session} to the message under the
 * {@link com.coherentlogic.coherent.datafeed.misc.Constants#SESSION} key.
 *
 * @throws ApplicationInitializationFailedException Since this service should be
 *  called at the beginning of the workflow, the expectation is that the session
 *  will not have been added to the headers -- if this has happened then this is
 *  considered to be invalid and an exception is thrown.
 *
 * @deprecated This works with only one session and we're in the process of
 *  setting this up such that we can work with any number of sessions.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class SessionEnricher {

    private static final Logger log =
        LoggerFactory.getLogger(SessionEnricher.class);

    private final Session session;

    public SessionEnricher(Session session) {
        super();
        this.session = session;
    }

    public Message<Session> addDacsIdToSession (Message<String> message) {
        String dacsId = message.getPayload();

        log.info("dacsId: " + dacsId);

        session.setDacsId(dacsId);

        Message<Session> result = MessageBuilder
            .withPayload(session).build();

        return result;
    }

    public Message<Session> addSessionToPayload (Message<?> message) {

        log.debug ("session " + session);

        Message<Session> result = MessageBuilder
            .withPayload(session).build();

        return result;
    }

    public Message<Session> addSessionToMessageHeaders (Message<?> message) {

        log.debug ("session " + session);

        Message<Session> result = MessageBuilder
            .withPayload(session).build();

        return result;
    }
}

//.setHeader(SESSION, session).build();
//
//MessageHeaders headers = message.getHeaders();
//
//Session currentSession = (Session) headers.get(SESSION);
//
//if (currentSession != null)
//  throw new ApplicationInitializationFailedException (
//      "The session has already been added to the message headers " +
//      "and, at this point, the expectation is that this would be " +
//      "null.");
//
//Session session = new Session ();
//
//headers.put(SESSION, session);
