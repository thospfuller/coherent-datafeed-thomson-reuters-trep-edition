package com.coherentlogic.coherent.datafeed.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.coherentlogic.coherent.datafeed.domain.MarketByPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketByPrice.Order;

/**
 * Factory class for creating {@link MarketByPrice} objects with a unique id and
 * timestamp. This factory is used when we need to pipe market price updates
 * to a database.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DefaultMarketByPriceFactory extends AbstractRFABeanFactory<MarketByPrice> {

    public static final String BEAN_NAME = "marketByPriceFactory";

    @Override
    public MarketByPrice getInstance() {
        return configure(new MarketByPrice ());
    }

    /**
     * @todo Extract this class.
     */
    public static class DefaultOrderFactory extends AbstractRFABeanFactory<MarketByPrice.Order> {

        public static final String BEAN_NAME = "marketByPriceOrderFactory";

        @Autowired
        private ApplicationContext applicationContext;

        @Override
        public Order getInstance() {

            MarketByPrice.Order order = applicationContext.getBean (MarketByPrice.Order.class);

            return configure(order);
        }
    }
}
