package com.coherentlogic.coherent.datafeed.beans;

import java.io.Serializable;

import com.reuters.ts1.TS1Def;

/**
 * An entry that helps track requests for {@link TS1Def} information.
 *
 * @todo This class replaces the TimeSeriesEntries.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefEntry implements Serializable {

    private static final long serialVersionUID = 1950159429292066405L;

    private boolean loaded = false;

    public TS1DefEntry () {
    }

    public TS1DefEntry(boolean loaded) {
        this.loaded = loaded;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
