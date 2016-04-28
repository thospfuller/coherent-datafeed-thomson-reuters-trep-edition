package com.coherentlogic.coherent.datafeed.factories.hornetq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;

/**
 * Factory class for creating instances of {@link javax.jms.Session} from a
 * {@link javax.jms.Connection}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class SessionFactory implements TypedFactory<Session> {

    private final Session session;

    public SessionFactory (
        Connection connection,
        boolean transacted,
        int acknowledgeMode
    ) throws JMSException {
        this.session = connection.createSession(transacted, acknowledgeMode);
    }

    @Override
    public Session getInstance() {
        return session;
    }

    public void stop () throws JMSException {
        session.close();
    }
}
