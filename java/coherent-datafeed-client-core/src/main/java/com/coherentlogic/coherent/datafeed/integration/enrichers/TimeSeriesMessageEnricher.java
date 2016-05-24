package com.coherentlogic.coherent.datafeed.integration.enrichers;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.SessionUtils.getSession;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.domain.TimeSeriesKey;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Event;
import com.reuters.rfa.common.Handle;

/**
 * This class is used to enrich the message returned from the RFA api by using
 * the message payload handle to find the associated session; this session is
 * then added to the message headers under the Constants.SESSION key.
 *
 * @todo Consolidate all / most of these enrichers.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesMessageEnricher extends AbstractMessageEnricher {

    private static final Logger log =
        LoggerFactory.getLogger(TimeSeriesMessageEnricher.class);

    public TimeSeriesMessageEnricher(
        Cache<Handle, Session> sessionCache
    ) {
        super(sessionCache);
    }

    /**
     * @todo This method could be moved to a base class.
     */
//    @Transactional
    public Message<Event> enrich (Message<Event> message) {

        log.debug("enrich: method begins; message: " + message);

        // TODO: Add comments regarding why this is necessary.
        MessageHeaders headers = message.getHeaders();

        Cache<Handle, Session> sessionCache = getSessionCache();

        Message<Event> enrichedMessage = null;

        /* Note that it is possible that this method is invoked before the
         * QueryTimeSeriesMessageProcessor.process method completes adding the
         * session to the cache and if this happens we'll end up with a NPE as
         * the workflow progresses. The solution is to sync here and in the
         * QueryTimeSeriesMessageProcessor.process method.
         *
         * @TODO: Investigate using transactions and the cache lock method as an
         *  alternative.
         */
        synchronized (sessionCache) {

            Session session = getSession (message, sessionCache);

            Event event = message.getPayload();

            Handle handle = event.getHandle();

            TimeSeriesKey timeSeriesKey = session.getTimeSeriesKey(handle);

            if (timeSeriesKey != null)
                log.info("enrich: method in progress; handle: " + handle + ", timeSeriesKey: " + timeSeriesKey);
            else
                throw new NullPointerRuntimeException("The ric is null for the handle: " + handle);

            enrichedMessage =
                MessageBuilder
                    .fromMessage(message)
                    .copyHeaders(headers)
                    .setHeader(SESSION, session)
                    .build ();
        }
        log.debug("enrich: method ends; enrichedMessage: " + enrichedMessage);

        return enrichedMessage;
    }
}
