package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.adapters.StatusResponseAdapter;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.coherentlogic.coherent.datafeed.domain.StatusResponseBean;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Class is used to convert an {@link OMMItemEvent} into an instance of
 * {@link StatusResponse}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class StatusResponseMessageProcessor
    implements MessageProcessorSpecification<OMMItemEvent, StatusResponseBean> {

    private static final Logger log =
        LoggerFactory.getLogger(StatusResponseMessageProcessor.class);

    private final StatusResponseAdapter statusResponseAdapter;

    private final Map<Handle, String> ricCache;

    private final Map<String, StatusResponseBean> statusResponseBeanCache;

    public StatusResponseMessageProcessor (
        StatusResponseAdapter statusResponseAdapter,
        Map<Handle, String> ricCache,
        Map<String, StatusResponseBean> statusResponseBeanCache
    ) {
        this.statusResponseAdapter = statusResponseAdapter;
        this.ricCache = ricCache;
        this.statusResponseBeanCache = statusResponseBeanCache;
    }

    @Override
    public Message<StatusResponseBean> process(Message<OMMItemEvent> message) {

        log.debug("process: method begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        OMMItemEvent itemEvent = message.getPayload();

        OMMMsg ommMsg = itemEvent.getMsg();

        Handle handle = itemEvent.getHandle();

        String ric = ricCache.get(handle);

        StatusResponseBean statusResponseBean = statusResponseBeanCache.get(ric);

        StatusResponse statusResponse = statusResponseBean.getStatusResponse();

        if (statusResponse == null) {
            statusResponse = statusResponseAdapter.adapt(ommMsg);
            statusResponseBean.setStatusResponse(statusResponse);
        } else {
            // TODO: Is this else block (for updates) realistic?
            statusResponse.reset ();
            statusResponseAdapter.adapt(ommMsg, statusResponse);
        }

        log.debug("statusResponse: " + statusResponse);

        Message<StatusResponseBean> result = MessageBuilder
            .withPayload(statusResponseBean)
            .copyHeaders(headers)
            .build();

        log.debug("statusResponseMessageProcessor.process: method ends; result: " + result);

        return result;
    }
}
