package com.coherentlogic.coherent.datafeed.services.message.processors;

import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.services.AsynchronouslyUpdatableSpecification;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetNextMarketMakerUpdateMessageProcessor
    extends GetNextUpdateMessageProcessor<MarketMaker> {

    public GetNextMarketMakerUpdateMessageProcessor(
        AsynchronouslyUpdatableSpecification<MarketMaker>
            asynchronouslyUpdatableSpecification
    ) {
        super(asynchronouslyUpdatableSpecification);
    }
}
