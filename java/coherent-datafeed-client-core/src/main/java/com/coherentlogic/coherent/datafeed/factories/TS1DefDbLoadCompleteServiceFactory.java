package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.services.TS1DefDbLoadCompleteService;
import com.coherentlogic.coherent.datafeed.services.TS1DefServiceSpecification;
import com.reuters.ts1.TS1DefDb;

/**
 * Factory class for instantiating the {@link TS1DefDbLoadCompleteService}
 * class.
 *
 * Note that we are using a factory to instantiate the {@link
 * TS1DefDbLoadCompleteService} so as to keep the dependency on the
 * {@link TS1DefDb} isolated.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class TS1DefDbLoadCompleteServiceFactory
    implements TypedFactory<TS1DefDbLoadCompleteService> {

    private final TS1DefServiceSpecification ts1DefService;

    public TS1DefDbLoadCompleteServiceFactory (
        TS1DefServiceSpecification ts1DefService
    ) {
        this.ts1DefService = ts1DefService;
    }
    
    @Override
    public TS1DefDbLoadCompleteService getInstance() {

        String[] rics = TS1DefDb.getTs1DbRics();

        int qty = rics.length;

        return new TS1DefDbLoadCompleteService (
            ts1DefService,
            qty,
            0
        );
    }
}

/*
public class TS1DefDbLoadCompleteServiceFactory
implements Factory<TS1DefDbLoadCompleteService> {

private final ConcurrentTS1DefService ts1DefService;

public TS1DefDbLoadCompleteServiceFactory (
    ConcurrentTS1DefService ts1DefService
) {
    this.concurrentTS1DefService = ts1DefService;
}

@Override
public TS1DefDbLoadCompleteService getInstance() {

    String[] rics = TS1DefDb.getTs1DbRics();

    int qty = rics.length;

    return new TS1DefDbLoadCompleteService (
        ts1DefService,
        qty,
        0
    );
}
}
*/
