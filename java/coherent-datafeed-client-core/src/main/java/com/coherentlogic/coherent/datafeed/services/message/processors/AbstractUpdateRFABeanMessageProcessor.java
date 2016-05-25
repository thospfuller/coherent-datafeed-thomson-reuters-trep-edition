package com.coherentlogic.coherent.datafeed.services.message.processors;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.RFABeanAdapter;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.reuters.rfa.common.Handle;

public abstract class AbstractUpdateRFABeanMessageProcessor<T extends RFABean>
    extends AbstractRefreshOrUpdateEventRFABeanMessageProcessor<T> {

    public AbstractUpdateRFABeanMessageProcessor(
        RFABeanAdapter<T> objectAdapter,
        Map<Handle, String> ricCache,
        Map<String, T> objectCache
    ) {
        super(objectAdapter, ricCache, objectCache);
    }

    @Override
    protected void beforeUpdate(T target) {
        // target.clear (); is *NOT* required for update events since this is resetting a subset of properties in the
        // target object.
    }

    @Override
    protected void afterUpdate(T target) {
        // Ignored.
    }
}
//private final RFABeanAdapter<T> objectAdapter;
//
//   private final Map<Handle, String> ricCache;
//
//   private final Map<String, T> objectCache;
//
//   public AbstractRefreshRFABeanMessageProcessor(
//       RFABeanAdapter<T> objectAdapter,
//       Map<Handle, String> ricCache,
//       Map<String, T> objectCache
//   ) {
//       this.objectAdapter = objectAdapter;
//       this.ricCache = ricCache;
//       this.objectCache = objectCache;
//   }
//
//   /**
//    * @todo Should refresh events for existing market price objects cause an
//    *  exception to be thrown? See assertNull below.
//    */
//   @Override
//   public Message<T> process(Message<OMMItemEvent> message) {
//
//       log.debug("process: method begins; message: " + message);
//
//       MessageHeaders headers = message.getHeaders();
//
//       OMMItemEvent itemEvent = message.getPayload();
//
//       Handle handle = itemEvent.getHandle();
//
//       String ric = ricCache.get(handle);
//
//       T rfaBean = objectCache.get(ric);
//
//       log.debug("Refresh begins for the itemEvent with the handle " + handle);
//
//       OMMMsg ommMsg = itemEvent.getMsg();
//
//       assertNotNull("rfaBean", rfaBean);
//
//       beforeUpdate(rfaBean);
//
//       objectAdapter.adapt(ommMsg, rfaBean);
//
//       afterUpdate(rfaBean);
//
//       log.debug("The rfaBean with the ric " + ric + " was refreshed.");
//
//       Message<T> result = MessageBuilder.withPayload(rfaBean).copyHeaders(headers).build();
//
//       log.debug("process: method ends; result: " + result);
//
//       return result;
//   }
//
//   protected abstract void beforeUpdate (T target);
//
//   protected abstract void afterUpdate (T target);
