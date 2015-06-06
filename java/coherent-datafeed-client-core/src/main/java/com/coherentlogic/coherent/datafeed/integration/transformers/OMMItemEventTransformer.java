package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.springframework.integration.transformer.AbstractPayloadTransformer;

import com.reuters.rfa.common.Event;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * A payload transformer that converts an Event into an instance of
 * OMMItemEvent.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class OMMItemEventTransformer
    extends AbstractPayloadTransformer<Event, OMMItemEvent> {

    @Override
    protected OMMItemEvent transformPayload(Event payload) throws Exception {

        OMMItemEvent ommItemEvent = (OMMItemEvent) payload;

        return ommItemEvent;
    }
}
