package com.coherentlogic.coherent.datafeed.adapters.json;

import static com.coherentlogic.coherent.datafeed.factories.MethodMapFactory.analyze;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants;

/**
 * Unit test for the {@link RFABeanToJSONAdapter} class.
 *
 * Note that Short and Integer instances will not be quoted, whereas BigDecimal
 * and BigInteger instances will have quotes as this is how XStream handles
 * these types when converting into JSON.
 *
 * @TODO: Review all commented tests and fix where appropriate.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class JSONAdapterTest implements MarketPriceConstants {

    private static final String DEFAULT_BIG_INTEGER_TEXT = "456";

    private static final BigInteger DEFAULT_BIG_INTEGER =
        new BigInteger (DEFAULT_BIG_INTEGER_TEXT);

    private static final Integer DEFAULT_INTEGER = new Integer (
        DEFAULT_BIG_INTEGER_TEXT);

    private static final String DEFAULT_BIG_DECIMAL_TEXT = "123.45",
        DEFAULT_STRING = "fubar";

    private static final BigDecimal DEFAULT_BIG_DECIMAL =
        new BigDecimal (DEFAULT_BIG_DECIMAL_TEXT);

    private static final Long DEFAULT_LONG = 45678L;

    private static final String DEFAULT_LONG_TEXT = DEFAULT_LONG.toString ();

    /**
     * Note that quoting the braces is the same thing as escaping them and if we
     * don't do this the string won't be parsed properly.
     */
    private static final String QUOTED_PATTERN =
        "'{'\"marketPrice\":'{'\"{0}\":\"{1}\"'}}'",
        UNQUOTED_PATTERN = "'{'\"marketPrice\":'{'\"{0}\":{1}'}}'";

    private Map<String, Method> methodMap = analyze (MarketPrice.class);

    private RFABeanToJSONAdapter<MarketPrice> jsonGenerator = null;

    @Before
    public void setUp () throws Exception {
        jsonGenerator = new RFABeanToJSONAdapter<MarketPrice> (new Class[]{MarketPrice.class});
    }

    @After
    public void tearDown () throws Exception {
        jsonGenerator = null;
    }

    static String createExpectationFor (String variable, String value) {
        return MessageFormat.format (QUOTED_PATTERN, variable, value);
    }

    static String createExpectationFor (String pattern, String variable,
        Number value) {
        return MessageFormat.format (pattern, variable, value);
    }

    static String createExpectationFor (String pattern, String variable,
        String value) {
        return MessageFormat.format (pattern, variable, value);
    }

    /**
     * 
     * @param methodName
     * @param paramType
     * @param actualValue
     * @param variableName
     * @param resultantText
     * @param expectedResult
     */
    private void reviewWhereNoAnnotationIsPresent (
        String methodName,
        Class<?> paramType,
        Object actualValue,
        String variableName,
        String resultantText,
        String expectationPattern
    ) {
        Method targetMethod;

        try {
            targetMethod = MarketPrice.class.getMethod (methodName, paramType);
        } catch (Exception exception) {
            throw new RuntimeException ("Unable to get the method named " +
                methodName + " with paramType " + paramType, exception);
        }

        MarketPrice marketPrice = new MarketPrice ();

        try {
            targetMethod.invoke (marketPrice, actualValue);
        } catch (Exception exception) {
            throw new RuntimeException ("Unable to get the method named " +
                methodName + " with paramType " + paramType, exception);
        }

        String expected = createExpectationFor (
            expectationPattern, variableName, resultantText);

        String actual = jsonGenerator.adapt (marketPrice);

        assertEquals (expected, actual);
    }

    private void reviewBigDecimalConversion (String variableName) {
        reviewConversion (
            DEFAULT_BIG_DECIMAL,
            variableName,
            DEFAULT_BIG_DECIMAL_TEXT,
            UNQUOTED_PATTERN
        );
    }

    private void reviewBigIntegerConversion (String variableName) {
        reviewConversion (
            DEFAULT_BIG_INTEGER,
            variableName,
            DEFAULT_BIG_INTEGER_TEXT,
            UNQUOTED_PATTERN
        );
    }

    private void reviewIntegerConversion (String variableName) {
        reviewConversion (
            DEFAULT_INTEGER,
            variableName,
            DEFAULT_BIG_INTEGER_TEXT,
            UNQUOTED_PATTERN
        );
    }

    private void reviewLongConversion (String variableName) {
        reviewConversion (
            DEFAULT_LONG,
            variableName,
            DEFAULT_LONG_TEXT,
            UNQUOTED_PATTERN
        );
    }

    private void reviewStringConversion (String variableName) {
        reviewConversion (
            DEFAULT_STRING,
            variableName,
            DEFAULT_STRING,
            QUOTED_PATTERN
        );
    }

    /**
     * Method tests the conversion of the variable with the specified
     * variableName into JSON. The actualValue is passed in and the
     * resultantText concerns what we expect the result to be.
     * The expectationPattern is used as a template for what we expect the
     * format of the JSON to look like.
     */
    private void reviewConversion (
        Object actualValue,
        String variableName,
        String resultantText,
        String expectationPattern
    ) {
        Method targetMethod;

        try {
            targetMethod = methodMap.get (variableName);
        } catch (Exception exception) {
            throw new RuntimeException ("Unable to get the method " +
                "associated with the variable named " + variableName + ".",
                exception);
        }

        MarketPrice marketPrice = new MarketPrice ();

        try {
            targetMethod.invoke (marketPrice, actualValue);
        } catch (Exception exception) {
            throw new RuntimeException ("Unable to get the method " +
                "associated with the variable named " + variableName + ".",
                exception);
        }

        String expected = createExpectationFor (
            expectationPattern, variableName, resultantText);

        String actual = jsonGenerator.adapt (marketPrice);

        assertEquals (expected, actual);
    }

    /**
     * The adapt method is called a lot, so we do not check for null values here
     * -- instead we assume the method will be called with a valid reference,
     * and in the event that null is passed, an exception should not be thrown.
     */
    @Test
    public void adaptPassingNullParam () {
        jsonGenerator.adapt (null);
    }

    @Test
    public void setTradeTimeMillis () {
        reviewConversion (
            DEFAULT_LONG,
            TRDTIM_1,
            DEFAULT_LONG_TEXT,
            UNQUOTED_PATTERN
        );
    }

