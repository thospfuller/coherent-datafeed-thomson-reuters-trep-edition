package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.coherentlogic.coherent.datafeed.beans.LoginQueryParameters;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.reuters.rfa.common.Handle;

/**
 * This class delegates calls to the {@link #authenticationService}'s login
 * method.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class LoginMessageProcessor implements MessageProcessorSpecification<LoginQueryParameters, Handle> {

    private static final Logger log = LoggerFactory.getLogger(LoginMessageProcessor.class);

    private final AuthenticationServiceSpecification authenticationService;

    private final Map<Handle, String> dacsIdCache;

    private final Map<String, SessionBean> sessionBeanCache;

    public LoginMessageProcessor(
        AuthenticationServiceSpecification authenticationService,
        Map<Handle, String> dacsIdCache,
        Map<String, SessionBean> sessionBeanCache
    ) {
        this.authenticationService = authenticationService;
        this.dacsIdCache = dacsIdCache;
        this.sessionBeanCache = sessionBeanCache;
    }

    /**
     * 
     */
    @Override
    public Message<Handle> process(Message<LoginQueryParameters> message) {

        LoginQueryParameters loginQueryParameters = message.getPayload();

        SessionBean sessionBean = loginQueryParameters.getSessionBean();

        String dacsId = sessionBean.getDacsId();

        assertNotNull("dacsId", dacsId);

        sessionBeanCache.put(dacsId, sessionBean);

        Handle handle = authenticationService.login(sessionBean);

        log.debug("handle: " + handle);

        dacsIdCache.put(handle, dacsId);

        sessionBean.setHandle(handle);

        Message<Handle> result = MessageBuilder
            .withPayload(handle)
            .build();

        return result;
    }
}
