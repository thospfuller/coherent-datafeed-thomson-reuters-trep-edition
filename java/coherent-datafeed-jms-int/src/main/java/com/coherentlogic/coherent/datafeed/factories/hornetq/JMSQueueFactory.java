package com.coherentlogic.coherent.datafeed.factories.hornetq;

import javax.jms.Queue;

import org.hornetq.api.jms.HornetQJMSClient;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;

/**
 * A Factory class for creating instances of {@link javax.jms.Queue Queue} via
 * the {@link org.hornetq.api.jms.HornetQJMSClient HornetQJMSClient}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class JMSQueueFactory implements TypedFactory<Queue> {

    private final Queue queue;

    public JMSQueueFactory (String queueName) {
        this.queue = HornetQJMSClient.createQueue(queueName);
    }

    @Override
    public Queue getInstance() {
        return queue;
    }
}
