package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.caches.DictionaryEntryCache;
import com.coherentlogic.coherent.datafeed.caches.DirectoryEntryCache;
import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * A service which reviews the number of dictionaries being loaded and then
 * calls the {@link DictionaryService#setLoaded(boolean)} method when
 * the load process has completed.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DictionaryLoadCompleteService {

    private static final Logger log = LoggerFactory.getLogger(DictionaryLoadCompleteService.class);

    public static final String BEAN_NAME = "dictionaryLoadCompleteService";

    private final DictionaryEntryCache dictionaryEntryCache;

    public DictionaryLoadCompleteService(
        DirectoryEntryCache directoryEntryCache,
        DictionaryEntryCache dictionaryEntryCache
    ) {
        this.dictionaryEntryCache = dictionaryEntryCache;
    }

    /**
     * @todo This needs to go into its own class.
     */
    public boolean allDictionariesAreLoaded (MessageHeaders headers) {

        boolean result = dictionaryEntryCache.allDictionariesAreLoaded();

        log.debug("allDictionariesAreLoaded: method ends; result: " + result);

        return result;
    }

//    /**
//     * @return True when the loaded flag for ever dictionary has been set to true otherwise this method returns false.
//     */
//    public boolean allDictionariesAreLoaded () {
//
//        boolean result = true;
//
//        Set<Entry<Handle, DictionaryEntry>> dictionaryEntries = getDictionaryEntryCache().entrySet();
//
//        Iterator<Entry<Handle, DictionaryEntry>> iterator = dictionaryEntries.iterator();
//
//        while (iterator.hasNext()) {
//
//            Entry<Handle, DictionaryEntry> nextEntry = iterator.next();
//
//            DictionaryEntry nextDictionaryServiceEntry = nextEntry.getValue();
//
//            boolean subResult = nextDictionaryServiceEntry.isLoaded();
//
//            if (!subResult) {
//                result = false;
//                break;
//            }
//        }
//        return result;
//    }
//
//    public DictionaryEntry findDictionaryServiceEntry (Handle handle) {
//
//        assertNotNull (HANDLE, handle);
//  
//        Map<Handle, DictionaryEntry> dictionaryServiceEntriesMap = getDictionaryEntryCache();
//  
//        DictionaryEntry result = dictionaryServiceEntriesMap.get(handle);
//  
//        return result;
//    }

    /**
     * Method find the {@link DictionaryEntry} and marks it as loaded.
     */
    public Message<OMMItemEvent> markAsLoaded (Message<OMMItemEvent> message) {

        log.debug("markAsLoaded: method begins; message: " + message);

//        MessageHeaders headers = message.getHeaders();
//
//        Session session = (Session) headers.get(SESSION);

        OMMItemEvent itemEvent = (OMMItemEvent) message.getPayload();

        Handle handle = itemEvent.getHandle();

        String dictionaryName = getDictionaryName (itemEvent);

        log.debug("handle: " + handle + ", dictionaryName: " + dictionaryName);

        DictionaryEntry dictionaryEntry = dictionaryEntryCache.findDictionaryServiceEntry(handle);

        assertNotNull ("dictionaryServiceEntry", dictionaryEntry);

        dictionaryEntry.setLoaded(true);

        log.debug("markAsLoaded: method ends; dictionaryServiceEntry: " + dictionaryEntry);

        return message;
    }

    /**
     * Method returns the dictionary name in the itemEvent's attribute
     * information.
     */
    String getDictionaryName (OMMItemEvent itemEvent) {

        OMMMsg msg = itemEvent.getMsg();

        OMMAttribInfo attribInfo = msg.getAttribInfo();

        String result = attribInfo.getName();

        return result;
    }
}
