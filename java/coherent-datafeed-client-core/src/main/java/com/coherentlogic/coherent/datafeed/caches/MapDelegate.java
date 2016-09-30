package com.coherentlogic.coherent.datafeed.caches;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <K>
 * @param <V>
 */
public class MapDelegate<K, V> implements Map<K, V> {

    private final Map<K, V> cache;

    public MapDelegate(Map<K, V> cache) {
        this.cache = cache;
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return cache.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return cache.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return cache.get(key);
    }

    @Override
    public V put(K key, V value) {
        return cache.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return cache.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        cache.putAll(map);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public Set<K> keySet() {
        return cache.keySet();
    }

    @Override
    public Collection<V> values() {
        return cache.values();
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return cache.entrySet();
    }

    public Map<K, V> getCache() {
        return cache;
    }
}
