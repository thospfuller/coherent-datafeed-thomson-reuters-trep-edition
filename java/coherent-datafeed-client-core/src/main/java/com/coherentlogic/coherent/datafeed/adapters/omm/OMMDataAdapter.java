package com.coherentlogic.coherent.datafeed.adapters.omm;

import com.coherentlogic.coherent.datafeed.adapters.AbstractAdapter;
import com.reuters.rfa.dictionary.FidDef;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMFieldEntry;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMDataAdapter extends AbstractAdapter<OMMFieldEntry, OMMData> {

    private final FieldDictionary fieldDictionary;

    public static final String BEAN_NAME = "dataAdapter";

    public OMMDataAdapter(FieldDictionary fieldDictionary) {
        this.fieldDictionary = fieldDictionary;
    }

    @Override
    public OMMData adapt(OMMFieldEntry fieldEntry) {
        short fieldId = fieldEntry.getFieldId();

        FidDef fidDef = fieldDictionary.getFidDef(fieldId);

        short type = fidDef.getOMMType();

        OMMData data = fieldEntry.getData(type);

        return data;
    }
}
