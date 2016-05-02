package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketPriceFactory implements TypedFactory<MarketPrice> {

    public static final String BEAN_NAME = "marketPriceFactory";

    @Override
    public MarketPrice getInstance() {
        return new MarketPrice ();
    }
}
