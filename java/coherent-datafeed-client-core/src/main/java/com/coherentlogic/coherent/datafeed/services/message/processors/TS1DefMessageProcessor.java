package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.SessionUtils.assertNotNull;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.coherentlogic.coherent.datafeed.services.TS1DefServiceSpecification;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefMessageProcessor
    implements MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log =
        LoggerFactory.getLogger(TS1DefMessageProcessor.class);

    private final Map<Handle, Session> sessionCache;

    private final Map<Handle, Session> ts1DefCache;

    private final TS1DefServiceSpecification ts1DefService;

    /**
     * 
     * @param ts1DefService
     */
    private TS1DefMessageProcessor(
        Map<Handle, Session> sessionCache,
        Map<Handle, Session> ts1DefCache,
        TS1DefServiceSpecification ts1DefService
    ) {
        this.sessionCache = sessionCache;
        this.ts1DefCache = ts1DefCache;
        this.ts1DefService = ts1DefService;
    }

    /**
     * Method extracts the loginHandle from the message headers and invokes the
     * TS1DefService#initialize method; the resultant 
     * 
     * Note that the session is set in the headers earlier in the workflow.
     * 
     * this method returns a list of handles which are added to the {@link
     * #ts1DefCache} using the {@link Session} as the key.
     *
     * As COMPLETION_EVENTS are received these will be removed from the 
     */
    @Override
    @Transactional
    public Message<OMMItemEvent> process(
        Message<OMMItemEvent> message) {

        log.info("ts1DefMessageProcessor.process: method begins; message: " +
            message);

//        synchronized (ts1DefCache) {

            MessageHeaders headers = message.getHeaders();

            Session session = (Session) headers.get(SESSION);

            assertNotNull(session);

            Handle loginHandle = session.getLoginHandle();

            List<Handle> handles = ts1DefService.initialize(loginHandle);

            log.info("handles.size: " + handles.size());

            for (Handle nextHandle : handles) {
                ts1DefCache.put (nextHandle, session);
                TS1DefEntry ts1DefEntry = new TS1DefEntry ();
                session.putTS1DefEntry(nextHandle, ts1DefEntry);
                sessionCache.put(nextHandle, session);
            }
//        }

        log.info("ts1DefMessageProcessor.process: method ends; message: " +
            ToStringBuilder.reflectionToString(message));

        return message;
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