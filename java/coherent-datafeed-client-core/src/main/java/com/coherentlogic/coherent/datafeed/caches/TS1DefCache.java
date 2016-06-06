package com.coherentlogic.coherent.datafeed.caches;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.reuters.rfa.common.Handle;

/**
 * @deprecated May be able to remove this class altogether.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefCache extends MapDelegate<Handle, TS1DefEntry> {

    public TS1DefCache(Map<Handle, TS1DefEntry> cache) {
        super(cache);
    }

//    public void putTS1DefEntry (Handle handle, TS1DefEntry ts1DefEntry) {
//        put(handle, ts1DefEntry);
//    }
//
//    public TS1DefEntry getTS1DefEntry (Handle handle) {
//        return get (handle);
//    }
//
//    public TS1DefEntry removeTS1DefEntry (Handle handle) {
//        return remove(handle);
//    }
//
//    public Map<Handle, TS1DefEntry> getTS1DefEntryCache () {
//        return this;
//    }
}
