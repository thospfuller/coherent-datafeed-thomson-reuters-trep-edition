package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.RFABean;

/**
 * Factory class for creating {@link MarketPrice} objects with a null unique id
 * and null timestamp. This is used when returning market price updates as JSON
 * to R users as a null value will not appear in the output.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class NullRFABeanFactory implements Factory<RFABean> {

    public NullRFABeanFactory (){
    }

    @Override
    public RFABean getInstance() {

        RFABean rfaBean = new RFABean();

        // Intended
        rfaBean.setUniqueId(null);
        rfaBean.setTimestamp(null);

        return rfaBean;
    }
}
