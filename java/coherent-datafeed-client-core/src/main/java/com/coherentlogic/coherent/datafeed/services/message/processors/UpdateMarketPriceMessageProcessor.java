package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.adapters.MarketPriceAdapter;
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
public class UpdateMarketPriceMessageProcessor
    extends AbstractMarketPriceMessageProcessor {

    private static final Logger log =
        LoggerFactory.getLogger(UpdateMarketPriceMessageProcessor.class);

    private final MarketPriceAdapter marketPriceAdapter;

    private final Map<Handle, String> ricCache;

    private final Map<String, MarketPrice> marketPriceCache;

    public UpdateMarketPriceMessageProcessor (
        MarketPriceAdapter marketPriceAdapter,
        Map<Handle, String> ricCache,
        Map<String, MarketPrice> marketPriceCache
    ) {
        this.marketPriceAdapter = marketPriceAdapter;
        this.ricCache = ricCache;
        this.marketPriceCache = marketPriceCache;
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
    public Message<MarketPrice> process(Message<OMMItemEvent> message) {

        log.info("updateMarketPriceMessageProcessor.process: method " +
            "begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        OMMItemEvent itemEvent = message.getPayload();

        OMMMsg ommMsg = itemEvent.getMsg();

        Handle handle = itemEvent.getHandle();

        String ric = ricCache.get(handle);

        MarketPrice currentMarketPrice = marketPriceCache.get(ric);

        assertNotNull("currentMarketPrice", currentMarketPrice);

        log.info ("Updating the following marketPrice: " + currentMarketPrice);

//        MarketPrice updatedMarketPrice =
//            (MarketPrice) SerializationUtils.clone(currentMarketPrice);

        marketPriceAdapter.adapt(ommMsg, currentMarketPrice);

//        session.putMarketPrice(handle, updatedMarketPrice);

        Message<MarketPrice> result = MessageBuilder
            .withPayload(currentMarketPrice)
            .copyHeaders(headers).build();

        log.info("updateMarketPriceMessageProcessor.process: method " +
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