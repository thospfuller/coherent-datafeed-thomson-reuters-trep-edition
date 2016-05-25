package com.coherentlogic.coherent.datafeed.services;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.common.Handle;

/**
 * Used as a gateway interface into the Spring Integration workflow. Note that we have a very similar interface named
 * ie. MarketMakerServiceSpecification however if we use this interface the framework won't work and the reason may have
 * to do with AOP but it's not clear -- at one point we were seeing multiple method calls being executed when only one
 * was expected. Adding another interface is not ideal but the issue oes not appear if we use this interface when
 * calling into the integration workflow.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 */
public interface MarketPriceServiceGatewaySpecification {

	Map<String, MarketPrice> query (ServiceName serviceName, Handle loginHandle, MarketPrice... marketPrices);
}
