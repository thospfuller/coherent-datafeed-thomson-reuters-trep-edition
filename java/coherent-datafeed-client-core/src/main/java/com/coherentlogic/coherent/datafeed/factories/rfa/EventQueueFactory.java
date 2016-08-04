package com.coherentlogic.coherent.datafeed.factories.rfa;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.reuters.rfa.common.EventQueue;

/**
 * A factory bean for creating instances of
 * {@link com.reuters.rfa.common.EventQueue EventQueue}.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class EventQueueFactory implements TypedFactory<EventQueue> {

    public static final String BEAN_NAME = "eventQueueFactory";

    private final String eventQueueName;

    public EventQueueFactory(String eventQueueName) {
        this.eventQueueName = eventQueueName;
    }

    @Override
    public EventQueue getInstance() {
        return EventQueue.create(eventQueueName);
    }
}
