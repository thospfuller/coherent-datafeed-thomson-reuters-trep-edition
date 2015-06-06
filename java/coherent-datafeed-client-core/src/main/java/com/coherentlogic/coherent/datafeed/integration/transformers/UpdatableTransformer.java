package com.coherentlogic.coherent.datafeed.integration.transformers;

import java.util.Map;

import org.apache.commons.lang.SerializationUtils;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.beans.CachedObject;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Updates the market price information.
 *
 * @see ExampleUtil.java
 *
 * @deprecated Classed that extend from CachedObject are no longer used.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UpdatableTransformer
    <C extends CachedObject<R>, R extends RFABean>
        extends AbstractRFABeanTransformer<C, R> {

    private static final Logger log =
        LoggerFactory.getLogger(UpdatableTransformer.class);

    public UpdatableTransformer(
        Cache<Handle, C> cache,
        RFABeanAdapter<R> rfaBeanAdapter
    ) {
        super(cache, rfaBeanAdapter);
    }

    /**
     * An update requires us to clone the existing {@link MarketPrice} and then
     * update the modified variables, set the marketPrice on the marketPriceMap,
     * and then return this new marketPrice.
     */
    @Override
    protected R process(OMMItemEvent itemEvent) {

        Handle handle = itemEvent.getHandle();

        log.debug("Update begins for the RFABean with the handle " +
            handle);

        OMMMsg ommMsg = itemEvent.getMsg();

        RFABeanAdapter<R> rfaBeanAdapter =
            getRFABeanAdapter();

        Map<Handle, C> cache = getCache();

        C currentCachedObject = cache.get(handle);

        if (currentCachedObject == null)
            throw new NullPointerRuntimeException("The cached object " +
                "associated with the handle " + handle + " is null and hence " +
                "the system is in an invalid state as this should never " +
                "happen.");
        else
            log.debug("Updating the following rfaBean: " +
                currentCachedObject);

        R currentRFABean = (R) currentCachedObject.getObject();

        R nextRFABean = (R)
            SerializationUtils.clone(currentRFABean);

        rfaBeanAdapter.adapt(ommMsg, nextRFABean);

        currentCachedObject.setObject(nextRFABean);

        cache.put(handle, currentCachedObject);

        return nextRFABean;
    }
}
