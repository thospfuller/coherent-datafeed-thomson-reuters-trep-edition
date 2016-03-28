package com.coherentlogic.coherent.datafeed.integration.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMState;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * LoginClient: Received Login STATUS OK Response
 * MESSAGE
 *     Msg Type: MsgType.STATUS_RESP
 *     Msg Model Type: LOGIN
 *     Indication Flags: 
 *     Hint Flags: HAS_ATTRIB_INFO | HAS_STATE
 *     State: OPEN, OK, NONE,  "Login accepted by host stlimsp2pse."
 *     AttribInfo
 *         Name: CoherentLogic_Fuller
 *         NameType: 1 (USER_NAME)
 *         Attrib
 *             ELEMENT_LIST
 *                 ELEMENT_ENTRY ApplicationId: 256
 *                 ELEMENT_ENTRY ApplicationName: P2PS
 *                 ELEMENT_ENTRY Position: 192.168.10.2/Godzilla
 *                 ELEMENT_ENTRY ProvidePermissionExpressions: 1
 *                 ELEMENT_ENTRY ProvidePermissionProfile: 0
 *                 ELEMENT_ENTRY AllowSuspectData: 1
 *                 ELEMENT_ENTRY SingleOpen: 1
 *                 ELEMENT_ENTRY SupportBatchRequests: 1
 *                 ELEMENT_ENTRY SupportOptimizedPauseResume: 1
 *                 ELEMENT_ENTRY SupportPauseResume: 1
 *                 ELEMENT_ENTRY SupportViewRequests: 1
 *                 ELEMENT_ENTRY SupportOMMPost: 1
 *     Payload: None
 * Consumer Login successful...
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 */
public class StatusInterpreter {

    private static final Logger log =
        LoggerFactory.getLogger(StatusInterpreter.class);

    public static final String BEAN_NAME = "statusInterpreter";

    /**
     * Determine if we can route via the ommMsg characteristics.
     *
     * For example:
     *
     * code: 0 (NONE), dataState: 2 (SUSPECT), streamState: 1 (OPEN)
     */
    public void onStatusResponseReceived (Message<OMMItemEvent> message) {

        log.info("onStatusResponseReceived: method begins!");

        OMMItemEvent payload = message.getPayload();

        OMMMsg ommMsg = payload.getMsg();

        if (ommMsg.has(OMMMsg.HAS_STATE)) {

            OMMState state = ommMsg.getState();

            short code = state.getCode();
            byte dataState = state.getDataState ();
            byte streamState = state.getStreamState();

            log.info("code: " + code + ", dataState: " + dataState +
                ", streamState: " + streamState + ", text: " + state.getText());

            if (
                code == OMMState.Code.NONE
                &&
                dataState == OMMState.Data.OK
                &&
                streamState == OMMState.Stream.OPEN) {
                log.info("Operation succeeded; text: " + state.getText());
            } else if (
                code == OMMState.Code.UNSUPPORTED_VIEW_TYPE
                &&
                dataState == OMMState.Data.SUSPECT
                &&
                streamState == OMMState.Stream.CLOSED
            ) {
                log.warn("Operation failed -- reason: " +
                    state.getText());
            }
        }
    }
}
