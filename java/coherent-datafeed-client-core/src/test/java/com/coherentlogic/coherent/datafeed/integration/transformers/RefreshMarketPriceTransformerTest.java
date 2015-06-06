package com.coherentlogic.coherent.datafeed.integration.transformers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.adapters.MarketPriceAdapter;
import com.coherentlogic.coherent.datafeed.beans.CachedMarketPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Unit test for the {@link RefreshMarketPriceTransformer} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RefreshMarketPriceTransformerTest {

    private OMMItemEvent itemEvent = null;

    private Handle handle = null;

    private CacheContainer cacheManager = null;

    private Cache<Handle, CachedMarketPrice> map = null;

    private MarketPriceAdapter marketPriceAdapter = null;

    private RefreshMarketPriceTransformer refreshMarketPriceTransformer = null;

    private CachedMarketPrice cachedMarketPrice = null;

    @Before
    public void setUp() throws Exception {

        itemEvent = mock (OMMItemEvent.class);

        handle = mock (Handle.class);

        cacheManager = new DefaultCacheManager();

        map = cacheManager.getCache("test");

        marketPriceAdapter = mock (MarketPriceAdapter.class);

        refreshMarketPriceTransformer = new RefreshMarketPriceTransformer (
            map, marketPriceAdapter);

        cachedMarketPrice = new CachedMarketPrice(handle);
    }

    @After
    public void tearDown() throws Exception {
        cacheManager.stop();
        cacheManager = null;

        itemEvent = null;
        handle = null;
        map = null;
        marketPriceAdapter = null;
        refreshMarketPriceTransformer = null;
        cachedMarketPrice = null;
    }

    @Test
    public void processHappyPath() {

        /**
         * This should already be in the cache because once a query is made the
         * resulting handle is immediately added to the cache with an empty
         * CachedObject as the value.
         */
        map.put(handle, cachedMarketPrice);

        MarketPrice marketPrice = new MarketPrice ();

        BigDecimal ask = new BigDecimal ("1234.56");
        marketPrice.setAsk(ask);

        when (itemEvent.getHandle()).thenReturn(handle);

        when (
            marketPriceAdapter.adapt(
                any(OMMMsg.class)
            )
        ).thenReturn(marketPrice);

        refreshMarketPriceTransformer.process(itemEvent);

        CachedMarketPrice cachedMarketPrice = map.get(handle);

        MarketPrice actualMarketPrice = cachedMarketPrice.getObject();

        assertEquals(marketPrice, actualMarketPrice);
    }
}
