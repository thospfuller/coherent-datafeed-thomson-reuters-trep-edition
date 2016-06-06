package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.coherentlogic.coherent.datafeed.client.Client;
import com.coherentlogic.coherent.datafeed.domain.Sample;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.reuters.rfa.common.Handle;
import com.reuters.ts1.TS1Constants;

/**
 * Unit test for the {@link TimeSeriesService} class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    locations="/spring/application-context.xml")
@Ignore
public class TimeSeriesServiceTest {

    @Autowired
    protected AbstractApplicationContext applicationContext = null;

    @Autowired
    protected AuthenticationServiceSpecification authenticationService = null;

    @Autowired
    private TimeSeriesGatewaySpecification timeSeriesService = null;

    private Handle loginHandle = null;

    private Client client = null;

    @Before
    public void setUp() throws Exception {
        applicationContext.registerShutdownHook();

        client = new Client (applicationContext);

        client.start();

        // This needs to be set in the operating system environment variables.
        String dacsId = System.getenv(DACS_ID);

        loginHandle = client.login(dacsId);

        client.waitForInitialisationToComplete();
    }

    @After
    public void tearDown() throws Exception {
        authenticationService.logout();
        applicationContext.close();
        applicationContext = null;
        client = null;
    }

    @Test
    public void getNextTimeSeries() throws InterruptedException, ExecutionException, TimeoutException {

        // At the moment we're going to wait for the dictionary to load.

        CompletableFuture<TimeSeries> result = timeSeriesService.getTimeSeriesFor(
            ServiceName.ELEKTRON_DD.toString(),
            loginHandle,
            null, // need to set the SessionBean here but right now this is null.
            "TRI.N",
            TS1Constants.DAILY_PERIOD
        );

        TimeSeries timeSeries = result.get(2L, TimeUnit.SECONDS);

        assertNotNull (timeSeries);

        List<String> headers = timeSeries.getHeaders();

        assertEquals (6, headers.size());

        assertEquals ("CLOSE", headers.get(0));
        assertEquals ("OPEN", headers.get(1));
        assertEquals ("HIGH", headers.get(2));
        assertEquals ("LOW", headers.get(3));
        assertEquals ("VOLUME", headers.get(4));
        assertEquals ("VWAP", headers.get(5));

        List<Sample> samples = timeSeries.getSamples();

        int sampleSize = samples.size();

        assertTrue (0 < sampleSize);

        Sample sample = samples.get(0);

        Long sampleDate = sample.getDate();

        assertNotNull (sampleDate);

        List<String> points = sample.getPoints();

        int pointSize = points.size();

        assertTrue (0 < pointSize);

        String point = points.get(0);

        assertNotNull (point);
    }
}
