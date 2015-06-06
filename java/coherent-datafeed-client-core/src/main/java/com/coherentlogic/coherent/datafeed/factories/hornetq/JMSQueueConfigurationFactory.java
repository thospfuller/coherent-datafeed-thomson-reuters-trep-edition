package com.coherentlogic.coherent.datafeed.factories.hornetq;

import java.util.ArrayList;
import java.util.List;

import org.hornetq.jms.server.config.JMSConfiguration;
import org.hornetq.jms.server.config.JMSQueueConfiguration;
import org.hornetq.jms.server.config.impl.JMSConfigurationImpl;

import com.coherentlogic.coherent.datafeed.factories.Factory;

/**
 * Factory class used to create instances of the {@link JMSConfiguration} using
 * the QueueConfiguration(s) provided.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class JMSQueueConfigurationFactory implements Factory<JMSConfiguration> {

    /**
     * A list of QueueConfiguration objects which will be passed to the
     * new JMSConfiguration created by an instance of this class.
     */
    private final List<JMSQueueConfiguration> queueConfigurations;

    public JMSQueueConfigurationFactory (
        JMSQueueConfiguration queueConfiguration) {

        queueConfigurations
            = new ArrayList<JMSQueueConfiguration> ();

        queueConfigurations.add (queueConfiguration);
    }

    public JMSQueueConfigurationFactory (
        JMSQueueConfiguration queueConfigurationA,
        JMSQueueConfiguration queueConfigurationB
    ) {
        queueConfigurations
        = new ArrayList<JMSQueueConfiguration> ();

        queueConfigurations.add (queueConfigurationA);
        queueConfigurations.add (queueConfigurationB);
    }

    public JMSQueueConfigurationFactory (
        List<JMSQueueConfiguration> queueConfigurations) {
        this.queueConfigurations = queueConfigurations;
    }

    /**
     * Factory method for creating the JMSConfiguration.
     */
    public JMSConfiguration getInstance () {

        JMSConfiguration result = new JMSConfigurationImpl();

        List<JMSQueueConfiguration> resultantQueueConfigurations
            = result.getQueueConfigurations();

        for (JMSQueueConfiguration nextQueueConfiguration : queueConfigurations)
            resultantQueueConfigurations.add(nextQueueConfiguration);

        return result;
    }
}
