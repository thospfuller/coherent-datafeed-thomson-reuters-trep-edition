package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.infinispan.Cache;
import org.springframework.integration.transformer.AbstractPayloadTransformer;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.beans.CachedEntry;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Note that this transformer is transactional.
 *
 * @param <T> The type of CachedEntry.
 * @param <O> the type of object contained in the CachedEntry.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
//@Transactional
public abstract class AbstractRFABeanTransformer
    <C extends CachedEntry, R extends RFABean>
        extends AbstractPayloadTransformer<OMMItemEvent, R> {

    private Cache<Handle, C> cache;

    private final RFABeanAdapter<R> rfaBeanAdapter;

    public AbstractRFABeanTransformer(
        Cache<Handle, C> cache,
        RFABeanAdapter<R> rfaBeanAdapter
    ) {
        super();
        this.cache = cache;
        this.rfaBeanAdapter = rfaBeanAdapter;
    }

    protected Cache<Handle, C> getCache() {
        return cache;
    }

    @Override
    protected R transformPayload(OMMItemEvent itemEvent)
        throws Exception {

        /* 1.) Get msgType, may want to route via this, instead of determining
         *     this here.
         * 2.) If refresh, then create the domain object associated with the
         *     symbol set all properties on it, and set this instance on a map
         *     which is used to manage the link between the symbol and the
         *     domain object.
         * 3.) If update, get the current domain object associated with the
         *     symbol, clone it, update the corresponding properties, and then
         *     set the current domain object to this one.
         */

        Handle handle = itemEvent.getHandle();

        R result = process (itemEvent);

//        result.setHandle(handle);

        return result;
    }

    public RFABeanAdapter<R> getRFABeanAdapter() {
        return rfaBeanAdapter;
    }

    protected abstract R process (OMMItemEvent itemEvent);
}
