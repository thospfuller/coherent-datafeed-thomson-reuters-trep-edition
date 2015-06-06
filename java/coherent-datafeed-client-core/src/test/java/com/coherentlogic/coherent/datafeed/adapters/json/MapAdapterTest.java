package com.coherentlogic.coherent.datafeed.adapters.json;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants;
import com.coherentlogic.coherent.datafeed.domain.RFABean;

/**
 * Unit test for the {@link MapAdapter} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MapAdapterTest implements MarketPriceConstants {

    static final String DEF_HIGH_FOR_TODAY = "123.45";

    private Map<String, Method> typeToGetterMethodMap = null;

    private RFABeanToMapAdapter<RFABean> adapter = null;

    @Before
    public void setUp() throws Exception {
        typeToGetterMethodMap = new HashMap<String, Method> ();
        adapter = new RFABeanToMapAdapter<RFABean>(MarketPrice.class);
    }

    @After
    public void tearDown() throws Exception {
        typeToGetterMethodMap = null;
        adapter = null;
    }

    @Test
    public void testScan() {
        RFABeanToMapAdapter.scan(MarketPrice.class, typeToGetterMethodMap);

        // If the number of methods changes in the MarketPrice class then we'll
        // need to modify the number below.
        assertEquals (223, typeToGetterMethodMap.size());
    }

    @Test
    public void testAdapt() {

        MarketPrice marketPrice = new MarketPrice ();

        marketPrice.setTodaysHigh(new BigDecimal (DEF_HIGH_FOR_TODAY));

        Map<String, String> result = adapter.adapt(marketPrice);

        // If the number of methods changes in the MarketPrice class then we'll
        // need to modify the number below.
        assertEquals (DEF_HIGH_FOR_TODAY, result.get(HIGH_1));
    }
}
