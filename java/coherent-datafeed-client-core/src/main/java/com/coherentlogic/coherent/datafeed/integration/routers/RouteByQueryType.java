package com.coherentlogic.coherent.datafeed.integration.routers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.coherentlogic.coherent.datafeed.beans.CachedObject;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * A router that uses the handle to determine what kind of query was made for
 * this specific {@link OMMItemEvent}. If the query was for a time series, then
 * the work flow follows the time series channel, if it was for market price,
 * then the work flow follows the market price channel, otherwise the work flow
 * follows the error channel.
 *
 * @deprecated After refactoring this project we no longer need this class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class RouteByQueryType extends AbstractMessageRouter {

    private static final Logger log =
        LoggerFactory.getLogger(RouteByQueryType.class);

    private final Map<Class<? extends Serializable>, MessageChannel>
        messageChannelMap;

    private final MessageChannel errorChannel;

    private final Cache<Handle, ? extends CachedObject<? extends Serializable>>
        queryCache;

    public RouteByQueryType(
        Map<Class<? extends Serializable>, MessageChannel> messageChannelMap,
        MessageChannel errorChannel,
        Cache<Handle, ? extends CachedObject<? extends Serializable>> queryCache
    ) {
        super();
        this.messageChannelMap = messageChannelMap;
        this.errorChannel = errorChannel;
        this.queryCache = queryCache;
    }

    @Override
    protected Collection<MessageChannel> determineTargetChannels(
        Message<?> message) {

        List<MessageChannel> results = new ArrayList<MessageChannel> (1);

        OMMItemEvent itemEvent = (OMMItemEvent) message.getPayload();

        Handle handle = itemEvent.getHandle();

        CachedObject<? extends Serializable> cachedObject = queryCache.get(handle);

        MessageChannel nextMessageChannel = errorChannel;

        if (cachedObject != null) {

            Class<? extends Serializable> cachedObjectClass =
                cachedObject.getClass ();

            nextMessageChannel =
                messageChannelMap.get(cachedObjectClass);
        }

        results.add(nextMessageChannel);

        return results;
    }

//  private final MessageChannel
//  marketPriceMessageChannel,
//  timeSeriesMessageChannel,
//  ts1DefMessageChannel,
//  errorMessageChannel;
//
//private final Cache<Handle, ? extends CachedObject<? extends Serializable>>
//  queryCache;
//
//public RouteByQueryType(
//  MessageChannel marketPriceMessageChannel,
//  MessageChannel timeSeriesMessageChannel,
//  MessageChannel ts1DefMessageChannel,
//  MessageChannel errorMessageChannel,
//  Cache<Handle, ? extends CachedObject<? extends Serializable>> queryCache) {
//  super();
//  this.marketPriceMessageChannel = marketPriceMessageChannel;
//  this.timeSeriesMessageChannel = timeSeriesMessageChannel;
//  this.ts1DefMessageChannel = ts1DefMessageChannel;
//  this.errorMessageChannel = errorMessageChannel;
//  this.cache = queryCache;
//}

//    @Override
//    protected Collection<MessageChannel> determineTargetChannels(
//        Message<?> message) {
//
//        List<MessageChannel> results = new ArrayList<MessageChannel> ();
//
//        OMMItemEvent itemEvent = (OMMItemEvent) message.getPayload();
//
//        Handle handle = itemEvent.getHandle();
//
//        CachedObject<? extends Serializable> cachedObject = queryCache.get(handle);
//
//        if (cachedObject instanceof CachedMarketPrice) {
//            results.add(marketPriceMessageChannel);
//        } else if (cachedObject instanceof TimeSeriesEntries) {
//            results.add(timeSeriesMessageChannel);
//        } else if (cachedObject instanceof TS1DefEntry) {
//            results.add(ts1DefMessageChannel);
//        } else {
//            results.add(errorMessageChannel);
//        }
//
//        log.debug("cachedObject: " + cachedObject +
//            ", results: " + ToStringBuilder.reflectionToString(results));
//
//        return results;
//    }
}
