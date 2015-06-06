package com.coherentlogic.coherent.datafeed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that is used to indicate that a given setter method applies to
 * an OMM data type with the specified filter id value.
 *
 * @see {@link RDMService.FilterId}
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterId {

    int value ();
}
