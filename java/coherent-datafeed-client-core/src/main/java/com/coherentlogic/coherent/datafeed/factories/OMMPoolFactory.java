package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.reuters.rfa.omm.OMMPool;

/**
 * Factory class used for creating instances of
 * {@link com.reuters.rfa.omm.OMMPool OMMPool}.
 *
 * @todo This will likely need to be added as a custom bean, as we'll need to call destroy once we're finished using
 *  this.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMPoolFactory implements TypedFactory<OMMPool> {

    public static final String BEAN_NAME = "poolFactory";

    @Override
    public OMMPool getInstance() {
        return OMMPool.create();
    }
}
