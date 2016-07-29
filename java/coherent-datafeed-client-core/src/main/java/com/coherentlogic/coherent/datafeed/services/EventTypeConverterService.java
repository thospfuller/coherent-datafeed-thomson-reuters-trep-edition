package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.COMPLETION_EVENT;
import static com.coherentlogic.coherent.datafeed.misc.Constants.OMM_ITEM_EVENT;
import static com.coherentlogic.coherent.datafeed.misc.Constants.REFRESH_RESP;

import java.util.HashMap;
import java.util.Map;

import com.coherentlogic.coherent.datafeed.exceptions.UnknownEventTypeException;
import com.reuters.rfa.common.Event;
import com.reuters.rfa.omm.OMMMsg.MsgType;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * A service that is used to convert the value of an event type into a string;
 * this is useful when routing via a header since we don't want to be adding
 * integer values to the Spring configuration file as it will make it
 * difficult to sort out what the integer represents without adding comments.
 *
 * Note that MsgType represents the message types for:
 * * Request:  REQUEST, CLOSE_REQUEST
 * * Response: REFRESH_RESP, UPDATE_RESP, STATUS_RESP, ACK_RESP
 *
 * @see Event
 * @see OMMItemEvent
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @deprecated We will be doing content-based routing, so this is no longer
 *  needed and hence will be deleted.
 */
public class EventTypeConverterService {

    private final Map<Integer, String> eventTypeToStringMap;

    public EventTypeConverterService() {
        this(new HashMap<Integer, String> ());

        int refreshResp = (int) MsgType.REFRESH_RESP;

        eventTypeToStringMap.put(refreshResp, REFRESH_RESP);
        eventTypeToStringMap.put(Event.COMPLETION_EVENT, COMPLETION_EVENT);
        eventTypeToStringMap.put(Event.OMM_ITEM_EVENT, OMM_ITEM_EVENT);
    }

    public EventTypeConverterService(
        Map<Integer, String> eventTypeToStringMap) {
        this.eventTypeToStringMap = eventTypeToStringMap;
    }

    /**
     * Method converts the eventType to a string and returns this value.
     */
    public String toString (int eventType) {
        String result = eventTypeToStringMap.get(eventType);

        if (result == null)
            throw new UnknownEventTypeException("The event type with the value "
                + eventType + " does not have an associate string value.");

        return result;
    }
}
