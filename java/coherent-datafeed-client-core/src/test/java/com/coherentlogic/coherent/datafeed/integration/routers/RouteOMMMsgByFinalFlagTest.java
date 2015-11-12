package com.coherentlogic.coherent.datafeed.integration.routers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.reuters.rfa.omm.OMMMsg;

/**
 * Unit test for the RouteOMMMsgByFinalFlag class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class RouteOMMMsgByFinalFlagTest {

    private final MessageChannel success = mock (MessageChannel.class);
    private final MessageChannel error = mock (MessageChannel.class);

    private RouteOMMMsgByFinalFlag router = null;

    private OMMMsg msg = mock (OMMMsg.class);

    @Before
    public void setUp() throws Exception {
        router = new RouteOMMMsgByFinalFlag (success, error);
    }

    @After
    public void tearDown() throws Exception {
        router = null;
    }

    void testIsFinal(
        final boolean flagValue, final MessageChannel expectedChannel) {

        when(msg.isFinal()).thenReturn(flagValue);

        Message<OMMMsg> message
            = MessageBuilder.withPayload(msg).build();

        Collection<MessageChannel> results
            = router.determineTargetChannels(message);

        List<MessageChannel> resultList
            = new ArrayList<MessageChannel> (results);

        assertEquals(1, resultList.size());
        assertEquals(expectedChannel, resultList.get (0));
    }

    @Test
    public void finalFlagIsTrue () {
        testIsFinal(true, error);
    }

    @Test
    public void finalFlagIsFalse () {
        testIsFinal(false, success);
    }
}
