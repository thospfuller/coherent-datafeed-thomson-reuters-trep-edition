package com.coherentlogic.coherent.datafeed.services;

import static com.reuters.rfa.rdm.RDMDictionary.Type.FIELD_DEFINITIONS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.exceptions.DecodingFailedException;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMSeries;

/**
 * Unit test for the {@link FieldDictionaryDecoder} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class FieldDictionaryDecoderTest {

    private FieldDictionaryDecoder decoder = null;

    private OMMSeries series = null;

    @Before
    public void setUp() throws Exception {

        FieldDictionary fieldDictionary = mock (FieldDictionary.class);

        decoder = new FieldDictionaryDecoder (
            fieldDictionary, FIELD_DEFINITIONS);

        series = mock (OMMSeries.class);
    }

    @After
    public void tearDown() throws Exception {
        decoder = null;
        series = null;
    }

    @Test(expected=DecodingFailedException.class)
    public void decode () {

        when(series.has(OMMSeries.HAS_DATA_DEFINITIONS)).thenReturn(false);

        decoder.decode(series);
    }
}
