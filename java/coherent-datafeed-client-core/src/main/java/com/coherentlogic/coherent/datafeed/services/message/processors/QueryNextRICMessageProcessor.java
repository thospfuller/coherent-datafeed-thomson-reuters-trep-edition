package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntry;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.domain.TimeSeriesKey;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
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
public class QueryNextRICMessageProcessor implements MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log = LoggerFactory.getLogger(QueryNextRICMessageProcessor.class);

    private final TimeSeriesServiceSpecification timeSeriesService;

    private final Map<Handle, TimeSeriesEntries> timeSeriesEntriesCache;

    public QueryNextRICMessageProcessor(
        TimeSeriesServiceSpecification timeSeriesService,
        Map<Handle, TimeSeriesEntries> timeSeriesEntriesCache
    ) {
        this.timeSeriesService = timeSeriesService;
        this.timeSeriesEntriesCache = timeSeriesEntriesCache;
    }

    /**
     * @todo This method is too big and should be refactored. 
     */
    @Override
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        log.info("queryNextRICMessageProcessor.process: method begins; message: " + message);

        OMMItemEvent itemEvent = message.getPayload();

        Handle handle = itemEvent.getHandle();

        SessionBean sessionBean = (SessionBean) message.getHeaders().get(SESSION);

        TimeSeriesKey parentTimeSeriesKey = sessionBean.getTimeSeriesKey(handle);

        log.info("process: method in progress; parentTimeSeriesKey: " + parentTimeSeriesKey);

        Handle loginHandle = sessionBean.getHandle();

        TimeSeriesEntries timeSeriesEntries = timeSeriesEntriesCache.get(handle);

        String nextRic = timeSeriesEntries.nextRic();

        assertNotNull("nextRic", nextRic);

        log.info("Requesting the nextRic value '" + nextRic + "'.");

        String serviceName = timeSeriesEntries.getServiceName();

        int period = timeSeriesEntries.getPeriod();

        TS1Series ts1Series = TS1Series.createSeries(nextRic, period);

        Handle queryHandle = timeSeriesService.queryTimeSeriesFor(
            serviceName, sessionBean, nextRic);

        log.info("nextRic: " + nextRic + ", queryHandle: " + queryHandle);

        /* We have to do this because we need to keep track of the parent
         * RIC (ie. TRI.N) otherwise when this workflow ends, we won't know
         * what ric the time series is associated with because every time
         * the queryTimeSeriesFor method is called, a new handle is returned
         * so we need to pass the parent RIC from handle to handle until
         * there are no more RICs to query.
         */
        sessionBean.putTimeSeriesKey(queryHandle, parentTimeSeriesKey);

        TimeSeriesEntry timeSeriesEntry = new TimeSeriesEntry(nextRic);

        timeSeriesEntry.setTs1Series(ts1Series);

        timeSeriesEntries.putTimeSeriesEntry(queryHandle, timeSeriesEntry);

        // @todo: We're doing a lot of copying here so we may want to
        // reconsider our approach.
        timeSeriesEntriesCache.put(queryHandle, timeSeriesEntries);

        log.info("queryNextRICMessageProcessor.process: method ends; message: " + message);

        return message;
    }



    static String[] getRicsFor (String ric, int period) {
        TS1Series ts1Series = TS1Series.createSeries(ric, period);

        String[] results = ts1Series.getRics();

        return results;
    }
}
