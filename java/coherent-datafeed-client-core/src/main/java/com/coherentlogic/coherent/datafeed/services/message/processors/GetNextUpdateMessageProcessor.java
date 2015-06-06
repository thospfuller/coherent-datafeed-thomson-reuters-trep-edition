package com.coherentlogic.coherent.datafeed.services.message.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import com.coherentlogic.coherent.datafeed.beans.TimeoutParameter;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.services.AsynchronouslyUpdatableSpecification;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetNextUpdateMessageProcessor<R extends RFABean>
    implements MessageProcessorSpecification<TimeoutParameter, R> {

    private static final Logger log =
        LoggerFactory.getLogger(GetNextUpdateMessageProcessor.class);

    private final AsynchronouslyUpdatableSpecification<R>
        asynchronouslyUpdatableSpecification;

    public GetNextUpdateMessageProcessor(
        AsynchronouslyUpdatableSpecification<R>
            asynchronouslyUpdatableSpecification
    ) {
        super();
        this.asynchronouslyUpdatableSpecification =
            asynchronouslyUpdatableSpecification;
    }

    @Override
    public Message<R> process(Message<TimeoutParameter> message) {

        log.info("process: method begins; message: " + message);

        TimeoutParameter timeoutParameter = message.getPayload();

        Long timeout = timeoutParameter.getTimeout();

        log.info("timeout: " + timeout);

        R result = asynchronouslyUpdatableSpecification.
            getNextUpdate (timeout);

        log.info("result: " + result);

        Message<R> resultantMessage = null;

        if (result != null)
            resultantMessage = MessageBuilder
                .withPayload(result)
                .build();

        log.info("process: method returns; resultantMessage: " +
            resultantMessage);

        return resultantMessage;
    }
}
