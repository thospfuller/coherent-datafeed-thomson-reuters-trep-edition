package com.coherentlogic.reuters.rfa.session.omm;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.reuters.rfa.session.Session;
import com.reuters.rfa.session.omm.OMMConsumer;

/**
 * Factory bean used to create instances of
 * {@link com.reuters.rfa.session.omm.OMMConsumer OMMConsumer}.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMConsumerFactory implements TypedFactory<OMMConsumer> {

    private final Session session;

    private final int eventSourceType;

    private final String name;

    private final boolean wCompEvents;

    public OMMConsumerFactory(
        Session session,
        int eventSourceType,
        String name,
        boolean wCompEvents
    ) {
        this.session = session;
        this.eventSourceType = eventSourceType;
        this.name = name;
        this.wCompEvents = wCompEvents;
        
    }

    @Override
    public OMMConsumer getInstance() {
        return (OMMConsumer)
            session.createEventSource(eventSourceType, name, wCompEvents);
    }
}
