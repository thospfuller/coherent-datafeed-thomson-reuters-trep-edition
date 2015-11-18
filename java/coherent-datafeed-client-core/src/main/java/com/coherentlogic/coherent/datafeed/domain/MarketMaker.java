package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_MAKER;

import java.math.BigDecimal;

import javax.persistence.Entity;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDateTimeAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XStreamAlias(MARKET_MAKER)
public class MarketMaker extends RFABean implements MarketPriceConstants {

    private static final long serialVersionUID = 1L;

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

    public BigDecimal getBid() {
        return bid;
    }

    @RFAType(type=BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    @RFAType(type=ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getBidSize() {
        return bidSize;
    }

    @RFAType(type=BIDSIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBidSize(BigDecimal bidSize) {
        this.bidSize = bidSize;
    }

    public BigDecimal getAskSize() {
        return askSize;
    }

    @RFAType(type=ASKSIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAskSize(BigDecimal askSize) {
        this.askSize = askSize;
    }

    public String getMarketMakerName() {
        return marketMakerName;
    }

    @RFAType(type=MKT_MKR_NM)
    public void setMarketMakerName(String marketMakerName) {
        this.marketMakerName = marketMakerName;
    }

    public String getMarketMakerId() {
        return marketMakerId;
    }

    @RFAType(type=MMID)
    public void setMarketMakerId(String marketMakerId) {
        this.marketMakerId = marketMakerId;
    }

    public Long getAskTimeMillis() {
        return askTimeMillis;
    }

    @RFAType(type=ASK_TIM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAskTimeMillis(Long askTimeMillis) {
        this.askTimeMillis = askTimeMillis;
    }

    public Long getLastActivityTimeMillis() {
        return lastActivityTimeMillis;
    }

    @RFAType(type=TIMACT_MS)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLastActivityTimeMillis(Long lastActivityTimeMillis) {
        this.lastActivityTimeMillis = lastActivityTimeMillis;
    }

    public Long getBidTimeMillis() {
        return bidTimeMillis;
    }

    @RFAType(type=BID_TIM_MS)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setBidTimeMillis(Long bidTimeMillis) {
        this.bidTimeMillis = bidTimeMillis;
    }

    public Boolean getPrimaryMarketMaker() {
        return primaryMarketMaker;
    }

    @RFAType(type=PRIMARY_MM)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPrimaryMarketMaker(Boolean primaryMarketMaker) {
        this.primaryMarketMaker = primaryMarketMaker;
    }

    public Boolean getMarketMakerMode() {
        return marketMakerMode;
    }

    @RFAType(type=MM_MODE)
    @Adapt(using=OMMEnumAdapter.class)
    public void setMarketMakerMode(Boolean marketMakerMode) {
        this.marketMakerMode = marketMakerMode;
    }

    public Boolean getMarketMakerState() {
        return marketMakerState;
    }

    @RFAType(type=MM_STATE)
    @Adapt(using=OMMEnumAdapter.class)
    public void setMarketMakerState(Boolean marketMakerState) {
        this.marketMakerState = marketMakerState;
    }

    public Long getPriorityTimeMillis() {
        return priorityTimeMillis;
    }

    @RFAType(type=PR_TIM_MS)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setPriorityTimeMillis(Long priorityTimeMillis) {
        this.priorityTimeMillis = priorityTimeMillis;
    }

    public Long getPriortyDate() {
        return priortyDate;
    }

    @RFAType(type=PR_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setPriortyDate(Long priortyDate) {
        this.priortyDate = priortyDate;
    }
}
