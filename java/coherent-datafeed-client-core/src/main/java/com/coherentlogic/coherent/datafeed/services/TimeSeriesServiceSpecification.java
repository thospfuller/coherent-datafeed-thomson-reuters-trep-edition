package com.coherentlogic.coherent.datafeed.services;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.common.Handle;

/**
 * The specification that defines the methods that market price services must
 * provide.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface TimeSeriesServiceSpecification {

    /**
     * Method queries the time series service passing the loginHandle, Reuters
     * Instrument Code (RIC), for the specified period.
     */
    Handle queryTimeSeriesFor(
        String serviceName,
        SessionBean sessionBean,
        String ric
    );
}
