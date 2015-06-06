package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.exceptions.InvalidApplicationSessionException;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;

/**
 * This class delegates calls to the {@link #authenticationService}'s login
 * method.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class LoginMessageProcessor
    extends AbstractAuthenticationMessageProcessor<String, Handle> {

    private static final Logger log =
        LoggerFactory.getLogger(LoginMessageProcessor.class);

    private LoginMessageProcessor(
        AuthenticationServiceSpecification authenticationService,
        Cache<Handle, Session> sessionCache
    ) {
        super(authenticationService, sessionCache);
    }

    /**
     * 
     */
    @Override
    @Transactional
    public Message<Handle> process(Message<String> message) {

        Message<Handle> result = null;

        AuthenticationServiceSpecification authenticationService =
            getAuthenticationService();

        Cache<Handle, Session> sessionCache = getSessionCache();

        /* Note that it is possible that this method is paused prior and then
         * the AuthenticationMessageEnricher.enrich method is executed, which
         * can result in an NPE progresses. The solution is to sync here and in
         * the AuthenticationMessageEnricher.enrich method.
         *
         * @TODO: Investigate using transactions and the cache lock method as an
         *  alternative.
         */
//        synchronized (sessionCache) {

            String dacsId = message.getPayload();

            if (dacsId == null)
                throw new InvalidApplicationSessionException(
                    "The dacsId is null.");

            Handle loginHandle = authenticationService.login(dacsId);

//            sessionCache.getAdvancedCache().lock(loginHandle);

            log.info("loginHandle: " + loginHandle);

            Session session = (Session) sessionCache.get(loginHandle);

            assertNotNull (SESSION, session);

            result = MessageBuilder
                .withPayload(loginHandle)
                .setHeader(SESSION, session)
                .build();
//        }
        return result;
    }
}
