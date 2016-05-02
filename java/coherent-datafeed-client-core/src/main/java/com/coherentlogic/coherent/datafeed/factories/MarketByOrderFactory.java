package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketByOrderFactory implements TypedFactory<MarketByOrder> {

    public static final String BEAN_NAME = "marketByOrderFactory";

    @Override
    public MarketByOrder getInstance() {
        return new MarketByOrder ();
    }
}
