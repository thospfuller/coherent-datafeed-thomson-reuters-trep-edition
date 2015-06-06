package com.coherentlogic.coherent.datafeed.factories;

import javax.jms.ConnectionFactory;

//import org.hornetq.api.core.TransportConfiguration;
//import org.hornetq.api.jms.HornetQJMSClient;

/**
 * Factory class for creating JMS connection factories using the
 * {@link org.hornetq.api.jms.HornetQJMSClient HornetQJMSClient}.
 *
 * @deprecated Not sure we need this anymore.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class HornetQJMSConnectionFactoryFactory implements
    Factory<ConnectionFactory> {

//    private final ConnectionFactory connectionFactory;
//
//    public HornetQJMSConnectionFactoryFactory(
//        TransportConfiguration transportConfiguration) {
//        this.connectionFactory
//            = HornetQJMSClient.createConnectionFactory(transportConfiguration);
//    }

    @Override
    public ConnectionFactory getInstance() {
        return null;
    }
}
