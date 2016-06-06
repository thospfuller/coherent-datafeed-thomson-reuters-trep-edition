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

    public List<Handle> query (Handle loginHandle, SessionBean sessionBean) {
        return query (loginHandle, sessionBean, UNUSED, UNUSED);
    }

    public List<Handle> query (
        Handle loginHandle,
        SessionBean sessionBean,
        String item
    ) {
        return query (serviceName, loginHandle, sessionBean, item);
    }

    /**
     * @todo This needs to go in a base class.
     */
    public List<Handle> query (
        String serviceName,
        Handle loginHandle,
        SessionBean sessionBean,
        String item
    ) {
        assertNotNull ("item", item);

        return query (serviceName, loginHandle, sessionBean, new String[] {item});
    }

    public List<Handle> query (
        Handle loginHandle,
        SessionBean sessionBean,
        String... items
    ) {
        return query (serviceName, loginHandle, sessionBean, items);
    }

    public List<Handle> query (
        String serviceName,
        Handle loginHandle,
        SessionBean sessionBean,
        String... items
    ) {
        assertNotNull("serviceName", serviceName);
        assertNotNull("loginHandle", loginHandle);
        assertNotNullOrEmpty ("items", items);

        log.debug("serviceName: " + serviceName + ", loginHandle: " + loginHandle + ", items: " + items);

        List<Handle> results = null;

        if (loginHandle == null)
            throw new InvalidQueryException ("The login handle is null, which may indicate that you either didn't " +
                "login, or the login failed.");

        results = executeRequest(serviceName, loginHandle, msgModelType, sessionBean, items);

        return results;
    }

    public short getMsgModelType() {
        return msgModelType;
    }
}
