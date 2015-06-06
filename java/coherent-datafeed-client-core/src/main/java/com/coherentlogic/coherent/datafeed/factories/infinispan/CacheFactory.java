package com.coherentlogic.coherent.datafeed.factories.infinispan;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import com.coherentlogic.coherent.datafeed.factories.Factory;
import com.coherentlogic.coherent.datafeed.listeners.infinispan.CachedObjectListener;

/**
 * A factory used to create instances of a named {@link Cache}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class CacheFactory<K, V> implements Factory<Cache<K, V>> {

    private final Cache<K, V> cache;

    public CacheFactory(
        CacheContainer cacheManager,
        String cacheName
    ) {
        this (
            ((Cache<K, V>) cacheManager.getCache(cacheName)),
            null
        );
    }

    public CacheFactory(
        CacheContainer cacheManager,
        String cacheName,
        CachedObjectListener<K, V> cachedObjectListener
    ) {
        this (
            ((Cache<K, V>) cacheManager.getCache(cacheName)),
            cachedObjectListener
        );
    }

    public CacheFactory(
        Cache<K, V> cache,
        CachedObjectListener<K, V> cachedObjectListener) {
        this.cache = cache;

        if (cachedObjectListener != null)
            cache.addListener(cachedObjectListener);
    }

    @Override
    public Cache<K, V> getInstance() {
        return cache;
    }
}
