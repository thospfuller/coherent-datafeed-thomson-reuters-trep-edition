package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;

/**
 * Factory class for creating {@link MarketPrice} objects with a null unique id
 * and null timestamp. This is used when returning market price updates as JSON
 * to R users as a null value will not appear in the output.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class NullMarketPriceFactory implements Factory<MarketPrice> {

    public NullMarketPriceFactory (){
    }

    @Override
    public MarketPrice getInstance() {

        MarketPrice marketPrice = new MarketPrice();

        // Intended
        marketPrice.setUniqueId(null);
        marketPrice.setTimestamp(null);

        return marketPrice;
    }
}
