package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder;
import com.reuters.rfa.common.EventQueue;
import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMPool;
import com.reuters.rfa.session.omm.OMMConsumer;

/**
 * A factory for creating instances of {@link RequestMessageBuilder}.
 *
 * Note that the {@link RequestMessageBuilder} is not thread-safe since state is
 * maintained in each instance of this class, which explains why we use a
 * factory to create this class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RequestMessageBuilderFactory implements
    Factory<RequestMessageBuilder> {

    public static final String BEAN_NAME = "defaultRequestMessageBuilderFactory";

    private final OMMConsumer consumer;

    private final EventQueue eventQueue;

    private final OMMPool pool;

    private final OMMEncoder encoder;

    public RequestMessageBuilderFactory(
        OMMConsumer consumer,
        EventQueue eventQueue,
        OMMPool pool,
        OMMEncoder encoder
    ) {
        this.consumer = consumer;
        this.eventQueue = eventQueue;
        this.pool = pool;
        this.encoder = encoder;
    }

    @Override
    public RequestMessageBuilder getInstance() {
        return new RequestMessageBuilder(consumer, eventQueue, pool, encoder);
    }
}
