package com.coherentlogic.coherent.datafeed.services;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
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

    Handle login (SessionBean sessionBean);

    void logout ();
}
