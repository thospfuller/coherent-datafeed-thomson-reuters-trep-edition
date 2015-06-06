package com.coherentlogic.coherent.datafeed.listeners.infinispan;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Listener
public interface CachedObjectListener<K, V> {

    void onCacheEntryModified (
        CacheEntryModifiedEvent<K, V> modifiedEvent
    );

    void onCacheEntryCreated (
        CacheEntryCreatedEvent<K, V> createdEvent);

    void onCacheEntryRemoved (
        CacheEntryRemovedEvent<K, V> removedEvent);
}
