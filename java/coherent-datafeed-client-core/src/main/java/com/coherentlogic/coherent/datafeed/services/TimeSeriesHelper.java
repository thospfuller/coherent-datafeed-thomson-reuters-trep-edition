package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import org.springframework.integration.MessageHeaders;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesHelper {

    public boolean isRicListEmpty (
        OMMItemEvent itemEvent,
        MessageHeaders headers
    ) {
        Session session = (Session) headers.get(SESSION);

        assertNotNull (SESSION, session);

        Handle handle = itemEvent.getHandle();

        TimeSeriesEntries timeSeriesEntries =
            session.getTimeSeriesEntries(handle);

        return timeSeriesEntries.isRicListEmpty();
    }
}
