package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.domain.DictionaryEntry;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * A service which reviews the number of dictionaries being loaded and then
 * calls the {@link DictionaryService#setLoaded(boolean)} method when
 * the load process has completed.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class DictionaryLoadCompleteService
    implements MessageProcessorSpecification<OMMItemEvent, OMMItemEvent> {

    private static final Logger log =
        LoggerFactory.getLogger(DictionaryLoadCompleteService.class);

    public static final String BEAN_NAME = "dictionaryLoadCompleteService";

    @Override
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {
        throw new RuntimeException ("This method has not been implemented.");
    }

    /**
     * @todo This needs to go into its own class.
     */
    public boolean allDictionariesAreLoaded (MessageHeaders headers) {

        Session session = (Session) headers.get(SESSION);

        boolean result = session.allDictionariesAreLoaded();

//        for (Entry<Handle, DictionaryEntry> next : session.getDictionaryMap().entrySet()) {
//            log.info("===== next: " + ToStringBuilder.reflectionToString(next));
//        }

        log.info("allDictionariesAreLoaded: method ends; result: " + result);

        return result;
    }

    /**
     * Method find the {@link DictionaryEntry} and marks it as loaded.
     */
    public Message<OMMItemEvent> markAsLoaded (Message<OMMItemEvent> message) {
        log.info("markAsLoaded: method begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        Session session = (Session) headers.get(SESSION);

        OMMItemEvent itemEvent = (OMMItemEvent) message.getPayload();

        Handle handle = itemEvent.getHandle();

        String dictionaryName = getDictionaryName (itemEvent);

        log.info("handle: " + handle + ", dictionaryName: " + dictionaryName);

        DictionaryEntry dictionaryEntry =
            session.findDictionaryServiceEntry(handle);

        assertNotNull ("dictionaryServiceEntry", dictionaryEntry);

        dictionaryEntry.setLoaded(true);

        log.info("markAsLoaded: method ends; dictionaryServiceEntry: " +
            dictionaryEntry);

        return message;
    }

    /**
     * Method returns the dictionary name in the itemEvent's attribute
     * information.
     */
    String getDictionaryName (OMMItemEvent itemEvent) {

        OMMMsg msg = itemEvent.getMsg();

        OMMAttribInfo attribInfo = msg.getAttribInfo();

        String result = attribInfo.getName();

        return result;
    }
}
