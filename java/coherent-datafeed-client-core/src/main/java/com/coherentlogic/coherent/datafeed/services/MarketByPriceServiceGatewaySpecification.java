package com.coherentlogic.coherent.datafeed.services;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.domain.MarketByPrice;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;

/**
 * Used as a gateway interface into the Spring Integration workflow. Note that we have a very similar interface named
 * ie. MarketMakerServiceSpecification however if we use this interface the framework won't work and the reason may have
 * to do with AOP but it's not clear -- at one point we were seeing multiple method calls being executed when only one
 * was expected. Adding another interface is not ideal but the issue does not appear if we use this interface when
 * calling into the integration workflow.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface MarketByPriceServiceGatewaySpecification {

    Map<String, MarketByPrice> query (ServiceName serviceName, SessionBean sessionBean,
        MarketByPrice... marketByPrices);
}
