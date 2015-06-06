package com.coherentlogic.coherent.datafeed.factories;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.exceptions.MissingDataException;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;

/**
 * @deprecated I'm not sure I want this class yet so I'm deprecating it. Note
 *  that this is supposed to be used with the directory service.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class GenericAnnotationProcessor<A extends Annotation, T>
    implements Factory<Map<T, Method>> {

    private static final Logger log =
        LoggerFactory.getLogger(GenericAnnotationProcessor.class);

    private final Class<? extends Serializable> targetClass;

    private final Class<A> targetAnnotation;

    private final AnnotationFoundCallback<A, T> annotationFoundCallback;

    private final Map<T, Method> methodMap;

    public GenericAnnotationProcessor(
        Class<? extends Serializable> targetClass,
        Class<A> targetAnnotation,
        AnnotationFoundCallback<A, T> annotationFoundCallback,
        Map<T, Method> methodMap
    ) {
        super();
        this.targetClass = targetClass;
        this.targetAnnotation = targetAnnotation;
        this.annotationFoundCallback = annotationFoundCallback;
        this.methodMap = methodMap;
        analyze();
    }

    /**
     * Method analyzes the type and returns a {@link java.util.Map} of types to
     * setter methods that have been annotated with the {@link RFAType}
     * annotation.
     *
     * @param targetClass The domain class type.
     * @param methodMap A map that will be populated where the keys will be
     *  constant RFA dictionary values and the values will be methods that the
     *  key is associated with.
     */
    public void analyze () {

        if (targetClass == null)
            throw new NullPointerRuntimeException("The type is null.");

        if (methodMap == null)
            throw new NullPointerRuntimeException("The methodMap is null.");

        Method[] methods = targetClass.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(targetAnnotation)) {
                A annotation = method.getAnnotation(targetAnnotation);

                annotationFoundCallback.annotationFound(
                    annotation,
                    method,
                    methodMap
                );
            }
        }

        if (methodMap.isEmpty())
            throw new MissingDataException("No annotations were found on " +
                "the type " + targetClass + ".");
    }

    @Override
    public Map<T, Method> getInstance() {
        return methodMap;
    }
}
