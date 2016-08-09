package com.coherentlogic.coherent.datafeed.adapters;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeEvent;
import com.coherentlogic.coherent.datafeed.domain.RFABean;

/**
 * Class is used to collect PropertyChangeEvent instances for a specific instance of an {@link RFABean} and then notify
 * all listeners interested in {@link AggregatePropertyChangeEvent}s.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <T> The {@link com.coherentlogic.coherent.datafeed.domain.RFABean} type.
 */
public class AggregatePropertyChangeCollector<T extends SerializableBean> {

    private final Map<String, PropertyChangeEvent> propertyChangeEventMap;

    private final T rfaBean;

    private final PropertyChangeListener propertyChangeListener = new PropertyChangeListener () {

        @Override
        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
            propertyChangeEventMap.put(propertyChangeEvent.getPropertyName(), propertyChangeEvent);
        }
    };

    public AggregatePropertyChangeCollector (T rfaBean) {
        this (new HashMap<String, PropertyChangeEvent> (), rfaBean);
    }

    public AggregatePropertyChangeCollector (Map<String, PropertyChangeEvent> propertyChangeEventMap, T rfaBean) {
        this.propertyChangeEventMap = propertyChangeEventMap;
        this.rfaBean = rfaBean;
        rfaBean.addPropertyChangeListener(propertyChangeListener);
    }

    /**
     * Method invokes the fireAggregatePropertyChangeEvent method on the {@link RFABean} and then removes the
     * propertyChangeListener.
     */
    public void done () {

        if (!propertyChangeEventMap.isEmpty())
            rfaBean.fireAggregatePropertyChangeEvent(
                new AggregatePropertyChangeEvent (rfaBean, propertyChangeEventMap)
            );

        rfaBean.removePropertyChangeListener(propertyChangeListener);
    }
}
