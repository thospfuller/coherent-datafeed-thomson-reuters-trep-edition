package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;

import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.coherentlogic.coherent.datafeed.services.TS1DefDbLoader;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * This class simply invokes the {@link #ts1DefDbLoader} <i>load</I> method and
 * then returns the same message that the <i>load</i> method was called with.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefDbLoaderMessageProcessor
    implements MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log =
        LoggerFactory.getLogger(TS1DefDbLoaderMessageProcessor.class);

    private final TS1DefDbLoader ts1DefDbLoader;

    private TS1DefDbLoaderMessageProcessor(TS1DefDbLoader ts1DefDbLoader) {
        this.ts1DefDbLoader = ts1DefDbLoader;
    }

    long ctr = 0;

    @Override
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        log.info("process[" + (ctr++) + "]: method begins; message: " +
            message);

        MessageHeaders headers = message.getHeaders();

        Session session = (Session) headers.get(SESSION);

        log.info("session: " + session);

        OMMItemEvent itemEvent = message.getPayload();

        ts1DefDbLoader.load(itemEvent);

        Handle handle = itemEvent.getHandle();

        // TODO: test below.
        TS1DefEntry entry = session.getTS1DefEntry(handle);

        entry.setLoaded(true);

        log.info("process: method ends; message: " + message);

        return message;
    }

}
