package com.coherentlogic.coherent.datafeed.examples;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
import com.coherentlogic.coherent.datafeed.domain.AttribInfo;
import com.coherentlogic.coherent.datafeed.domain.Sample;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesGatewaySpecification;
import com.reuters.rfa.common.Handle;
import com.reuters.ts1.TS1Constants;

/**
 * An example application that authenticates, executes a query, and gets the
 * data.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @see http://docs.spring.io/spring-boot/docs/1.2.7.RELEASE/reference/htmlsingle/#getting-started-installing-the-cli
 * @see https://spring.io/guides/gs/consuming-rest/
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages="com.coherentlogic.coherent.datafeed")
public class TimeSeriesExample implements CommandLineRunner {

    private static final Logger log =
        LoggerFactory.getLogger(TimeSeriesExample.class);

    @Autowired
    private AbstractApplicationContext applicationContext;

    public static void main (String[] unused) throws Exception {

        SpringApplicationBuilder builder = new SpringApplicationBuilder (TimeSeriesExample.class);

        builder
            .web(false)
            .headless(false)
            .run(unused);
    }

    @Override
    public void run(String... args) throws Exception {

        Client client = new Client (applicationContext);

        client.start();

        // This needs to be set in the operating system environment variables.
        String dacsId = System.getenv(DACS_ID);

//        final StatusResponseServiceSpecification statusResponseService =
//            client.getStatusResponseService();

        final Handle loginHandle = client.login(dacsId);

        client.waitForInitialisationToComplete();

        log.info("Login handle is: " + loginHandle);

        final TimeSeriesGatewaySpecification timeSeriesService =
            client.getTimeSeriesService();

        CompletableFuture<TimeSeries> timeSeriesPromise = null;

        try {
            timeSeriesPromise = timeSeriesService.getTimeSeriesFor(
                Constants.ELEKTRON_DD,
                loginHandle,
                "TRI.N",
                TS1Constants.WEEKLY_PERIOD
            );
        } catch (Throwable thrown) {
            thrown.printStackTrace(System.err);
            System.exit(-9999);
        }

        System.out.println ("timeSeriesPromise: " + timeSeriesPromise);

        TimeSeries timeSeries = timeSeriesPromise.get(60, TimeUnit.SECONDS);

        if (timeSeries != null) {

            AttribInfo attribInfo = timeSeries.getAttribInfo();

            System.out.print("timeSeries: " + timeSeries + ", attribInfo: " + attribInfo + ", sampleSize: " + timeSeries.getSamples().size() + "\n\n\n");

            DateFormat formatter = new SimpleDateFormat("yyyy MMM dd   HH:mm");

            String TABS = "\t\t\t\t\t";

            boolean closeAdded = false;

            for (String nextHeader : timeSeries.getHeaders()) {
                if (!closeAdded) {
                    closeAdded = true;
                    System.out.print(nextHeader + TABS);
                } else {
                    System.out.print(nextHeader + "\t\t\t");
                }
            }

            for (Sample sample : timeSeries.getSamples()) {

                long timeMillis = sample.getDate();

                Calendar calendar = Calendar.getInstance();

                calendar.setTimeInMillis(timeMillis);

                String date = formatter.format(calendar.getTime());

                System.out.print("\n" + date);

                for (String nextPoint : sample.getPoints())
                    System.out.print ("\t\t\t" + nextPoint);
            }
        } else {
            log.warn("The timeSeries reference is null.");
        }
//        System.exit(-9999);
    }
}
