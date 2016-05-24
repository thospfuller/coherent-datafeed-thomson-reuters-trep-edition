package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DAILY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.HANDLE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MONTHLY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Constants.YEARLY;
import static com.coherentlogic.coherent.datafeed.services.message.processors.QueryTimeSeriesMessageProcessor.addRicsFromSeriesTo;
import static com.coherentlogic.coherent.datafeed.services.message.processors.QueryTimeSeriesMessageProcessor.getTimeSeriesEntries;
import static com.coherentlogic.coherent.datafeed.services.message.processors.QueryTimeSeriesMessageProcessor.newTimeSeriesEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntry;
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
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class TimeSeriesService
    extends QueryableService
    implements TimeSeriesServiceSpecification {

    private static final Logger log =
        LoggerFactory.getLogger(TimeSeriesService.class);

    private final Map<String, Integer> ts1ConstantsMap =
        new HashMap<String, Integer> ();

    private final CommonRequestExecutor commonRequestExecutor;

    private final Cache<Handle, Session> sessionCache;

    private final Map<Handle, CompletableFuture<TimeSeries>> handleToCompletableFutureCache;

    private final Map<TimeSeriesKey, CompletableFuture<TimeSeries>> timeSeriesKeyToCompletableFutureCache;

    public TimeSeriesService(
        RequestMessageBuilderFactory factory,
        Client client,
        CommonRequestExecutor commonRequestExecutor,
        final Cache<Handle, Session> sessionCache,
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

        this.sessionCache = sessionCache;
        this.handleToCompletableFutureCache = handleToCompletableFutureCache;
        this.timeSeriesKeyToCompletableFutureCache = timeSeriesKeyToCompletableFutureCache;

        ts1ConstantsMap.put(DAILY, TS1Constants.DAILY_PERIOD);
        ts1ConstantsMap.put(MONTHLY, TS1Constants.WEEKLY_PERIOD);
        ts1ConstantsMap.put(YEARLY, TS1Constants.MONTHLY_PERIOD);
    }

    @Override
    protected List<Handle> executeRequest(
        String serviceName,
        Handle loginHandle,
        short msgModelType,
        String... itemNames
    ) {
        return commonRequestExecutor.executeRequest(
            serviceName,
            loginHandle,
            msgModelType,
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
        Handle loginHandle,
        String ric
    ) {
        List<Handle> results = query (serviceName, loginHandle, ric);

        return results.get(0);
    }

    public CompletableFuture<TimeSeries> getTimeSeriesFor (
        String serviceName,
        Handle loginHandle,
        String ric,
        String period
    ) {
        return getTimeSeriesFor (serviceName, loginHandle, ric, Integer.valueOf(period));
    }

    public CompletableFuture<TimeSeries> getTimeSeriesFor (
        String serviceName,
        Handle loginHandle,
        String ric,
        int period
    ) {
        log.debug("getTimeSeriesFor: method begins; serviceName: " + serviceName + ", loginHandle: " + loginHandle +
            ", ric: " + ric);

        CompletableFuture<TimeSeries> result = null;

        CompletableFuture<TimeSeries> existingTimeSeriesPromise = timeSeriesKeyToCompletableFutureCache.get(ric);

        if (existingTimeSeriesPromise != null) {
            log.info("We have a cache hit for the ric " + ric + " and a non-null time series promise: " +
                existingTimeSeriesPromise);

            result = existingTimeSeriesPromise;

        } else {

            Session session = (Session) sessionCache.get(loginHandle);

            synchronized (session) {

                CompletableFuture<TimeSeries> newTimeSeriesPromise = new CompletableFuture<TimeSeries> ();

                TS1Series series = TS1Series.createSeries(ric, period);

                String primaryRic = series.getPrimaryRic();

                Handle handle = queryTimeSeriesFor(serviceName, loginHandle, primaryRic);

                TimeSeriesKey timeSeriesKey = new TimeSeriesKey (serviceName, ric, period);

                session.putTimeSeriesKey(handle, timeSeriesKey);

                log.error("1. loginHandle: " + loginHandle + ", handle: " + handle + ", ric: " + ric + ", primaryRic: " + primaryRic);

                TimeSeriesEntries timeSeriesEntries = getTimeSeriesEntries (session, serviceName, handle, period);

                addRicsFromSeriesTo (series, timeSeriesEntries);

                TimeSeriesEntry timeSeriesEntry = newTimeSeriesEntry (series);

                timeSeriesEntries.putTimeSeriesEntry(handle, timeSeriesEntry);

                session.putTimeSeriesEntries(handle, timeSeriesEntries);

                sessionCache.put(handle,  session);

                log.error("1. handleToCompletableFutureCache.put: " + handle + ", newTimeSeriesPromise: " + newTimeSeriesPromise);

//                handleToCompletableFutureCache.put(handle, newTimeSeriesPromise);

                timeSeriesKeyToCompletableFutureCache.put(timeSeriesKey, newTimeSeriesPromise);

                result = newTimeSeriesPromise;
            }
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

        Session session = (Session) headers.get(SESSION);

        synchronized (session) {

            Handle handle = (Handle) headers.get(HANDLE);
//
//            log.error("2. handle: " + handle + ", handleToCompletableFutureCache: " + handleToCompletableFutureCache + ", size: " + handleToCompletableFutureCache.size());
//
//            handleToCompletableFutureCache.forEach((Object key, Object value) -> { log.error("onTimeSeriesArrival.handleToCompletableFutureCache.key: " + key + ", value: " + value); });
//
//            CompletableFuture<TimeSeries> newTimeSeriesPromise = handleToCompletableFutureCache.remove(handle);
//
//            log.error("2. newTimeSeriesPromise: " + newTimeSeriesPromise + ", timeSeries: " + timeSeries);

            TimeSeriesKey timeSeriesKey = session.getTimeSeriesKey(handle);

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

//    @Override
//    public TimeSeries getNextUpdate(Long timeout) {
//        throw new UnsupportedOperationException("The getNextUpdate method is not supported.");
//    }
//
//    @Override
//    public String getNextUpdateAsJSON(String timeout) {
//        throw new UnsupportedOperationException("The getNextUpdateAsJSON method is not supported.");
//    }
//
//    @Override
//    public String getNextUpdateAsJSON(Long timeout) {
//        throw new UnsupportedOperationException("The getNextUpdateAsJSON method is not supported.");
//    }
}
