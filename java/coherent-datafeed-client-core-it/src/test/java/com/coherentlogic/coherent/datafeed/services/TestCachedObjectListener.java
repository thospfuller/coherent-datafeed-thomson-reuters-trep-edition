package com.coherentlogic.coherent.datafeed.services;

import static org.junit.Assert.assertEquals;

import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;

import com.coherentlogic.coherent.datafeed.beans.CachedEntry;
import com.coherentlogic.coherent.datafeed.listeners.infinispan.AbstractCachedObjectListener;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.dictionary.FieldDictionary;

/**
 * We need to review the size of the field dictionary and Infinispan won't
 * accept this listener unless it's in a public class, which explains why we
 * have this.
 *
 * @deprecated This class is not currently used.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TestCachedObjectListener
    extends AbstractCachedObjectListener<CachedEntry> {

    private final FieldDictionary fieldDictionary;

    private final Handle handle;

    public TestCachedObjectListener(
        FieldDictionary fieldDictionary,
        Handle handle) {
        super();
        this.fieldDictionary = fieldDictionary;
        this.handle = handle;
    }

    @CacheEntryRemoved
    @Override
    public void onCacheEntryRemoved(
        CacheEntryRemovedEvent<Handle, CachedEntry> removedEvent
    ) {
        synchronized (handle) {

            CachedEntry cachedEntry = removedEvent.getValue();

            if (handle.equals(cachedEntry.getHandle())) {

                int size = fieldDictionary.size();

                // This is the correct number as of 14 Dec 2012 however it may
                // change so if this test starts failing, just review the number
                // that's returned from TR and adjust accordingly.
                assertEquals (6239 + 1, size);

                handle.notifyAll();
            }
        }
    }
}
