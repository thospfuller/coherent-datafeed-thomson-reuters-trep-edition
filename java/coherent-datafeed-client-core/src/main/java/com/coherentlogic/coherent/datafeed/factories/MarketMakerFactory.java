package com.coherentlogic.coherent.datafeed.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketMakerFactory implements TypedFactory<MarketMaker> {

    public static final String BEAN_NAME = "marketMakerFactory";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public MarketMaker getInstance() {
        return applicationContext.getBean(MarketMaker.class);
    }
}
