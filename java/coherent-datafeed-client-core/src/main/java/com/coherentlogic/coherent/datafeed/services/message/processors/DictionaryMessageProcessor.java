package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.LOGIN_HANDLE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
//import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.caches.DictionaryEntryCache;
import com.coherentlogic.coherent.datafeed.caches.DirectoryEntryCache;
import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.services.DictionaryServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * An instance of {@link MessageProcessorSpecification} that creates a list of all dictionaries used and then loads
 * those dictionaries by invoking the dictionaryService.loadDictionaries method.
 *
 * A handle can be associated with multiple DSEs. The reason this is set up this way is that when we query for several
 * dictionaries, one handle is returned, so we associate the one handle with a set of DictionaryEntry objects.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DictionaryMessageProcessor implements MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log = LoggerFactory.getLogger(DictionaryMessageProcessor.class);

    private final DictionaryServiceSpecification dictionaryService;

    private final DirectoryEntryCache directoryEntryCache;

    private final DictionaryEntryCache dictionaryEntryCache;

    public DictionaryMessageProcessor(
        DictionaryServiceSpecification dictionaryService,
        DirectoryEntryCache directoryEntryCache,
        DictionaryEntryCache dictionaryEntryCache
    ) {
        this.dictionaryService = dictionaryService;
        this.directoryEntryCache = directoryEntryCache;
        this.dictionaryEntryCache = dictionaryEntryCache;
    }

    /**
     * Method gets all dictionaries used from the session and then queries the
     * dictionary service for these dictionaries; 
     */
    @Override
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        MessageHeaders headers = message.getHeaders();

        SessionBean sessionBean = (SessionBean) headers.get(SESSION);

        Handle loginHandle = sessionBean.getHandle();

        assertNotNull(LOGIN_HANDLE, loginHandle);

        // eg. dIDN_RDF, IDN_RDF, dELEKTRON_DD, etc.
        Set<String> directoryNameSet = directoryEntryCache.getAllDirectoryNames();

        Iterator<String> iterator = directoryNameSet.iterator();

        while (iterator.hasNext()) {
            String directoryName = iterator.next();

            loadDictionariesFor (directoryName, sessionBean);
        }

        return message;
    }

    void loadDictionariesFor (String directoryName, SessionBean sessionBean) {

        log.info("loadDictionariesFor: method begins; directoryName: " + directoryName + ", sessionBean: " +
            sessionBean);

        Handle loginHandle = sessionBean.getHandle();

        Set<Entry <Handle, Map<String, DirectoryEntry>>> directories = directoryEntryCache.entrySet();

        // If the dictionary has been loaded already, then we don't want to
        // load it again, so we will trim the dictionaryNames so that duplicates
        // are not included when we invoke loadDictionaries.
        String[] dictionaryNames = dictionaryEntryCache.removeDuplicateDictionaries (directoryName, directories);

        if (dictionaryNames != null && 0 < dictionaryNames.length) {

            List<Handle> dictionaryHandles = dictionaryService.loadDictionaries(
                directoryName, sessionBean, dictionaryNames);

            int ctr = 0;

            for (String dictionaryName : dictionaryNames) {

                Handle nextHandle = dictionaryHandles.get(ctr);

                ctr++;

                DictionaryEntry dictionaryEntry = new DictionaryEntry (directoryName, dictionaryName);

                dictionaryEntryCache.putDictionary(nextHandle, dictionaryEntry);
            }
        }
    }

    /**
     * Method iterates over the keys and copies the dictionariesUsed into the set of dictionaryIds which will be loaded.
     */
    void copyDictionaryIdsInto (Cache<String, DirectoryEntry> directoryCache, Set<String> dictionaryIds) {

        Set<String> keys = directoryCache.keySet();

        for (String nextKey : keys) {

            log.debug("nextKey: " + nextKey);

            DirectoryEntry directoryEntry = directoryCache.get(nextKey);

            List<String> dictionariesUsed = directoryEntry.getDictionariesUsed();

            dictionaryIds.addAll(dictionariesUsed);
        }
    }
}
