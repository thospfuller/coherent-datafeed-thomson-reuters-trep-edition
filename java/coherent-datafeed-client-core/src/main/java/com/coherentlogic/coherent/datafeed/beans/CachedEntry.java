package com.coherentlogic.coherent.datafeed.beans;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.common.Handle;

/**
 * Holds the data pertaining to a CachedEntry. This is used as a value held in
 * the cache which, for example, may be managing an instance of
 * {@link MarketPrice}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class CachedEntry implements Serializable {

    private static final long serialVersionUID = 5862264593081425045L;

    private final Handle handle;

    private final long timestamp = System.currentTimeMillis();

    public CachedEntry(
        Handle handle
    ) {
        super();
        this.handle = handle;
    }

    public Handle getHandle() {
        return handle;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Date getTimestampAsDate() {
        return new Date (timestamp);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
