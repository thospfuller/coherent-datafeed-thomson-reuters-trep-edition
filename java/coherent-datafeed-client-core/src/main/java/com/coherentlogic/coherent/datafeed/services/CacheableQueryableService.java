package com.coherentlogic.coherent.datafeed.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidStateException;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.coherentlogic.coherent.datafeed.misc.Utils;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;

public abstract class CacheableQueryableService<T> extends QueryableService {

    private final Map<Handle, String> ricCache;

    private final Map<String, T> objectCache;

    private final TypedFactory<T> objectFactory;

    public CacheableQueryableService(
        String serviceName,
        short msgModelType,
        RequestMessageBuilderFactory factory,
        Client client,
        Map<Handle, String> ricCache,
        Map<String, T> objectCache,
        TypedFactory<T> objectFactory
    ) {
        super(serviceName, msgModelType, factory, client);
        this.ricCache = ricCache;
        this.objectCache = objectCache;
        this.objectFactory = objectFactory;
    }

    public Map<String, T> query(ServiceName serviceName, Handle loginHandle, String... rics) {

        Utils.assertNotNull("serviceName", serviceName);
        Utils.assertNotNull("rics", rics);

        Map<String, T> result = new HashMap<String, T> (rics.length);

        for (String ric : rics) {

            T object = objectCache.get(ric);

            Handle handle = findHandle (ric);

            if (object != null && handle == null)
                throw new InvalidStateException ("Invalid state detected: the domain object is not null however " +
                    "there is no handle associated with the ric " + ric);
            else if (object == null && handle != null)
                throw new InvalidStateException ("Invalid state detected: the domain object is null however there is " +
                    "a non-null handle associated with the ric " + ric);

            if (object == null) {

                object = objectFactory.getInstance();

                objectCache.put(ric, object);

                List<Handle> handleList = query(serviceName.toString(), loginHandle, ric);

                ricCache.put(handleList.get(0), ric);
            }
            result.put(ric, object);
        }

        return result;
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
