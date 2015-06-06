package com.coherentlogic.coherent.datafeed.factories;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import com.coherentlogic.coherent.datafeed.annotations.FilterId;

/**
 * @deprecated I'm not sure I want this class yet so I'm deprecating it. Note
 *  that this is supposed to be used with the directory service.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class FilterIdAnnotationProcessor
    extends GenericAnnotationProcessor<FilterId, Integer> {

    public FilterIdAnnotationProcessor(
        Class<? extends Serializable> targetClass,
        AnnotationFoundCallback<FilterId, Integer> annotationFoundCallback,
        Map<Integer, Method> methodMap
    ) {
        super(
            targetClass,
            FilterId.class,
            annotationFoundCallback,
            methodMap
        );
    }
}
