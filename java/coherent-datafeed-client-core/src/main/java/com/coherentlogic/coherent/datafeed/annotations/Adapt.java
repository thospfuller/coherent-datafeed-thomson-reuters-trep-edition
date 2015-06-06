package com.coherentlogic.coherent.datafeed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMFieldEntryAdapter;
import com.reuters.rfa.omm.OMMData;

/**
 * An annotation which dictates which adapter to use when converting a property
 * on a domain object to JSON.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Adapt {

    Class<? extends OMMFieldEntryAdapter<? extends OMMData>> using();
}
