package com.coherentlogic.coherent.datafeed.adapters.omm;

import com.coherentlogic.coherent.data.model.core.exceptions.ConversionFailedException;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMEnum;
import com.reuters.rfa.omm.OMMFieldEntry;

/**
 * An adapter for converting an instance of {@link OMMFieldEntry} into a String.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMEnumAdapter
    extends OMMFieldEntryAdapter<OMMEnum> {

    public static final String BEAN_NAME = "ommEnumAdapter", YES = "Y", NO = "N";

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
        else if (Boolean.class.equals(type)) {

            String expandedValue = fieldDictionary.expandedValueFor(fieldId, enumValue);

            result = null;

            if (YES.equals(expandedValue))
                result = Boolean.TRUE;
            else if (NO.equals(expandedValue))
                result = Boolean.FALSE;
            else throw new ConversionFailedException("The fieldId " + fieldId + " cannot be converted into the type " +
                type);

        } else
            throw new ConversionFailedException("The fieldId " + fieldId + " cannot be converted into the type " +
                type);

        return type.cast(result);
    }
}
