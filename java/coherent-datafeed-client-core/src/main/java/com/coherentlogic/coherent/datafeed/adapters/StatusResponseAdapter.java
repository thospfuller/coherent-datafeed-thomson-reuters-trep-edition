package com.coherentlogic.coherent.datafeed.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.adapters.InOutAdapterSpecification;
import com.coherentlogic.coherent.data.model.core.adapters.InReturnAdapterSpecification;
import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeEvent.UpdateType;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMState;

/**
 * 
 * INFO: statusResponseMessageProcessor.process: method ends; result: GenericMessage [
 * payload=StatusResponse [code=NOT_FOUND, streamState=CLOSED, dataState=SUSPECT, text=The record could not be found],
 * headers={history=onMarketMakerAddSessionToHeadersChannel,routeByMarketMakerEventTypeChannel,routeByMarketMakerMsgTypeChannel,processMarketMakerChannel,processStatusResponseChannel, id=47245f6b-1a02-b151-50cb-5d8fcc363dbb, timestamp=1464020539595}]
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 */
public class StatusResponseAdapter
    implements InOutAdapterSpecification<OMMMsg, StatusResponse>, InReturnAdapterSpecification<OMMMsg, StatusResponse> {

    private static final Logger log =
        LoggerFactory.getLogger(StatusResponseAdapter.class);

    private final TypedFactory<StatusResponse> statusResponseFactory;

    public StatusResponseAdapter(TypedFactory<StatusResponse> statusResponseFactory)
        throws SecurityException, NoSuchMethodException {
        this.statusResponseFactory = statusResponseFactory;
    }

    @Override
    public StatusResponse adapt(OMMMsg msg) {

        log.debug("adapt: method begins; msg: " + msg);

        StatusResponse statusResponse = statusResponseFactory.getInstance();

        adapt(msg, statusResponse);

        log.debug("adapt: method ends; statusResponse: " + statusResponse);

        return statusResponse;
    }

    @Override
    public void adapt(OMMMsg msg, StatusResponse statusResponse) {

        log.debug("adapt: method begins; msg: " + msg + ", statusResponse: " + statusResponse);

        // See Issue #25 -- the update type needs to be set properly.
        AggregatePropertyChangeCollector<StatusResponse> collector
            = new AggregatePropertyChangeCollector<StatusResponse> (statusResponse, UpdateType.full);

        if (msg.has(OMMMsg.HAS_STATE)) {

            OMMState state = msg.getState();

            short codeShort = state.getCode();

            String code = OMMState.Code.toString(codeShort);

            byte dataStateByte = state.getDataState();

            String dataState = OMMState.Data.toString(dataStateByte);

            byte streamStateByte = state.getStreamState();

            String streamState = OMMState.Stream.toString(streamStateByte);

            String text = state.getText();

            statusResponse.setCode(code);
            statusResponse.setDataState(dataState);
            statusResponse.setStreamState(streamState);
            statusResponse.setText(text);
        }

        collector.done();

        log.debug("adapt: method ends; statusResponse: " + statusResponse);
    }
}
