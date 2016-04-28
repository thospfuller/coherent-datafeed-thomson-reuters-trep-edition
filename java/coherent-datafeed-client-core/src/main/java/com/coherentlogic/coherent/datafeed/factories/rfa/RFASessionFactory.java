package com.coherentlogic.coherent.datafeed.factories.rfa;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.exceptions.
    SessionCreationFailedException;
import com.reuters.rfa.session.Session;

/**
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class RFASessionFactory implements TypedFactory<Session> {

    private final Session session;

    public RFASessionFactory (String sessionName) {
        this (Session.acquire(sessionName));
    }

    public RFASessionFactory (Session session) {

        if (session == null)
            throw new SessionCreationFailedException ("The session is null.");

        this.session = session;
    }

    @Override
    public Session getInstance() {
        return session;
    }
}
