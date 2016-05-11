package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_BY_ORDER;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.Changeable;
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
 * java -cp ..\Examples;..\Libs\rfa.jar com.reuters.rfa.example.omm.gui.viewer.Viewer -serviceName dELEKTRON_DD -session mySession
 *
 * and use RIC ANZ.AX
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=MARKET_BY_ORDER)
@XStreamAlias(MARKET_BY_ORDER)
public class MarketByOrder extends RFABean implements MarketPriceConstants {

    private static final long serialVersionUID = 1L;

//    static final String
//        DSPLY_NAME = "DSPLY_NAME",
//        ACTIV_DATE = "ACTIV_DATE",
//        TRD_UNITS = "TRD_UNITS",
//        RECORDTYPE = "RECORDTYPE",
//        RDN_EXCHD2 = "RDN_EXCHD2",
//        PROV_SYMB = "PROV_SYMB",
//        PR_RNK_RUL = "PR_RNK_RUL",
//        OR_RNK_RUL = "OR_RNK_RUL",
//        MNEMONIC = "MNEMONIC",
//        TIMACT_MS = "TIMACT_MS",
//        CONTEXT_ID = "CONTEXT_ID",
//        DDS_DSO_ID = "DDS_DSO_ID",
//        SPS_SP_RIC = "SPS_SP_RIC",
//        BOOK_STATE = "BOOK_STATE",
//        HALT_REASN = "HALT_REASN",
//        TRD_STATUS = "TRD_STATUS",
//        HALT_RSN = "HALT_RSN",
//        ORDERS = "orders";

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
    @XStreamAlias(PROD_PERM)
    private BigInteger permission = null;

    @XStreamAlias(DSPLY_NAME)
    private String displayName;

//    @XStreamAlias(ACTIV_DATE)
//    private Date activeDate;

    @XStreamAlias(TRD_UNITS)
    private Integer tradingUnits; // enum

    @XStreamAlias(RECORDTYPE)
    private Integer recordType; // uint32

    @XStreamAlias(CURRENCY)
    private String currency;

    /**
     * @see Appears in MarketPrice as well.
     */
    @XStreamAlias(RDN_EXCHD2)
    private String exchangeId2 = null;

    /**
     * @TODO: Appears in MarketPrice as well and MarketMaker.
     */
    @XStreamAlias(PROV_SYMB)
    private String providerSymbol = null;

//    @XStreamAlias(PR_RNK_RUL)
//    private String priceRankRule = null;
//    
//    @XStreamAlias(OR_RNK_RUL)
//    private String orderRankRule = null;

    /**
     * @see Appears in MarketPrice as well.
     */
    @XStreamAlias(MNEMONIC)
    private String exchangeId = null;

    @XStreamAlias(TIMACT_MS)
    private Long lastActivityTimeMillis;

    @XStreamAlias(CONTEXT_ID)
    private Integer contextId;

    @XStreamAlias(DDS_DSO_ID)
    private Integer elektronDataSourceOwnerId = null;

    @XStreamAlias(SPS_SP_RIC)
    private String spsSubProviderLevelRic;

    @XStreamAlias(BOOK_STATE)
    private String orderBookState;

    @XStreamAlias(HALT_REASN)
    private String haltReason;

    @XStreamAlias(TRD_STATUS)
    private String tradingStatus;

    @XStreamAlias(HALT_RSN)
    private String haltReasonCode;

    @XStreamAlias(ORDERS)
    private final Map<String, Order> orders;
    
    // Probably RMTES_STRING -- same as orderId so probably not needed.
    // private String key;

    public MarketByOrder() {
        this (new HashMap<String, Order> ());
    }

    public MarketByOrder(Map<String, Order> orders) {
        this.orders = orders;
    }
    
    public BigInteger getPermission() {
        return permission;
    }

    public static final String PERMISSION = "permission";

