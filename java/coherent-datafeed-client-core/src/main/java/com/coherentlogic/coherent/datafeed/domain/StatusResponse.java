package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.CODE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DATA_STATE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STATUS_RESPONSE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STREAM_STATE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.TEXT;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
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

//    @UsingKey(type=CODE)
    public String getCode() {
        return code;
    }

//    @RFAType(type=CODE)
//    @Adapt(using=OMMNumericAdapter.class)
    public void setCode(String code) {

        String oldValue = this.code;

        if (oldValue == null || (oldValue != null && !oldValue.equals(code))) {

            this.code = code;

            firePropertyChange(CODE, oldValue, code);
        }
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

        if (oldValue == null || (oldValue != null && !oldValue.equals(streamState))) {

            this.streamState = streamState;

            firePropertyChange("streamState", oldValue, streamState);
        }
    }

    public StatusResponse withStreamState(String streamState) {

        setStreamState(streamState);

        return this;
    }

//    @UsingKey(type=DATA_STATE_OK)
//    public boolean isDataStateOK() {
//        return dataState;
//    }

    public String getDataState() {
        return dataState;
    }

    //    @RFAType(type=DATA_STATE_OK)
    public void setDataState(String dataState) {

        String oldValue = this.dataState;

        if (oldValue == null || (oldValue != null && !oldValue.equals(dataState))) {

            this.dataState = dataState;

            firePropertyChange("dataState", oldValue, dataState);
        }
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

        if (oldValue == null || (oldValue != null && !oldValue.equals(text))) {

            this.text = text;

            firePropertyChange("text", oldValue, text);
        }
        return this;
    }

    public StatusResponse withText(String text) {

        setText(text);

        return this;
    }

    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder ();

        AttribInfo attribInfo = getAttribInfo();

        if (attribInfo != null) {

            builder.append(attribInfo.toString() + "\n");

            Map<String, String> elements = attribInfo.getElements();

            elements.forEach(
                (String key, String value) -> {
                    builder.append(key + ": ").append(value).append ("\n");
                }
            );
        }

        return "StatusResponse [code=" + code + ", streamState="
            + streamState + ", dataState=" + dataState + ", text="
            + text + "]\n" + builder.toString();
    }
}
