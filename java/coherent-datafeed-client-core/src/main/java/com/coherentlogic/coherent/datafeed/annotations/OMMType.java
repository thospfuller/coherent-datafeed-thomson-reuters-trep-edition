package com.coherentlogic.coherent.datafeed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to mark methods that apply to a specific named OMM data
 * type (see {@link OMMTypes}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OMMType {

    /**
     * The type of the OMM object that this method applies to -- ie. if the type
     * of this annotation is set to RMTEString and the OMM object is an UINT,
     * then the method should not be processed since there's a mismatch.
     */
    short value ();

    /**
     * The name of the OMM object that this method applies to.
     */
    String named ();
}
