package com.coherentlogic.coherent.datafeed.integration.enrichers;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Handle;

/**
 * An partial message enricher implementation that provides a cache and some
 * helper methods.
 *
 * @todo getSession should be abstracted into a static method since this logic
 *  is also used in market price message processor -- see Utils.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractMessageEnricher
    implements EnricherSpecification {

    private static final Logger log =
        LoggerFactory.getLogger(AbstractMessageEnricher.class);

    /**
     * We need to be able to associate a directory handle with the correct
     * session and that's what this cache does.
     */
    private final Cache<Handle, Session> sessionCache;

    /**
     * Constructor requires an instance of {@link Cache} where the
     * {@link Handle} is the key and the {@link Session} is the value.
     */
    public AbstractMessageEnricher (
        Cache<Handle, Session> sessionCache
    ) {
        this.sessionCache = sessionCache;
    }

    /**
     * @return The cache where the {@link Handle} is the key and the
     *  {@link Session} is the value.
     */
    public Cache<Handle, Session> getSessionCache() {
        return sessionCache;
    }
}
