package com.coherentlogic.coherent.datafeed.domain;

import java.math.BigInteger;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @TODO: We need a better name for this class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractCommonProperties extends StatusResponseBean {

    /**
     * Product permissions information.
     *
     * PROD_PERM: UINT
     */
    @XStreamAlias(MarketPriceConstants.PROD_PERM)
    private BigInteger permission = null;

    @XStreamAlias(MarketPriceConstants.DSPLY_NAME)
    private String displayName;

    @XStreamAlias(MarketPriceConstants.CURRENCY)
    private String currency = null;

    @XStreamAlias(MarketPriceConstants.RECORDTYPE)
    private BigInteger recordType = null;

    @XStreamAlias(MarketPriceConstants.RDN_EXCHD2)
    private String exchangeId2 = null;

    @XStreamAlias(MarketPriceConstants.PROV_SYMB)
    private String providerSymbol = null;

    @XStreamAlias(MarketPriceConstants.MNEMONIC)
    private String exchangeId = null;

    public AbstractCommonProperties () {
    }

    @UsingKey(type=MarketPriceConstants.PROD_PERM)
    public BigInteger getPermission() {
        return permission;
    }

    /**
     *
     */
    @RFAType(type=MarketPriceConstants.PROD_PERM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPermission(BigInteger permission) {
        this.permission = permission;
    }

    @UsingKey(type=MarketPriceConstants.DSPLY_NAME)
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @todo Test this.
     */
    @RFAType(type=MarketPriceConstants.DSPLY_NAME)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @UsingKey(type=MarketPriceConstants.CURRENCY)
    public String getCurrency() {
        return currency;
    }

    @RFAType(type=MarketPriceConstants.CURRENCY)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @UsingKey(type=MarketPriceConstants.RECORDTYPE)
    public BigInteger getRecordType() {
        return recordType;
    }

    @RFAType(type=MarketPriceConstants.RECORDTYPE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRecordType(BigInteger recordType) {
        this.recordType = recordType;
    }

    @UsingKey(type=MarketPriceConstants.RDN_EXCHD2)
    public String getExchangeId2() {
        return exchangeId2;
    }

    @RFAType(type=MarketPriceConstants.RDN_EXCHD2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setExchangeId2(String exchangeId2) {
        this.exchangeId2 = exchangeId2;
    }

    @UsingKey(type=MarketPriceConstants.PROV_SYMB)
    public String getProviderSymbol() {
        return providerSymbol;
    }

    @RFAType(type=MarketPriceConstants.PROV_SYMB)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setProviderSymbol(String providerSymbol) {
        this.providerSymbol = providerSymbol;
    }

    @UsingKey(type=MarketPriceConstants.MNEMONIC)
    public String getExchangeId() {
        return exchangeId;
    }

    @RFAType(type=MarketPriceConstants.MNEMONIC)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    @Override
    public String toString() {
        return "AbstractCommonProperties [permission=" + permission + ", displayName=" + displayName + ", currency="
            + currency + ", recordType=" + recordType + ", exchangeId2=" + exchangeId2 + ", providerSymbol="
            + providerSymbol + ", exchangeId=" + exchangeId + ", toString()=" + super.toString() + "]";
    }
}
