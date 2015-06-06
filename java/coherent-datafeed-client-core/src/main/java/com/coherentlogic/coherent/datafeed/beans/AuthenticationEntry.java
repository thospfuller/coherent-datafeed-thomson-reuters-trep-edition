package com.coherentlogic.coherent.datafeed.beans;

import com.reuters.rfa.common.Handle;

/**
 * A representation of an authenication entry which is held in the cache.
 *
 * @deprecated To be replaced by the Session.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AuthenticationEntry extends CachedEntry {

    private static final long serialVersionUID = -4797955334473366755L;

    private final String dacsId;

    public AuthenticationEntry(Handle handle, String dacsId) {
        super(handle);
        this.dacsId = dacsId;
    }

    public String getDacsId() {
        return dacsId;
    }
}