//    /**
//     * Note: requires awareness of the setter method name.
//     */
//    @Test
//    public void setSvcName () {
//        reviewWhereNoAnnotationIsPresent (
//            SET_SERVICE_NAME,
//            String.class,
//            DEFAULT_STRING,
//            SVC_NAME,
//            DEFAULT_STRING,
//            QUOTED_PATTERN
//        );
//    }

//    /**
//     *
//     */
//    @Test
//    public void setSvcId () {
//
//        int integerVal = 231;
//
//        MarketPrice marketPrice = new MarketPrice ();
//
//        marketPrice.setServiceId (integerVal);
//
//        String expected = createExpectationFor (
//            UNQUOTED_PATTERN,
//            SVC_ID,
//            integerVal);
//
//        String actual = jsonGenerator.adapt (marketPrice);
//
//        assertEquals (expected, actual);
//    }

//    /**
//     *
//     */
//    @Test
//    public void setNameType () {
//
//        short shortVal = 99;
//
//        MarketPrice marketPrice = new MarketPrice ();
//
//        marketPrice.setNameType (shortVal);
//
//        String expected = createExpectationFor (
//            UNQUOTED_PATTERN,
//            NAME_TYPE,
//            "" + shortVal);
//
//        String actual = jsonGenerator.adapt (marketPrice);
//
//        assertEquals (expected, actual);
//    }

