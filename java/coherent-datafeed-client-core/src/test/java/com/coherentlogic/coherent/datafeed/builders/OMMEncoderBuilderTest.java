package com.coherentlogic.coherent.datafeed.builders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMTypes;

/**
 * Unit test for the OMMEncoderBuilder class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMEncoderBuilderTest {

    static final String TEST = "test";

    static final short DEFAULT_SHORT_VALUE = 132;

    static final int DEFAULT_INT_VALUE = 10209;

    static final long DEFAULT_LONG_VALUE = 99001;

    static final byte DEFAULT_BYTE_VALUE = 16;

    private OMMEncoder encoder = null;

    private OMMEncoderBuilder<OMMEncoder> builder = null;

    @Before
    public void setUp() throws Exception {
        encoder = mock (OMMEncoder.class);
        builder = new OMMEncoderBuilder<OMMEncoder>(encoder);
    }

    @After
    public void tearDown() throws Exception {
        encoder = null;
        builder = null;
    }

    @Test
    public void encodeAsASCIIString () {
        builder.encodeAsASCIIString(TEST);
        verify (encoder).encodeString (TEST, OMMTypes.ASCII_STRING);
    }

    @Test
    public void encodeElementEntryInit () {
        builder.encodeElementEntryInit(TEST, DEFAULT_SHORT_VALUE);
        verify (encoder).encodeElementEntryInit (TEST, DEFAULT_SHORT_VALUE);
    }

    @Test
    public void encodeElementListInit () {
        builder.encodeElementListInit(
            DEFAULT_INT_VALUE, DEFAULT_SHORT_VALUE, DEFAULT_SHORT_VALUE);
        verify (encoder).encodeElementListInit(
            DEFAULT_INT_VALUE, DEFAULT_SHORT_VALUE, DEFAULT_SHORT_VALUE);
    }

    @Test
    public void encodeMsgInit () {

        OMMMsg ommMsg = mock (OMMMsg.class);

        builder.encodeMsgInit(ommMsg, DEFAULT_SHORT_VALUE, DEFAULT_SHORT_VALUE);
        verify (encoder)
            .encodeMsgInit(ommMsg, DEFAULT_SHORT_VALUE, DEFAULT_SHORT_VALUE);
    }

    @Test
    public void encodeString () {
        builder.encodeString(TEST, DEFAULT_SHORT_VALUE);
        verify (encoder).encodeString(TEST, DEFAULT_SHORT_VALUE);
    }

    @Test
    public void encodeUInt () {
        builder.encodeUInt(DEFAULT_LONG_VALUE);
        verify (encoder).encodeUInt(DEFAULT_LONG_VALUE);
    }

    @Test
    public void getEncodedObject () {
        builder.getEncodedObject();
        verify (encoder).getEncodedObject();
    }
}
