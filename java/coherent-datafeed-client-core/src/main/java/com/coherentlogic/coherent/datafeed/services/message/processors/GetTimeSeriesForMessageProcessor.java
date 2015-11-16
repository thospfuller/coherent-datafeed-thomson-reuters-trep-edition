package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.*;

import java.util.concurrent.CompletableFuture;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesQueryParameter;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesService;
import com.reuters.rfa.common.Handle;

/**
 * This service is called from the Spring Integration work flow, which will pass
 * along an instance of the {@link Session} which can be found in the message
 * headers.
 *
 * Note that we use this to shield the user from working with the service
 * directly. We accomplish this by having a gateway send messages to an instance
 * of this class, which in turn invokes methods on the actual service
 * implementation.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetTimeSeriesForMessageProcessor
    implements MessageProcessorSpecification<TimeSeriesQueryParameter, CompletableFuture<TimeSeries>> {

    private static final Logger log =
        LoggerFactory.getLogger(GetTimeSeriesForMessageProcessor.class);

    private final TimeSeriesService timeSeriesService;

    // Probably don't need the sessionCache in this message processor.
    @Deprecated
    private final Cache<Handle, Session> sessionCache;

    public GetTimeSeriesForMessageProcessor(
        TimeSeriesService timeSeriesService,
        Cache<Handle, Session> sessionCache
    ) {
        this.timeSeriesService = timeSeriesService;
        this.sessionCache = sessionCache;
    }

    @Override
    @Transactional
    public Message<CompletableFuture<TimeSeries>> process(
        Message<TimeSeriesQueryParameter> message
    ) {
        log.info("getTimeSeriesMessageProcessor.process: method begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        TimeSeriesQueryParameter parameters = message.getPayload();

        log.info("parameters: " + parameters);

        String serviceName = parameters.getServiceName();

        Handle loginHandle = parameters.getLoginHandle();

        String ric = parameters.getItem();

        int period = parameters.getPeriod();

        Session session = (Session) sessionCache.get(loginHandle);

        if (session == null)
            throw new NullPointerRuntimeException ("The session is null!");

        session.setTimeSeriesRIC(ric);

        // This service method will query for the *primary ric*, which happens inside the method whereas for the
        // Query, we do this *outside* the method (in the message processor).
        CompletableFuture<TimeSeries> payload = timeSeriesService.getTimeSeriesFor(
            serviceName, loginHandle, ric, period);

        Message<CompletableFuture<TimeSeries>> result =
            MessageBuilder
                .withPayload(payload)
                .copyHeaders(headers)
                // TODO: Do we really need this?
                .setHeader(SESSION, session)
                .build();

        log.info("getTimeSeriesMessageProcessor.process: method ends; result: " + result);

        return result;
    }
}
