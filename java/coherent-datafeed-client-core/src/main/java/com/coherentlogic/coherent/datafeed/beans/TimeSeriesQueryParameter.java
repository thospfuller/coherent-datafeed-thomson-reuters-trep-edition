package com.coherentlogic.coherent.datafeed.beans;

import java.util.HashMap;
import java.util.Map;

import com.coherentlogic.coherent.datafeed.exceptions.ConversionFailedException;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DAILY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.WEEKLY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MONTHLY;
import com.reuters.rfa.common.Handle;
import com.reuters.ts1.TS1Constants;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesQueryParameter extends QueryParameter {

    private static final long serialVersionUID = 1846548007261409136L;

    private final int period;

    private static final Map<String, Integer> periodMap =
        new HashMap<String, Integer> ();

    static {
        periodMap.put(DAILY, TS1Constants.DAILY_PERIOD);
        periodMap.put(WEEKLY, TS1Constants.WEEKLY_PERIOD);
        periodMap.put(MONTHLY, TS1Constants.MONTHLY_PERIOD);
    }

    public TimeSeriesQueryParameter(
        String serviceName,
        Handle loginHandle,
        String item,
        Integer period
    ) {
        super(serviceName, loginHandle, item);
        this.period = period;
    }

    /**
     * This ctor is used when R calls into Java.
     */
    public TimeSeriesQueryParameter(
        String serviceName,
        Handle loginHandle,
        String item,
        String period
    ) {
        super(serviceName, loginHandle, item);

        Integer periodValue = periodMap.get(period);

        if (periodValue == null)
            throw new ConversionFailedException("The period '" + period +
                "' is not valid.");

        this.period = Integer.valueOf(periodValue);
    }

    public int getPeriod() {
        return period;
    }
}
