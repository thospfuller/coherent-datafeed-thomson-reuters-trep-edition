package com.coherentlogic.coherent.datafeed.services.message.processors;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.services.AsynchronouslyUpdatableSpecification;

/**
 * A type-specific implementation of {@link
 * GetNextUpdateAsJSONMessageProcessor}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetNextMarketPriceUpdateAsJSONMessageProcessor extends
    GetNextUpdateAsJSONMessageProcessor<MarketPrice> {

    public GetNextMarketPriceUpdateAsJSONMessageProcessor(
            AsynchronouslyUpdatableSpecification<MarketPrice>
                asynchronouslyUpdatableSpecification) {
        super(asynchronouslyUpdatableSpecification);
    }
}
