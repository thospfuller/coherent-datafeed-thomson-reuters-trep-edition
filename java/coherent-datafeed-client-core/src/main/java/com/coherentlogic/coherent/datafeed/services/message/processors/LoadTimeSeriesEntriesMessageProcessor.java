package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesLoader;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * As this is being invoked by an RFA thread the Session should already be in
 * the message.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class LoadTimeSeriesEntriesMessageProcessor
    implements MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log = LoggerFactory.getLogger(LoadTimeSeriesEntriesMessageProcessor.class);

    private final TimeSeriesLoader timeSeriesLoader;

    private final Map<Handle, TimeSeriesEntries> timeSeriesEntriesCache;

    public LoadTimeSeriesEntriesMessageProcessor (
        TimeSeriesLoader timeSeriesLoader,
        Map<Handle, TimeSeriesEntries> timeSeriesEntriesCache
    ) {
        this.timeSeriesLoader = timeSeriesLoader;
        this.timeSeriesEntriesCache = timeSeriesEntriesCache;
    }

    @Override
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        log.info("loadTimeSeriesEntriesMessageProcessor.process: method begins; message: " + message);

        // We need to sync here as the timeSeriesEntries may still be null when
        // the message is returned from RFA.

        OMMItemEvent itemEvent = message.getPayload();

        Handle handle = itemEvent.getHandle();

//        MessageHeaders headers = message.getHeaders();
//
//        Session session = (SessionBean) headers.get(SESSION);

        TimeSeriesEntries timeSeriesEntries = timeSeriesEntriesCache.get(handle);

        timeSeriesLoader.load(itemEvent, timeSeriesEntries);

        log.info("loadTimeSeriesEntriesMessageProcessor.process: method ends; message: " + message);

        return message;
    }
}
