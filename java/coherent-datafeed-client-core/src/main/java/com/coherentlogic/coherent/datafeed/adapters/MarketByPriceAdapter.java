package com.coherentlogic.coherent.datafeed.adapters;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeEvent.UpdateType;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMFieldEntryAdapter;
import com.coherentlogic.coherent.datafeed.domain.AttribInfo;
import com.coherentlogic.coherent.datafeed.domain.EventType;
import com.coherentlogic.coherent.datafeed.domain.MarketByPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketByPrice.Order;
import com.coherentlogic.coherent.datafeed.domain.OrderEvent;
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
 * An adapter that converts the OMMMsg into an instance of MarketByPrice.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketByPriceAdapter extends RFABeanAdapter<MarketByPrice> {

    private static final Logger log = LoggerFactory.getLogger(MarketByPriceAdapter.class);

    private final OrderAdapter orderAdapter;

    private final TypedFactory<MarketByPrice.Order> orderFactory;

    public MarketByPriceAdapter (
        TypedFactory<MarketByPrice> marketByPriceFactory,
        TypedFactory<MarketByPrice.Order> orderFactory,
        TypedFactory<AttribInfo> attribInfoFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
            OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        OrderAdapter orderAdapter)
        throws SecurityException, NoSuchMethodException {
        this (
            marketByPriceFactory,
            orderFactory,
            attribInfoFactory,
            fieldDictionary,
            fieldEntryAdapters,
            new HashMap<String, Method> (),
            MarketByPrice.class,
            orderAdapter
        );
        
    }

    public MarketByPriceAdapter (
        TypedFactory<MarketByPrice> marketByPriceFactory,
        TypedFactory<MarketByPrice.Order> orderFactory,
        TypedFactory<AttribInfo> attribInfoFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
        OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        Map<String, Method> methodMap,
        Class<? extends RFABean> rfaBeanClass,
        OrderAdapter orderAdapter)
        throws SecurityException, NoSuchMethodException {

        super (
            marketByPriceFactory,
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
    public MarketByPrice adapt (OMMMsg ommMsg) {

        TypedFactory<? extends RFABean> rfaFactory = getRFABeanFactory();

        MarketByPrice marketByPrice = (MarketByPrice) rfaFactory.getInstance();

        adapt (ommMsg, marketByPrice);

        return marketByPrice;
    }

    /**
     * @see RFABeanAdapter for ideas re how to convert this using reflection.
     */
    @Override
    public void adapt(OMMMsg ommMsg, MarketByPrice marketByPrice) {

        log.debug ("adapt: method begins; ommMsg: " + ommMsg + ", marketByPrice: " + marketByPrice);

        // See Issue #25 -- the update type needs to be set properly.
        AggregatePropertyChangeCollector<MarketByPrice> collector
            = new AggregatePropertyChangeCollector<MarketByPrice> (marketByPrice, UpdateType.full);

        OMMData marketByPriceData = ommMsg.getPayload();

        OMMMap marketByPriceMap = (OMMMap) marketByPriceData;

        toRFABean(((OMMMap)marketByPriceMap), marketByPrice);

        if (ommMsg.has(OMMMsg.HAS_ATTRIB_INFO)) {

            OMMAttribInfo attribInfo = ommMsg.getAttribInfo();

            toRFABean (attribInfo, marketByPrice);
        }

        Iterator<OMMMapEntry> iterator = marketByPriceMap.iterator();

        while (iterator.hasNext()) {

            OMMMapEntry mapEntry = iterator.next();

            OMMData ommKey = (OMMData) mapEntry.getKey();

            String key = ommKey.toString();

            byte action = mapEntry.getAction();

            if (action == OMMMapEntry.Action.ADD) {
                addOrder (marketByPrice, key, mapEntry);
            } else if (action == OMMMapEntry.Action.DELETE) {
                deleteOrder (marketByPrice, key, mapEntry);
            } else if (action == OMMMapEntry.Action.UPDATE) {
                updateOrder (marketByPrice, key, mapEntry);
            }
        }

        collector.done();

        log.debug ("adapt: method ends.");
    }

    void addOrder (MarketByPrice marketByPrice, String key, OMMMapEntry mapEntry) {

        log.debug("addOrder: method begins; marketByPrice: " + marketByPrice + ", key: " + key + ", mapEntry: " +
            mapEntry);

        Map<String, Order> orders = marketByPrice.getOrders();

        Order order = orders.get(key);

        if (order != null)
            throw new AddFailedException("An order already exists under the key: " + key + " (order: " + order + ").");

        order = orderFactory.getInstance();

        marketByPrice.fireOrderEvent(new OrderEvent<MarketByPrice.Order> (order, key, EventType.instantiated));

        OMMFieldList fieldList = (OMMFieldList) mapEntry.getData();

        orderAdapter.toRFABean(fieldList, order);

        orders.put(key,  order);

        marketByPrice.fireOrderEvent(new OrderEvent<MarketByPrice.Order> (order, key, EventType.added));

        log.debug("addOrder: method ends; order: " + order);
    }

    void deleteOrder (MarketByPrice marketByPrice, String key, OMMMapEntry mapEntry) {

        log.debug("deleteOrder: method begins; marketByPrice: " + marketByPrice + ", key: " + key + ", mapEntry: " +
            mapEntry);

         Map<String, Order> orders = marketByPrice.getOrders();

         Order order = orders.remove(key);

         marketByPrice.fireOrderEvent(new OrderEvent<MarketByPrice.Order> (order, key, EventType.deleted));

         if (order == null) {
             throw new DeleteFailedException("An order did not already exist under the key: " + key + ".");
         }
         log.debug("deleteOrder: method ends; order: " + order);
    }

    void updateOrder (MarketByPrice marketByPrice, String key, OMMMapEntry mapEntry) {

        log.debug("updateOrder: method begins; marketByPrice: " + marketByPrice + ", key: " + key + ", mapEntry: " +
            mapEntry);

        Map<String, Order> orders = marketByPrice.getOrders();

        Order order = orders.get(key);

        if (order == null)
            throw new UpdateFailedException("An order did not already exist under the key: " + key + ".");

        OMMFieldList fieldList = (OMMFieldList) mapEntry.getData();

        orderAdapter.toRFABean(fieldList, order);

        marketByPrice.fireOrderEvent(new OrderEvent<MarketByPrice.Order> (order, key, EventType.updated));

        log.debug("updateOrder: method ends; order: " + order);
    }

    /**
     * An adapter that converts the OMMMsg into an instance of
     * MarketByPrice.Order.
     *
     * @author <a href="mailto:support@coherentlogic.com">Support</a>
     */
    public static class OrderAdapter
        extends RFABeanAdapter<Order> {

        private static final Logger log = LoggerFactory.getLogger(OrderAdapter.class);

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

        @Override
        void toRFABean(OMMFieldList fieldList, Order t) {

            // See Issue #25 -- the update type needs to be set properly.
            AggregatePropertyChangeCollector<MarketByPrice.Order> collector
                = new AggregatePropertyChangeCollector<MarketByPrice.Order> (t, UpdateType.full);

            super.toRFABean(fieldList, t);

            collector.done();
        }
    }
}
