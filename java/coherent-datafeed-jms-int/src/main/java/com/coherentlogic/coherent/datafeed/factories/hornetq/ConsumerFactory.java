package com.coherentlogic.coherent.datafeed.factories.hornetq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;

/**
 * Factory class for creating instances of {@link javax.jms.MessageConsumer} via
 * the session and destinations provided.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class ConsumerFactory implements TypedFactory<MessageConsumer> {

    private final MessageConsumer messageConsumer;

    public ConsumerFactory (Session session, Destination destination)
        throws JMSException {
        this.messageConsumer = session.createConsumer(destination);
    }

    @Override
    public MessageConsumer getInstance() {
        return messageConsumer;
    }
}
