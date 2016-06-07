package com.coherentlogic.coherent.datafeed.beans;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.common.Handle;

/**
 * A bean that is passed as the message payload when querying the market price
 * service via a Spring Integration gateway.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class QueryParameter extends AbstractQuery<String> {

    private static final long serialVersionUID = -900023712494047554L;

    public QueryParameter(
        String serviceName,
        SessionBean sessionBean,
        String items
    ) {
        super (serviceName, sessionBean, items);
    }
}
