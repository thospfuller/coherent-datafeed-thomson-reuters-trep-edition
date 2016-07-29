package com.coherentlogic.coherent.datafeed.domain;

/**
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <T> The order type.
 */
public interface OrderEventListener<T> {

    void onOrderEvent(OrderEvent<T> orderEvent);
}
