package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.UNUSED;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNullOrEmpty;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidQueryException;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;

/**
 * A service that can be queried.
 *
 * @todo This class needs to be refactored -- ie. methods that only query once
 *  should not have a return type that is a list. We could make this generic
 *  but on the flip-side that should really be unnecessary.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class QueryableService extends RequestService {

    private static final Logger log =
        LoggerFactory.getLogger(QueryableService.class);

    private final String serviceName;

    private final short msgModelType;

    public QueryableService(
        String serviceName,
        short msgModelType,
        RequestMessageBuilderFactory factory,
        Client client
    ) {
        super(factory, client);
        this.serviceName = serviceName;
        this.msgModelType = msgModelType;
    }

    public List<Handle> query (SessionBean sessionBean) {
        return query (sessionBean, UNUSED, UNUSED);
    }

    public List<Handle> query (
        SessionBean sessionBean,
        String item
    ) {
        return query (serviceName, sessionBean, item);
    }

    /**
     * @todo This needs to go in a base class.
     */
    public List<Handle> query (
        String serviceName,
        SessionBean sessionBean,
        String item
    ) {
        assertNotNull ("item", item);

        return query (serviceName, sessionBean, new String[] {item});
    }

    public List<Handle> query (
        SessionBean sessionBean,
        String... items
    ) {
        return query (serviceName, sessionBean, items);
    }

    public List<Handle> query (
        String serviceName,
        SessionBean sessionBean,
        String... items
    ) {
        assertNotNull("serviceName", serviceName);
        assertNotNull("sessionBean", sessionBean);
        assertNotNull("sessionBean.handle", sessionBean.getHandle());
        assertNotNullOrEmpty ("items", items);

        log.debug("serviceName: " + serviceName + ", sessionBean: " + sessionBean + ", items: " + items);

        List<Handle> results = null;

        results = executeRequest(serviceName, msgModelType, sessionBean, items);

        return results;
    }

    public short getMsgModelType() {
        return msgModelType;
    }
}
