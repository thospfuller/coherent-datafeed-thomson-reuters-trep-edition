package com.coherentlogic.coherent.datafeed.beans;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reuters.rfa.common.Handle;
import com.reuters.ts1.TS1DefDb;


/**
 * A class that helps manage the aggregation of data for
 * {@link TimeSeriesEntry} objects.
 *
 * @todo In this case handle is actually the loginHandle and I think this is a
 *  bit messy since in every other case the cachedObject has a handle which is
 *  set to that handle returned from the call to query.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesEntries implements Serializable {

    private static final long serialVersionUID = 1139895190469460424L;

    private static final Logger log =
        LoggerFactory.getLogger(TimeSeriesEntries.class);

    private transient final TS1DefDb ts1DefDb;

    private final Map<Handle, TimeSeriesEntry> timeSeriesEntryMap;

    private final String serviceName;

    private final int period;

    /**
     * Do we need this?
     */
    private List<String> ricList = new ArrayList<String> ();

    private boolean ricsHaveBeenAdded = false;

    public TimeSeriesEntries(
        TS1DefDb ts1DefDb,
        String serviceName,
        int period
    ) {
        this (
            ts1DefDb,
            serviceName,
            period,
            new HashMap<Handle, TimeSeriesEntry> ()
        );
    }
    
    public TimeSeriesEntries(
        TS1DefDb ts1DefDb,
        String serviceName,
        int period,
        Map<Handle, TimeSeriesEntry> timeSeriesEntryMap
    ) {
        super();
        this.ts1DefDb = ts1DefDb;
        this.serviceName = serviceName;
        this.period = period;
        this.timeSeriesEntryMap = timeSeriesEntryMap;
    }

    public TimeSeriesEntry getTimeSeriesEntry (Handle handle) {
        return timeSeriesEntryMap.get(handle);
    }

    public void putTimeSeriesEntry (
        Handle handle,
        TimeSeriesEntry timeSeriesEntry
    ) {
        timeSeriesEntryMap.put(handle, timeSeriesEntry);
    }

    public TimeSeriesEntry removeTimeSeriesEntry (Handle handle) {
        return timeSeriesEntryMap.remove(handle);
    }

    public boolean loadComplete () {

        boolean result = true;

        for (Map.Entry<Handle, TimeSeriesEntry> entry :
            timeSeriesEntryMap.entrySet()) {

            TimeSeriesEntry timeSeriesEntry = entry.getValue();

            if (!timeSeriesEntry.isLoaded()) {
                result = false;
                break;
            }
        }
        return result;
    }

    public TS1DefDb getTs1DefDb() {
        return ts1DefDb;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getPeriod() {
        return period;
    }

    public Map<Handle, TimeSeriesEntry> getTimeSeriesEntryMap() {
        return timeSeriesEntryMap;
    }

    public void addRics (String ric) {
        addRics(new String[] {ric});
    }

    public void addRics (String... rics) {

        assertNotNull ("rics", rics);

        List<String> results = new ArrayList<String> ();

        for (String ric : rics)
            ricList.add(ric);

        addRics(results);
    }

    public void addRics (Collection<String> rics) {
        ricList.addAll(rics);
        ricsHaveBeenAdded = true;
    }

    /**
     * Method removes the first RIC and returns this value.
     *
     * When this method returns null, there are no further RICs to be processed.
     *
     * @todo Why do we start at last and go towards zero?
     */
    public String nextRic () {

        // If we remove the last then the order will be 7, 6, 5, .. for time
        // series requested from TR -- always removing the first ensures what
        // we request appears in the same order as what the TimeSeriesConsole
        // application demonstrates. Note it might not make a difference one
        // way or the next what we request, but it will probably behoove us to
        // stick closely with the example.
        //int last = ricList.size() - 1;

        String nextRic = ricList.remove(0);

        log.info ("nextRic: " + nextRic);

        return nextRic;
    }

    public int getRicListSize () {
        return ricList.size();
    }

    public boolean isRicListEmpty () {
        return ricList.isEmpty();
    }

    public boolean ricsHaveBeenAdded () {
        return ricsHaveBeenAdded;
    }
}
