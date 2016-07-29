package com.coherentlogic.coherent.datafeed.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.domain.StatusResponse;

/**
 * Class is used to receive {@link StatusResponse} messages.
 *
 * @todo With the introduction of the StatusResponseMessageProcessor this
 *  class is no longer necessary.
 *
 * @deprecated This class is no longer necessary (I think).
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <T> The type of object that this class is expected to receive.
 */
public class StatusResponseService
    implements StatusResponseServiceSpecification {

    private static final Logger log =
        LoggerFactory.getLogger(StatusResponseService.class);

//    private final BasicAdapter<StatusResponse, String> jsonGenerator;
//
//    private final MessageConsumer messageConsumer;
//
//    public StatusResponseService(
//        BasicAdapter<StatusResponse, String> jsonGenerator,
//        MessageConsumer messageConsumer
//    ) {
//        this.jsonGenerator = jsonGenerator;
//        this.messageConsumer = messageConsumer;
//    }

//    @Override
//    public StatusResponse getNextUpdate (Long timeout) {
//
//        Message nextMessage;
//
//        try {
//            nextMessage = messageConsumer.receive(timeout);
//        } catch (JMSException jmsException) {
//            throw new UpdateFailedException ("The next market price update " +
//                "was not received due to an exception being thrown while " +
//                "waiting to receive the next message.",
//                jmsException);
//        }
//
//        StatusResponse result = null;
//
//        if (nextMessage != null) {
//
//            ObjectMessage nextObjectMessage = (ObjectMessage) nextMessage;
//
//            try {
//                result = (StatusResponse) nextObjectMessage.getObject();
//                nextMessage.acknowledge();
//            } catch (JMSException jmsException) {
//                throw new UpdateFailedException ("The next object could not be " +
//                    "converted to an instance of StatusResponse.",
//                    jmsException);
//            }
//        }
//
//        log.debug("Next object: " + result);
//
//        return result;
//    }
//
//    @Override
//    public String getNextUpdateAsJSON(Long timeout) {
//
//        StatusResponse nextUpdate = getNextUpdate(timeout);
//
//        String result = jsonGenerator.adapt(nextUpdate);
//
//        log.debug("Next status response: " + result);
//
//        return result;
//    }
//
//    @Override
//    public String getNextUpdateAsJSON(String timeout) {
//        return getNextUpdateAsJSON(Long.valueOf(timeout));
//    }
}
