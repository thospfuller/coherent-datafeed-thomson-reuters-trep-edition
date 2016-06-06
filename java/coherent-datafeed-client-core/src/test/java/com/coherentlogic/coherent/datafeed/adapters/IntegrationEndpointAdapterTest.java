package com.coherentlogic.coherent.datafeed.adapters;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.services.FlowInverterService;

/**
 * Unit test for the {@link IntegrationEndpointAdapter} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class IntegrationEndpointAdapterTest {

    private final FlowInverterService pauseResumeService =
        new FlowInverterService ();

    private IntegrationEndpointAdapter adapter = null;

    @Before
    public void setUp() throws Exception {
        adapter = new IntegrationEndpointAdapter(pauseResumeService);
    }

    @After
    public void tearDown() throws Exception {
        adapter = null;
    }

    @Test
    public void testInitialisationSuccessful() {
        adapter.initialisationSuccessful(null);
        assertEquals (true, pauseResumeService.isSuccess());
    }

    @Test
    public void testInitialisationFailed() {
        adapter.initialisationFailed(null);
        assertEquals (false, pauseResumeService.isSuccess());
    }
}
