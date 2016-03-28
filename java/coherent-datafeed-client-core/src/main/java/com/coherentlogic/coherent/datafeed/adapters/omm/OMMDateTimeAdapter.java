package com.coherentlogic.coherent.datafeed.adapters.omm;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.exceptions.ConversionFailedException;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMDateTime;
import com.reuters.rfa.omm.OMMFieldEntry;

/**
 * An adapter for {@link OMMDateTime} instances.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMDateTimeAdapter extends OMMFieldEntryAdapter<OMMDateTime> {

    private static final Logger log =
        LoggerFactory.getLogger(OMMDateTimeAdapter.class);

    public static final String BEAN_NAME = "ommDateTimeAdapter";

    public OMMDateTimeAdapter(FieldDictionary fieldDictionary,
        OMMDataAdapter dataAdapter) {
        super(fieldDictionary, dataAdapter);
    }

    @Override
    public OMMDateTime adapt(OMMFieldEntry fieldEntry) {

        OMMData data = dataAdapter.adapt(fieldEntry);

        OMMDateTime ommDateTime = (OMMDateTime) data;

        return ommDateTime;
    }

    @Override
    public <X> X adapt(OMMFieldEntry fieldEntry, Class<X> type) {

        OMMDateTime dateTime = adapt(fieldEntry);

        Date date = dateTime.toDate();

        Object result = null;

        if (Long.class.equals(type))
            result = date.getTime();
        else if (Date.class.equals(type))
            result = date;
        else
            throw new ConversionFailedException ("Unable to convert the " +
                "fieldEntry " + fieldEntry + " to the type " + type + ".");

        log.debug("adapt: method ends; result: " + result);

        return type.cast(result);
    }
}
