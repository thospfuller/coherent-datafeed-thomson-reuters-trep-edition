package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.SessionUtils.assertNotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
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

    private static final Logger log =
        LoggerFactory.getLogger(LoadTimeSeriesEntriesMessageProcessor.class);

    private final TimeSeriesLoader timeSeriesLoader;

    public LoadTimeSeriesEntriesMessageProcessor (
        TimeSeriesLoader timeSeriesLoader
    ) {
        this.timeSeriesLoader = timeSeriesLoader;
    }

    @Override
//    @Transactional
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        log.info("loadTimeSeriesEntriesMessageProcessor.process: method " +
            "begins; message: " + message);

        // We need to sync here as the timeSeriesEntries may still be null when
        // the message is returned from RFA.

        OMMItemEvent itemEvent = message.getPayload();

        Handle handle = itemEvent.getHandle();

        MessageHeaders headers = message.getHeaders();

        Session session = (Session) headers.get(SESSION);

        synchronized (session) {

            assertNotNull (session);

            TimeSeriesEntries timeSeriesEntries =
                session.getTimeSeriesEntries(handle);

            log.info("session: " + ToStringBuilder.reflectionToString(session));
            log.info("handle: " + ToStringBuilder.reflectionToString(handle));

            timeSeriesLoader.load(itemEvent, timeSeriesEntries);
        }

        log.info("loadTimeSeriesEntriesMessageProcessor.process: method " +
            "ends; message: " + message);

        return message;
    }
}
