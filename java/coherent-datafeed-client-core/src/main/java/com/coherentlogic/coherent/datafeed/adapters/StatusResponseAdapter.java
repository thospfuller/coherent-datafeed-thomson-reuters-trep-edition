package com.coherentlogic.coherent.datafeed.adapters;

import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.reuters.rfa.omm.OMMState;

/**
 * An adapter that converts the OMMMsg into an instance of
 * {@link StatusResponse}.
 *
 * The OMMState is not like other OMMMsg objects so it will not be converted
 * using annotations.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class StatusResponseAdapter
    implements BasicAdapter<OMMState, StatusResponse> {

    @Override
    public StatusResponse adapt(OMMState ommState) {

        StatusResponse result = new StatusResponse ();

        short code = ommState.getCode();

        byte dataState = ommState.getDataState();

        byte streamState = ommState.getStreamState();

        String text = ommState.getText();

        result.setCode(code);
        result.setDataState(dataState);
        result.setStreamState(streamState);
        result.setText(text);

        return result;
    }
}
