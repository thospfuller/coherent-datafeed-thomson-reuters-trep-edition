package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.Map;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.adapters.MarketMakerAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * A class which is used to process {@link MarketMaker} messages.
 *
 * @deprecated This class is being removed in favor of AbstractRefreshRFABeanMessageProcessor.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractMarketMakerMessageProcessor<T extends RFABean> implements
    MessageProcessorSpecification<OMMItemEvent, T> {

//    /**
//     * Method returns the {@link Session} which is taken from the message
//     * headers.
//     *
//     * @throws NullPointerRuntimeException when the session is null.
//     */
//    public Session getSession (Message<OMMItemEvent> message) {
//        MessageHeaders headers = message.getHeaders();
//
//        Session session = (Session) headers.get(SESSION);
//
//        assertNotNull(SESSION, session);
//
//        return session;
//    }
}

