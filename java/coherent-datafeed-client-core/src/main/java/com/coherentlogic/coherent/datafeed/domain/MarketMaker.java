package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_MAKER;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDateTimeAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
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

    public String getOfficialCode() {
        return officialCode;
    }


    public void setOfficialCode(String officialCode) {
        this.officialCode = officialCode;
    }

    
    public String getNasdStatus() {
        return nasdStatus;
    }


    public void setNasdStatus(String nasdStatus) {
        this.nasdStatus = nasdStatus;
    }


    public BigDecimal getLotSize() {
        return lotSize;
    }


    public void setLotSize(BigDecimal lotSize) {
        this.lotSize = lotSize;
    }


    public String getOfficialCodeIndicator() {
        return officialCodeIndicator;
    }


    public void setOfficialCodeIndicator(String officialCodeIndicator) {
        this.officialCodeIndicator = officialCodeIndicator;
    }


    public String getListingMarket() {
        return listingMarket;
    }


    public void setListingMarket(String listingMarket) {
        this.listingMarket = listingMarket;
    }


    public String getInstrumentClass() {
        return instrumentClass;
    }


    public void setInstrumentClass(String instrumentClass) {
        this.instrumentClass = instrumentClass;
    }


    public String getPeriodCode() {
        return periodCode;
    }


    public void setPeriodCode(String periodCode) {
        this.periodCode = periodCode;
    }


    public String getFinancialStatusIndicator() {
        return financialStatusIndicator;
    }


    public void setFinancialStatusIndicator(String financialStatusIndicator) {
        this.financialStatusIndicator = financialStatusIndicator;
    }


    public String getMarketStatusIndicator() {
        return marketStatusIndicator;
    }


    public void setMarketStatusIndicator(String marketStatusIndicator) {
        this.marketStatusIndicator = marketStatusIndicator;
    }

    public Map<String, Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "MarketMaker [officialCode=" + officialCode + ", nasdStatus="
            + nasdStatus + ", lotSize=" + lotSize
            + ", officialCodeIndicator=" + officialCodeIndicator
            + ", listingMarket=" + listingMarket + ", instrumentClass="
            + instrumentClass + ", periodCode=" + periodCode
            + ", financialStatusIndicator=" + financialStatusIndicator
            + ", marketStatusIndicator=" + marketStatusIndicator
            + ", orders=" + orders + "]";
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

        @XStreamAlias(MM_MODE)
        private Boolean marketMakerMode = null;

        @XStreamAlias(MM_STATE)
        private Boolean marketMakerState = null;

        @XStreamAlias(PR_TIM_MS)
        private Long priorityTimeMillis = null;

        @XStreamAlias(PR_DATE)
        private Long priortyDate = null;

        @UsingKey(type=BID)
        public BigDecimal getBid() {
            return bid;
        }

        @RFAType(type=BID)
        @Adapt(using=OMMNumericAdapter.class)
        public void setBid(BigDecimal bid) {
            this.bid = bid;
        }

        @UsingKey(type=ASK)
        public BigDecimal getAsk() {
            return ask;
        }

        @RFAType(type=ASK)
        @Adapt(using=OMMNumericAdapter.class)
        public void setAsk(BigDecimal ask) {
            this.ask = ask;
        }

        @UsingKey(type=BIDSIZE)
        public BigDecimal getBidSize() {
            return bidSize;
        }

        @RFAType(type=BIDSIZE)
        @Adapt(using=OMMNumericAdapter.class)
        public void setBidSize(BigDecimal bidSize) {
            this.bidSize = bidSize;
        }

        @UsingKey(type=ASKSIZE)
        public BigDecimal getAskSize() {
            return askSize;
        }

        /**
         * @TODO: SHOULD THIS BE AN INTEGER OR LONG?
         *
         * @param askSize
         */
        @RFAType(type=ASKSIZE)
        @Adapt(using=OMMNumericAdapter.class)
        public void setAskSize(BigDecimal askSize) {
            this.askSize = askSize;
        }

        @UsingKey(type=MKT_MKR_NM)
        public String getMarketMakerName() {
            return marketMakerName;
        }

        @RFAType(type=MKT_MKR_NM)
        @Adapt(using=OMMDataBufferAdapter.class)
        public void setMarketMakerName(String marketMakerName) {
            this.marketMakerName = marketMakerName;
        }

        @UsingKey(type=MMID)
        public String getMarketMakerId() {
            return marketMakerId;
        }

        @RFAType(type=MMID)
        @Adapt(using=OMMDataBufferAdapter.class)
        public void setMarketMakerId(String marketMakerId) {
            this.marketMakerId = marketMakerId;
        }

        @UsingKey(type=ASK_TIM_MS)
        public Long getAskTimeMillis() {
            return askTimeMillis;
        }

        @RFAType(type=ASK_TIM_MS)
        @Adapt(using=OMMNumericAdapter.class)
        public void setAskTimeMillis(Long askTimeMillis) {
            this.askTimeMillis = askTimeMillis;
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

        @UsingKey(type=BID_TIM_MS)
        public Long getBidTimeMillis() {
            return bidTimeMillis;
        }

        @RFAType(type=BID_TIM_MS)
        @Adapt(using=OMMNumericAdapter.class)
        public void setBidTimeMillis(Long bidTimeMillis) {
            this.bidTimeMillis = bidTimeMillis;
        }

        @UsingKey(type=PRIMARY_MM)
        public Boolean getPrimaryMarketMaker() {
            return primaryMarketMaker;
        }

        @RFAType(type=PRIMARY_MM)
        @Adapt(using=OMMEnumAdapter.class)
        public void setPrimaryMarketMaker(Boolean primaryMarketMaker) {
            this.primaryMarketMaker = primaryMarketMaker;
        }

        @UsingKey(type=MM_MODE)
        public Boolean getMarketMakerMode() {
            return marketMakerMode;
        }

        @RFAType(type=MM_MODE)
        @Adapt(using=OMMEnumAdapter.class)
        public void setMarketMakerMode(Boolean marketMakerMode) {
            this.marketMakerMode = marketMakerMode;
        }

        @UsingKey(type=MM_STATE)
        public Boolean getMarketMakerState() {
            return marketMakerState;
        }

        @RFAType(type=MM_STATE)
        @Adapt(using=OMMEnumAdapter.class)
        public void setMarketMakerState(Boolean marketMakerState) {
            this.marketMakerState = marketMakerState;
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

        @UsingKey(type=PR_DATE)
        public Long getPriortyDate() {
            return priortyDate;
        }

        @RFAType(type=PR_DATE)
        @Adapt(using=OMMDateTimeAdapter.class)
        public void setPriortyDate(Long priortyDate) {
            this.priortyDate = priortyDate;
        }

        @Override
        public String toString() {
            return "Order [bid=" + bid + ", ask=" + ask + ", bidSize="
                + bidSize + ", askSize=" + askSize + ", marketMakerName="
                + marketMakerName + ", marketMakerId=" + marketMakerId
                + ", askTimeMillis=" + askTimeMillis
                + ", lastActivityTimeMillis=" + lastActivityTimeMillis
                + ", bidTimeMillis=" + bidTimeMillis
                + ", primaryMarketMaker=" + primaryMarketMaker
                + ", marketMakerMode=" + marketMakerMode
                + ", marketMakerState=" + marketMakerState
                + ", priorityTimeMillis=" + priorityTimeMillis
                + ", priortyDate=" + priortyDate + "]";
        }
    }
}
