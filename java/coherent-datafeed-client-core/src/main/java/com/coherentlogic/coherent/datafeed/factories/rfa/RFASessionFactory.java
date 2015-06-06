package com.coherentlogic.coherent.datafeed.factories.rfa;

import com.coherentlogic.coherent.datafeed.exceptions.
    SessionCreationFailedException;
import com.coherentlogic.coherent.datafeed.factories.Factory;
import com.reuters.rfa.session.Session;

/**
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class RFASessionFactory implements Factory<Session> {

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
