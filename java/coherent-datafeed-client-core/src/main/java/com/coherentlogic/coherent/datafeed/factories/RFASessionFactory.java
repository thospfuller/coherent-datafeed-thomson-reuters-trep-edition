package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.datafeed.exceptions.
    ApplicationInitializationFailedException;
import com.reuters.rfa.session.Session;

/**
 * Factory class for creating sessions using a sessionName string.
 *
 * @TODO Should we have start/stop methods map to acquire/release?
 * @TODO Rename this class to RFASessionFactory.
 * @todo See .rfa.SessionFactory -- is this the same as this class?
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class RFASessionFactory implements Factory<Session> {

    private final String sessionName;

    public RFASessionFactory(String sessionName) {
        super();
        this.sessionName = sessionName;
    }

    /**
     * Method acquires a session with the given <i>sessionName<i>.
     */
    @Override
    public Session getInstance() {
        Session session = Session.acquire(sessionName);

        if (session == null)
            throw new ApplicationInitializationFailedException("Cannot " +
                "acquire the session with the name '" + sessionName + "'.");

        return session;
    }
}
