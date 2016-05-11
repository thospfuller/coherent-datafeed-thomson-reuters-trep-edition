package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.beans.QueryParameters;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.services.MarketByOrderServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.ServiceName;
import com.reuters.rfa.common.Handle;

/**
 * Message processor implementation that delegates calls to the {@link
 * #marketByOrderService}'s query method and returns the list of handles to the
 * caller.
 *
 * Note that we use this to shield the user from working with the service
 * directly. We accomplish this by having a gateway send messages to an instance
 * of this class, which in turn invokes methods on the actual service
 * implementation.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class QueryMarketByOrderMessageProcessor
    implements MessageProcessorSpecification <QueryParameters, Map<String, MarketByOrder>> {

    private static final Logger log = LoggerFactory.getLogger(QueryMarketByOrderMessageProcessor.class);

    private final MarketByOrderServiceSpecification marketByOrderService;

    public QueryMarketByOrderMessageProcessor(MarketByOrderServiceSpecification marketByOrderService) {
        this.marketByOrderService = marketByOrderService;
    }

    /**
     * @todo Unit test this method.
     */
    @Override
    @Transactional
    public Message<Map<String, MarketByOrder>> process(
        Message<QueryParameters> message) {

        Message<Map<String, MarketByOrder>> result = null;

        QueryParameters parameters = message.getPayload();

        log.info("parameters: " + parameters);

        String serviceName = parameters.getServiceName();

        Handle loginHandle = parameters.getLoginHandle();

        String[] items = parameters.getItem();

        Map<String, MarketByOrder> results = marketByOrderService.query(
            ServiceName.valueOf(serviceName), loginHandle, items);

        MessageHeaders headers = message.getHeaders();

        result =
            MessageBuilder
                .withPayload(results)
                .copyHeaders(headers)
                .build();

        return result;
    }
}
