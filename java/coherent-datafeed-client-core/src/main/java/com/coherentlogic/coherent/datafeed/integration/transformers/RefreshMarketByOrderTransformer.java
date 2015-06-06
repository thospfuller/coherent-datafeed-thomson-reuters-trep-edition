package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.infinispan.Cache;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.beans.CachedMarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.reuters.rfa.common.Handle;

/**
 * This transformer takes REFRESH_RESP events and transfers the market price
 * values to the domain model.
 *
 * Events of type REFRESH_RESP are sent initially from Thomson Reuters and
 * contain the initial snapshot of data pertaining to a specific RIC. Data
 * updates are then sent as UPDATE_RESP events, which are processed differently
 * since this is a subset of the initial snapshot.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RefreshMarketByOrderTransformer
    extends RefreshableTransformer<CachedMarketByOrder, MarketByOrder> {

//    private static final Logger log =
//        LoggerFactory.getLogger(RefreshMarketByOrderTransformer.class);

    public RefreshMarketByOrderTransformer(
        Cache<Handle, CachedMarketByOrder> cache,
        RFABeanAdapter<MarketByOrder> rfaBeanAdapter
    ) {
        super(cache, rfaBeanAdapter);
    }

//    /**
//     * Refresh events require that all variables be copied into a new instance
//     * of {@link MarketByOrder} and then the marketByOrder will be set on the
//     * handleToMarketByOrderMap.
//     *
//     * @param attribInfo
//     * @param data
//     */
//    @Override
//    protected MarketByOrder process(OMMItemEvent itemEvent) {
//
//        Handle handle = itemEvent.getHandle();
//
//        log.debug("Refresh begins for the marketByOrder with the handle " +
//            handle);
//
//        OMMMsg ommMsg = itemEvent.getMsg();
//
//        RFABeanAdapter<MarketByOrder> marketByOrderAdapter =
//            getRFABeanAdapter();
//
//        MarketByOrder marketByOrder =
//            marketByOrderAdapter.adapt(ommMsg);
//
//        Map<Handle, MarketByOrder> handleToMarketByOrderMap =
//            (Map<Handle, MarketByOrder>) getHandleToRFABeanMap();
//
//        handleToMarketByOrderMap.put(handle, marketByOrder);
//
//        return marketByOrder;
//    }
}
