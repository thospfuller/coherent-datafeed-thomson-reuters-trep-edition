package com.coherentlogic.coherent.datafeed.adapters.omm;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.reuters.rfa.omm.OMMDateTime;
import com.reuters.rfa.omm.OMMFieldEntry;

/**
 * Unit test for the {@link OMMDateTimeAdapter} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMDateTimeAdapterTest {

    private OMMDataAdapter parentAdapter = null;

    private OMMDateTimeAdapter adapter = null;

    private OMMFieldEntry fieldEntry = null;

    @Before
    public void setUp() throws Exception {
        parentAdapter = mock (OMMDataAdapter.class);
        adapter = new OMMDateTimeAdapter (null, parentAdapter);
        fieldEntry = mock (OMMFieldEntry.class);
    }

    @After
    public void tearDown() throws Exception {
        parentAdapter = null;
        adapter = null;
        fieldEntry = null;
    }

    @Test
    public void testAdaptOMMFieldEntry() {

        OMMDateTime value = mock (OMMDateTime.class);

        // June 22 1980
        Long millis = 393566400000L;

        Date date = new Date (millis);

        when (parentAdapter.adapt(fieldEntry)).thenReturn(value);

        when (value.toDate()).thenReturn(date);

        Long result = (Long) adapter.adapt (fieldEntry, Long.class);

        assertEquals(millis, result);
    }
}
