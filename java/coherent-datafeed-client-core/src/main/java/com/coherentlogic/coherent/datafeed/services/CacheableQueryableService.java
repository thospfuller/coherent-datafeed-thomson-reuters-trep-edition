package com.coherentlogic.coherent.datafeed.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidStateException;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.coherentlogic.coherent.datafeed.misc.Utils;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;

public abstract class CacheableQueryableService<T> extends QueryableService {

    private final Map<Handle, String> ricCache;

    private final Map<String, T> objectCache;

//    private final TypedFactory<T> objectFactory;

    public CacheableQueryableService(
        String serviceName,
        short msgModelType,
        RequestMessageBuilderFactory factory,
        Client client,
        Map<Handle, String> ricCache,
        Map<String, T> objectCache
//        TypedFactory<T> objectFactory
    ) {
        super(serviceName, msgModelType, factory, client);
        this.ricCache = ricCache;
        this.objectCache = objectCache;
//        this.objectFactory = objectFactory;
    }

    public void query(ServiceName serviceName, Handle loginHandle, SessionBean sessionBean, String... rics) {

        Utils.assertNotNull("serviceName", serviceName);
        Utils.assertNotNull("rics", rics);

        Map<String, T> result = new HashMap<String, T> (rics.length);

        for (String ric : rics) {

            T object = objectCache.get(ric);

            Handle handle = findHandle (ric);

//            /*
//             * Consider a proper association from handle to object looks like this:
//             *
//             * handle -> ric -> object
//             *
//             * So for below, if the object (marketPrice, for example) is not null but the handle is null then we have an
//             * invalid state because an object should always be associated with a handle and instead we have:
//             * 
//             * null -> ric -> object
//             *
//             * Likewise if the object is null and the handle is not then this is also invalid because the link from the
//             * handle to the object is broken:
//             *
//             * handle -> ric -> null
//             *
//             * NOTE: This scenario can happen when there's a cache shared between instances of this class (say MBO and
//             *       MP services. Caches should *not* be shared between services.
//             */
//            if (object != null && handle == null)
//                throw new InvalidStateException ("Invalid state detected: the domain object is not null however " +
//                    "there is no handle associated with the ric " + ric);
            if (object == null && handle != null)
                throw new InvalidStateException ("Invalid state detected: the domain object is null however there is " +
                    "a non-null handle associated with the ric " + ric);

//            if (object == null) {

//                object = objectFactory.getInstance();
//
//                objectCache.put(ric, object);

                List<Handle> handleList = query(serviceName.toString(), loginHandle, sessionBean, ric);

                ricCache.put(handleList.get(0), ric);
//            }
//            result.put(ric, object);
        }

//        return result;
    }

    Handle findHandle (String ric) {

        Handle result = null;

        for (Entry<Handle, String> nextEntry : ricCache.entrySet()) {
            if (nextEntry.getValue().equals(ric)) {
                result = nextEntry.getKey();
                break;
            }
        }

        return result;
    }
}
