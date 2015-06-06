package com.coherentlogic.coherent.datafeed.misc;

import static com.reuters.rfa.omm.OMMState.Data.OK;
import static com.reuters.rfa.omm.OMMState.Stream.OPEN;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.reuters.rfa.internal.rwf.RwfMsgOverride;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMState;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * This is a hack solution for what appears to be the following problem:
 *
 * See: https://jira.springsource.org/browse/SPR-7831
 *
 * This expression works most of the time however when we get a message of type
 * {@link RwfMsgOverride} the following exception is thrown:
 * 
 * Method call: Method has(java.lang.Integer) cannot be found on
 * com.reuters.rfa.internal.rwf.RwfMsgOverride type
 * 
 * however this method is available on the child class.
 *
 * payload.has(#{T(com.reuters.rfa.omm.OMMMsg).HAS_STATE})
 *
 *
 * if ((respMsg.getMsgType() == OMMMsg.MsgType.STATUS_RESP) && (respMsg.has(OMMMsg.HAS_STATE))
 *     && (respMsg.getState().getStreamState() == OMMState.Stream.OPEN)
 *     && (respMsg.getState().getDataState() == OMMState.Data.OK))
 * {
 *     ...
 * }
 *
 * @see LoginClient.java in the RFA examples.
 *
 * @deprecated Move the loginSucceeded method to the authenticationService and
 *  then delete this class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AuthenticationHelper {

    private static final Logger log =
        LoggerFactory.getLogger(AuthenticationHelper.class);

    public boolean loginSucceeded (OMMItemEvent itemEvent) {

        OMMMsg msg = itemEvent.getMsg();

        return loginSucceeded(msg);
    }

    /**
     * Method reviews the state of the OMMMsg and returns false if the ommMsg
     * has failed.
     *
     * Possible NPE below however this method should never be called with a null
     * ommMsg.
     */
    public boolean loginSucceeded (OMMMsg ommMsg) {

        /* We originally wanted to throw an exception and have this handled by
         * the exception-type-router handle this appropriately. We ran into
         * trouble, however, because when we throw an exception here, it results
         * in other exceptions being thrown, and hence we end up receiving an
         * AggregateMessageDeliveryException, which is not what we want.
         */

        log.info ("loginSucceeded: method begins; ommMsg: " + ommMsg +
            ", ommMsg.isFinal: " + ommMsg.isFinal());

        byte msgType = ommMsg.getMsgType();

        boolean hasStatusResp = (msgType == OMMMsg.MsgType.STATUS_RESP);

        boolean hasState = ommMsg.has(OMMMsg.HAS_STATE);

        OMMState state = ommMsg.getState();

        byte streamState = state.getStreamState();

        byte dataState = state.getDataState();

        boolean streamStateOpen = (streamState == OPEN);

        boolean dataStateOK = (dataState == OK);

        boolean loginSuccessful =
            hasStatusResp
            &&
            hasState
            &&
            streamStateOpen
            &&
            dataStateOK;

        log.info("loginSucceeded: method ends; loginSuccessful: " +
            loginSuccessful +
            ", hasStatusResp: " + hasStatusResp +
            ", hasState: " + hasState +
            ", streamStateOpen: " + streamStateOpen +
            ", dataStateOK: " + dataStateOK
        );

        return loginSuccessful;
    }

    /**
     * Method that is called when authentication has failed; this method simply
     * logs a message at warning level and nothing more.
     */
    public void loginFailed (OMMMsg ommMsg) {

        byte msgType = ommMsg.getMsgType();

        boolean hasStatusResp = (msgType == OMMMsg.MsgType.STATUS_RESP);

        boolean hasState = ommMsg.has(OMMMsg.HAS_STATE);

        OMMState state = ommMsg.getState();

        byte streamState = state.getStreamState();

        byte dataState = state.getDataState();

        log.warn("Authentication has failed -- please check your username and " +
            "try again (ommMsg: " + ommMsg + ", hasStatusResp: " +
                hasStatusResp + ", hasState: " + hasState + ", streamState: " +
                streamState + ", dataState: " + dataState + ").");

//        throw new AuthenticationFailedException (
//            "AuthenticationServiceSpecification has failed -- please check your username and " +
//            "try again ommMsg: " + ommMsg + ".",
//            hasStatusResp,
//            hasState,
//            streamState,
//            dataState
//        );
    }
}
