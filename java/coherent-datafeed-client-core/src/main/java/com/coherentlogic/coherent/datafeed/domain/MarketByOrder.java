package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_BY_ORDER;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.coherentlogic.coherent.data.model.core.annotations.Changeable;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * A domain class containing market by order data.
 *
 * https://customers.reuters.com/a/knowledgecentre/resolution.aspx?id=1002673
 *
 * Issue
 *
 * What market depths are available in Thomson Reuters Tick History (TRTH)?
 *
 * Resolution
 * 
 * The data on Thomson Reuters Tick History (TRTH) is provided by the Reuters
 * IDN feed. The feed provides the following Market Depth data:
 * 
 * Level 1 - Top of Book
 * 
 * This data contains the best bid/offer for each instrument and provides
 * visibility of the best price. The data provided is in a similar instrument as
 * trading information is.  
 * 
 * Market by Price - Market Depth
 * 
 * The data provides a summary of the market based upon price levels at the top
 * of the market (typically five to ten levels). The data is provided in a
 * different instrument code to the Level 1, whereas in TRTH, the data is
 * supplied when the Market Depth view is selected. The Market Depth data is
 * provided in snaps at a regular interval (for example, five second snaps).
 * Market depth data does not provide visibility of every order.
 *
 * Market by Order - Full Order Book
 *
 * Data is not available on TRTH. This equates to a full un-aggregated order
 * book including every quote on the instrument.  A new product, Thomson Reuters
 * Tick History Full Order Book will provide Market by Order data for a limited
 * number of North American and European feeds.
 *
 * @see http://www.sec.gov/answers/mktord.htm
 * @see RFA Java OMM Viewer
 *
 * java -cp ..\Examples;..\Libs\rfa.jar com.reuters.rfa.example.omm.gui.viewer.Viewer -serviceName dELEKTRON_DD
 * -session mySession
 *
 * and use RIC ANZ.AX
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=MARKET_BY_ORDER)
@XStreamAlias(MARKET_BY_ORDER)
public class MarketByOrder extends StatusResponseBean
    implements RICBeanSpecification, OrderEventGenerator<MarketByOrder.Order> {

    private static final long serialVersionUID = 5047595610465925838L;

//    static final String
//        DSPLY_NAME = "DSPLY_NAME",
//        ACTIV_DATE_KEY = "ACTIV_DATE_KEY",
//        TRD_UNITS = "TRD_UNITS",
//        RECORDTYPE = "RECORDTYPE",
//        RDN_EXCHD2 = "RDN_EXCHD2",
//        PROV_SYMB = "PROV_SYMB",
//        PR_RNK_RUL = "PR_RNK_RUL",
//        OR_RNK_RUL = "OR_RNK_RUL",
//        MNEMONIC = "MNEMONIC",
//        TIMACT_MS = "TIMACT_MS",
//        CONTEXT_ID_KEY = "CONTEXT_ID_KEY",
//        DDS_DSO_ID = "DDS_DSO_ID",
//        SPS_SP_RIC = "SPS_SP_RIC",
//        BOOK_STATE = "BOOK_STATE",
//        HALT_REASN = "HALT_REASN",
//        TRD_STATUS = "TRD_STATUS",
//        HALT_RSN = "HALT_RSN",
//        ORDERS = "orders";

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

//    @XStreamAlias(ACTIV_DATE_KEY)
//    private Date activeDate;

    @XStreamAlias(RDMFieldDictionaryConstants.TRD_UNITS)
    private Integer tradingUnits; // enum

    @XStreamAlias(RDMFieldDictionaryConstants.RECORDTYPE)
    private Integer recordType; // uint32

    @XStreamAlias(CURRENCY)
    private String currency;

    /**
     * @see Appears in MarketPrice as well.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.RDN_EXCHD2)
    private String exchangeId2 = null;

    /**
     * @TODO: Appears in MarketPrice as well and MarketMaker.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.PROV_SYMB)
    private String providerSymbol = null;

//    @XStreamAlias(PR_RNK_RUL)
//    private String priceRankRule = null;
//    
//    @XStreamAlias(OR_RNK_RUL)
//    private String orderRankRule = null;

    /**
     * @see Appears in MarketPrice as well.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.MNEMONIC)
    private String exchangeId = null;

    @XStreamAlias(RDMFieldDictionaryConstants.TIMACT_MS)
    private Long lastActivityTimeMillis;

    @XStreamAlias(RDMFieldDictionaryConstants.CONTEXT_ID_KEY)
    private Integer contextId;

    @XStreamAlias(RDMFieldDictionaryConstants.DDS_DSO_ID)
    private Integer elektronDataSourceOwnerId = null;

    @XStreamAlias(RDMFieldDictionaryConstants.SPS_SP_RIC)
    private String spsSubProviderLevelRic;

    @XStreamAlias(RDMFieldDictionaryConstants.BOOK_STATE)
    private String orderBookState;

    @XStreamAlias(RDMFieldDictionaryConstants.HALT_REASN)
    private String haltReason;

    @XStreamAlias(RDMFieldDictionaryConstants.TRD_STATUS)
    private String tradingStatus;

    @XStreamAlias(RDMFieldDictionaryConstants.HALT_RSN)
    private String haltReasonCode;

    @XStreamAlias(RDMFieldDictionaryConstants.ORDERS)
    private Map<String, Order> orders;

    // Probably RMTES_STRING -- same as orderId so probably not needed.
    // private String key;

    private transient final List<OrderEventListener<MarketByOrder.Order>> orderEventListeners;

    public MarketByOrder() {
        this (new HashMap<String, Order> (), new ArrayList<OrderEventListener<MarketByOrder.Order>> ());
    }

    public MarketByOrder(Map<String, Order> orders, List<OrderEventListener<MarketByOrder.Order>> orderListeners) {
        this.orders = orders;
        this.orderEventListeners = orderListeners;
    }

    @Override
    public String getRic() {
        return ric;
    }

    public void setRic(String ric) {
        this.ric = ric;
    }

    public BigInteger getPermission() {
        return permission;
    }

    public static final String PERMISSION = "permission";

    /**
     *
     */
    @RFAType(type=RDMFieldDictionaryConstants.PROD_PERM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPermission(@Changeable(PERMISSION) BigInteger permission) {
        this.permission = permission;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static final String DISPLAY_NAME = "displayName";

    @RFAType(type=RDMFieldDictionaryConstants.DSPLY_NAME)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setDisplayName(@Changeable(DISPLAY_NAME) String displayName) {
        this.displayName = displayName;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TRD_UNITS)
    public Integer getTradingUnits() {
        return tradingUnits;
    }

    public static final String TRADING_UNITS = "tradingUnits";

    @RFAType(type=RDMFieldDictionaryConstants.TRD_UNITS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradingUnits(@Changeable(TRADING_UNITS) Integer tradingUnits) {
        this.tradingUnits = tradingUnits;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.RECORDTYPE)
    public Integer getRecordType() {
        return recordType;
    }

    public static final String RECORD_TYPE = "recordType";

    @RFAType(type=RDMFieldDictionaryConstants.RECORDTYPE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRecordType(@Changeable(RECORD_TYPE) Integer recordType) {
        this.recordType = recordType;
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

    @UsingKey(type=RDMFieldDictionaryConstants.PROV_SYMB)
    public String getProviderSymbol() {
        return providerSymbol;
    }

    public static final String PROVIDER_SYMBOL = "providerSymbol";

    @RFAType(type=RDMFieldDictionaryConstants.PROV_SYMB)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setProviderSymbol(@Changeable(PROVIDER_SYMBOL) String providerSymbol) {
        this.providerSymbol = providerSymbol;
    }

//    @UsingKey(type=PR_RNK_RUL)
//    public String getPriceRankRule() {
//        return priceRankRule;
//    }
//
//    @RFAType(type=PR_RNK_RUL)
//    @Adapt(using=OMMEnumAdapter.class)
//    public void setPriceRankRule(String priceRankRule) {
//        this.priceRankRule = priceRankRule;
//    }
//
//    @UsingKey(type=OR_RNK_RUL)
//    public String getOrderRankRule() {
//        return orderRankRule;
//    }
//
//    @RFAType(type=OR_RNK_RUL)
//    @Adapt(using=OMMEnumAdapter.class)
//    public void setOrderRankRule(String orderRankRule) {
//        this.orderRankRule = orderRankRule;
//    }
    
    @UsingKey(type=RDMFieldDictionaryConstants.MNEMONIC)
    public String getExchangeId() {
        return exchangeId;
    }

    public static final String EXCHANGE_ID = "exchangeId";

    @RFAType(type=RDMFieldDictionaryConstants.MNEMONIC)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setExchangeId(@Changeable(EXCHANGE_ID) String exchangeId) {
        this.exchangeId = exchangeId;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TIMACT_MS)
    public Long getLastActivityTimeMillis() {
        return lastActivityTimeMillis;
    }

    public static final String LAST_ACTIVITY_TIME_MILLIS = "lastActivityTimeMillis";

    @RFAType(type=RDMFieldDictionaryConstants.TIMACT_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLastActivityTimeMillis(@Changeable(LAST_ACTIVITY_TIME_MILLIS) Long lastActivityTimeMillis) {
        this.lastActivityTimeMillis = lastActivityTimeMillis;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.CONTEXT_ID_KEY)
    public Integer getContextId() {
        return contextId;
    }

    public static final String CONTEXT_ID = "contextId";

    @RFAType(type=RDMFieldDictionaryConstants.CONTEXT_ID_KEY)
    @Adapt(using=OMMNumericAdapter.class)
    public void setContextId(@Changeable(CONTEXT_ID) Integer contextId) {
        this.contextId = contextId;
    }

    /**
     * @TODO: Consider renaming this method.
     */
    @UsingKey(type=RDMFieldDictionaryConstants.DDS_DSO_ID)
    public Integer getElektronDataSourceOwnerId() {
        return elektronDataSourceOwnerId;
    }

    public static final String ELEKTRON_DATA_SOURCE_OWNER_ID = "elektronDataSourceOwnerId";

    @RFAType(type=RDMFieldDictionaryConstants.DDS_DSO_ID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setElektronDataSourceOwnerId(@Changeable(ELEKTRON_DATA_SOURCE_OWNER_ID)
        Integer elektronDataSourceOwnerId) {
        this.elektronDataSourceOwnerId = elektronDataSourceOwnerId;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.RDN_EXCHD2)
    public String getExchangeId2() {
        return exchangeId2;
    }

    public static final String EXCHANGE_ID_2 = "exchangeId2";

    @RFAType(type=RDMFieldDictionaryConstants.RDN_EXCHD2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setExchangeId2(@Changeable(EXCHANGE_ID_2) String exchangeId2) {
        this.exchangeId2 = exchangeId2;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.SPS_SP_RIC)
    public String getSpsSubProviderLevelRic() {
        return spsSubProviderLevelRic;
    }

    public static final String SPS_SUB_PROVIDER_LEVEL_RIC = "spsSubProviderLevelRic";

    @RFAType(type=RDMFieldDictionaryConstants.SPS_SP_RIC)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setSpsSubProviderLevelRic(@Changeable(SPS_SUB_PROVIDER_LEVEL_RIC) String spsSubProviderLevelRic) {
        this.spsSubProviderLevelRic = spsSubProviderLevelRic;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BOOK_STATE)
    public String getOrderBookState() {
        return orderBookState;
    }

    public static final String ORDER_BOOK_STATE = "orderBookState";

    @RFAType(type=RDMFieldDictionaryConstants.BOOK_STATE)
    @Adapt(using=OMMEnumAdapter.class)
    public void setOrderBookState(@Changeable(ORDER_BOOK_STATE) String orderBookState) {
        this.orderBookState = orderBookState;
    }

    
    @UsingKey(type=RDMFieldDictionaryConstants.HALT_REASN)
    public String getHaltReason() {
        return haltReason;
    }

    public static final String HALT_REASON = "haltReason";

    @RFAType(type=RDMFieldDictionaryConstants.HALT_REASN)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setHaltReason(@Changeable(HALT_REASON) String haltReason) {
        this.haltReason = haltReason;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TRD_STATUS)
    public String getTradingStatus() {
        return tradingStatus;
    }

    public static final String TRADING_STATUS = "tradingStatus";

    @RFAType(type=RDMFieldDictionaryConstants.TRD_STATUS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradingStatus(@Changeable(TRADING_STATUS) String tradingStatus) {
        this.tradingStatus = tradingStatus;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.HALT_RSN)
    public String getHaltReasonCode() {
        return haltReasonCode;
    }

    public static final String HALT_REASON_CODE = "haltReasonCode";

    @RFAType(type=RDMFieldDictionaryConstants.HALT_RSN)
    @Adapt(using=OMMEnumAdapter.class)
    public void setHaltReasonCode(@Changeable(HALT_REASON_CODE) String haltReasonCode) {
        this.haltReasonCode = haltReasonCode;
    }

    public void setOrders(Map<String, Order> orders) {
        this.orders = orders;
    }

    @ElementCollection
    public Map<String, Order> getOrders() {
        return orders;
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

    @Override
    public String toString() {
        return "MarketByOrder [ric=" + ric + ", permission=" + permission + ", displayName=" + displayName
            + ", tradingUnits=" + tradingUnits + ", recordType=" + recordType + ", currency=" + currency
            + ", exchangeId2=" + exchangeId2 + ", providerSymbol=" + providerSymbol + ", exchangeId=" + exchangeId
            + ", lastActivityTimeMillis=" + lastActivityTimeMillis + ", contextId=" + contextId
            + ", elektronDataSourceOwnerId=" + elektronDataSourceOwnerId + ", spsSubProviderLevelRic="
            + spsSubProviderLevelRic + ", orderBookState=" + orderBookState + ", haltReason=" + haltReason
            + ", tradingStatus=" + tradingStatus + ", haltReasonCode=" + haltReasonCode + ", orders=" + orders
            + ", orderListeners=" + orderEventListeners + "]";
    }

    /**
     * @TODO: Consider renaming this class to something like MarketByOrderOrder or
     * moving it to another package because we also have a MarketMakerOrder, which
     * has different properties and hence reuse is limited and if we're not
     * careful with the names this can get confusing.
     *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
     */
    @XStreamAlias(RDMFieldDictionaryConstants.ORDER)
    public static class Order extends RFABean {

        private static final long serialVersionUID = 3061616938490422985L;

        // RMTES_STRING
        @XStreamAlias(RDMFieldDictionaryConstants.ORDER_ID)
        private String orderId;

        // REAL64
        @XStreamAlias(RDMFieldDictionaryConstants.ORDER_PRC)
        private BigDecimal orderPrice;

        // ENUM
        @XStreamAlias(RDMFieldDictionaryConstants.ORDER_SIDE)
        private String orderSide;

        // REAL64
        @XStreamAlias(RDMFieldDictionaryConstants.ORDER_SIZE)
        private Long orderSize;

        // UINT64
        // QUOTIM_MS
        @XStreamAlias(RDMFieldDictionaryConstants.QUOTIM_MS)
        private Long quoteTimeMillis;

        // RMTES_STRING
        @XStreamAlias(RDMFieldDictionaryConstants.ORDER_TONE)
        private String orderTone;

        @XStreamAlias(RDMFieldDictionaryConstants.PR_TIM_MS)
        private Long priorityTimeMillis;

        public Order() {
        }

        @UsingKey(type=RDMFieldDictionaryConstants.ORDER_ID)
        public String getOrderId() {
            return orderId;
        }

        public static final String ORDER_ID = "orderId";

        @RFAType(type=RDMFieldDictionaryConstants.ORDER_ID)
        @Adapt(using=OMMDataBufferAdapter.class)
        public void setOrderId(@Changeable(ORDER_ID) String orderId) {
            this.orderId = orderId;
        }

        @UsingKey(type=RDMFieldDictionaryConstants.ORDER_PRC)
        public BigDecimal getOrderPrice() {
            return orderPrice;
        }

        public static final String ORDER_PRICE = "orderPrice";

        @RFAType(type=RDMFieldDictionaryConstants.ORDER_PRC)
        @Adapt(using=OMMNumericAdapter.class)
        public void setOrderPrice(@Changeable(ORDER_PRICE) BigDecimal orderPrice) {
            this.orderPrice = orderPrice;
        }

        @UsingKey(type=RDMFieldDictionaryConstants.ORDER_SIDE)
        public String getOrderSide() {
            return orderSide;
        }

        public static final String ORDER_SIDE = "orderSide";

        @RFAType(type=RDMFieldDictionaryConstants.ORDER_SIDE)
        @Adapt(using=OMMEnumAdapter.class)
        public void setOrderSide(@Changeable(ORDER_SIDE) String orderSide) {
            this.orderSide = orderSide;
        }

        @UsingKey(type=RDMFieldDictionaryConstants.ORDER_SIZE)
        public Long getOrderSize() {
            return orderSize;
        }

        public static final String ORDER_SIZE = "orderSize";

        @RFAType(type=RDMFieldDictionaryConstants.ORDER_SIZE)
        @Adapt(using=OMMNumericAdapter.class)
        public void setOrderSize(@Changeable(ORDER_SIZE) Long orderSize) {
            this.orderSize = orderSize;
        }

        @UsingKey(type=RDMFieldDictionaryConstants.QUOTIM_MS)
        public Long getQuoteTimeMillis() {
            return quoteTimeMillis;
        }

        public static final String QUOTE_TIME_MILLIS = "quoteTimeMillis";

        @RFAType(type=RDMFieldDictionaryConstants.QUOTIM_MS)
        @Adapt(using=OMMNumericAdapter.class)
        public void setQuoteTimeMillis(@Changeable(QUOTE_TIME_MILLIS) Long quoteTimeMillis) {
            this.quoteTimeMillis = quoteTimeMillis;
        }

        @UsingKey(type=RDMFieldDictionaryConstants.ORDER_TONE)
        public String getOrderTone() {
            return orderTone;
        }

        public static final String ORDER_TONE = "orderTone";

        @RFAType(type=RDMFieldDictionaryConstants.ORDER_TONE)
        @Adapt(using=OMMDataBufferAdapter.class)
        public void setOrderTone(@Changeable(ORDER_TONE) String orderTone) {
            this.orderTone = orderTone;
        }

        @UsingKey(type=RDMFieldDictionaryConstants.PR_TIM_MS)
        public Long getPriorityTimeMillis() {
            return priorityTimeMillis;
        }

        public static final String PRIORITY_TIME_MILLIS = "priorityTimeMillis";

        @RFAType(type=RDMFieldDictionaryConstants.PR_TIM_MS)
        @Adapt(using=OMMNumericAdapter.class)
        public void setPriorityTimeMillis(@Changeable(PRIORITY_TIME_MILLIS) Long priorityTimeMillis) {
            this.priorityTimeMillis = priorityTimeMillis;
        }

        @Override
        public String toString() {
            return "Order [orderId=" + orderId + ", orderPrice=" + orderPrice
                + ", orderSide=" + orderSide + ", orderSize=" + orderSize
                + ", quoteTimeMillis=" + quoteTimeMillis + ", orderTone="
                + orderTone + ", priorityTimeMillis=" + priorityTimeMillis
                + "]";
        }
    }
}
