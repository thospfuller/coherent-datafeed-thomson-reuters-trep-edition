package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

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
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class StatusResponseMessageProcessor
    implements MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log = LoggerFactory.getLogger(StatusResponseMessageProcessor.class);

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
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        log.debug("process: method begins; message: " + message);

//        SessionBean sessionBean = (SessionBean) message.getHeaders().get(SESSION);

        OMMItemEvent itemEvent = message.getPayload();

        OMMMsg ommMsg = itemEvent.getMsg();

        Handle handle = itemEvent.getHandle();

        String ric = ricCache.get(handle);

        StatusResponseBean statusResponseBean = statusResponseBeanCache.get(ric);

        StatusResponse statusResponse = statusResponseBean.getStatusResponse();

        assertNotNull("statusResponse", statusResponse);

//        if (statusResponse == null) {
//            statusResponse = statusResponseAdapter.adapt(ommMsg);
//            statusResponseBean.setStatusResponse(statusResponse);
//        }
//        else {
            // TODO: Is this else block (for updates) realistic?
            statusResponse.reset ();
            statusResponseAdapter.adapt(ommMsg, statusResponse);
//        }

        log.debug("statusResponse: " + statusResponse);

        Message<OMMItemEvent> result = MessageBuilder.fromMessage(message).copyHeadersIfAbsent(message.getHeaders()).build();

        log.debug("statusResponseMessageProcessor.process: method ends; message: " + message);

        return result;
    }
}
