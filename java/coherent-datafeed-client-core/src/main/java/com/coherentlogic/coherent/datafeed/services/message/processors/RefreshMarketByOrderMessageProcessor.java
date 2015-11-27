package com.coherentlogic.coherent.datafeed.services.message.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.adapters.MarketByOrderAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Message processor that handles refresh messages from the Thomson Reuters
 * Enterprise Platform (TREP).
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RefreshMarketByOrderMessageProcessor
    extends AbstractMarketByOrderMessageProcessor {

    private static final Logger log =
        LoggerFactory.getLogger(RefreshMarketByOrderMessageProcessor.class);

    private final MarketByOrderAdapter marketByOrderAdapter;

    public RefreshMarketByOrderMessageProcessor (
        MarketByOrderAdapter marketByOrderAdapter
    ) {
        this.marketByOrderAdapter = marketByOrderAdapter;
    }

    /**
     * @todo Should refresh events for existing market price objects cause an
     *  exception to be thrown? See assertNull below.
     */
    @Override
    public Message<MarketByOrder> process(Message<OMMItemEvent> message) {

        log.info("refreshMarketByOrderMessageProcessor.process: method " +
            "begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        Session session = getSession(message);

        OMMItemEvent itemEvent = message.getPayload();

        Handle handle = itemEvent.getHandle();

        log.debug("Refresh begins for the itemEvent with the handle " + handle);

        OMMMsg ommMsg = itemEvent.getMsg();

        MarketByOrder marketByOrder = session.getMarketByOrder(handle);

        /* TODO: This is not really valid -- consider the following 
         *       The user disconnects the Ethernet cable and waits a few moments
         *       -- when the user reconnects the Ethernet cable a refresh will
         *       take place however the market price will not be null, so this
         *       should not be an exception. 
         */
//        assertNull("marketPrice (expected: " +
//            marketByOrderAdapter.adapt(ommMsg) + ") ", marketPrice);

        if (marketByOrder == null) {
            marketByOrder = marketByOrderAdapter.adapt(ommMsg);
            log.info("The marketByOrder was null so a new instance was " +
                "created for this refresh.");
        } else {
            marketByOrderAdapter.adapt(ommMsg, marketByOrder);
            log.info("The marketByOrder was not null so an existing instance " +
                "was refreshed: " + marketByOrder);
        }

        session.putMarketByOrder(handle, marketByOrder);

        Message<MarketByOrder> result = MessageBuilder
            .withPayload(marketByOrder)
            .copyHeaders(headers).build();

        log.info("refreshMarketByOrderMessageProcessor.process: method " +
            "ends; result: " + result);

        return result;
    }
}
