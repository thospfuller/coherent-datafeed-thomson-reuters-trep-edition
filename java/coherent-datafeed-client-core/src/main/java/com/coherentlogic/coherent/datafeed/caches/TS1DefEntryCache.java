package com.coherentlogic.coherent.datafeed.caches;

import java.util.Map;
import java.util.Set;

import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.reuters.rfa.common.Handle;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefEntryCache extends MapDelegate<Handle, TS1DefEntry> {

	public static final String BEAN_NAME = "ts1DefEntryCache";

    public TS1DefEntryCache(Map<Handle, TS1DefEntry> cache) {
        super(cache);
    }

//    public void putTS1DefEntry (Handle handle, TS1DefEntry ts1DefEntry) {
//        put (handle, ts1DefEntry);
//    }

    public TS1DefEntry getTS1DefEntry (Handle handle) {
        return get (handle);
    }

    public TS1DefEntry removeTS1DefEntry (Handle handle) {
        return remove (handle);
    }

    public Map<Handle, TS1DefEntry> getTS1DefEntryCache () {
        return this;
    }

    public boolean allTS1DefDbEntriesAreLoaded (Set<Map.Entry<Handle, TS1DefEntry>> ts1DefEntries) {

        boolean result = true;

        for (Map.Entry<Handle, TS1DefEntry> nextEntry : ts1DefEntries) {

            TS1DefEntry ts1DefEntry = nextEntry.getValue();

            if (!ts1DefEntry.isLoaded()) {
                result = false;
                break;
            }
        }
        return result;
    }

    public boolean allTS1DefDbEntriesAreLoaded () {
        return allTS1DefDbEntriesAreLoaded (entrySet());
    }
}
