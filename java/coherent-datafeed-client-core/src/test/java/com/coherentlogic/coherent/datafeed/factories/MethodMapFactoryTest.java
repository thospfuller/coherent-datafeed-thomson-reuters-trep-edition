package com.coherentlogic.coherent.datafeed.factories;

import static com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants.BID;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.exceptions.MissingDataException;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;

/**
 * Unit test for the {@link MethodMapFactory} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MethodMapFactoryTest {

    @Test(expected=NullPointerRuntimeException.class)
    public void testAnalyzeWhereTypeIsNull() {

        Map<String, Method> methodMap = new HashMap<String, Method> ();

        MethodMapFactory.analyze(null, methodMap);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void testAnalyzeWhereMethodMapIsNull() {

        Map<String, Method> methodMap = new HashMap<String, Method> ();

        MethodMapFactory.analyze(null, methodMap);
    }

    @Test
    public void testAnalyzeHappyPath() {

        Map<String, Method> methodMap = new HashMap<String, Method> ();

        MethodMapFactory.analyze(MarketPrice.class, methodMap);

        assertNotNull(methodMap.get(BID));
    }

    @Test(expected=MissingDataException.class)
    public void testAnalyzeWhereClassHasZeroAnnotations() {

        Map<String, Method> methodMap = new HashMap<String, Method> ();

        MethodMapFactory.analyze(EmptyRFABean.class, methodMap);

        assertNotNull(methodMap.get(BID));
    }
}

class EmptyRFABean extends RFABean {
    private static final long serialVersionUID = 1L;
}
