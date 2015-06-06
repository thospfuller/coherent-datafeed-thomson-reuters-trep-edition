package com.coherentlogic.coherent.datafeed.integration.routers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.router.AbstractMessageRouter;

import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMState;

/**
 * Do we need login closed?
 *
 * @todo Either deprecate this, or rename it to CheckLoginSuccessOrFailRouter
 *  or something like this, as we need to check the message to determine if the
 *  login succeeded.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class OMMMsgRouter extends AbstractMessageRouter {

    private final MessageChannel
        loginAcceptedMessageChannel,
        loginRejectedMessageChannel,
        //loginClosedMessageChannel,
        refreshCompleteMessageChannel;

    /**
     * 
     * @param loginAcceptedMessageChannel
     * @param loginRejectedMessageChannel
     * @param loginClosedMessageChannel -- need to determine if this represents
     *  a msg with the final flag set to true -- if so, then remove.
     * @param refreshCompleteMessageChannel
     */
    public OMMMsgRouter(
        MessageChannel loginAcceptedMessageChannel,
        MessageChannel loginRejectedMessageChannel,
//        MessageChannel loginClosedMessageChannel,
        MessageChannel refreshCompleteMessageChannel) {
        super();
        this.loginAcceptedMessageChannel = loginAcceptedMessageChannel;
        this.loginRejectedMessageChannel = loginRejectedMessageChannel;
//        this.loginClosedMessageChannel = loginClosedMessageChannel;
        this.refreshCompleteMessageChannel = refreshCompleteMessageChannel;
    }

    @Override
    protected Collection<MessageChannel> determineTargetChannels(
        Message<?> message) {

        OMMMsg msg = (OMMMsg) message.getPayload();

        List<MessageChannel> results = new ArrayList<MessageChannel> ();

        if (isLoginAccepted(msg))
            results.add(loginAcceptedMessageChannel);
        else if (isRefreshComplete(msg))
            // We can probably use a null channel here as the workflow will just
            // end anyway.
            results.add (refreshCompleteMessageChannel);
        else
            results.add(loginRejectedMessageChannel);

        return results;
    }

    boolean isLoginAccepted (OMMMsg msg) {
        return
            msg.getMsgType() == OMMMsg.MsgType.STATUS_RESP
            &&
            msg.has(OMMMsg.HAS_STATE)
            &&
            msg.getState().getStreamState() == OMMState.Stream.OPEN
            &&
            msg.getState().getDataState() == OMMState.Data.OK;
    }

    /**
     * The client sends a login request to RFA and immediately a REFRESH_RESP is
     * returned and the indication flag will be set to REFRESH_COMPLETE.
     *
     * @param msg
     * @return
     *
     * @see Refer to Chapter 3 page 11 of the Reuters Domain Model.
     */
    boolean isRefreshComplete(OMMMsg msg) {

        boolean refreshComplete = msg.isSet(OMMMsg.Indication.REFRESH_COMPLETE);

        return msg.getMsgType() == OMMMsg.MsgType.REFRESH_RESP
            && refreshComplete;
    }

    /**
     * 
     */
    boolean isRefreshResponse (OMMMsg msg) {
        return msg.getMsgType() == OMMMsg.MsgType.REFRESH_RESP;
    }
//
//    boolean isLoginRejected (OMMMsg msg) {
//        // Set an error on the login bean or throw an exception.
//    }
//
//    boolean isLoginClosed (OMMMsg msg) {
//        // You must login again.
//    }
}
