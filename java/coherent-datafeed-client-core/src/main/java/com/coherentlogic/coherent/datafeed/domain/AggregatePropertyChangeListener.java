package com.coherentlogic.coherent.datafeed.domain;

public interface AggregatePropertyChangeListener {

    void onAggregatePropertyChangeEvent (AggregatePropertyChangeEventType aggregatePropertyChangeType);
}
