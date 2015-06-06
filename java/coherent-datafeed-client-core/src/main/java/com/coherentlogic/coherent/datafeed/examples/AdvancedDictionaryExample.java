package com.coherentlogic.coherent.datafeed.examples;

import static com.coherentlogic.coherent.datafeed.misc.Constants.AUTHENTICATION_SERVICE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_APP_CTX_PATH;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DICTIONARY_SERVICE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.FIELD_DICTIONARY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STATUS_RESPONSE_SERVICE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.THREE_MINUTES;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.DictionaryService;
import com.coherentlogic.coherent.datafeed.services.StatusResponseService;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.dictionary.FieldDictionary;

/**
 * An example application that authenticates, executes a query, and gets the
 * data.
 *
 * @deprecated This class is no longer needed.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AdvancedDictionaryExample {

    private static final Logger log =
        LoggerFactory.getLogger(AdvancedDictionaryExample.class);

    public static void main (String[] unused)
        throws JMSException, InterruptedException {

        AbstractApplicationContext applicationContext =
            new ClassPathXmlApplicationContext (
                DEFAULT_APP_CTX_PATH);

        applicationContext.registerShutdownHook();

        AuthenticationServiceSpecification authenticationService =
            (AuthenticationServiceSpecification) applicationContext.getBean(
                AUTHENTICATION_SERVICE);

        StatusResponseService statusResponseService =
            (StatusResponseService)
            applicationContext.getBean(STATUS_RESPONSE_SERVICE);

        DictionaryService dictionaryService =
            (DictionaryService) applicationContext.
                getBean(DICTIONARY_SERVICE);

        String dacsId = System.getenv(DACS_ID);

        authenticationService.login(dacsId);

        Handle handle = authenticationService.getHandle();

        log.info("Login handle is: " + handle);

        FieldDictionary fieldDictionary =
            (FieldDictionary) applicationContext.getBean(FIELD_DICTIONARY);

        dictionaryService.loadDictionaries(
            Constants.IDN_RDF,
            handle,
            "RWFFld",
            "RWFEnum"
        );

        int size = fieldDictionary.size();

        log.info("size: " + size);

        while (true) {
            String report =
                statusResponseService.getNextUpdateAsJSON(THREE_MINUTES);
            log.info("report: " + report);
        }
    }
}
