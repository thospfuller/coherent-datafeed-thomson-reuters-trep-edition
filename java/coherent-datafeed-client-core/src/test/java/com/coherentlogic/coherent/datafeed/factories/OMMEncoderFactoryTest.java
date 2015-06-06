package com.coherentlogic.coherent.datafeed.factories;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMPool;

/**
 * Unit test for the OMMEncoderFactory class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class OMMEncoderFactoryTest {

    private static final short TEST_MSG_TYPE = 123, TEST_SIZE = 232;

    private OMMEncoderFactory factory = null;

    private OMMEncoder encoder = null;

    @Before
    public void setUp() throws Exception {

        OMMPool pool = mock (OMMPool.class);

        encoder = mock (OMMEncoder.class);

        when(pool.acquireEncoder()).thenReturn(encoder);

        factory = new OMMEncoderFactory (pool, TEST_MSG_TYPE, TEST_SIZE);
    }

    @After
    public void tearDown() throws Exception {
        factory = null;
        encoder = null;
    }

    @Test
    public void testAllPropertiesArePassedToInit() {

        factory.getInstance();

        verify (encoder).initialize(TEST_MSG_TYPE, TEST_SIZE);
    }
}
