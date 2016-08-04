package com.coherentlogic.coherent.datafeed.integration.transformers;

import java.util.Map;

import org.springframework.integration.transformer.AbstractPayloadTransformer;

import com.coherentlogic.coherent.datafeed.adapters.MarketPriceAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * 
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractMarketPriceTransformer
    extends AbstractPayloadTransformer<OMMItemEvent, MarketPrice> {

    private Map<Handle, MarketPrice> marketPriceMap;

    private final MarketPriceAdapter marketPriceAdapter;

    public AbstractMarketPriceTransformer(
        Map<Handle, MarketPrice> marketPriceMap,
        MarketPriceAdapter marketPriceAdapter
    ) {
        super();
        this.marketPriceMap = marketPriceMap;
        this.marketPriceAdapter = marketPriceAdapter;
    }

    protected Map<Handle, MarketPrice> getMarketPriceMap(){
        return marketPriceMap;
    }

    @Override
    protected MarketPrice transformPayload(OMMItemEvent itemEvent)
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

        MarketPrice marketPrice = process(itemEvent);

        marketPrice.setHandle(handle);

        return marketPrice;
    }

    public MarketPriceAdapter getMarketPriceAdapter(){
        return marketPriceAdapter;
    }

    protected abstract MarketPrice process(OMMItemEvent itemEvent);
}
