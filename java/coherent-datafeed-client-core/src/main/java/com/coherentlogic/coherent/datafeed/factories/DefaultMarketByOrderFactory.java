package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder.Order;

/**
 * Factory class for creating {@link MarketPrice} objects with a unique id and
 * timestamp. This factory is used when we need to pipe market price updates
 * to a database.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DefaultMarketByOrderFactory
    extends AbstractRFABeanFactory<MarketByOrder> {

    @Override
    public MarketByOrder getInstance() {
        return configure(new MarketByOrder ());
    }

    public static class DefaultOrderFactory
        extends AbstractRFABeanFactory<Order> {

        @Override
        public Order getInstance() {

            MarketByOrder.Order order = new MarketByOrder.Order ();

            return configure(order);
        }
    }
}
