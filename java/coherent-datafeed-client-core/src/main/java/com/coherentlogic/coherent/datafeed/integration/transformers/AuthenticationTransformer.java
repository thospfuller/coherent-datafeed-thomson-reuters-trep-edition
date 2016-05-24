package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.transformer.AbstractPayloadTransformer;

import com.coherentlogic.coherent.datafeed.adapters.StatusResponseAdapter;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.reuters.rfa.omm.OMMMsg;

/**
 * Works for both refresh and updates.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 */
public class AuthenticationTransformer
    extends AbstractPayloadTransformer<OMMMsg, StatusResponse> {

    private static final Logger log =
        LoggerFactory.getLogger(AuthenticationTransformer.class);

    private final StatusResponseAdapter statusResponseAdapter;

    private final StatusResponse statusResponse;

    public AuthenticationTransformer(
        StatusResponseAdapter statusResponseAdapter,
        StatusResponse statusResponse
    ) {
        this.statusResponseAdapter = statusResponseAdapter;
        this.statusResponse = statusResponse;
    }

    @Override
    protected StatusResponse transformPayload(OMMMsg ommMsg) throws Exception {

        log.warn("transformPayload: method begins; ommMsg: " + ommMsg);

        statusResponseAdapter.adapt(ommMsg, statusResponse);

        log.warn("transformPayload: method ends; statusResponse: " +
            statusResponse);

        return statusResponse;
    }
}
