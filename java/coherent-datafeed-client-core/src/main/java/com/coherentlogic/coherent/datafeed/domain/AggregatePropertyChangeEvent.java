package com.coherentlogic.coherent.datafeed.domain;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.util.Map;

public class AggregatePropertyChangeEvent<T> implements Serializable {

    private static final long serialVersionUID = -4448205480412645059L;

    private final AggregatePropertyChangeEventType eventType;

    private final T source;

    private final Map<String, PropertyChangeEvent> propertyChangeEvents;

    public AggregatePropertyChangeEvent(
        AggregatePropertyChangeEventType eventType,
        T source,
        Map<String, PropertyChangeEvent> propertyChangeEvents
    ) {
        this.eventType = eventType;
        this.source = source;
        this.propertyChangeEvents = propertyChangeEvents;
    }

    public AggregatePropertyChangeEventType getEventType() {
        return eventType;
    }

    public T getSource() {
        return source;
    }

    public Map<String, PropertyChangeEvent> getPropertyChangeEvents() {
        return propertyChangeEvents;
    }

    @Override
    public String toString() {
        return "AggregatePropertyChangeEvent [eventType=" + eventType + ", source=" + source + ", propertyChangeEvents="
            + propertyChangeEvents + "]";
    }
}
