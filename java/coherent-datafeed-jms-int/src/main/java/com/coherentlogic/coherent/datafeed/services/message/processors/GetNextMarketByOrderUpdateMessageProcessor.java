package com.coherentlogic.coherent.datafeed.services.message.processors;

import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.services.AsynchronouslyUpdatableSpecification;

/**
 * @TODO: Merge this with the GetNextMarketPriceUpdateMessageProcessor since
 * these are the same -- the only difference is the type.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetNextMarketByOrderUpdateMessageProcessor
    extends GetNextUpdateMessageProcessor<MarketByOrder> {

    public GetNextMarketByOrderUpdateMessageProcessor(
        AsynchronouslyUpdatableSpecification<MarketByOrder>
            asynchronouslyUpdatableSpecification
    ) {
        super(asynchronouslyUpdatableSpecification);
    }
}
