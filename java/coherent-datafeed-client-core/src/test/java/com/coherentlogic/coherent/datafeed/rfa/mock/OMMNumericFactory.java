package com.coherentlogic.coherent.datafeed.rfa.mock;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.reuters.rfa.omm.OMMFieldEntry;
import com.reuters.rfa.omm.OMMNumeric;
import com.reuters.rfa.omm.OMMTypes;

/**
 * Factory class for creating and configuring mock instances of OMMNumeric.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMNumericFactory extends OMMDataFactory<OMMNumeric> {

    public OMMNumericFactory() {
        super(OMMNumeric.class);
    }

    public OMMNumeric getInstance (
        OMMFieldEntry mockFieldEntry,
        BigInteger value
    ) {
        OMMNumeric result = (OMMNumeric) getInstance(
            mockFieldEntry, OMMTypes.UINT);

        when (result.toBigInteger()).thenReturn(value);

        return result;
    }

    public OMMNumeric getInstance (
        OMMFieldEntry mockFieldEntry,
        BigDecimal value
    ) {
        OMMNumeric result = (OMMNumeric) getInstance(
            mockFieldEntry, OMMTypes.REAL);

        when (result.toBigDecimal()).thenReturn(value);

        return result;
    }
}
