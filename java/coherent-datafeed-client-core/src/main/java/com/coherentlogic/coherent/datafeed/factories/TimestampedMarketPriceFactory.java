package com.coherentlogic.coherent.datafeed.factories;

import java.util.Date;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;

/**
 * Factory class for creating {@link MarketPrice} objects with timestamp only.
 * This is used when returning market price updates that will be written to a
 * database where the id is auto-generated via Hibernate.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimestampedMarketPriceFactory implements TypedFactory<MarketPrice> {

    public TimestampedMarketPriceFactory (){
    }

    @Override
    public MarketPrice getInstance() {

        MarketPrice marketPrice = new MarketPrice();

        // Intended
        marketPrice.setUniqueId(null);
        marketPrice.setTimestamp(new Date (System.currentTimeMillis ()));

        return marketPrice;
    }
}
