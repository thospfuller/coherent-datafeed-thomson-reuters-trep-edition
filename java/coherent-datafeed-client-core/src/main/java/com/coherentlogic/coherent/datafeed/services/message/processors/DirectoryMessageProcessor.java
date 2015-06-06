package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertEquals;
import static com.coherentlogic.coherent.datafeed.misc.SessionUtils.assertNotNull;

import java.util.List;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;

import com.coherentlogic.coherent.datafeed.services.DirectoryServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DirectoryMessageProcessor
    implements MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log =
        LoggerFactory.getLogger(DirectoryMessageProcessor.class);

    private final DirectoryServiceSpecification directoryService;

    private final Cache<Handle, Session> directoryCache;

    private DirectoryMessageProcessor(
        DirectoryServiceSpecification directoryService,
        Cache<Handle, Session> directoryCache
    ) {
        this.directoryService = directoryService;
        this.directoryCache = directoryCache;
    }

    /**
     * @todo Move this method into its own DirectoryMessageProcessor.
     */
    @Override
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        log.info ("process: method begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        Session session = (Session) headers.get(SESSION);

        assertNotNull (session);

        Handle loginHandle = session.getLoginHandle();

        List<Handle> directoryHandles = directoryService.query(loginHandle);

        int size = directoryHandles.size();

        assertEquals("directoryHandles", 1, size);

        // We also need to be able to associate a directory handle with a
        // session, that way when RFA returns directory information, we can put
        // the correct session in the message headers.
        for (Handle directoryHandle : directoryHandles)
            directoryCache.put(directoryHandle, session);

        assertEquals("directoryCache.size", 1, directoryCache.size());

        log.info ("process: method ends.");

        return message;
    }
}
