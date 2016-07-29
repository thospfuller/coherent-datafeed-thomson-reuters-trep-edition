package com.coherentlogic.coherent.datafeed.services.message.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;

/**
 * This class delegates calls to the {@link #authenticationService}'s login
 * method.
 *
 * Note that the logout method does not require any params, however we've left
 * the type set to String in both cases -- treat this as ignored. 
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class LogoutMessageProcessor
    extends AbstractAuthenticationMessageProcessor<String, String> {

    private static final Logger log =
        LoggerFactory.getLogger(LogoutMessageProcessor.class);

    public LogoutMessageProcessor(AuthenticationServiceSpecification authenticationService) {
        super(authenticationService);
    }

    @Override
    public Message<String> process(Message<String> message) {

        log.info("logoutMessageProcessor.process: method invoked.");

        AuthenticationServiceSpecification authenticationService = getAuthenticationService();

        authenticationService.logout();

        return message;
    }
}
