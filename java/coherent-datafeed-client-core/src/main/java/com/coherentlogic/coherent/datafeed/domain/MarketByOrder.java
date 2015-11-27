package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_BY_ORDER;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDateTimeAdapter;
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

    static final String DSPLY_NAME = "DSPLY_NAME",
        ACTIV_DATE = "ACTIV_DATE",
        TRD_UNITS = "TRD_UNITS",
        RECORDTYPE = "RECORDTYPE",
//        RDN_EXCHD2 = "RDN_EXCHD2",
//        PROV_SYMB = "PROV_SYMB",
        PR_RNK_RUL = "PR_RNK_RUL",
        OR_RNK_RUL = "OR_RNK_RUL",
//        MNEMONIC = "MNEMONIC",
        TIMACT_MS = "TIMACT_MS",
        CONTEXT_ID = "CONTEXT_ID",
        DDS_DSO_ID = "DDS_DSO_ID",
        SPS_SP_RIC = "SPS_SP_RIC",
        BOOK_STATE = "BOOK_STATE",
        HALT_REASN = "HALT_REASN",
        TRD_STATUS = "TRD_STATUS",
        HALT_RSN = "HALT_RSN";



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

    @XStreamAlias(CURRENCY)
    private String currency;
    
    @XStreamAlias(ACTIV_DATE)
    private String activeDate;

    @XStreamAlias(TRD_UNITS)
    private Integer tradingUnits; // enum

    @XStreamAlias(RECORDTYPE)
    private Integer recordType; // uint32

    /**
     * @see Appears in MarketPrice as well.
     */
    @XStreamAlias(RDN_EXCHD2)
    private String exchangeId2 = null;

    /**
     * @see Appears in MarketPrice as well.
     */
    @XStreamAlias(PROV_SYMB)
    private String providerSymbol = null;

    @XStreamAlias(PR_RNK_RUL)
    private String priceRankRule = null;
    
    @XStreamAlias(OR_RNK_RUL)
    private String orderRankRule = null;

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
    
    // Probably RMTES_STRING -- same as orderId so probably not needed.
    // private String key;

    // RMTES_STRING
    @XStreamAlias(ORDER_ID)
    private String orderId;

    // REAL64
    @XStreamAlias(ORDER_PRC)
    private BigDecimal orderPrice;

    // ENUM
    @XStreamAlias(ORDER_SIDE)
    private Object orderSide;

    // REAL64
    @XStreamAlias(ORDER_SIZE)
    private Long orderSize;

    // UINT64
    // QUOTIM_MS
    @XStreamAlias(QUOTIM_MS)
    private Long quoteTimeMillis;

    // RMTES_STRING
    @XStreamAlias(ORDER_TONE)
    String orderTone;

    @XStreamAlias(PR_TIM_MS)
    private Long priorityTimeMillis;

    public BigInteger getPermission() {
        return permission;
    }

    /**
     *
     */
    @RFAType(type=PROD_PERM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPermission(BigInteger permission) {
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

    @RFAType(type=PROV_SYMB)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setProviderSymbol(String providerSymbol) {
        this.providerSymbol = providerSymbol;
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

    @RFAType(type=PR_RNK_RUL)
    @Adapt(using=OMMEnumAdapter.class)
    public void setOrderRankRule(String orderRankRule) {
        this.orderRankRule = orderRankRule;
    }
    
    @UsingKey(type=MNEMONIC)
    public String getExchangeId() {
        return exchangeId;
    }

    @RFAType(type=MNEMONIC)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    @UsingKey(type=TIMACT_MS)
    public Long getLastActivityTimeMillis() {
        return lastActivityTimeMillis;
    }

    @RFAType(type=TIMACT_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLastActivityTimeMillis(Long lastActivityTimeMillis) {
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

	// -----

	public String getOrderId() {
        return orderId;
    }

	@RFAType(type=ORDER_ID)
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    @RFAType(type=ORDER_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Object getOrderSide() {
        return orderSide;
    }

    @RFAType(type=ORDER_SIDE)
    @Adapt(using=OMMEnumAdapter.class)
    public void setOrderSide(Object orderSide) {
        this.orderSide = orderSide;
    }

    public Long getOrderSize() {
        return orderSize;
    }

    @RFAType(type=ORDER_SIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOrderSize(Long orderSize) {
        this.orderSize = orderSize;
    }

    public Long getQuoteTimeMillis() {
        return quoteTimeMillis;
    }

    @RFAType(type=QUOTIM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setQuoteTimeMillis(Long quoteTimeMillis) {
        this.quoteTimeMillis = quoteTimeMillis;
    }

    public String getOrderTone() {
        return orderTone;
    }

    @RFAType(type=ORDER_TONE)
    public void setOrderTone(String orderTone) {
        this.orderTone = orderTone;
    }

    public Long getPriorityTimeMillis() {
        return priorityTimeMillis;
    }

    @RFAType(type=PR_TIM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriorityTimeMillis(Long priorityTimeMillis) {
        this.priorityTimeMillis = priorityTimeMillis;
    }
}
