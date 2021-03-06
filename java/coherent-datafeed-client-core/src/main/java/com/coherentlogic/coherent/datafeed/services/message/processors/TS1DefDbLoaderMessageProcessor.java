package com.coherentlogic.coherent.datafeed.services.message.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.coherentlogic.coherent.datafeed.caches.TS1DefEntryCache;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.TS1DefDbLoader;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * This class simply invokes the {@link #ts1DefDbLoader} <i>load</I> method and
 * then returns the same message that the <i>load</i> method was called with.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefDbLoaderMessageProcessor implements MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log = LoggerFactory.getLogger(TS1DefDbLoaderMessageProcessor.class);

    private final TS1DefDbLoader ts1DefDbLoader;

    private final TS1DefEntryCache ts1DefEntryCache;

    public TS1DefDbLoaderMessageProcessor(TS1DefDbLoader ts1DefDbLoader, TS1DefEntryCache ts1DefEntryCache) {
        this.ts1DefDbLoader = ts1DefDbLoader;
        this.ts1DefEntryCache = ts1DefEntryCache;
    }

    @Override
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        log.debug("process: method begins; message: " + message);

        OMMItemEvent itemEvent = message.getPayload();

        ts1DefDbLoader.load(itemEvent);

        Handle handle = itemEvent.getHandle();

        TS1DefEntry entry = ts1DefEntryCache.getTS1DefEntry(handle);

        entry.setLoaded(true);

        log.debug("process: method ends; message: " + message);

        return message;
    }

}
