package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesQueryParameter;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesService;
import com.reuters.rfa.common.Handle;

/**
 * This service is called from the Spring Integration work flow, which will pass
 * along an instance of the {@link SessionBean} which can be found in the message
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

    private static final Logger log = LoggerFactory.getLogger(GetTimeSeriesForMessageProcessor.class);

    private final TimeSeriesService timeSeriesService;

    public GetTimeSeriesForMessageProcessor(TimeSeriesService timeSeriesService) {
        this.timeSeriesService = timeSeriesService;
    }

    @Override
    public Message<CompletableFuture<TimeSeries>> process(
        Message<TimeSeriesQueryParameter> message
    ) {
        log.debug("process: method begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        TimeSeriesQueryParameter parameters = message.getPayload();

        log.debug("parameters: " + parameters);

        String serviceName = parameters.getServiceName();

        SessionBean sessionBean = parameters.getSessionBean();

        Handle loginHandle = sessionBean.getHandle();

        String ric = parameters.getItem();

        int period = parameters.getPeriod();

        // This service method will query for the *primary ric*, which happens inside the method whereas for the
        // Query, we do this *outside* the method (in the message processor).
        CompletableFuture<TimeSeries> payload = timeSeriesService.getTimeSeriesFor(
            serviceName, sessionBean, ric, period);

        Message<CompletableFuture<TimeSeries>> result =
            MessageBuilder
                .withPayload(payload)
                .copyHeaders(headers)
                // TODO: Do we really need this?
                .setHeader(SESSION, sessionBean)
                .build();

        log.debug("process: method ends; result: " + result);

        return result;
    }
}
