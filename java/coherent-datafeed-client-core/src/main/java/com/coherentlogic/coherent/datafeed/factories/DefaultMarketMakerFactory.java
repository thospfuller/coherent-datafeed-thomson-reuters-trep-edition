package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.datafeed.domain.MarketByOrder.Order;
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

    @Override
    public MarketMaker getInstance() {
        return configure(new MarketMaker ());
    }

    /**
     * @todo Extract this class.
     */
    public static class DefaultOrderFactory
        extends AbstractRFABeanFactory<MarketMaker.Order> {

        public static final String BEAN_NAME = "marketMakerOrderFactory";

        @Override
        public MarketMaker.Order getInstance() {

            MarketMaker.Order order = new MarketMaker.Order ();

            return configure(order);
        }
    }
}
