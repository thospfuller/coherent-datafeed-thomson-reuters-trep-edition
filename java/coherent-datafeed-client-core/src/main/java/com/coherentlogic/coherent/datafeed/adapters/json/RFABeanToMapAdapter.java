package com.coherentlogic.coherent.datafeed.adapters.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.exceptions.GenericReflectionException;

/**
 * 
 * @deprecated Deprecated in favor of GJSONGenerator and associated classes.
 */
public class RFABeanToMapAdapter<S extends RFABean>
    implements BasicAdapter<S, Map<String, String>> {

    private final Map<String, Method> typeToGetterMethodMap;

    public RFABeanToMapAdapter (
        Class<? extends RFABean> targetClass
    ) {
        this (targetClass, new HashMap<String, Method> ());
    }

    public RFABeanToMapAdapter (
        Class<? extends RFABean> targetClass,
        Map<String, Method> typeToGetterMethodMap
    ) {
        super();
        this.typeToGetterMethodMap = typeToGetterMethodMap;

        scan (targetClass, typeToGetterMethodMap);
    }

    static void scan (
        Class<? extends RFABean> targetClass,
        Map<String, Method> typeToGetterMethodMap) {

        Method[] methods = targetClass.getMethods();

        for (Method next : methods) {

            UsingKey usingKey = next.getAnnotation(UsingKey.class);

            if (usingKey != null) {
                String type = usingKey.type();

                if (type != null) {
                    typeToGetterMethodMap.put(type, next);
                }
            }
        }
    }

    /**
     * Method converts the marketPrice into an instance of Map. This class is
     * used to convert an instance of MarketPrice into an object that serializes
     * into JSON which is easier to work with on the R Platform.
     */
    @Override
    public Map<String, String> adapt(S marketPrice) {

        Map<String, String> result = new HashMap<String, String> ();

        Set<String> keys = typeToGetterMethodMap.keySet();

        for (String key : keys) {
            Method method = typeToGetterMethodMap.get(key);

            Object value;

            try {
                value = method.invoke(marketPrice);
            } catch (
                IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException exception
            ) {
                throw new GenericReflectionException("Invoking the method " +
                    "with the RFAType type set to '" + key + "' failed.",
                    exception);
            }
            result.put(
                key,
                toString (value)
            );
        }

        return result;
    }

    /**
     * Method either returns the result of the object's toString method call or
     * null if the object is null.
     */
    static String toString (Object object) {

        String result = null;

        if (object != null)
            result = object.toString();

        return result;
    }
}
