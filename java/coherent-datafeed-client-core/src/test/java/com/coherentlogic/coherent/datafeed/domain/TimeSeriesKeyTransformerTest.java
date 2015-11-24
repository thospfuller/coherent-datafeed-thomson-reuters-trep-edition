package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.domain.TimeSeriesKeyTransformer.DIVIDER;
import static com.coherentlogic.coherent.datafeed.misc.Constants.ELEKTRON_DD;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.ts1.TS1Constants;

public class TimeSeriesKeyTransformerTest {

    private TimeSeriesKey key = null;

    private TimeSeriesKeyTransformer transformer = null;

    private static final String RIC = "TRI.N";

    private static final int PERIOD = TS1Constants.MONTHLY_PERIOD;

    private static final String KEY_TEXT =
        ELEKTRON_DD + DIVIDER + RIC + DIVIDER + PERIOD;

    @Before
    public void setUp() throws Exception {
        key = new TimeSeriesKey (Constants.ELEKTRON_DD, RIC, PERIOD);
        transformer = new TimeSeriesKeyTransformer ();
    }

    @After
    public void tearDown() throws Exception {
        key = null;
        transformer = null;
    }

    @Test
    public void testFromString() {

        TimeSeriesKey newKey = (TimeSeriesKey) transformer.fromString(KEY_TEXT);

        assertEquals (key, newKey);
    }

    @Test
    public void testToStringObject() {
        String result = transformer.toString(key);
        assertEquals (KEY_TEXT, result);
    }
}
