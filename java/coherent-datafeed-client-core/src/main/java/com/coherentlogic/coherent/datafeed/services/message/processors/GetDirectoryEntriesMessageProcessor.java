package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.domain.DirectoryEntries;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.exceptions.MethodNotSupportedException;
import com.coherentlogic.coherent.datafeed.services.DirectoryGatewaySpecification;
import com.reuters.rfa.common.Handle;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetDirectoryEntriesMessageProcessor
    implements DirectoryGatewaySpecification {

    private static final Logger log =
        LoggerFactory.getLogger(GetDirectoryEntriesMessageProcessor.class);

    private final Map<Handle, Map<Handle, DirectoryEntry>> directoryEntryCache;

    public GetDirectoryEntriesMessageProcessor(
        Map<Handle, Map<Handle, DirectoryEntry>> directoryEntryCache
    ) {
        super();
        this.directoryEntryCache = directoryEntryCache;
    }

    /**
     * Method iterates over the entries in the directoryEntryCache and adds
     * all instances of DirectoryEntry to an instance of DirectoryEntries
     * and returns the result.
     */
    @Override
    public DirectoryEntries getDirectoryEntries() {

        log.info("getDirectoryEntries: method begins.");

        Set<DirectoryEntry> directoryEntrySet = new HashSet<DirectoryEntry> ();

        DirectoryEntries directoryEntries =
            new DirectoryEntries (directoryEntrySet);

        Set<Entry<Handle, Map<Handle, DirectoryEntry>>> directoryEntryCacheSet =
            directoryEntryCache.entrySet();

        for (Entry<Handle, Map<Handle, DirectoryEntry>> nextEntry :
            directoryEntryCacheSet) {

            Map<Handle, DirectoryEntry> directoryEntryMap =
                nextEntry.getValue();

            Set<Entry<Handle, DirectoryEntry>> subDirectoryEntrySet =
                directoryEntryMap.entrySet();

            addAll (subDirectoryEntrySet, directoryEntrySet);

            log.info ("Adding a directoryEntrySet of size " + directoryEntrySet.size() + " to the subDirectoryEntrySet, which now has a size of " + subDirectoryEntrySet.size());
        }

        log.info("getDirectoryEntries: method ends.");

        return directoryEntries;
    }

    

    @Override
	public String getDirectoryEntriesAsJSON() {
		throw new MethodNotSupportedException();
	}

	void addAll (
        Set<Entry<Handle, DirectoryEntry>> source,
        Set<DirectoryEntry> target
    ) {
        for (Entry<Handle, DirectoryEntry> sourceEntry : source) {

            log.warn("addAll: handle: " + sourceEntry.getKey() + ", value: " + sourceEntry.getValue());

            DirectoryEntry result = sourceEntry.getValue();

            target.add(result);
        }
    }
}
