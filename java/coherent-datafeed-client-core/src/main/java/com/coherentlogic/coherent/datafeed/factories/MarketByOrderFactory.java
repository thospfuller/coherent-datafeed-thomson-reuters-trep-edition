package com.coherentlogic.coherent.datafeed.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketByOrderFactory implements TypedFactory<MarketByOrder> {

    public static final String BEAN_NAME = "marketByOrderFactory";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public MarketByOrder getInstance() {
        return applicationContext.getBean(MarketByOrder.class);
    }
}
