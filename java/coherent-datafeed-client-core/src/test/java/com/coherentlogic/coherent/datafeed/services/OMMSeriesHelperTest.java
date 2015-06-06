package com.coherentlogic.coherent.datafeed.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.exceptions.MissingDataException;
import com.reuters.rfa.omm.OMMElementEntry;
import com.reuters.rfa.omm.OMMElementList;
import com.reuters.rfa.omm.OMMNumeric;
import com.reuters.rfa.omm.OMMSeries;

/**
 * Unit test for the {@link OMMSeriesHelper} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMSeriesHelperTest {

    private static final Long DEFAULT_LONG = 1234L;

    private OMMNumeric data = null;

    private OMMElementEntry entry = null;

    private OMMElementList summaryData = null;

    private OMMSeries series = null;

    private OMMSeriesHelper seriesHelper = null;

    @Before
    public void setUp() throws Exception {
        data = mock (OMMNumeric.class);
        entry = mock (OMMElementEntry.class);
        summaryData = mock (OMMElementList.class);
        series = mock (OMMSeries.class);
        seriesHelper = new OMMSeriesHelper();

        when (series.getSummaryData()).thenReturn(summaryData);
        when (summaryData.find(any(String.class))).thenReturn(entry);
        when (entry.getData()).thenReturn(data);
    }

    @After
    public void tearDown() throws Exception {
        data = null;
        entry = null;
        summaryData = null;
        series = null;
        seriesHelper = null;
    }

    @Test
    public void testGetDictionaryTypeWhenSummaryDataIsNotNull() {

        when (data.toLong()).thenReturn(DEFAULT_LONG);

        int result = seriesHelper.getDictionaryType(series);

        assertEquals (DEFAULT_LONG, Long.valueOf("" + result));
    }

    @Test(expected=MissingDataException.class)
    public void testGetDictionaryTypeWhenSummaryDataIsNull() {
        when (summaryData.find(any(String.class))).thenReturn(null);
        seriesHelper.getDictionaryType(series);
    }
}
