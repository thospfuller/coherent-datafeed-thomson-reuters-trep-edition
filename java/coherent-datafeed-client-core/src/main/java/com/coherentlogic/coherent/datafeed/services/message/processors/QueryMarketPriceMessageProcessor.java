package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;

import java.util.List;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.beans.QueryParameters;
import com.coherentlogic.coherent.datafeed.services.MarketPriceServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
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
    implements MessageProcessorSpecification
        <QueryParameters, List<Handle>> {

    private static final Logger log =
        LoggerFactory.getLogger(QueryMarketPriceMessageProcessor.class);

    private final MarketPriceServiceSpecification marketPriceService;

    private final Cache<Handle, Session> sessionCache;

    private final Cache<Handle, Session> marketPriceCache;

    private QueryMarketPriceMessageProcessor(
        MarketPriceServiceSpecification marketPriceService,
        Cache<Handle, Session> sessionCache,
        Cache<Handle, Session> marketPriceCache
    ) {
        this.marketPriceService = marketPriceService;
        this.sessionCache = sessionCache;
        this.marketPriceCache = marketPriceCache;
    }

    /**
     * @todo Unit test this method.
     */
    @Override
    @Transactional
    public Message<List<Handle>> process(
        Message<QueryParameters> message) {

        Message<List<Handle>> result = null;

        QueryParameters parameters = message.getPayload();

        /* Note that it is possible that this method is paused prior and then
         * the MarketPriceMessageEnricher.enrich method is executed, which
         * can result in an NPE progresses. The solution is to sync here and in
         * the MarketPriceMessageEnricher.enrich method.
         *
         * @TODO: Investigate using transactions and the cache lock method as an
         *  alternative.
         */
//        synchronized (marketPriceCache) {

            log.info("parameters: " + parameters);

            String serviceName = parameters.getServiceName();

            Handle loginHandle = parameters.getLoginHandle();

            Session session = (Session) sessionCache.get(loginHandle);

            String[] items = parameters.getItem();

            List<Handle> results = marketPriceService.query(
                serviceName, loginHandle, items);

            for (Handle nextHandle : results)
                marketPriceCache.put(nextHandle, session);

            MessageHeaders headers = message.getHeaders();

            result =
                MessageBuilder
                    .withPayload(results)
                    .copyHeaders(headers)
                    .setHeader(SESSION, session)
                    .build();
//        }
        return result;
    }
}
