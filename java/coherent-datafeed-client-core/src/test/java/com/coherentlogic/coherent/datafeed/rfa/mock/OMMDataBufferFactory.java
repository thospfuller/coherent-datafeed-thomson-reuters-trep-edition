package com.coherentlogic.coherent.datafeed.rfa.mock;

import static org.mockito.Mockito.when;

import com.reuters.rfa.omm.OMMDataBuffer;
import com.reuters.rfa.omm.OMMFieldEntry;
import com.reuters.rfa.omm.OMMTypes;

/**
 * Factory class for creating mock instances of {@link OMMDataBuffer}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMDataBufferFactory extends OMMDataFactory<OMMDataBuffer> {

    public OMMDataBufferFactory() {
        super(OMMDataBuffer.class);
    }

    public OMMDataBuffer getInstance (
        final OMMFieldEntry mockFieldEntry,
        final String value
    ) {
        OMMDataBuffer result = (OMMDataBuffer) getInstance(
            mockFieldEntry, OMMTypes.RMTES_STRING);

        when (result.getBytes()).thenReturn(value.getBytes());

        return result;
    }
}
