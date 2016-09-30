package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.domain.MarketMaker.Order.LAST_ACTIVITY_TIME_MILLIS;
import static com.coherentlogic.coherent.datafeed.domain.RDMFieldDictionaryConstants.ACTIV_DATE_KEY;
import static com.coherentlogic.coherent.datafeed.domain.RDMFieldDictionaryConstants.MKT_STATUS;
import static com.coherentlogic.coherent.datafeed.domain.RDMFieldDictionaryConstants.MNEMONIC;
import static com.coherentlogic.coherent.datafeed.domain.RDMFieldDictionaryConstants.OR_RNK_RUL;
import static com.coherentlogic.coherent.datafeed.domain.RDMFieldDictionaryConstants.PR_RNK_RUL;
import static com.coherentlogic.coherent.datafeed.domain.RDMFieldDictionaryConstants.TIMACT_MS;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_BY_PRICE;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.coherentlogic.coherent.data.model.core.annotations.Changeable;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDateTimeAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * A domain model representation of market by price data.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=MARKET_BY_PRICE)
@XStreamAlias(MARKET_BY_PRICE)
public class MarketByPrice extends StatusResponseBean
    implements RICBeanSpecification, OrderEventGenerator<MarketByPrice.Order> {

    private static final long serialVersionUID = -8605808741253646718L;

    private transient final List<OrderEventListener<MarketByPrice.Order>> orderEventListeners;

    private String ric;

    /**
     * WARNING: COPY/PASTED FROM MARKETPRICE SIMPLY TO TEST THIS, SO I'M NOT
     * SURE WE NEED THIS AND HENCE I'M DEPRECATING IT.
     *
     * Product permissions information.
     *
     * PROD_PERM: UINT
     *
     * @deprecated See comments.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.PROD_PERM)
    private BigInteger permission = null;

    @XStreamAlias(RDMFieldDictionaryConstants.DSPLY_NAME)
    private String displayName;

    @XStreamAlias(CURRENCY)
    private String currency;

    @XStreamAlias(ACTIV_DATE_KEY)
    private Date activeDate;

    @XStreamAlias(PR_RNK_RUL)
    private String priceRankRule = null;

    @XStreamAlias(OR_RNK_RUL)
    private String orderRankRule = null;

    /**
     * @see Appears in MarketPrice as well.
     */
    @XStreamAlias(MNEMONIC)
    private String exchangeId = null;

    @XStreamAlias(MKT_STATUS)
    private String marketStatusIndicator = null;

    @XStreamAlias(TIMACT_MS)
    private Long lastActivityTimeMillis = null;

    @XStreamAlias(RDMFieldDictionaryConstants.CONTEXT_ID)
    private Integer contextId;

    @XStreamAlias(RDMFieldDictionaryConstants.DDS_DSO_ID)
    private Integer elektronDataSourceOwnerId = null;

    @XStreamAlias(RDMFieldDictionaryConstants.SPS_SP_RIC)
    private String spsSubProviderLevelRic;

    @XStreamAlias(RDMFieldDictionaryConstants.BOOK_STATE)
    private String orderBookState;

    /* Numerical value indicating whether Market Orders should be combined with priced orders in the top row of a
     * ranked MBP book.
     *
     * VALUE      DISPLAY   MEANING
     * 0          " "       Undefined
     * 1          "I"       Market Orders Included in Top Row
     * 2          "E"       Market Orders Excluded From Top Row
     * 3          "D"       Market Orders aggregated volume and number of orders is presented as a discrete, blank price
     *                      point at the top row of the order book.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.MKT_OR_RUL)
    private Integer combineMarketOrdersWithPriceOrders;

    @XStreamAlias(RDMFieldDictionaryConstants.TRD_STATUS)
    private String tradingStatus;

    public MarketByPrice() {
        this (new HashMap<String, Order> (), new ArrayList<OrderEventListener<MarketByPrice.Order>> ());
    }

    public MarketByPrice(Map<String, Order> orders, List<OrderEventListener<MarketByPrice.Order>> orderListeners) {
        this.orders = orders;
        this.orderEventListeners = orderListeners;
    }

    @XStreamAlias(RDMFieldDictionaryConstants.ORDERS)
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

    /**
     *
     */
    @RFAType(type=RDMFieldDictionaryConstants.PROD_PERM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPermission(@Changeable(MarketByOrder.PERMISSION) BigInteger permission) {
        this.permission = permission;
    }

    public String getDisplayName() {
        return displayName;
    }

    @RFAType(type=RDMFieldDictionaryConstants.DSPLY_NAME)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setDisplayName(@Changeable(MarketByOrder.DISPLAY_NAME) String displayName) {
        this.displayName = displayName;
    }
    
    @UsingKey(type=CURRENCY)
    public String getCurrency() {
        return currency;
    }

    public static final String CURRENCY = "currency";

    @RFAType(type=CURRENCY)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCurrency(@Changeable(CURRENCY) String currency) {
        this.currency = currency;
    }

    @UsingKey(type=ACTIV_DATE_KEY)
    public Date getActiveDate() {
        return activeDate;
    }

    @RFAType(type=ACTIV_DATE_KEY)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    @UsingKey(type=PR_RNK_RUL)
    public String getPriceRankRule() {
        return priceRankRule;
    }

    @RFAType(type=PR_RNK_RUL)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceRankRule(String priceRankRule) {
        this.priceRankRule = priceRankRule;
    }

    @UsingKey(type=OR_RNK_RUL)
    public String getOrderRankRule() {
        return orderRankRule;
    }

    @RFAType(type=OR_RNK_RUL)
    @Adapt(using=OMMEnumAdapter.class)
    public void setOrderRankRule(String orderRankRule) {
        this.orderRankRule = orderRankRule;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.MNEMONIC)
    public String getExchangeId() {
        return exchangeId;
    }

    @RFAType(type=RDMFieldDictionaryConstants.MNEMONIC)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setExchangeId(@Changeable(MarketByOrder.EXCHANGE_ID) String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getMarketStatusIndicator() {
        return marketStatusIndicator;
    }

    public void setMarketStatusIndicator(
        @Changeable(MarketMaker.MARKET_STATUS_INDICATOR) String marketStatusIndicator) {
        this.marketStatusIndicator = marketStatusIndicator;
    }

    @UsingKey(type=TIMACT_MS)
    public Long getLastActivityTimeMillis() {
        return lastActivityTimeMillis;
    }

    @RFAType(type=TIMACT_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLastActivityTimeMillis(
        @Changeable(LAST_ACTIVITY_TIME_MILLIS) Long lastActivityTimeMillis) {
        this.lastActivityTimeMillis = lastActivityTimeMillis;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.CONTEXT_ID)
    public Integer getContextId() {
        return contextId;
    }

    @RFAType(type=RDMFieldDictionaryConstants.CONTEXT_ID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setContextId(@Changeable(MarketByOrder.CONTEXT_ID) Integer contextId) {
        this.contextId = contextId;
    }

    /**
     * @TODO: Consider renaming this method.
     */
    @UsingKey(type=RDMFieldDictionaryConstants.DDS_DSO_ID)
    public Integer getElektronDataSourceOwnerId() {
        return elektronDataSourceOwnerId;
    }

    @RFAType(type=RDMFieldDictionaryConstants.DDS_DSO_ID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setElektronDataSourceOwnerId(@Changeable(MarketByOrder.ELEKTRON_DATA_SOURCE_OWNER_ID)
        Integer elektronDataSourceOwnerId
    ) {
        this.elektronDataSourceOwnerId = elektronDataSourceOwnerId;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.SPS_SP_RIC)
    public String getSpsSubProviderLevelRic() {
        return spsSubProviderLevelRic;
    }

    @RFAType(type=RDMFieldDictionaryConstants.SPS_SP_RIC)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setSpsSubProviderLevelRic(
        @Changeable(MarketByOrder.SPS_SUB_PROVIDER_LEVEL_RIC) String spsSubProviderLevelRic
    ) {
        this.spsSubProviderLevelRic = spsSubProviderLevelRic;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BOOK_STATE)
    public String getOrderBookState() {
        return orderBookState;
    }

    @RFAType(type=RDMFieldDictionaryConstants.BOOK_STATE)
    @Adapt(using=OMMEnumAdapter.class)
    public void setOrderBookState(@Changeable(MarketByOrder.ORDER_BOOK_STATE) String orderBookState) {
        this.orderBookState = orderBookState;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.MKT_OR_RUL)
    public Integer getCombineMarketOrdersWithPriceOrders() {
        return combineMarketOrdersWithPriceOrders;
    }

    public static final String COMBINE_MARKET_ORDERS_WITH_PRICE_ORDERS = "combineMarketOrdersWithPriceOrders";

    @RFAType(type=RDMFieldDictionaryConstants.MKT_OR_RUL)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCombineMarketOrdersWithPriceOrders(
        @Changeable(COMBINE_MARKET_ORDERS_WITH_PRICE_ORDERS) Integer combineMarketOrdersWithPriceOrders
    ) {
        this.combineMarketOrdersWithPriceOrders = combineMarketOrdersWithPriceOrders;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TRD_STATUS)
    public String getTradingStatus() {
        return tradingStatus;
    }

    @RFAType(type=RDMFieldDictionaryConstants.TRD_STATUS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradingStatus(@Changeable(MarketByOrder.TRADING_STATUS) String tradingStatus) {
        this.tradingStatus = tradingStatus;
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

    @XStreamAlias(RDMFieldDictionaryConstants.ORDER)
    public static class Order extends RFABean {

        // REAL64
        @XStreamAlias(RDMFieldDictionaryConstants.ORDER_PRC)
        private BigDecimal orderPrice;

        // ENUM
        @XStreamAlias(RDMFieldDictionaryConstants.ORDER_SIDE)
        private String orderSide;

        @XStreamAlias(RDMFieldDictionaryConstants.NO_ORD) //Integer / UINT64
        private Integer numberOfOrders;

        @XStreamAlias(RDMFieldDictionaryConstants.ACC_SIZE) // Integer / Real64
        private Integer accumulatedSize;

        @XStreamAlias(RDMFieldDictionaryConstants.LV_TIM_MS) // Level activity time / Integer / UINT64
        private Long levelActivityTimeMillis;

        /**
         * The Data associated with the Level Activity Time.
         */
        @XStreamAlias(RDMFieldDictionaryConstants.LV_DATE) 
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

        @RFAType(type=RDMFieldDictionaryConstants.ACC_SIZE)
        @Adapt(using=OMMNumericAdapter.class)
        public void setAccumulatedSize(@Changeable(ACCUMULATED_SIZE) Integer accumulatedSize) {
            this.accumulatedSize = accumulatedSize;
        }

        public Long getLevelActivityTimeMillis() {
            return levelActivityTimeMillis;
        }

        public static final String LEVEL_ACTIVITY_TIME_MILLIS = "levelActivityTimeMillis";

        @RFAType(type=RDMFieldDictionaryConstants.LV_TIM_MS)
        @Adapt(using=OMMNumericAdapter.class)
        public void setLevelActivityTimeMillis(@Changeable(LEVEL_ACTIVITY_TIME_MILLIS) Long levelActivityTimeMillis) {
            this.levelActivityTimeMillis = levelActivityTimeMillis;
        }

        public Long getLevelActivityDate() {
            return levelActivityDate;
        }

        public static final String LEVEL_ACTIVITY_DATE = "levelActivityDate";

        @RFAType(type=RDMFieldDictionaryConstants.LV_DATE)
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
