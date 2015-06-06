package com.coherentlogic.coherent.datafeed.adapters;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMFieldEntryAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.factories.Factory;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;

/**
 * An adapter that converts the OMMMsg into an instance of MarketPrice.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketPriceAdapter extends RFABeanAdapter<MarketPrice> {

    public MarketPriceAdapter (
        Factory<MarketPrice> marketPriceFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters)
        throws SecurityException, NoSuchMethodException {
        this (
            marketPriceFactory,
            fieldDictionary,
            fieldEntryAdapters,
            new HashMap<String, Method> (),
            MarketPrice.class
        );
    }

    public MarketPriceAdapter (
        Factory<MarketPrice> marketPriceFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        Class<? extends RFABean> rfaBeanClass)
        throws SecurityException, NoSuchMethodException {
        this (
            marketPriceFactory,
            fieldDictionary,
            fieldEntryAdapters,
            new HashMap<String, Method> (),
            rfaBeanClass
        );
    }

    public MarketPriceAdapter (
        Factory<MarketPrice> marketPriceFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
        OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        Map<String, Method> methodMap,
        Class<? extends RFABean> rfaBeanClass)
        throws SecurityException, NoSuchMethodException {

        super (
            marketPriceFactory,
            fieldDictionary,
            fieldEntryAdapters,
            MarketPrice.class
        );
    }
}
