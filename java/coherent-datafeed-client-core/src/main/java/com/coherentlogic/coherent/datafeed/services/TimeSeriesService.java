package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DAILY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.HANDLE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MONTHLY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Constants.YEARLY;
import static com.coherentlogic.coherent.datafeed.services.message.processors.QueryTimeSeriesMessageProcessor.newTimeSeriesEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntry;
import com.coherentlogic.coherent.datafeed.caches.TimeSeriesEntriesCache;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.coherentlogic.coherent.datafeed.domain.TimeSeriesKey;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.rdm.RDMMsgTypes;
import com.reuters.ts1.TS1Constants;
import com.reuters.ts1.TS1Series;

/**
 * Front-end for the Thomson Reuters market price service.
 *
 * d = historic data
 * r = session
 * s = RIC(s)
 * p = the period of the data
 * d = date
 * f = fields
 * x = historic data
 *
 * x = history(r,s)
 * x = history(r,s,p)
 * x = history(r,s,f)
 * x = history(r,s,f,p)
 * x = history(r,s,d)
 * x = history(r,s,startdate,enddate)
 * x = history(r,s,startdate,enddate,p)
 * x = history(r,s,f,startdate,enddate)
 * x = history(r,s,f,startdate,enddate,p)
 *
 * @see {@link com.reuters.ts1.TS1Constants#MONTHLY_PERIOD}
 * @see {@link com.reuters.ts1.TS1Series} The period is set here.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesService extends QueryableService implements TimeSeriesServiceSpecification {

    private static final Logger log = LoggerFactory.getLogger(TimeSeriesService.class);

    private final Map<String, Integer> ts1ConstantsMap =
        new HashMap<String, Integer> ();

    private final CommonRequestExecutor commonRequestExecutor;

    private final TimeSeriesEntriesCache timeSeriesEntriesCache;

    private final Map<Handle, CompletableFuture<TimeSeries>> handleToCompletableFutureCache;

    private final Map<TimeSeriesKey, CompletableFuture<TimeSeries>> timeSeriesKeyToCompletableFutureCache;

    public TimeSeriesService(
        RequestMessageBuilderFactory factory,
        Client client,
        CommonRequestExecutor commonRequestExecutor,
        TimeSeriesEntriesCache timeSeriesEntriesCache,
        final Map<Handle, CompletableFuture<TimeSeries>> handleToCompletableFutureCache,
        final Map<TimeSeriesKey, CompletableFuture<TimeSeries>> timeSeriesKeyToCompletableFutureCache
    ) {
        super(
            Constants.ELEKTRON_DD,
            RDMMsgTypes.MARKET_PRICE,
            factory,
            client
        );

        this.commonRequestExecutor = commonRequestExecutor;

        this.timeSeriesEntriesCache = timeSeriesEntriesCache;
        this.handleToCompletableFutureCache = handleToCompletableFutureCache;
        this.timeSeriesKeyToCompletableFutureCache = timeSeriesKeyToCompletableFutureCache;

        ts1ConstantsMap.put(DAILY, TS1Constants.DAILY_PERIOD);
        ts1ConstantsMap.put(MONTHLY, TS1Constants.WEEKLY_PERIOD);
        ts1ConstantsMap.put(YEARLY, TS1Constants.MONTHLY_PERIOD);
    }

    @Override
    protected List<Handle> executeRequest(
        String serviceName,
        short msgModelType,
        SessionBean sessionBean,
        String... itemNames
    ) {
        return commonRequestExecutor.executeRequest(
            serviceName,
            msgModelType,
            sessionBean,
            itemNames
        );
    }

    /**
     * 
     * d = historic data
     * r = session
     * s = RIC(s)
     * p = the period of the data
     *
     * TS1Series.createSeries(itemName, period);
     *
     * @see {@link QueryableService}
     *
     * @todo Refactor this method so that it returns a single handle.
     */
    public Handle queryTimeSeriesFor(
        String serviceName,
        SessionBean sessionBean,
        String ric
    ) {
        List<Handle> results = query (serviceName, sessionBean, ric);

        return results.get(0);
    }

    public CompletableFuture<TimeSeries> getTimeSeriesFor (
        String serviceName,
        SessionBean sessionBean,
        String ric,
        String period
    ) {
        return getTimeSeriesFor (serviceName, sessionBean, ric, Integer.valueOf(period));
    }

    public CompletableFuture<TimeSeries> getTimeSeriesFor (
        String serviceName,
        SessionBean sessionBean,
        String ric,
        int period
    ) {
        log.debug("getTimeSeriesFor: method begins; serviceName: " + serviceName + ", sessionBean: " + sessionBean +
            ", ric: " + ric);

        CompletableFuture<TimeSeries> result = null;

        CompletableFuture<TimeSeries> existingTimeSeriesPromise = timeSeriesKeyToCompletableFutureCache.get(ric);

        if (existingTimeSeriesPromise != null) {
            log.info("We have a cache hit for the ric " + ric + " and a non-null time series promise: " +
                existingTimeSeriesPromise);

            result = existingTimeSeriesPromise;

        } else {

            CompletableFuture<TimeSeries> newTimeSeriesPromise = new CompletableFuture<TimeSeries> ();

            TS1Series series = TS1Series.createSeries(ric, period);

            String primaryRic = series.getPrimaryRic();

            Handle handle = queryTimeSeriesFor(serviceName, sessionBean, primaryRic);

            TimeSeriesKey timeSeriesKey = new TimeSeriesKey (serviceName, ric, period);

            sessionBean.putTimeSeriesKey(handle, timeSeriesKey);

            log.debug("sessionBean: " + sessionBean + ", handle: " + handle + ", ric: " + ric + ", primaryRic: " +
                primaryRic);

            TimeSeriesEntries timeSeriesEntries =
                timeSeriesEntriesCache.getTimeSeriesEntries (serviceName, handle, period);

            timeSeriesEntries.addRicsFromSeriesTo (series);

            TimeSeriesEntry timeSeriesEntry = newTimeSeriesEntry (series);

            timeSeriesEntries.putTimeSeriesEntry(handle, timeSeriesEntry);

            timeSeriesEntriesCache.put(handle, timeSeriesEntries);

            timeSeriesKeyToCompletableFutureCache.put(timeSeriesKey, newTimeSeriesPromise);

            result = newTimeSeriesPromise;
        }

        log.info("getTimeSeriesFor: method ends; result: " + result);

        return result;
    }

    /**
     * This method is called from the Spring Integration workflow.
     *
     * @TODO Once we've set the TS on the CompletableFuture we should be able to remove the CompletableFuture from the
     *  handleToCompletableFutureCache.
     *
     * @param message
     */
    public void onTimeSeriesArrival (Message<TimeSeries> message) {

        log.info("onTimeSeriesArrival: method begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        TimeSeries timeSeries = message.getPayload();

        // NOTE: The SAME newTimeSeriesPromise from the #getTimeSeriesFor method is used in the
        //       handleToCompletableFutureCache ** AS WELL AS THE ** timeSeriesKeyToCompletableFutureCache (again see the method
        //       above) which means we don't need to associate the handle with a ric and then get the promise -- all we
        //       need is the handle and we're good.

        SessionBean sessionBean = (SessionBean) headers.get(SESSION);

        Handle handle = (Handle) headers.get(HANDLE);

        TimeSeriesKey timeSeriesKey = sessionBean.getTimeSeriesKey(handle);

        if (timeSeriesKey == null) {
            throw new NullPointerRuntimeException("The timeSeriesKey is null for the timeSeries associated with the "
                + "handle: " + handle + "; this is definitely a bug and should never happen.");
        } else {
            log.info ("timeSeriesKey: " + timeSeriesKey);
        }

        CompletableFuture<TimeSeries> timeSeriesPromise = timeSeriesKeyToCompletableFutureCache.get(timeSeriesKey);

        if (timeSeriesPromise != null) {
            boolean completed = timeSeriesPromise.complete(timeSeries);
            log.info("onTimeSeriesArrival: method ends; completed: " + completed + " for timeSeriesKey " + timeSeriesKey + " and timeSeries: " + timeSeries);
        } else {
            throw new NullPointerRuntimeException("onTimeSeriesArrival: method ends; note that the newTimeSeriesPromise is null for the timeSeriesKey: " + timeSeriesKey);
        }
    }
}
