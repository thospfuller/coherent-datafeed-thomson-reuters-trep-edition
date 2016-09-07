package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.exceptions.AdaptFailedException;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * 
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="support@coherentlogic.com">Support</a>
 *
 * @param <T>
 */
public abstract class AbstractRefreshOrUpdateEventRFABeanMessageProcessor <T extends RFABean> implements
    MessageProcessorSpecification<OMMItemEvent, T> {

    private static final Logger log =
        LoggerFactory.getLogger(AbstractRefreshOrUpdateEventRFABeanMessageProcessor.class);

    private final RFABeanAdapter<T> objectAdapter;

    private final Map<Handle, String> ricCache;

    private final Map<String, T> objectCache;

    public AbstractRefreshOrUpdateEventRFABeanMessageProcessor(
        RFABeanAdapter<T> objectAdapter,
        Map<Handle, String> ricCache,
        Map<String, T> objectCache
    ) {
        this.objectAdapter = objectAdapter;
        this.ricCache = ricCache;
        this.objectCache = objectCache;
    }

    /**
     * @todo Should refresh events for existing market price objects cause an
     *  exception to be thrown? See assertNull below.
     */
    @Override
    public Message<T> process(Message<OMMItemEvent> message) {

        log.debug("process: method begins; message: " + message);

        MessageHeaders headers = message.getHeaders();

        OMMItemEvent itemEvent = message.getPayload();

        Handle handle = itemEvent.getHandle();

        String ric = ricCache.get(handle);

        T rfaBean = objectCache.get(ric);

        OMMMsg ommMsg = itemEvent.getMsg();

        log.debug("A refresh (6) OR update (8) (ommMsg.msgType: " + ommMsg.getMsgType() + ") begins for the "
            + "itemEvent with the handle " + handle + ", ric: " + ric);

        assertNotNull("rfaBean", rfaBean);

        beforeUpdate(rfaBean);

        try {
            objectAdapter.adapt(ommMsg, rfaBean);
        } catch (NullPointerException npe) {
            // See Issue #22 as this only seems to happen with select rics and it's not clear why.
            // https://bitbucket.org/CoherentLogic/coherent-datafeed-thomson-reuters-trep-edition/issues/22/investigate-mbp-for-the-bricmi-ric
            throw new AdaptFailedException ("Unable to adapt the ommMsg of type " + ommMsg.getMsgType() + " (refresh "
                + "(6) OR update (8)) for the ric: " + ric, npe);
        }

        afterUpdate(rfaBean);

        log.debug("The rfaBean with the ric " + ric + " was refreshed.");

        Message<T> result = MessageBuilder.withPayload(rfaBean).copyHeaders(headers).build();

        log.debug("process: method ends; result: " + result);

        return result;
    }

    protected abstract void beforeUpdate (T target);

    protected abstract void afterUpdate (T target);
}
