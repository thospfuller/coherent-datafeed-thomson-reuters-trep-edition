package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.Order;

/**
 * Factory class for creating {@link Order} objects with a unique id and
 * timestamp. This factory is used when we need to pipe order updates
 * to a database.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DefaultOrderFactory
    extends AbstractRFABeanFactory<Order> {

    @Override
    public Order getInstance() {
        return configure(new Order());
    }
}
