package net.coherentlogic.coherent.datafeed.examples;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.AbstractApplicationContext;

import com.coherentlogic.coherent.datafeed.client.Client;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntries;
import com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants;
import com.coherentlogic.coherent.datafeed.services.DirectoryGatewaySpecification;
import com.reuters.rfa.common.Handle;

/**
 * An example application that authenticates, executes a query, and gets the
 * data.
 * 
 * @see (RFA) Examples/com/reuters/rfa/example/framework/sub/
 *      DirectoryClient.java
 * 
 * @see (RFA) com.reuters.rfa.example.omm.dictionary.DictionaryDemo
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
//@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan(basePackages="com.coherentlogic.coherent.datafeed")
public class DirectoryServiceExample implements CommandLineRunner, MarketPriceConstants {

    private static final Logger log = LoggerFactory
        .getLogger(DirectoryServiceExample.class);

    @Autowired
    private AbstractApplicationContext applicationContext;

    public static void main (String[] unused) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder (DirectoryServiceExample.class);

        builder
            .web(false)
            .headless(false)
            .run(unused);
    }

    @Override
    public void run (String... unused) throws InterruptedException {

        log.info("main: method begins.");

        String dacsId = System.getenv(DACS_ID);

        // For some reason, including the appCtx below causes the waitFor
        // method to not return -- not sure what's going on here.
        Client client = new Client(applicationContext);

        client.start();

        Handle loginHandle = client.login(dacsId);

        log.info("waitForInitialisationToComplete: method begins.");

        //client.waitForInitialisationToComplete();

        log.info("waitForInitialisationToComplete: method returned.");

        DirectoryGatewaySpecification directoryService =
            (DirectoryGatewaySpecification) client.getDirectoryService();

        log.info("Will invoke the getDictionaryEntries method.");

        DirectoryEntries directoryEntries = directoryService.getDirectoryEntries();

        log.info("The getDirectoryEntries method has returned.");

        log.info("directoryEntries: " + ToStringBuilder.reflectionToString(directoryEntries));

        Thread.sleep(5000L);

        log.info("main: method ends.");
    }
}
