package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.domain.DictionaryEntries;
import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.coherentlogic.coherent.datafeed.exceptions.MethodNotSupportedException;
import com.coherentlogic.coherent.datafeed.services.DictionaryGatewaySpecification;
import com.reuters.rfa.common.Handle;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetDictionaryEntriesMessageProcessor
    implements DictionaryGatewaySpecification {

    private static final Logger log =
        LoggerFactory.getLogger(GetDictionaryEntriesMessageProcessor.class);

    private final Map<Handle, DictionaryEntry> dictionaryEntryCache;

    public GetDictionaryEntriesMessageProcessor(
        Map<Handle, DictionaryEntry> dictionaryEntryCache
    ) {
        super();
        this.dictionaryEntryCache = dictionaryEntryCache;
    }

    @Override
    public DictionaryEntries getDictionaryEntries() {

        log.info("getDictionaryEntries: method begins.");

        Set<DictionaryEntry> results = new HashSet<DictionaryEntry> ();

        Set<Entry<Handle, DictionaryEntry>> dictionaryEntrySet =
            dictionaryEntryCache.entrySet();

        for (Entry<Handle, DictionaryEntry> nextEntry : dictionaryEntrySet) {

            DictionaryEntry dictionaryEntry = nextEntry.getValue();

            results.add(dictionaryEntry);
        }

        log.info("getDictionaryEntries: method ends.");

        return new DictionaryEntries (results);
    }

    @Override
    public String getDictionaryEntriesAsJSON() {
        // This method should be unnecessary and IIRC this was working without it whereas the directory equiv had some
        // problem and once the method was added the issue went away.
        throw new MethodNotSupportedException();
    }
}
