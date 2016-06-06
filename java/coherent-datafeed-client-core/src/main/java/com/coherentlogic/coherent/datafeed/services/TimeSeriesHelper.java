package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.caches.TimeSeriesEntriesCache;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesHelper {

    public static final String BEAN_NAME = "timeSeriesHelper";

    private final TimeSeriesEntriesCache timeSeriesEntriesCache;

    public TimeSeriesHelper (TimeSeriesEntriesCache timeSeriesEntriesCache) {
        this.timeSeriesEntriesCache = timeSeriesEntriesCache;
    }

    public boolean isRicListEmpty (
        OMMItemEvent itemEvent,
        MessageHeaders headers
    ) {
        Handle handle = itemEvent.getHandle();

        TimeSeriesEntries timeSeriesEntries = timeSeriesEntriesCache.get(handle);

        return timeSeriesEntries.isRicListEmpty();
    }
}
