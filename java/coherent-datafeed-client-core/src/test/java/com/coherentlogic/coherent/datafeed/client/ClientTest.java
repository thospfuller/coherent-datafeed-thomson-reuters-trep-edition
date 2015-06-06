package com.coherentlogic.coherent.datafeed.client;

import static org.mockito.Mockito.mock;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;

import com.coherentlogic.coherent.datafeed.exceptions.ClientNotInitializedException;

/**
 * Unit test for the {@link Client} class.
 *
 * Note that we consider this to be a unit test because the logic we're
 * exercising does not require an actual connection to Thomson Reuters.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Ignore
public class ClientTest {

    private AbstractApplicationContext applicationContext = null;

    private Client client = null;

    @Before
    public void setUp() throws Exception {
        applicationContext = mock (AbstractApplicationContext.class);
        client = new Client ();
    }

    @After
    public void tearDown() throws Exception {

        if (client.isStarted())
            client.stop();

        applicationContext = null;
        client = null;
    }

    @Test
    public void testCallStartTwiceNoExceptionExpected() {
        // Configure the client such that it appears that it has already been
        // started.
        client.setApplicationContext(applicationContext);
        client.start();
        client.start();
    }

    @Test
    public void testCallStopTwiceNoExceptionExpected() {
        // Configure the client such that it appears that it has already been
        // stopped.
        client.setApplicationContext(null);
        client.stop();
        client.stop();
    }

    @Test(expected=ClientNotInitializedException.class)
    public void assertHasStartedWithFalse() {
        client.setStarted(false);
        client.assertHasStarted();
    }

    @Test
    public void assertHasStartedWithTrue() {
        client.setStarted(true);
        client.assertHasStarted();
    }

    @Test(expected=ClientNotInitializedException.class)
    public void getAuthenticationServiceNotStarted () {
        client.getAuthenticationService();
    }

    @Test(expected=ClientNotInitializedException.class)
    public void getMarketPriceServiceNotStarted () {
        client.getMarketPriceService();
    }

    @Test(expected=ClientNotInitializedException.class)
    public void getTimeSeriesServiceNotStarted () {
        client.getTimeSeriesService();
    }
}
