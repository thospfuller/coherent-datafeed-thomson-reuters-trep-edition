package com.coherentlogic.coherent.datafeed.domain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.exceptions.TimeSeriesConversionFailedException;

/**
 * Unit test for the {@link TimeSeries} class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesTest {

    private TimeSeries timeSeries = null;

    @Before
    public void setUp() throws Exception {

        timeSeries = new TimeSeries();

        long currentTimeMillis = 1000L;

        long currentTimeMillis1 = currentTimeMillis,
            currentTimeMillis2 = currentTimeMillis + 1000,
            currentTimeMillis3 = currentTimeMillis + 2000,
            currentTimeMillis4 = currentTimeMillis + 3000,
            currentTimeMillis5 = currentTimeMillis + 4000;

        Sample sample1 = new Sample ();

        // DATE, FOO, BAR, BAZ, BOO, FEE
        sample1.setDate(currentTimeMillis1);
        sample1.addPoint("a1", "b1", "c1", "d1", "e1");

        Sample sample2 = new Sample ();

        sample2.setDate(currentTimeMillis2);
        sample2.addPoint("a2", "b2", "c2", "d2", "e2");

        Sample sample3 = new Sample ();

        sample3.setDate(currentTimeMillis3);
        sample3.addPoint("a3", "b3", null, "d3", "e3");

        Sample sample4 = new Sample ();

        sample4.setDate(currentTimeMillis4);
        sample4.addPoint("a4", "b4", "c4", "d4", "e4");

        Sample sample5 = new Sample ();

        sample5.setDate(currentTimeMillis5);
        sample5.addPoint("a5", "b5", "c5", "d5", "e5");

        Sample[] samples = new Sample[] {
            sample1,
            sample2,
            sample3,
            sample4,
            sample5
        };

        timeSeries.addSample(samples);

        timeSeries.addHeader("DATE", "FOO", "BAR", "BAZ", "BOO", "FEE");
    }

    @After
    public void tearDown() throws Exception {
        timeSeries = null;
    }

    @Test
    public void testGetValuesForHeaderHappyPath() {

        // We want the column BAR so the list should be of size 5 containing b1-b5.
        List<String> barResult = timeSeries.getValuesForHeader("BAR");

        // Size is date + all entries under 
        assertEquals(5, barResult.size());
        assertEquals("b1", barResult.get(0));

        // We want the column BAR so the list should be of size 5 containing b1-b5.
        List<String> feeResult = timeSeries.getValuesForHeader("FEE");

        assertEquals(5, feeResult.size());
        assertEquals("e5", feeResult.get(4));
    }

    @Test
    public void testGetValuesForHeaderCheckNulls() {

        // We want the column BAR so the list should be of size 5 containing b1-b5.
        List<String> barResult = timeSeries.getValuesForHeader("BAZ");

        // Size is date + all entries under 
        assertEquals(5, barResult.size());
        assertEquals("NA", barResult.get(2));
    }

    @Test(expected=TimeSeriesConversionFailedException.class)
    public void testGetValuesForSampleWithNoPoints() {

        timeSeries.addHeader("FUBAR");

        Sample sample6 = new Sample ();

        timeSeries.addSample(sample6);

        sample6.setDate(System.currentTimeMillis());
        // sample6.addPoint(/* Deliberately empty. */);

        // We want the column BAR so the list should be of size 5 containing b1-b5.
        List<String> fubarResult = timeSeries.getValuesForHeader("BAZ");
    }
}
