package com.coherentlogic.coherent.datafeed.examples;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.client.Client;
import com.coherentlogic.coherent.datafeed.services.DictionaryGatewaySpecification;
import com.reuters.rfa.common.Handle;

/**
 * An example application that authenticates, executes a query, and gets the
 * data.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DictionaryServiceExample {

    private static final Logger log =
        LoggerFactory.getLogger(DictionaryServiceExample.class);

    public static void main (String[] unused) {

        log.info("main: method begins.");

        String dacsId = System.getenv(DACS_ID);

        // For some reason, including the appCtx below causes the waitFor
        // method to not return -- not sure what's going on here.
        Client client = new Client (); //applicationContext);

        client.start();

        Handle loginHandle = client.login(dacsId);

        client.waitForInitialisationToComplete();

        DictionaryGatewaySpecification dictionaryService =
            (DictionaryGatewaySpecification)
                client.getDictionaryService();

        String json = dictionaryService.getDictionaryEntriesAsJSON();

        log.info("json: " + json);

        log.info("main: method ends.");
    }
}
