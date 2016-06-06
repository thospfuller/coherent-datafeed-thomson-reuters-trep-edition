package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.common.Handle;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UpdateSessionBeanMessageProcessor extends AbstractUpdateRFABeanMessageProcessor<SessionBean> {

    public UpdateSessionBeanMessageProcessor(
        RFABeanAdapter<SessionBean> objectAdapter,
        Map<Handle, String> ricCache,
        Map<String, SessionBean> objectCache
    ) {
        super(objectAdapter, ricCache, objectCache);
    }
}
