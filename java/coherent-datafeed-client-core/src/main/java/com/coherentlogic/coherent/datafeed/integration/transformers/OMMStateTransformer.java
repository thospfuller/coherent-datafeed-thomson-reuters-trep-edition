package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.springframework.integration.transformer.AbstractPayloadTransformer;

import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMState;

/**
 * A transformer that extracts the {@link OMMState} from the payload.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMStateTransformer
    extends AbstractPayloadTransformer<OMMMsg, OMMState> {

    public static final String BEAN_NAME = "ommStateTransformer";

    @Override
    protected OMMState transformPayload(OMMMsg payload) throws Exception {

        OMMState state = payload.getState();

        return state;
    }
}
