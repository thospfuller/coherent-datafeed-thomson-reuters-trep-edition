package com.coherentlogic.coherent.datafeed.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.adapters.AdapterUnitTestHelper;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMTypes;
import com.reuters.ts1.TS1DefDb;

public class CachedTimeSeriesLoaderTest {

    private static final String NAME = "whatever";

    private FieldDictionary fieldDictionary = null;

    // private EmbeddedCacheManager cacheManager = null;

    private TimeSeriesLoader loader = null;

    @Before
    public void setUp() throws Exception {

        //cacheManager = new DefaultCacheManager();

        fieldDictionary = AdapterUnitTestHelper.getFieldDictionary();

        TS1DefDb ts1DefDb = new TS1DefDb ();

        // Cache<Handle, TimeSeriesEntries> cache = cacheManager.getCache();

        loader = new TimeSeriesLoader(fieldDictionary, ts1DefDb);

    }

    @After
    public void tearDown() throws Exception {
        fieldDictionary = null;
        //cacheManager = null;
        loader = null;
    }

    // @Test
    public void testGetNameOMMItemEvent() {
        // loader.getName(msg)
    }

    // @Test
    public void testGetNameOMMMsg() {
        fail("Not yet implemented");
    }

    /**
     * Is this test really worth keeping?
     */
    @Test
    public void testGetNameOMMAttribInfoHasNameIsTrue() {

        OMMAttribInfo attribInfo = mock(OMMAttribInfo.class);

        when(attribInfo.has(OMMAttribInfo.HAS_NAME)).thenReturn(true);
        when(attribInfo.getName()).thenReturn(NAME);

        String name = loader.getName(attribInfo);

        assertEquals(NAME, name);
    }

    /**
     * Is this test really worth keeping?
     */
    @Test
    public void testGetNameOMMAttribInfoHasNameIsFalse() {

        OMMAttribInfo attribInfo = mock(OMMAttribInfo.class);

        when(attribInfo.has(OMMAttribInfo.HAS_NAME)).thenReturn(false);

        String name = loader.getName(attribInfo);

        assertNull(name);
    }

    @Test
    public void testGetFieldDataWhereTypeIsNotFieldList () {

        String expectedResult = 
            "433 \"UNCHANGED VOL\"      \"UNCHGD VOL\" SM FT VO N" +
            "                ";

        OMMMsg msg = mock(OMMMsg.class);
        when (msg.getDataType()).thenReturn(OMMTypes.NO_DATA);

//        OMMFieldList fieldList = mock (OMMFieldList.class);
//        when (msg.getPayload()).thenReturn(fieldList);
//
//        OMMData data = mock (OMMData.class);
//
//        OMMFieldEntry fieldEntry = mock (OMMFieldEntry.class);
//        when (fieldEntry.getData(anyShort ())).thenReturn(data);
//
//        when (fieldList.find (anyShort ())).thenReturn(fieldEntry);
//
//        when (data.getBytes()).thenReturn(expectedResult.getBytes());

        short fieldId = 217;

        OMMData resultantData = loader.getFieldData(msg, fieldId);

        assertNull(resultantData);
    }

//    // @Test
//    public void getBytes() {
//
//        byte[] expectedResults = new byte[] { 32, 33, 127, -40, 32, 32, 57, 91,
//                65, -45, 48, -31, 32, 76, 39, 60, -17, 108, 67, 62, -88, -53,
//                -50, 66, 33, -35, -85, -26, 55, 106, 32, 111, 93, -29, 61, -75,
//                -56, 49, 106, -27, 36, -77, -45, 97, 54, 124, 40, 121, -13,
//                -75, 122, 40, -55, -48, -56, 54, 32, -95, 88, 72, 34, -43, 50,
//                93, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
//
//        OMMItemEvent itemEvent = mock(OMMItemEvent.class);
//        OMMFieldList fieldList = mock(OMMFieldList.class);
//        OMMMsg msg = mock(OMMMsg.class);
//        OMMFieldEntry fieldEntry = mock(OMMFieldEntry.class);
//        OMMEnum ommEnum = mock(OMMEnum.class);
//
//        when(itemEvent.getMsg()).thenReturn(msg);
//        when(msg.getDataType()).thenReturn(OMMTypes.FIELD_LIST);
//        when(msg.getPayload()).thenReturn(fieldList);
//        when(fieldList.find(anyShort())).thenReturn(fieldEntry);
//        when(fieldEntry.getData(anyShort())).thenReturn(ommEnum);
//        when(ommEnum.getValue()).thenReturn(217);
//
//        OMMData data = mock(OMMData.class);
//
//        // when (data.getBytes(any(byte[].class))).then
//        //
//        // byte[] actualResults = loader.getBytes(itemEvent);
//        //
//        // System.out.println("actualResults: " +
//        // ToStringBuilder.reflectionToString(actualResults));
//        //
//        // assertEquals (expectedResults, actualResults);
//
//    }
}
