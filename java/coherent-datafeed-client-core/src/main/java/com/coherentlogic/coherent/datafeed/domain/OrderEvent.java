package com.coherentlogic.coherent.datafeed.domain;

import java.io.Serializable;

/**
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OrderEvent<T> implements Serializable {

    private static final long serialVersionUID = 4729039764731574275L;

    private final T order;

    private final String key;

    private final EventType eventType;

    public OrderEvent(T order, String key, EventType eventType) {
        this.order = order;
        this.key = key;
        this.eventType = eventType;
    }

    public T getOrder () {
        return order;
    }

    public String getKey () {
        return key;
    }

    public EventType getEventType () {
        return eventType;
    }

    @Override
    public String toString() {
        return "OrderEvent [order=" + order + ", key=" + key + ", eventType=" + eventType + "]";
    }
}
