package com.coherentlogic.coherent.datafeed.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Event;

/**
 * Class is used to determine if all {@link TS1DefDbEntry} instances in the
 * session have their loaded flag set to true.
 *
 * @todo Move this class into another package as it's easy to think it belongs
 *  to the other module.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefDbHelper {

    private static final Logger log =
        LoggerFactory.getLogger(TS1DefDbHelper.class);

    public static final String BEAN_NAME = "ts1DefDbHelper";

    public boolean allTS1DefDbEntriesAreLoaded (Event event, Session session) {
        log.info ("allTS1DefDbEntriesAreLoaded: method begins; event: " + event
            + ", session: " + session);

        boolean result = session.allTS1DefDbEntriesAreLoaded();

        log.info ("allTS1DefDbEntriesAreLoaded: method ends; result: " +
            result);

        return result;
    }
}
