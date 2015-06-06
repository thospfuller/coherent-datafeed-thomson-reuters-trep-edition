package com.coherentlogic.coherent.datafeed.rfa.mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyShort;

import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMFieldEntry;

/**
 * Factory class for creating mock instances of {@link OMMData}.
 *
 * @TODO Should this be a factory class, or something else? We're not using the
 *  return value from the call to {@link #getInstance(OMMFieldEntry, short)} so
 *  we could probably remove it and rename this class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMDataFactory<T extends OMMData> {

    private final Class<T> ommDataType;

    public OMMDataFactory(final Class<T> ommDataType) {
        super();
        this.ommDataType = ommDataType;
    }

    public OMMData getInstance (
        final OMMFieldEntry mockFieldEntry, final short fieldId) {

        OMMData result = mock (ommDataType);

        when (mockFieldEntry.getFieldId()).thenReturn(fieldId);

        when (mockFieldEntry.getData (anyShort ())).thenReturn(result);

        return result;
    }
}
