package com.coherentlogic.coherent.datafeed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that indicates which RFA type a given property applied to.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsingKey {
    String type();
}
