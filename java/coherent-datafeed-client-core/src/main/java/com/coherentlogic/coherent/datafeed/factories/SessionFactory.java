package com.coherentlogic.coherent.datafeed.factories;

import java.util.Map;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.caches.TimeSeriesEntriesCache;
import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.exceptions.MethodNotSupportedException;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;

/**
 * @deprecated The Session is no longer being used.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class SessionFactory implements TypedFactory<Session> {

    private final Map<Handle, Map<String, DirectoryEntry>>
        directoryEntryCache;

    private final Map<Handle, DictionaryEntry> dictionaryEntryCache;

    private final Map<Handle, TS1DefEntry> ts1DefEntryCache;

    private final Map<Handle, TimeSeriesEntries> timeSeriesEntriesCache;

    public SessionFactory(
        Map<Handle, Map<String, DirectoryEntry>> directoryEntryCache,
        Map<Handle, DictionaryEntry> dictionaryEntryCache,
        Map<Handle, TS1DefEntry> ts1DefEntryCache,
        Map<Handle, TimeSeriesEntries> timeSeriesEntriesCache
    ) {
        super();
        this.directoryEntryCache = directoryEntryCache;
        this.dictionaryEntryCache = dictionaryEntryCache;
        this.ts1DefEntryCache = ts1DefEntryCache;
        this.timeSeriesEntriesCache = timeSeriesEntriesCache;
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
            ts1DefEntryCache,
            timeSeriesEntriesCache
        );

        return session;
    }
}
