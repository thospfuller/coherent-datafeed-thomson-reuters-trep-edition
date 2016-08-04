package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.springframework.integration.transformer.AbstractPayloadTransformer;

import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * A transformer that returns the OMMMsg reference from the OMMItemEvent.
 *
 * @deprecated We are no longer converting messages to OMMMsg as we lose the
 *  handle, so this class has been deprecated as a result.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMMsgTransformer
    extends AbstractPayloadTransformer<OMMItemEvent, OMMMsg> {

    @Override
    protected OMMMsg transformPayload(OMMItemEvent payload) throws Exception {
        return payload.getMsg();
    }
}
