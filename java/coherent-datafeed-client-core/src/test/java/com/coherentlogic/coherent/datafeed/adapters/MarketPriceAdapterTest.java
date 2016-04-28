package com.coherentlogic.coherent.datafeed.adapters;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants;
import com.coherentlogic.coherent.datafeed.factories.NullMarketPriceFactory;
import com.reuters.rfa.omm.OMMDataBuffer;
import com.reuters.rfa.omm.OMMDateTime;
import com.reuters.rfa.omm.OMMEnum;
import com.reuters.rfa.omm.OMMFieldEntry;
import com.reuters.rfa.omm.OMMFieldList;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMNumeric;
import com.reuters.rfa.omm.OMMTypes;

/**
 * Unit test for the {@link MarketPriceAdapter} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketPriceAdapterTest extends AdapterUnitTestHelper {

    private final TypedFactory<MarketPrice> marketPriceFactory =
        new NullMarketPriceFactory();

    private MarketPriceAdapter adapter = null;

    @Before
    public void setUp() throws Exception {
        setUp(marketPriceFactory, MarketPriceAdapter.class);
        adapter = (MarketPriceAdapter) getAdapter();
    }

    @After
    public void tearDown() throws Exception{
        super.tearDown();
    }

    @Test
    public void setBid(){

        short bidFieldId = 22;

        reviewNumericConversion(
            bidFieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getBid",
            DEFAULT_BIG_DECIMAL);
    }

    @Test
    public void setBid1(){
        short bid_1FieldId = 23;

        reviewNumericConversion(
            bid_1FieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getBid1",
            DEFAULT_BIG_DECIMAL);
    }

    @Test
    public void setBid2(){

        short bid_2FieldId = 24;

        reviewNumericConversion(
            bid_2FieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getBid2",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setAsk(){
        short askFieldId = 25;

        reviewNumericConversion(
            askFieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getAsk",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setAsk1() {
        short ask_1FieldId = 26;

        reviewNumericConversion(
            ask_1FieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getAsk1",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setAsk2() {
        short ask_2FieldId = 27;

        reviewNumericConversion(
            ask_2FieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getAsk2",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setBidSize() {
        short bidSizeFieldId = 30;

        reviewNumericConversion(
            bidSizeFieldId,
            OMMTypes.REAL64,
            BigInteger.class,
            DEFAULT_BIG_INTEGER,
            "getBidSize",
            DEFAULT_BIG_INTEGER
        );
    }

    @Test
    public void setAskSize() {
        short askSizeFieldId = 31;

        reviewNumericConversion(
            askSizeFieldId,
            OMMTypes.REAL64,
            BigInteger.class,
            DEFAULT_BIG_INTEGER,
            "getAskSize",
            DEFAULT_BIG_INTEGER
        );
    }

    @Test
    public void setLast() {
        short trdprc_1FieldId = 6;

        reviewNumericConversion(
            trdprc_1FieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getLast",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setLast1() {
        short trdprc_2FieldId = 7;

        reviewNumericConversion(
            trdprc_2FieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getLast1",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setLast2() {
        short trdprc_3FieldId = 8;

        reviewNumericConversion(
            trdprc_3FieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getLast2",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setLast3() {
        short trdprc_4FieldId = 9;

        reviewNumericConversion(
            trdprc_4FieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getLast3",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setLast4() {
        short trdprc_5FieldId = 10;

        reviewNumericConversion(
            trdprc_5FieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getLast4",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setDisplayTemplate() {
        short rdnDisplayFieldId = 2;

        reviewNumericConversion(
            rdnDisplayFieldId,
            OMMTypes.UINT32,
            BigInteger.class,
            DEFAULT_BIG_INTEGER,
            "getDisplayTemplate",
            DEFAULT_BIG_INTEGER
        );
    }

    @Test
    public void setNetChange() {

        short netchng_1FieldId = 11;

        reviewNumericConversion(
            netchng_1FieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getNetChange",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setTodaysHigh () {

        short high1FieldId = 12;

        reviewNumericConversion(
            high1FieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getTodaysHigh",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setTodaysLow () {

        short low1FieldId = 13;

        reviewNumericConversion(
            low1FieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getTodaysLow",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setTickArrow() {

        short tickArrowFieldId = 14;

        short upTick = 1;

        Integer expectedResult = 1;

        reviewEnumConversion(tickArrowFieldId, upTick, "getTickArrow",
            expectedResult);
    }

    @Test
    public void setCurrency() {

        short currencyFieldId = 15;

        // 840: USD in enumtype.def however I'm not entirely sure of this so we
        //      may need to change this as it could be wrong.
        short usdEnumId = 840;

        reviewEnumConversion(currencyFieldId, usdEnumId, "getCurrency", "USD");
    }

    @Test
    public void setTradeDate() {

        short tradeDateFieldId = 16;

        reviewDateTimeConversion(
            tradeDateFieldId,
            OMMTypes.DATE,
            "getTradeDateMillis",
            DEFAULT_LONG_VALUE
        );
    }

    /**
     * This has been tested in production as well.
     */
    @Test
    public void setTradeTime() {

        short tradeDateFieldId = 18;

        reviewDateTimeConversion(
            tradeDateFieldId,
            OMMTypes.TIME,
            "getTradeTimeMillis",
            DEFAULT_LONG_VALUE
        );
    }

    @Test
    public void setDisplayName() {

        short fieldId = 3;

        reviewDataBufferConversion(
            fieldId, "getDisplayName", DEFAULT_STRING_VALUE);
    }

    /**
     * Note that this is working, however in production not every ric will
     * result in this value being set.
     */
    @Test
    public void setIDNExchangeId() {

        short idnExchId = 4, enumValue = 7;

        // 7, "THM", NASDAQ InterMarket
        reviewEnumConversion(
            idnExchId, enumValue, "getIdnExchangeId", "THM");
    }

    /**
     *
     */
    @Test
    public void setPermission() {

        short fieldId = 1;

        reviewNumericConversion(
            fieldId,
            OMMTypes.UINT64,
            BigInteger.class,
            DEFAULT_BIG_INTEGER,
            "getPermission",
            DEFAULT_BIG_INTEGER);
    }

    @Test
    public void setPutCall () {

        short fieldId = 1643;

        short call = 3;

        String expectedResult = "C-AM";

        reviewEnumConversion(fieldId, call, "getPutCall", expectedResult);
    }

    @Test
    public void setPutCallNotApplicable () {

        short fieldId = 1643;

        short call = 0;

        String expectedResult = MarketPriceConstants.NOT_ALLOCATED;

        reviewEnumConversion(fieldId, call, "getPutCall", expectedResult);
    }

    @Test
    public void setImpliedVolatilitytOfAskPrice () {

        short fieldId = 2144;

        reviewNumericConversion(
            fieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getImpliedVolatilitytOfAskPrice",
            DEFAULT_BIG_DECIMAL);
    }

    @Test
    public void setImpliedVolatilitytOfBidPrice () {

        short fieldId = 2145;

        reviewNumericConversion(
            fieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getImpliedVolatilitytOfBidPrice",
            DEFAULT_BIG_DECIMAL);
    }

    @Test
    public void setOpenInterest () {

        short fieldId = 64;

        reviewNumericConversion(
            fieldId,
            OMMTypes.REAL64,
            BigInteger.class,
            DEFAULT_BIG_INTEGER,
            "getOpenInterest",
            DEFAULT_BIG_INTEGER);
    }

    @Test
    public void setOpenInterestNetChange () {

        short fieldId = 65;

        reviewNumericConversion(
            fieldId,
            OMMTypes.REAL64,
            BigInteger.class,
            DEFAULT_BIG_INTEGER,
            "getOpenInterestNetChange",
            DEFAULT_BIG_INTEGER);
    }

    @Test
    public void setStrikePrice () {

        short fieldId = 66;

        reviewNumericConversion(
            fieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getStrikePrice",
            DEFAULT_BIG_DECIMAL);
    }

    @Test
    public void setContractMonth () {

        short fieldId = 41;

        reviewDataBufferConversion(
            fieldId,
            "getContractMonth",
            DEFAULT_STRING_VALUE
        );
    }

    @Test
    public void setLotSizeUnits () {

        short fieldId = 54, enumValue = 9;

        reviewEnumConversion(fieldId, enumValue, "getLotSizeUnits", "M JPY");
    }

    @Test
    public void setOpenAskPrice () {

        short fieldId = 59;

        reviewNumericConversion(
            fieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getOpenAskPrice",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setExpiryDate () {
        short fieldId = 67;

        reviewDateTimeConversion(
            fieldId,
            OMMTypes.DATE,
            "getExpiryDate",
            DEFAULT_LONG_VALUE
        );
    }

    @Test
    public void setSettlementPrice () {
        short fieldId = 70;

        reviewNumericConversion(
            fieldId,
            OMMTypes.REAL64,
            BigDecimal.class,
            DEFAULT_BIG_DECIMAL,
            "getSettlementPrice",
            DEFAULT_BIG_DECIMAL
        );
    }

    @Test
    public void setSettleDate () {
        short fieldId = 288;

        reviewDateTimeConversion(
            fieldId,
            OMMTypes.DATE,
            "getSettleDate",
            DEFAULT_LONG_VALUE
        );
    }

    /**
     * Method is used to creat a mock instance of {@link OMMMsg}.
     *
     * @param enumValue For example 1, which points to " U" in the enumtype.def
     *  file.
     *
     * @return A mock instance of {@link OMMMsg}.
     */
    public OMMMsg createEnum(final short fieldId, final int enumValue) {

        OMMMsg ommMsg = mock(OMMMsg.class);

        OMMFieldList fieldList = mock(OMMFieldList.class);

        when(ommMsg.getPayload()).thenReturn(fieldList);

        OMMFieldEntry fieldEntry = mock(OMMFieldEntry.class);

        when(fieldList.iterator()).thenReturn(
                new FieldListIterator(fieldEntry));

        when(fieldEntry.getFieldId()).thenReturn(fieldId);

        OMMEnum ommEnum = mock(OMMEnum.class);

        // 1: up tick or zero uptick -- see enumtype.def (though I'm not
        //    entirely certain this 1 applies here).
        when(ommEnum.getValue()).thenReturn(enumValue);

        when(
            fieldEntry.getData(OMMTypes.ENUM)
        ).thenReturn(ommEnum);

        return ommMsg;
    }

    public OMMMsg createNumeric(
        final short fieldId,
        final short ommNumericType,
        final Class<? extends Number> returnedAs,
        final Number value
    ) {

        OMMMsg ommMsg = mock(OMMMsg.class);

        OMMFieldList fieldList = mock(OMMFieldList.class);

        when (ommMsg.getPayload()).thenReturn(fieldList);

        OMMFieldEntry fieldEntry = mock(OMMFieldEntry.class);

        when(fieldList.iterator()).thenReturn(
            new FieldListIterator(fieldEntry));

        when(fieldEntry.getFieldId()).thenReturn(fieldId);

        OMMNumeric ommNumeric = mock(OMMNumeric.class);

        if (BigDecimal.class.equals(returnedAs)) {
            when(ommNumeric.toBigDecimal()).thenReturn(
                new BigDecimal (value.toString()));
        } else if (BigInteger.class.equals(returnedAs)) {
            when(ommNumeric.toBigInteger()).thenReturn(
                new BigInteger (value.toString()));
        }

        when(
            fieldEntry.getData(ommNumericType)
        ).thenReturn(ommNumeric);

        return ommMsg;
    }

    public OMMMsg createDataBuffer(
        final short fieldId,
        final short ommDataBufferType,
        final String value
    ) {
        OMMMsg ommMsg = mock(OMMMsg.class);

        OMMFieldList fieldList = mock(OMMFieldList.class);

        when(ommMsg.getPayload()).thenReturn(fieldList);

        OMMFieldEntry fieldEntry = mock(OMMFieldEntry.class);

        when(fieldList.iterator()).thenReturn(
            new FieldListIterator(fieldEntry));

        when(fieldEntry.getFieldId()).thenReturn(fieldId);

        OMMDataBuffer ommDataBuffer = mock(OMMDataBuffer.class);

        when(fieldEntry.getData(ommDataBufferType)).thenReturn(ommDataBuffer);

        when(ommDataBuffer.getBytes()).thenReturn(value.getBytes());

        return ommMsg;
    }

    public OMMMsg createDateTime(final short fieldId, final short ommType) {

        Date date = new Date(DEFAULT_LONG_VALUE);

        return create(fieldId, ommType, date);
    }

    public OMMMsg createTime(final short fieldId) {

        Date date = new Date(DEFAULT_LONG_VALUE);

        return create(fieldId, OMMTypes.TIME, date);
    }

    public OMMMsg createDataBuffer(final short fieldId) {
        return create(fieldId, OMMTypes.RMTES_STRING, DEFAULT_STRING_VALUE);
    }

    public OMMMsg create(
        final short fieldId, final short ommType, final Date resultantDate) {

        OMMMsg ommMsg = mock(OMMMsg.class);

        OMMFieldList fieldList = mock(OMMFieldList.class);

        when(ommMsg.getPayload()).thenReturn(fieldList);

        OMMFieldEntry fieldEntry = mock (OMMFieldEntry.class);

        when(fieldList.iterator()).thenReturn(
                new FieldListIterator(fieldEntry));

        when(fieldEntry.getFieldId()).thenReturn(fieldId);

        OMMDateTime ommDateTime = mock(OMMDateTime.class);

        when(fieldEntry.getData(ommType)).thenReturn(ommDateTime);

        when(ommDateTime.toDate()).thenReturn(resultantDate);

        return ommMsg;
    }

    public OMMMsg create(
        final short fieldId, final short ommType, final String value) {

        OMMMsg ommMsg = mock(OMMMsg.class);

        OMMFieldList fieldList = mock(OMMFieldList.class);

        when(ommMsg.getPayload()).thenReturn(fieldList);

        OMMFieldEntry fieldEntry = mock(OMMFieldEntry.class);

        when(fieldList.iterator()).thenReturn(
                new FieldListIterator(fieldEntry));

        when(fieldEntry.getFieldId()).thenReturn(fieldId);

        OMMDataBuffer ommDataBuffer = mock(OMMDataBuffer.class);

        when(fieldEntry.getData(ommType)).thenReturn(ommDataBuffer);

        when(ommDataBuffer.getBytes()).thenReturn(value.getBytes());

        return ommMsg;
    }

    private Object callGetterMethodOn(
        final MarketPrice marketPrice,
        final String getterMethodName
    ) {

        Method getterMethod;

        try {
            getterMethod = MarketPrice.class.getMethod(getterMethodName);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to find the getter method '" +
                getterMethodName + "'.", exception);
        }

        Object actualResult;

        try {
            actualResult = getterMethod.invoke(marketPrice);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to invoke the getter method.",
                exception);
        }
        return actualResult;
    }

    private void reviewNumericConversion(
        final short fieldId,
        final short ommNumericType,
        final Class<? extends Number> returnedAs,
        final Number value,
        final String getterMethodName,
        final Number expectedResult
    ) {

        OMMMsg ommMsg = createNumeric(
            fieldId,
            ommNumericType,
            returnedAs,
            value);

        MarketPrice marketPrice = adapter.adapt(ommMsg);

        Object actualResult = callGetterMethodOn(
            marketPrice, getterMethodName);

        assertEquals(expectedResult, actualResult);
    }

    private void reviewEnumConversion(
        final short fieldId,
        final short enumValue,
        final String getterMethodName,
        final Object expectedResult
    ) {
        OMMMsg ommMsg = createEnum(fieldId, enumValue);

        MarketPrice marketPrice = adapter.adapt(ommMsg);

        Object actualResult = callGetterMethodOn(
            marketPrice, getterMethodName);

        assertEquals(expectedResult, actualResult);
    }

    private void reviewDateTimeConversion(
        final short fieldId,
        final short ommType,
        final String getterMethodName,
        final Long expectedResult
    ) {
        OMMMsg ommMsg = createDateTime(fieldId, ommType);

        MarketPrice marketPrice = adapter.adapt(ommMsg);

        Long actualResult = (Long) callGetterMethodOn(
            marketPrice, getterMethodName);

        assertEquals(expectedResult, actualResult);
    }

    private void reviewDataBufferConversion(
        final short fieldId,
        final String getterMethodName,
        final String expectedResult
    ) {
        OMMMsg ommMsg = createDataBuffer(fieldId);

        MarketPrice marketPrice = adapter.adapt(ommMsg);

        String actualResult = (String) callGetterMethodOn(
            marketPrice, getterMethodName);

        assertEquals(expectedResult, actualResult);
    }
}

class FieldListIterator implements Iterator<OMMFieldEntry> {

    private OMMFieldEntry fieldEntry;

    private boolean hasNextFlag = true;

    public FieldListIterator(final OMMFieldEntry fieldEntry) {
        super();
        this.fieldEntry = fieldEntry;
    }

    @Override
    public boolean hasNext() {

        boolean result = hasNextFlag;

        hasNextFlag = false;

        return result;
    }

    @Override
    public OMMFieldEntry next() {
        return fieldEntry;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}