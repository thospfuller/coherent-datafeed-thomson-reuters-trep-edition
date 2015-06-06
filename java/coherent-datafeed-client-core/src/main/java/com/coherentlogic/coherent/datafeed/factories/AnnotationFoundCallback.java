package com.coherentlogic.coherent.datafeed.factories;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * A callback that is executed when an annotation is found.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <A> The type of annotation.
 */
public interface AnnotationFoundCallback<A extends Annotation, T> {

    void annotationFound (
        A annotation,
        Method method,
        Map<T, Method> methodMap
    );
}
