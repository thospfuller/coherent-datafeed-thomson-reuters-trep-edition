package com.coherentlogic.coherent.datafeed.adapters.omm;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMFieldEntry;
import com.reuters.rfa.omm.OMMNumeric;

/**
 * An adapter for {@link OMMNumeric} objects which converts an OMMFieldEntry
 * into either an instance of BigInteger or into an instance of BigDecimal.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMNumericAdapter
    extends OMMFieldEntryAdapter<OMMNumeric> {

    public static final String BEAN_NAME = "ommNumericAdapter";

    public OMMNumericAdapter(
        FieldDictionary fieldDictionary,
        OMMDataAdapter dataAdapter
    ) {
        super(fieldDictionary, dataAdapter);
    }

    @Override
    public OMMNumeric adapt(OMMFieldEntry fieldEntry) {
        OMMData data = dataAdapter.adapt (fieldEntry);

        OMMNumeric numeric = (OMMNumeric) data;

        return numeric;
    }

    @Override
    public <X> X adapt(OMMFieldEntry fieldEntry, Class<X> type) {

        OMMNumeric numeric = adapt(fieldEntry);

        Number result = null;

        if (BigInteger.class.equals(type))
            result = numeric.toBigInteger();
        else if (BigDecimal.class.equals(type))
            result = numeric.toBigDecimal();

        return type.cast (result);
    }
}
