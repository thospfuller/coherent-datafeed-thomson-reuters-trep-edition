package com.coherentlogic.coherent.datafeed.adapters.omm;

import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMDataBuffer;
import com.reuters.rfa.omm.OMMFieldEntry;

/**
 * An adapter for {@link OMMDataBuffer} objects (text/string data).
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMDataBufferAdapter extends OMMFieldEntryAdapter<OMMDataBuffer> {

    public static final String BEAN_NAME = "ommDataBufferAdapter";

    public OMMDataBufferAdapter(FieldDictionary fieldDictionary,
            OMMDataAdapter dataAdapter) {
        super(fieldDictionary, dataAdapter);
    }

    @Override
    public OMMDataBuffer adapt(OMMFieldEntry fieldEntry) {

        OMMData data = dataAdapter.adapt(fieldEntry);

        OMMDataBuffer dataBuffer = (OMMDataBuffer) data;

        return dataBuffer;
    }

    @Override
    public <X> X adapt(OMMFieldEntry fieldEntry, Class<X> type) {

        OMMDataBuffer dataBuffer = adapt(fieldEntry);

        byte[] bytes = dataBuffer.getBytes();

        Object result = null;

        if (byte[].class.equals(type))
            result = bytes;
        else if (String.class.equals(type))
            result = new String (dataBuffer.getBytes());

        return type.cast(result);
    }
}
