package com.coherentlogic.coherent.datafeed.integration.transformers;

import java.util.Map;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.beans.CachedObject;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * This transformer takes REFRESH_RESP events and transfers the market price
 * values to the domain model.
 *
 * Events of type REFRESH_RESP are sent initially from Thomson Reuters and
 * contain the initial snapshot of data pertaining to a specific RIC. Data
 * updates are then sent as UPDATE_RESP events, which are processed differently
 * since this is a subset of the initial snapshot.
 *
 * @deprecated After refactoring this project we no longer need this (has been
 *  replaced by a message processor).
 *
 * @deprecated Classed that extend from CachedObject are no longer used.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RefreshableTransformer
    <C extends CachedObject<R>, R extends RFABean>
    extends AbstractRFABeanTransformer<C, R> {

    private static final Logger log =
        LoggerFactory.getLogger(RefreshableTransformer.class);

    public RefreshableTransformer(
        Cache<Handle, C> cache,
        RFABeanAdapter<R> adapter
    ) {
        super(cache, adapter);
    }

    /**
     * Refresh events require that all variables be copied into a new instance
     * of {@link MarketPrice} and then the marketPrice be set on the
     * marketPriceMap.
     *
     * @param attribInfo
     * @param data
     */
    @Override
    protected R process(OMMItemEvent itemEvent) {

        Handle handle = itemEvent.getHandle();

        log.debug("Refresh begins for the RFABean with the handle " + handle);

        OMMMsg ommMsg = itemEvent.getMsg();

        RFABeanAdapter<R> adapter = getRFABeanAdapter();

        R result =
            adapter.adapt(ommMsg);

        Map<Handle, C> cache =
            (Map<Handle, C>) getCache();

        C cachedObject = cache.get(handle); // If this is null then we may need to create the CO.

        cachedObject.setObject(result);

        cache.put(handle, cachedObject);

        return result;
    }
}
