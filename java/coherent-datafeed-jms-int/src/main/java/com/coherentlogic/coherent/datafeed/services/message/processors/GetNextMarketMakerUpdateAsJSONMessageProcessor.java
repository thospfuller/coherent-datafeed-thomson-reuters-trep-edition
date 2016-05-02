package com.coherentlogic.coherent.datafeed.services.message.processors;

import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.services.AsynchronouslyUpdatableSpecification;

/**
 * A type-specific implementation of {@link
 * GetNextUpdateAsJSONMessageProcessor}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetNextMarketMakerUpdateAsJSONMessageProcessor extends
    GetNextUpdateAsJSONMessageProcessor<MarketMaker> {

    public GetNextMarketMakerUpdateAsJSONMessageProcessor(
            AsynchronouslyUpdatableSpecification<MarketMaker>
                asynchronouslyUpdatableSpecification) {
        super(asynchronouslyUpdatableSpecification);
    }
}
