package com.coherentlogic.coherent.datafeed.domain;

import org.infinispan.query.Transformable;

import com.coherentlogic.coherent.datafeed.misc.Constants;

/**
 * 
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + period;
        result = prime * result + ((ric == null) ? 0 : ric.hashCode());
        result = prime * result
                + ((serviceName == null) ? 0 : serviceName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TimeSeriesKey other = (TimeSeriesKey) obj;
        if (period != other.period)
            return false;
        if (ric == null) {
            if (other.ric != null)
                return false;
        } else if (!ric.equals(other.ric))
            return false;
        if (serviceName == null) {
            if (other.serviceName != null)
                return false;
        } else if (!serviceName.equals(other.serviceName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TimeSeriesKey [serviceName=" + serviceName + ", ric=" + ric
                + ", period=" + period + "]";
    }
}
