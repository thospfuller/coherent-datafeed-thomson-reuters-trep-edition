package com.coherentlogic.coherent.datafeed.domain;

import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.util.Map;

public class AggregatePropertyChangeEvent<T> implements Serializable {

	private final AggregatePropertyChangeEventType eventType;

	private final T source;

	private final Map<String, PropertyChangeEvent> propertyChangeEvent;

	public AggregatePropertyChangeEvent(AggregatePropertyChangeEventType eventType, T source,
			Map<String, PropertyChangeEvent> propertyChangeEvent) {
		super();
		this.eventType = eventType;
		this.source = source;
		this.propertyChangeEvent = propertyChangeEvent;
	}

	

}
