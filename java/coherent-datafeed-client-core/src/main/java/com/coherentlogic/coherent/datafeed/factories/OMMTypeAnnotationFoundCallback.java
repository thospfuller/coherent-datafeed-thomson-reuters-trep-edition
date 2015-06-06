package com.coherentlogic.coherent.datafeed.factories;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.annotations.OMMType;

/**
 * Used to indicate that the given {@link OMMType annotation has been detected
 * on the specific method.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMTypeAnnotationFoundCallback
    implements AnnotationFoundCallback<OMMType, String> {

    private static final Logger log =
        LoggerFactory.getLogger(OMMTypeAnnotationFoundCallback.class);

    @Override
    public void annotationFound(
        OMMType annotation,
        Method method,
        Map<String, Method> methodMap
    ) {
        String key = annotation.named();

        log.debug("Putting the following key: " + key + ", method: " + method);

        methodMap.put(key, method);
    }
}
