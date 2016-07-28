package com.coherentlogic.coherent.datafeed.domain;

import com.coherentlogic.coherent.data.model.core.annotations.Changeable;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Bean which contains properties related to the RFA version information.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RFAVersionInfoBean {

    public static final String PRODUCT_VERSION_ID = "PRODUCT_VERSION_ID";

    @XStreamAlias(PRODUCT_VERSION_ID)
    private String productVersion = null;

    public String getProductVersion() {
        return productVersion;
    }

    public static final String PRODUCT_VERSION = "productVersion";

    public void setProductVersion(@Changeable(PRODUCT_VERSION) String productVersion) {
        this.productVersion = productVersion;
    }
}
