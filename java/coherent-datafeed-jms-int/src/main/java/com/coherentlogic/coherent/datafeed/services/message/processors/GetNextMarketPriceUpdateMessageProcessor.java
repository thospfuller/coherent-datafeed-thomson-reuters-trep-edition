package com.coherentlogic.coherent.datafeed.services.message.processors;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.services.AsynchronouslyUpdatableSpecification;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetNextMarketPriceUpdateMessageProcessor
    extends GetNextUpdateMessageProcessor<MarketPrice> {

    public GetNextMarketPriceUpdateMessageProcessor(
        AsynchronouslyUpdatableSpecification<MarketPrice>
            asynchronouslyUpdatableSpecification
    ) {
        super(asynchronouslyUpdatableSpecification);
    }
}
