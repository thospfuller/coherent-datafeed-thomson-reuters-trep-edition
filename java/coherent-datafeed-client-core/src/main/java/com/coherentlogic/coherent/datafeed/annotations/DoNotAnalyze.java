package com.coherentlogic.coherent.datafeed.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation which is applied to classes that extend from {@link RFABean} which are effectively empty of data.
 *
 * @todo Work on the documentation for this annotation.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DoNotAnalyze {

}
