package com.coherentlogic.coherent.datafeed.caches;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.reuters.rfa.common.Handle;
import com.reuters.ts1.TS1DefDb;

public class TimeSeriesEntriesCache extends MapDelegate<Handle, TimeSeriesEntries> {

	public static final String BEAN_NAME = "timeSeriesEntriesCache";

    public TimeSeriesEntriesCache(Map<Handle, TimeSeriesEntries> cache) {
        super(cache);
    }

    /**
     * Method gets the {@link TimeSeriesEntries} from the session using the
     * handle and if this is null it will create a new instance of {@link
     * TimeSeriesEntries} and add it to the session.
     */
    public TimeSeriesEntries getTimeSeriesEntries (String serviceName, Handle handle, int period) {

        TimeSeriesEntries timeSeriesEntries = get(handle);

        if (timeSeriesEntries == null) {
            TS1DefDb ts1DefDb = new TS1DefDb ();

            timeSeriesEntries = new TimeSeriesEntries (ts1DefDb, serviceName, period);

            put (handle, timeSeriesEntries);
        }
        return timeSeriesEntries;
    }
}
