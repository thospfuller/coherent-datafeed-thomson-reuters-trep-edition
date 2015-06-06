package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.infinispan.Cache;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.beans.CachedMarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.reuters.rfa.common.Handle;

/**
 * Updates the market price information.
 *
 * @see ExampleUtil.java
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UpdateMarketByOrderTransformer
    extends UpdatableTransformer<CachedMarketByOrder, MarketByOrder> {

    public UpdateMarketByOrderTransformer(
        Cache<Handle, CachedMarketByOrder> cache,
        RFABeanAdapter<MarketByOrder> marketByOrderAdapter
    ) {
        super(cache, marketByOrderAdapter);
    }

//    /**
//     * An update requires us to clone the existing {@link MarketPrice} and then
//     * update the modified variables, set the marketPrice on the marketPriceMap,
//     * and then return this new marketPrice.
//     */
//    @Override
//    protected MarketByOrder process(OMMItemEvent itemEvent) {
//
//        Handle handle = itemEvent.getHandle();
//
//        log.debug("Update begins for the marketPrice with the handle " + handle);
//
//        OMMMsg ommMsg = itemEvent.getMsg();
//
//        RFABeanAdapter<MarketByOrder> marketPriceAdapter = getRFABeanAdapter();
//
//        Map<Handle, MarketByOrder> marketByOrderMap =
//            (Map<Handle, MarketByOrder>) getHandleToRFABeanMap();
//
//        MarketByOrder currentMarketPrice = marketByOrderMap.get(handle);
//
//        if (currentMarketPrice == null)
//            throw new NullPointerRuntimeException("The market price " +
//                "associated with the handle " + handle + " is null and hence " +
//                "cannot be updated.");
//        else
//            log.debug("Updating the following marketPrice: " +
//                currentMarketPrice);
//
//        MarketByOrder nextMarketPrice = (MarketByOrder)
//            SerializationUtils.clone(currentMarketPrice);
//
//        marketPriceAdapter.adapt(ommMsg, nextMarketPrice);
//
//        marketByOrderMap.put(handle, nextMarketPrice);
//
//        return nextMarketPrice;
//    }
}
