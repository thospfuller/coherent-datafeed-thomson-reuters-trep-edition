package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.beans.QueryParameters;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.services.MarketPriceService;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.ServiceName;
import com.reuters.rfa.common.Handle;

/**
 * Message processor implementation that delegates calls to the {@link
 * #marketPriceService}'s query method and returns the list of handles to the
 * caller.
 *
 * Note that we use this to shield the user from working with the service
 * directly. We accomplish this by having a gateway send messages to an instance
 * of this class, which in turn invokes methods on the actual service
 * implementation.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class QueryMarketPriceMessageProcessor
    implements MessageProcessorSpecification <QueryParameters, Map<String, MarketPrice>> {

    private static final Logger log = LoggerFactory.getLogger(QueryMarketPriceMessageProcessor.class);

    private final MarketPriceService marketPriceService;

    public QueryMarketPriceMessageProcessor(
        MarketPriceService marketPriceService
    ) {
        this.marketPriceService = marketPriceService;
    }

    /**
     * @todo Unit test this method.
     */
    @Override
    @Transactional
    public Message<Map<String, MarketPrice>> process(Message<QueryParameters> message) {

        Message<Map<String, MarketPrice>> result = null;

        QueryParameters parameters = message.getPayload();

        log.info("parameters: " + parameters);

        String serviceName = parameters.getServiceName();

        Handle loginHandle = parameters.getLoginHandle();

        String[] items = parameters.getItem();

        Map<String, MarketPrice> results = marketPriceService.query(
            ServiceName.valueOf(serviceName),
            loginHandle,
            items
        );

        MessageHeaders headers = message.getHeaders();

        result =
            MessageBuilder
                .withPayload(results)
                .copyHeaders(headers)
                .build();

        return result;
    }
}
