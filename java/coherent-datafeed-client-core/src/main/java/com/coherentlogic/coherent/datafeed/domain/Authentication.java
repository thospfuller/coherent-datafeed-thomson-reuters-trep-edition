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

    public void setCode(short code) {

        short oldValue = this.code;

        this.code = code;

        firePropertyChange(CODE, oldValue, code);
    }

    public boolean isStreamStateOpen() {
        return streamStateOpen;
    }

    public void setStreamStateOpen(boolean streamStateOpen) {

        boolean oldValue = this.streamStateOpen;

        this.streamStateOpen = streamStateOpen;

        firePropertyChange("streamStateOpen", oldValue, streamStateOpen);
    }

    public boolean isDataStateOK() {
        return dataStateOK;
    }

    public void setDataStateOK(boolean dataStateOK) {

        boolean oldValue = this.dataStateOK;

        this.dataStateOK = dataStateOK;

        firePropertyChange("dataStateOK", oldValue, dataStateOK);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {

        String oldValue = this.text;

        this.text = text;

        firePropertyChange("text", oldValue, text);
    }
}
