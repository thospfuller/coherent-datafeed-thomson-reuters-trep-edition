package com.coherentlogic.coherent.datafeed.adapters.omm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.data.model.core.exceptions.ConversionFailedException;
import com.coherentlogic.coherent.datafeed.factories.AbstractDictionaryFactory;
import com.coherentlogic.coherent.datafeed.factories.DefaultFieldDictionaryFactory;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMEnum;
import com.reuters.rfa.omm.OMMFieldEntry;

/**
 * Unit test for the {@link OMMEnumAdapter} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMEnumAdapterTest {

    private OMMEnumAdapter enumAdapter = null;

    @Before
    public void setUp() throws Exception {

        AbstractDictionaryFactory factory = new DefaultFieldDictionaryFactory ();

        factory.start();

        FieldDictionary fieldDictionary = factory.getInstance();

        enumAdapter = new OMMEnumAdapter (fieldDictionary, new OMMDataAdapter (fieldDictionary));
    }

    @After
    public void tearDown() throws Exception {
        enumAdapter = null;
    }

    @Test
    public void testAdaptEnumBooleanNull() {

        OMMFieldEntry fieldEntry = mock (OMMFieldEntry.class);

        OMMEnum data = mock (OMMEnum.class);

        // 5007 : PRIMARY_MM
        when(fieldEntry.getFieldId()).thenReturn(Short.valueOf("5007"));
        when(fieldEntry.getData(any(Short.class))).thenReturn(data);

        when(data.getValue()).thenReturn(0); // Undefined.

        Boolean result = enumAdapter.adapt(fieldEntry, Boolean.class);

        assertNull (result);
    }

    @Test
    public void testAdaptEnumBooleanTrue() {

        OMMFieldEntry fieldEntry = mock (OMMFieldEntry.class);

        OMMEnum data = mock (OMMEnum.class);

        // 5007 : PRIMARY_MM
        when(fieldEntry.getFieldId()).thenReturn(Short.valueOf("5007"));
        when(fieldEntry.getData(any(Short.class))).thenReturn(data);

        when(data.getValue()).thenReturn(1);

        Boolean result = enumAdapter.adapt(fieldEntry, Boolean.class);

        assertTrue (result);
    }

    @Test
    public void testAdaptEnumBooleanFalse() {

        OMMFieldEntry fieldEntry = mock (OMMFieldEntry.class);

        OMMEnum data = mock (OMMEnum.class);

        // 5007 : PRIMARY_MM
        when(fieldEntry.getFieldId()).thenReturn(Short.valueOf("5007"));
        when(fieldEntry.getData(any(Short.class))).thenReturn(data);

        when(data.getValue()).thenReturn(2);

        Boolean result = enumAdapter.adapt(fieldEntry, Boolean.class);

        assertFalse (result);
    }

    /**
     * TODO: Review this test as right now the OMMEnumAdapter is returning null when the data is not valid and it makes
     *       sense to throw an exception.
     */
    @Test(expected=ConversionFailedException.class)
    public void testAdaptEnumBooleanInvalidValue() {

        OMMFieldEntry fieldEntry = mock (OMMFieldEntry.class);

        OMMEnum data = mock (OMMEnum.class);

        // 5007 : PRIMARY_MM
        when(fieldEntry.getFieldId()).thenReturn(Short.valueOf("5007"));
        when(fieldEntry.getData(any(Short.class))).thenReturn(data);

        when(data.getValue()).thenReturn(99);

        Boolean result = enumAdapter.adapt(fieldEntry, Boolean.class);

        assertNull(result);
    }
}
