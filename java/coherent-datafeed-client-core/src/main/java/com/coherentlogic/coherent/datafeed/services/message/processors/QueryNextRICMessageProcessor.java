package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.SessionUtils.getSession;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntry;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesServiceSpecification;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;
import com.reuters.ts1.TS1Series;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class QueryNextRICMessageProcessor implements
    MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log =
        LoggerFactory.getLogger(QueryNextRICMessageProcessor.class);

    private final TimeSeriesServiceSpecification timeSeriesService;

    private final Cache<Handle, Session> sessionCache;

    public QueryNextRICMessageProcessor(
        TimeSeriesServiceSpecification timeSeriesService,
        Cache<Handle, Session> sessionCache
    ) {
        this.timeSeriesService = timeSeriesService;
        this.sessionCache = sessionCache;
    }

    /**
     * @todo This method is too big and should be refactored. 
     */
    @Override
    @Transactional
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        log.info("queryNextRICMessageProcessor.process: method begins; " +
            "message: " + message);

        OMMItemEvent itemEvent = message.getPayload();

        Handle handle = itemEvent.getHandle();

        Session session = getSession(message);

        Handle loginHandle = session.getLoginHandle();

//        synchronized (sessionCache) {

            TimeSeriesEntries timeSeriesEntries =
                session.getTimeSeriesEntries(handle);

            String nextRic = timeSeriesEntries.nextRic();

            assertNotNull("nextRic", nextRic);

            log.info("Requesting the nextRic value '" + nextRic + "'.");

            String serviceName = timeSeriesEntries.getServiceName();

            int period = timeSeriesEntries.getPeriod();

            TS1Series ts1Series = TS1Series.createSeries(nextRic, period);

            Handle queryHandle = timeSeriesService.queryTimeSeriesFor(
                serviceName, loginHandle, nextRic);

            log.info("nextRic: " + nextRic + ", queryHandle: " + queryHandle);

            TimeSeriesEntry timeSeriesEntry = new TimeSeriesEntry(nextRic);

            timeSeriesEntry.setTs1Series(ts1Series);

            timeSeriesEntries.putTimeSeriesEntry(queryHandle, timeSeriesEntry);

            // @todo: We're doing a lot of copying here so we may want to
            // reconsider our approach.
            session.putTimeSeriesEntries(queryHandle, timeSeriesEntries);

            sessionCache.put(queryHandle, session);
//        }

        log.info("queryNextRICMessageProcessor.process: method ends; " +
            "message: " + message);

        return message;
    }



    static String[] getRicsFor (String ric, int period) {
        TS1Series ts1Series = TS1Series.createSeries(ric, period);

        String[] results = ts1Series.getRics();

        return results;
    }
}
