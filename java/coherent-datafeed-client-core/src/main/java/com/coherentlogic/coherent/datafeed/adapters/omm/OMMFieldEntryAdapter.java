package com.coherentlogic.coherent.datafeed.adapters.omm;

import com.coherentlogic.coherent.datafeed.adapters.AbstractAdapter;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMFieldEntry;

/**
 * There's more than one {@link OMMFieldEntry} type that can be converted.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <T> The type being returned is usually a String, BigDecimal,
 *  BigInteger, etc.
 */
public abstract class OMMFieldEntryAdapter<T extends OMMData>
    extends AbstractAdapter<OMMFieldEntry, T> {

    protected final FieldDictionary fieldDictionary;

    protected final OMMDataAdapter dataAdapter;

    public OMMFieldEntryAdapter(
        FieldDictionary fieldDictionary,
        OMMDataAdapter dataAdapter
    ) {
        this.fieldDictionary = fieldDictionary;
        this.dataAdapter = dataAdapter;
    }

    /**
     * Generic implementation of the {@link #adapt(OMMFieldEntry, Class)} method
     * which calls the {@link #adapt(Object)} method and then casts the result
     * to the specified type.
     */
    @Override
    public <X> X adapt(OMMFieldEntry fieldEntry, Class<X> type) {

        T target = adapt(fieldEntry);

        return type.cast(target);
    }
}
