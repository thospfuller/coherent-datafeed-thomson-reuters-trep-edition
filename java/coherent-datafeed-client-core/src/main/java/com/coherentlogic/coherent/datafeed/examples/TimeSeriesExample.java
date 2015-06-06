package com.coherentlogic.coherent.datafeed.examples;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.client.Client;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.coherentlogic.coherent.datafeed.services.StatusResponseServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesGatewaySpecification;
import com.reuters.rfa.common.Handle;
import com.reuters.ts1.TS1Constants;

/**
 * An example application that authenticates, executes a query, and gets the
 * data.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesExample {

    private static final Logger log =
        LoggerFactory.getLogger(TimeSeriesExample.class);

    public static void main (String[] unused)
        throws JMSException, InterruptedException {

        Client client = new Client ();

        client.start();

        // This needs to be set in the operating system environment variables.
        String dacsId = System.getenv(DACS_ID);

        final StatusResponseServiceSpecification statusResponseService =
            client.getStatusResponseService();

//        client.installLicense(
//            "C:/Temp/CDatafeedTrialLicenseForCMEGroup-Expires10Jan2014.lic");

        final Handle loginHandle = client.login(dacsId);

        client.waitForInitialisationToComplete();

        log.info("Login handle is: " + loginHandle);

//        StatusResponse statusResponse =
//            statusResponseService.getNextUpdate(5000L);
//
//        log.info("statusResponse: " + statusResponse);

        final TimeSeriesGatewaySpecification timeSeriesService =
            client.getTimeSeriesService();

        timeSeriesService.queryTimeSeriesFor(
            Constants.IDN_RDF,
            loginHandle,
            "TRI.N",
            TS1Constants.WEEKLY_PERIOD
        );

//        TimeSeries timeSeries =
//            timeSeriesService.getNextUpdate(2500L);

        String json = timeSeriesService.getNextUpdateAsJSON(7500L);

        System.out.println ("json: " + json);

//        System.out.print("timeSeries: " + timeSeries + ", sampleSize: " + timeSeries.getSamples().size());
//
//        DateFormat formatter = new SimpleDateFormat("yyyy MMM dd   HH:mm");
//
//        String TABS = "\t\t\t";
//
//        System.out.print ("\tDATE");
//
//        for (String nextHeader : timeSeries.getHeaders())
//            System.out.print(TABS + nextHeader);
//
//        for (Sample sample : timeSeries.getSamples()) {
//
//            long timeMillis = sample.getDate();
//
//            Calendar calendar = Calendar.getInstance();
//
//            calendar.setTimeInMillis(timeMillis);
//
//            String date = formatter.format(calendar.getTime());
//
//            System.out.print("\n" + date);
//
//            for (String nextPoint : sample.getPoints())
//                System.out.print (TABS + nextPoint);
//        }
    }
}