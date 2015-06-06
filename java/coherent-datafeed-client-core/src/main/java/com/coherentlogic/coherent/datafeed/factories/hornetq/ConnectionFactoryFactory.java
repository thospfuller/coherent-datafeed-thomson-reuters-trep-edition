package com.coherentlogic.coherent.datafeed.factories.hornetq;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.jms.client.HornetQConnectionFactory;

import com.coherentlogic.coherent.datafeed.factories.Factory;

/**
 * A factory class for creating instances of {@link javax.jms.ConnectionFactory}
 * via the {@link org.hornetq.api.jms.HornetQJMSClient} class.
 *
 * Note that currently we're using this for topic-based queues only (see line
 * 109 in the hornetq-beans.xml file.
 *
 * @deprecated This class may be removed as with the upgrade to HornetQ version
 *  2.2.21.Final HornetQ no longer has support for an implementation of
 *  javax.jms.ConnectionFactory that appears to be available.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class ConnectionFactoryFactory implements Factory<HornetQConnectionFactory> {

    private final HornetQConnectionFactory connectionFactory;

    /**
     * 
     */
    public ConnectionFactoryFactory (
        JMSFactoryType factoryType,
        TransportConfiguration transportConfiguration) {

            HornetQConnectionFactory hornetQConnectionFactory
                = HornetQJMSClient.createConnectionFactoryWithoutHA(
                    factoryType, transportConfiguration);

//            HornetQJMSClient.createConnectionFactory(transportConfiguration);
//
            this.connectionFactory = hornetQConnectionFactory;
    }

    @Override
    public HornetQConnectionFactory getInstance() {
//        throw new RuntimeException ("FEEBAR!");
        return connectionFactory;
    }
}
