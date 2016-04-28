package com.coherentlogic.coherent.datafeed.services.message.processors;

import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.services.AsynchronouslyUpdatableSpecification;

/**
 * A type-specific implementation of {@link GetNextUpdateAsJSONMessageProcessor}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GetNextMarketByOrderUpdateAsJSONMessageProcessor extends
    GetNextUpdateAsJSONMessageProcessor<MarketByOrder> {

    public GetNextMarketByOrderUpdateAsJSONMessageProcessor(
            AsynchronouslyUpdatableSpecification<MarketByOrder>
                asynchronouslyUpdatableSpecification) {
        super(asynchronouslyUpdatableSpecification);
    }
}
