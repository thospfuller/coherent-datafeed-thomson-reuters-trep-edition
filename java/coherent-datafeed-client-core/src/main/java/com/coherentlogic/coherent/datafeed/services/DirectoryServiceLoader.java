package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.adapters.DirectoryEntryAdapter;
import com.coherentlogic.coherent.datafeed.caches.DirectoryEntryCache;
import com.coherentlogic.coherent.datafeed.domain.ActionType;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidDataTypeException;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMap;
import com.reuters.rfa.omm.OMMMapEntry;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMTypes;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Service endpoint adapter that loads the directory service entries into the
 * cache.
 * 
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DirectoryServiceLoader {

    private static final Logger log = LoggerFactory.getLogger(DirectoryServiceLoader.class);

    private final DirectoryEntryCache directoryEntryCache;

    private final DirectoryEntryAdapter directoryEntryAdapter;

    public DirectoryServiceLoader(
        DirectoryEntryCache directoryEntryCache,
        DirectoryEntryAdapter directoryEntryAdapter
    ) {
        this.directoryEntryCache = directoryEntryCache;
        this.directoryEntryAdapter = directoryEntryAdapter;
    }

    /**
     * @todo Check if the message is an add, update, or delete message.
     */
    public Message<OMMItemEvent> load(Message<OMMItemEvent> message) {

        OMMItemEvent ommItemEvent = message.getPayload();
        OMMMsg msg = ommItemEvent.getMsg();

        Handle handle = ommItemEvent.getHandle();

        OMMMap serviceMap = getServiceMap (ommItemEvent);

        executeActionOn (msg, serviceMap, handle);

        return message;
    }

    int getSize (Map<Handle, Map<String, DirectoryEntry>> directoryCache) {

        int result = 0;

        Set<Entry<Handle, Map<String, DirectoryEntry>>> topLevelEntrySet = directoryCache.entrySet();

        for (Entry<Handle, Map<String, DirectoryEntry>> entry : topLevelEntrySet) {
            result += entry.getValue().size();
        }
        return result;
    }

    OMMMap getServiceMap (OMMItemEvent ommItemEvent) {
        OMMMsg msg = ommItemEvent.getMsg();

        return getServiceMap (msg);
    }

    OMMMap getServiceMap (OMMMsg msg) {
        short dataType = msg.getDataType();

        if (dataType != OMMTypes.MAP)
            throw new InvalidDataTypeException("Expected an OMMTypes.MAP, however received the following dataType: "
                + OMMTypes.toString(dataType));

        OMMMap serviceMap = (OMMMap) msg.getPayload();

        return serviceMap;
    }

    List<DirectoryEntry> transform (OMMMsg msg, OMMMapEntry mapEntry) {

        short mapEntryDataType = mapEntry.getDataType();

        if (mapEntryDataType != OMMTypes.FILTER_LIST)
            throw new InvalidDataTypeException("Expected an OMMTypes.FILTER_LIST, however received the following "
                + "dataType: " + OMMTypes.toString(mapEntryDataType));

        List<DirectoryEntry> directoryEntries = transform(msg);

        return directoryEntries;
    }

    /**
     * Method converts the map into an instance of {@link DirectoryEntry}
     * and returns the result.
     */
    List<DirectoryEntry> transform(OMMMsg msg) {

        List<DirectoryEntry> directoryEntries =
            directoryEntryAdapter.adapt(msg);

        return directoryEntries;
    }

    /**
     * Method returns reference to the map of String:DirectoryEntry; note
     * that if the reference is null a new reference will be created and stored
     * using the handle reference.
     */
    public Map<String, DirectoryEntry> getDirectoryServiceEntryCache (Handle handle) {

        assertNotNull("handle", handle);

        Map<String, DirectoryEntry> result = directoryEntryCache.get (handle);

        if (result == null) {
            result = new HashMap<String, DirectoryEntry> ();
            directoryEntryCache.put(handle, result);
        }
        return result;
    }

    void executeActionOn (OMMMsg msg, OMMMap serviceMap, Handle handle) {

        Map<String, DirectoryEntry> directoryServiceEntryMap =
            directoryEntryCache.getDirectoryServiceEntryCache(handle);

        for (Iterator<?> iterator = serviceMap.iterator(); iterator.hasNext();) {

            OMMMapEntry mapEntry = (OMMMapEntry) iterator.next();

            List<DirectoryEntry> directoryEntries = transform(msg, mapEntry);

            executeActionOn(directoryEntries, directoryServiceEntryMap);
        }
    }

    /**
     * Method uses the actionType to either add, delete, or update the
     * {@link DirectoryEntry} in the directoryServiceEntryCache.
     *
     * @todo We need to test the update logic below.
     */
    void executeActionOn(
        List<DirectoryEntry> directoryEntries,
        Map<String, DirectoryEntry> directoryServiceEntryCache
    ) {
        log.debug("directoryServiceEntries.size: " + directoryEntries.size());

        for (DirectoryEntry entry : directoryEntries) {

            String key = entry.getName();

            ActionType actionType = entry.getActionType();

            log.debug ("actionType: " + actionType + ", name (key): " + key + ", entry: " + entry);

            if (actionType == ActionType.ADD || actionType == ActionType.UPDATE) {
                directoryServiceEntryCache.put(key, entry);
            } else
                directoryServiceEntryCache.remove(key);
        }
    }
}
