package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.SessionUtils.assertNotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.services.DictionaryServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * An instance of {@link MessageProcessorSpecification} that creates a list of
 * all dictionaries used and then loads those dictionaries by invoking the
 * dictionaryService.loadDictionaries method.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DictionaryMessageProcessor
    implements MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log =
        LoggerFactory.getLogger(DictionaryMessageProcessor.class);

    private final DictionaryServiceSpecification dictionaryService;

    private final Cache<Handle, Session> dictionaryCache;

    private DictionaryMessageProcessor(
        DictionaryServiceSpecification dictionaryService,
        Cache<Handle, Session> dictionaryCache
    ) {
        super();
        this.dictionaryService = dictionaryService;
        this.dictionaryCache = dictionaryCache;
    }

    /**
     * Method gets all dictionaries used from the session and then queries the
     * dictionary service for these dictionaries; 
     */
    @Override
    @Transactional
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        /* Note that it is possible that this method is paused prior and then
         * the DictionaryMessageEnricher.enrich method is executed, which
         * can result in an NPE progresses. The solution is to sync here and in
         * the FieldDictionaryHolder query method.
         *
         * @TODO: Investigate using transactions and the cache lock method as an
         *  alternative.
         */
//        synchronized (dictionaryCache) {
            MessageHeaders headers = message.getHeaders();

            Session session = (Session) headers.get(SESSION);

            assertNotNull (session);

            // eg. dIDN_RDF, IDN_RDF, dELEKTRON_DD, etc.
            Set<String> directoryNameSet = session.getAllDirectoryNames();

            Iterator<String> iterator = directoryNameSet.iterator();

            while (iterator.hasNext()) {
                String directoryName = iterator.next();

                loadDictionariesFor (session, directoryName);
            }
//        }
        return message;
    }

    /**
     * This method removes dictionaries that have already been loaded -- we do
     * this so that the framework is not making repeat calls to TREP for data
     * that it already has in-memory.
     */
    String[] removeDuplicateDictionaries (
        Session session,
        String directoryName
    ) {
        Set<String> dictionarySet = session.getAllDictionariesUsed(
            directoryName);

        Set<String> resultSet = new HashSet<String> ();

        for (String next : dictionarySet) {
            if (session.findDictionaryServiceEntry(next) == null) {
                resultSet.add(next);
                log.warn("The dictionary named " + next +
                    " will be loaded (directoryName: " + directoryName + ")");
            } else {
                log.warn(
                    "The dictionary named " + next +
                    " has already been loaded (directoryName: " +
                    directoryName + ")");
            }
        }

        String[] results = new String[resultSet.size()];

        results = resultSet.toArray(results);

        return results;
    }

    void loadDictionariesFor (Session session, String directoryName) {

        log.info("loadDictionariesFor: method begins; directoryName: " +
            directoryName);

        Handle loginHandle = session.getLoginHandle();

//        Set<String> dictionarySet = session.getAllDictionariesUsed(
//            directoryName);
//
//        log.info("dictionarySet: " + ToStringBuilder.
//            reflectionToString(dictionarySet));
//
//        int size = dictionarySet.size();
//
//        String[] dictionaryNames = new String[size];
//
//        dictionaryNames = dictionarySet.toArray(dictionaryNames);
//
//        log.info("dictionaryNames: " + ToStringBuilder.reflectionToString(
//            dictionaryNames));

        // If the dictionary has been loaded already, then we don't want to
        // load it again, so we will trim the dictionaryNames so that duplicates
        // are not included when we invoke loadDictionaries.
        String[] dictionaryNames = removeDuplicateDictionaries (session, directoryName);

        if (dictionaryNames != null && 0 < dictionaryNames.length) {

            List<Handle> dictionaryHandles = dictionaryService.loadDictionaries(
                directoryName, loginHandle, dictionaryNames);

            int ctr = 0;

            for (String dictionaryName : dictionaryNames) {

                Handle nextHandle = dictionaryHandles.get(ctr);

                ctr++;

                dictionaryCache.put(nextHandle, session);

                DictionaryEntry dictionaryEntry =
                    new DictionaryEntry (directoryName, dictionaryName);

                session.putDictionary(nextHandle, dictionaryEntry);
            }
        }
    }

    /**
     * Method iterates over the keys of the next sessionCache and copies the
     * dictionariesUsed into the set of dictionaryIds which will be loaded.
     */
    void copyDictionaryIdsInto (
        Cache<String, DirectoryEntry> directoryCache,
        Set<String> dictionaryIds
    ) {
        Set<String> keys = directoryCache.keySet();

        for (String nextKey : keys) {

            log.info("nextKey: " + nextKey);

            DirectoryEntry directoryEntry =
                directoryCache.get(nextKey);

            List<String> dictionariesUsed =
                directoryEntry.getDictionariesUsed();

            dictionaryIds.addAll(dictionariesUsed);
        }
    }
}
