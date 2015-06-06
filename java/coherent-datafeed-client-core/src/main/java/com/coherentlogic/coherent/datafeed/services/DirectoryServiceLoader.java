package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.SessionUtils.getSession;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;

import com.coherentlogic.coherent.datafeed.adapters.DirectoryEntryAdapter;
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
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DirectoryServiceLoader {

    private static final Logger log = LoggerFactory
        .getLogger(DirectoryServiceLoader.class);

    private final DirectoryEntryAdapter serviceEntryAdapter;

    public DirectoryServiceLoader(
        DirectoryEntryAdapter serviceEntryAdapter
    ) {
        this.serviceEntryAdapter = serviceEntryAdapter;
    }

    /**
     * @todo Check if the message is an add, update, or delete message.
     */
    public Message<OMMItemEvent> load(Message<OMMItemEvent> message) {

        log.warn("load: method begins; message: " + message);

        Session session = getSession(message);

        OMMItemEvent ommItemEvent = message.getPayload();
        OMMMsg msg = ommItemEvent.getMsg();

        Handle handle = ommItemEvent.getHandle();

        OMMMap serviceMap = getServiceMap (ommItemEvent);

        executeActionOn (msg, serviceMap, handle, session);

        log.warn("load: method ends.");

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
            throw new InvalidDataTypeException("Expected an OMMTypes.MAP, "
                    + "however received the following dataType: "
                    + OMMTypes.toString(dataType));

        OMMMap serviceMap = (OMMMap) msg.getPayload();

        return serviceMap;
    }

    List<DirectoryEntry> transform (
        OMMMsg msg,
        OMMMapEntry mapEntry
    ) {
        short mapEntryDataType = mapEntry.getDataType();

        if (mapEntryDataType != OMMTypes.FILTER_LIST)
            throw new InvalidDataTypeException(
                    "Expected an "
                            + "OMMTypes.FILTER_LIST, however received "
                            + "the following dataType: "
                            + OMMTypes.toString(mapEntryDataType));

        List<DirectoryEntry> directoryEntries =
            transform(msg);

        return directoryEntries;
    }

    /**
     * Method converts the map into an instance of {@link DirectoryEntry}
     * and returns the result.
     */
    List<DirectoryEntry> transform(OMMMsg msg) {

        List<DirectoryEntry> directoryEntries =
            serviceEntryAdapter.adapt(msg);

        return directoryEntries;
    }

    void executeActionOn (
        OMMMsg msg,
        OMMMap serviceMap,
        Handle handle,
        Session session
    ) {
        Map<String, DirectoryEntry> directoryServiceEntryMap =
            session.getDirectoryServiceEntryCache(handle);

        for (
            Iterator<?> iterator = serviceMap.iterator();
            iterator.hasNext();
        ) {
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
    void executeActionOn(List<DirectoryEntry> directoryEntries,
        Map<String, DirectoryEntry> directoryServiceEntryCache) {

        log.warn("directoryServiceEntries.size: "
                + directoryEntries.size());

        for (DirectoryEntry entry : directoryEntries) {

            String key = entry.getName();

            ActionType actionType = entry.getActionType();

            log.info ("actionType: " + actionType + ", name (key): " + key +
                ", entry: " + entry);

            if (actionType == ActionType.ADD
                ||
                actionType == ActionType.UPDATE
            ) {
                directoryServiceEntryCache.put(key, entry);
            } else
                directoryServiceEntryCache.remove(key);
        }
    }
}
//
//OMMMapEntry mapEntry = (OMMMapEntry) iterator.next();
//
//short mapEntryDataType = mapEntry.getDataType();
//
//if (mapEntryDataType != OMMTypes.FILTER_LIST)
//  throw new InvalidDataTypeException(
//          "Expected an "
//                  + "OMMTypes.FILTER_LIST, however received "
//                  + "the following dataType: "
//                  + OMMTypes.toString(mapEntryDataType));
//
//List<DirectoryEntry> directoryEntries =
//  transform(msg);
//
//Map<String, DirectoryEntry> directoryServiceEntryMap =
//  session.getDirectoryServiceEntryCache(handle);
//
//executeActionOn(directoryEntries, directoryServiceEntryMap);
//}
