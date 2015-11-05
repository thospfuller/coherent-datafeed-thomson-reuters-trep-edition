package com.coherentlogic.coherent.datafeed.examples;

import static com.coherentlogic.coherent.datafeed.misc.Constants.AUTHENTICATION_SERVICE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_APP_CTX_PATH;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DICTIONARY_SERVICE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_BY_ORDER_SERVICE;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.FieldDictionaryHolder;
import com.coherentlogic.coherent.datafeed.services.MarketByOrderService;
import com.reuters.rfa.common.Handle;

/**
 * An example application that authenticates, executes a query, and gets the
 * data.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketByOrderExample {

    private static final Logger log =
        LoggerFactory.getLogger(MarketByOrderExample.class);

    public static void main (String[] unused) throws JMSException {

        AbstractApplicationContext applicationContext =
            new ClassPathXmlApplicationContext (
                DEFAULT_APP_CTX_PATH);

        applicationContext.registerShutdownHook();

        AuthenticationServiceSpecification authenticationService =
            (AuthenticationServiceSpecification) applicationContext.getBean(
                AUTHENTICATION_SERVICE);

        MarketByOrderService marketByOrderService = (MarketByOrderService)
            applicationContext.getBean(MARKET_BY_ORDER_SERVICE);

        // This needs to be set in the operating system environment variables.
        String dacsId = System.getenv(DACS_ID);

        authenticationService.login(dacsId);

        Handle handle = authenticationService.getHandle();

        // IBM.ARC
        List<Handle> itemHandles = marketByOrderService.query(
            Constants.ELEKTRON_DD,
            handle,
            "IBM.O"
        );

        for (Handle nextHandle : itemHandles)
            log.debug("nextHandle: " + nextHandle);

        FieldDictionaryHolder fieldDictionaryHolder =
            (FieldDictionaryHolder) applicationContext.getBean(DICTIONARY_SERVICE);

        log.info("fieldDictionary: " + fieldDictionaryHolder.getFieldDictionary());

//        long ctr = 0;
//
//        while (true) {
//            String next = marketByOrderService.getNextUpdateAsJSON();
//
//            System.out.println ("next[" + ctr + "]: " + next);
//
//            ctr++;
//        }
    }
}
