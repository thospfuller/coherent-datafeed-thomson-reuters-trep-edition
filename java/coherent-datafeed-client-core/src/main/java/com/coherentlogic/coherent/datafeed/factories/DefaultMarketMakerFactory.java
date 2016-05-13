package com.coherentlogic.coherent.datafeed.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;

/**
 * Factory class for creating {@link MarketPrice} objects with a unique id and
 * timestamp. This factory is used when we need to pipe market price updates
 * to a database.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DefaultMarketMakerFactory
    extends AbstractRFABeanFactory<MarketMaker> {

    public static final String BEAN_NAME = "marketMakerFactory";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public MarketMaker getInstance() {

        MarketMaker marketMaker = applicationContext.getBean(MarketMaker.class);

        return configure(marketMaker);
    }

    /**
     * @todo Extract this class.
     */
    public static class DefaultOrderFactory
        extends AbstractRFABeanFactory<MarketMaker.Order> {

        public static final String BEAN_NAME = "marketMakerOrderFactory";

        @Autowired
        private ApplicationContext applicationContext;

        @Override
        public MarketMaker.Order getInstance() {

            MarketMaker.Order order = applicationContext.getBean(MarketMaker.Order.class);

            return configure(order);
        }
    }
}
