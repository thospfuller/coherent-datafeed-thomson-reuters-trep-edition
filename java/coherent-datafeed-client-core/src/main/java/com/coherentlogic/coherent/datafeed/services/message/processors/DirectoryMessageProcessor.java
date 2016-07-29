package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertEquals;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.services.DirectoryServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Handle -> dacsId -> DirectoryEntry(s)
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DirectoryMessageProcessor implements MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log = LoggerFactory.getLogger(DirectoryMessageProcessor.class);

    private final DirectoryServiceSpecification directoryService;

    private final Map<Handle, DirectoryEntry> directoryCache;

    private DirectoryMessageProcessor(
        DirectoryServiceSpecification directoryService,
        Map<Handle, DirectoryEntry> directoryCache
    ) {
        this.directoryService = directoryService;
        this.directoryCache = directoryCache;
    }

    /**
     *
     */
    @Override
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        MessageHeaders headers = message.getHeaders();

        SessionBean sessionBean = (SessionBean) headers.get(SESSION);

        List<Handle> directoryHandles = directoryService.query(sessionBean);

        int size = directoryHandles.size();

        log.debug("directoryHandles.size: " + directoryHandles.size ());

        assertEquals("directoryHandles", 1, size);

        // We also need to be able to associate a directory handle with a
        // session, that way when RFA returns directory information, we can put
        // the correct session in the message headers.
        for (Handle directoryHandle : directoryHandles)
            directoryCache.put(directoryHandle, new DirectoryEntry ());

        // Not sure why we expect the directoryCache.size to only ever be one -- may want to explore this further, or it
        // could be a copy/paste error.
        assertEquals("directoryCache.size", 1, directoryCache.size());

        return message;
    }
}
