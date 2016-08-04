package com.coherentlogic.coherent.datafeed.integration.transformers;

import java.util.Map;

import org.springframework.integration.transformer.AbstractPayloadTransformer;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * 
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractMarketByOrderTransformer <T extends RFABean>
    extends AbstractPayloadTransformer<OMMItemEvent, RFABean> {

    private Map<Handle, ? extends RFABean> handleToRFABeanMap;

    private final RFABeanAdapter<MarketByOrder> rfaBeanAdapter;

    public AbstractMarketByOrderTransformer(
        Map<Handle, ? extends RFABean> handleToRFABeanMap,
        RFABeanAdapter<MarketByOrder> rfaBeanAdapter
    ) {
        super();
        this.handleToRFABeanMap = handleToRFABeanMap;
        this.rfaBeanAdapter = rfaBeanAdapter;
    }

    protected Map<Handle, ? extends RFABean> getHandleToRFABeanMap() {
        return handleToRFABeanMap;
    }

    @Override
    protected RFABean transformPayload(OMMItemEvent itemEvent)
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

        RFABean rfaBean = process(itemEvent);

        rfaBean.setHandle(handle);

        return rfaBean;
    }

    public RFABeanAdapter<MarketByOrder> getRFABeanAdapter(){
        return rfaBeanAdapter;
    }

    protected abstract T process(OMMItemEvent itemEvent);
}
