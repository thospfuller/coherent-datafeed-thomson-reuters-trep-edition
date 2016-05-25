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
import com.coherentlogic.coherent.datafeed.domain.EventType;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.OrderEvent;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
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

    private final TypedFactory<MarketMaker.Order> orderFactory;

    public MarketMakerAdapter (
        TypedFactory<MarketMaker> marketMakerFactory,
        TypedFactory<AttribInfo> attribInfoFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
        OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        MarketMakerAdapter.OrderAdapter orderAdapter,
        TypedFactory<MarketMaker.Order> orderFactory
    ) throws SecurityException, NoSuchMethodException {
        this (
            marketMakerFactory,
            attribInfoFactory,
            fieldDictionary,
            fieldEntryAdapters,
            new HashMap<String, Method> (),
            MarketMaker.class,
            orderAdapter,
            orderFactory
        );
        
    }

    public MarketMakerAdapter (
        TypedFactory<MarketMaker> marketMakerFactory,
        TypedFactory<AttribInfo> attribInfoFactory,
        FieldDictionary fieldDictionary,
        Map<Class<? extends OMMFieldEntryAdapter<? extends OMMData>>,
        OMMFieldEntryAdapter<? extends OMMData>> fieldEntryAdapters,
        Map<String, Method> methodMap,
        Class<? extends RFABean> rfaBeanClass,
        MarketMakerAdapter.OrderAdapter orderAdapter,
        TypedFactory<MarketMaker.Order> orderFactory
    ) throws SecurityException, NoSuchMethodException {
        super (
            marketMakerFactory,
            attribInfoFactory,
            fieldDictionary,
            fieldEntryAdapters,
            rfaBeanClass
        );

        this.orderAdapter = orderAdapter;
        this.orderFactory = orderFactory;
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

        if (marketMakerMap != null) {

            toRFABean(marketMakerMap, marketMaker);

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

        } else
            log.debug("The marketMakerMap was null -- this has not been the case until recently.");

        if (ommMsg.has(OMMMsg.HAS_ATTRIB_INFO)) {

            OMMAttribInfo attribInfo = ommMsg.getAttribInfo();

            toRFABean (attribInfo, marketMaker);
        }

        log.info ("adapt: method ends.");
    }

    void addOrder (MarketMaker marketMaker, String key, OMMMapEntry mapEntry) {

        log.info("addOrder: method begins; marketMaker: " + marketMaker + ", key: " + key +
            ", mapEntry: " + mapEntry);

        Map<String, MarketMaker.Order> orders = marketMaker.getOrders();

        MarketMaker.Order order = orders.get(key);

        if (order != null) {
            // Not sure if we should throw new AddFailedException exception but adding an order when one already exists
            // seems wrong -- this is possibly a replace?
            log.warn("An order already exists under the key: " + key + " (order: " + order + ") -- will treat this "
                + "as an update (need to ask TR about this because we've not seen this behavior before).");
            updateOrder (marketMaker, key, mapEntry);
        } else {

            order = orderFactory.getInstance();

            marketMaker.fireOrderEvent(new OrderEvent<MarketMaker.Order> (order, key, EventType.instantiated));

            OMMFieldList fieldList = (OMMFieldList) mapEntry.getData();

            orderAdapter.toRFABean(fieldList, order);

            marketMaker.fireOrderEvent(new OrderEvent<MarketMaker.Order> (order, key, EventType.added));

            orders.put(key, order);
        }
        log.info("addOrder: method ends; order: " + order);
    }

    void deleteOrder (MarketMaker marketMaker, String key, OMMMapEntry mapEntry) {

        log.info("deleteOrder: method begins; marketMaker: " + marketMaker + ", key: " + key +
            ", mapEntry: " + mapEntry);

         Map<String, MarketMaker.Order> orders = marketMaker.getOrders();

         MarketMaker.Order order = orders.remove(key);

         marketMaker.fireOrderEvent(new OrderEvent<MarketMaker.Order> (order, key, EventType.deleted));

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
            throw new UpdateFailedException("An order did not already exist under the key: " + key + ".");
        }

        OMMFieldList fieldList = (OMMFieldList) mapEntry.getData();

        orderAdapter.toRFABean(fieldList, order);

        marketMaker.fireOrderEvent(new OrderEvent<MarketMaker.Order> (order, key, EventType.updated));

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
                MarketMaker.Order.class
            );
        }

        public OrderAdapter (
            TypedFactory<MarketMaker.Order> orderFactory,
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
