package com.coherentlogic.coherent.datafeed.factories;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.exceptions.MethodNotSupportedException;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class SessionFactory implements Factory<Session> {

    private final Map<Handle, Map<String, DirectoryEntry>>
        directoryEntryCache;

    private final Map<Handle, DictionaryEntry> dictionaryEntryCache;

    private final Map<Handle, MarketPrice> marketPriceEntryCache;

    private final Map<Handle, MarketByOrder> marketByOrderEntryCache;

    private final Map<Handle, TS1DefEntry> ts1DefEntryCache;

    private final Map<Handle, TimeSeriesEntries> timeSeriesEntryCache;

    public SessionFactory(
        Map<Handle, Map<String, DirectoryEntry>> directoryEntryCache,
        Map<Handle, DictionaryEntry> dictionaryEntryCache,
        Map<Handle, MarketPrice> marketPriceEntryCache,
        Map<Handle, MarketByOrder> marketByOrderEntryCache,
        Map<Handle, TS1DefEntry> ts1DefEntryCache,
        Map<Handle, TimeSeriesEntries> timeSeriesEntryCache
    ) {
        super();
        this.directoryEntryCache = directoryEntryCache;
        this.dictionaryEntryCache = dictionaryEntryCache;
        this.marketPriceEntryCache = marketPriceEntryCache;
        this.marketByOrderEntryCache = marketByOrderEntryCache;
        this.ts1DefEntryCache = ts1DefEntryCache;
        this.timeSeriesEntryCache = timeSeriesEntryCache;
    }

    @Override
    public Session getInstance() {
        throw new MethodNotSupportedException(
            "The default getInstance method is not supported by this factory.");
    }

    public Session getInstance(Handle loginHandle) {

        Session session = new Session (
            directoryEntryCache,
            dictionaryEntryCache,
            marketPriceEntryCache,
            marketByOrderEntryCache,
            ts1DefEntryCache,
            timeSeriesEntryCache
        );

        return session;
    }
}
