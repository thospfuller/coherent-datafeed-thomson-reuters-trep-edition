package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;
import com.reuters.ts1.TS1DefDb;

/**
 * A service that is used to initialize the instance of {@link TS1DefDb}; this
 * entails calling TR and requesting all of the QQ?? RICs available and must be
 * done before other, for example time series, RICS are requested, otherwise
 * decoding will fail and no headers will be available (among other possible
 * problems).
 *
 * @todo Should this have it's own cache?
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefService extends QueryableService implements TS1DefServiceSpecification {

    private static final Logger log = LoggerFactory.getLogger(TS1DefService.class);

    public static final String BEAN_NAME = "ts1DefService";

    private final CommonRequestExecutor commonRequestExecutor;

    public TS1DefService (
        String serviceName,
        short msgModelType,
        RequestMessageBuilderFactory factory,
        Client client,
        CommonRequestExecutor commonRequestExecutor
    ) {
        super(serviceName, msgModelType, factory, client);
        this.commonRequestExecutor = commonRequestExecutor;
    }

    /**
     * Method initializes the application's {@link TS1DefDb} database by
     * requesting all rics returned from the call to the {@link
     * TS1DefDb#getTs1DbRics} method.
     *
     * @param loginHandle The login handle.
     */
    public List<Handle> initialize (SessionBean sessionBean) {

        String[] rics = TS1DefDb.getTs1DbRics();

        return initialize (sessionBean, rics);
    }

    /**
     * Method initializes the application's {@link TS1DefDb} database by
     * requesting the specified rics.
     * 
     * @param loginHandle The login handle.
     * @param rics The rics required to initialize the {@link TS1DefDb}.
     */
    public List<Handle> initialize (SessionBean sessionBean, String... rics) {

        log.info("initialize: method begins; sessionBean: " + sessionBean + ", rics: " +
            ToStringBuilder.reflectionToString(rics));

        List<Handle> handles = query(sessionBean, rics);

        return handles;
    }

    @Override
    protected List<Handle> executeRequest(
        String serviceName,
        short msgModelType,
        SessionBean sessionBean,
        String... itemNames
    ) {
        return commonRequestExecutor.executeRequest(
            serviceName,
            msgModelType,
            sessionBean,
            itemNames
        );
    }
}
