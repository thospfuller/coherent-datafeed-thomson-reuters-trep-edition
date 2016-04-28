package com.coherentlogic.coherent.datafeed.factories;

import javax.jms.ConnectionFactory;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;

/**
 * Factory class for creating JMS connection factories using the
 * {@link org.hornetq.api.jms.HornetQJMSClient HornetQJMSClient}.
 *
 * @deprecated Not sure we need this anymore.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class HornetQJMSConnectionFactoryFactory implements TypedFactory<ConnectionFactory> {

    @Override
    public ConnectionFactory getInstance() {
        return null;
    }
}
