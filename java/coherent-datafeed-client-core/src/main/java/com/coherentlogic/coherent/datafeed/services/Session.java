package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;
import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.domain.TimeSeriesKey;
import com.reuters.rfa.common.Handle;

/**
 * This class is used to hold information that is collected as the workflow
 * progresses. For example, the application authenticates and adds a reference
 * to the loginHandle to an instance of this class. Next this message is sent
 * to the directory service where the dictionary names will be extracted
 * from the directories and then added here, etc.
 *
 * Note that there is only ever one session (at the moment there is only one
 * session per framework instance).
 *
 * @todo We should be able to cache the TimeSeries by the RIC and period that
 *  was used to originally request the data -- that way if the user requests the
 *  same RIC for a second time we can look it up locally and return the result.
 *
 * @todo Clean up this class as there are several methods here which could be
 *  combined and/or simplified.
 *
 * @todo Handle is not serializable so this bean cannot be serializable either
 *  -- might as well remove it.
 *
 * @todo Can we have just one cache?
 *
 * @todo Extend from SerializableBean.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class Session extends SerializableBean {

    private static final long serialVersionUID = 8397567572051054604L;

    private static final String HANDLE = "handle",
        DICTIONARY_SERVICE_ENTRY = "dictionaryServiceEntry",
        DICTIONARY_NAME = "dictionaryName";

    private static final Logger log =
        LoggerFactory.getLogger(Session.class);

    private String dacsId = null;

    private Handle loginHandle = null;

    /**
     * A handle can be associated with multiple DSEs and each DSE has a unique
     * name, which we can use to associate with a specific DSE.
     */
    private final Map<Handle, Map<String, DirectoryEntry>>
        directoryEntryCache;

    /**
     * A handle can be associated with multiple DSEs. The reason this is set up
     * this way is that when we query for several dictionaries, one handle is
     * returned, so we associate the one handle with a set of
     * DictionaryEntry objects.
     */
    private final Map<Handle, DictionaryEntry> dictionaryEntryCache;

    private final Map<Handle, TS1DefEntry> ts1DefEntryCache;

    private final Map<Handle, TimeSeriesEntries> timeSeriesEntryCache;

    /** @TODO: Set this property via the ctor.
     *  @deprecated See comment above.
     */
    private final Map<Handle, TimeSeriesKey> timeSeriesKeyCache =
        new HashMap<Handle, TimeSeriesKey> ();

    private PropertyChangeSupport propertyChangeSupport = null;

    public Session(
        Map<Handle, Map<String, DirectoryEntry>> directoryEntryCache,
        Map<Handle, DictionaryEntry> dictionaryEntryCache,
        Map<Handle, TS1DefEntry> ts1DefEntryCache,
        Map<Handle, TimeSeriesEntries> timeSeriesEntryCache
    ) {
        this.directoryEntryCache = directoryEntryCache;
        this.dictionaryEntryCache = dictionaryEntryCache;
        this.ts1DefEntryCache = ts1DefEntryCache;
        this.timeSeriesEntryCache = timeSeriesEntryCache;
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    public void setPropertyChangeSupport(
        PropertyChangeSupport propertyChangeSupport
    ) {
        this.propertyChangeSupport = propertyChangeSupport;
    }

    public String getDacsId() {
        return dacsId;
    }

    public void setDacsId(String dacsId) {
        this.dacsId = dacsId;
    }

    public Handle getLoginHandle() {
        return loginHandle;
    }

    public void setLoginHandle(Handle loginHandle) {
        this.loginHandle = loginHandle;
    }

    public Map<Handle, Map<String, DirectoryEntry>> getDirectoryCache() {
        return directoryEntryCache;
    }

//    /**
//     * @todo Rename this method to put or addDirectoryEntry.
//     */
//    public void putDirectory (
//        Handle handle,
//        DirectoryEntry directoryEntry
//    ) {
//        assertNotNull(HANDLE, handle);
//        assertNotNull("directoryServiceEntry", directoryEntry);
//
//        Map<String, DirectoryEntry> nameToDirectoryMap =
//            directoryEntryCache.get(handle);
//
//        // This is an indicator that there may be a concurrency issue as this
//        // should never happen.
//        // @todo This should never happen but the question is should we bother
//        //       checking for this?
//        if (nameToDirectoryMap == null)
//            throw new InvalidApplicationSessionException(
//                "The handle " + handle + " pointed to a null " +
//                "nameToDirectoryMap entry.");
//
//        String name = directoryEntry.getName();
//
//        assertNotNull ("directoryServiceEntry.name", name);
//
//        nameToDirectoryMap.put(name, directoryEntry);
//    }

    /**
     * @todo Rename this method to put or addDictionaryEntry.
     */
    public void putDictionary (
        Handle handle,
        DictionaryEntry dictionaryEntry
    ) {
        assertNotNull(HANDLE, handle);
        assertNotNull(DICTIONARY_SERVICE_ENTRY, dictionaryEntry);

        dictionaryEntryCache.put(handle, dictionaryEntry);
    }

    public DictionaryEntry findDictionaryServiceEntry (
        Handle handle
    ) {
        assertNotNull ("handle", handle);

        Map<Handle, DictionaryEntry> dictionaryServiceEntriesMap =
            getDictionaryCache();

        DictionaryEntry result = dictionaryServiceEntriesMap.get(handle);

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
    public DictionaryEntry findDictionaryServiceEntry (
        String dictionaryName
    ) {
        assertNotNull (DICTIONARY_NAME, dictionaryName);

        Map<Handle, DictionaryEntry> dictionaryServiceEntriesMap =
            getDictionaryCache();

        DictionaryEntry result = null;

        for (Map.Entry<Handle, DictionaryEntry> dictionaryEntrySet :
            dictionaryServiceEntriesMap.entrySet()) {

            DictionaryEntry nextDictionaryEntry = dictionaryEntrySet.getValue();

            String nextName = nextDictionaryEntry.getName();

            if (dictionaryName.equals(nextName)) {
                result = nextDictionaryEntry;
                break;
            }
        }
        return result;
    }

    public void putTS1DefEntry (Handle handle, TS1DefEntry ts1DefEntry) {
        ts1DefEntryCache.put(handle, ts1DefEntry);
    }

    public TS1DefEntry getTS1DefEntry (Handle handle) {
        return ts1DefEntryCache.get (handle);
    }

    public TS1DefEntry removeTS1DefEntry (Handle handle) {
        return ts1DefEntryCache.remove(handle);
    }

    public Map<Handle, TS1DefEntry> getTS1DefEntryCache () {
        return ts1DefEntryCache;
    }

    /**
     * @return True when the loaded flag for ever dictionary has been set to
     *  true otherwise this method returns false.
     */
    public boolean allDictionariesAreLoaded () {

        boolean result = true;

        Set<Entry<Handle, DictionaryEntry>> dictionaryEntries =
            dictionaryEntryCache.entrySet();

        Iterator<Entry<Handle, DictionaryEntry>> iterator =
            dictionaryEntries.iterator();

        while (iterator.hasNext()) {

            Entry<Handle, DictionaryEntry>
                nextEntry = iterator.next();

            DictionaryEntry nextDictionaryServiceEntry =
                nextEntry.getValue();

            boolean subResult = nextDictionaryServiceEntry.isLoaded();

            if (!subResult) {
                result = false;
                break;
            }
        }
        return result;
    }

    boolean allDictionariesAreLoaded (
        Set<DictionaryEntry> dictionaryEntries
    ) {
        boolean result = true;

        Iterator<DictionaryEntry> iterator =
            dictionaryEntries.iterator();

        while (iterator.hasNext()) {

            DictionaryEntry dictionaryEntry = iterator.next();

            if (!dictionaryEntry.isLoaded()) {
                result = false;
                break;
            }
        }
        return result;
    }

    public Set<String> getAllDirectoryNames () {

        Set<String> results = new HashSet<String> ();

        Set<Entry <Handle, Map<String, DirectoryEntry>>> directories =
            directoryEntryCache.entrySet();

        for (Entry <Handle, Map<String, DirectoryEntry>> nextDirectory :
            directories
        ) {
            Map<String, DirectoryEntry> directoryEntryMap =
                nextDirectory.getValue();

            getAllDirectoryNames (directoryEntryMap, results);
        }
        return results;
    }

    void getAllDirectoryNames (
        Map<String, DirectoryEntry> directoryEntryMap,
        Set<String> results
    ) {
        // Results, at least as we've written it above, cannot be a non-null
        // value, so we're only going to assertNotNull on the dEM.
        assertNotNull("directoryEntryMap", directoryEntryMap);

        Set<Entry <String, DirectoryEntry>> directoryEntrySet =
            directoryEntryMap.entrySet();

        for (Entry <String, DirectoryEntry> nextDirectoryEntry :
            directoryEntrySet
        ) {
            String result = nextDirectoryEntry.getKey();
            results.add(result);
        }
    }

    /**
     * Method returns reference to the map of String:DirectoryEntry; note
     * that if the reference is null a new reference will be created and stored
     * using the handle reference.
     */
    public Map<String, DirectoryEntry> getDirectoryServiceEntryCache (
        Handle handle
    ) {
        assertNotNull("handle", handle);

        Map<String, DirectoryEntry> result =
            directoryEntryCache.get (handle);

        if (result == null) {
            result = new HashMap<String, DirectoryEntry> ();
            directoryEntryCache.put(handle, result);
        }
        return result;
    }

    /**
     * This method iterates over all {@link DirectoryServiceEntries} in the
     * directoryEntryCache and builds a set of strings that contain the
     * dictionariesUsed 
     */
    public Set<String> getAllDictionariesUsed (String directoryName) {

        log.info ("getAllDictionariesUsed: method invoked with directoryName: " + directoryName);

        Set<String> results = new HashSet<String> ();

        Set<Entry <Handle, Map<String, DirectoryEntry>>> directories =
            directoryEntryCache.entrySet();

        for (Entry <Handle, Map<String, DirectoryEntry>> nextDirectory :
            directories
        ) {
            Map<String, DirectoryEntry> directoryEntryMap =
                nextDirectory.getValue();

            Set<Entry <String, DirectoryEntry>> directoryEntrySet =
                directoryEntryMap.entrySet();

            for (Entry <String, DirectoryEntry> nextDirectoryEntry :
                directoryEntrySet
            ) {
                log.debug("nextDirectoryEntry: " + ToStringBuilder.reflectionToString(nextDirectoryEntry));

                DirectoryEntry directoryEntry =
                    nextDirectoryEntry.getValue();

                if (directoryName.equals(directoryEntry.getName())) {
                    List<String> dictionariesUsed =
                        directoryEntry.getDictionariesUsed();

                    results.addAll(dictionariesUsed);
                }
            }
        }
        return results;
    }

    /**
     * This method iterates over all {@link DirectoryServiceEntries} in the
     * directoryEntryCache and builds a set of strings that contain the
     * dictionariesUsed
     *
     * @todo There's a better way to do this -- either pass a closure, or
     *  possibly create a map that has a name key pointing to all the DSE's w.
     *  that name.
     */
    public Set<String> getAllDictionariesUsedForDirectoryName (
        String directoryName) {

        Set<String> results = new HashSet<String> ();

        Set<Entry <Handle, Map<String, DirectoryEntry>>> directories =
            directoryEntryCache.entrySet();

        for (Entry <Handle, Map<String, DirectoryEntry>> nextDirectory :
            directories
        ) {
            Map<String, DirectoryEntry> directoryEntryMap =
                nextDirectory.getValue();

            Set<Entry <String, DirectoryEntry>> directoryEntrySet =
                directoryEntryMap.entrySet();

            for (Entry <String, DirectoryEntry> nextDirectoryEntry :
                directoryEntrySet
            ) {
                log.debug("nextDirectoryEntry: " +
                    ToStringBuilder.reflectionToString(nextDirectoryEntry));

                DirectoryEntry directoryEntry =
                    nextDirectoryEntry.getValue();

                if (directoryName.equals(directoryEntry.getName())) {
                    List<String> dictionariesUsed =
                        directoryEntry.getDictionariesUsed();
                    results.addAll(dictionariesUsed);
                    break;
                }
            }
        }
        return results;
    }

    public boolean allTS1DefDbEntriesAreLoaded (
        Set<Map.Entry<Handle, TS1DefEntry>> ts1DefEntries
    ) {
        boolean result = true;

        for (Map.Entry<Handle, TS1DefEntry> nextEntry : ts1DefEntries) {

            TS1DefEntry ts1DefEntry = nextEntry.getValue();

            if (!ts1DefEntry.isLoaded()) {
                result = false;
                break;
            }
        }
        return result;
    }

    public boolean allTS1DefDbEntriesAreLoaded () {
        return allTS1DefDbEntriesAreLoaded (ts1DefEntryCache.entrySet());
    }

    /**
     * @return A reference to the {@link #dictionaryEntryCache}, which holds references
     *  to the handles and associated {@link DictionaryEntry} objects.
     */
    public Map<Handle, DictionaryEntry> getDictionaryCache() {
        return dictionaryEntryCache;
    }

    public TimeSeriesEntries getTimeSeriesEntries (Handle handle) {
        return timeSeriesEntryCache.get(handle);
    }

    public TimeSeriesKey getTimeSeriesKey(Handle handle) {
        return timeSeriesKeyCache.get (handle);
    }

    /**
     * @param handle
     * @param timeSeriesKey
     */
    public void putTimeSeriesKey(Handle handle, TimeSeriesKey timeSeriesKey) {
        timeSeriesKeyCache.put (handle, timeSeriesKey);
    }

    public void putTimeSeriesEntries (
        Handle handle,
        TimeSeriesEntries timeSeriesEntries
    ) {
        timeSeriesEntryCache.put(handle, timeSeriesEntries);
    }

    public void end (Handle handle) {
        // Remove all entries in all caches associated with the given handle.
        // This should be handled differently for time series vs market price
        // workflows because, for example, the market price updates will
        // continue to come in as long as the request hasn't been terminated.
        throw new NotImplementedException("The 'end' method has not been implemented.");
    }

    public void addPropertyChangeListener (
        PropertyChangeListener propertyChangeListener
    ) {
        if (propertyChangeSupport != null)
            propertyChangeSupport.addPropertyChangeListener(
                propertyChangeListener);
    }

    public void removePropertyChangeListener (
        PropertyChangeListener propertyChangeListener
    ) {
        if (propertyChangeSupport != null)
            propertyChangeSupport.removePropertyChangeListener(
                propertyChangeListener);
    }
}
