package com.coherentlogic.coherent.datafeed.integration.endpoints;

import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryRemoved;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryRemovedEvent;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.history.TrackableComponent;

import com.coherentlogic.coherent.datafeed.beans.CachedEntry;
import com.coherentlogic.coherent.datafeed.listeners.infinispan.CachedObjectListener;
import com.reuters.rfa.common.Handle;

/**
 * An endpoint adapter that is used by the Cache event listener to convert
 * notifications into Spring integration messages that are sent to the Spring
 * integration workflow.
 *
 * @todo Combine this class with the other EDE so that there is a common base
 *  class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractCacheEventListenerEndpoint<C extends CachedEntry>
    extends AbstractEndpoint
        implements TrackableComponent, CachedObjectListener <Handle, C> {

    static final String
        CACHE_EVENT_DRIVEN_ENDPOINT = "cache:event-driven-endpoint";

    
    /**
     * Used to send the message.
     */
    private final GatewayDelegate gatewayDelegate;

    public AbstractCacheEventListenerEndpoint(GatewayDelegate gatewayDelegate) {
        super();
        this.gatewayDelegate = gatewayDelegate;
    }

    @Override
    protected void doStart() {
        gatewayDelegate.start();
    }

    @Override
    protected void doStop() {
        gatewayDelegate.stop();
    }

    @Override
    public String getComponentType() {
        return CACHE_EVENT_DRIVEN_ENDPOINT;
    }

    @Override
    public void setShouldTrack(boolean shouldTrack) {
        gatewayDelegate.setShouldTrack(shouldTrack);
    }

    public void setComponentName(String componentName){
        this.gatewayDelegate.setComponentName(componentName);
    }

    public void setRequestChannel(MessageChannel requestChannel){
        this.gatewayDelegate.setRequestChannel(requestChannel);
    }

    public void setReplyChannel(MessageChannel replyChannel){
        this.gatewayDelegate.setReplyChannel(replyChannel);
    }

    public void setErrorChannel(MessageChannel errorChannel){
        this.gatewayDelegate.setErrorChannel(errorChannel);
    }

    protected GatewayDelegate getGatewayDelegate() {
        return gatewayDelegate;
    }

    @CacheEntryModified
    @Override
    public void onCacheEntryModified(
        CacheEntryModifiedEvent<Handle, C> modifiedEvent){}

    @CacheEntryCreated
    @Override
    public void onCacheEntryCreated(
        CacheEntryCreatedEvent<Handle, C> createdEvent){}

    @CacheEntryRemoved
    @Override
    public void onCacheEntryRemoved(
        CacheEntryRemovedEvent<Handle, C> removedEvent){}
}
