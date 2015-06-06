package com.coherentlogic.coherent.datafeed.integration.endpoints;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.exceptions.FatalRuntimeException;
import com.reuters.rfa.common.Event;

/**
 * Unit test for the {@link EventDrivenEndpoint} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class EventDrivenEndpointTest {

    private GatewayDelegate gatewayDelegate = null;

    private EventDrivenEndpoint eventDrivenEndpoint = null;

    @Before
    public void setUp() throws Exception {

        gatewayDelegate = mock (GatewayDelegate.class);

        eventDrivenEndpoint = new EventDrivenEndpoint(gatewayDelegate);
    }

    @After
    public void tearDown() throws Exception {
        gatewayDelegate = null;
        eventDrivenEndpoint = null;
    }

    @Test
    public void processEventHappyPath() {

        Event event = mock (Event.class);

        eventDrivenEndpoint.processEvent(event);
    }

    /**
     * Note that we currently expect the exception to be re-thrown, which is
     * verified here.
     */
    @Test(expected=FatalRuntimeException.class)
    public void processEventExceptionOnSend() {

        doThrow (
            new FatalRuntimeException (
                "Help!"
            )
        ).when(
            gatewayDelegate
        ).send(
            any (
                Object.class
            )
        );

        Event event = mock (Event.class);

        eventDrivenEndpoint.processEvent(event);
    }
}
