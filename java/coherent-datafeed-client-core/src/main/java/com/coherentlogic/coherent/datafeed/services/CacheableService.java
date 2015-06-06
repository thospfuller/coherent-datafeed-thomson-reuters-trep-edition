package com.coherentlogic.coherent.datafeed.services;

import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.reuters.rfa.common.Client;

/**
 * An extension of {@link RequestService} that caches the query params along
 * with the handles.
 *
 * @deprecated This class should be reviewed and then removed if no longer
 *  needed. We should move the cache into the session along with the associated
 *  cache entry. Doing so should effective remove the need for this class.
 *
 * @deprecated We are no longer adding a handle : cachedEntry to a cache, rather
 *  we are associating a handle with a session and this happens at the service
 *  implementation level.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Transactional
public abstract class CacheableService
    extends RequestService {

    public CacheableService(
        RequestMessageBuilderFactory requestMessageBuilderFactory,
        Client client
    ) {
        super(requestMessageBuilderFactory, client);
    }

//    /**
//     * Adds the same cachedEntry to the cache under each handle in the handles
//     * list.
//     *
//     * @param handles A list of handles, each one a key.
//     *
//     * @param cachedEntry The cached entry which will be the value for each
//     *  handle key.
//     */
//    protected void putAll (List<Handle> handles, C cachedEntry) {
//        for (Handle nextHandle : handles) {
//            put(nextHandle, cachedEntry);
//        }
//    }
//
//    /**
//     * For Adds the cachedEntry to the cache under each handle in the handles
//     * list.
//     *
//     * @param handles A list of handles, each one a key.
//     *
//     * @param cachedEntry The cached entry which will be the value for each
//     *  handle key.
//     *
//     * @throws ValueOutOfBoundsException if the size of the handles list differs
//     *  from the size of the cachedEntries list.
//     */
//    protected void putAll (Map<Handle, C> entries) {
//        cache.putAll(entries);
//    }
//
//    /**
//     * Adds the {@link CachedEntry} to the cache using the {@link handle} as
//     * the key.
//     */
//    protected void put (Handle handle, C cachedEntry) {
//
//        if (cachedEntry == null)
//            throw new MissingDataException("An attempt was made to add an " +
//                "entry to the cache with a null cachedEntry reference.");
//
//        cache.put(handle, cachedEntry);
//    }
//
//    /**
//     * Method returns the instance of T associated with the handle.
//     */
//    protected C get (Handle handle) {
//        return cache.get(handle);
//    }
//
//    protected Cache<Handle, C> getCache() {
//        return cache;
//    }

//    protected Map<Handle, C> createCachedEntries(List<Handle> handles) {
//
//        int initialSize = handles.size();
//
//        Map<Handle, C> results = new HashMap<Handle, C> (initialSize);
//
//        for (Handle handle : handles) {
//
//            C next = createCachedEntry(handle);
//
//            results.put(handle, next);
//        }
//        return results;
//    }
//
//    /**
//     * Factory method for creating the {@link CachedEntry} that is used to hold
//     * the data in the cache.
//     *
//     * @param handle The handle, which is passed into the {@link CachedEntry}'s
//     *  constructor.
//     *
//     * @return An instance of <C>.
//     */
//    protected abstract C createCachedEntry (Handle handle);
}
