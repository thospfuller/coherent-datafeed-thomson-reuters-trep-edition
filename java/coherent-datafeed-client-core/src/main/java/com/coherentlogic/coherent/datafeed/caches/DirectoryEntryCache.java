package com.coherentlogic.coherent.datafeed.caches;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.infinispan.Cache;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidApplicationSessionException;
import com.reuters.rfa.common.Handle;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DirectoryEntryCache extends MapDelegate<Handle, Map<String, DirectoryEntry>> {

	/*
        @Listener
        class CacheListener {
        	@CacheEntryCreated
        	public void cacheEntryCreated (CacheEntryCreatedEvent event) {
        		System.out.println("cacheEntryCreated.key: " + event.getKey() + ", value: " + event.getValue());
        		if (event.getValue() instanceof DirectoryEntry)
        			new Exception ("event.getValue() is a DirectoryEntry: ").printStackTrace();
        	}

        }
        
        ((Cache)getCache ()).getAdvancedCache().addListener(new CacheListener ());
	 */

    private static final Logger log = LoggerFactory.getLogger(DirectoryEntryCache.class);

    public DirectoryEntryCache(Map<Handle, Map<String, DirectoryEntry>> directoryEntryCache) {
        super (directoryEntryCache);
    }

    /**
     * @todo Rename this method to put or addDirectoryEntry.
     */
    public void putDirectory (Handle handle, DirectoryEntry directoryEntry) {

        assertNotNull(DictionaryEntryCache.HANDLE, handle);
        assertNotNull("directoryServiceEntry", directoryEntry);

        Map<String, DirectoryEntry> nameToDirectoryMap = get(handle);

        // This is an indicator that there may be a concurrency issue as this
        // should never happen.
        // @todo This should never happen but the question is should we bother checking for this?
        if (nameToDirectoryMap == null)
            throw new InvalidApplicationSessionException(
                "The handle " + handle + " pointed to a null nameToDirectoryMap entry.");

        String name = directoryEntry.getName();

        assertNotNull ("directoryServiceEntry.name", name);

        nameToDirectoryMap.put(name, directoryEntry);
    }

    /**
     * This method iterates over all {@link DirectoryServiceEntries} in the
     * directoryEntryCache and builds a set of strings that contain the
     * dictionariesUsed 
     */
    public Set<String> getAllDictionariesUsed (String directoryName) {

        log.debug ("getAllDictionariesUsed: method invoked with directoryName: " + directoryName);

        Set<String> results = new HashSet<String> ();

        Set<Entry <Handle, Map<String, DirectoryEntry>>> directories = entrySet();

        for (Entry <Handle, Map<String, DirectoryEntry>> nextDirectory : directories) {

         Map<String, DirectoryEntry> directoryEntryMap = nextDirectory.getValue();

         Set<Entry <String, DirectoryEntry>> directoryEntrySet = directoryEntryMap.entrySet();

         for (Entry <String, DirectoryEntry> nextDirectoryEntry : directoryEntrySet) {

             log.debug("nextDirectoryEntry: " + ToStringBuilder.reflectionToString(nextDirectoryEntry));

             DirectoryEntry directoryEntry = nextDirectoryEntry.getValue();

             if (directoryName.equals(directoryEntry.getName())) {
                 List<String> dictionariesUsed = directoryEntry.getDictionariesUsed();

                 results.addAll(dictionariesUsed);
             }
         }
     }
     return results;
 }

    public Set<String> getAllDirectoryNames () {

        Set<String> results = new HashSet<String> ();

        Set<Entry <Handle, Map<String, DirectoryEntry>>> directories = getCache().entrySet();

        for (Entry <Handle, Map<String, DirectoryEntry>> nextDirectory : directories) {

            Map<String, DirectoryEntry> directoryEntryMap = nextDirectory.getValue();

            getAllDirectoryNames (directoryEntryMap, results);
        }
        return results;
    }

    public void getAllDirectoryNames (Map<String, DirectoryEntry> directoryEntryMap, Set<String> results) {

        // Results, at least as we've written it above, cannot be a non-null
        // value, so we're only going to assertNotNull on the dEM.
        assertNotNull("directoryEntryMap", directoryEntryMap);

        Set<Entry <String, DirectoryEntry>> directoryEntrySet = directoryEntryMap.entrySet();

        for (Entry <String, DirectoryEntry> nextDirectoryEntry : directoryEntrySet) {
            String result = nextDirectoryEntry.getKey();
            results.add(result);
        }
    }

    /**
     * Method returns reference to the map of String:DirectoryEntry; note that if the reference is null a new reference
     * will be created and stored using the handle reference.
     */
    public Map<String, DirectoryEntry> getDirectoryServiceEntryCache (Handle handle) {
 
        assertNotNull("handle", handle);

        Map<String, DirectoryEntry> result = (Map<String, DirectoryEntry>) get (handle);

        if (result == null) {
            result = new HashMap<String, DirectoryEntry> ();
            put(handle, result);
        }
        return result;
    }
}
