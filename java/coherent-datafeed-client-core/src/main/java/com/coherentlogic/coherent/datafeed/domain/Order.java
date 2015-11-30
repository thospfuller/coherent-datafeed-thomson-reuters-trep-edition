package com.coherentlogic.coherent.datafeed.domain;

import java.math.BigDecimal;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias(MarketPriceConstants.ORDER)
public class Order extends RFABean implements MarketPriceConstants {

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

    @RFAType(type=ORDER_SIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOrderSize(Long orderSize) {
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
