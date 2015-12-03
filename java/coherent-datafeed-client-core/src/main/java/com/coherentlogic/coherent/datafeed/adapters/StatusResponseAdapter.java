package com.coherentlogic.coherent.datafeed.adapters;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMFieldEntryAdapter;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMState;
import com.reuters.rfa.omm.OMMState.Stream;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 */
public class StatusResponseAdapter
    extends RFABeanAdapter<StatusResponse> {

    private static final Logger log =
        LoggerFactory.getLogger(StatusResponseAdapter.class);

    public StatusResponseAdapter(
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
        OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters
    ) throws SecurityException, NoSuchMethodException {
        super(
            null,
            fieldDictionary,
            fieldEntryAdapters,
            new HashMap<String, Method> (),
            StatusResponse.class
        );
    }

    @Override
    public StatusResponse adapt(OMMMsg msg) {

        log.info("adapt: method begins; msg: " + msg);

        StatusResponse statusResponse = new StatusResponse ();

        adapt(msg, statusResponse);

        if (msg.has(OMMMsg.HAS_STATE)) {

            OMMState state = msg.getState();

            short codeShort = state.getCode();

            String code = OMMState.Code.toString(codeShort);

            byte dataStateByte = state.getDataState();

            String dataState = OMMState.Data.toString(dataStateByte);

            byte streamStateByte = state.getStreamState();

            String streamState = OMMState.Stream.toString(streamStateByte);

            String text = state.getText();

            statusResponse
                .withCode(code)
                .withDataState(dataState)
                .withStreamState(streamState)
                .withText(text);
        }

        log.info("adapt: method ends; statusResponse: " + statusResponse);

        return statusResponse;
    }
}
