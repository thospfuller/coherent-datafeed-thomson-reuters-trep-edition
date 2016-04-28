package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.CODE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DATA_STATE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STATUS_RESPONSE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STREAM_STATE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.TEXT;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @TODO: Rename this class State.
 * 
 * @TODO: Unit test this class.
 * @TODO: Remove XStream.
 * @TODO: Refactor the conditional such that a single method (something like
 *  isDifferent(oldValue, newValue) is shared amongst all setters.
 * @TODO: Should we keep the original values and convert to text in a different
 *  method? (ie. getStreamStateText)
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=STATUS_RESPONSE)
@XStreamAlias(STATUS_RESPONSE)
public class StatusResponse extends RFABean {

    @XStreamAlias(CODE)
    private String code;

    @XStreamAlias(STREAM_STATE)
    private String streamState;

    @XStreamAlias(DATA_STATE)
    private String dataState;

    @XStreamAlias(TEXT)
    private String text;

    public StatusResponse() {
    }

    public StatusResponse(
        String code,
        String streamStateOpen,
        String dataStateOK,
        String text
    ) {
        super();
        this.code = code;
        this.streamState = streamStateOpen;
        this.dataState = dataStateOK;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {

        String oldValue = this.code;

        this.code = code;

        firePropertyChange(CODE, oldValue, code);
    }

    public StatusResponse withCode(String code) {

        setCode (code);

        return this;
    }

    public String getStreamState() {
        return streamState;
    }

    public void setStreamState(String streamState) {

        String oldValue = this.streamState;

        this.streamState = streamState;

        firePropertyChange(STREAM_STATE, oldValue, streamState);
    }

    public StatusResponse withStreamState(String streamState) {

        setStreamState(streamState);

        return this;
    }

    public String getDataState() {
        return dataState;
    }

    public void setDataState(String dataState) {

        String oldValue = this.dataState;

        this.dataState = dataState;

        firePropertyChange(DATA_STATE, oldValue, dataState);
    }

    public StatusResponse withDataState(String dataState) {

        setDataState(dataState);

        return this;
    }

    @UsingKey(type=TEXT)
    public String getText() {
        return text;
    }

    @RFAType(type=TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public StatusResponse setText(String text) {

        String oldValue = this.text;

        this.text = text;

        firePropertyChange(TEXT, oldValue, text);

        return this;
    }

    public StatusResponse withText(String text) {

        setText(text);

        return this;
    }
}
