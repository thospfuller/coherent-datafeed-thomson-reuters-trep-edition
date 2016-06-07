package com.coherentlogic.coherent.datafeed.services.message.processors;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.beans.AbstractQuery;
import com.coherentlogic.coherent.datafeed.domain.RICBeanSpecification;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.services.CacheableQueryableService;
import com.coherentlogic.coherent.datafeed.services.MessageProcessorSpecification;
import com.coherentlogic.coherent.datafeed.services.ServiceName;
import com.reuters.rfa.common.Handle;

public class AbstractQueryMessageProcessor<T extends RICBeanSpecification>
    implements MessageProcessorSpecification <AbstractQuery<T[]>, Map<String, T>> {

    private static final Logger log = LoggerFactory.getLogger(QueryMarketPriceMessageProcessor.class);

    private final CacheableQueryableService<T> queryableService;

    private final Map<String, T> objectCache;

    public AbstractQueryMessageProcessor(
        CacheableQueryableService<T> queryableService,
        Map<String, T> objectCache
    ) {
        this.queryableService = queryableService;
        this.objectCache = objectCache;
    }

    @Override
    public Message<Map<String, T>> process(Message<AbstractQuery<T[]>> message) {

      Message<Map<String, T>> result = null;

      AbstractQuery<T[]> parameters = message.getPayload();

      log.debug("parameters: " + parameters);

      String serviceName = parameters.getServiceName();

      SessionBean sessionBean = parameters.getSessionBean();

      Handle loginHandle = sessionBean.getHandle();

      T[] items = parameters.getItem();

      for (T nextMarketPrice : items) {

          String ric = nextMarketPrice.getRic();

          assertNotNull ("ric", ric);

          objectCache.put(ric, nextMarketPrice);

          queryableService.query(
              ServiceName.valueOf(serviceName),
              sessionBean,
              ric
          );
      }

      MessageHeaders headers = message.getHeaders();

      result =
          MessageBuilder
              .withPayload(objectCache)
              .copyHeaders(headers)
              .build();

      return result;
    }
}
