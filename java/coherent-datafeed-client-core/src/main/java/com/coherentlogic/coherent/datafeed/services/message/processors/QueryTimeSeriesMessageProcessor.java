package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntry;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesQueryParameter;
import com.coherentlogic.coherent.datafeed.caches.TimeSeriesEntriesCache;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesServiceSpecification;
import com.reuters.rfa.common.Handle;
import com.reuters.ts1.TS1Series;

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
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class QueryTimeSeriesMessageProcessor
    implements MessageProcessorSpecification<TimeSeriesQueryParameter, Handle> {

    private static final Logger log =
        LoggerFactory.getLogger(QueryTimeSeriesMessageProcessor.class);

    private final TimeSeriesServiceSpecification timeSeriesService;

    private final TimeSeriesEntriesCache timeSeriesEntriesCache;

    public QueryTimeSeriesMessageProcessor(
        TimeSeriesServiceSpecification timeSeriesService,
        TimeSeriesEntriesCache timeSeriesEntriesCache
    ) {
        this.timeSeriesService = timeSeriesService;
        this.timeSeriesEntriesCache = timeSeriesEntriesCache;
    }

    @Override
    public Message<Handle> process(Message<TimeSeriesQueryParameter> message) {

        log.debug("queryTimeSeriesMessageProcessor.process: method begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        Message<Handle> result = null;

        TimeSeriesQueryParameter parameters = message.getPayload();

        log.debug("parameters: " + parameters);

        SessionBean sessionBean = parameters.getSessionBean();

        Handle loginHandle = sessionBean.getHandle();

        String serviceName = parameters.getServiceName();

        String ric = parameters.getItem();

        int period = parameters.getPeriod();

        TS1Series series = TS1Series.createSeries(ric, period);

        String primaryRic = series.getPrimaryRic();

        log.debug("primaryRic: " + primaryRic);

        Handle handle = timeSeriesService.queryTimeSeriesFor(
            serviceName,
            sessionBean,
            primaryRic
        );

        TimeSeriesEntries timeSeriesEntries = timeSeriesEntriesCache.getTimeSeriesEntries (serviceName, handle, period);

        timeSeriesEntries.addRicsFromSeriesTo (series);

        TimeSeriesEntry timeSeriesEntry = newTimeSeriesEntry (series);

        timeSeriesEntries.putTimeSeriesEntry(handle, timeSeriesEntry);

        timeSeriesEntriesCache.put(handle, timeSeriesEntries);

        result =
            MessageBuilder
                .withPayload(handle)
                .copyHeaders(headers)
                .setHeaderIfAbsent(SESSION, sessionBean)
                .build();

        log.debug("queryTimeSeriesMessageProcessor.process: method ends; result: " + result);

        return result;
    }

//    /**
//     * Method gets the {@link TimeSeriesEntries} from the session using the
//     * handle and if this is null it will create a new instance of {@link
//     * TimeSeriesEntries} and add it to the session.
//     */
//    public static TimeSeriesEntries getTimeSeriesEntries (
//        Map<Handle, TimeSeriesEntries> timeSeriesEntryCache,
//        String serviceName,
//        Handle handle,
//        int period
//    ) {
//        TimeSeriesEntries timeSeriesEntries = timeSeriesEntryCache.get(handle);
//
//        if (timeSeriesEntries == null) {
//            TS1DefDb ts1DefDb = new TS1DefDb ();
//
//            timeSeriesEntries = new TimeSeriesEntries (ts1DefDb, serviceName, period);
//
//            timeSeriesEntryCache.put (handle, timeSeriesEntries);
//        }
//        return timeSeriesEntries;
//    }

//    public static void addRicsFromSeriesTo (TS1Series series, TimeSeriesEntries timeSeriesEntries) {
//
//        String[] rics = series.getRics();
//
//        if (rics != null && 0 < rics.length) {
//            timeSeriesEntries.addRics(rics);
//        } else {
//            // This is not a problem as the first query will only have one RIC
//            // -- the primary RIC.
//            log.debug("The rics variable was either null or empty.");
//        }
//    }

    public static TimeSeriesEntry newTimeSeriesEntry (TS1Series ts1Series) {

        String primaryRic = ts1Series.getPrimaryRic();

        TimeSeriesEntry result = new TimeSeriesEntry(primaryRic);
        result.setTs1Series(ts1Series);

        return result;
    }
}
