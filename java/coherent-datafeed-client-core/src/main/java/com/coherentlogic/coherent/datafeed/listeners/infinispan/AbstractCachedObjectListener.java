package com.coherentlogic.coherent.datafeed.listeners.infinispan;

import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;

import com.coherentlogic.coherent.datafeed.beans.CachedEntry;
import com.reuters.rfa.common.Handle;

/**
 * An abstract class containing empty methods from the
 * {@link CachedObjectListener} that can be overridden by the developer.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AbstractCachedObjectListener<T extends CachedEntry>
    implements CachedObjectListener<Handle, T> {

    @Override
    public void onCacheEntryModified(
        CacheEntryModifiedEvent<Handle, T>
            modifiedEvent
    ) {}

    @Override
    public void onCacheEntryCreated(
        CacheEntryCreatedEvent<Handle, T>
            createdEvent
    ) {}

    @Override
    public void onCacheEntryRemoved(
        CacheEntryRemovedEvent<Handle, T>
            removedEvent
    ) {}
}
