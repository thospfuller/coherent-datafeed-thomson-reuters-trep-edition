package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;

/**
 * A service which is used to send requests to Thomson Reuters.
 *
 * @todo Refactor this class so that the {@link #executeRequest(Handle,
 *  ServiceName, short, String...)} can take both a String ric and String...
 *  rics and also returns either a Handle or a List<Handle>.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class RequestService {

    private final RequestMessageBuilderFactory requestMessageBuilderFactory;

    private final Client client;

    public RequestService(
        RequestMessageBuilderFactory requestMessageBuilderFactory,
        Client client
    ) {
        super();
        this.requestMessageBuilderFactory = requestMessageBuilderFactory;
        this.client = client;
    }

    /**
     * 
     * @param loginHandle
     * @param msgModelType RDMMsgTypes#MARKET_PRICE, MARKET_BY_ORDER, etc.
     * @param itemNames
     * @return
     */
    protected abstract List<Handle> executeRequest (
        String serviceName,
        short msgModelType,
        SessionBean sessionBean,
        String... itemNames
    );

    public RequestMessageBuilderFactory getRequestMessageBuilderFactory() {
        return requestMessageBuilderFactory;
    }

    public Client getClient() {
        return client;
    }
}
