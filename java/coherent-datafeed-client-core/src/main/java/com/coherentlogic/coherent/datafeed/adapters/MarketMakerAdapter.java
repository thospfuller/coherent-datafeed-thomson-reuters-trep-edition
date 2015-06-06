package com.coherentlogic.coherent.datafeed.adapters;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMFieldEntryAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.factories.Factory;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;

/**
 * An adapter that converts the OMMMsg into an instance of MarketPrice.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketMakerAdapter extends RFABeanAdapter<MarketMaker> {

    public MarketMakerAdapter (
        Factory<MarketMaker> marketMakerFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters
    ) throws SecurityException, NoSuchMethodException {
        this (
            marketMakerFactory,
            fieldDictionary,
            fieldEntryAdapters,
            new HashMap<String, Method> (),
            MarketMaker.class
        );
    }

    public MarketMakerAdapter(
        Factory<MarketMaker> rfaBeanFactory,
        FieldDictionary fieldDictionary,
        Map<
            Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>
        > fieldEntryAdapters,
        Class<? extends RFABean> rfaBeanClass
    ) throws SecurityException, NoSuchMethodException {
        super(
            rfaBeanFactory,
            fieldDictionary,
            fieldEntryAdapters,
            rfaBeanClass
        );
    }

    public MarketMakerAdapter(
        Factory<MarketMaker> rfaBeanFactory,
        FieldDictionary fieldDictionary,
        Map<
            Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>
        > fieldEntryAdapters,
        Map<String, Method> methodMap,
        Class<? extends RFABean> rfaBeanClass
    ) throws SecurityException, NoSuchMethodException {
        super(
            rfaBeanFactory,
            fieldDictionary,
            fieldEntryAdapters,
            methodMap,
            rfaBeanClass
        );
    }
}
