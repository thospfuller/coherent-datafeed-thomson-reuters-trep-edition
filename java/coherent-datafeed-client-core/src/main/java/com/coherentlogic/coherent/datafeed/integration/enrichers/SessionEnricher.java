package com.coherentlogic.coherent.datafeed.integration.enrichers;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.exceptions.ApplicationInitializationFailedException;
import com.reuters.rfa.common.Event;

/**
 * A service that adds a new {@link SessionBean} to the message under the
 * {@link com.coherentlogic.coherent.datafeed.misc.Constants#SESSION} key.
 *
 * @throws ApplicationInitializationFailedException Since this service should be
 *  called at the beginning of the workflow, the expectation is that the session
 *  will not have been added to the headers -- if this has happened then this is
 *  considered to be invalid and an exception is thrown.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class SessionEnricher implements EnricherSpecification {

    @Override
    public Message<Event> enrich(Message<Event> message) {

        Event event = message.getPayload();

        SessionBean sessionBean = (SessionBean) event.getClosure();

        assertNotNull ("sessionBean", sessionBean);

        Message<Event> result = MessageBuilder.fromMessage(message).setHeaderIfAbsent(SESSION, sessionBean).build();

        return result;
    }
}
