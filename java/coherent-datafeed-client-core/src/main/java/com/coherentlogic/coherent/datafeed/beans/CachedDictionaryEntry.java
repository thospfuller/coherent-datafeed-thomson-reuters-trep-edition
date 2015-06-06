package com.coherentlogic.coherent.datafeed.beans;

import com.reuters.rfa.common.Handle;

/**
 * An implementation of {@link CachedEntry} specifically for the service that
 * loads the dictionary.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class CachedDictionaryEntry extends CachedEntry {

    private static final long serialVersionUID = 8029458096723410124L;

    public CachedDictionaryEntry(Handle handle) {
        super(handle);
    }
}