    /**
     *
     */
    @RFAType(type=PROD_PERM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPermission(@Changeable(PERMISSION) BigInteger permission) {
        this.permission = permission;
    }

    public String getDisplayName() {
        return displayName;
    }

    @RFAType(type=DSPLY_NAME)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @UsingKey(type=TRD_UNITS)
    public Integer getTradingUnits() {
        return tradingUnits;
    }

    @RFAType(type=TRD_UNITS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradingUnits(Integer tradingUnits) {
        this.tradingUnits = tradingUnits;
    }

    @UsingKey(type=RECORDTYPE)
    public Integer getRecordType() {
        return recordType;
    }

    @RFAType(type=RECORDTYPE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    @UsingKey(type=CURRENCY)
    public String getCurrency() {
        return currency;
    }

    @RFAType(type=CURRENCY)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @UsingKey(type=PROV_SYMB)
    public String getProviderSymbol() {
        return providerSymbol;
    }

    public static final String PROVIDER_SYMBOL = "providerSymbol";

    @RFAType(type=PROV_SYMB)
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
    
    @UsingKey(type=MNEMONIC)
    public String getExchangeId() {
        return exchangeId;
    }

    public static final String EXCHANGE_ID = "exchangeId";

    @RFAType(type=MNEMONIC)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setExchangeId(@Changeable(EXCHANGE_ID) String exchangeId) {
        this.exchangeId = exchangeId;
    }

    @UsingKey(type=TIMACT_MS)
    public Long getLastActivityTimeMillis() {
        return lastActivityTimeMillis;
    }

    public static final String LAST_ACTIVITY_TIME_MILLIS = "lastActivityTimeMillis";

    @RFAType(type=TIMACT_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLastActivityTimeMillis(@Changeable(LAST_ACTIVITY_TIME_MILLIS) Long lastActivityTimeMillis) {
        this.lastActivityTimeMillis = lastActivityTimeMillis;
    }

    @UsingKey(type=CONTEXT_ID)
    public Integer getContextId() {
        return contextId;
    }

    @RFAType(type=CONTEXT_ID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setContextId(Integer contextId) {
        this.contextId = contextId;
    }

    /**
     * @TODO: Consider renaming this method.
     */
    @UsingKey(type=DDS_DSO_ID)
    public Integer getElektronDataSourceOwnerId() {
        return elektronDataSourceOwnerId;
    }

    @RFAType(type=DDS_DSO_ID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setElektronDataSourceOwnerId(Integer dataSourceOwnerId) {
        this.elektronDataSourceOwnerId = dataSourceOwnerId;
    }

    @UsingKey(type=RDN_EXCHD2)
    public String getExchangeId2() {
        return exchangeId2;
    }

    @RFAType(type=RDN_EXCHD2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setExchangeId2(String exchangeId2) {
        this.exchangeId2 = exchangeId2;
    }

    @UsingKey(type=SPS_SP_RIC)
    public String getSpsSubProviderLevelRic() {
        return spsSubProviderLevelRic;
    }

    @RFAType(type=SPS_SP_RIC)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setSpsSubProviderLevelRic(String spsSubProviderLevelRic) {
        this.spsSubProviderLevelRic = spsSubProviderLevelRic;
    }

    @UsingKey(type=BOOK_STATE)
    public String getOrderBookState() {
        return orderBookState;
    }

    @RFAType(type=BOOK_STATE)
    @Adapt(using=OMMEnumAdapter.class)
    public void setOrderBookState(String orderBookState) {
        this.orderBookState = orderBookState;
    }

    
    @UsingKey(type=HALT_REASN)
    public String getHaltReason() {
        return haltReason;
    }

    @RFAType(type=HALT_REASN)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setHaltReason(String haltReason) {
        this.haltReason = haltReason;
    }

    @UsingKey(type=TRD_STATUS)
    public String getTradingStatus() {
        return tradingStatus;
    }

    @RFAType(type=TRD_STATUS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradingStatus(String tradingStatus) {
        this.tradingStatus = tradingStatus;
    }

    @UsingKey(type=HALT_RSN)
    public String getHaltReasonCode() {
        return haltReasonCode;
    }

    @RFAType(type=HALT_RSN)
    @Adapt(using=OMMEnumAdapter.class)
    public void setHaltReasonCode(String haltReasonCode) {
        this.haltReasonCode = haltReasonCode;
    }

    public Map<String, Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "MarketByOrder [permission=" + permission + ", displayName=" + displayName + ", tradingUnits="
            + tradingUnits + ", recordType=" + recordType + ", currency=" + currency + ", exchangeId2="
            + exchangeId2 + ", providerSymbol=" + providerSymbol + ", exchangeId=" + exchangeId
            + ", lastActivityTimeMillis=" + lastActivityTimeMillis + ", contextId=" + contextId
            + ", elektronDataSourceOwnerId=" + elektronDataSourceOwnerId + ", spsSubProviderLevelRic="
            + spsSubProviderLevelRic + ", orderBookState=" + orderBookState + ", haltReason=" + haltReason
            + ", tradingStatus=" + tradingStatus + ", haltReasonCode=" + haltReasonCode + ", orders=" + orders
            + "]";
    }

    /**
     * @TODO: Consider renaming this class to something like MarketByOrderOrder or
     * moving it to another package because we also have a MarketMakerOrder, which
     * has different properties and hence reuse is limited and if we're not
     * careful with the names this can get confusing.
     *
     *
     * @author <a href="mailto:support@coherentlogic.com">Support</a>
     *
     */
    @XStreamAlias(MarketPriceConstants.ORDER)
    public static class Order extends RFABean implements MarketPriceConstants {

        // RMTES_STRING
        @XStreamAlias(ORDER_ID)
        private String orderId;

        // REAL64
        @XStreamAlias(ORDER_PRC)
        private BigDecimal orderPrice;

        // ENUM
        @XStreamAlias(ORDER_SIDE)
        private String orderSide;

        // REAL64
        @XStreamAlias(ORDER_SIZE)
        private Long orderSize;

        // UINT64
        // QUOTIM_MS
        @XStreamAlias(QUOTIM_MS)
        private Long quoteTimeMillis;

        // RMTES_STRING
        @XStreamAlias(ORDER_TONE)
        private String orderTone;

        @XStreamAlias(PR_TIM_MS)
        private Long priorityTimeMillis;

        public Order() {
        }

        @UsingKey(type=ORDER_ID)
        public String getOrderId() {
            return orderId;
        }

        @RFAType(type=ORDER_ID)
        @Adapt(using=OMMDataBufferAdapter.class)
        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        @UsingKey(type=ORDER_PRC)
        public BigDecimal getOrderPrice() {
            return orderPrice;
        }

        @RFAType(type=ORDER_PRC)
        @Adapt(using=OMMNumericAdapter.class)
        public void setOrderPrice(BigDecimal orderPrice) {
            this.orderPrice = orderPrice;
        }

        @UsingKey(type=ORDER_SIDE)
        public String getOrderSide() {
            return orderSide;
        }

        @RFAType(type=ORDER_SIDE)
        @Adapt(using=OMMEnumAdapter.class)
        public void setOrderSide(String orderSide) {
            this.orderSide = orderSide;
        }

        @UsingKey(type=ORDER_SIZE)
        public Long getOrderSize() {
            return orderSize;
        }

        public static final String ORDER_SIZE = "orderSize";

        @RFAType(type=ORDER_SIZE)
        @Adapt(using=OMMNumericAdapter.class)
        public void setOrderSize(@Changeable(ORDER_SIZE) Long orderSize) {
            this.orderSize = orderSize;
        }

        @UsingKey(type=QUOTIM_MS)
        public Long getQuoteTimeMillis() {
            return quoteTimeMillis;
        }

        @RFAType(type=QUOTIM_MS)
        @Adapt(using=OMMNumericAdapter.class)
        public void setQuoteTimeMillis(Long quoteTimeMillis) {
            this.quoteTimeMillis = quoteTimeMillis;
        }

        @UsingKey(type=ORDER_TONE)
        public String getOrderTone() {
            return orderTone;
        }

        @RFAType(type=ORDER_TONE)
        @Adapt(using=OMMDataBufferAdapter.class)
        public void setOrderTone(String orderTone) {
            this.orderTone = orderTone;
        }

        @UsingKey(type=PR_TIM_MS)
        public Long getPriorityTimeMillis() {
            return priorityTimeMillis;
        }

        @RFAType(type=PR_TIM_MS)
        @Adapt(using=OMMNumericAdapter.class)
        public void setPriorityTimeMillis(Long priorityTimeMillis) {
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
