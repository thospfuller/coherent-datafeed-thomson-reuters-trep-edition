package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.CODE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DATA_STATE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STATUS_RESPONSE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STREAM_STATE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.TEXT;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coherentlogic.coherent.data.model.core.annotations.Changeable;
import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;
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
public class StatusResponse extends SerializableBean {

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

    /**
     * Resets all properties to null and does not result in any of the fire methods being called.
     */
    public void reset () {
        this.code = null;
        this.streamState = null;
        this.dataState = null;
        this.text = null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(@Changeable(CODE) String code) {
        this.code = code;
    }

    public String getStreamState() {
        return streamState;
    }

    public void setStreamState(@Changeable(STREAM_STATE) String streamState) {
        this.streamState = streamState;
    }

    public String getDataState() {
        return dataState;
    }

    public void setDataState(@Changeable(DATA_STATE) String dataState) {
        this.dataState = dataState;
    }

    @UsingKey(type=TEXT)
    public String getText() {
        return text;
    }

    @RFAType(type=TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setText(@Changeable(TEXT) String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "StatusResponse [code=" + code + ", streamState=" + streamState + ", dataState=" + dataState + ", text="
            + text + "]";
    }
}
