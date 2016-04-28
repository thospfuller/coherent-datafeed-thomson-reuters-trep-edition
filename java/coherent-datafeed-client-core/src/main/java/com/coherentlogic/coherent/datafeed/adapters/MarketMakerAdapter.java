package com.coherentlogic.coherent.datafeed.adapters;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMFieldEntryAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.exceptions.AddFailedException;
import com.coherentlogic.coherent.datafeed.exceptions.DeleteFailedException;
import com.coherentlogic.coherent.datafeed.exceptions.UpdateFailedException;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMFieldList;
import com.reuters.rfa.omm.OMMMap;
import com.reuters.rfa.omm.OMMMapEntry;
import com.reuters.rfa.omm.OMMMsg;

/**
 * An adapter that converts the OMMMsg into an instance of MarketPrice.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketMakerAdapter extends RFABeanAdapter<MarketMaker> {

    private static final Logger log = LoggerFactory.getLogger(MarketMakerAdapter.class);

    private final MarketMakerAdapter.OrderAdapter orderAdapter;

    public MarketMakerAdapter (
        TypedFactory<MarketMaker> marketMakerFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
        OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        MarketMakerAdapter.OrderAdapter orderAdapter)
        throws SecurityException, NoSuchMethodException {
        this (
            marketMakerFactory,
            fieldDictionary,
            fieldEntryAdapters,
            new HashMap<String, Method> (),
            MarketMaker.class,
            orderAdapter
        );
        
    }

    public MarketMakerAdapter (
        TypedFactory<MarketMaker> marketByOrderFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
        OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        Map<String, Method> methodMap,
        Class<? extends RFABean> rfaBeanClass,
        MarketMakerAdapter.OrderAdapter orderAdapter)
        throws SecurityException, NoSuchMethodException {

        super (
            marketByOrderFactory,
            fieldDictionary,
            fieldEntryAdapters,
            rfaBeanClass
        );

        this.orderAdapter = orderAdapter;
    }

    /**
     * @deprecated We may be able to remove this method altogether.
     */
    @Override
    public MarketMaker adapt (OMMMsg ommMsg) {

        TypedFactory<? extends RFABean> rfaFactory = getRFABeanFactory();

        MarketMaker marketMaker = (MarketMaker) rfaFactory.getInstance();

        adapt (ommMsg, marketMaker);

        return marketMaker;
    }

    @Override
    public void adapt(OMMMsg ommMsg, MarketMaker marketMaker) {

        log.info ("adapt: method begins; ommMsg: " + ommMsg + ", marketMaker: " + marketMaker);

        OMMData marketMakerData = ommMsg.getPayload();

        OMMMap marketMakerMap = (OMMMap) marketMakerData;

        toRFABean(marketMakerMap, marketMaker);

        if (ommMsg.has(OMMMsg.HAS_ATTRIB_INFO)) {

            OMMAttribInfo attribInfo = ommMsg.getAttribInfo();

            toRFABean (attribInfo, marketMaker);
        }

        Iterator iterator = marketMakerMap.iterator();

        while (iterator.hasNext()) {

            OMMMapEntry mapEntry = (OMMMapEntry) iterator.next();

            OMMData ommKey = (OMMData) mapEntry.getKey();

            String key = ommKey.toString();

            byte action = mapEntry.getAction();

            if (action == OMMMapEntry.Action.ADD) {
                addOrder (marketMaker, key, mapEntry);
            } else if (action == OMMMapEntry.Action.DELETE) {
                deleteOrder (marketMaker, key, mapEntry);
            } else if (action == OMMMapEntry.Action.UPDATE) {
                updateOrder (marketMaker, key, mapEntry);
            }
        }
        log.info ("adapt: method ends.");
    }

    void addOrder (MarketMaker marketMaker, String key, OMMMapEntry mapEntry) {

        log.info("addOrder: method begins; marketMaker: " + marketMaker + ", key: " + key +
            ", mapEntry: " + mapEntry);

        Map<String, MarketMaker.Order> orders = marketMaker.getOrders();

        MarketMaker.Order order = orders.get(key);

        if (order != null) {
            throw new AddFailedException("An order already exists under the " +
                "key" + key + " (order: " + order + ").");
        }

        order = new MarketMaker.Order ();

        OMMFieldList fieldList = (OMMFieldList) mapEntry.getData();

        orderAdapter.toRFABean(fieldList, order);

        orders.put(key,  order);

        log.info("addOrder: method ends; order: " + order);
    }

    void deleteOrder (MarketMaker marketMaker, String key, OMMMapEntry mapEntry) {

        log.info("deleteOrder: method begins; marketMaker: " + marketMaker + ", key: " + key +
            ", mapEntry: " + mapEntry);

         Map<String, MarketMaker.Order> orders = marketMaker.getOrders();

         MarketMaker.Order order = orders.remove(key);

         if (order == null) {
             throw new DeleteFailedException("An order did not already exist " +
                 "under the key: " + key + ".");
         }
         log.info("deleteOrder: method ends; order: " + order);
    }

    void updateOrder (MarketMaker marketMaker, String key, OMMMapEntry mapEntry) {

        log.info("updateOrder: method begins; marketMaker: " + marketMaker + ", key: " + key +
            ", mapEntry: " + mapEntry);

        Map<String, MarketMaker.Order> orders = marketMaker.getOrders();

        MarketMaker.Order order = orders.get(key);

        if (order == null) {
            throw new UpdateFailedException("An order did not already exist under the key" + key + ".");
        }

        OMMFieldList fieldList = (OMMFieldList) mapEntry.getData();

        orderAdapter.toRFABean(fieldList, order);

        log.info("updateOrder: method ends; order: " + order);
    }

    /**
     * An adapter that converts the OMMMsg into an instance of
     * MarketByOrder.Order.
     *
     * @author <a href="mailto:support@coherentlogic.com">Support</a>
     */
    public static class OrderAdapter extends RFABeanAdapter<MarketMaker.Order> {

        private static final Logger log =
            LoggerFactory.getLogger(OrderAdapter.class);

        public OrderAdapter (
            TypedFactory<MarketMaker.Order> orderFactory,
            FieldDictionary fieldDictionary,
            Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
                OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters)
            throws SecurityException, NoSuchMethodException {
            this (
                orderFactory,
                fieldDictionary,
                fieldEntryAdapters,
                new HashMap<String, Method> (),
                MarketMaker.Order.class
            );
        }

        public OrderAdapter (
            TypedFactory<MarketMaker.Order> orderFactory,
            FieldDictionary fieldDictionary,
            Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
            Map<String, Method> methodMap,
            Class<? extends RFABean> rfaBeanClass)
            throws SecurityException, NoSuchMethodException {

            super (
                orderFactory,
                fieldDictionary,
                fieldEntryAdapters,
                rfaBeanClass
            );
        }
    }
}
