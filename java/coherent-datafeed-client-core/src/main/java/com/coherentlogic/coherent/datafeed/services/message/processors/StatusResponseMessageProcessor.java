package com.coherentlogic.coherent.datafeed.services.message.processors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.adapters.StatusResponseAdapter;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Class is used to convert an {@link OMMItemEvent} into an instance of
 * {@link StatusResponse}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class StatusResponseMessageProcessor
    implements MessageProcessorSpecification<OMMItemEvent, StatusResponse> {

    private static final Logger log =
        LoggerFactory.getLogger(StatusResponseMessageProcessor.class);

    private final StatusResponseAdapter statusResponseAdapter;

    public StatusResponseMessageProcessor (
        StatusResponseAdapter statusResponseAdapter
    ) {
        this.statusResponseAdapter = statusResponseAdapter;
    }

    @Override
    public Message<StatusResponse> process(Message<OMMItemEvent> message) {

        log.info("statusResponseMessageProcessor.process: method begins; " +
            "message: " + message);

        MessageHeaders headers = message.getHeaders();

        OMMItemEvent itemEvent = message.getPayload();

        OMMMsg ommMsg = itemEvent.getMsg();

        StatusResponse statusResponse = statusResponseAdapter.adapt(ommMsg);

        log.info("statusResponse: "
            + ToStringBuilder.reflectionToString(statusResponse));

        Message<StatusResponse> result = MessageBuilder
            .withPayload(statusResponse)
            .copyHeaders(headers)
            .build();

        log.info("statusResponseMessageProcessor.process: method ends; result: "
            + result);

        return result;
    }
}
