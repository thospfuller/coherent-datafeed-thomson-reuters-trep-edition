package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.springframework.integration.transformer.AbstractPayloadTransformer;

import com.reuters.rfa.omm.OMMMap;
import com.reuters.rfa.omm.OMMMsg;

/**
 * A transformer that casts the OMMMsg to an OMMMap and returns the result.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class OMMMapTransformer
    extends AbstractPayloadTransformer<OMMMsg, OMMMap> {

    @Override
    protected OMMMap transformPayload(OMMMsg msg) throws Exception {
        return (OMMMap) msg.getPayload();
    }
}
