package com.coherentlogic.coherent.datafeed.integration.transformers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.adapters.MarketPriceAdapter;
import com.coherentlogic.coherent.datafeed.beans.CachedMarketPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Unit test for the {@link UpdateMarketPriceTransformer} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UpdateMarketPriceTransformerTest {

    private OMMItemEvent itemEvent = null;

    private Handle handle = null;

    private static CacheContainer cacheManager = null;

    private Cache<Handle, CachedMarketPrice> map = null;

    private UpdateMarketPriceTransformer updateMarketPriceTransformer = null;

    private CachedMarketPrice cachedMarketPrice = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        cacheManager = new DefaultCacheManager();
    }

    @Before
    public void setUp() throws Exception {

        itemEvent = mock (OMMItemEvent.class);

        handle = mock (Handle.class);

        map = cacheManager.getCache("test");

        MarketPriceAdapter marketPriceAdapter = mock (MarketPriceAdapter.class);

        updateMarketPriceTransformer = new UpdateMarketPriceTransformer (
            map, marketPriceAdapter);

        cachedMarketPrice = new CachedMarketPrice(handle);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        cacheManager.stop();
        cacheManager = null;
    }

    @After
    public void tearDown() throws Exception {
        itemEvent = null;
        handle = null;
        map = null;
        updateMarketPriceTransformer = null;
        cachedMarketPrice = null;
    }

    @Test
    public void processHappyPath() {

        MarketPrice originalMarketPrice = new MarketPrice ();

        BigDecimal ask = new BigDecimal ("1234.56");
        originalMarketPrice.setAsk(ask);

        cachedMarketPrice.setObject(originalMarketPrice);

        map.put(handle, cachedMarketPrice);

        when (itemEvent.getHandle()).thenReturn(handle);

        updateMarketPriceTransformer.process(itemEvent);

        // At the moment we'll just assume the MarketPrice has been cloned as
        // it's a bit difficult to determine if the class is exactly the same
        // via memory address as the class prior.
        CachedMarketPrice cachedMarketPrice = map.get(handle);

        MarketPrice clonedMarketPrice = cachedMarketPrice.getObject();

        assertEquals(originalMarketPrice.getAsk(), clonedMarketPrice.getAsk());
    }

    /**
     * @TODO Determine if this test is valid as we should never have an update
     *  where the MarketPrice does not exist in the marketPriceMap.
     */
    @Test(expected=NullPointerRuntimeException.class)
    public void testProcessWhereNoMarketPriceExistsInMap() {

        when (itemEvent.getHandle()).thenReturn(handle);

        updateMarketPriceTransformer.process(itemEvent);
    }
}
