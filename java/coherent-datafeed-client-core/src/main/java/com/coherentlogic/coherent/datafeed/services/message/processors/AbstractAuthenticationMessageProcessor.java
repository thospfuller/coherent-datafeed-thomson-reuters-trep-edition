package com.coherentlogic.coherent.datafeed.services.message.processors;

import org.infinispan.Cache;

import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;

/**
 * This class delegates calls to the {@link #authenticationService}'s login
 * method.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractAuthenticationMessageProcessor<S, T>
    implements MessageProcessorSpecification<S, T> {

    private final AuthenticationServiceSpecification authenticationService;

    /**
     * This sessionCache is global (ie. we do not create a unique one per each
     * login).
     */
    private final Cache<Handle, Session> sessionCache;

    public AbstractAuthenticationMessageProcessor(
        AuthenticationServiceSpecification authenticationService,
        Cache<Handle, Session> sessionCache
    ) {
        super();
        this.authenticationService = authenticationService;
        this.sessionCache = sessionCache;
    }

    public AuthenticationServiceSpecification getAuthenticationService() {
        return authenticationService;
    }

    public Cache<Handle, Session> getSessionCache() {
        return sessionCache;
    }
}
