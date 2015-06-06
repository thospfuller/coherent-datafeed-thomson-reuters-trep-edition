package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.CODE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DATA_STATE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STATUS_RESPONSE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STREAM_STATE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.TEXT;

import javax.persistence.Entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * A bean that represents the response sent from Thomson Reuters regarding the
 * state of the request for information and/or the login.
 *
 * @todo This class originally extended from OldDefaultObject however while working
 *  with the json adapters we had to make this extend from RFABean. We need to
 *  return to using the DefObj as this makes more sense.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Entity
@XStreamAlias(STATUS_RESPONSE)
public class StatusResponse extends RFABean {

    private static final long serialVersionUID = 1L;

    @XStreamAlias(CODE)
    private Short code;

    @XStreamAlias(DATA_STATE)
    private Byte dataState;

    @XStreamAlias(STREAM_STATE)
    private Byte streamState;

    @XStreamAlias(TEXT)
    private String text;

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public Byte getDataState() {
        return dataState;
    }

    public void setDataState(Byte dataState) {
        this.dataState = dataState;
    }

    public Byte getStreamState() {
        return streamState;
    }

    public void setStreamState(Byte streamState) {
        this.streamState = streamState;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}