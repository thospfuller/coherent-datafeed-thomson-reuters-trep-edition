package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.coherentlogic.coherent.datafeed.beans.TS1DefQueryParameters;
import com.coherentlogic.coherent.datafeed.caches.TS1DefEntryCache;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.TS1DefServiceSpecification;
import com.reuters.rfa.common.Handle;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefGatewayDelegateMessageProcessor
    implements MessageProcessorSpecification<TS1DefQueryParameters, List<Handle>> {

    private static final Logger log = LoggerFactory.getLogger(TS1DefGatewayDelegateMessageProcessor.class);

    private final TS1DefEntryCache ts1DefEntryCache;

//    private final TS1DefCache ts1DefCache;

    private final TS1DefServiceSpecification ts1DefService;

    /**
     * 
     * @param ts1DefService
     */
    public TS1DefGatewayDelegateMessageProcessor(
        TS1DefEntryCache ts1DefEntryCache,
//        TS1DefCache ts1DefCache,
        TS1DefServiceSpecification ts1DefService
    ) {
        this.ts1DefEntryCache = ts1DefEntryCache;
//        this.ts1DefCache = ts1DefCache;
        this.ts1DefService = ts1DefService;
    }

    /**
     * Method extracts the loginHandle and the rics from the {@link
     * TS1DefQueryParameters} and invokes the TS1DefService#initialize method;
     * this method returns a list of handles which are added to the {@link
     * #ts1DefCache} using the {@link Session} as the key.
     *
     * As COMPLETION_EVENTS are received these will be removed from the 
     */
    @Override
    public Message<List<Handle>> process(Message<TS1DefQueryParameters> message) {

        TS1DefQueryParameters ts1DefQueryParameters = message.getPayload();

        SessionBean sessionBean = (SessionBean) message.getHeaders().get(SESSION);

        assertNotNull("sessionBean", sessionBean);

        Handle loginHandle = sessionBean.getHandle();

        MessageHeaders headers = null;

        Message<List<Handle>> result = null;

        String[] rics = ts1DefQueryParameters.getRics();

        log.debug("ts1DefQueryParameters: " + ts1DefQueryParameters);

        List<Handle> handles = null;

        if (rics == null || rics.length == 0)
            handles = ts1DefService.initialize(sessionBean);
        else
            handles = ts1DefService.initialize(sessionBean, rics);

        for (Handle nextHandle : handles) {
//            ts1DefCache.put (nextHandle, session);
            TS1DefEntry ts1DefEntry = new TS1DefEntry ();
            ts1DefEntryCache.put(nextHandle, ts1DefEntry);
        }

        headers = message.getHeaders();

        result = MessageBuilder.withPayload(handles).copyHeaders(headers).build();

        log.debug("process: method ends; result: " + ToStringBuilder.reflectionToString(result));

        return result;
    }
}
