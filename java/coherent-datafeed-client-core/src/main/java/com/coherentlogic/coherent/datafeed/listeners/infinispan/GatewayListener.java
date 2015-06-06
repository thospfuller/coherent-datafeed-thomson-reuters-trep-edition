package com.coherentlogic.coherent.datafeed.listeners.infinispan;

import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.gateway.MessagingGatewaySupport;

import com.coherentlogic.coherent.datafeed.beans.CachedEntry;
import com.reuters.rfa.common.Handle;

/**
 * @deprecated Do we even need this?
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <T> The type of object stored in the Cache.
 */
public class GatewayListener<T extends CachedEntry>
    extends MessagingGatewaySupport
    implements CachedObjectListener <Handle, T> {

    private static final Logger log =
        LoggerFactory.getLogger(DefaultCachedObjectListener.class);

    @CacheEntryModified
    @Override
    public void onCacheEntryModified(
        CacheEntryModifiedEvent<Handle, T>
            modifiedEvent
    ) {
        T result = (T) modifiedEvent.getValue();

        super.send(result);
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
