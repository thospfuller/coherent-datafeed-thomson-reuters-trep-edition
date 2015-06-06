package com.coherentlogic.coherent.datafeed.factories.hornetq;

import javax.jms.Topic;

import org.hornetq.api.jms.HornetQJMSClient;

import com.coherentlogic.coherent.datafeed.factories.Factory;

/**
 * A Factory class for creating instances of {@link javax.jms.Topic Topic} via
 * the {@link org.hornetq.api.jms.HornetQJMSClient HornetQJMSClient}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class JMSTopicFactory implements Factory<Topic> {

    private final Topic topic;

    public JMSTopicFactory (String topicName) {
        this.topic = HornetQJMSClient.createTopic(topicName);
    }

    @Override
    public Topic getInstance() {
        return topic;
    }
}
