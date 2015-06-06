package com.coherentlogic.coherent.datafeed.factories.hornetq;

import java.util.ArrayList;
import java.util.List;

import org.hornetq.jms.server.config.JMSConfiguration;
import org.hornetq.jms.server.config.TopicConfiguration;
import org.hornetq.jms.server.config.impl.JMSConfigurationImpl;

import com.coherentlogic.coherent.datafeed.factories.Factory;

/**
 * Factory class used to create instances of the {@link JMSConfiguration} using
 * the TopicConfiguration(s) provided.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class JMSTopicConfigurationFactory implements Factory<JMSConfiguration> {

    /**
     * A list of QueueConfiguration objects which will be passed to the
     * new JMSConfiguration created by an instance of this class.
     */
    private final List<TopicConfiguration> topicConfigurations;

    public JMSTopicConfigurationFactory (
        TopicConfiguration topicConfiguration) {

        topicConfigurations
            = new ArrayList<TopicConfiguration> ();

        topicConfigurations.add (topicConfiguration);
    }

    protected JMSTopicConfigurationFactory (
        TopicConfiguration topicConfigurationA,
        TopicConfiguration topicConfigurationB
    ) {
        topicConfigurations
            = new ArrayList<TopicConfiguration> ();

        topicConfigurations.add (topicConfigurationA);
        topicConfigurations.add (topicConfigurationB);
    }

    protected JMSTopicConfigurationFactory (
        List<TopicConfiguration> topicConfigurations) {
        this.topicConfigurations = topicConfigurations;
    }

    /**
     * Factory method for creating the JMSConfiguration.
     */
    public JMSConfiguration getInstance () {

        JMSConfiguration result = new JMSConfigurationImpl();

        List<TopicConfiguration> resultantTopicConfigurations
            = result.getTopicConfigurations();

        for (TopicConfiguration nextTopicConfiguration : topicConfigurations)
            resultantTopicConfigurations.add(nextTopicConfiguration);

        return result;
    }
}
