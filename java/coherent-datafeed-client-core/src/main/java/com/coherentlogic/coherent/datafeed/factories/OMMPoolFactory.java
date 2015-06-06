package com.coherentlogic.coherent.datafeed.factories;

import com.reuters.rfa.omm.OMMPool;

/**
 * Factory class used for creating instances of
 * {@link com.reuters.rfa.omm.OMMPool OMMPool}.
 *
 * @todo This will likely need to be added as a custom bean, as we'll need to
 *  call destroy once we're finished using this.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class OMMPoolFactory implements Factory<OMMPool> {

    @Override
    public OMMPool getInstance() {
        return OMMPool.create();
    }
}
