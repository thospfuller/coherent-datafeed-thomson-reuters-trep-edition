package com.coherentlogic.coherent.datafeed.builders;

import static com.coherentlogic.coherent.datafeed.builders.OMMEncoderBuilderTest.DEFAULT_BYTE_VALUE;
import static com.coherentlogic.coherent.datafeed.builders.OMMEncoderBuilderTest.DEFAULT_INT_VALUE;
import static com.coherentlogic.coherent.datafeed.builders.OMMEncoderBuilderTest.DEFAULT_SHORT_VALUE;
import static com.coherentlogic.coherent.datafeed.builders.OMMEncoderBuilderTest.TEST;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyShort;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.exceptions.MissingDataException;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMPool;
import com.reuters.rfa.omm.OMMPriority;

/**
 * Unit test for the OMMMsgBuilder class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class OMMMsgBuilderTest {

    private OMMMsg ommMsg = null;

    private OMMPool pool = null;

    private OMMEncoder encoder = null;

    private OMMMsgBuilder<RequestMessageBuilder> builder = null;

    @Before
    public void setUp() throws Exception {
        ommMsg = mock (OMMMsg.class);

        pool = mock (OMMPool.class);
        when (pool.acquireMsg()).thenReturn(ommMsg);

        encoder = mock (OMMEncoder.class);

        builder =
            new OMMMsgBuilder<RequestMessageBuilder> (pool, encoder);
    }

    @After
    public void tearDown() throws Exception {
        ommMsg = null;
        pool = null;
        encoder = null;
        builder = null;
    }

    @Test
    public void createOMMMsg () {
        builder.createOMMMsg();
        verify (pool).acquireMsg ();
    }

    @Test
    public void encodeMsgInit () {
        builder.encodeMsgInit(
            DEFAULT_SHORT_VALUE,
            DEFAULT_SHORT_VALUE);
        verify(encoder).encodeMsgInit(
            any (OMMMsg.class),
            anyShort(),
            anyShort());
    }

    @Test
    public void setMsgType () {
        builder.createOMMMsg();
        builder.setMsgType(DEFAULT_BYTE_VALUE);
        verify (ommMsg).setMsgType(DEFAULT_BYTE_VALUE);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void setMsgType1 () {
        builder.setMsgType(DEFAULT_BYTE_VALUE);
    }

    @Test
    public void setMsgModelType () {
        builder.createOMMMsg();
        builder.setMsgModelType(DEFAULT_BYTE_VALUE);
        verify (ommMsg).setMsgModelType(DEFAULT_BYTE_VALUE);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void setMsgModelType1 () {
        builder.setMsgModelType(DEFAULT_BYTE_VALUE);
    }

    @Test
    public void setIndicationFlags () {
        builder.createOMMMsg();
        builder.setIndicationFlags(DEFAULT_INT_VALUE);
        verify (ommMsg).setIndicationFlags(DEFAULT_INT_VALUE);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void setIndicationFlags1 () {
        builder.setIndicationFlags(DEFAULT_INT_VALUE);
    }

    @Test(expected=MissingDataException.class)
    public void setAttribInfo1 () {
        builder.createOMMMsg();
        builder.setAttribInfo(TEST, DEFAULT_SHORT_VALUE);
    }

    @Test(expected=MissingDataException.class)
    public void setAttribInfo2 () {
        builder.createOMMMsg();
        builder.setAttribInfo(TEST, DEFAULT_SHORT_VALUE, new String [] {});
    }

    @Test
    public void setAttribInfo3 () {

        OMMAttribInfo attribInfo = mock (OMMAttribInfo.class);

        when (pool.acquireAttribInfo()).thenReturn(attribInfo);

        builder.createOMMMsg();
        builder.setAttribInfo(TEST, DEFAULT_SHORT_VALUE, TEST);

        verify (ommMsg, times (1)).setAttribInfo(
            TEST, TEST, DEFAULT_SHORT_VALUE);
    }

    @Test
    public void setAttribInfo4 () {

        OMMAttribInfo attribInfo = mock (OMMAttribInfo.class);

        when (pool.acquireAttribInfo()).thenReturn(attribInfo);

        builder.createOMMMsg();
        builder.setAttribInfo(TEST, DEFAULT_SHORT_VALUE, TEST, TEST);

        verify (ommMsg, times (2)).setAttribInfo(
            TEST, TEST, DEFAULT_SHORT_VALUE);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void setAttribInfo5 () {
        builder.setAttribInfo(TEST, DEFAULT_SHORT_VALUE, TEST, TEST);
    }

    @Test
    public void setAttribInfo6 () {

        OMMAttribInfo attribInfo = mock (OMMAttribInfo.class);

        when (pool.acquireAttribInfo()).thenReturn(attribInfo);

        String idnRdf = Constants.IDN_RDF;

        builder.createOMMMsg();
        builder.setAttribInfo(idnRdf, DEFAULT_SHORT_VALUE, TEST);

        verify (ommMsg).setAttribInfo(
            idnRdf.toString(),
            TEST,
            DEFAULT_SHORT_VALUE
        );
    }

    /* Note that prior to removing the ServiceName (enum) the method that
     * took a ServiceName (juxtaposed with a String) performed a check, as
     * is below, where if the serviceName is null an exception is thrown.
     *
     * Once we removed the ServiceName enum, that method was no longer
     * necessary and hence we only have this method.
     *
     * Since authentication does not require a serviceName we're removing
     * the assertNotNull check from below, which means a unit test has been
     * disabled as well.
     */
//    @Test(expected=NullPointerRuntimeException.class)
//    public void setAttribInfo7 () {
//
//        String idnRdf = null;
//
//        builder.createOMMMsg();
//        builder.setAttribInfo(idnRdf, DEFAULT_SHORT_VALUE, TEST);
//    }

    @Test
    public void setAttribInfo8 () {

        OMMAttribInfo attribInfo = mock (OMMAttribInfo.class);

        when (pool.acquireAttribInfo()).thenReturn(attribInfo);

        String idnRdf = Constants.IDN_RDF;

        builder.createOMMMsg();
        builder.setAttribInfo(idnRdf, DEFAULT_SHORT_VALUE, TEST);
    }

    @Test
    public void setAttribInfo9 () {

        OMMAttribInfo attribInfo = mock (OMMAttribInfo.class);

        when (pool.acquireAttribInfo()).thenReturn(attribInfo);

        String idnRdf = Constants.IDN_RDF;

        builder.createOMMMsg();
        builder.setAttribInfo(
            idnRdf, DEFAULT_SHORT_VALUE, (Integer) null, TEST);

        verify(attribInfo).setName(Constants.IDN_RDF);
        verify(attribInfo).setNameType(DEFAULT_SHORT_VALUE);
        verify(attribInfo, never()).setFilter(any(Integer.class));

        verify(ommMsg, times(1)).setAttribInfo(
            any(String.class), any(String.class), any(Short.class));
    }

    @Test
    public void setPriority () {
        builder.createOMMMsg();
        builder.setPriority(DEFAULT_BYTE_VALUE, DEFAULT_INT_VALUE);
        verify (ommMsg).setPriority(DEFAULT_BYTE_VALUE, DEFAULT_INT_VALUE);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void setPriority1 () {
        builder.setPriority(DEFAULT_BYTE_VALUE, DEFAULT_INT_VALUE);
    }

    @Test
    public void setPriority2 () {
        builder.createOMMMsg();
        builder.setPriority(OMMPriority.DEFAULT);
        verify (ommMsg).setPriority(OMMPriority.DEFAULT);
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void setPriority3 () {
        builder.setPriority(OMMPriority.DEFAULT);
    }
}
