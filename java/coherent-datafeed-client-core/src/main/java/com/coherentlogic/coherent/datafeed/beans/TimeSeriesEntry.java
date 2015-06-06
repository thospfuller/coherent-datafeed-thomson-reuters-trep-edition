package com.coherentlogic.coherent.datafeed.beans;

import java.io.Serializable;

import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.ts1.TS1Series;

/**
 * A {@link TimeSeriesEntry} that holds instances of {@link TimeSeries}.
 *
 * @todo Should this be an instance of CachedEntry? If so, then this change will
 *  impact other classes so I'm going to leave this alone for the moment.
 *
 * @todo Should this be named TS1SeriesEntry?
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesEntry implements Serializable {

    private static final long serialVersionUID = 6436778936750295223L;

    private final String ric;

    private boolean loaded = false;

    private byte[] permissionData = null;

    /**
     * Created using the RIC as the item name so we do need one instance for
     * each CTS in the CTSH.
     */
    private transient TS1Series ts1Series = null;

    public TimeSeriesEntry(String ric) {
        this.ric = ric;
    }

    public TS1Series getTs1Series() {
        return ts1Series;
    }

    public void setTs1Series(TS1Series ts1Series) {
        this.ts1Series = ts1Series;
    }

    public String getRic() {
        return ric;
    }

    public TS1Series getTS1Series() {
        return ts1Series;
    }

    /**
     * This method is called from the TimeSeriesService.queryTimeSeriesFor
     * method.
     *
     * @param ts1Series
     */
    public void setTS1Series(TS1Series ts1Series) {
        this.ts1Series = ts1Series;
    }

    public byte[] getPermissionData() {
        return permissionData;
    }

    public void setPermissionData(byte[] permissionData) {
        this.permissionData = permissionData;
    }

    public void setPermissionData (OMMMsg msg) {
        byte[] bytes = msg.getPermissionData();
        setPermissionData(bytes);
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
