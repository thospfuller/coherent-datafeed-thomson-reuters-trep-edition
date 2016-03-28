package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;

/**
 * Factory class for creating {@link MarketPrice} objects with a unique id and
 * timestamp. This factory is used when we need to pipe market price updates
 * to a database.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DefaultMarketPriceFactory
    extends AbstractRFABeanFactory<MarketPrice> {

    public static final String BEAN_NAME = "marketPriceFactory";

    @Override
    public MarketPrice getInstance() {
        return configure(new MarketPrice());
    }
}
