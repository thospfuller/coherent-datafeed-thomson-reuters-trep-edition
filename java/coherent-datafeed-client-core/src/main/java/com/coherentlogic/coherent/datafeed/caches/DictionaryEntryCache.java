package com.coherentlogic.coherent.datafeed.caches;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.reuters.rfa.common.Handle;

public class DictionaryEntryCache extends MapDelegate<Handle, DictionaryEntry> {

    private static final Logger log = LoggerFactory.getLogger(DictionaryEntryCache.class);

    protected static final String HANDLE = "handle", DICTIONARY_SERVICE_ENTRY = "dictionaryServiceEntry",
        DICTIONARY_NAME = "dictionaryName";

    public DictionaryEntryCache (Map<Handle, DictionaryEntry> dictionaryEntryCache) {
        super (dictionaryEntryCache);
    }

    public DictionaryEntry findDictionaryServiceEntry (Handle handle) {

        assertNotNull ("handle", handle);

        DictionaryEntry result = get(handle);

        return result;
    }

    /**
     * Method attempts to find the {@link DictionaryEntry} associated with the
     * given dictionaryName.
     *
     * @return The dictionaryName or null if none was found.
     *
     * @todo Unit test this method. 
     */
    public DictionaryEntry findDictionaryServiceEntry (String dictionaryName) {

        assertNotNull (DICTIONARY_NAME, dictionaryName);

        DictionaryEntry result = null;

        for (Map.Entry<Handle, DictionaryEntry> dictionaryEntrySet : entrySet()) {

            DictionaryEntry nextDictionaryEntry = dictionaryEntrySet.getValue();

            String nextName = nextDictionaryEntry.getName();

            if (dictionaryName.equals(nextName)) {
                result = nextDictionaryEntry;
                break;
            }
        }
        return result;
    }

    /**
     * This method removes dictionaries that have already been loaded -- we do
     * this so that the framework is not making repeat calls to TREP for data
     * that it already has in-memory.
     */
    public String[] removeDuplicateDictionaries (
        String directoryName,
        Set<Entry <Handle, Map<String, DirectoryEntry>>> directories
    ) {

        Set<String> dictionarySet = getAllDictionariesUsed(directoryName, directories);

        Set<String> resultSet = new HashSet<String> ();

        for (String next : dictionarySet) {
            if (findDictionaryServiceEntry(next) == null) {

                resultSet.add(next);

                log.warn("The dictionary named " + next + " will be loaded (directoryName: " + directoryName + ")");

            } else
                log.warn("The dictionary named " + next + " has already been loaded (directoryName: " + directoryName +
                    ")");
        }

        String[] results = new String[resultSet.size()];

        results = resultSet.toArray(results);

        return results;
    }

    /**
     * @todo Rename this method to put or addDictionaryEntry.
     */
    public void putDictionary (Handle handle, DictionaryEntry dictionaryEntry) {

        assertNotNull(HANDLE, handle);
        assertNotNull(DICTIONARY_SERVICE_ENTRY, dictionaryEntry);

        put(handle, dictionaryEntry);
    }

    public Set<String> getAllDictionariesUsed (
        String directoryName, Set<Entry <Handle, Map<String, DirectoryEntry>>> directories
    ) {
        log.debug ("getAllDictionariesUsed: method invoked with directoryName: " + directoryName);

        Set<String> results = new HashSet<String> ();

//        Set<Entry <Handle, Map<String, DirectoryEntry>>> directories = directoryEntryCache.entrySet();

        for (Entry <Handle, Map<String, DirectoryEntry>> nextDirectory : directories) {

            Map<String, DirectoryEntry> directoryEntryMap = nextDirectory.getValue();

            Set<Entry <String, DirectoryEntry>> directoryEntrySet = directoryEntryMap.entrySet();

            for (Entry <String, DirectoryEntry> nextDirectoryEntry : directoryEntrySet) {

                log.debug("nextDirectoryEntry: " + ToStringBuilder.reflectionToString(nextDirectoryEntry));

                DirectoryEntry directoryEntry =
                    nextDirectoryEntry.getValue();

                if (directoryName.equals(directoryEntry.getName())) {
                    List<String> dictionariesUsed = directoryEntry.getDictionariesUsed();

                    results.addAll(dictionariesUsed);
                }
            }
        }
        return results;
    }

    /**
     * @return True when the loaded flag for ever dictionary has been set to true otherwise this method returns false.
     */
    public boolean allDictionariesAreLoaded () {

        boolean result = true;

        Set<Entry<Handle, DictionaryEntry>> dictionaryEntries = entrySet();

        Iterator<Entry<Handle, DictionaryEntry>> iterator = dictionaryEntries.iterator();

        while (iterator.hasNext()) {

            Entry<Handle, DictionaryEntry> nextEntry = iterator.next();

            DictionaryEntry nextDictionaryServiceEntry = nextEntry.getValue();

            boolean subResult = nextDictionaryServiceEntry.isLoaded();

            if (!subResult) {
                result = false;
                break;
            }
        }
        return result;
    }
}
