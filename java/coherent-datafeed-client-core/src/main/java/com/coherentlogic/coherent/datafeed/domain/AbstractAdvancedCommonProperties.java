package com.coherentlogic.coherent.datafeed.domain;

import java.util.Date;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDateTimeAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractAdvancedCommonProperties
    extends AbstractCommonProperties implements RDMFieldDictionaryConstants {

    @XStreamAlias(ACTIV_DATE_KEY)
    private Date activeDate;

    @XStreamAlias(PR_RNK_RUL)
    private String priceRankRule = null;

    @XStreamAlias(OR_RNK_RUL)
    private String orderRankRule = null;

    public AbstractAdvancedCommonProperties() {
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
}
