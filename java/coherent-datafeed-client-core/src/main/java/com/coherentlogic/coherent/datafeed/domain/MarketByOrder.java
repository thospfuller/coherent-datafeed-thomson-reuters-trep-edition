package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
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
