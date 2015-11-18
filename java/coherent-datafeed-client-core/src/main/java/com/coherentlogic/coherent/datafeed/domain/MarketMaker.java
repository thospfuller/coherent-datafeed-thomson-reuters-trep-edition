package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_MAKER;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;

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
}
