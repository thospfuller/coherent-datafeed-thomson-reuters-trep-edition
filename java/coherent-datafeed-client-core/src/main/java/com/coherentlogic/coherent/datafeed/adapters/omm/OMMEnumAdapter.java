package com.coherentlogic.coherent.datafeed.adapters.omm;

import com.coherentlogic.coherent.datafeed.exceptions.ConversionFailedException;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMEnum;
import com.reuters.rfa.omm.OMMFieldEntry;

/**
 * An adapter for converting an instance of {@link OMMFieldEntry} into a String.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMEnumAdapter
    extends OMMFieldEntryAdapter<OMMEnum> {

    public OMMEnumAdapter(
        FieldDictionary fieldDictionary,
        OMMDataAdapter dataAdapter
    ) {
        super(fieldDictionary, dataAdapter);
    }

    @Override
    public OMMEnum adapt(OMMFieldEntry fieldEntry) {

        OMMEnum ommEnum = dataAdapter.adapt(fieldEntry, OMMEnum.class);

        return ommEnum;
    }

    @Override
    public <X> X adapt(OMMFieldEntry fieldEntry, Class<X> type) {

        OMMEnum ommEnum = adapt(fieldEntry);

        Object result = null;

        short fieldId = fieldEntry.getFieldId();

        int enumValue = ommEnum.getValue();

        if (Integer.class.equals(type))
            result = enumValue;
        else if (String.class.equals(type))
            result = fieldDictionary.expandedValueFor(fieldId, enumValue);
        else
            throw new ConversionFailedException("Cannot cast to type " + type +
                ".");

        return type.cast(result);
    }
}
