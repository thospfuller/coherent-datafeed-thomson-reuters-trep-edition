package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.AUTHENTICATION;
import static com.coherentlogic.coherent.datafeed.misc.Constants.CODE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.TEXT;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@Table(name=AUTHENTICATION)
@XStreamAlias(AUTHENTICATION)
public class Authentication extends RFABean {

    private static final long serialVersionUID = 6018039010564100716L;

    static final String STREAM_STATE_OPEN = "streamStateOpen",
        DATA_STATE_OK = "dataStateOK";

    @XStreamAlias(CODE)
    private short code = -199;
    
    @XStreamAlias(STREAM_STATE_OPEN)
    private boolean streamStateOpen;

    @XStreamAlias(DATA_STATE_OK)
    private boolean dataStateOK;

    @XStreamAlias(TEXT)
    private String text;

    public Authentication() {
    }

    public Authentication(
        short code,
        boolean streamStateOpen,
        boolean dataStateOK,
        String text
    ) {
        super();
        this.code = code;
        this.streamStateOpen = streamStateOpen;
        this.dataStateOK = dataStateOK;
        this.text = text;
    }

    public short getCode() {
        return code;
    }

    public Authentication setCode(short code) {

        short oldValue = this.code;

        if (oldValue != code) {

            this.code = code;

            firePropertyChange(CODE, oldValue, code);
        }
        return this;
    }

    public boolean isStreamStateOpen() {
        return streamStateOpen;
    }

    public Authentication setStreamStateOpen(boolean streamStateOpen) {

        boolean oldValue = this.streamStateOpen;

        if (oldValue != streamStateOpen) {

            this.streamStateOpen = streamStateOpen;

            firePropertyChange("streamStateOpen", oldValue, streamStateOpen);
        }
        return this;
    }

    public boolean isDataStateOK() {
        return dataStateOK;
    }

    public Authentication setDataStateOK(boolean dataStateOK) {

        boolean oldValue = this.dataStateOK;

        if (oldValue != dataStateOK) {

            this.dataStateOK = dataStateOK;

            firePropertyChange("dataStateOK", oldValue, dataStateOK);
        }
        return this;
    }

    public String getText() {
        return text;
    }

    public Authentication setText(String text) {

        String oldValue = this.text;

        if (oldValue == null || (oldValue != null && !oldValue.equals(text))) {

            this.text = text;

            firePropertyChange("text", oldValue, text);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Authentication [code=" + code + ", streamStateOpen="
            + streamStateOpen + ", dataStateOK=" + dataStateOK + ", text="
            + text + "]";
    }
}
