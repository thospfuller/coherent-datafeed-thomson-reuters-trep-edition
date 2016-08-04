package com.coherentlogic.coherent.datafeed.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder.Order;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;

/**
 * Factory class for creating {@link MarketPrice} objects with a unique id and
 * timestamp. This factory is used when we need to pipe market price updates
 * to a database.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DefaultMarketByOrderFactory extends AbstractRFABeanFactory<MarketByOrder> {

    public static final String BEAN_NAME = "marketByOrderFactory";

    @Override
    public MarketByOrder getInstance() {
        return configure(new MarketByOrder ());
    }

    /**
     * @todo Extract this class.
     */
    public static class DefaultOrderFactory extends AbstractRFABeanFactory<Order> {

        public static final String BEAN_NAME = "marketByOrderOrderFactory";

        @Autowired
        private ApplicationContext applicationContext;

        @Override
        public Order getInstance() {

            MarketByOrder.Order order = applicationContext.getBean (MarketByOrder.Order.class);

            return configure(order);
        }
    }
}
