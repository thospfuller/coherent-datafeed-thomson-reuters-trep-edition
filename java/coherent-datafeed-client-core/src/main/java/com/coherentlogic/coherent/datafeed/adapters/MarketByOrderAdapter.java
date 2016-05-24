package com.coherentlogic.coherent.datafeed.adapters;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMFieldEntryAdapter;
import com.coherentlogic.coherent.datafeed.domain.AttribInfo;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder.Order;
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
public class MarketByOrderAdapter
    extends RFABeanAdapter<MarketByOrder> {

    private static final Logger log =
        LoggerFactory.getLogger(MarketByOrderAdapter.class);

    private final OrderAdapter orderAdapter;

    private final TypedFactory<MarketByOrder.Order> orderFactory;

    public MarketByOrderAdapter (
        TypedFactory<MarketByOrder> marketByOrderFactory,
        TypedFactory<MarketByOrder.Order> orderFactory,
        TypedFactory<AttribInfo> attribInfoFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        OrderAdapter orderAdapter)
        throws SecurityException, NoSuchMethodException {
        this (
            marketByOrderFactory,
            orderFactory,
            attribInfoFactory,
            fieldDictionary,
            fieldEntryAdapters,
            new HashMap<String, Method> (),
            MarketByOrder.class,
            orderAdapter
        );
        
    }

    public MarketByOrderAdapter (
        TypedFactory<MarketByOrder> marketByOrderFactory,
        TypedFactory<MarketByOrder.Order> orderFactory,
        TypedFactory<AttribInfo> attribInfoFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
        OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        Map<String, Method> methodMap,
        Class<? extends RFABean> rfaBeanClass,
        OrderAdapter orderAdapter)
        throws SecurityException, NoSuchMethodException {

        super (
            marketByOrderFactory,
            attribInfoFactory,
            fieldDictionary,
            fieldEntryAdapters,
            rfaBeanClass
        );

        this.orderFactory = orderFactory;
        this.orderAdapter = orderAdapter;
    }

    /**
     * @deprecated We may be able to remove this method altogether.
     */
    @Override
    public MarketByOrder adapt (OMMMsg ommMsg) {

        TypedFactory<? extends RFABean> rfaFactory = getRFABeanFactory();

        MarketByOrder marketByOrder = (MarketByOrder) rfaFactory.getInstance();

        adapt (ommMsg, marketByOrder);

        return marketByOrder;
    }

    /**
     * @see RFABeanAdapter for ideas re how to convert this using reflection.
     */
    @Override
    public void adapt(OMMMsg ommMsg, MarketByOrder marketByOrder) {

        log.info ("adapt: method begins; ommMsg: " + ommMsg +
            ", marketByOrder: " + marketByOrder);

        OMMData marketByOrderData = ommMsg.getPayload();

        OMMMap marketByOrderMap = (OMMMap) marketByOrderData;

        toRFABean(marketByOrderMap, marketByOrder);

        if (ommMsg.has(OMMMsg.HAS_ATTRIB_INFO)) {

            OMMAttribInfo attribInfo = ommMsg.getAttribInfo();

            toRFABean (attribInfo, marketByOrder);
        }

        Iterator iterator = marketByOrderMap.iterator();

        while (iterator.hasNext()) {

            OMMMapEntry mapEntry = (OMMMapEntry) iterator.next();

            OMMData ommKey = (OMMData) mapEntry.getKey();

            String key = ommKey.toString();

            byte action = mapEntry.getAction();

            if (action == OMMMapEntry.Action.ADD) {
                addOrder (marketByOrder, key, mapEntry);
            } else if (action == OMMMapEntry.Action.DELETE) {
                deleteOrder (marketByOrder, key, mapEntry);
            } else if (action == OMMMapEntry.Action.UPDATE) {
                updateOrder (marketByOrder, key, mapEntry);
            }
        }
        log.info ("adapt: method ends.");
    }

    void addOrder (MarketByOrder marketByOrder, String key,
        OMMMapEntry mapEntry) {

        log.info("addOrder: method begins; marketByOrder: " + marketByOrder +
            ", key: " + key + ", mapEntry: " + mapEntry);

        Map<String, Order> orders = marketByOrder.getOrders();

        Order order = orders.get(key);

        if (order != null) {
            throw new AddFailedException("An order already exists under the " +
                "key: " + key + " (order: " + order + ").");
        }

        order = orderFactory.getInstance();

        OMMFieldList fieldList = (OMMFieldList) mapEntry.getData();

        orderAdapter.toRFABean(fieldList, order);

        orders.put(key,  order);

        log.info("addOrder: method ends; order: " + order);
    }

    void deleteOrder (MarketByOrder marketByOrder, String key,
        OMMMapEntry mapEntry) {

        log.info("deleteOrder: method begins; marketByOrder: " + marketByOrder +
            ", key: " + key + ", mapEntry: " + mapEntry);

         Map<String, Order> orders = marketByOrder.getOrders();

         Order order = orders.remove(key);

         if (order == null) {
             throw new DeleteFailedException("An order did not already exist " +
                 "under the key: " + key + ".");
         }
         log.info("deleteOrder: method ends; order: " + order);
    }

    void updateOrder (MarketByOrder marketByOrder, String key,
        OMMMapEntry mapEntry) {

        log.info("updateOrder: method begins; marketByOrder: " + marketByOrder +
            ", key: " + key + ", mapEntry: " + mapEntry);

        Map<String, Order> orders = marketByOrder.getOrders();

        Order order = orders.get(key);

        if (order == null) {
            throw new UpdateFailedException("An order did not already exist " +
                "under the key" + key + ".");
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
    public static class OrderAdapter
        extends RFABeanAdapter<Order> {

        private static final Logger log =
            LoggerFactory.getLogger(OrderAdapter.class);

        public OrderAdapter (
            TypedFactory<Order> orderFactory,
            TypedFactory<AttribInfo> attribInfoFactory,
            FieldDictionary fieldDictionary,
            Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
                OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters)
            throws SecurityException, NoSuchMethodException {
            this (
                orderFactory,
                attribInfoFactory,
                fieldDictionary,
                fieldEntryAdapters,
                new HashMap<String, Method> (),
                Order.class
            );
        }

        public OrderAdapter (
            TypedFactory<Order> orderFactory,
            TypedFactory<AttribInfo> attribInfoFactory,
            FieldDictionary fieldDictionary,
            Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
            Map<String, Method> methodMap,
            Class<? extends RFABean> rfaBeanClass)
            throws SecurityException, NoSuchMethodException {

            super (
                orderFactory,
                attribInfoFactory,
                fieldDictionary,
                fieldEntryAdapters,
                rfaBeanClass
            );
        }
    }
}
