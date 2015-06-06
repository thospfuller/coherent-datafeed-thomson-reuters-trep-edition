package com.coherentlogic.coherent.datafeed.builders;

import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMTypes;

/**
 * Builder class for creating instances of {@link OMMEncoder}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <T> The type of class returned from methods so as to facilitate
 *  method-chaining by subclasses.
 */
public class OMMEncoderBuilder<T> {

    private final OMMEncoder encoder;

    public OMMEncoderBuilder (OMMEncoder encoder) {
        this.encoder = encoder;
    }

    public OMMEncoder getOMMEncoder () {
        return encoder;
    }

    public T encodeMsgInit (
        OMMMsg ommMsg,
        short attribDataType,
        short dataType
    ) {
        encoder.encodeMsgInit(ommMsg, attribDataType, dataType);
        return (T) this;
    }

    public T encodeElementListInit (
        int flags,
        short elementListNumber,
        short dataDefId
    ) {
        encoder.encodeElementListInit(flags, elementListNumber, dataDefId);
        return (T) this;
    }

    public T encodeElementEntryInit (
        String elementName,
        short dataType
    ) {
        encoder.encodeElementEntryInit(elementName, dataType);
        return (T) this;
    }

    public T encodeString (String string, short dataType) {
        encoder.encodeString(string, dataType);
        return (T) this;
    }

    public T encodeAsASCIIString (String string) {
        encoder.encodeString(string, OMMTypes.ASCII_STRING);
        return (T) this;
    }

    public T encodeUInt (long value) {
        encoder.encodeUInt(value);
        return (T) this;
    }

    public T encodeAggregateComplete () {
        encoder.encodeAggregateComplete();
        return (T) this;
    }

    public OMMMsg getEncodedObject () {
        return (OMMMsg) encoder.getEncodedObject();
    }
}
