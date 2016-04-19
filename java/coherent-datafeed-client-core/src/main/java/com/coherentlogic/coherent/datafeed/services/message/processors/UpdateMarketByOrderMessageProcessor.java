package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.adapters.MarketByOrderAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 */
public class UpdateMarketByOrderMessageProcessor
    extends AbstractMarketByOrderMessageProcessor {

    private static final Logger log =
        LoggerFactory.getLogger(UpdateMarketByOrderMessageProcessor.class);

    private final MarketByOrderAdapter marketByOrderAdapter;

    public UpdateMarketByOrderMessageProcessor (
        MarketByOrderAdapter marketPriceAdapter
    ) {
        this.marketByOrderAdapter = marketPriceAdapter;
    }

    /**
     * @todo We don't necessarily need to clone the market price altogether --
     *  rather we could simply update whatever data point requires an update and
     *  then have the domain object issue the update via the property change
     *  listener. This would be beneficial in the sense that we could then
     *  attach any number of listeners to the domain class, one of which could
     *  propagate that update as a single event or another which could send the
     *  entire instance as an update.
     */
    @Override
    public Message<MarketByOrder> process(Message<OMMItemEvent> message) {

        log.info("updateMarketByOrderMessageProcessor.process: method " +
            "begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        OMMItemEvent itemEvent = message.getPayload();

        OMMMsg ommMsg = itemEvent.getMsg();

        Handle handle = itemEvent.getHandle();

        Session session = getSession(message);

        MarketByOrder currentMarketByOrder = session.getMarketByOrder(handle);

        assertNotNull("currentMarketByOrder", currentMarketByOrder);

        log.info ("Updating the currentMarketByOrder: " + currentMarketByOrder);

        MarketByOrder updatedMarketByOrder =
            (MarketByOrder) SerializationUtils.clone(currentMarketByOrder);

        marketByOrderAdapter.adapt(ommMsg, updatedMarketByOrder);

        session.putMarketByOrder(handle, updatedMarketByOrder);

        Message<MarketByOrder> result = MessageBuilder
            .withPayload(updatedMarketByOrder)
            .copyHeaders(headers).build();

        log.info("updateMarketByOrderMessageProcessor.process: method " +
            "ends; result: " + result);

        return result;
    }
}
/*
See UpdateMarketPriceTransformer

    @Override
    protected R process(OMMItemEvent itemEvent) {

        Handle handle = itemEvent.getHandle();

        log.debug("Update begins for the RFABean with the handle " +
            handle);

        OMMMsg ommMsg = itemEvent.getMsg();

        RFABeanAdapter<R> rfaBeanAdapter =
            getRFABeanAdapter();

        Map<Handle, C> cache = getCache();

        C currentCachedObject = cache.get(handle);

        if (currentCachedObject == null)
            throw new NullPointerRuntimeException("The cached object " +
                "associated with the handle " + handle + " is null and hence " +
                "the system is in an invalid state as this should never " +
                "happen.");
        else
            log.debug("Updating the following rfaBean: " +
                currentCachedObject);

        R currentRFABean = (R) currentCachedObject.getObject();

        R nextRFABean = (R)
            SerializationUtils.clone(currentRFABean);

        rfaBeanAdapter.adapt(ommMsg, nextRFABean);

        currentCachedObject.setObject(nextRFABean);

        cache.put(handle, currentCachedObject);

        return nextRFABean;
    }
*/