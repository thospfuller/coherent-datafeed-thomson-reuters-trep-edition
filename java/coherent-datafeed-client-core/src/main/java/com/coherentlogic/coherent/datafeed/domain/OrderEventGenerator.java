package com.coherentlogic.coherent.datafeed.domain;

import java.util.List;

import javax.persistence.Transient;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <T> The order event type (see MarketMaker or MarketByOrder).
 */
public interface OrderEventGenerator<T> {

    @Transient
    List<OrderEventListener<T>> getOrderEventListeners();

    void addOrderEventListener (OrderEventListener<T> orderEventListener);

    boolean removeOrderEventListener (OrderEventListener<T> orderEventListener);

    void fireOrderEvent (OrderEvent<T> orderEvent);
}
