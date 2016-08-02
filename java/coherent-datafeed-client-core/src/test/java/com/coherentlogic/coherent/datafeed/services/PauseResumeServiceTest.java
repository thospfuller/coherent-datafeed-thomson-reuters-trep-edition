package com.coherentlogic.coherent.datafeed.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.adapters.IntegrationEndpointAdapter;

/**
 * Unit test for the {@link IntegrationEndpointAdapter} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class PauseResumeServiceTest {

    private WorkflowInverterService service = null;

    @Before
    public void setUp() throws Exception {
        service = new WorkflowInverterService();
    }

    @After
    public void tearDown() throws Exception {
        service = null;
    }

    /**
     * This is not a multi-threaded test so it's not really rigorous.
     */
    @Test
    public void testInitialisationSuccessful() {
        service.setSuccessFlag(true);
        assertEquals (true, service.isSuccess());
    }

    /**
     * This is not a multi-threaded test so it's not really rigorous.
     */
    @Test
    public void testInitialisationFailed() {
        service.setSuccessFlag(false);
        assertEquals (false, service.isSuccess());
    }

//    @Test
    public void testTimeout () throws InterruptedException {
        boolean result = service.pause();
        assertFalse(result);
    }
}
