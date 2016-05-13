package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_MAKER;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDateTimeAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.Changeable;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=MARKET_MAKER)
@XStreamAlias(MARKET_MAKER)
public class MarketMaker extends AbstractAdvancedCommonProperties
    implements MarketPriceConstants {

    @XStreamAlias(OFFCL_CODE)
    private String officialCode;

    /**
     * NASDSTATUS / enum
     */
    @XStreamAlias(NASDSTATUS)
    private String nasdStatus;

    /**
     * REAL64
     */
    @XStreamAlias(LOT_SIZE_A)
    private BigDecimal lotSize;

    /**
     * Enum
     */
    @XStreamAlias(OFF_CD_IND)
    private String officialCodeIndicator;

    /**
     * RMTES_STRING
     */
    @XStreamAlias(LIST_MKT)
    private String listingMarket = null;

    /**
     * RMTES_STRING
     */
    @XStreamAlias(CLASS_CODE)
    private String instrumentClass = null;

    /**
     * RMTES_STRING
     */
    @XStreamAlias(PERIOD_CDE)
    private String periodCode = null;

    @XStreamAlias(FIN_STATUS)
    private String financialStatusIndicator = null;

    @XStreamAlias(MKT_STATUS)
    private String marketStatusIndicator = null;

    @XStreamAlias(ORDERS)
    private final Map<String, Order> orders;

    public MarketMaker () {
        this (new HashMap<String, Order> ());
    }

    public MarketMaker (Map<String, Order> orders) {
        this.orders = orders;
    }

    @UsingKey(type=MarketPriceConstants.OFFCL_CODE)
    public String getOfficialCode() {
        return officialCode;
    }

    public static final String OFFICIAL_CODE = "officialCode";

    @RFAType(type=MarketPriceConstants.OFFCL_CODE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOfficialCode(@Changeable(OFFICIAL_CODE) String officialCode) {
        this.officialCode = officialCode;
    }

    @UsingKey(type=MarketPriceConstants.NASDSTATUS)
    public String getNasdStatus() {
        return nasdStatus;
    }

    public static final String NASD_STATUS = "nasdStatus";

    @RFAType(type=MarketPriceConstants.NASDSTATUS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setNasdStatus(@Changeable(NASD_STATUS) String nasdStatus) {
        this.nasdStatus = nasdStatus;
    }

    @UsingKey(type=MarketPriceConstants.LOT_SIZE_A)
    public BigDecimal getLotSize() {
        return lotSize;
    }

    public static final String LOT_SIZE = "lotSize";

    /**
     * todo: According to the TR example this is, in fact LOT_SIZE_A, so check that other methods that we have use
     *       the name setLotSize and NOT setLotSizeA.
     */
    @RFAType(type=MarketPriceConstants.LOT_SIZE_A)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLotSize(@Changeable(LOT_SIZE) BigDecimal lotSize) {
        this.lotSize = lotSize;
    }

    @UsingKey(type=MarketPriceConstants.OFF_CD_IND)
    public String getOfficialCodeIndicator() {
        return officialCodeIndicator;
    }

    public static final String OFFICIAL_CODE_INDICATOR = "officialCodeIndicator";

    @RFAType(type=MarketPriceConstants.OFF_CD_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setOfficialCodeIndicator(@Changeable(OFFICIAL_CODE_INDICATOR) String officialCodeIndicator) {
        this.officialCodeIndicator = officialCodeIndicator;
    }

    @UsingKey(type=MarketPriceConstants.LIST_MKT)
    public String getListingMarket() {
        return listingMarket;
    }

    public static final String LISTING_MARKET = "listingMarket";

    @RFAType(type=MarketPriceConstants.LIST_MKT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setListingMarket(@Changeable(LISTING_MARKET) String listingMarket) {
        this.listingMarket = listingMarket;
    }

    public String getInstrumentClass() {
        return instrumentClass;
    }

    public static final String INSTRUMENT_CLASS = "instrumentClass";

    public void setInstrumentClass(@Changeable(INSTRUMENT_CLASS) String instrumentClass) {
        this.instrumentClass = instrumentClass;
    }

    public String getPeriodCode() {
        return periodCode;
    }

    public static final String PERIOD_CODE = "periodCode";

    public void setPeriodCode(@Changeable(PERIOD_CODE) String periodCode) {
        this.periodCode = periodCode;
    }

    public String getFinancialStatusIndicator() {
        return financialStatusIndicator;
    }

    public static final String FINANCIAL_STATUS_INDICATOR = "financialStatusIndicator";

    public void setFinancialStatusIndicator(@Changeable(FINANCIAL_STATUS_INDICATOR) String financialStatusIndicator) {
        this.financialStatusIndicator = financialStatusIndicator;
    }


    public String getMarketStatusIndicator() {
        return marketStatusIndicator;
    }

    public static final String MARKET_STATUS_INDICATOR = "marketStatusIndicator";

    public void setMarketStatusIndicator(@Changeable(MARKET_STATUS_INDICATOR) String marketStatusIndicator) {
        this.marketStatusIndicator = marketStatusIndicator;
    }

    public Map<String, Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "MarketMaker [officialCode=" + officialCode + ", nasdStatus=" + nasdStatus + ", lotSize=" + lotSize
            + ", officialCodeIndicator=" + officialCodeIndicator + ", listingMarket=" + listingMarket
            + ", instrumentClass=" + instrumentClass + ", periodCode=" + periodCode + ", financialStatusIndicator="
            + financialStatusIndicator + ", marketStatusIndicator=" + marketStatusIndicator + ", orders=" + orders
            + "]";
    }

    /**
     * 
     *
     * @author <a href="mailto:support@coherentlogic.com">Support</a>
     */
    public static class Order extends RFABean {

        @XStreamAlias(BID)
        private BigDecimal bid = null;

        @XStreamAlias(ASK)
        private BigDecimal ask = null;

        @XStreamAlias(BIDSIZE)
        private BigDecimal bidSize = null;

        @XStreamAlias(ASKSIZE)
        private BigDecimal askSize = null;

        @XStreamAlias(MKT_MKR_NM)
        private String marketMakerName = null;

        @XStreamAlias(MMID)
        private String marketMakerId = null;

        @XStreamAlias(ASK_TIM_MS)
        private Long askTimeMillis = null;

        @XStreamAlias(TIMACT_MS)
        private Long lastActivityTimeMillis = null;

        @XStreamAlias(BID_TIM_MS)
        private Long bidTimeMillis = null;

        @XStreamAlias(PRIMARY_MM)
        private Boolean primaryMarketMaker = null;

        /**
         * TODO: Make this an enum.
         */
        @XStreamAlias(MM_MODE)
        private String marketMakerMode = null;

        /**
         * TODO: Make this an enum.
         */
        @XStreamAlias(MM_STATE)
        private String marketMakerState = null;

        @XStreamAlias(PR_TIM_MS)
        private Long priorityTimeMillis = null;

        @XStreamAlias(PR_DATE)
        private Long priortyDate = null;

        @UsingKey(type=BID)
        public BigDecimal getBid() {
            return bid;
        }

        @RFAType(type=MarketPriceConstants.BID)
        @Adapt(using=OMMNumericAdapter.class)
        public void setBid(@Changeable(MarketPrice.BID) BigDecimal bid) {
            this.bid = bid;
        }

        @UsingKey(type=ASK)
        public BigDecimal getAsk() {
            return ask;
        }

        public static final String ASK = "ask";

        @RFAType(type=ASK)
        @Adapt(using=OMMNumericAdapter.class)
        public void setAsk(@Changeable(ASK) BigDecimal ask) {
            this.ask = ask;
        }

        @UsingKey(type=BIDSIZE)
        public BigDecimal getBidSize() {
            return bidSize;
        }

        public static final String BID_SIZE = "bidSize";

        @RFAType(type=BIDSIZE)
        @Adapt(using=OMMNumericAdapter.class)
        public void setBidSize(@Changeable(BID_SIZE) BigDecimal bidSize) {
            this.bidSize = bidSize;
        }

        @UsingKey(type=ASKSIZE)
        public BigDecimal getAskSize() {
            return askSize;
        }

        public static final String ASK_SIZE = "askSize";

        /**
         * @TODO: SHOULD THIS BE AN INTEGER OR LONG?
         *
         * @param askSize
         */
        @RFAType(type=ASKSIZE)
        @Adapt(using=OMMNumericAdapter.class)
        public void setAskSize(@Changeable(ASK_SIZE) BigDecimal askSize) {
            this.askSize = askSize;
        }

        @UsingKey(type=MKT_MKR_NM)
        public String getMarketMakerName() {
            return marketMakerName;
        }

        public static final String MARKET_MAKER_NAME = "marketMakerName";

        @RFAType(type=MKT_MKR_NM)
        @Adapt(using=OMMDataBufferAdapter.class)
        public void setMarketMakerName(@Changeable(MARKET_MAKER_NAME) String marketMakerName) {
            this.marketMakerName = marketMakerName;
        }

        @UsingKey(type=MMID)
        public String getMarketMakerId() {
            return marketMakerId;
        }

        public static final String MARKET_MAKER_ID = "marketMakerId";

        @RFAType(type=MMID)
        @Adapt(using=OMMDataBufferAdapter.class)
        public void setMarketMakerId(@Changeable(MARKET_MAKER_ID) String marketMakerId) {
            this.marketMakerId = marketMakerId;
        }

        @UsingKey(type=ASK_TIM_MS)
        public Long getAskTimeMillis() {
            return askTimeMillis;
        }

        public static final String ASK_TIME_MILLIS = "askTimeMillis";

        @RFAType(type=ASK_TIM_MS)
        @Adapt(using=OMMNumericAdapter.class)
        public void setAskTimeMillis(@Changeable(ASK_TIME_MILLIS) Long askTimeMillis) {
            this.askTimeMillis = askTimeMillis;
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

        @UsingKey(type=BID_TIM_MS)
        public Long getBidTimeMillis() {
            return bidTimeMillis;
        }

        public static final String BID_TIME_MILLIS = "bidTimeMillis";

        @RFAType(type=BID_TIM_MS)
        @Adapt(using=OMMNumericAdapter.class)
        public void setBidTimeMillis(@Changeable(BID_TIME_MILLIS) Long bidTimeMillis) {
            this.bidTimeMillis = bidTimeMillis;
        }

        @UsingKey(type=PRIMARY_MM)
        public Boolean getPrimaryMarketMaker() {
            return primaryMarketMaker;
        }

        public static final String PRIMARY_MARKET_MAKER = "primaryMarketMaker";

        @RFAType(type=PRIMARY_MM)
        @Adapt(using=OMMEnumAdapter.class)
        public void setPrimaryMarketMaker(@Changeable(PRIMARY_MARKET_MAKER) Boolean primaryMarketMaker) {
            this.primaryMarketMaker = primaryMarketMaker;
        }

        @UsingKey(type=MM_MODE)
        public String getMarketMakerMode() {
            return marketMakerMode;
        }

        public static final String MARKET_MAKER_MODE = "marketMakerMode";

        @RFAType(type=MM_MODE)
        @Adapt(using=OMMEnumAdapter.class)
        public void setMarketMakerMode(@Changeable(MARKET_MAKER_MODE) String marketMakerMode) {
            this.marketMakerMode = marketMakerMode;
        }

        @UsingKey(type=MM_STATE)
        public String getMarketMakerState() {
            return marketMakerState;
        }

        public static final String MARKET_SHARE_STATE = "marketMakerState";

        @RFAType(type=MM_STATE)
        @Adapt(using=OMMEnumAdapter.class)
        public void setMarketMakerState(@Changeable(MARKET_SHARE_STATE) String marketMakerState) {
            this.marketMakerState = marketMakerState;
        }

        @UsingKey(type=PR_TIM_MS)
        public Long getPriorityTimeMillis() {
            return priorityTimeMillis;
        }

        public static final String PRIORITY_TIME_MILLIS = "priorityTimeMillis";

        @RFAType(type=PR_TIM_MS)
        @Adapt(using=OMMNumericAdapter.class)
        public void setPriorityTimeMillis(@Changeable(PRIORITY_TIME_MILLIS) Long priorityTimeMillis) {
            this.priorityTimeMillis = priorityTimeMillis;
        }

        @UsingKey(type=PR_DATE)
        public Long getPriortyDate() {
            return priortyDate;
        }

        public static final String PRIORITY_DATE = "priortyDate";

        @RFAType(type=PR_DATE)
        @Adapt(using=OMMDateTimeAdapter.class)
        public void setPriortyDate(@Changeable(PRIORITY_DATE) Long priortyDate) {
            this.priortyDate = priortyDate;
        }

        @Override
        public String toString() {
            return "Order [bid=" + bid + ", ask=" + ask + ", bidSize=" + bidSize + ", askSize=" + askSize
                + ", marketMakerName=" + marketMakerName + ", marketMakerId=" + marketMakerId + ", askTimeMillis="
                + askTimeMillis + ", lastActivityTimeMillis=" + lastActivityTimeMillis + ", bidTimeMillis="
                + bidTimeMillis + ", primaryMarketMaker=" + primaryMarketMaker + ", marketMakerMode="
                + marketMakerMode + ", marketMakerState=" + marketMakerState + ", priorityTimeMillis="
                + priorityTimeMillis + ", priortyDate=" + priortyDate + "]";
        }
    }
}
