package com.coherentlogic.coherent.datafeed.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Transient;

import com.coherentlogic.coherent.data.model.core.annotations.Changeable;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDateTimeAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * A domain model representation of market by price data.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class MarketByPrice extends StatusResponseBean
    implements RICBeanSpecification, OrderEventGenerator<MarketByPrice.Order> {

    private transient final List<OrderEventListener<MarketByPrice.Order>> orderEventListeners;

    private String ric;

    public MarketByPrice() {
        this (new HashMap<String, Order> (), new ArrayList<OrderEventListener<MarketByPrice.Order>> ());
    }

    public MarketByPrice(Map<String, Order> orders, List<OrderEventListener<MarketByPrice.Order>> orderListeners) {
        this.orders = orders;
        this.orderEventListeners = orderListeners;
    }

    @XStreamAlias(MarketPriceConstants.ORDERS)
    private Map<String, Order> orders;

    @ElementCollection
    public Map<String, Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, Order> orders) {
        this.orders = orders;
    }

    @Override
    public String getRic() {
        return ric;
    }

    public void setRic(String ric) {
        this.ric = ric;
    }

    @Transient
    @Override
    public List<OrderEventListener<Order>> getOrderEventListeners() {
        return orderEventListeners;
    }

    @Override
    public void addOrderEventListener(OrderEventListener<Order> orderEventListener) {
        orderEventListeners.add(orderEventListener);
    }

    @Override
    public boolean removeOrderEventListener(OrderEventListener<Order> orderEventListener) {
        return orderEventListeners.remove(orderEventListener);
    }

    @Override
    public void fireOrderEvent(OrderEvent<Order> orderEvent) {
        orderEventListeners.forEach(
            listener -> {
                listener.onOrderEvent(orderEvent);
            }
        );
    }

    @XStreamAlias(MarketPriceConstants.ORDER)
    public static class Order extends RFABean {

        // REAL64
        @XStreamAlias(MarketPriceConstants.ORDER_PRC)
        private BigDecimal orderPrice;

        // ENUM
        @XStreamAlias(MarketPriceConstants.ORDER_SIDE)
        private String orderSide;

        @XStreamAlias(MarketPriceConstants.NO_ORD) //Integer / UINT64
        private Integer numberOfOrders;

        @XStreamAlias(MarketPriceConstants.ACC_SIZE) // Integer / Real64
        private Integer accumulatedSize;

        @XStreamAlias(MarketPriceConstants.LV_TIM_MS) // Level activity time / Integer / UINT64
        private Long levelActivityTimeMillis;

        /**
         * The Data associated with the Level Activity Time.
         */
        @XStreamAlias(MarketPriceConstants.LV_DATE) 
        private Long levelActivityDate;

        public BigDecimal getOrderPrice() {
            return orderPrice;
        }

        public static final String ORDER_PRICE = "orderPrice";
        
        public void setOrderPrice(@Changeable(ORDER_PRICE) BigDecimal orderPrice) {
            this.orderPrice = orderPrice;
        }

        public String getOrderSide() {
            return orderSide;
        }

        public static final String ORDER_SIDE = "orderSide";

        public void setOrderSide(@Changeable(ORDER_SIDE) String orderSide) {
            this.orderSide = orderSide;
        }

        public Integer getNumberOfOrders() {
            return numberOfOrders;
        }

        public static final String NUMBER_OF_ORDERS = "numberOfOrders";

        public void setNumberOfOrders(@Changeable(NUMBER_OF_ORDERS) Integer numberOfOrders) {
            this.numberOfOrders = numberOfOrders;
        }

        public Integer getAccumulatedSize() {
            return accumulatedSize;
        }

        public static final String ACCUMULATED_SIZE = "accumulatedSize";

        @RFAType(type=MarketPriceConstants.ACC_SIZE)
        @Adapt(using=OMMNumericAdapter.class)
        public void setAccumulatedSize(@Changeable(ACCUMULATED_SIZE) Integer accumulatedSize) {
            this.accumulatedSize = accumulatedSize;
        }

        public Long getLevelActivityTimeMillis() {
            return levelActivityTimeMillis;
        }

        public static final String LEVEL_ACTIVITY_TIME_MILLIS = "levelActivityTimeMillis";

        @RFAType(type=MarketPriceConstants.LV_TIM_MS)
        @Adapt(using=OMMNumericAdapter.class)
        public void setLevelActivityTimeMillis(@Changeable(LEVEL_ACTIVITY_TIME_MILLIS) Long levelActivityTimeMillis) {
            this.levelActivityTimeMillis = levelActivityTimeMillis;
        }

        public Long getLevelActivityDate() {
            return levelActivityDate;
        }

        public static final String LEVEL_ACTIVITY_DATE = "levelActivityDate";

        @RFAType(type=MarketPriceConstants.LV_DATE)
        @Adapt(using=OMMDateTimeAdapter.class)
        public void setLevelActivityDate(@Changeable(LEVEL_ACTIVITY_DATE) Long levelActivityDate) {
            this.levelActivityDate = levelActivityDate;
        }

        @Override
        public String toString() {
            return "Order [orderPrice=" + orderPrice + ", orderSide=" + orderSide + ", numberOfOrders=" + numberOfOrders
                + ", accumulatedSize=" + accumulatedSize + ", levelActivityTimeMillis=" + levelActivityTimeMillis
                + ", levelActivityDate=" + levelActivityDate + "]";
        }
    }
}
