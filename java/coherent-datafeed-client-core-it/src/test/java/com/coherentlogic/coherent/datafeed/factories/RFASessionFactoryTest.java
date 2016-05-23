package com.coherentlogic.coherent.datafeed.factories;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.exceptions.ApplicationInitializationFailedException;

/**
 * An integration test for the ({@link RFASessionFactory} class. The registry settings must be in place for this test
 * to run and this means the session name "mySession" must be in the registry (or the test will need to be modified).
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class RFASessionFactoryTest {

    private RFASessionFactory rfaSessionFactory = null;

    @Before
    public void setUp() throws Exception {
        rfaSessionFactory = new RFASessionFactory ("mySession");

        System.clearProperty(RFASessionFactory.CDATAFEED_SESSION_NAME);
    }

    @After
    public void tearDown() throws Exception {
        rfaSessionFactory = null;
    }

    /**
     * Note that if the registry is missing TREP settings then this test will fail.
     */
    @Test
    public void testGetInstance() {
        assertNotNull (rfaSessionFactory.getInstance());
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void testGetInstanceOverrideDefault () {

        System.setProperty(RFASessionFactory.CDATAFEED_SESSION_NAME, "blahSession");

        assertNotNull (rfaSessionFactory.getInstance());

        System.clearProperty(RFASessionFactory.CDATAFEED_SESSION_NAME);
    }
}
