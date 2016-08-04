package com.coherentlogic.coherent.datafeed.exceptions;


/**
 * An exception that is thrown when authentication fails; note this exception
 * also includes properties that can be used to determine what the problem was.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AuthenticationFailedException extends FatalRuntimeException {

    private static final long serialVersionUID = 5475373426827953334L;

    private final boolean hasStatusResp;

    private final boolean hasState;

    private final byte streamState;

    private final byte dataState;

    public AuthenticationFailedException(
        String msg,
        boolean hasStatusResp,
        boolean hasState,
        byte streamState,
        byte dataState
    ) {
        super(msg);
        this.hasStatusResp = hasStatusResp;
        this.hasState = hasState;
        this.streamState = streamState;
        this.dataState = dataState;
    }

    public boolean isHasStatusResp() {
        return hasStatusResp;
    }

    public boolean isHasState() {
        return hasState;
    }

    public byte getStreamState() {
        return streamState;
    }

    public byte getDataState() {
        return dataState;
    }
}
