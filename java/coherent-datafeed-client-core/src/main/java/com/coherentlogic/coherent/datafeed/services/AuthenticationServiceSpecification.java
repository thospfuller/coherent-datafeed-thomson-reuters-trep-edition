package com.coherentlogic.coherent.datafeed.services;

import com.reuters.rfa.common.Handle;

/**
 * An interface that describes the methods available for the purposes of
 * authenticating with Thomson Reuters.
 *
 * Note that we need this interface because with AspectJ either we use CGLIB
 * proxies, which require a default ctor and all dependencies passed via setter
 * methods, or it will use JDK Dynamic Proxies, which require an interface. 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface AuthenticationServiceSpecification {

    Handle login (String dacsId);

    /**
     * @deprecated This is here temporarily so as to deal with a problem
     *  introduced by AspectJ, however this will be removed in due course.
     */
    Handle getHandle();

    void logout ();
}
