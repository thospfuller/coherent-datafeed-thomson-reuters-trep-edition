package com.coherentlogic.coherent.datafeed.domain;

import java.util.HashMap;
import java.util.Map;

import com.coherentlogic.coherent.datafeed.annotations.DoNotAnalyze;
import com.reuters.rfa.common.Handle;

/**
 * 
 * @author <a href="support@coherentlogic.com">Support</a>
 */
@DoNotAnalyze
public class SessionBean extends StatusResponseBean {

    private static final long serialVersionUID = -3565388297410289405L;

    private String dacsId;

    private int applicationId;

    public SessionBean () {
    }

    public String getDacsId() {
        return dacsId;
    }

    public void setDacsId(String dacsId) {
        this.dacsId = dacsId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    private final Map<Handle, TimeSeriesKey> timeSeriesKeyCache = new HashMap<Handle, TimeSeriesKey> ();

    /**
     * @deprecated This method was copied from the Session and has been added here only so we can get rid of the
     *  session altogether and replace it with this bean. This method should be moved into it's own cache (possibly),
     *  or added to a child class which is for timeseries-specific queries.
     */
    @Deprecated
    public TimeSeriesKey getTimeSeriesKey(Handle handle) {
        return timeSeriesKeyCache.get (handle);
    }

    /**
     * @param handle
     * @param timeSeriesKey
     */
    public void putTimeSeriesKey(Handle handle, TimeSeriesKey timeSeriesKey) {
        timeSeriesKeyCache.put (handle, timeSeriesKey);
    }

//    public void putTimeSeriesEntries (
//        Handle handle,
//        TimeSeriesEntries timeSeriesEntries
//    ) {
//        timeSeriesEntryCache.put(handle, timeSeriesEntries);
//    }

    @Override
    public String toString() {
        return "SessionBean [dacsId=" + dacsId + ", applicationId=" + applicationId + ", toString()=" + super.toString()
            + "]";
    }
}
