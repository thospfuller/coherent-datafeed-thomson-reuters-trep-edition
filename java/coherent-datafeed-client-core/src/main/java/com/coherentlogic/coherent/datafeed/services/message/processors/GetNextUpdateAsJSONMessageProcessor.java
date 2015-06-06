package com.coherentlogic.coherent.datafeed.services.message.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import com.coherentlogic.coherent.datafeed.beans.TimeoutParameter;
import com.coherentlogic.coherent.datafeed.services.AsynchronouslyUpdatableSpecification;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;

/**
 * @todo Create specific extensions of this class as this is currently a raw
 *  type.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class GetNextUpdateAsJSONMessageProcessor<R>
    implements MessageProcessorSpecification<TimeoutParameter, String> {

    private static final Logger log =
        LoggerFactory.getLogger(GetNextUpdateAsJSONMessageProcessor.class);

    private final AsynchronouslyUpdatableSpecification<R>
        asynchronouslyUpdatableSpecification;

    public GetNextUpdateAsJSONMessageProcessor(
        AsynchronouslyUpdatableSpecification<R>
            asynchronouslyUpdatableSpecification
    ) {
        super();
        this.asynchronouslyUpdatableSpecification =
            asynchronouslyUpdatableSpecification;
    }

    @Override
    public Message<String> process(Message<TimeoutParameter> message) {

        log.info("process: method begins; message: " + message);

        TimeoutParameter timeoutParameter = message.getPayload();

        Long timeout = timeoutParameter.getTimeout();

        log.info("timeout: " + timeout);

        String result = asynchronouslyUpdatableSpecification.
            getNextUpdateAsJSON(timeout);

        log.info("result: " + result);

        Message<String> resultantMessage =
            MessageBuilder
                .withPayload(result)
                .build();

        log.info("process: method returns; resultantMessage: " +
            resultantMessage);

        return resultantMessage;
    }
}
