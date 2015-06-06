package com.coherentlogic.coherent.datafeed.integration.transformers;

import static com.coherentlogic.coherent.datafeed.misc.Constants.CACHE_BEAN_ID;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_APP_CTX_PATH;
import static com.coherentlogic.coherent.datafeed.misc.Constants.METHOD_NOT_IMPLEMENTED;
import static com.coherentlogic.coherent.datafeed.misc.Constants.REFRESH_MARKET_PRICE_TRANSFORMER_BEAN_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.Map;

import org.infinispan.Cache;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.coherentlogic.coherent.datafeed.beans.CachedMarketPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMFieldEntry;
import com.reuters.rfa.omm.OMMFieldList;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.session.omm.OMMItemEvent;

/**
 * Integration test for the {@link RefreshMarketPriceTransformer} class.
 *
 * Note that we consider this to be an integration test since we're loading
 * the beans via Spring as it will be too cumbersome to set up this test
 * given the number of dependencies involved.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Ignore
public class RefreshMarketPriceTransformerTest {

    private AbstractApplicationContext applicationContext = null;

    private Map<Handle, MarketPrice> marketPriceMap = null;

    private RefreshMarketPriceTransformer transformer = null;

    private Cache<Handle, CachedMarketPrice> cache = null;

    @Before
    public void setUp() throws Exception {

        applicationContext =
            new ClassPathXmlApplicationContext (
                DEFAULT_APP_CTX_PATH);

        applicationContext.registerShutdownHook();

        marketPriceMap = (Map<Handle, MarketPrice>)
            applicationContext.getBean(CACHE_BEAN_ID);

        transformer =
            (RefreshMarketPriceTransformer) applicationContext.getBean(
                REFRESH_MARKET_PRICE_TRANSFORMER_BEAN_ID);

        cache = (Cache) applicationContext.getBean(CACHE_BEAN_ID);
    }

    @After
    public void tearDown() throws Exception {
        applicationContext.close();
        applicationContext = null;
        marketPriceMap = null;
        transformer = null;
        cache = null;
    }

    /**
     * This is a simple test that processes an {@link OMMItemEvent} and then
     * checks that the size of the map has increased by one.
     */
    @Test
    public void testProcessHappyPath() {

        OMMItemEvent itemEvent = mock (OMMItemEvent.class);

        Handle handle = mock(Handle.class);
        // We have to do this otherwise the test will fail due to a NPE.
        cache.put(handle, new CachedMarketPrice(handle));

        when (itemEvent.getHandle()).thenReturn(handle);

        OMMMsg ommMsg = mock (OMMMsg.class);

        when (itemEvent.getMsg()).thenReturn(ommMsg);

        OMMFieldList fieldList = mock (OMMFieldList.class);

        when (ommMsg.getPayload()).thenReturn(fieldList);

        when (fieldList.iterator()).thenReturn(
            new Iterator<OMMFieldEntry>() {

                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public OMMFieldEntry next() {
                    throw new RuntimeException(METHOD_NOT_IMPLEMENTED);
                }

                @Override
                public void remove() {
                    throw new RuntimeException(METHOD_NOT_IMPLEMENTED);
                }
            }
        );

        transformer.process(itemEvent);

        assertEquals (1, marketPriceMap.size());
    }

//    @Test
//    public void testTransformPayloadOMMItemEvent() {
//        fail("Not yet implemented");
//    }

}
