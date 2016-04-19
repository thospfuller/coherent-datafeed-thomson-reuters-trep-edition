package com.coherentlogic.coherent.datafeed.integration.transformers;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.integration.transformer.AbstractPayloadTransformer;

import com.coherentlogic.coherent.datafeed.exceptions.EventProcessingFailedException;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMSeries;

/**
 * Transforms an instance of {@link OMMMsg#getPayload()} into an instance of
 * {@link OMMSeries} and returns this result.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMSeriesTransformer
    extends AbstractPayloadTransformer<OMMMsg, OMMSeries> {

    public static final String BEAN_NAME = "ommSeriesTransformer";

    @Override
    public OMMSeries transformPayload(OMMMsg payload) throws Exception {

        OMMData data = payload.getPayload();

        if (!(data instanceof OMMSeries))
            throw new EventProcessingFailedException(
                "The payload was not an instance of OMMSeries; payload: " +
                ToStringBuilder.reflectionToString(payload));

        OMMSeries result = (OMMSeries) data;

        return result;
    }
}
