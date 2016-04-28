package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMPool;

/**
 * Factory class for creating instances of {@link com.reuters.rfa.omm.OMMEncoder
 * OMMEncoder}
 *
 * @todo This may need to be converted into a Spring bean with a custom
 * namespace.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class OMMEncoderFactory implements TypedFactory<OMMEncoder> {

    public static final String BEAN_NAME = "encoderFactory";

    private final OMMPool pool;

    private final short msgType;

    private final int size;

    /**
     * Constructor which sets the pool and msgType to the params passed in. If
     * this constructor is used to instantiate this class, then the developer
     * <i>must</i> invoke the <i>getInstance</i> method that takes the size.
     *
     * Note the size defaults to zero.
     *
     * @param pool
     * @param msgType A short which will be cast to a byte.
     */
    public OMMEncoderFactory(OMMPool pool, short msgType) {
        this(pool, msgType, 0);
    }

    public OMMEncoderFactory(OMMPool pool, short msgType, int size) {
        this.pool = pool;
        this.msgType = msgType;
        this.size = size;
    }

    @Override
    public OMMEncoder getInstance() {
        return getInstance (size);
    }

    public OMMEncoder getInstance(int size) {

        OMMEncoder result = pool.acquireEncoder();

        result.initialize(msgType, size);

        return result;
    }
}
