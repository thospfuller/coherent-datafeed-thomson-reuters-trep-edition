package com.coherentlogic.coherent.datafeed.beans;

import com.reuters.rfa.common.Handle;

/**
 * A bean that is passed as the message payload when querying the market price
 * service via a Spring Integration gateway.
 *
 * @deprecated This is being replaced by domain-specific query parameters -- see {@link MarketPriceQueryParameters}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class QueryParameters extends AbstractQuery<String[]> {

    private static final long serialVersionUID = -4481591451419966358L;

    public QueryParameters(
        String serviceName,
        Handle loginHandle,
        String[] items
    ) {
        super (serviceName, loginHandle, items);
    }
}
