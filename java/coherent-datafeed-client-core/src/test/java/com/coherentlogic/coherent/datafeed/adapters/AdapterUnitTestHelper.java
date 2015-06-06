package com.coherentlogic.coherent.datafeed.adapters;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_RELATIVE_PATH;
import static com.coherentlogic.coherent.datafeed.misc.Constants.ENUM_TYPE_DEF;
import static com.coherentlogic.coherent.datafeed.misc.Constants.RDM_FIELD_DICTIONARY;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDateTimeAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMFieldEntryAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.factories.Factory;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;

public class AdapterUnitTestHelper {

    public static final Integer DEFAULT_INT_VALUE = 123;

    public static final long DEFAULT_LONG_VALUE = 99999L;

    public static final Short DEFAULT_SHORT_VALUE = 121;

    public static final String DEFAULT_STRING_VALUE = "foo";

    public static final String DEFAULT_BIG_DECIMAL_TEXT = "432.15",
        DEFAULT_INTEGER_DECIMAL_TEXT = "5432";

    public static final BigDecimal DEFAULT_BIG_DECIMAL =
        new BigDecimal(DEFAULT_BIG_DECIMAL_TEXT);

    public static final BigInteger DEFAULT_BIG_INTEGER =
        new BigInteger (DEFAULT_INTEGER_DECIMAL_TEXT);

    private RFABeanAdapter<? extends RFABean> adapter = null;

    /**
     * 
     * @throws Exception
     */
    public void setUp(
        Factory<? extends RFABean> factory,
        Class<? extends RFABeanAdapter<? extends RFABean>> adapterClass
    ) throws Exception {

        ClassLoader defaultClassLoader =
            AdapterUnitTestHelper.class.getClassLoader();

        URL enumTypeDefURL =
            defaultClassLoader.getResource(
                DEFAULT_RELATIVE_PATH + ENUM_TYPE_DEF);

        URL rdmDictionaryURL =
            defaultClassLoader.getResource(
                DEFAULT_RELATIVE_PATH + RDM_FIELD_DICTIONARY);

        String DEFAULT_ENUM_FIELD_DICTIONARY_PATH = enumTypeDefURL.getFile(),
            DEFAULT_RDM_FIELD_DICTIONARY_PATH = rdmDictionaryURL.getFile();

        FieldDictionary fieldDictionary = FieldDictionary.create();

        FieldDictionary.readRDMFieldDictionary(
                fieldDictionary, DEFAULT_RDM_FIELD_DICTIONARY_PATH);

        FieldDictionary.readEnumTypeDef(
            fieldDictionary, DEFAULT_ENUM_FIELD_DICTIONARY_PATH);

        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters =
            new HashMap
                <Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
                OMMFieldEntryAdapter<? extends OMMData>>();

        OMMDataAdapter dataAdapter = new OMMDataAdapter(fieldDictionary);

        OMMNumericAdapter numericAdapter = new OMMNumericAdapter(
            fieldDictionary, dataAdapter);

        OMMDataBufferAdapter dataBufferAdapter =
            new OMMDataBufferAdapter(fieldDictionary, dataAdapter);

        OMMEnumAdapter enumAdapter =
            new OMMEnumAdapter(fieldDictionary, dataAdapter);

        OMMDateTimeAdapter dateTimeAdapter = new OMMDateTimeAdapter(
            fieldDictionary, dataAdapter);

        fieldEntryAdapters.put(OMMNumericAdapter.class, numericAdapter);
        fieldEntryAdapters.put(OMMDataBufferAdapter.class, dataBufferAdapter);
        fieldEntryAdapters.put(OMMEnumAdapter.class, enumAdapter);
        fieldEntryAdapters.put(OMMDateTimeAdapter.class, dateTimeAdapter);

        Constructor<? extends RFABeanAdapter<? extends RFABean>> constructor =
            adapterClass.getConstructor(
                Factory.class, FieldDictionary.class, Map.class);

        adapter =
            (RFABeanAdapter<? extends RFABean>) constructor.newInstance(
                factory, fieldDictionary, fieldEntryAdapters);
    }

    public void tearDown() throws Exception {
        adapter = null;
    }

    protected RFABeanAdapter<? extends RFABean> getAdapter() {
        return adapter;
    }

    public void setAdapter(RFABeanAdapter<? extends RFABean> adapter) {
        this.adapter = adapter;
    }

    public static FieldDictionary getFieldDictionary () {
        ClassLoader defaultClassLoader =
            AdapterUnitTestHelper.class.getClassLoader();

        URL enumTypeDefURL =
            defaultClassLoader.getResource(
                DEFAULT_RELATIVE_PATH + ENUM_TYPE_DEF);

        URL rdmDictionaryURL =
            defaultClassLoader.getResource(
                DEFAULT_RELATIVE_PATH + RDM_FIELD_DICTIONARY);

        String DEFAULT_ENUM_FIELD_DICTIONARY_PATH = enumTypeDefURL.getFile(),
            DEFAULT_RDM_FIELD_DICTIONARY_PATH = rdmDictionaryURL.getFile();

        FieldDictionary fieldDictionary = FieldDictionary.create();

        FieldDictionary.readRDMFieldDictionary(
                fieldDictionary, DEFAULT_RDM_FIELD_DICTIONARY_PATH);

        FieldDictionary.readEnumTypeDef(
            fieldDictionary, DEFAULT_ENUM_FIELD_DICTIONARY_PATH);

        return fieldDictionary;
    }

    public static Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
        OMMFieldEntryAdapter<? extends OMMData>> getFieldEntryAdapters (
            FieldDictionary fieldDictionary) {

        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters = null;

        fieldEntryAdapters =
            new HashMap
                <Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
                OMMFieldEntryAdapter<? extends OMMData>>();

        OMMDataAdapter dataAdapter = new OMMDataAdapter(fieldDictionary);

        OMMNumericAdapter numericAdapter = new OMMNumericAdapter(
            fieldDictionary, dataAdapter);

        OMMDataBufferAdapter dataBufferAdapter =
            new OMMDataBufferAdapter(fieldDictionary, dataAdapter);

        OMMEnumAdapter enumAdapter =
            new OMMEnumAdapter(fieldDictionary, dataAdapter);

        OMMDateTimeAdapter dateTimeAdapter = new OMMDateTimeAdapter(
            fieldDictionary, dataAdapter);

        fieldEntryAdapters.put(OMMNumericAdapter.class, numericAdapter);
        fieldEntryAdapters.put(OMMDataBufferAdapter.class, dataBufferAdapter);
        fieldEntryAdapters.put(OMMEnumAdapter.class, enumAdapter);
        fieldEntryAdapters.put(OMMDateTimeAdapter.class, dateTimeAdapter);

        return fieldEntryAdapters;
    }
}
