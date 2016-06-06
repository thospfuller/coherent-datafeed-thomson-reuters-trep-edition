package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Utils.getSeries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.caches.DictionaryEntryCache;
import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMSeries;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class DictionaryStatusMonitorService
    implements MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log = LoggerFactory.getLogger(DictionaryStatusMonitorService.class);

    private final DictionaryEntryCache dictionaryEntryCache;

    public DictionaryStatusMonitorService(DictionaryEntryCache dictionaryEntryCache) {
        this.dictionaryEntryCache = dictionaryEntryCache;
    };

    @Override
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        MessageHeaders headers = message.getHeaders();

        OMMItemEvent itemEvent = message.getPayload();

        Handle handle = itemEvent.getHandle();

        OMMSeries series = getSeries (itemEvent);

        DictionaryEntry dictionaryServiceEnty = dictionaryEntryCache.findDictionaryServiceEntry(handle);//, dictionaryId);

        dictionaryServiceEnty.setLoaded(true);

        log.info("dictionaryServiceEnty: " + dictionaryServiceEnty);

        return message;
    }
}
