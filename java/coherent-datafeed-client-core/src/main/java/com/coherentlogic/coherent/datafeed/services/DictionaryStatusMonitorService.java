package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.getSeries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;

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

    private static final Logger log =
        LoggerFactory.getLogger(DictionaryStatusMonitorService.class);

    private final OMMSeriesHelper seriesHelper;

    public DictionaryStatusMonitorService() {
        this (new OMMSeriesHelper());
    };

    public DictionaryStatusMonitorService(OMMSeriesHelper seriesHelper) {
        this.seriesHelper = seriesHelper;
    };

    @Override
    public Message<OMMItemEvent> process(Message<OMMItemEvent> message) {

        MessageHeaders headers = message.getHeaders();

        Session session = (Session) headers.get(SESSION);

        OMMItemEvent itemEvent = message.getPayload();

        Handle handle = itemEvent.getHandle();

        OMMSeries series = getSeries (itemEvent);

//        String dictionaryId = seriesHelper.findDictionaryId(series);

        DictionaryEntry dictionaryServiceEnty =
            session.findDictionaryServiceEntry(handle);//, dictionaryId);

        dictionaryServiceEnty.setLoaded(true);

        log.info("dictionaryServiceEnty: " + dictionaryServiceEnty);

        return message;
    }
}
