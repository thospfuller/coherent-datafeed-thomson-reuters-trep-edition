package com.coherentlogic.coherent.datafeed.beans;

import java.io.Serializable;

import com.reuters.rfa.common.Handle;

/**
 * An cached entry that contains an object.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @deprecated This class is no longer used and should be removed.
 *
 * @param <O> The type of object held by this instance. 
 */
public class CachedObject<O extends Serializable> extends CachedEntry {

    private static final long serialVersionUID = 3976233765231606583L;

    private O object;

    public CachedObject(Handle handle) {
        super(handle);
    }

    public O getObject() {
        return object;
    }

    public void setObject(O object) {
        this.object = object;
    }
}
