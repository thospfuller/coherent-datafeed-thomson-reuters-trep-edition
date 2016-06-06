package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.common.Handle;

public class RefreshSessionBeanMessageProcessor extends AbstractRefreshRFABeanMessageProcessor<SessionBean> {

    public RefreshSessionBeanMessageProcessor(
        RFABeanAdapter<SessionBean> objectAdapter,
        Map<Handle, String> ricCache,
        Map<String, SessionBean> objectCache
    ) {
        super(objectAdapter, ricCache, objectCache);
    }
}
