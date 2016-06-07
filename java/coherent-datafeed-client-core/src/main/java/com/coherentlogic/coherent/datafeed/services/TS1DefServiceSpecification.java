package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.common.Handle;
import com.reuters.ts1.TS1DefDb;

/**
 * A specification for services that provide methods for querying the
 * Thomson Reuters TS1 service.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public interface TS1DefServiceSpecification {

    /**
     * Method initializes the application's {@link TS1DefDb} database by
     * requesting all rics returned from the call to the {@link
     * TS1DefDb#getTs1DbRics} method.
     */
    List<Handle> initialize (SessionBean sessionBean);

    /**
     * Method initializes the application's {@link TS1DefDb} database by
     * requesting the specified rics.
     * 
     * @param loginHandle The login handle.
     * @param rics The rics required to initialize the {@link TS1DefDb}.
     */
    public List<Handle> initialize (SessionBean sessionBean, String... rics);
}
