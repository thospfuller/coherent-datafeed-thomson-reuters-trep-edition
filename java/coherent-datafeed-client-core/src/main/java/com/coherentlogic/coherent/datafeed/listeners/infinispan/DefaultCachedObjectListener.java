package com.coherentlogic.coherent.datafeed.listeners.infinispan;

import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.beans.CachedEntry;
import com.reuters.rfa.common.Handle;

/**
 * A default implementation of the {@link CachedObjectListener}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DefaultCachedObjectListener<T extends CachedEntry>
    extends AbstractCachedObjectListener<T> {

    private static final Logger log =
        LoggerFactory.getLogger(DefaultCachedObjectListener.class);

    @CacheEntryModified
    @Override
    public void onCacheEntryModified(
        CacheEntryModifiedEvent<Handle, T>
            modifiedEvent
    ) {
        log.debug("modifiedEvent: " + modifiedEvent);
    }

    @CacheEntryCreated
    @Override
    public void onCacheEntryCreated(
        CacheEntryCreatedEvent<Handle, T>
            createdEvent
    ) {
        log.debug("createdEvent: " + createdEvent);
    }

    @CacheEntryRemoved
    @Override
    public void onCacheEntryRemoved(
        CacheEntryRemovedEvent<Handle, T> removedEvent
    ) {
        log.debug("removedEvent: " + removedEvent);
    }
}
