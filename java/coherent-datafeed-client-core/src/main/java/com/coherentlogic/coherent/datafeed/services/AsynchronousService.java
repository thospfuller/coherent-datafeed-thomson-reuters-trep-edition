package com.coherentlogic.coherent.datafeed.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.coherentlogic.coherent.datafeed.exceptions.UpdateFailedException;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.reuters.rfa.common.Client;

/**
 * Front-end for the Thomson Reuters asynchronous services.
 *
 * @param R The type of object the jsonGenerator accepts.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public abstract class AsynchronousService<R>
    extends QueryableService {

    private static final Logger log =
        LoggerFactory.getLogger(AsynchronousService.class);

    private final MessageConsumer messageConsumer;

    private final BasicAdapter<R, String> jsonGenerator;

    public AsynchronousService(
        String serviceName,
        short msgModelType,
        RequestMessageBuilderFactory factory,
        Client client,
        MessageConsumer messageConsumer,
        BasicAdapter<R, String> jsonGenerator
    ) {
        super(
            serviceName,
            msgModelType,
            factory,
            client
        );
        this.messageConsumer = messageConsumer;
        this.jsonGenerator = jsonGenerator;
    }

    public R getNextUpdate (Long timeout) {

        log.info("getNextUpdate: method begins; timeout: " + timeout);

        Message nextMessage;

        try {
            nextMessage = messageConsumer.receive(timeout);
        } catch (JMSException jmsException) {
            throw new UpdateFailedException ("The next update " +
                "was not received due to an exception being thrown while " +
                "waiting to receive the next message.",
                jmsException);
        }

        R result = null;

        if (nextMessage != null) {

            ObjectMessage nextObjectMessage = (ObjectMessage) nextMessage;

            try {
                result = (R) nextObjectMessage.getObject();
                nextMessage.acknowledge();
            } catch (JMSException jmsException) {
                throw new UpdateFailedException ("The next object could " +
                    "not be converted to a market price.", jmsException);
            }
        }

        log.info("getNextUpdate: method ends; result: " + result);

        return result;
    }

    public String getNextUpdateAsJSON (String timeout) {

        log.info("getNextUpdateAsJSON: method begins; timeout (String): " +
            timeout);

        return getNextUpdateAsJSON (
            Long.valueOf(timeout)
        );
    }

    public String getNextUpdateAsJSON (Long timeout) {

        log.info("getNextUpdateAsJSON: method begins; timeout (Long): " +
            timeout);

        R nextUpdate = getNextUpdate(timeout);

        String result = jsonGenerator.adapt(nextUpdate);

        log.debug("Next result: " + result);

        return result;
    }
}
