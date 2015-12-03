package com.coherentlogic.coherent.datafeed.domain;

import java.util.Date;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDateTimeAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public abstract class AbstractAdvancedCommonProperties
    extends AbstractCommonProperties implements MarketPriceConstants {

    @XStreamAlias(ACTIV_DATE)
    private Date activeDate;

    @XStreamAlias(PR_RNK_RUL)
    private String priceRankRule = null;

    @XStreamAlias(OR_RNK_RUL)
    private String orderRankRule = null;

    public AbstractAdvancedCommonProperties() {
    }

    @UsingKey(type=ACTIV_DATE)
    public Date getActiveDate() {
        return activeDate;
    }

    @RFAType(type=ACTIV_DATE)
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

    @Override
    public String toString() {
        return "AbstractAdvancedCommonProperties [activeDate=" + activeDate
            + ", priceRankRule=" + priceRankRule + ", orderRankRule="
            + orderRankRule + "]";
    }
}
