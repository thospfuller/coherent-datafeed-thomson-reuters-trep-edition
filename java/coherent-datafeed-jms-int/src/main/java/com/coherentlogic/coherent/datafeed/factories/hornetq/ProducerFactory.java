package com.coherentlogic.coherent.datafeed.factories.hornetq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;

/**
 * Factory class for creating instances of {@link javax.jms.MessageProducer}
 * via the session and destination provided.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class ProducerFactory implements TypedFactory<MessageProducer> {

    private final MessageProducer messageProducer;

    public ProducerFactory(Session session, Destination destination)
        throws JMSException {
        this.messageProducer = session.createProducer(destination);
    }

    @Override
    public MessageProducer getInstance() {
        return messageProducer;
    }

    public void stop () throws JMSException {
        messageProducer.close();
    }
}
