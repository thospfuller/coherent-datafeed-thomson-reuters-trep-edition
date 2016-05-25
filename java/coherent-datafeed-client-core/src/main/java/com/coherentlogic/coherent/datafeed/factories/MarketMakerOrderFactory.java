package com.coherentlogic.coherent.datafeed.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker.Order;

/**
*
* @author <a href="mailto:support@coherentlogic.com">Support</a>
*/
public class MarketMakerOrderFactory implements TypedFactory<MarketMaker.Order> {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Order getInstance() {
        return applicationContext.getBean(MarketMaker.Order.class);
    }
}
