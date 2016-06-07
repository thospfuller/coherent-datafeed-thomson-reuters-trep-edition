package com.coherentlogic.coherent.datafeed.services.message.processors;

import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;

/**
 * This class delegates calls to the {@link #authenticationService}'s login
 * method.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractAuthenticationMessageProcessor<S, T>
    implements MessageProcessorSpecification<S, T> {

    private final AuthenticationServiceSpecification authenticationService;

    public AbstractAuthenticationMessageProcessor(
        AuthenticationServiceSpecification authenticationService
    ) {
        this.authenticationService = authenticationService;
    }

    public AuthenticationServiceSpecification getAuthenticationService() {
        return authenticationService;
    }
}
