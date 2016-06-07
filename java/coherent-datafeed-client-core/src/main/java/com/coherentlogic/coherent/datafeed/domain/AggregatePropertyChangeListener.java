package com.coherentlogic.coherent.datafeed.domain;

public interface AggregatePropertyChangeListener<T> {

    void onAggregatePropertyChangeEvent (AggregatePropertyChangeEvent<T> aggregatePropertyChangeEvent);
}
