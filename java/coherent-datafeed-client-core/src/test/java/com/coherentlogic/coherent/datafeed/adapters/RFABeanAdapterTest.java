package com.coherentlogic.coherent.datafeed.adapters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.AttribInfo;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.factories.NullRFABeanFactory;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMFieldList;

/**
 * Unit test for the {@link RFABeanAdapter} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RFABeanAdapterTest extends AdapterUnitTestHelper {

    private final TypedFactory<RFABean> rfaBeanFactory =
        new NullRFABeanFactory();

    private RFABeanAdapter<MarketPrice> adapter = null;

    private TypedFactory<AttribInfo> attribInfoFactory = null;

    @Before
    public void setUp() throws Exception {

        attribInfoFactory = mock (TypedFactory.class);

        when (attribInfoFactory.getInstance()).thenReturn(new AttribInfo ());

        setUp(rfaBeanFactory, attribInfoFactory, MarketPriceAdapter.class);

        adapter = (RFABeanAdapter<MarketPrice>) getAdapter();

    }

    @After
    public void tearDown () throws Exception {
        attribInfoFactory = null;
    }

    private OMMAttribInfo configureToReturnNonNullValues () {

        OMMAttribInfo attribInfo = mock (OMMAttribInfo.class);

        when (attribInfo.has (OMMAttribInfo.HAS_NAME)).thenReturn(true);
        when (attribInfo.getName()).thenReturn(DEFAULT_STRING_VALUE);

        when (attribInfo.has (OMMAttribInfo.HAS_SERVICE_NAME)).thenReturn(true);
        when (attribInfo.getName()).thenReturn(DEFAULT_STRING_VALUE);

        when (attribInfo.has (OMMAttribInfo.HAS_SERVICE_ID))
            .thenReturn(true);
        when (attribInfo.getServiceID()).thenReturn(DEFAULT_INT_VALUE);

        when (attribInfo.has (OMMAttribInfo.HAS_NAME_TYPE))
            .thenReturn(true);
        when (attribInfo.getNameType()).thenReturn(DEFAULT_SHORT_VALUE);

        return attribInfo;
    }

    private OMMAttribInfo configureToReturnNullValues () {

        OMMAttribInfo attribInfo = mock (OMMAttribInfo.class);

        when (attribInfo.has (OMMAttribInfo.HAS_NAME)).thenReturn(false);

        when (attribInfo.has (OMMAttribInfo.HAS_SERVICE_NAME))
            .thenReturn(false);

        when (attribInfo.has (OMMAttribInfo.HAS_SERVICE_ID))
            .thenReturn(false);

        when (attribInfo.has (OMMAttribInfo.HAS_NAME_TYPE))
            .thenReturn(false);

        return attribInfo;
    }

    @Test
    public void testGetNameHasIsTrue() {

        OMMAttribInfo attribInfo = configureToReturnNonNullValues ();

        String result = adapter.getName(attribInfo);

        assertEquals (DEFAULT_STRING_VALUE, result);
    }

    @Test
    public void testGetNameHasIsFalse() {

        OMMAttribInfo attribInfo = configureToReturnNullValues ();

        String result = adapter.getName(attribInfo);

        assertNull (result);
    }

    public void testGetServiceNameHasIsTrue() {

        OMMAttribInfo attribInfo = configureToReturnNonNullValues ();

        String result = adapter.getName(attribInfo);

        assertEquals (DEFAULT_STRING_VALUE, result);
    }

    @Test
    public void testGetServiceNameHasIsFalse() {

        OMMAttribInfo attribInfo = configureToReturnNullValues ();

        String result = adapter.getServiceName(attribInfo);

        assertNull (result);
    }

    @Test
    public void testGetServiceIdHasIsTrue() {
        OMMAttribInfo attribInfo = configureToReturnNonNullValues ();

        Integer result = adapter.getServiceId(attribInfo);

        assertEquals (DEFAULT_INT_VALUE, result);
    }

    @Test
    public void testGetServiceIdHasIsFalse() {
        OMMAttribInfo attribInfo = configureToReturnNullValues ();

        Integer result = adapter.getServiceId(attribInfo);

        assertNull (result);
    }

    @Test
    public void testGetNameTypeHasIsTrue() {
        OMMAttribInfo attribInfo = configureToReturnNonNullValues ();

        Short result = adapter.getNameType(attribInfo);

        assertEquals (DEFAULT_SHORT_VALUE, result);
    }

    @Test
    public void testGetNameTypeHasIsFalse() {
        OMMAttribInfo attribInfo = configureToReturnNullValues ();

        Integer result = adapter.getServiceId(attribInfo);

        assertNull (result);
    }

    /**
     * It is possible (??) that an instance of OMMData not have a field list --
     * if this happens an exception should not be thrown.
     */
    @Ignore("Review this test as we may need to add null-checking.")
    public void toRFABeanWhereFieldListIsNull() {
        adapter.toRFABean((OMMFieldList)null, new MarketPrice());
    }
}
