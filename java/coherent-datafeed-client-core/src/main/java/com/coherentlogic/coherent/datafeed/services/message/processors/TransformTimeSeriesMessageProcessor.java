package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.HANDLE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.adapters.AbstractAdapter;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

public class TransformTimeSeriesMessageProcessor
    implements MessageProcessorSpecification<OMMItemEvent, TimeSeries> {

    private final AbstractAdapter<TimeSeriesEntries, TimeSeries>
        timeSeriesAdapter;

    public TransformTimeSeriesMessageProcessor(
        AbstractAdapter<TimeSeriesEntries, TimeSeries> timeSeriesAdapter
    ) {
        this.timeSeriesAdapter = timeSeriesAdapter;
    }

    /**
     * @todo We can share some of this code with the TimeSeriesHelper class.
     */
    @Override
    public Message<TimeSeries> process(Message<OMMItemEvent> message) {

        MessageHeaders headers = message.getHeaders();

        Session session = (Session) headers.get(SESSION);

        assertNotNull (SESSION, session);

        OMMItemEvent itemEvent = message.getPayload();

        Handle handle = itemEvent.getHandle();

        TimeSeriesEntries timeSeriesEntries =
            session.getTimeSeriesEntries(handle);

        TimeSeries timeSeries = timeSeriesAdapter.adapt(timeSeriesEntries);

        Message<TimeSeries> result = MessageBuilder
            .withPayload(timeSeries)
            .copyHeaders(headers)
            .setHeader(HANDLE, handle)
            .build();

        return result;
    }
}
