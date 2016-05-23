package com.coherentlogic.coherent.datafeed.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.exceptions.
    ApplicationInitializationFailedException;
import com.reuters.rfa.session.Session;

/**
 * Factory class for creating a single session using a sessionName string. The sessionName can be overridden as follows:
 * - By setting the CDATAFEED_SESSION_NAME system property (ie. -DCDATAFEED_SESSION_NAME="blahSession").
 * - By setting the CDATAFEED_SESSION_NAME environment variable.
 * 
 * -- if neither of these are set the sessionName will default to the sessionName property -- ie. "mySession".
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class RFASessionFactory implements TypedFactory<Session> {

    public static final String CDATAFEED_SESSION_NAME = "CDATAFEED_SESSION_NAME";

    private static final Logger log = LoggerFactory.getLogger(RFASessionFactory.class);

    private final String sessionName;

    public RFASessionFactory(String sessionName) {
        this.sessionName = sessionName;
    }

    /**
     * Method acquires a session with the given <i>sessionName<i>.
     */
    @Override
    public Session getInstance() {

        String sessionName = System.getProperty(CDATAFEED_SESSION_NAME);

        log.info("sessionName (system property): " + sessionName); 

        if (sessionName == null) {

            sessionName = System.getenv(CDATAFEED_SESSION_NAME);

            log.info("sessionName (environment variable): " + sessionName);
        }

        if (sessionName == null) {

            sessionName = this.sessionName;

            log.info("sessionName (default): " + sessionName);
        }

        Session session = Session.acquire(sessionName);

        if (session == null)
            throw new ApplicationInitializationFailedException("Cannot " +
                "acquire the session with the name '" + sessionName + "'.");

        return session;
    }
}
