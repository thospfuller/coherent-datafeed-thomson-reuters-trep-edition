package com.coherentlogic.coherent.datafeed.factories;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.annotations.DoNotAnalyze;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.exceptions.MissingDataException;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

/**
 * A factory class that creates iterates over the setter methods of an
 * {#link RFABean} and adds each method as a key in a map of strings to
 * {#link Method} values.
 *
 * @todo Unit test this class.
 *
 * @todo Rename this class as the name should indicate that this applies to a
 *  specific type of class -- as it is this is too general. This is an
 *  annotation processor, even if it is a factory, and it applies specifically
 *  to instances of OMMFieldEntry.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MethodMapFactory implements TypedFactory<Map<String, Method>> {

    private static final Logger log = LoggerFactory.getLogger(MethodMapFactory.class);

    private final Class<RFABean> targetClass;

    private final Map<String, Method> methodMap; 

    public MethodMapFactory() {
        this (null, null);
    }

    public MethodMapFactory(Class<RFABean> targetClass,
        Map<String, Method> methodMap) {
        super();
        this.targetClass = targetClass;
        this.methodMap = methodMap;
    }

    @Override
    public Map<String, Method> getInstance() {
        analyze (targetClass, methodMap);
        return methodMap;
    }

    /**
     * Method analyzes the type and returns a {@link java.util.Map} of types to
     * setter methods that have been annotated with the {@link RFAType}
     * annotation.
     *
     * @param type The domain class type.
     * @param methodMap A map that will be populated where the keys will be
     *  constant RFA dictionary values and the values will be methods that the
     *  key is associated with.
     */
    public static void analyze (Class<? extends RFABean> type, Map<String, Method> methodMap) {

        if (type != null && type.isAnnotationPresent(DoNotAnalyze.class)) {

            log.debug("The type " + type + " is marked as " + DoNotAnalyze.class + " so the analysis for this bean " +
                "will be skipped.");

            return;
        }

        assertNotNull ("type", type);
        assertNotNull ("methodMap", methodMap);

        Method[] methods = type.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(RFAType.class)) {

                RFAType rfaType = method.getAnnotation(RFAType.class);

                String key = rfaType.type();

                log.debug("Putting the following key: " + key + ", method: " + method);

                methodMap.put(key, method);
            }
        }

        if (methodMap.isEmpty())
            throw new MissingDataException("No annotation on the type " + type + " were found.");
    }

    /**
     * Method which instantiates a HashMap and then invokes the
     * {@link #analyze(Class, Map)} method.
     *
     * @param type The class to analyze.
     *
     * @return An instance of {@link java.util.Map} with populated with the
     *  key/value (String/method) pairs.
     */
    public static Map<String, Method> analyze (Class<? extends RFABean> type) {

        Map<String, Method> methodMap = new HashMap<String, Method>();

        analyze(type, methodMap);

        return methodMap;
    }
}
