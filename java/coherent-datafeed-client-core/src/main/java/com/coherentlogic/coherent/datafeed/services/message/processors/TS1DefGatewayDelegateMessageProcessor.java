package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.coherentlogic.coherent.datafeed.beans.TS1DefQueryParameters;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.coherentlogic.coherent.datafeed.services.TS1DefServiceSpecification;
import com.reuters.rfa.common.Handle;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefGatewayDelegateMessageProcessor implements
    MessageProcessorSpecification
        <TS1DefQueryParameters, List<Handle>> {

    private static final Logger log =
        LoggerFactory.getLogger(TS1DefGatewayDelegateMessageProcessor.class);

    private final Map<Handle, Session> sessionCache;

    private final Map<Handle, Session> ts1DefCache;

    private final TS1DefServiceSpecification ts1DefService;

    /**
     * 
     * @param ts1DefService
     */
    public TS1DefGatewayDelegateMessageProcessor(
        Map<Handle, Session> sessionCache,
        Map<Handle, Session> ts1DefCache,
        TS1DefServiceSpecification ts1DefService
    ) {
        this.sessionCache = sessionCache;
        this.ts1DefCache = ts1DefCache;
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
    @Transactional
    public Message<List<Handle>> process(
        Message<TS1DefQueryParameters> message) {

        TS1DefQueryParameters ts1DefQueryParameters = message.getPayload();

        Handle loginHandle = ts1DefQueryParameters.getLoginHandle();

        MessageHeaders headers = null;

        Message<List<Handle>> result = null;

//        synchronized (sessionCache) {

            Session session = sessionCache.get(loginHandle);

            String[] rics = ts1DefQueryParameters.getRics();

            log.info("ts1DefQueryParameters: " + ts1DefQueryParameters);

            List<Handle> handles = null;

            if (rics == null || rics.length == 0)
                handles = ts1DefService.initialize(loginHandle);
            else
                handles = ts1DefService.initialize(loginHandle, rics);

            for (Handle nextHandle : handles) {
                ts1DefCache.put (nextHandle, session);
                TS1DefEntry ts1DefEntry = new TS1DefEntry ();
                session.putTS1DefEntry(nextHandle, ts1DefEntry);
                sessionCache.put(nextHandle, session);
            }
    
            headers = message.getHeaders();

            result = MessageBuilder
                .withPayload(handles)
                .copyHeaders(headers)
                .build();
//        }

        log.info("process: method ends; result: " +
            ToStringBuilder.reflectionToString(result));

        return result;
    }
}
/*
    @Override
    public Message<List<Handle>> process(
        Message<TS1DefQueryParameters> message) {

        TS1DefQueryParameters ts1DefQueryParameters = message.getPayload();

        Handle loginHandle = ts1DefQueryParameters.getLoginHandle();

        MessageHeaders headers = null;

        Message<List<Handle>> result = null;

        synchronized (sessionCache) {

            Session session = sessionCache.get(loginHandle);

            String[] rics = ts1DefQueryParameters.getRics();

            log.info("ts1DefQueryParameters: " + ts1DefQueryParameters);

            List<Handle> handles = null;

            if (rics == null || rics.length == 0)
                handles = ts1DefService.initialize(loginHandle);
            else
                handles = ts1DefService.initialize(loginHandle, rics);

            for (Handle nextHandle : handles) {
                ts1DefCache.put (nextHandle, session);
                TS1DefEntry ts1DefEntry = new TS1DefEntry ();
                session.putTS1DefEntry(nextHandle, ts1DefEntry);
                sessionCache.put(nextHandle, session);
            }
    
            headers = message.getHeaders();

            result = MessageBuilder
                .withPayload(handles)
                .copyHeaders(headers)
                .build();
        }

        log.info("process: method ends; result: " +
            ToStringBuilder.reflectionToString(result));

        return result;
    }
 */