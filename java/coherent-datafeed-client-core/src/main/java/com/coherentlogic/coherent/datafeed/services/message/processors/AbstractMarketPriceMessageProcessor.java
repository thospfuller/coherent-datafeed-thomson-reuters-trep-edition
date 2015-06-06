package com.coherentlogic.coherent.datafeed.services.message.processors;

import org.springframework.integration.Message;
import org.springframework.integration.MessageHeaders;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * A class which is used to process {@link MarketPrice} messages.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractMarketPriceMessageProcessor implements
        MessageProcessorSpecification<OMMItemEvent, MarketPrice> {

    /**
     * Method returns the {@link Session} which is taken from the message
     * headers.
     *
     * @throws NullPointerRuntimeException when the session is null.
     */
    public Session getSession (Message<OMMItemEvent> message) {
        MessageHeaders headers = message.getHeaders();

        Session session = (Session) headers.get(SESSION);

        assertNotNull(SESSION, session);

        return session;
    }
}

