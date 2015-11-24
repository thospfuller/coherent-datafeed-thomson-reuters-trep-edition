package com.coherentlogic.coherent.datafeed.domain;

import org.infinispan.query.Transformable;

import com.coherentlogic.coherent.datafeed.misc.Constants;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Transformable(transformer = TimeSeriesKeyTransformer.class)
public class TimeSeriesKey {

    private String serviceName = null;

    private String ric = null;

    private int period = Constants.DEFAULT_INT_RETURN_VALUE;

    public TimeSeriesKey() {
    }

    public TimeSeriesKey(String serviceName, String ric, int period) {
        this.serviceName = serviceName;
        this.ric = ric;
        this.period = period;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRic() {
        return ric;
    }

    public void setRic(String ric) {
        this.ric = ric;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "TimeSeriesKey [serviceName=" + serviceName + ", ric=" + ric
                + ", period=" + period + "]";
    }
}
