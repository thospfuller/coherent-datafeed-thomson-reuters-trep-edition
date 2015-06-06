package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntry;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesQueryParameter;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesServiceSpecification;
import com.reuters.rfa.common.Handle;
import com.reuters.ts1.TS1DefDb;
import com.reuters.ts1.TS1Series;

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
public class QueryTimeSeriesMessageProcessor
    implements MessageProcessorSpecification<TimeSeriesQueryParameter, Handle> {

    private static final Logger log =
        LoggerFactory.getLogger(QueryTimeSeriesMessageProcessor.class);

    private final TimeSeriesServiceSpecification timeSeriesService;

    private final Cache<Handle, Session> sessionCache;

    public QueryTimeSeriesMessageProcessor(
        TimeSeriesServiceSpecification timeSeriesService,
        Cache<Handle, Session> sessionCache
    ) {
        this.timeSeriesService = timeSeriesService;
        this.sessionCache = sessionCache;
    }

    @Override
    @Transactional
    public Message<Handle> process(
        Message<TimeSeriesQueryParameter> message
    ) {
        log.info("queryTimeSeriesMessageProcessor.process: method begins; " +
            "message: " + message);

        MessageHeaders headers = message.getHeaders();

        Message<Handle> result = null;

        TimeSeriesQueryParameter parameters = message.getPayload();

        /* Note that it is possible that this method is paused prior and then
         * the TimeSeriesMessageEnricher.enrich method is executed, which
         * can result in an NPE progresses. The solution is to sync here and in
         * the TimeSeriesMessageEnricher.enrich method.
         *
         * @TODO: Investigate using transactions and the cache lock method as an
         *  alternative.
         *
         * @TODO Use the SessionUtils to get the session.
         */
//        synchronized (sessionCache) {

            log.info("parameters: " + parameters);

            Handle loginHandle = parameters.getLoginHandle();

            Session session = (Session) sessionCache.get(loginHandle);

            String serviceName = parameters.getServiceName();

            String ric = parameters.getItem();

            int period = parameters.getPeriod();

            TS1Series series = TS1Series.createSeries(ric, period);

            String primaryRic = series.getPrimaryRic();

            log.info("primaryRic: " + primaryRic);

            Handle handle = timeSeriesService.queryTimeSeriesFor(
                serviceName,
                loginHandle,
                primaryRic
            );

            TimeSeriesEntries timeSeriesEntries =
                getTimeSeriesEntries (session, serviceName, handle, period);

            addRicsFromSeriesTo (series, timeSeriesEntries);

            TimeSeriesEntry timeSeriesEntry = newTimeSeriesEntry (
                series
            );

            timeSeriesEntries.putTimeSeriesEntry(handle, timeSeriesEntry);

            session.putTimeSeriesEntries(handle, timeSeriesEntries);

            sessionCache.put(handle, session);

            result =
                MessageBuilder
                    .withPayload(handle)
                    .copyHeaders(headers)
                    .setHeader(SESSION, session)
                    .build();
//        }

        log.info("queryTimeSeriesMessageProcessor.process: method ends; " +
            "result: " + result);

        return result;
    }

    /**
     * Method gets the {@link TimeSeriesEntries} from the session using the
     * handle and if this is null it will create a new instance of {@link
     * TimeSeriesEntries} and add it to the session.
     */
    static TimeSeriesEntries getTimeSeriesEntries (
        Session session,
        String serviceName,
        Handle handle,
        int period
    ) {
        TimeSeriesEntries timeSeriesEntries =
            session.getTimeSeriesEntries(handle);

        if (timeSeriesEntries == null) {
            TS1DefDb ts1DefDb = new TS1DefDb ();

            timeSeriesEntries = new TimeSeriesEntries (
                ts1DefDb,
                serviceName,
                period
            );

            session.putTimeSeriesEntries(handle, timeSeriesEntries);
        }
        return timeSeriesEntries;
    }

    static void addRicsFromSeriesTo (
        TS1Series series,
        TimeSeriesEntries timeSeriesEntries
    ) {
        String[] rics = series.getRics();

        if (rics != null && 0 < rics.length) {
            timeSeriesEntries.addRics(rics);
        } else {
            // This is not a problem as the first query will only have one RIC
            // -- the primary RIC.
            log.info("The rics variable was either null or empty.");
        }
    }

    static TimeSeriesEntry newTimeSeriesEntry (
        TS1Series ts1Series
    ) {
        String primaryRic = ts1Series.getPrimaryRic();

        TimeSeriesEntry result = new TimeSeriesEntry(primaryRic);
        result.setTs1Series(ts1Series);

        return result;
    }
}
