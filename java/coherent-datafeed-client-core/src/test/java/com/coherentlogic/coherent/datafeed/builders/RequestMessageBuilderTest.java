package com.coherentlogic.coherent.datafeed.builders;

import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.exceptions.MissingDataException;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.EventQueue;
import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMPool;
import com.reuters.rfa.session.omm.OMMConsumer;

/**
 * Unit test for the RequestMessageBuilder class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RequestMessageBuilderTest {

    private RequestMessageBuilder builder = null;

    private OMMConsumer consumer = null;

    private EventQueue eventQueue = null;

    private OMMPool pool = null;

    private OMMEncoder encoder = null;

    private Client client = null;

    @Before
    public void setUp() throws Exception {

        consumer = mock (OMMConsumer.class);

        eventQueue = mock (EventQueue.class);

        pool = mock (OMMPool.class);

        encoder = mock (OMMEncoder.class);

        client = mock (Client.class);

        builder = new RequestMessageBuilder(
            consumer,
            eventQueue,
            pool,
            encoder
        );
    }

    @After
    public void tearDown() throws Exception {
        consumer = null;
        eventQueue = null;
        pool = null;
        encoder = null;
        builder = null;
        client = null;
    }

    @Test(expected=MissingDataException.class)
    public void testRegister() {
        builder.register(client, Constants.dIDN_RDF);
    }
}
