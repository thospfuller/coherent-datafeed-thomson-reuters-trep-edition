package com.coherentlogic.coherent.datafeed.factories;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import com.coherentlogic.coherent.datafeed.annotations.OMMType;

/**
 * @todo Unit test this class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMTypeAnnotationProcessor
    extends GenericAnnotationProcessor<OMMType, String> {

    public OMMTypeAnnotationProcessor(
        Class<Serializable> targetClass,
        Class<OMMType> targetAnnotation,
        AnnotationFoundCallback<OMMType, String> annotationFoundCallback,
        Map<String, Method> methodMap
    ) {
        super(
            targetClass,
            targetAnnotation,
            annotationFoundCallback,
            methodMap
        );
    }
}
