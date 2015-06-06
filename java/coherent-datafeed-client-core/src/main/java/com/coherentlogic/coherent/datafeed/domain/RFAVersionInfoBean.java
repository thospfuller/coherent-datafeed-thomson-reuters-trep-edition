package com.coherentlogic.coherent.datafeed.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Bean which contains properties related to the RFA version information.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RFAVersionInfoBean {

    public static final String PRODUCT_VERSION = "PRODUCT_VERSION";

    @XStreamAlias(PRODUCT_VERSION)
    private String productVersion = null;

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }
}
