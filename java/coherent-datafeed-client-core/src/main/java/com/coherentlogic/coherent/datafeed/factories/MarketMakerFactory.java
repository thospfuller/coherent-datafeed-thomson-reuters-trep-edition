package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketMakerFactory implements TypedFactory<MarketMaker> {

    public static final String BEAN_NAME = "marketMakerFactory";

    @Override
    public MarketMaker getInstance() {
        return new MarketMaker ();
    }
}
