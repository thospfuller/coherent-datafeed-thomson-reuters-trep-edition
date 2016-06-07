package com.coherentlogic.coherent.datafeed.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.coherentlogic.coherent.data.model.core.exceptions.ConversionFailedException;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.ts1.TS1Constants;

/**
 * Unit test for the {@link TimeSeriesQueryParameterTest} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesQueryParameterTest {

    private static final String UNKNOWN = "foo";

    @Test
    public void testCtorWithDailyPeriod () {
        TimeSeriesQueryParameter timeSeriesQueryParameter =
            new TimeSeriesQueryParameter(UNKNOWN, null, null, Constants.DAILY);

        assertEquals (
            TS1Constants.DAILY_PERIOD,
            timeSeriesQueryParameter.getPeriod()
        );
    }

    @Test
    public void testCtorWithWeeklyPeriod () {
        TimeSeriesQueryParameter timeSeriesQueryParameter =
            new TimeSeriesQueryParameter(UNKNOWN, null, null, Constants.WEEKLY);

        assertEquals (
            TS1Constants.WEEKLY_PERIOD,
            timeSeriesQueryParameter.getPeriod()
        );
    }

    @Test
    public void testCtorWithMonthlyPeriod () {
        TimeSeriesQueryParameter timeSeriesQueryParameter =
            new TimeSeriesQueryParameter(
                UNKNOWN, null, null, Constants.MONTHLY);

        assertEquals (
            TS1Constants.MONTHLY_PERIOD,
            timeSeriesQueryParameter.getPeriod()
        );
    }

    @Test(expected=ConversionFailedException.class)
    public void testCtorWithUnknownPeriod () {
        TimeSeriesQueryParameter timeSeriesQueryParameter =
            new TimeSeriesQueryParameter(UNKNOWN, null, null, UNKNOWN);
    }

    @Test(expected=ConversionFailedException.class)
    public void testCtorWithNullPeriodPeriod () {
        TimeSeriesQueryParameter timeSeriesQueryParameter =
            new TimeSeriesQueryParameter(UNKNOWN, null, null, (String) null);
    }
}
