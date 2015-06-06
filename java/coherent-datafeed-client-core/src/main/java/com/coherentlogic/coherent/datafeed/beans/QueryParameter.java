package com.coherentlogic.coherent.datafeed.beans;

import com.reuters.rfa.common.Handle;

/**
 * A bean that is passed as the message payload when querying the market price
 * service via a Spring Integration gateway.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class QueryParameter extends AbstractQuery<String> {

    private static final long serialVersionUID = 6335351867406815676L;

    public QueryParameter(
        String serviceName,
        Handle loginHandle,
        String item
    ) {
        super (serviceName, loginHandle, item);
    }
}