//    /**
//     * Note: requires awareness of the setter method name.
//     */
//    @Test
//    public void setName () {
//        reviewWhereNoAnnotationIsPresent (
//            SET_NAME,
//            String.class,
//            DEFAULT_STRING,
//            NAME,
//            DEFAULT_STRING,
//            QUOTED_PATTERN
//        );
//    }

    /**
     *
     */
    @Test
    public void setPermission () {
        reviewBigIntegerConversion (PROD_PERM);
    }

    @Test
    public void setDisplayTemplate () {
        reviewBigIntegerConversion (RDNDISPLAY);
    }

    /**
     *
     */
    @Test
    public void setBidSize () {
        reviewBigIntegerConversion (BIDSIZE);
    }

    /**
     *
     */
    @Test
    public void setAskSize () {
        reviewBigIntegerConversion (ASKSIZE);
    }

    /**
     *
     */
    @Test
    public void setBid () {
        reviewBigDecimalConversion (BID);
    }

    /**
     *
     */
    @Test
    public void setBid1 () {
        reviewBigDecimalConversion (BID_1);
    }

    /**
     *
     */
    @Test
    public void setBid2 () {
        reviewBigDecimalConversion (BID_2);
    }

    /**
     *
     */
    @Test
    public void setAsk () {
        reviewBigDecimalConversion (ASK);
    }

    /**
     *
     */
    @Test
    public void setAsk1 () {
        reviewBigDecimalConversion (ASK_1);
    }

    /**
     *
     */
    @Test
    public void setAsk2 () {
        reviewBigDecimalConversion (ASK_2);
    }

    /**
     *
     */
    @Test
    public void setTrdPrc1 () {
        reviewBigDecimalConversion (TRDPRC_1);
    }

    /**
     *
     */
    @Test
    public void setTrdPrc2 () {
        reviewBigDecimalConversion (TRDPRC_2);
    }

    /**
     *
     */
    @Test
    public void setTrdPrc3 () {
        reviewBigDecimalConversion (TRDPRC_3);
    }

    /**
     *
     */
    @Test
    public void setTrdPrc4 () {
        reviewBigDecimalConversion (TRDPRC_4);
    }

    /**
     *
     */
    @Test
    public void setTrdPrc5 () {
        reviewBigDecimalConversion (TRDPRC_5);
    }

    /**
     *
     */
    @Test
    public void setNetChng1 () {
        reviewBigDecimalConversion (NETCHNG_1);
    }

    /**
     *
     */
    @Test
    public void setHigh1 () {
        reviewBigDecimalConversion (HIGH_1);
    }

    /**
     *
     */
    @Test
    public void setLow1 () {
        reviewBigDecimalConversion (LOW_1);
    }

    /**
     *
     */
    @Test
    public void setIDNExchangeId () {
        reviewStringConversion (RDN_EXCHID);
    }

    /**
     *
     */
    @Ignore("TODO: Fix this test.")
    public void setDisplayName () {
        reviewStringConversion (DSPLY_NAME);
    }

    /**
     *
     */
    @Test
    public void setTickArrow () {
        reviewIntegerConversion(PRCTCK_1);
    }

    @Test
    public void setCurrency () {
        reviewStringConversion (CURRENCY);
    }

    @Test
    public void setOpenPrice () {
        reviewBigDecimalConversion (OPEN_PRC);
    }

    @Test
    public void setHistoricClose () {
        reviewBigDecimalConversion (HST_CLOSE);
    }

    @Test
    public void setNews () {
        reviewStringConversion (NEWS);
    }

    @Test
    public void setNewsTime () {
        reviewLongConversion (NEWS_TIME);
    }

    @Test
    public void setVolumeAccumulated () {
        reviewBigIntegerConversion (ACVOL_1);
    }

    @Test
    public void setEarnings () {
        reviewBigDecimalConversion (EARNINGS);
    }

    @Test
    public void setYield () {
        reviewBigDecimalConversion (YIELD);
    }

    @Test
    public void setPERatio () {
        reviewBigDecimalConversion (PERATIO);
    }

    @Test
    public void setDividendType () {
        reviewStringConversion (DIVIDENDTP);
    }

    @Test
    public void setDividendPayDate () {
        reviewLongConversion (DIVPAYDATE);
    }

    @Test
    public void setExDividendDate () {
        reviewLongConversion (EXDIVDATE);
    }

    @Test
    public void setTradePriceQualifier () {
        reviewStringConversion (CTS_QUAL);
    }

    @Test
    public void setBlockCount () {
        reviewBigIntegerConversion (BLKCOUNT);
    }

    @Test
    public void setBlockVolume () {
        reviewBigIntegerConversion (BLKVOLUM);
    }

    @Test
    public void setTradeExchangeId () {
        reviewStringConversion (TRDXID_1);
    }

    @Test
    public void setTradingUnits () {
        reviewStringConversion (TRD_UNITS);
    }

    @Test
    public void setLotSize () {
        reviewBigIntegerConversion (LOT_SIZE);
    }

    @Test
    public void setPercentChange () {
        reviewBigDecimalConversion (PCTCHNG);
    }

    @Test
    public void setOpenBid () {
        reviewBigDecimalConversion (OPEN_BID);
    }

    @Test
    public void setLatestDowJonesNewsStoryTime () {
        reviewLongConversion (DJTIME);
    }

    @Test
    public void setCloseBid () {
        reviewBigDecimalConversion (CLOSE_BID);
    }

    @Test
    public void setCloseAsk () {
        reviewBigDecimalConversion (CLOSE_ASK);
    }

    @Test
    public void setDividend () {
        reviewBigDecimalConversion (DIVIDEND);
    }

    @Test
    public void setTotalTradesToday () {
        reviewBigIntegerConversion (NUM_MOVES);
    }

    @Test
    public void setOfficialCode () {
        reviewStringConversion (OFFCL_CODE);
    }

    @Test
    public void setHistoricCloseDate () {
        reviewLongConversion (HSTCLSDATE);
    }

    @Test
    public void setYearHigh () {
        reviewBigDecimalConversion (YRHIGH);
    }

    @Test
    public void setYearLow () {
        reviewBigDecimalConversion (YRLOW);
    }

    @Test
    public void setTurnover () {
        reviewBigDecimalConversion (TURNOVER);
    }

    @Test
    public void setBondType () {
        reviewStringConversion (BOND_TYPE);
    }

    @Test
    public void setBackgroundPage () {
        reviewStringConversion (BCKGRNDPAG);
    }

    @Test
    public void setYearOrContractHighIndicator () {
        reviewStringConversion (YCHIGH_IND);
    }

    @Test
    public void setYearOrContractLowIndicator () {
        reviewStringConversion (YCLOW_IND);
    }

    @Test
    public void setBidNetChange () {
        reviewBigDecimalConversion (BID_NET_CH);
    }

    @Test
    public void setBidTick1 () {
        reviewStringConversion (BID_TICK_1);
    }

    @Test
    public void setCumExMarker () {
        reviewStringConversion (CUM_EX_MKR);
    }

    @Test
    public void setPriceCode () {
        reviewStringConversion (PRC_QL_CD);
    }

    @Test
    public void setNasdStatus () {
        reviewStringConversion (NASDSTATUS);
    }

    @Test
    public void setPriceCode2 () {
        reviewStringConversion (PRC_QL2);
    }

    @Test
    public void setTradeVolume () {
        reviewBigIntegerConversion (TRDVOL_1);
    }

    @Test
    public void setTodaysHighBid () {
        reviewBigDecimalConversion (BID_HIGH_1);
    }

    @Test
    public void setTodaysLowBid () {
        reviewBigDecimalConversion (BID_LOW_1);
    }

    @Test
    public void setYearHighBid () {
        reviewBigDecimalConversion (YRBIDHIGH);
    }

    @Test
    public void setYearLowBid () {
        reviewBigDecimalConversion (YRBIDLOW);
    }

    @Test
    public void setHistoricalClosingBid () {
        reviewBigDecimalConversion (HST_CLSBID);
    }

    @Test
    public void setHistoricalClosingBidDate () {
        reviewLongConversion (HSTCLBDDAT);
    }

    /**
     * @todo Should this be a String?
     */
    @Test
    public void setYearBidHigh () {
        reviewStringConversion (YRBDHI_IND);
    }

    /**
     * @todo Should this be a String?
     */
    @Test
    public void setYearBidLow () {
        reviewStringConversion (YRBDLO_IND);
    }

    @Test
    public void setNumberOfBids () {
        reviewBigIntegerConversion (NUM_BIDS);
    }

    @Test
    public void setRecordType () {
        reviewBigIntegerConversion (RECORDTYPE);
    }

    @Test
    public void setMarketParticipantBidId () {
        reviewStringConversion (BID_MMID1);
    }

    @Test
    public void setMarketParticipantAskId () {
        reviewStringConversion (ASK_MMID1);
    }

    @Test
    public void setOptionExchangeId () {
        reviewStringConversion (OPTION_XID);
    }

    @Test
    public void setYearHighDate () {
        reviewLongConversion (YRHIGHDAT);
    }

    @Test
    public void setYearLowDate () {
        reviewLongConversion (YRLOWDAT);
    }

    @Test
    public void setIrgPrice () {
        reviewBigDecimalConversion (IRGPRC);
    }

    @Test
    public void setIrgVolume () {
        reviewBigIntegerConversion (IRGVOL);
    }

    @Test
    public void setIrgPriceType () {
        reviewStringConversion (IRGCOND);
    }

    @Test
    public void setPriceCorrectionTime () {
        reviewLongConversion (TIMCOR);
    }

    @Test
    public void setInsertPrice () {
        reviewBigDecimalConversion (INSPRC);
    }

    @Test
    public void setInsertVolume () {
        reviewBigIntegerConversion (INSVOL);
    }

    @Test
    public void setInsertPriceType () {
        reviewStringConversion (INSCOND);
    }

    @Test
    public void setLastTime () {
        reviewLongConversion (SALTIM);
    }

    @Test
    public void setTurnoverScale () {
        reviewStringConversion (TNOVER_SC);
    }

    @Test
    public void setBroadcastXReference () {
        reviewStringConversion (BCAST_REF);
    }

    @Test
    public void setCrossRateScale () {
        reviewStringConversion (CROSS_SC);
    }

    @Test
    public void setAmountOutstanding () {
        reviewBigDecimalConversion (AMT_OS);
    }

    @Test
    public void setAmountOutstandingScale () {
        reviewStringConversion (AMT_OS_SC);
    }

    @Test
    public void setOfficialCodeIndicator () {
        reviewStringConversion (OFF_CD_IND);
    }

    @Test
    public void setPriceVolatility () {
        reviewBigDecimalConversion (PRC_VOLTY);
    }

    @Test
    public void setAmountOutstandingDate () {
        reviewLongConversion (AMT_OS_DAT);
    }


    @Test
    public void setBackgroundReference () {
        reviewStringConversion (BKGD_REF);
    }

    @Test
    public void setGeneralPurposeValue1 () {
        reviewBigDecimalConversion (GEN_VAL1);
    }

    @Test
    public void setGeneralPurposeValue2 () {
        reviewBigDecimalConversion (GEN_VAL2);
    }

    @Test
    public void setGeneralPurposeValue3 () {
        reviewBigDecimalConversion (GEN_VAL3);
    }

    @Test
    public void setGeneralPurposeValue4 () {
        reviewBigDecimalConversion (GEN_VAL4);
    }

    @Test
    public void setGeneralPurposeValue1Description () {
        reviewStringConversion (GV1_TEXT);
    }

    @Test
    public void setGeneralPurposeValue2Description () {
        reviewStringConversion (GV2_TEXT);
    }

    @Test
    public void setGeneralPurposeValue3Description () {
        reviewStringConversion (GV3_TEXT);
    }

    @Test
    public void setGeneralPurposeValue4Description () {
        reviewStringConversion (GV4_TEXT);
    }

    @Test
    public void setSequenceNumber () {
        reviewBigIntegerConversion (SEQNUM);
    }

    @Test
    public void setPrintType () {
        reviewStringConversion (PRNTYP);
    }

    @Test
    public void setAlteredTradeEventSequenceNumber () {
        reviewBigIntegerConversion (PRNTBCK);
    }

    @Test
    public void setQuoteTimeSeconds () {
        reviewLongConversion (QUOTIM);
    }

    @Test
    public void setGenericFlag1 () {
        reviewStringConversion (GV1_FLAG);
    }

    @Test
    public void setGenericFlag2 () {
        reviewStringConversion (GV2_FLAG);
    }

    @Test
    public void setGenericFlag3 () {
        reviewStringConversion (GV3_FLAG);
    }

    @Test
    public void setGenericFlag4 () {
        reviewStringConversion (GV4_FLAG);
    }

    @Test
    public void setUniqueInstrumentId2Source () {
        reviewStringConversion (OFF_CD_IN2);
    }

    @Test
    public void setUniqueInstrumentId2 () {
        reviewStringConversion (OFFC_CODE2);
    }

    @Test
    public void setTimeInSeconds1 () {
        reviewLongConversion (GV1_TIME);
    }

    @Test
    public void setTimeInSeconds2 () {
        reviewLongConversion (GV2_TIME);
    }

    @Test
    public void setExchangeTime () {
        reviewLongConversion (EXCHTIM);
    }

    @Test
    public void setYearHighIndicator () {
        reviewStringConversion (YRHI_IND);
    }

    @Test
    public void setYearLowIndicator () {
        reviewStringConversion (YRLO_IND);
    }

    @Test
    public void setBetaValue () {
        reviewBigDecimalConversion (BETA_VAL);
    }

    @Test
    public void setPreferredDisplayTemplateNumber () {
        reviewIntegerConversion (PREF_DISP);
    }

    @Test
    public void setLocalLanguageInstrumentName () {
        reviewStringConversion (DSPLY_NMLL);
    }

    @Test
    public void setLatestTradeOrTradeTurnoverValue () {
        reviewBigDecimalConversion (VOL_X_PRC1);
    }

    @Test
    public void setDataSourceOwnerId () {
        reviewIntegerConversion (DSO_ID);
    }

    @Test
    public void setAveragePrice () {
        reviewBigDecimalConversion (AVERG_PRC);
    }

    @Test
    public void setUpc71RestrictedFlag () {
        reviewStringConversion (UPC71_REST);
    }

    @Test
    public void setAdjustedClose () {
        reviewBigDecimalConversion (ADJUST_CLS);
    }

    @Test
    public void setWeighting () {
        reviewBigDecimalConversion (WEIGHTING);
    }

    @Test
    public void setStockType () {
        reviewStringConversion (STOCK_TYPE);
    }

    @Test
    public void setImpliedVolatility () {
        reviewBigDecimalConversion (IMP_VOLT);
    }

    @Test
    public void setExchangeId2 () {
        reviewStringConversion (RDN_EXCHD2);
    }

    @Test
    public void setCapitalAdjustmentFactor () {
        reviewBigDecimalConversion (CP_ADJ_FCT);
    }

    @Test
    public void setCapitalAdjustmentDate () {
        reviewLongConversion (CP_ADJ_DAT);
    }

    @Test
    public void setSharesIssuedTotal () {
        reviewBigIntegerConversion (AMT_ISSUE);
    }

    @Test
    public void setMarketValue () {
        reviewBigDecimalConversion (MKT_VALUE);
    }

    @Test
    public void setSpecialTermsTradingFlag () {
        reviewIntegerConversion (SPEC_TRADE);
    }

    @Test
    public void setForecastedEarnings () {
        reviewBigDecimalConversion (FCAST_EARN);
    }

    @Test
    public void setEarningsRankRatio () {
        reviewBigDecimalConversion (EARANK_RAT);
    }

    @Test
    public void setForecastDate () {
        reviewLongConversion (FCAST_DATE);
    }

    @Test
    public void setForecastYear () {
        reviewStringConversion(YEAR_FCAST);
    }

    @Test
    public void setIrgPriceTypeModifier () {
        reviewStringConversion(IRGMOD);
    }

    @Test
    public void setInsertPriceTypeModifier () {
        reviewStringConversion(INSMOD);
    }

    @Test
    public void setAskPlayersLevel1 () {
        reviewBigIntegerConversion(A_NPLRS_1);
    }

    @Test
    public void setBidPlayersLevel1 () {
        reviewBigIntegerConversion(B_NPLRS_1);
    }

    @Test
    public void setGenericTime3 () {
        reviewLongConversion(GV3_TIME);
    }

    @Test
    public void setGenericTime4 () {
        reviewLongConversion(GV4_TIME);
    }

    @Test
    public void setMarketCapitalisation () {
        reviewBigIntegerConversion(MKT_CAP);
    }

    @Test
    public void setIrgCorrectionValueFid () {
        reviewBigIntegerConversion(IRGFID);
    }

    @Test
    public void setIrgCorrectionValue () {
        reviewBigIntegerConversion(IRGVAL);
    }

    @Test
    public void setAbnormalVolumeIncreasePercentage () {
        reviewBigDecimalConversion(PCT_ABNVOL);
    }

    @Test
    public void setBlockTransactionsBetween10KAnd50KShares () {
        reviewBigIntegerConversion(BC_10_50K);
    }

    @Test
    public void setBlockTransactionsBetween50KAnd100KShares () {
        reviewBigIntegerConversion(BC_50_100K);
    }

    @Test
    public void setBlockTransactionsAbove100KShares () {
        reviewBigIntegerConversion(BC_100K);
    }

    @Test
    public void setPriceMovingAverages50D () {
        reviewBigDecimalConversion(PMA_50D);
    }

    @Test
    public void setPriceMovingAverages150D () {
        reviewBigDecimalConversion(PMA_150D);
    }

    @Test
    public void setPriceMovingAverages200D () {
        reviewBigDecimalConversion(PMA_200D);
    }

    @Test
    public void setVolumeMovingAverages10D () {
        reviewBigIntegerConversion(VMA_10D);
    }

    @Test
    public void setVolumeMovingAverages25D () {
        reviewBigIntegerConversion(VMA_25D);
    }

    @Test
    public void setVolumeMovingAverages50D () {
        reviewBigIntegerConversion(VMA_50D);
    }

    @Test
    public void setOpenPriceNetChange () {
        reviewBigDecimalConversion(OPN_NETCH);
    }

    @Test
    public void setLatestReportedCashDividend () {
        reviewBigDecimalConversion(CASH_EXDIV);
    }

    @Test
    public void setMarketValueScalingFactor () {
        reviewStringConversion(MKT_VAL_SC);
    }

    @Test
    public void setExDividendTradeDate () {
        reviewLongConversion(CASH_EXDAT);
    }

    @Test
    public void setPreviousDisplayTemplate () {
        reviewIntegerConversion(PREV_DISP);
    }

    @Test
    public void setExtendedPriceQualifierFid () {
        reviewStringConversion(PRC_QL3);
    }

    @Test
    public void setMinimumPriceMovement () {
        reviewStringConversion(MPV);
    }

    @Test
    public void setOfficialClosingPrice () {
        reviewBigDecimalConversion(OFF_CLOSE);
    }

    @Test
    public void setQuoteDate () {
        reviewLongConversion(QUOTE_DATE);
    }

    @Test
    public void setVolumeWeightedAveragePrice() {
        reviewBigDecimalConversion(VWAP);
    }

    @Test
    public void setProviderSymbol() {
        reviewStringConversion(PROV_SYMB);
    }

    @Test
    public void setBidAskDate() {
        reviewLongConversion(BID_ASK_DT);
    }

    @Test
    public void setIsinCode() {
        reviewStringConversion(ISIN_CODE);
    }

    @Test
    public void setExchangeId() {
        reviewStringConversion(MNEMONIC);
    }

    @Test
    public void setRtrsOpeningPrice() {
        reviewBigDecimalConversion(RTR_OPN_PR);
    }

    @Test
    public void setSedolCode() {
        reviewStringConversion(SEDOL);
    }

    @Test
    public void setMarketSegment() {
        reviewStringConversion(MKT_SEGMNT);
    }

    @Test
    public void setRegularTradesTimeMillis() {
        reviewLongConversion(TRDTIM_MS);
    }

    @Test
    public void setAllTradesTimeMillis() {
        reviewLongConversion(SALTIM_MS);
    }

    @Test
    public void setQuoteTimeMillis() {
        reviewLongConversion(QUOTIM_MS);
    }

    @Test
    public void setCorrectionTimeMillis() {
        reviewBigIntegerConversion(TIMCOR_MS);
    }

    @Test
    public void setFinancialStatusIndicator() {
        reviewStringConversion(FIN_STATUS);
    }

    @Test
    public void setLastTradeSubMarketIndicator() {
        reviewStringConversion(LS_SUBIND);
    }

    @Test
    public void setIrgPriceSubmarketIndicator() {
        reviewStringConversion(IRG_SUBIND);
    }

    @Test
    public void setVolumeScaling() {
        reviewStringConversion(ACVOL_SC);
    }

    @Test
    public void setExchangeCode() {
        reviewStringConversion(EXCHCODE);
    }

    @Test
    public void setOddBestAsk() {
        reviewBigDecimalConversion(ODD_ASK);
    }

    @Test
    public void setOddBestAskSize() {
        reviewBigIntegerConversion(ODD_ASKSIZ);
    }

    public void setOddBestBid() {
        reviewBigDecimalConversion(ODD_BID);
    }

    public void setOddBestBidSize() {
        reviewBigIntegerConversion(ODD_BIDSIZ);
    }

    public void setRoundVolume() {
        reviewBigIntegerConversion(ROUND_VOL);
    }

    public void setOrganizationId() {
        reviewBigIntegerConversion(ORGID);
    }

    public void setPriceUpdateFrequency() {
        reviewStringConversion(PR_FREQ);
    }

    public void setRcsAssetClassification() {
        reviewStringConversion(RCS_AS_CLA);
    }

    public void setUnderlyingIndex() {
        reviewStringConversion(UNDR_INDEX);
    }

    public void setFuturesChainRic() {
        reviewStringConversion(FUTURES);
    }

    public void setOptionsChainRic() {
        reviewStringConversion(OPTIONS);
    }

    public void setStrikesCoverage() {
        reviewStringConversion(STRIKES);
    }

    public void setNewsTimeMillis() {
        reviewBigIntegerConversion(NEWSTM_MS);
    }

    public void setCompanyComplianceStatus(String companyComplianceStatus) {
        reviewStringConversion(FIN_ST_IND);
    }

    public void setIrgSubMarketCenterId(String irgSubMarketCenterId) {
        reviewStringConversion(IRG_SMKTID);
    }

    public void setSubMarketCenterId(String subMarketCenterId) {
        reviewStringConversion(SUB_MKT_ID);
    }

    public void setActiveDomesticExchangeIds(String activeDomesticExchangeIds) {
        reviewStringConversion(ACT_DOM_EX);
    }

    public void setActiveOtherExchangeIds(String activeOtherExchangeIds) {
        reviewStringConversion(ACT_OTH_EX);
    }

    public void setTradePriceQualifier2(String tradePriceQualifier2) {
        reviewStringConversion(TRD_QUAL_2);
    }

    public void setLatestCapitalChangeEffectiveDate(
        Long latestCapitalChangeEffectiveDate) {
        reviewLongConversion(CP_EFF_DAT);
    }
}
