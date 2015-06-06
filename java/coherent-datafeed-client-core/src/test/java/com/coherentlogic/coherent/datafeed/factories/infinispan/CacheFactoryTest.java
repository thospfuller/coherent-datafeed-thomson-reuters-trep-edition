package com.coherentlogic.coherent.datafeed.factories.infinispan;

import static org.junit.Assert.assertNotNull;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.beans.CachedEntry;
import com.coherentlogic.coherent.datafeed.listeners.infinispan.AbstractCachedObjectListener;
import com.coherentlogic.coherent.datafeed.listeners.infinispan.DefaultCachedObjectListener;
import com.reuters.rfa.common.Handle;

/**
 * Unit test for the {@link CacheFactory} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class CacheFactoryTest<T extends CachedEntry> {

    private static final String NAME = "fubar";

    private static CacheContainer cacheManager = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        cacheManager = new DefaultCacheManager();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        cacheManager.stop();
        cacheManager = null;
    }

    /**
     * We do not expect an NPE to be thrown.
     */
    @Test
    public void getInstanceWithNoListener () {

        CacheFactory<Handle, T> cacheFactory =
            new CacheFactory<Handle, T> (cacheManager, NAME);

        Cache<Handle, T> result = cacheFactory.getInstance();

        assertNotNull (result);
    }

    /**
     * We do not expect an NPE to be thrown.
     */
    @Test
    public void getInstanceWithAListener () {

        AbstractCachedObjectListener<T> cachedObjectListener =
            new DefaultCachedObjectListener<T> ();

        CacheFactory<Handle, T> cacheFactory =
            new CacheFactory<Handle, T> (
                cacheManager,
                NAME,
                cachedObjectListener
            );

        Cache<Handle, T> result = cacheFactory.getInstance();

        assertNotNull (result);
    }
}
