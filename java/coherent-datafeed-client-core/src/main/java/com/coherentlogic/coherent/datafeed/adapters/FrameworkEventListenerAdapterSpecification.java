package com.coherentlogic.coherent.datafeed.adapters;

import java.util.List;

import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.listeners.FrameworkEventListener;
import com.reuters.rfa.common.Event;

public interface FrameworkEventListenerAdapterSpecification {

    void addInitialisationSuccessfulListeners(List<FrameworkEventListener> frameworkEventListeners);

    void addInitialisationFailedListeners(List<FrameworkEventListener> frameworkEventListeners);

    /**
     * Method is called by the integration work flow and will set the completed
     * flag to true and then notify all waiting threads.
     */
    Message<?> initialisationSuccessful(Message<Event> message);

    /**
     * Method is called by the integration work flow and will set the completed
     * flag to false and then notify all waiting threads.
     */
    Message<?> initialisationFailed(Message<?> message);

    List<FrameworkEventListener> getInitialisationSuccessfulListenerList();

    List<FrameworkEventListener> getInitialisationFailedListenerList();
}