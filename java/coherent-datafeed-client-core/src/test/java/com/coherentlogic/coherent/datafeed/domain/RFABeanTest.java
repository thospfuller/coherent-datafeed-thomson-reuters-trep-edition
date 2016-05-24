package com.coherentlogic.coherent.datafeed.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RFABeanTest {

    private RFABean bean = null;

    @Before
    public void setUp() throws Exception {
        bean = new MarketPrice();
    }

    @After
    public void tearDown() throws Exception {
        bean = null;
    }

    @Test
    public void testNotDiffers1() {

        boolean result = bean.differs(null, null);

        assertFalse(result);
    }

    @Test
    public void testNotDiffes2() {

        boolean result = bean.differs("abc", "abc");

        assertFalse(result);
    }

    @Test
    public void testDiffers1() {

        boolean result = bean.differs(null, "");

        assertTrue(result);
    }

    @Test
    public void testDiffers2() {

        boolean result = bean.differs("abc", "123");

        assertTrue(result);
    }
}
