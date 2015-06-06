package com.coherentlogic.coherent.datafeed.adapters;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMFieldEntryAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.factories.Factory;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMMsg;

/**
 * An adapter that converts the OMMMsg into an instance of MarketPrice.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketByOrderAdapter
    extends RFABeanAdapter<MarketByOrder> {

    public MarketByOrderAdapter (
        Factory<MarketByOrder> marketByOrderFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters)
        throws SecurityException, NoSuchMethodException {
        this (
            marketByOrderFactory,
            fieldDictionary,
            fieldEntryAdapters,
            new HashMap<String, Method> (),
            MarketByOrder.class
        );
    }

    public MarketByOrderAdapter (
        Factory<MarketByOrder> marketByOrderFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        Class<? extends RFABean> rfaBeanClass)
        throws SecurityException, NoSuchMethodException {
        this (
            marketByOrderFactory,
            fieldDictionary,
            fieldEntryAdapters,
            new HashMap<String, Method> (),
            rfaBeanClass
        );
    }

    public MarketByOrderAdapter (
        Factory<MarketByOrder> marketByOrderFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
        OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        Map<String, Method> methodMap,
        Class<? extends RFABean> rfaBeanClass)
        throws SecurityException, NoSuchMethodException {

        super (
            marketByOrderFactory,
            fieldDictionary,
            fieldEntryAdapters,
            rfaBeanClass
        );
    }

    public MarketByOrder adapt (OMMMsg ommMsg) {

        Factory<? extends RFABean> rfaFactory = getRFABeanFactory();

        MarketByOrder marketByOrder = (MarketByOrder) rfaFactory.getInstance();

        adapt (ommMsg, marketByOrder);

        return marketByOrder;
    }
}
