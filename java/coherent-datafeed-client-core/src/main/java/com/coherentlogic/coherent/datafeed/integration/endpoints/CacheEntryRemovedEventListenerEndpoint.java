package com.coherentlogic.coherent.datafeed.integration.endpoints;

import java.util.concurrent.atomic.AtomicLong;

import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.integration.support.MessageBuilder;

import com.coherentlogic.coherent.datafeed.beans.CachedEntry;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;
import com.reuters.rfa.common.Handle;

/**
 * A listener that is interested in events that are fired when items are removed
 * from the cache.
 *
 * Here's how this works:
 * 
 * The user requests, for example, time series data from Thomson Reuters (TR).
 * 
 * TR sends this data in chunks and it get's stored in a subclass of
 * CachedEntry.
 *
 * When the data has been transfered, TR sends a completion event for the given
 * request -- at this point the CachedEntry associated, by handle, with the
 * request is removed.
 *
 * It is this removal that we want to know about because the TimeSeries data
 * will be available in its entirety at that point, so we convert the data into
 * an instance of {@link TimeSeries} and send it to the queue.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <C> The CachedEntry type.
 */
public class CacheEntryRemovedEventListenerEndpoint
    extends AbstractCacheEventListenerEndpoint<CachedEntry> {

    private static final Logger log =
        LoggerFactory.getLogger(CacheEntryRemovedEventListenerEndpoint.class);

    private final AtomicLong eventCounter = new AtomicLong (0);

    public static final long ONE = 1L;

    public CacheEntryRemovedEventListenerEndpoint() {
        this(new GatewayDelegate());
    }

    public CacheEntryRemovedEventListenerEndpoint(
        GatewayDelegate gatewayDelegate) {
        super(gatewayDelegate);
    }

    /**
     * 
     */
    @CacheEntryRemoved
    @Override
    public void onCacheEntryRemoved(
        CacheEntryRemovedEvent<Handle, CachedEntry> removedEvent
    ) {
        // Once the entry has been removed the payload will be null, so we want
        // to capture the entry while it is still available and then pass this
        // to the workflow.
        if (removedEvent.isPre()) {

            log.info("onCacheEntryRemoved: method invoked; received event " +
                "number " + eventCounter.addAndGet(ONE) + " at time " +
                System.currentTimeMillis() + "; event: " + removedEvent);

            CachedEntry payload = removedEvent.getValue();

            Message<CachedEntry> message = MessageBuilder
                .withPayload(payload)
                .build();

            GatewayDelegate gatewayDelegate = getGatewayDelegate();

            gatewayDelegate.send(message);
        }
    }
}

//Sample sample1 = new Sample ("2012 Dec 17");
//sample1.addPoint("29.2", "28.8", "29.22", "28.73", "177,292",
//    "29.0428");
//Sample sample2 = new Sample ("2012 Dec 14");
//sample2.addPoint("28.7", "28.92", "29.1", "28.66", "152,732",
//    "28.7799");
//Sample sample3 = new Sample ("2012 Dec 13");
//sample3.addPoint("28.93", "29.06", "29.12", "28.87", "178,738",
//    "28.9663");
//
//timeSeries.addSample(sample1, sample2, sample3);
//
//timeSeries.setName("FOOBARBAZBOO!!!!!");