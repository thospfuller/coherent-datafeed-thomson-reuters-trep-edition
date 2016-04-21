package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_PRICE;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDateTimeAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Class which represents the domain model for a quote.
 *
 * @TODO: Consider adding StatusResponseEventListener capabilities.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=MARKET_PRICE)
@XStreamAlias(MARKET_PRICE)
public class MarketPrice
    extends AbstractCommonProperties
    implements MarketPriceConstants {

    private static final long serialVersionUID = -8330990635265356088L;

    /**
     * Display information for the IDN terminal device.
     *
     * RDNDISPLAY: UINT32 though treat this as a UINT as UINT32 has been
     *             deprecated.
     */
    @XStreamAlias(RDNDISPLAY)
    private BigInteger displayTemplate = null;

    /**
     * Identifier for the market on which the instrument trades.
     *
     * @deprecated (From the RDMFieldDictionary) Use field RDN_EXCHD2 #1709.
     */
    @XStreamAlias(RDN_EXCHID)
    private String idnExchangeId = null;

    /**
     * Full or abbreviated text instrument name.
     */
    @XStreamAlias(DSPLY_NAME)
    private String displayName = null;

    /**
     * BID: REAL
     */
    @XStreamAlias(BID)
    private BigDecimal bid = null;

    /**
     * BID: REAL
     */
    @XStreamAlias(BID_1)
    private BigDecimal bid1 = null;

    /**
     * BID: REAL
     */
    @XStreamAlias(BID_2)
    private BigDecimal bid2 = null;

    /**
     * ASK: REAL
     */
    @XStreamAlias(ASK)
    private BigDecimal ask = null;

    /**
     * ASK: REAL
     */
    @XStreamAlias(ASK_1)
    private BigDecimal ask1 = null;

    /**
     * ASK: REAL
     */
    @XStreamAlias(ASK_2)
    private BigDecimal ask2 = null;

    /**
     * BIDSIZE: REAL
     */
    @XStreamAlias(BIDSIZE)
    private BigInteger bidSize = null;

    /**
     * ASKSIZE: REAL
     */
    @XStreamAlias(ASKSIZE)
    private BigInteger askSize = null;

    /**
     * Trade price 1
     *
     * LAST: REAL
     */
    @XStreamAlias(TRDPRC_1)
    private BigDecimal last = null;

    /**
     * Trade price 2
     *
     * LAST: REAL
     */
    @XStreamAlias(TRDPRC_2)
    private BigDecimal last1 = null;

    /**
     * Trade price 3
     *
     * REAL
     */
    @XStreamAlias(TRDPRC_3)
    private BigDecimal last2 = null;

    /**
     * Trade price 4
     *
     * REAL
     */
    @XStreamAlias(TRDPRC_4)
    private BigDecimal last3 = null;

    /**
     * Trade price 5
     *
     * REAL
     */
    @XStreamAlias(TRDPRC_5)
    private BigDecimal last4 = null;

    /**
     * Net change.
     */
    @XStreamAlias(NETCHNG_1)
    private BigDecimal netChange = null;

    @XStreamAlias(HIGH_1)
    private BigDecimal todaysHigh = null;

    @XStreamAlias(LOW_1)
    private BigDecimal todaysLow = null;

    /**
     * Tick up/down arrow.
     */
    @XStreamAlias(PRCTCK_1)
    private Integer tickArrow = null;

//    @XStreamAlias(CURRENCY)
//    private String currency = null;

    @XStreamAlias(TRADE_DATE)
    private Long tradeDateMillis = null;

    @XStreamAlias(TRDTIM_1)
    private Long tradeTimeMillis = null;

    @XStreamAlias(OPEN_PRC)
    private BigDecimal openPrice = null;

    @XStreamAlias(HST_CLOSE)
    private BigDecimal historicClose = null;

    @XStreamAlias(NEWS)
    private String news = null;

    @XStreamAlias(NEWS_TIME)
    private Long newsTime = null;

    @XStreamAlias(ACVOL_1)
    private BigInteger volumeAccumulated = null;

    @XStreamAlias(EARNINGS)
    private BigDecimal earnings = null;

    @XStreamAlias(YIELD)
    private BigDecimal yield = null;

    @XStreamAlias(PERATIO)
    private BigDecimal priceToEarningsRatio = null;

    @XStreamAlias(DIVIDENDTP)
    private String dividendType = null;

    @XStreamAlias(DIVPAYDATE)
    private Long dividendPayDate = null;

    @XStreamAlias(EXDIVDATE)
    private Long exDividendDate = null;

    @XStreamAlias(CTS_QUAL)
    private String tradePriceQualifier = null;

    @XStreamAlias(BLKCOUNT)
    private BigInteger blockCount = null;

    @XStreamAlias(BLKVOLUM)
    private BigInteger blockVolume = null;

    @XStreamAlias(TRDXID_1)
    private String tradeExchangeId = null;

    @XStreamAlias(TRD_UNITS)
    private String tradingUnits = null;

    @XStreamAlias(LOT_SIZE)
    private BigInteger lotSize = null;

    @XStreamAlias(PCTCHNG)
    private BigDecimal percentChange = null;

    @XStreamAlias(OPEN_BID)
    private BigDecimal openBid = null;

    @XStreamAlias(DJTIME)
    private Long latestDowJonesNewsStoryTime = null;

    @XStreamAlias(CLOSE_BID)
    private BigDecimal closeBid = null;

    @XStreamAlias(CLOSE_ASK)
    private BigDecimal closeAsk = null;

    @XStreamAlias(DIVIDEND)
    private BigDecimal dividend = null;

    @XStreamAlias(NUM_MOVES)
    private BigInteger totalTradesToday = null;

    @XStreamAlias(OFFCL_CODE)
    private String officialCode = null;

    @XStreamAlias(HSTCLSDATE)
    private Long historicCloseDate = null;

    @XStreamAlias(YRHIGH)
    private BigDecimal yearHigh = null;

    @XStreamAlias(YRLOW)
    private BigDecimal yearLow = null;

    @XStreamAlias(TURNOVER)
    private BigDecimal turnover = null;

    @XStreamAlias(BOND_TYPE)
    private String bondType = null;

    @XStreamAlias(BCKGRNDPAG)
    private String backgroundPage = null;

    @XStreamAlias(YCHIGH_IND)
    private String yearOrContractHighIndicator = null;

    @XStreamAlias(YCLOW_IND)
    private String yearOrContractLowIndicator = null;

    @XStreamAlias(BID_NET_CH)
    private BigDecimal bidNetChange = null;

    /**
     * @todo Rename this property as the name is ugly.
     */
    @XStreamAlias(BID_TICK_1)
    private String bidTick1 = null;

    @XStreamAlias(CUM_EX_MKR)
    private String cumExMarker = null;

    @XStreamAlias(PRC_QL_CD)
    private String priceCode = null;

    @XStreamAlias(NASDSTATUS)
    private String nasdStatus = null;

    @XStreamAlias(PRC_QL2)
    private String priceCode2 = null;

    @XStreamAlias(TRDVOL_1)
    private BigInteger tradeVolume = null;

    @XStreamAlias(BID_HIGH_1)
    private BigDecimal todaysHighBid = null;
    
    @XStreamAlias(BID_LOW_1)
    private BigDecimal todaysLowBid = null;
    
    @XStreamAlias(YRBIDHIGH)
    private BigDecimal yearHighBid = null;

    @XStreamAlias(YRBIDLOW)
    private BigDecimal yearLowBid = null;

    @XStreamAlias(HST_CLSBID)
    private BigDecimal historicalClosingBid = null;

    @XStreamAlias(HSTCLBDDAT)
    private Long historicalClosingBidDate = null;

    @XStreamAlias(YRBDHI_IND)
    private String yearBidHigh = null;

    @XStreamAlias(YRBDLO_IND)
    private String yearBidLow = null;

    @XStreamAlias(NUM_BIDS)
    private BigInteger numberOfBids = null;

//    @XStreamAlias(RECORDTYPE)
//    private BigInteger recordType = null;

    @XStreamAlias(OPTION_XID)
    private String optionExchangeId = null;

    @XStreamAlias(YRHIGHDAT)
    private Long yearHighDate = null;

    @XStreamAlias(YRLOWDAT)
    private Long yearLowDate = null;

    @XStreamAlias(IRGPRC)
    private BigDecimal irgPrice = null;

    @XStreamAlias(IRGVOL)
    private BigInteger irgVolume = null;

    @XStreamAlias(IRGCOND)
    private String irgPriceType = null;

    @XStreamAlias(TIMCOR)
    private Long priceCorrectionTime = null;

    @XStreamAlias(INSPRC)
    private BigDecimal insertPrice = null;

    @XStreamAlias(INSVOL)
    private BigInteger insertVolume = null;

    @XStreamAlias(INSCOND)
    private String insertPriceType = null;

    @XStreamAlias(SALTIM)
    private Long lastTime = null;

    @XStreamAlias(TNOVER_SC)
    private String turnoverScale = null;

    @XStreamAlias(BCAST_REF)
    private String broadcastXRef = null;

    @XStreamAlias(CROSS_SC)
    private String crossRateScale = null;

    @XStreamAlias(AMT_OS)
    private BigDecimal amountOutstanding = null;

    @XStreamAlias(AMT_OS_SC)
    private String amountOutstandingScale = null;

    @XStreamAlias(OFF_CD_IND)
    private String officialCodeIndicator = null;

    @XStreamAlias(PRC_VOLTY)
    private BigDecimal priceVolatility = null;

    /**
     * The date when the shares outstanding was reported.
     */
    @XStreamAlias(AMT_OS_DAT)
    private Long amountOutstandingDate = null;

    @XStreamAlias(BKGD_REF)
    private String backgroundReference = null;

    @XStreamAlias(GEN_VAL1)
    private BigDecimal generalPurposeValue1 = null;

    @XStreamAlias(GV1_TEXT)
    private String generalPurposeValue1Description = null;

    @XStreamAlias(GEN_VAL2)
    private BigDecimal generalPurposeValue2 = null;

    @XStreamAlias(GV2_TEXT)
    private String generalPurposeValue2Description = null;
    
    @XStreamAlias(GEN_VAL3)
    private BigDecimal generalPurposeValue3 = null;

    @XStreamAlias(GV3_TEXT)
    private String generalPurposeValue3Description = null;
    
    @XStreamAlias(GEN_VAL4)
    private BigDecimal generalPurposeValue4 = null;

    @XStreamAlias(GV4_TEXT)
    private String generalPurposeValue4Description = null;

    @XStreamAlias(SEQNUM)
    private BigInteger sequenceNumber = null;

    @XStreamAlias(PRNTYP)
    private String printType = null;

    @XStreamAlias(PRNTBCK)
    private BigInteger alteredTradeEventSequenceNumber = null;

    @XStreamAlias(QUOTIM)
    private Long quoteTimeSeconds = null;

    @XStreamAlias(GV1_FLAG)
    private String genericFlag1 = null;

    @XStreamAlias(GV2_FLAG)
    private String genericFlag2 = null;

    @XStreamAlias(GV3_FLAG)
    private String genericFlag3 = null;

    @XStreamAlias(GV4_FLAG)
    private String genericFlag4 = null;

    @XStreamAlias(OFF_CD_IN2)
    private String uniqueInstrumentId2Source = null;

    @XStreamAlias(OFFC_CODE2)
    private String uniqueInstrumentId2 = null;

    @XStreamAlias(GV1_TIME)
    private Long timeInSeconds1 = null;

    @XStreamAlias(GV2_TIME)
    private Long timeInSeconds2 = null;

    @XStreamAlias(EXCHTIM)
    private Long exchangeTime = null;

    @XStreamAlias(YRHI_IND)
    private String yearHighIndicator = null;

    @XStreamAlias(YRLO_IND)
    private String yearLowIndicator = null;

    @XStreamAlias(BETA_VAL)
    private BigDecimal betaValue = null;

    /**
     * This is a UINT32 / binary so I'm marking this as a int for the moment.
     */
    @XStreamAlias(PREF_DISP)
    private Integer preferredDisplayTemplateNumber = null;

    @XStreamAlias(DSPLY_NMLL)
    private String localLanguageInstrumentName = null;

    @XStreamAlias(VOL_X_PRC1)
    private BigDecimal latestTradeOrTradeTurnoverValue = null;

    @XStreamAlias(DSO_ID)
    private Integer dataSourceOwnerId = null;

    @XStreamAlias(AVERG_PRC)
    private BigDecimal averagePrice = null;

    @XStreamAlias(UPC71_REST)
    private String upc71RestrictedFlag = null;
    
    @XStreamAlias(ADJUST_CLS)
    private BigDecimal adjustedClose = null;

    @XStreamAlias(WEIGHTING)
    private BigDecimal weighting = null;

    @XStreamAlias(STOCK_TYPE)
    private String stockType = null;

    @XStreamAlias(IMP_VOLT)
    private BigDecimal impliedVolatility = null;

//    @XStreamAlias(RDN_EXCHD2)
//    private String exchangeId2 = null;

    @XStreamAlias(CP_ADJ_FCT)
    private BigDecimal capitalAdjustmentFactor = null;

    @XStreamAlias(CP_ADJ_DAT)
    private Long capitalAdjustmentDate = null;

    @XStreamAlias(AMT_ISSUE)
    private BigInteger sharesIssuedTotal = null;

    @XStreamAlias(MKT_VALUE)
    private BigDecimal marketValue = null;

    @XStreamAlias(SPEC_TRADE)
    private Integer specialTermsTradingFlag = null;

    @XStreamAlias(FCAST_EARN)
    private BigDecimal forecastedEarnings = null;

    @XStreamAlias(EARANK_RAT)
    private BigDecimal earningsRankRatio = null;

    @XStreamAlias(FCAST_DATE)
    private Long forecastDate = null;

    /**
     * Data buffer
     */
    @XStreamAlias(YEAR_FCAST)
    private String forecastYear = null;

    /**
     * Enum
     */
    @XStreamAlias(IRGMOD)
    private String irgPriceTypeModifier = null;

    @XStreamAlias(INSMOD)
    private String insertPriceTypeModifier = null;

    @XStreamAlias(A_NPLRS_1)
    private BigInteger askPlayersLevel1 = null;

    @XStreamAlias(B_NPLRS_1)
    private BigInteger bidPlayersLevel1 = null;

    @XStreamAlias(GV3_TIME)
    private Long genericTime3 = null;

    @XStreamAlias(GV4_TIME)
    private Long genericTime4 = null;

    /**
     * Deprecated in favor of MKT_VAL (note this is available in this api
     * already).
     */
    @XStreamAlias(MKT_CAP)
    private BigInteger marketCapitalisation = null;

    @XStreamAlias(IRGFID)
    private BigInteger irgCorrectionValueFid = null;

    @XStreamAlias(IRGVAL)
    private BigInteger irgCorrectionValue = null;

    @XStreamAlias(PCT_ABNVOL)
    private BigDecimal abnormalVolumeIncreasePercentage = null;

    @XStreamAlias(BC_10_50K)
    private BigInteger blockTransactionsBetween10KAnd50KShares = null;

    @XStreamAlias(BC_50_100K)
    private BigInteger blockTransactionsBetween50KAnd100KShares = null;

    @XStreamAlias(BC_100K)
    private BigInteger blockTransactionsAbove100KShares = null;

    @XStreamAlias(PMA_50D)
    private BigDecimal priceMovingAverages50D = null;

    @XStreamAlias(PMA_150D)
    private BigDecimal priceMovingAverages150D = null;

    @XStreamAlias(PMA_200D)
    private BigDecimal priceMovingAverages200D = null;

    @XStreamAlias(VMA_10D)
    private BigInteger volumeMovingAverages10D = null;

    @XStreamAlias(VMA_25D)
    private BigInteger volumeMovingAverages25D = null;

    @XStreamAlias(VMA_50D)
    private BigInteger volumeMovingAverages50D = null;

    @XStreamAlias(OPN_NETCH)
    private BigDecimal openPriceNetChange = null;

    @XStreamAlias(CASH_EXDIV)
    private BigDecimal latestReportedCashDividend = null;

    /**
     * Enum
     */
    @XStreamAlias(MKT_VAL_SC)
    private String marketValueScalingFactor = null;

    @XStreamAlias(CASH_EXDAT)
    private Long exDividendTradeDate = null;

    /**
     * Binary
     */
    @XStreamAlias(PREV_DISP)
    private Integer previousDisplayTemplate = null;

    /**
     * Enum
     */
    @XStreamAlias(PRC_QL3)
    private String extendedPriceQualifierFid = null;

    /**
     * Enum
     */
    @XStreamAlias(MPV)
    private String minimumPriceMovement = null;

    @XStreamAlias(OFF_CLOSE)
    private BigDecimal officialClosingPrice = null;

    @XStreamAlias(QUOTE_DATE)
    private Long quoteDate = null;

    @XStreamAlias(VWAP)
    private BigDecimal volumeWeightedAveragePrice = null;

//    @XStreamAlias(PROV_SYMB)
//    private String providerSymbol = null;

    @XStreamAlias(BID_ASK_DT)
    private Long bidAskDate = null;

    /**
     * International Security Identification Number code (ISIN).
     */
    @XStreamAlias(ISIN_CODE)
    private String isinCode = null;

//    @XStreamAlias(MNEMONIC)
//    private String exchangeId = null;

    @XStreamAlias(RTR_OPN_PR)
    private BigDecimal rtrsOpeningPrice = null;

    @XStreamAlias(SEDOL)
    private String sedolCode = null;

    @XStreamAlias(MKT_SEGMNT)
    private String marketSegment = null;

    /**
     * TODO: This is an Int/UINT64 -- need to review the conversion.
     */
    @XStreamAlias(TRDTIM_MS)
    private Long regularTradesTimeMillis = null;

    /**
     * TODO: This is an Int/UINT64 -- need to review the conversion.
     */
    @XStreamAlias(SALTIM_MS)
    private Long allTradesTimeMillis = null;

    /**
     * TODO: This is an Int/UINT64 -- need to review the conversion.
     */
    @XStreamAlias(QUOTIM_MS)
    private Long quoteTimeMillis = null;

    /**
     * @deprecated Convert type to Long.
     * @TODO: Convert type to Long.
     */
    @XStreamAlias(TIMCOR_MS)
    private BigInteger correctionTimeMillis = null;

    @XStreamAlias(FIN_STATUS)
    private String financialStatusIndicator = null;

    @XStreamAlias(LS_SUBIND)
    private String lastTradeSubMarketIndicator = null;

    @XStreamAlias(IRG_SUBIND)
    private String irgPriceSubmarketIndicator = null;

    @XStreamAlias(ACVOL_SC)
    private String volumeScaling = null;

    @XStreamAlias(EXCHCODE)
    private String exchangeCode = null;

    @XStreamAlias(ODD_ASK)
    private BigDecimal oddBestAsk = null;

    @XStreamAlias(ODD_ASKSIZ)
    private BigInteger oddBestAskSize = null;

    @XStreamAlias(ODD_BID)
    private BigDecimal oddBestBid = null;

    @XStreamAlias(ODD_BIDSIZ)
    private BigInteger oddBestBidSize = null;

    @XStreamAlias(ROUND_VOL)
    private BigInteger roundVolume = null;

    @XStreamAlias(ORGID)
    private BigInteger organizationId = null;

    @XStreamAlias(PR_FREQ)
    private String priceUpdateFrequency = null;

    /**
     * Reuters Classification Scheme
     */
    @XStreamAlias(RCS_AS_CLA)
    private String rcsAssetClassification = null;

    @XStreamAlias(UNDR_INDEX)
    private String underlyingIndex = null;

    @XStreamAlias(FUTURES)
    private String futuresChainRic = null;

    @XStreamAlias(OPTIONS)
    private String optionsChainRic = null;

    @XStreamAlias(STRIKES)
    private String strikesCoverage = null;

    @XStreamAlias(NEWSTM_MS)
    private BigInteger newsTimeMillis = null;

    @XStreamAlias(TRD_THRU_X)
    private String tradeThroughExemptFlags = null;

    @XStreamAlias(FIN_ST_IND)
    private String companyComplianceStatus = null;
    
    @XStreamAlias(IRG_SMKTID)
    private String irgSubMarketCenterId = null;
    
    @XStreamAlias(SUB_MKT_ID)
    private String subMarketCenterId = null;

    /**
     * Docs say markets -- so we're using ids.
     */
    @XStreamAlias(ACT_DOM_EX)
    private String activeDomesticExchangeIds = null;

    @XStreamAlias(ACT_OTH_EX)
    private String activeOtherExchangeIds = null;
    
    @XStreamAlias(TRD_QUAL_2)
    private String tradePriceQualifier2 = null;
    
    @XStreamAlias(CP_EFF_DAT)
    private Long latestCapitalChangeEffectiveDate = null;

    /**
     * @deprecated The name of this property is a bit vague as this is not the
     *  bid id, it is the id of the market participant who is making the bid.
     */
    @XStreamAlias(BID_MMID1)
    private String marketParticipantBidId = null;

    // BELOW HERE IS ALL TIME SERIES-RELATED PROPERTIES.

    @XStreamOmitField
    private String row64_1 = null;

    @XStreamOmitField
    private String row64_2 = null;

    @XStreamOmitField
    private String row64_3 = null;

    @XStreamOmitField
    private String row64_4 = null;

    @XStreamOmitField
    private String row64_5 = null;

    @XStreamOmitField
    private String row64_6 = null;

    @XStreamOmitField
    private String row64_7 = null;

    @XStreamOmitField
    private String row64_8 = null;

    @XStreamOmitField
    private String row64_9 = null;

    @XStreamOmitField
    private String row64_10 = null;

    @XStreamOmitField
    private String row64_11 = null;

    @XStreamOmitField
    private String row64_12 = null;

    @XStreamOmitField
    private String row64_13 = null;

    @XStreamOmitField
    private String row64_14 = null;

    /**
     * @deprecated The name of this property is a bit vague as this is not the
     *  ask id, it is the id of the market participant who is making the ask.
     */
    @XStreamAlias(ASK_MMID1)
    private String marketParticipantAskId = null;

//    private static final Map<Short, String> putCallMap =
//        new HashMap<Short, String> ();

//    private static final int NOT_ALLOCATED = 0, PUT = 1, CALL = 2, C_AM = 3,
//        P_AM = 4, 
//        C_EU = 5,
//        P_EU = 6,
//        AM   = 7,
//        EU   = 8,
//        CAP  = 9,
//        O_AM = 10,
//        U_AM = 11,
//        O_EU = 12,
//        U_EU = 13;
//
//    static {
//        
//    }

    /**
     * Indicates whether option is a put or a call. Standard usage will use
     * values that also specify the style of the option.
     *
     * ENUMERATED/ENUM
     *
     * 0       "    "   Not allocated
     * 1       "PUT "   put
     * 2       "CALL"   call
     * 3       "C-AM"   American expiration call option
     * 4       "P-AM"   American expiration put option
     * 5       "C-EU"   European expiration call option
     * 6       "P-EU"   European expiration put option
     * 7       "AM  "   American style expiration
     * 8       "EU  "   European style expiration
     * 9       "CAP "   Capped
     * 10       "O-AM"   American expiration binary Over option
     * 11       "U-AM"   American expiration binary Under option
     * 12       "O-EU"   European expiration binary Over option
     * 13       "U-EU"   European expiration binary Under option
     */
    @XStreamAlias(PUT_CALL)
    private String putCall = null;

    /**
     * Implied volatility of ASK price.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(IMP_VOLTA)
    private BigDecimal impliedVolatilitytOfAskPrice = null;

    /**
     * Implied volatility of BID price.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(IMP_VOLTB)
    private BigDecimal impliedVolatilitytOfBidPrice = null;

    /**
     * Open interest. The total number of option or futures contracts that
     * have not been closed or in the case of commodities liquidated or
     * offset by delivery.
     *
     * INTEGER/REAL64
     */
    @XStreamAlias(OPINT_1)
    private BigInteger openInterest = null;

    /**
     * Open interest net change. The difference between the current and
     * previous trading day open interest.
     *
     * REAL64
     */
    @XStreamAlias(OPINTNC)
    private BigInteger openInterestNetChange = null;

    /**
     * Strike price; the price at which an option may be exercised.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(STRIKE_PRC)
    private BigDecimal strikePrice = null;

    //
    /**
     * Contract month
     *
     * ALPHANUMERIC/RMTES_STRING
     */
    @XStreamAlias(CONTR_MNTH)
    private String contractMonth = null;

    /**
     * Lot size units.
     * 
     * ENUMERATED/ENUM
     */
    @XStreamAlias(LOTSZUNITS)
    private String lotSizeUnits = null;

    /**
     * Open ask price.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(OPEN_ASK)
    private BigDecimal openAskPrice = null;

    /**
     * The date on which the future, option or warrant expires.
     *
     * DATE/DATE
     */
    @XStreamAlias(EXPIR_DATE)
    private Long expiryDate = null;

    /**
     * Settlement price. The official closing price of a futures or option
     * contract set by the clearing house at the end of the trading day.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(SETTLE)
    private BigDecimal settlementPrice = null;

    /**
     * The date of the settlement price held in the SETTLE field.
     *
     * DATE/DATE
     */
    @XStreamAlias(SETTLEDATE)
    private Long settleDate = null;

    @UsingKey(type=RDN_EXCHID)
    public String getIdnExchangeId() {
        return idnExchangeId;
    }

    private static final String IDN_EXCHANGE_ID = "idnExchangeId";

    /**
     * @todo Test this.
     */
    @RFAType(type=RDN_EXCHID)
    @Adapt(using=OMMEnumAdapter.class)
    public void setIdnExchangeId(String idnExchangeId) {

        String oldValue = this.idnExchangeId;

        this.idnExchangeId = idnExchangeId;

        firePropertyChange(IDN_EXCHANGE_ID, oldValue, idnExchangeId);
    }

    @UsingKey(type=BID)
    public BigDecimal getBid() {
        return bid;
    }

    private static final String BID = "bid";

    @RFAType(type=BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid(BigDecimal bid) {

        BigDecimal oldValue = this.bid;

        this.bid = bid;

        firePropertyChange(BID, oldValue, bid);
    }

    @UsingKey(type=BID_1)
    public BigDecimal getBid1() {
        return bid1;
    }

    private static final String BID_1 = "bid1";

    @RFAType(type=MarketPriceConstants.BID_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid1(BigDecimal bid1) {

        BigDecimal oldValue = this.bid1;

        this.bid1 = bid1;

        firePropertyChange(MarketPrice.BID_1, oldValue, bid1);
    }

    @UsingKey(type=BID_2)
    public BigDecimal getBid2() {
        return bid2;
    }

    private static final String BID_2 = "bid2";

    @RFAType(type=MarketPriceConstants.BID_2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid2(BigDecimal bid2) {

        BigDecimal oldValue = this.bid2;

        this.bid2 = bid2;

        firePropertyChange(MarketPrice.BID_2, oldValue, bid2);
    }

    @UsingKey(type=ASK)
    public BigDecimal getAsk() {
        return ask;
    }

    private static final String ASK = "ask";

    /**
     * REAL64
     *
     * @param ask
     */
    @RFAType(type=MarketPriceConstants.ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAsk(BigDecimal ask) {

        BigDecimal oldValue = this.ask;

        this.ask = ask;

        firePropertyChange(MarketPrice.ASK, oldValue, ask);
    }

    @UsingKey(type=ASK_1)
    public BigDecimal getAsk1() {
        return ask1;
    }

    private static final String ASK1 = "ask1";

    /**
     * REAL64
     *
     * @param ask1
     */
    @RFAType(type=MarketPriceConstants.ASK_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAsk1(BigDecimal ask1) {

        BigDecimal oldValue = this.ask1;

        this.ask1 = ask1;

        firePropertyChange(MarketPrice.ASK1, oldValue, ask1);
    }

    @UsingKey(type=ASK_2)
    public BigDecimal getAsk2() {
        return ask2;
    }

    private static final String ASK2 = "ask2";

    /**
     * REAL64
     *
     * @param ask2
     */
    @RFAType(type=ASK_2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAsk2(BigDecimal ask2) {

        BigDecimal oldValue = this.ask2;

        this.ask2 = ask2;

        firePropertyChange(MarketPrice.ASK2, oldValue, ask2);
    }

    public BigInteger getBidSize() {
        return bidSize;
    }

    private static final String BID_SIZE = "bidSize";

    /**
     * REAL64
     *
     * @param bidSize
     */
    @RFAType(type=MarketPriceConstants.BIDSIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBidSize(BigInteger bidSize) {

        BigInteger oldValue = this.bidSize;

        this.bidSize = bidSize;

        firePropertyChange(MarketPrice.BID_SIZE, oldValue, bidSize);
    }

    public BigInteger getAskSize() {
        return askSize;
    }

    private static final String ASK_SIZE = "askSize";

    /**
     * REAL64
     *
     * @param askSize
     */
    @RFAType(type=MarketPriceConstants.ASKSIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAskSize(BigInteger askSize) {

        BigInteger oldValue = this.askSize;

        this.askSize = askSize;

        firePropertyChange(MarketPrice.ASK_SIZE, oldValue, askSize);
    }

    public BigDecimal getLast() {
        return last;
    }

    private static final String LAST = "last";

    /**
     * REAL64
     *
     * @param last
     */
    @RFAType(type=MarketPriceConstants.TRDPRC_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast(BigDecimal last) {

    	BigDecimal oldValue = this.last;

        this.last = last;

        firePropertyChange(MarketPrice.LAST, oldValue, last);
    }

    public BigDecimal getLast1() {
        return last1;
    }

    private static final String LAST_1 = "last1";
    
    /**
     * REAL64
     *
     * @param last1
     */
    @RFAType(type=MarketPriceConstants.TRDPRC_2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast1(BigDecimal last1) {

    	BigDecimal oldValue = this.last1;

        this.last1 = last1;

        firePropertyChange(MarketPrice.LAST_1, oldValue, last1);
    }

    public BigDecimal getLast2() {
        return last2;
    }

//    private static final String 

    /**
     * REAL64
     *
     * @param last2
     */
    @RFAType(type=TRDPRC_3)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast2(BigDecimal last2) {
        this.last2 = last2;
    }

    public BigDecimal getLast3() {
        return last3;
    }

//    private static final String 

    /**
     * REAL64
     *
     * @param last3
     */
    @RFAType(type=TRDPRC_4)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast3(BigDecimal last3) {
        this.last3 = last3;
    }

    public BigDecimal getLast4() {
        return last4;
    }

//    private static final String 

    /**
     * REAL64
     *
     * @param last4
     */
    @RFAType(type=TRDPRC_5)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast4(BigDecimal last4) {
        this.last4 = last4;
    }

    public BigInteger getDisplayTemplate() {
        return displayTemplate;
    }

//    private static final String 

    /**
     * UINT32
     *
     * @param rdnDisplay
     */
    @RFAType(type=RDNDISPLAY)
    @Adapt(using=OMMNumericAdapter.class)
    public void setDisplayTemplate(BigInteger rdnDisplay) {
        this.displayTemplate = rdnDisplay;
    }

    public BigDecimal getNetChange() {
        return netChange;
    }

//    private static final String 

    @RFAType(type=NETCHNG_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setNetChange(BigDecimal netChange) {
        this.netChange = netChange;
    }

    @UsingKey(type=HIGH_1)
    public BigDecimal getTodaysHigh() {
        return todaysHigh;
    }

//    private static final String 

    @RFAType(type=HIGH_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysHigh(BigDecimal todaysHigh) {
        this.todaysHigh = todaysHigh;
    }

    @UsingKey(type=LOW_1)
    public BigDecimal getTodaysLow() {
        return todaysLow;
    }

//    private static final String 

    /**
     * REAL64
     *
     * @param todaysLow
     */
    @RFAType(type=LOW_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysLow(BigDecimal todaysLow) {
        this.todaysLow = todaysLow;
    }

    @UsingKey(type=PRCTCK_1)
    public Integer getTickArrow() {
        return tickArrow;
    }

//    private static final String 

    @RFAType(type=PRCTCK_1)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTickArrow(Integer tickArrow) {
        this.tickArrow = tickArrow;
    }

//    @UsingKey(type=CURRENCY)
//    public String getCurrency() {
//        return currency;
//    }
//
//    @RFAType(type=CURRENCY)
//    @Adapt(using=OMMEnumAdapter.class)
//    public void setCurrency(String currency) {
//        this.currency = currency;
//    }

    @UsingKey(type=TRADE_DATE)
    public Long getTradeDateMillis() {
        return tradeDateMillis;
    }

//    private static final String 

    @RFAType(type=TRADE_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTradeDateMillis(Long tradeDateMillis) {
        this.tradeDateMillis = tradeDateMillis;
    }

    @UsingKey(type=TRDTIM_1)
    public Long getTradeTimeMillis() {
        return tradeTimeMillis;
    }

//    private static final String 

    @RFAType(type=TRDTIM_1)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTradeTimeMillis(Long tradeTimeMillis) {
        this.tradeTimeMillis = tradeTimeMillis;
    }

    @UsingKey(type=OPEN_PRC)
    public BigDecimal getOpenPrice() {
        return openPrice;
    }

//    private static final String 

    @RFAType(type=OPEN_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    @UsingKey(type=HST_CLOSE)
    public BigDecimal getHistoricClose() {
        return historicClose;
    }

//    private static final String 

    @RFAType(type=HST_CLOSE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setHistoricClose(BigDecimal historicClose) {
        this.historicClose = historicClose;
    }

    @UsingKey(type=NEWS)
    public String getNews() {
        return news;
    }

//    private static final String 

    @RFAType(type=NEWS)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setNews(String news) {
        this.news = news;
    }

    @UsingKey(type=NEWS_TIME)
    public Long getNewsTime() {
        return newsTime;
    }

//    private static final String 

    @RFAType(type=NEWS_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setNewsTime(Long newsTime) {
        this.newsTime = newsTime;
    }

    @UsingKey(type=ACVOL_1)
    public BigInteger getVolumeAccumulated() {
        return volumeAccumulated;
    }

//    private static final String 

    @RFAType(type=ACVOL_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeAccumulated(BigInteger volumeAccumulated) {
        this.volumeAccumulated = volumeAccumulated;
    }

    @UsingKey(type=EARNINGS)
    public BigDecimal getEarnings() {
        return earnings;
    }

//    private static final String 

    @RFAType(type=EARNINGS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setEarnings(BigDecimal earnings) {
        this.earnings = earnings;
    }

    @UsingKey(type=YIELD)
    public BigDecimal getYield() {
        return yield;
    }

//    private static final String 

    @RFAType(type=YIELD)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    @UsingKey(type=PERATIO)
    public BigDecimal getPriceToEarningsRatio() {
        return priceToEarningsRatio;
    }

//    private static final String 

    @RFAType(type=PERATIO)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceToEarningsRatio(BigDecimal priceToEarningsRatio) {
        this.priceToEarningsRatio = priceToEarningsRatio;
    }

    @UsingKey(type=DIVIDENDTP)
    public String getDividendType() {
        return dividendType;
    }

//    private static final String 

    @RFAType(type=DIVIDENDTP)
    @Adapt(using=OMMEnumAdapter.class)
    public void setDividendType(String dividendType) {
        this.dividendType = dividendType;
    }

    @UsingKey(type=DIVPAYDATE)
    public Long getDividendPayDate() {
        return dividendPayDate;
    }

//    private static final String 

    @RFAType(type=DIVPAYDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setDividendPayDate(Long dividendPayDate) {
        this.dividendPayDate = dividendPayDate;
    }

    @UsingKey(type=EXDIVDATE)
    public Long getExDividendDate() {
        return exDividendDate;
    }

//    private static final String 

    @RFAType(type=EXDIVDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExDividendDate(Long exDividendDate) {
        this.exDividendDate = exDividendDate;
    }

    @UsingKey(type=CTS_QUAL)
    public String getTradePriceQualifier() {
        return tradePriceQualifier;
    }

//    private static final String 

    @RFAType(type=CTS_QUAL)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradePriceQualifier(String tradePriceQualifier) {
        this.tradePriceQualifier = tradePriceQualifier;
    }

    @UsingKey(type=BLKCOUNT)
    public BigInteger getBlockCount() {
        return blockCount;
    }

//    private static final String 

    @RFAType(type=BLKCOUNT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockCount(BigInteger blockCount) {
        this.blockCount = blockCount;
    }

    @UsingKey(type=BLKVOLUM)
    public BigInteger getBlockVolume() {
        return blockVolume;
    }

//    private static final String 

    @RFAType(type=BLKVOLUM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockVolume(BigInteger blockVolume) {
        this.blockVolume = blockVolume;
    }

    @UsingKey(type=TRDXID_1)
    public String getTradeExchangeId() {
        return tradeExchangeId;
    }

//    private static final String 

    @RFAType(type=TRDXID_1)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradeExchangeId(String tradeExchangeId) {
        this.tradeExchangeId = tradeExchangeId;
    }

    @UsingKey(type=TRD_UNITS)
    public String getTradingUnits() {
        return tradingUnits;
    }

//    private static final String 

    @RFAType(type=TRD_UNITS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradingUnits(String tradingUnits) {
        this.tradingUnits = tradingUnits;
    }

    @UsingKey(type=LOT_SIZE)
    public BigInteger getLotSize() {
        return lotSize;
    }

//    private static final String 

    @RFAType(type=LOT_SIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLotSize(BigInteger lotSize) {
        this.lotSize = lotSize;
    }

    @UsingKey(type=PCTCHNG)
    public BigDecimal getPercentChange() {
        return percentChange;
    }

//    private static final String 

    @RFAType(type=PCTCHNG)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPercentChange(BigDecimal setPercentChange) {
        this.percentChange = setPercentChange;
    }

    @UsingKey(type=OPEN_BID)
    public BigDecimal getOpenBid() {
        return openBid;
    }

//    private static final String 

    @RFAType(type=OPEN_BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenBid(BigDecimal openBid) {
        this.openBid = openBid;
    }

    @UsingKey(type=DJTIME)
    public Long getLatestDowJonesNewsStoryTime() {
        return latestDowJonesNewsStoryTime;
    }

//    private static final String 

    @RFAType(type=DJTIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLatestDowJonesNewsStoryTime(Long time) {
        this.latestDowJonesNewsStoryTime = time;
    }

    @UsingKey(type=CLOSE_BID)
    public BigDecimal getCloseBid() {
        return closeBid;
    }

//    private static final String 

    @RFAType(type=CLOSE_BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCloseBid(BigDecimal closeBid) {
        this.closeBid = closeBid;
    }

    @UsingKey(type=CLOSE_ASK)
    public BigDecimal getCloseAsk() {
        return closeAsk;
    }

//    private static final String 

    @RFAType(type=CLOSE_ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCloseAsk(BigDecimal closeAsk) {
        this.closeAsk = closeAsk;
    }

    @UsingKey(type=DIVIDEND)
    public BigDecimal getDividend() {
        return dividend;
    }

//    private static final String 

    @RFAType(type=DIVIDEND)
    @Adapt(using=OMMNumericAdapter.class)
    public void setDividend(BigDecimal dividend) {
        this.dividend = dividend;
    }

    @UsingKey(type=NUM_MOVES)
    public BigInteger getTotalTradesToday() {
        return totalTradesToday;
    }

//    private static final String 

    @RFAType(type=NUM_MOVES)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTotalTradesToday(BigInteger totalTradesToday) {
        this.totalTradesToday = totalTradesToday;
    }

    @UsingKey(type=OFFCL_CODE)
    public String getOfficialCode() {
        return officialCode;
    }

//    private static final String 

    @RFAType(type=OFFCL_CODE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOfficialCode(String officialCode) {
        this.officialCode = officialCode;
    }

    @UsingKey(type=HSTCLSDATE)
    public Long getHistoricCloseDate() {
        return historicCloseDate;
    }

//    private static final String 

    @RFAType(type=HSTCLSDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setHistoricCloseDate(Long historicCloseDate) {
        this.historicCloseDate = historicCloseDate;
    }

    @UsingKey(type=YRHIGH)
    public BigDecimal getYearHigh() {
        return yearHigh;
    }

//    private static final String 

    @RFAType(type=YRHIGH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearHigh(BigDecimal yearHigh) {
        this.yearHigh = yearHigh;
    }

    @UsingKey(type=YRLOW)
    public BigDecimal getYearLow() {
        return yearLow;
    }

//    private static final String 

    @RFAType(type=YRLOW)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearLow(BigDecimal yearLow) {
        this.yearLow = yearLow;
    }

    @UsingKey(type=TURNOVER)
    public BigDecimal getTurnover() {
        return turnover;
    }

//    private static final String 

    @RFAType(type=TURNOVER)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    @UsingKey(type=BOND_TYPE)
    public String getBondType() {
        return bondType;
    }

//    private static final String 

    @RFAType(type=BOND_TYPE)
    @Adapt(using=OMMEnumAdapter.class)
    public void setBondType(String bondType) {
        this.bondType = bondType;
    }

    @UsingKey(type=BCKGRNDPAG)
    public String getBackgroundPage() {
        return backgroundPage;
    }

//    private static final String 

    @RFAType(type=BCKGRNDPAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setBackgroundPage(String backgroundPage) {
        this.backgroundPage = backgroundPage;
    }

    @UsingKey(type=YCHIGH_IND)
    public String getYearOrContractHighIndicator() {
        return yearOrContractHighIndicator;
    }

//    private static final String 

    /**
     * Requires visual inspection still as I have not seen this value set to
     * anything other than "   ".
     *
     * @param yearOrContractHighIndicator
     */
    @RFAType(type=YCHIGH_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearOrContractHighIndicator(
        String yearOrContractHighIndicator) {
        this.yearOrContractHighIndicator = yearOrContractHighIndicator;
    }

    @UsingKey(type=YCLOW_IND)
    public String getYearOrContractLowIndicator() {
        return yearOrContractLowIndicator;
    }

//    private static final String 

    /**
     * Requires visual inspection still as I have not seen this value set to
     * anything other than "   ".
     *
     * @param yearOrContractLowIndicator
     */
    @RFAType(type=YCLOW_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearOrContractLowIndicator(
        String yearOrContractLowIndicator) {
        this.yearOrContractLowIndicator = yearOrContractLowIndicator;
    }

    @UsingKey(type=BID_NET_CH)
    public BigDecimal getBidNetChange() {
        return bidNetChange;
    }

//    private static final String 

    @RFAType(type=BID_NET_CH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBidNetChange(BigDecimal bidNetChange) {
        this.bidNetChange = bidNetChange;
    }

    @UsingKey(type=BID_TICK_1)
    public String getBidTick1() {
        return bidTick1;
    }

//    private static final String 

    @RFAType(type=BID_TICK_1)
    @Adapt(using=OMMEnumAdapter.class)
    public void setBidTick1(String bidTick1) {
        this.bidTick1 = bidTick1;
    }

    @UsingKey(type=CUM_EX_MKR)
    public String getCumExMarker() {
        return cumExMarker;
    }

//    private static final String 

    @RFAType(type=CUM_EX_MKR)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCumExMarker(String cumExMarker) {
        this.cumExMarker = cumExMarker;
    }

    @UsingKey(type=PRC_QL_CD)
    public String getPriceCode() {
        return priceCode;
    }

//    private static final String 

    @RFAType(type=PRC_QL_CD)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
    }

    @UsingKey(type=NASDSTATUS)
    public String getNasdStatus() {
        return nasdStatus;
    }

//    private static final String 

    @RFAType(type=NASDSTATUS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setNasdStatus(String nasdStatus) {
        this.nasdStatus = nasdStatus;
    }

    @UsingKey(type=PRC_QL2)
    public String getPriceCode2() {
        return priceCode2;
    }

//    private static final String 

    @RFAType(type=PRC_QL2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceCode2(String priceCode2) {
        this.priceCode2 = priceCode2;
    }

    @UsingKey(type=TRDVOL_1)
    public BigInteger getTradeVolume() {
        return tradeVolume;
    }

//    private static final String 

    @RFAType(type=TRDVOL_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTradeVolume(BigInteger tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    @UsingKey(type=BID_HIGH_1)
    public BigDecimal getTodaysHighBid() {
        return todaysHighBid;
    }

//    private static final String 

    @RFAType(type=BID_HIGH_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysHighBid(BigDecimal todaysHighBid) {
        this.todaysHighBid = todaysHighBid;
    }

    @UsingKey(type=BID_LOW_1)
    public BigDecimal getTodaysLowBid() {
        return todaysLowBid;
    }

//    private static final String 

    @RFAType(type=BID_LOW_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysLowBid(BigDecimal todaysLowBid) {
        this.todaysLowBid = todaysLowBid;
    }

    @UsingKey(type=YRBIDHIGH)
    public BigDecimal getYearHighBid() {
        return yearHighBid;
    }

//    private static final String 

    @RFAType(type=YRBIDHIGH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearHighBid(BigDecimal yearHighBid) {
        this.yearHighBid = yearHighBid;
    }

    @UsingKey(type=YRBIDLOW)
    public BigDecimal getYearLowBid() {
        return yearLowBid;
    }

//    private static final String 

    @RFAType(type=YRBIDLOW)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearLowBid(BigDecimal yearLowBid) {
        this.yearLowBid = yearLowBid;
    }

    @UsingKey(type=HST_CLSBID)
    public BigDecimal getHistoricalClosingBid() {
        return historicalClosingBid;
    }

//    private static final String 

    @RFAType(type=HST_CLSBID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setHistoricalClosingBid(BigDecimal historicalClosingBid) {
        this.historicalClosingBid = historicalClosingBid;
    }

    @UsingKey(type=HSTCLBDDAT)
    public Long getHistoricalClosingBidDate() {
        return historicalClosingBidDate;
    }

//    private static final String 

    @RFAType(type=HSTCLBDDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setHistoricalClosingBidDate(Long historicalClosingBidDate) {
        this.historicalClosingBidDate = historicalClosingBidDate;
    }

    @UsingKey(type=YRBDHI_IND)
    public String getYearBidHigh() {
        return yearBidHigh;
    }

//    private static final String 

    @RFAType(type=YRBDHI_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearBidHigh(String yearBidHigh) {
        this.yearBidHigh = yearBidHigh;
    }

    @UsingKey(type=YRBDLO_IND)
    public String getYearBidLow() {
        return yearBidLow;
    }

//    private static final String 

    @RFAType(type=YRBDLO_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearBidLow(String yearBidLow) {
        this.yearBidLow = yearBidLow;
    }

    @UsingKey(type=NUM_BIDS)
    public BigInteger getNumberOfBids() {
        return numberOfBids;
    }

//    private static final String 

    @RFAType(type=NUM_BIDS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setNumberOfBids(BigInteger numberOfBids) {
        this.numberOfBids = numberOfBids;
    }

//    @UsingKey(type=RECORDTYPE)
//    public BigInteger getRecordType() {
//        return recordType;
//    }
//
//    @RFAType(type=RECORDTYPE)
//    @Adapt(using=OMMNumericAdapter.class)
//    public void setRecordType(BigInteger recordType) {
//        this.recordType = recordType;
//    }

    @UsingKey(type=BID_MMID1)
    public String getMarketParticipantBidId() {
        return marketParticipantBidId;
    }

//    private static final String 

    @RFAType(type=BID_MMID1)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setMarketParticipantBidId(String marketParticipantBidId) {
        this.marketParticipantBidId = marketParticipantBidId;
    }

    @UsingKey(type=ASK_MMID1)
    public String getMarketParticipantAskId() {
        return marketParticipantAskId;
    }

//    private static final String 

    @RFAType(type=ASK_MMID1)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setMarketParticipantAskId(String marketParticipantAskId) {
        this.marketParticipantAskId = marketParticipantAskId;
    }

    @UsingKey(type=OPTION_XID)
    public String getOptionExchangeId() {
        return optionExchangeId;
    }

//    private static final String 

    @RFAType(type=OPTION_XID)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOptionExchangeId(String optionExchangeId) {
        this.optionExchangeId = optionExchangeId;
    }

    @UsingKey(type=YRHIGHDAT)
    public Long getYearHighDate() {
        return yearHighDate;
    }

//    private static final String 

    @RFAType(type=YRHIGHDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setYearHighDate(Long yearHighDate) {
        this.yearHighDate = yearHighDate;
    }

    @UsingKey(type=YRLOWDAT)
    public Long getYearLowDate() {
        return yearLowDate;
    }

//    private static final String 

    @RFAType(type=YRLOWDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setYearLowDate(Long yearLowDate) {
        this.yearLowDate = yearLowDate;
    }

    @UsingKey(type=IRGPRC)
    public BigDecimal getIrgPrice() {
        return irgPrice;
    }

//    private static final String 

    @RFAType(type=IRGPRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgPrice(BigDecimal irgPrice) {
        this.irgPrice = irgPrice;
    }

    @UsingKey(type=IRGVOL)
    public BigInteger getIrgVolume() {
        return irgVolume;
    }

//    private static final String 

    @RFAType(type=IRGVOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgVolume(BigInteger irgVolume) {
        this.irgVolume = irgVolume;
    }

    @UsingKey(type=IRGCOND)
    public String getIrgPriceType() {
        return irgPriceType;
    }

//    private static final String 

    @RFAType(type=IRGCOND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setIrgPriceType(String irgPriceType) {
        this.irgPriceType = irgPriceType;
    }

    @UsingKey(type=TIMCOR)
    public Long getPriceCorrectionTime() {
        return priceCorrectionTime;
    }

//    private static final String 

    @RFAType(type=TIMCOR)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setPriceCorrectionTime(Long priceCorrectionTime) {
        this.priceCorrectionTime = priceCorrectionTime;
    }

    @UsingKey(type=INSPRC)
    public BigDecimal getInsertPrice() {
        return insertPrice;
    }

//    private static final String 

    @RFAType(type=INSPRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setInsertPrice(BigDecimal insertPrice) {
        this.insertPrice = insertPrice;
    }

    @UsingKey(type=INSVOL)
    public BigInteger getInsertVolume() {
        return insertVolume;
    }

//    private static final String 

    @RFAType(type=INSVOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setInsertVolume(BigInteger insertVolume) {
        this.insertVolume = insertVolume;
    }

    @UsingKey(type=INSCOND)
    public String getInsertPriceType() {
        return insertPriceType;
    }

//    private static final String 

    @RFAType(type=INSCOND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setInsertPriceType(String insertPriceType) {
        this.insertPriceType = insertPriceType;
    }

    @UsingKey(type=SALTIM)
    public Long getLastTime() {
        return lastTime;
    }

//    private static final String 

    @RFAType(type=SALTIM)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    @UsingKey(type=TNOVER_SC)
    public String getTurnoverScale() {
        return turnoverScale;
    }

//    private static final String 

    @RFAType(type=TNOVER_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTurnoverScale(String turnoverScale) {
        this.turnoverScale = turnoverScale;
    }

    @UsingKey(type=BCAST_REF)
    public String getBroadcastXRef() {
        return broadcastXRef;
    }

//    private static final String 

    @RFAType(type=BCAST_REF)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setBroadcastXRef(String broadcastXRef) {
        this.broadcastXRef = broadcastXRef;
    }

    @UsingKey(type=CROSS_SC)
    public String getCrossRateScale() {
        return crossRateScale;
    }

//    private static final String 

    @RFAType(type=CROSS_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCrossRateScale(String crossRateScale) {
        this.crossRateScale = crossRateScale;
    }

    @UsingKey(type=AMT_OS)
    public BigDecimal getAmountOutstanding() {
        return amountOutstanding;
    }

//    private static final String 

    @RFAType(type=AMT_OS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAmountOutstanding(BigDecimal amountOutstanding) {
        this.amountOutstanding = amountOutstanding;
    }

    @UsingKey(type=AMT_OS_SC)
    public String getAmountOutstandingScale() {
        return amountOutstandingScale;
    }

//    private static final String 

    @RFAType(type=AMT_OS_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setAmountOutstandingScale(String amountOutstandingScale) {
        this.amountOutstandingScale = amountOutstandingScale;
    }

    @UsingKey(type=OFF_CD_IND)
    public String getOfficialCodeIndicator() {
        return officialCodeIndicator;
    }

//    private static final String 

    @RFAType(type=OFF_CD_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setOfficialCodeIndicator(String officialCodeIndicator) {
        this.officialCodeIndicator = officialCodeIndicator;
    }

    @UsingKey(type=PRC_VOLTY)
    public BigDecimal getPriceVolatility() {
        return priceVolatility;
    }

//    private static final String 

    @RFAType(type=PRC_VOLTY)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceVolatility(BigDecimal priceVolatility) {
        this.priceVolatility = priceVolatility;
    }

    @UsingKey(type=AMT_OS_DAT)
    public Long getAmountOutstandingDate() {
        return amountOutstandingDate;
    }

//    private static final String 

    @RFAType(type=AMT_OS_DAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setAmountOutstandingDate(Long amountOutstandingDate) {
        this.amountOutstandingDate = amountOutstandingDate;
    }

    @UsingKey(type=BKGD_REF)
    public String getBackgroundReference() {
        return backgroundReference;
    }

//    private static final String 

    @RFAType(type=BKGD_REF)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setBackgroundReference(String backgroundReference) {
        this.backgroundReference = backgroundReference;
    }

    @UsingKey(type=GEN_VAL1)
    public BigDecimal getGeneralPurposeValue1() {
        return generalPurposeValue1;
    }

//    private static final String 

    @RFAType(type=GEN_VAL1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue1(BigDecimal generalPurposeValue1) {
        this.generalPurposeValue1 = generalPurposeValue1;
    }

    @UsingKey(type=GV1_TEXT)
    public String getGeneralPurposeValue1Description() {
        return generalPurposeValue1Description;
    }

    private static final String GENERAL_PURPOSE_VALUE_1_DESCRIPTION = "generalPurposeValue1Description";

    @RFAType(type=GV1_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue1Description(
        String generalPurposeValue1Description) {

        String oldValue = this.generalPurposeValue1Description;

        this.generalPurposeValue1Description = generalPurposeValue1Description;

        firePropertyChange(GENERAL_PURPOSE_VALUE_1_DESCRIPTION, oldValue, generalPurposeValue1Description);
    }

    @UsingKey(type=GEN_VAL2)
    public BigDecimal getGeneralPurposeValue2() {
        return generalPurposeValue2;
    }

    private static final String GENERAL_PURPOSE_VALUE_2 = "generalPurposeValue2";

    @RFAType(type=GEN_VAL2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue2(BigDecimal generalPurposeValue2) {

        BigDecimal oldValue = this.generalPurposeValue2;

        this.generalPurposeValue2 = generalPurposeValue2;

        firePropertyChange(GENERAL_PURPOSE_VALUE_2, oldValue, generalPurposeValue2);
    }

    @UsingKey(type=GV2_TEXT)
    public String getGeneralPurposeValue2Description() {
        return generalPurposeValue2Description;
    }

    private static final String GENERAL_PURPOSE_VALUE_2_DESCRIPTION = "generalPurposeValue2Description";

    @RFAType(type=GV2_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue2Description(
        String generalPurposeValue2Description) {

        String oldValue = this.generalPurposeValue2Description;

        this.generalPurposeValue2Description = generalPurposeValue2Description;

        firePropertyChange(GENERAL_PURPOSE_VALUE_2_DESCRIPTION, oldValue, generalPurposeValue2Description);
    }

    @UsingKey(type=GEN_VAL3)
    public BigDecimal getGeneralPurposeValue3() {
        return generalPurposeValue3;
    }

    private static final String GENERAL_PURPOSE_VALUE_3 = "generalPurposeValue3";

    @RFAType(type=GEN_VAL3)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue3(BigDecimal generalPurposeValue3) {

        BigDecimal oldValue = this.generalPurposeValue3;

        this.generalPurposeValue3 = generalPurposeValue3;

        firePropertyChange(GENERAL_PURPOSE_VALUE_3, oldValue, generalPurposeValue3);
    }

    @UsingKey(type=GV3_TEXT)
    public String getGeneralPurposeValue3Description() {
        return generalPurposeValue3Description;
    }

    private static final String GENERAL_PURPOSE_VALUE_3_DESCRIPTION = "generalPurposeValue3Description";

    @RFAType(type=GV3_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue3Description(
        String generalPurposeValue3Description) {

        String oldValue = this.generalPurposeValue3Description;

        this.generalPurposeValue3Description = generalPurposeValue3Description;

        firePropertyChange(GENERAL_PURPOSE_VALUE_3_DESCRIPTION, oldValue, generalPurposeValue3Description);
    }

    @UsingKey(type=GEN_VAL4)
    public BigDecimal getGeneralPurposeValue4() {
        return generalPurposeValue4;
    }

    private static final String GENERAL_PURPOSE_VALUE_4 = "generalPurposeValue4";

    @RFAType(type=GEN_VAL4)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue4(BigDecimal generalPurposeValue4) {

        BigDecimal oldValue = this.generalPurposeValue4;

        this.generalPurposeValue4 = generalPurposeValue4;

        firePropertyChange(GENERAL_PURPOSE_VALUE_4, oldValue, generalPurposeValue4);
    }

    @UsingKey(type=GV4_TEXT)
    public String getGeneralPurposeValue4Description() {
        return generalPurposeValue4Description;
    }

    private static final String GENERAL_PURPOSE_VALUE_4_DESCRIPTION = "generalPurposeValue4Description";

    @RFAType(type=GV4_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue4Description(
        String generalPurposeValue4Description) {

        String oldValue = this.generalPurposeValue4Description;

        this.generalPurposeValue4Description = generalPurposeValue4Description;

        firePropertyChange(GENERAL_PURPOSE_VALUE_4_DESCRIPTION, oldValue, generalPurposeValue4Description);
    }

    @UsingKey(type=SEQNUM)
    public BigInteger getSequenceNumber() {
        return sequenceNumber;
    }

    private static final String SEQUENCE_NUMBER = "sequenceNumber";

    @RFAType(type=SEQNUM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSequenceNumber(BigInteger sequenceNumber) {

        BigInteger oldValue = this.sequenceNumber;

        this.sequenceNumber = sequenceNumber;

        firePropertyChange(SEQUENCE_NUMBER, oldValue, sequenceNumber);
    }

    @UsingKey(type=PRNTYP)
    public String getPrintType() {
        return printType;
    }

    private static final String PRINT_TYPE = "printType";

    @RFAType(type=PRNTYP)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setPrintType(String printType) {

        String oldValue = this.printType;

        this.printType = printType;

        firePropertyChange(PRINT_TYPE, oldValue, printType);
    }

    @UsingKey(type=PRNTBCK)
    public BigInteger getAlteredTradeEventSequenceNumber() {
        return alteredTradeEventSequenceNumber;
    }

    private static final String ALTERED_TRADE_EVENTS_SEQUENCE_NUMBER = "alteredTradeEventSequenceNumber";

    @RFAType(type=PRNTBCK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAlteredTradeEventSequenceNumber(
        BigInteger alteredTradeEventSequenceNumber) {

        BigInteger oldValue = alteredTradeEventSequenceNumber;

        this.alteredTradeEventSequenceNumber = alteredTradeEventSequenceNumber;

        firePropertyChange(ALTERED_TRADE_EVENTS_SEQUENCE_NUMBER, oldValue, alteredTradeEventSequenceNumber);
    }

    @UsingKey(type=QUOTIM)
    public Long getQuoteTimeSeconds() {
        return quoteTimeSeconds;
    }

    private static final String QUOTE_TIME_SECONDS = "quoteTimeSeconds";

    @RFAType(type=QUOTIM)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setQuoteTimeSeconds(Long quoteTimeSeconds) {

        Long oldValue = this.quoteTimeSeconds;

        this.quoteTimeSeconds = quoteTimeSeconds;

        firePropertyChange(QUOTE_TIME_SECONDS, oldValue, quoteTimeSeconds);
    }

    @UsingKey(type=GV1_FLAG)
    public String getGenericFlag1() {
        return genericFlag1;
    }

    private static final String GENERIC_FLAG_1 = "genericFlag1";

    @RFAType(type=GV1_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag1(String genericFlag1) {

        String oldValue = this.genericFlag1;

        this.genericFlag1 = genericFlag1;

        firePropertyChange(GENERIC_FLAG_1, oldValue, genericFlag1);
    }

    @UsingKey(type=GV2_FLAG)
    public String getGenericFlag2() {
        return genericFlag2;
    }

    private static final String GENERIC_FLAG_2 = "genericFlag2";

    @RFAType(type=GV2_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag2(String genericFlag2) {

        String oldValue = this.genericFlag2;

        this.genericFlag2 = genericFlag2;

        firePropertyChange(GENERIC_FLAG_2, oldValue, genericFlag2);
    }

    @UsingKey(type=GV3_FLAG)
    public String getGenericFlag3() {
        return genericFlag3;
    }

    private static final String GENERIC_FLAG_3 = "genericFlag3";

    @RFAType(type=GV3_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag3(String genericFlag3) {

        String oldValue = this.genericFlag3;

        this.genericFlag3 = genericFlag3;

        firePropertyChange(GENERIC_FLAG_3, oldValue, genericFlag3);
    }

    @UsingKey(type=GV4_FLAG)
    public String getGenericFlag4() {
        return genericFlag4;
    }

    private static final String GENERIC_FLAG_4 = "genericFlag4";

    @RFAType(type=GV4_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag4(String genericFlag4) {

        String oldValue = this.genericFlag4;

        this.genericFlag4 = genericFlag4;

        firePropertyChange(GENERIC_FLAG_4, oldValue, genericFlag4);
    }

    @UsingKey(type=OFF_CD_IN2)
    public String getUniqueInstrumentId2Source() {
        return uniqueInstrumentId2Source;
    }

    private static final String UNIQUE_INSTRUMENT_ID_2_SOURCE = "uniqueInstrumentId2Source";

    @RFAType(type=OFF_CD_IN2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setUniqueInstrumentId2Source(String uniqueInstrumentId2Source) {
        
        String oldValue = this.uniqueInstrumentId2Source;

        this.uniqueInstrumentId2Source = uniqueInstrumentId2Source;

        firePropertyChange(UNIQUE_INSTRUMENT_ID_2_SOURCE, oldValue, uniqueInstrumentId2Source);
    }

    @UsingKey(type=OFFC_CODE2)
    public String getUniqueInstrumentId2() {
        return uniqueInstrumentId2;
    }

    private static final String UNIQUE_INSTRUMENT_ID_2 = "uniqueInstrumentId2";

    @RFAType(type=OFFC_CODE2)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setUniqueInstrumentId2(String uniqueInstrumentId2) {

        String oldValue = this.uniqueInstrumentId2;

        this.uniqueInstrumentId2 = uniqueInstrumentId2;

        firePropertyChange(UNIQUE_INSTRUMENT_ID_2, oldValue, uniqueInstrumentId2);
    }

    @UsingKey(type=GV1_TIME)
    public Long getTimeInSeconds1() {
        return timeInSeconds1;
    }

    private static final String TIME_IN_SECONDS_1 = "timeInSeconds1";

    @RFAType(type=GV1_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTimeInSeconds1(Long timeInSeconds1) {

        Long oldValue = this.timeInSeconds1;

        this.timeInSeconds1 = timeInSeconds1;

        firePropertyChange(TIME_IN_SECONDS_1, oldValue, timeInSeconds1);
    }

    @UsingKey(type=GV2_TIME)
    public Long getTimeInSeconds2() {
        return timeInSeconds2;
    }

    private static final String TIME_IN_SECONDS_2 = "timeInSeconds2";

    @RFAType(type=GV2_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTimeInSeconds2(Long timeInSeconds2) {

    	Long oldValue = this.timeInSeconds2;

        this.timeInSeconds2 = timeInSeconds2;

        firePropertyChange(TIME_IN_SECONDS_2, oldValue, timeInSeconds2);
    }

    @UsingKey(type=EXCHTIM)
    public Long getExchangeTime() {
        return exchangeTime;
    }

    private static final String EXCHANGE_TIME = "exchangeTime";

    @RFAType(type=EXCHTIM)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExchangeTime(Long exchangeTime) {

        Long oldValue = this.exchangeTime;

        this.exchangeTime = exchangeTime;

        firePropertyChange(EXCHANGE_TIME, oldValue, exchangeTime);
    }

    @UsingKey(type=YRHI_IND)
    public String getYearHighIndicator() {
        return yearHighIndicator;
    }

    private static final String YEAR_HIGH_INDICATOR = "yearHighIndicator";

    @RFAType(type=YRHI_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearHighIndicator(String yearHighIndicator) {

        String oldValue = this.yearHighIndicator;

        this.yearHighIndicator = yearHighIndicator;

        firePropertyChange(YEAR_HIGH_INDICATOR, oldValue, yearHighIndicator);
    }

    @UsingKey(type=YRLO_IND)
    public String getYearLowIndicator() {
        return yearLowIndicator;
    }

    private static final String YEAR_LOW_INDICATOR = "yearLowIndicator";

    @RFAType(type=YRLO_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearLowIndicator(String yearLowIndicator) {

        String oldValue = this.yearLowIndicator;

        this.yearLowIndicator = yearLowIndicator;

        firePropertyChange(YEAR_LOW_INDICATOR, oldValue, yearLowIndicator);
    }

    @UsingKey(type=BETA_VAL)
    public BigDecimal getBetaValue() {
        return betaValue;
    }

    private static final String BETA_VALUE = "betaValue";

    @RFAType(type=BETA_VAL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBetaValue(BigDecimal betaValue) {

        BigDecimal oldValue = this.betaValue;

        this.betaValue = betaValue;

        firePropertyChange(BETA_VALUE, oldValue, betaValue);
    }

    @UsingKey(type=PREF_DISP)
    public Integer getPreferredDisplayTemplateNumber() {
        return preferredDisplayTemplateNumber;
    }

    private static final String PREFERRED_DISPLAY_TEMPLATE_NUMBER = "preferredDisplayTemplateNumber";

    @RFAType(type=PREF_DISP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPreferredDisplayTemplateNumber(
        Integer preferredDisplayTemplateNumber) {

        Integer oldValue = this.preferredDisplayTemplateNumber;

        this.preferredDisplayTemplateNumber = preferredDisplayTemplateNumber;

        firePropertyChange(PREFERRED_DISPLAY_TEMPLATE_NUMBER, oldValue, preferredDisplayTemplateNumber);
    }

    @UsingKey(type=DSPLY_NMLL)
    public String getLocalLanguageInstrumentName() {
        return localLanguageInstrumentName;
    }

    private static final String LOCAL_LANGUAGE_INSTRUMENT_NAME = "localLanguageInstrumentName";

    @RFAType(type=DSPLY_NMLL)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setLocalLanguageInstrumentName(
        String localLanguageInstrumentName) {

        String oldValue = this.localLanguageInstrumentName;

        this.localLanguageInstrumentName = localLanguageInstrumentName;

        firePropertyChange(LOCAL_LANGUAGE_INSTRUMENT_NAME, oldValue, localLanguageInstrumentName);
    }

    @UsingKey(type=VOL_X_PRC1)
    public BigDecimal getLatestTradeOrTradeTurnoverValue() {
        return latestTradeOrTradeTurnoverValue;
    }

    private static final String LATEST_TRADE_OR_TRADE_TURNOVER_VALUE = "latestTradeOrTradeTurnoverValue";

    @RFAType(type=VOL_X_PRC1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLatestTradeOrTradeTurnoverValue(
        BigDecimal latestTradeOrTradeTurnoverValue) {

        BigDecimal oldValue = this.latestTradeOrTradeTurnoverValue;

        this.latestTradeOrTradeTurnoverValue = latestTradeOrTradeTurnoverValue;

        firePropertyChange(LATEST_TRADE_OR_TRADE_TURNOVER_VALUE, oldValue, latestTradeOrTradeTurnoverValue);
    }

    @UsingKey(type=DSO_ID)
    public Integer getDataSourceOwnerId() {
        return dataSourceOwnerId;
    }

    private static final String DATA_SOURCE_OWNER_ID = "dataSourceOwnerId";

    @RFAType(type=DSO_ID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setDataSourceOwnerId(Integer dataSourceOwnerId) {

        Integer oldValue = this.dataSourceOwnerId;

        this.dataSourceOwnerId = dataSourceOwnerId;

        firePropertyChange(DATA_SOURCE_OWNER_ID, oldValue, dataSourceOwnerId);
    }

    @UsingKey(type=AVERG_PRC)
    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    private static final String AVERAGE_PRICE = "averagePrice";

    @RFAType(type=AVERG_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAveragePrice(BigDecimal averagePrice) {

        BigDecimal oldValue = this.averagePrice;

        this.averagePrice = averagePrice;

        firePropertyChange(AVERAGE_PRICE, oldValue, averagePrice);
    }

    @UsingKey(type=UPC71_REST)
    public String getUpc71RestrictedFlag() {
        return upc71RestrictedFlag;
    }

    private static final String UPC_71_RESTRICTED_FLAG = "upc71RestrictedFlag";

    @RFAType(type=UPC71_REST)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setUpc71RestrictedFlag(String upc71RestrictedFlag) {

        String oldValue = this.upc71RestrictedFlag;

        this.upc71RestrictedFlag = upc71RestrictedFlag;

        firePropertyChange(UPC_71_RESTRICTED_FLAG, oldValue, upc71RestrictedFlag);
    }

    @UsingKey(type=ADJUST_CLS)
    public BigDecimal getAdjustedClose() {
        return adjustedClose;
    }

    private static final String ADJUSTED_CLOSE = "adjustedClose";

    @RFAType(type=ADJUST_CLS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAdjustedClose(BigDecimal adjustedClose) {

        BigDecimal oldValue = this.adjustedClose;

        this.adjustedClose = adjustedClose;

        firePropertyChange(ADJUSTED_CLOSE, oldValue, adjustedClose);
    }

    @UsingKey(type=WEIGHTING)
    public BigDecimal getWeighting() {
        return weighting;
    }

    public static final String WEIGHTING = "weighting";

    @RFAType(type=MarketPriceConstants.WEIGHTING)
    @Adapt(using=OMMNumericAdapter.class)
    public void setWeighting(BigDecimal weighting) {

        BigDecimal oldValue = this.weighting;

        this.weighting = weighting;

        firePropertyChange(MarketPrice.WEIGHTING, oldValue, weighting);
    }

    @UsingKey(type=STOCK_TYPE)
    public String getStockType() {
        return stockType;
    }

    public static final String STOCK_TYPE = "stockType";

    @RFAType(type=MarketPriceConstants.STOCK_TYPE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setStockType(String stockType) {

        String oldValue = this.stockType;

        this.stockType = stockType;

        firePropertyChange(MarketPrice.STOCK_TYPE, oldValue, stockType);
    }

    @UsingKey(type=IMP_VOLT)
    public BigDecimal getImpliedVolatility() {
        return impliedVolatility;
    }

    public static final String IMPLIED_VOLATILITY = "impliedVolatility";

    @RFAType(type=IMP_VOLT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setImpliedVolatility(BigDecimal impliedVolatility) {

        BigDecimal oldValue = this.impliedVolatility;

        this.impliedVolatility = impliedVolatility;

        firePropertyChange(MarketPrice.IMPLIED_VOLATILITY, oldValue, impliedVolatility);
    }

//    @UsingKey(type=RDN_EXCHD2)
//    public String getExchangeId2() {
//        return exchangeId2;
//    }
//
//    @RFAType(type=RDN_EXCHD2)
//    @Adapt(using=OMMEnumAdapter.class)
//    public void setExchangeId2(String exchangeId2) {
//        this.exchangeId2 = exchangeId2;
//    }

    @UsingKey(type=CP_ADJ_FCT)
    public BigDecimal getCapitalAdjustmentFactor() {
        return capitalAdjustmentFactor;
    }

    public static final String CAPITAL_ADJUSTMENT_FACTOR = "capitalAdjustmentFactor";

    @RFAType(type=CP_ADJ_FCT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCapitalAdjustmentFactor(BigDecimal capitalAdjustmentFactor) {

        BigDecimal oldValue = this.capitalAdjustmentFactor;

        this.capitalAdjustmentFactor = capitalAdjustmentFactor;

        firePropertyChange(MarketPrice.CAPITAL_ADJUSTMENT_FACTOR, oldValue, capitalAdjustmentFactor);
    }

    @UsingKey(type=CP_ADJ_DAT)
    public Long getCapitalAdjustmentDate() {
        return capitalAdjustmentDate;
    }

    public static final String CAPITAL_ADJUSTMENT_DATE = "capitalAdjustmentDate";

    @RFAType(type=CP_ADJ_DAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setCapitalAdjustmentDate(Long capitalAdjustmentDate) {

        Long oldValue = this.capitalAdjustmentDate;

        this.capitalAdjustmentDate = capitalAdjustmentDate;

        firePropertyChange(MarketPrice.CAPITAL_ADJUSTMENT_DATE, oldValue, capitalAdjustmentDate);
    }

    @UsingKey(type=AMT_ISSUE)
    public BigInteger getSharesIssuedTotal() {
        return sharesIssuedTotal;
    }

    public static final String SHARES_ISSUED_TOTAL = "sharesIssuedTotal";

    @RFAType(type=AMT_ISSUE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSharesIssuedTotal(BigInteger sharesIssuedTotal) {

        BigInteger oldValue = this.sharesIssuedTotal;

        this.sharesIssuedTotal = sharesIssuedTotal;

        firePropertyChange(MarketPrice.SHARES_ISSUED_TOTAL, oldValue, sharesIssuedTotal);
    }

    @UsingKey(type=MKT_VALUE)
    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public static final String MARKET_VALUE = "marketValue";

    @RFAType(type=MKT_VALUE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setMarketValue(BigDecimal marketValue) {

        BigDecimal oldValue = this.marketValue;

        this.marketValue = marketValue;

        firePropertyChange(MarketPrice.MARKET_VALUE, oldValue, marketValue);
    }

    @UsingKey(type=SPEC_TRADE)
    public Integer getSpecialTermsTradingFlag() {
        return specialTermsTradingFlag;
    }

    private static final String SPECIAL_TERMS_TRADING_FLAG = "specialTermsTradingFlag";

    @RFAType(type=SPEC_TRADE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSpecialTermsTradingFlag(Integer specialTermsTradingFlag) {

        Integer oldValue = this.specialTermsTradingFlag;

        this.specialTermsTradingFlag = specialTermsTradingFlag;

        firePropertyChange(MarketPrice.SPECIAL_TERMS_TRADING_FLAG, oldValue, specialTermsTradingFlag);
    }

    @UsingKey(type=FCAST_EARN)
    public BigDecimal getForecastedEarnings() {
        return forecastedEarnings;
    }

    public static final String FORECASTED_EARNINGS = "forecastedEarnings";

    @RFAType(type=FCAST_EARN)
    @Adapt(using=OMMNumericAdapter.class)
    public void setForecastedEarnings(BigDecimal forecastedEarnings) {

        BigDecimal oldValue = this.forecastedEarnings;

        this.forecastedEarnings = forecastedEarnings;

        firePropertyChange(MarketPrice.FORECASTED_EARNINGS, oldValue, forecastedEarnings);
    }

    @UsingKey(type=EARANK_RAT)
    public BigDecimal getEarningsRankRatio() {
        return earningsRankRatio;
    }

    public static final String EARNINGS_RANK_RATIO = "earningsRankRatio";

    @RFAType(type=EARANK_RAT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setEarningsRankRatio(BigDecimal earningsRankRatio) {

        BigDecimal oldValue = this.earningsRankRatio;

        this.earningsRankRatio = earningsRankRatio;

        firePropertyChange(MarketPrice.EARNINGS_RANK_RATIO, oldValue, earningsRankRatio);
    }

    @UsingKey(type=FCAST_DATE)
    public Long getForecastDate() {
        return forecastDate;
    }

    public static final String FORECAST_DATE = "forecastDate";

    @RFAType(type=FCAST_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setForecastDate(Long forecastDate) {

        Long oldValue = this.forecastDate;

        this.forecastDate = forecastDate;

        firePropertyChange(MarketPrice.FORECAST_DATE, oldValue, forecastDate);
    }

    @UsingKey(type=YEAR_FCAST)
    public String getForecastYear() {
        return forecastYear;
    }

    public static final String FORECAST_YEAR = "forecastYear";

    @RFAType(type=YEAR_FCAST)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setForecastYear(String forecastYear) {

        String oldValue = this.forecastYear;

        this.forecastYear = forecastYear;

        firePropertyChange(MarketPrice.FORECAST_YEAR, oldValue, forecastYear);
    }

    @UsingKey(type=IRGMOD)
    public String getIrgPriceTypeModifier() {
        return irgPriceTypeModifier;
    }

    public static final String IRG_PRICE_TYPE_MODIFIER = "irgPriceTypeModifier";

    @RFAType(type=IRGMOD)
    @Adapt(using=OMMEnumAdapter.class)
    public void setIrgPriceTypeModifier(String irgPriceTypeModifier) {

        String oldValue = this.irgPriceTypeModifier;

        this.irgPriceTypeModifier = irgPriceTypeModifier;

        firePropertyChange(MarketPrice.IRG_PRICE_TYPE_MODIFIER, oldValue, irgPriceTypeModifier);
    }

    @UsingKey(type=INSMOD)
    public String getInsertPriceTypeModifier() {
        return insertPriceTypeModifier;
    }

    public static final String INSERT_PRICE_TYPE_MODIFIER = "insertPriceTypeModifier";

    @RFAType(type=INSMOD)
    @Adapt(using=OMMEnumAdapter.class)
    public void setInsertPriceTypeModifier(String insertPriceTypeModifier) {

        String oldValue = this.insertPriceTypeModifier;

        this.insertPriceTypeModifier = insertPriceTypeModifier;

        firePropertyChange(MarketPrice.INSERT_PRICE_TYPE_MODIFIER, oldValue, insertPriceTypeModifier);
    }

    @UsingKey(type=A_NPLRS_1)
    public BigInteger getAskPlayersLevel1() {
        return askPlayersLevel1;
    }

    public static final String ASK_PLAYERS_LEVEL_1 = "askPlayersLevel1";

    @RFAType(type=A_NPLRS_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAskPlayersLevel1(BigInteger askPlayersLevel1) {

        BigInteger oldValue = this.askPlayersLevel1;

        this.askPlayersLevel1 = askPlayersLevel1;

        firePropertyChange(MarketPrice.ASK_PLAYERS_LEVEL_1, oldValue, askPlayersLevel1);
    }

    @UsingKey(type=B_NPLRS_1)
    public BigInteger getBidPlayersLevel1() {
        return bidPlayersLevel1;
    }

    public static final String BID_PLAYERS_LEVEL_1 = "bidPlayersLevel1";

    @RFAType(type=B_NPLRS_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBidPlayersLevel1(BigInteger bidPlayersLevel1) {

        BigInteger oldValue = this.bidPlayersLevel1;

        this.bidPlayersLevel1 = bidPlayersLevel1;

        firePropertyChange(BID_PLAYERS_LEVEL_1, oldValue, bidPlayersLevel1);
    }

    @UsingKey(type=GV3_TIME)
    public Long getGenericTime3() {
        return genericTime3;
    }

    public static final String GENERIC_TIME_3 = "genericTime3";

    @RFAType(type=GV3_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setGenericTime3(Long genericTime3) {

        Long oldValue = this.genericTime3;

        this.genericTime3 = genericTime3;

        firePropertyChange(GENERIC_TIME_3, oldValue, genericTime3);
    }

    @UsingKey(type=GV4_TIME)
    public Long getGenericTime4() {
        return genericTime4;
    }

    public static final String GENERIC_TIME_4 = "genericTime4";

    @RFAType(type=GV4_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setGenericTime4(Long genericTime4) {

        Long oldValue = this.genericTime4;

        this.genericTime4 = genericTime4;

        firePropertyChange(GENERIC_TIME_4, oldValue, genericTime4);
    }

    @UsingKey(type=MKT_CAP)
    public BigInteger getMarketCapitalisation() {
        return marketCapitalisation;
    }

    public static final String MARKET_CAPITALISATION = "marketCapitalisation";

    @RFAType(type=MKT_CAP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setMarketCapitalisation(BigInteger marketCapitalisation) {

        BigInteger oldValue = this.marketCapitalisation;

        this.marketCapitalisation = marketCapitalisation;

        firePropertyChange(MARKET_CAPITALISATION, oldValue, marketCapitalisation);
    }

    @UsingKey(type=IRGFID)
    public BigInteger getIrgCorrectionValueFid() {
        return irgCorrectionValueFid;
    }

    public static final String IRG_CORRECTION_VALUE_FID = "irgCorrectionValueFid";

    @RFAType(type=IRGFID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgCorrectionValueFid(BigInteger irgCorrectionValueFid) {

        BigInteger oldValue = this.irgCorrectionValueFid;

        this.irgCorrectionValueFid = irgCorrectionValueFid;

        firePropertyChange(IRG_CORRECTION_VALUE_FID, oldValue, irgCorrectionValueFid);
    }

    @UsingKey(type=IRGVAL)
    public BigInteger getIrgCorrectionValue() {
        return irgCorrectionValue;
    }

    public static final String IRG_CORRECTION_VALUE = "irgCorrectionValue";

    @RFAType(type=IRGVAL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgCorrectionValue(BigInteger irgCorrectionValue) {

        BigInteger oldValue = this.irgCorrectionValue;

        this.irgCorrectionValue = irgCorrectionValue;

        firePropertyChange(IRG_CORRECTION_VALUE, oldValue, irgCorrectionValue);
    }

    @UsingKey(type=PCT_ABNVOL)
    public BigDecimal getAbnormalVolumeIncreasePercentage() {
        return abnormalVolumeIncreasePercentage;
    }

    public static final String ABNORMAL_VOLUME_INCREASE_PERCENTAGE = "abnormalVolumeIncreasePercentage";

    @RFAType(type=PCT_ABNVOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAbnormalVolumeIncreasePercentage(
        BigDecimal abnormalVolumeIncreasePercentage) {

        BigDecimal oldValue = this.abnormalVolumeIncreasePercentage;

        this.abnormalVolumeIncreasePercentage = abnormalVolumeIncreasePercentage;

        firePropertyChange(ABNORMAL_VOLUME_INCREASE_PERCENTAGE, oldValue, abnormalVolumeIncreasePercentage);
    }

    @UsingKey(type=BC_10_50K)
    public BigInteger getBlockTransactionsBetween10KAnd50KShares() {
        return blockTransactionsBetween10KAnd50KShares;
    }

    public static final String BLOCK_TRANSACTION_BETWEEN_10K_AND_50K_SHARES
        = "blockTransactionsBetween10KAnd50KShares";

    @RFAType(type=BC_10_50K)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockTransactionsBetween10KAnd50KShares(
        BigInteger blockTransactionsBetween10KAnd50KShares) {

        BigInteger oldValue = this.blockTransactionsBetween10KAnd50KShares;

        this.blockTransactionsBetween10KAnd50KShares = blockTransactionsBetween10KAnd50KShares;

        firePropertyChange(
            BLOCK_TRANSACTION_BETWEEN_10K_AND_50K_SHARES,
            oldValue,
            blockTransactionsBetween10KAnd50KShares
        );
    }

    @UsingKey(type=BC_50_100K)
    public BigInteger getBlockTransactionsBetween50KAnd100KShares() {
        return blockTransactionsBetween50KAnd100KShares;
    }

    public static final String BLOCK_TRANSACTION_BETWEEN_50K_AND_100K_SHARES
        = "blockTransactionsBetween50KAnd100KShares";

    @RFAType(type=BC_50_100K)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockTransactionsBetween50KAnd100KShares(
        BigInteger blockTransactionsBetween50KAnd100KShares) {

        BigInteger oldValue = this.blockTransactionsBetween50KAnd100KShares;

        this.blockTransactionsBetween50KAnd100KShares = blockTransactionsBetween50KAnd100KShares;

        firePropertyChange(
            BLOCK_TRANSACTION_BETWEEN_50K_AND_100K_SHARES,
            oldValue,
            blockTransactionsBetween50KAnd100KShares
        );
    }

    @UsingKey(type=BC_100K)
    public BigInteger getBlockTransactionsAbove100KShares() {
        return blockTransactionsAbove100KShares;
    }

    public static final String BLOCK_TRANSACTION_ABOVE_100K_SHARES = "blockTransactionsAbove100KShares";

    @RFAType(type=BC_100K)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockTransactionsAbove100KShares(
        BigInteger blockTransactionsAbove100KShares) {

        BigInteger oldValue = this.blockTransactionsAbove100KShares;

        this.blockTransactionsAbove100KShares = blockTransactionsAbove100KShares;

        firePropertyChange(
            BLOCK_TRANSACTION_ABOVE_100K_SHARES,
            oldValue,
            blockTransactionsAbove100KShares
        );
    }

    @UsingKey(type=PMA_50D)
    public BigDecimal getPriceMovingAverages50D() {
        return priceMovingAverages50D;
    }

    public static final String PRICE_MOVING_AVERAGE_50D = "priceMovingAverages50D";

    @RFAType(type=PMA_50D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceMovingAverages50D(BigDecimal priceMovingAverages50D) {

        BigDecimal oldValue = this.priceMovingAverages50D;

        this.priceMovingAverages50D = priceMovingAverages50D;

        firePropertyChange(
            PRICE_MOVING_AVERAGE_50D,
            oldValue,
            priceMovingAverages50D
        );
    }

    @UsingKey(type=PMA_150D)
    public BigDecimal getPriceMovingAverages150D() {
        return priceMovingAverages150D;
    }

    public static final String PRICE_MOVING_AVERAGES_150D = "priceMovingAverages150D";

    @RFAType(type=PMA_150D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceMovingAverages150D(BigDecimal priceMovingAverages150D) {

        BigDecimal oldValue = this.priceMovingAverages150D;

        this.priceMovingAverages150D = priceMovingAverages150D;

        firePropertyChange(
            PRICE_MOVING_AVERAGES_150D,
            oldValue,
            priceMovingAverages150D
        );
    }

    @UsingKey(type=PMA_200D)
    public BigDecimal getPriceMovingAverages200D() {
        return priceMovingAverages200D;
    }

    public static final String PRICE_MOVING_AVERAGES_200D = "priceMovingAverages200D";

    @RFAType(type=PMA_200D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceMovingAverages200D(BigDecimal priceMovingAverages200D) {

        BigDecimal oldValue = this.priceMovingAverages200D;

        this.priceMovingAverages200D = priceMovingAverages200D;

        firePropertyChange(
            PRICE_MOVING_AVERAGES_200D,
            oldValue,
            priceMovingAverages200D
        );
    }

    @UsingKey(type=VMA_10D)
    public BigInteger getVolumeMovingAverages10D() {
        return volumeMovingAverages10D;
    }

    public static final String VOLUME_MOVING_AVERAGES_10D = "volumeMovingAverages10D";

    @RFAType(type=VMA_10D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeMovingAverages10D(BigInteger volumeMovingAverages10D) {

        BigInteger oldValue = this.volumeMovingAverages10D;

        this.volumeMovingAverages10D = volumeMovingAverages10D;

        firePropertyChange(
            VOLUME_MOVING_AVERAGES_10D,
            oldValue,
            volumeMovingAverages10D
        );
    }

    @UsingKey(type=VMA_25D)
    public BigInteger getVolumeMovingAverages25D() {
        return volumeMovingAverages25D;
    }

    public static final String VOLUME_MOVING_AVERAGES_25D = "volumeMovingAverages25D";

    @RFAType(type=VMA_25D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeMovingAverages25D(BigInteger volumeMovingAverages25D) {

        BigInteger oldValue = this.volumeMovingAverages25D;

        this.volumeMovingAverages25D = volumeMovingAverages25D;

        firePropertyChange(
            VOLUME_MOVING_AVERAGES_25D,
            oldValue,
            volumeMovingAverages25D
        );
    }

    @UsingKey(type=VMA_50D)
    public BigInteger getVolumeMovingAverages50D() {
        return volumeMovingAverages50D;
    }

    public static final String VOLUME_MOVING_AVERAGES_50D = "volumeMovingAverages50D";

    @RFAType(type=VMA_50D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeMovingAverages50D(BigInteger volumeMovingAverages50D) {

        BigInteger oldValue = this.volumeMovingAverages50D;

        this.volumeMovingAverages50D = volumeMovingAverages50D;

        firePropertyChange(
            VOLUME_MOVING_AVERAGES_50D,
            oldValue,
            volumeMovingAverages50D
        );
    }

    @UsingKey(type=OPN_NETCH)
    public BigDecimal getOpenPriceNetChange() {
        return openPriceNetChange;
    }

    public static final String OPEN_PRICE_NET_CHANGE = "openPriceNetChange";

    @RFAType(type=OPN_NETCH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenPriceNetChange(BigDecimal openPriceNetChange) {

        BigDecimal oldValue = this.openPriceNetChange;

        this.openPriceNetChange = openPriceNetChange;

        firePropertyChange(
            OPEN_PRICE_NET_CHANGE,
            oldValue,
            openPriceNetChange
        );
    }

    @UsingKey(type=CASH_EXDIV)
    public BigDecimal getLatestReportedCashDividend() {
        return latestReportedCashDividend;
    }

    public static final String LATEST_REPORTED_CASH_DIVIDEND = "latestReportedCashDividend";

    @RFAType(type=CASH_EXDIV)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLatestReportedCashDividend(BigDecimal latestReportedCashDividend) {

        BigDecimal oldValue = this.latestReportedCashDividend;

        this.latestReportedCashDividend = latestReportedCashDividend;

        firePropertyChange(
            LATEST_REPORTED_CASH_DIVIDEND,
            oldValue,
            latestReportedCashDividend
        );
    }

    @UsingKey(type=MKT_VAL_SC)
    public String getMarketValueScalingFactor() {
        return marketValueScalingFactor;
    }

    public static final String MARKET_VALUE_SCALING_FACTOR = "marketValueScalingFactor";

    @RFAType(type=MKT_VAL_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setMarketValueScalingFactor(String marketValueScalingFactor) {

        String oldValue = this.marketValueScalingFactor;

        this.marketValueScalingFactor = marketValueScalingFactor;

        firePropertyChange(
            MARKET_VALUE_SCALING_FACTOR,
            oldValue,
            marketValueScalingFactor
        );
    }

    @UsingKey(type=CASH_EXDAT)
    public Long getExDividendTradeDate() {
        return exDividendTradeDate;
    }

    public static final String EX_DIVIDEND_TRADE_DATE = "exDividendTradeDate";

    @RFAType(type=CASH_EXDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExDividendTradeDate(Long exDividendTradeDate) {

        Long oldValue = this.exDividendTradeDate;

        this.exDividendTradeDate = exDividendTradeDate;

        firePropertyChange(
            EX_DIVIDEND_TRADE_DATE,
            oldValue,
            exDividendTradeDate
        );
    }

    @UsingKey(type=PREV_DISP)
    public Integer getPreviousDisplayTemplate() {
        return previousDisplayTemplate;
    }

    public static final String PREVIOUS_DISPLAY_TEMPLATE = "previousDisplayTemplate";

    @RFAType(type=PREV_DISP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPreviousDisplayTemplate(Integer previousDisplayTemplate) {

        Integer oldValue = this.previousDisplayTemplate;

        this.previousDisplayTemplate = previousDisplayTemplate;

        firePropertyChange(
            PREVIOUS_DISPLAY_TEMPLATE,
            oldValue,
            previousDisplayTemplate
        );
    }

    @UsingKey(type=PRC_QL3)
    public String getExtendedPriceQualifierFid() {
        return extendedPriceQualifierFid;
    }

    public static final String EXTENDED_PRICE_QUALIFIER_FID = "extendedPriceQualifierFid";

    @RFAType(type=PRC_QL3)
    @Adapt(using=OMMEnumAdapter.class)
    public void setExtendedPriceQualifierFid(String extendedPriceQualifierFid) {

        String oldValue = this.extendedPriceQualifierFid;

        this.extendedPriceQualifierFid = extendedPriceQualifierFid;

        firePropertyChange(
            EXTENDED_PRICE_QUALIFIER_FID,
            oldValue,
            extendedPriceQualifierFid
        );
    }

    @UsingKey(type=MPV)
    public String getMinimumPriceMovement() {
        return minimumPriceMovement;
    }

    public static final String MINIMUM_PRICE_MOVEMENT = "minimumPriceMovement";

    @RFAType(type=MPV)
    @Adapt(using=OMMEnumAdapter.class)
    public void setMinimumPriceMovement(String minimumPriceMovement) {

        String oldValue = this.minimumPriceMovement;

        this.minimumPriceMovement = minimumPriceMovement;

        firePropertyChange(
            MINIMUM_PRICE_MOVEMENT,
            oldValue,
            minimumPriceMovement
        );
    }

    @UsingKey(type=OFF_CLOSE)
    public BigDecimal getOfficialClosingPrice() {
        return officialClosingPrice;
    }

    public static final String OFFICIAL_CLOSING_PRICE = "officialClosingPrice";

    @RFAType(type=MarketPriceConstants.OFF_CLOSE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOfficialClosingPrice(BigDecimal officialClosingPrice) {

        BigDecimal oldValue = this.officialClosingPrice;

        this.officialClosingPrice = officialClosingPrice;

        firePropertyChange(
            OFFICIAL_CLOSING_PRICE,
            oldValue,
            officialClosingPrice
        );
    }

    @UsingKey(type=QUOTE_DATE)
    public Long getQuoteDate() {
        return quoteDate;
    }

    public static final String QUOTE_DATE = "quoteDate";

    @RFAType(type=MarketPriceConstants.QUOTE_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setQuoteDate(Long quoteDate) {

        Long oldValue = this.quoteDate;

        this.quoteDate = quoteDate;

        firePropertyChange(
            MarketPrice.QUOTE_DATE,
            oldValue,
            quoteDate
        );
    }

    @UsingKey(type=VWAP)
    public BigDecimal getVolumeWeightedAveragePrice() {
        return volumeWeightedAveragePrice;
    }

    public static final String VOLUME_WEIGHTED_AVERAGE_PRICE = "volumeWeightedAveragePrice";

    @RFAType(type=VWAP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeWeightedAveragePrice(
        BigDecimal volumeWeightedAveragePrice) {

        BigDecimal oldValue = this.volumeWeightedAveragePrice;

        this.volumeWeightedAveragePrice = volumeWeightedAveragePrice;

        firePropertyChange(
            MarketPrice.VOLUME_WEIGHTED_AVERAGE_PRICE,
            oldValue,
            volumeWeightedAveragePrice
        );
    }

//    @UsingKey(type=PROV_SYMB)
//    public String getProviderSymbol() {
//        return providerSymbol;
//    }
//
//    @RFAType(type=PROV_SYMB)
//    @Adapt(using=OMMDataBufferAdapter.class)
//    public void setProviderSymbol(String providerSymbol) {
//        this.providerSymbol = providerSymbol;
//    }

    @UsingKey(type=BID_ASK_DT)
    public Long getBidAskDate() {
        return bidAskDate;
    }

    public static final String BID_ASK_DATE = "bidAskDate";

    @RFAType(type=MarketPrice.BID_ASK_DT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setBidAskDate(Long bidAskDate) {

        Long oldValue = this.bidAskDate;

        this.bidAskDate = bidAskDate;

        firePropertyChange(
            MarketPrice.BID_ASK_DATE,
            oldValue,
            bidAskDate
        );
    }

    @UsingKey(type=ISIN_CODE)
    public String getIsinCode() {
        return isinCode;
    }

    public static final String ISIN_CODE = "isinCode";

    @RFAType(type=MarketPriceConstants.ISIN_CODE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setIsinCode(String isinCode) {

        String oldValue = this.isinCode;

        this.isinCode = isinCode;

        firePropertyChange(
            MarketPrice.ISIN_CODE,
            oldValue,
            isinCode
        );
    }

//    @UsingKey(type=MNEMONIC)
//    public String getExchangeId() {
//        return exchangeId;
//    }
//
//    @RFAType(type=MNEMONIC)
//    @Adapt(using=OMMDataBufferAdapter.class)
//    public void setExchangeId(String exchangeId) {
//        this.exchangeId = exchangeId;
//    }

    @UsingKey(type=RTR_OPN_PR)
    public BigDecimal getRtrsOpeningPrice() {
        return rtrsOpeningPrice;
    }

    public static final String RTRS_OPENING_PRICE = "rtrsOpeningPrice";

    @RFAType(type=RTR_OPN_PR)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRtrsOpeningPrice(BigDecimal rtrsOpeningPrice) {

        BigDecimal oldValue = this.rtrsOpeningPrice;

        this.rtrsOpeningPrice = rtrsOpeningPrice;

        firePropertyChange(
            MarketPrice.RTRS_OPENING_PRICE,
            oldValue,
            rtrsOpeningPrice
        );
    }

    @UsingKey(type=SEDOL)
    public String getSedolCode() {
        return sedolCode;
    }

    public static final String SEDOL_CODE = "sedolCode";

    @RFAType(type=MarketPriceConstants.SEDOL)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setSedolCode(String sedolCode) {

        String oldValue = this.sedolCode;

        this.sedolCode = sedolCode;

        firePropertyChange(
            MarketPrice.SEDOL_CODE,
            oldValue,
            sedolCode
        );
    }

    @UsingKey(type=MKT_SEGMNT)
    public String getMarketSegment() {
        return marketSegment;
    }

    public static final String MARKET_SEGMENT = "marketSegment";

    @RFAType(type=MarketPriceConstants.MKT_SEGMNT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setMarketSegment(String marketSegment) {

        String oldValue = this.marketSegment;

        this.marketSegment = marketSegment;

        firePropertyChange(
            MarketPrice.MARKET_SEGMENT,
            oldValue,
            marketSegment
        );
    }

    @UsingKey(type=TRDTIM_MS)
    public Long getRegularTradesTimeMillis() {
        return regularTradesTimeMillis;
    }

    public static final String REGULAR_TRADE_TIME_MILLIS = "regularTradesTimeMillis";

    @RFAType(type=MarketPriceConstants.TRDTIM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRegularTradesTimeMillis(Long regularTradesTimeMillis) {

        Long oldValue = this.regularTradesTimeMillis;

        this.regularTradesTimeMillis = regularTradesTimeMillis;

        firePropertyChange(
            MarketPrice.REGULAR_TRADE_TIME_MILLIS,
            oldValue,
            regularTradesTimeMillis
        );
    }

    @UsingKey(type=SALTIM_MS)
    public Long getAllTradesTimeMillis() {
        return allTradesTimeMillis;
    }

    public static final String ALL_TRADES_TIME_MILLIS = "allTradesTimeMillis";

    @RFAType(type=MarketPriceConstants.SALTIM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAllTradesTimeMillis(Long allTradesTimeMillis) {

        Long oldValue = this.allTradesTimeMillis;

        this.allTradesTimeMillis = allTradesTimeMillis;

        firePropertyChange(
            MarketPrice.ALL_TRADES_TIME_MILLIS,
            oldValue,
            allTradesTimeMillis
        );
    }

    @UsingKey(type=QUOTIM_MS)
    public Long getQuoteTimeMillis() {
        return quoteTimeMillis;
    }

    public static final String QUOTE_TIME_MILLIS = "quoteTimeMillis";

    @RFAType(type=MarketPriceConstants.QUOTIM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setQuoteTimeMillis(Long quoteTimeMillis) {

        Long oldValue = this.quoteTimeMillis;

        this.quoteTimeMillis = quoteTimeMillis;

        firePropertyChange(
            MarketPrice.QUOTE_TIME_MILLIS,
            oldValue,
            quoteTimeMillis
        );
    }

    @UsingKey(type=TIMCOR_MS)
    public BigInteger getCorrectionTimeMillis() {
        return correctionTimeMillis;
    }

    public static final String CORRECTION_TIME_MILLIS = "correctionTimeMillis";

    @RFAType(type=TIMCOR_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCorrectionTimeMillis(BigInteger correctionTimeMillis) {

        BigInteger oldValue = this.correctionTimeMillis;

        this.correctionTimeMillis = correctionTimeMillis;

        firePropertyChange(CORRECTION_TIME_MILLIS, oldValue, correctionTimeMillis);
    }

    @UsingKey(type=FIN_STATUS)
    public String getFinancialStatusIndicator() {
        return financialStatusIndicator;
    }

    public static final String FINANCIAL_STATUS_INDICATOR = "financialStatusIndicator";

    @RFAType(type=FIN_STATUS)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setFinancialStatusIndicator(String financialStatusIndicator) {

        String oldValue = this.financialStatusIndicator;

        this.financialStatusIndicator = financialStatusIndicator;

        firePropertyChange(FINANCIAL_STATUS_INDICATOR, oldValue, financialStatusIndicator);
    }

    @UsingKey(type=LS_SUBIND)
    public String getLastTradeSubMarketIndicator() {
        return lastTradeSubMarketIndicator;
    }

    public static final String LAST_TRADE_SUB_MARKET_INDICATOR = "lastTradeSubMarketIndicator";

    @RFAType(type=LS_SUBIND)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setLastTradeSubMarketIndicator(
        String lastTradeSubMarketIndicator) {

        String oldValue = this.lastTradeSubMarketIndicator;

        this.lastTradeSubMarketIndicator = lastTradeSubMarketIndicator;

        firePropertyChange(LAST_TRADE_SUB_MARKET_INDICATOR, oldValue, lastTradeSubMarketIndicator);
    }

    @UsingKey(type=IRG_SUBIND)
    public String getIrgPriceSubmarketIndicator() {
        return irgPriceSubmarketIndicator;
    }

    public static final String IRG_PRICE_SUBMARKET_INDICATOR = "irgPriceSubmarketIndicator";

    @RFAType(type=IRG_SUBIND)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setIrgPriceSubmarketIndicator(
        String irgPriceSubmarketIndicator) {

        String oldValue = this.irgPriceSubmarketIndicator;

        this.irgPriceSubmarketIndicator = irgPriceSubmarketIndicator;

        firePropertyChange(IRG_PRICE_SUBMARKET_INDICATOR, oldValue, irgPriceSubmarketIndicator);
    }

    @UsingKey(type=ACVOL_SC)
    public String getVolumeScaling() {
        return volumeScaling;
    }

    public static final String VOLUME_SCALING = "volumeScaling";

    @RFAType(type=ACVOL_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setVolumeScaling(String volumeScaling) {

        String oldValue = volumeScaling;

        this.volumeScaling = volumeScaling;

        firePropertyChange(VOLUME_SCALING, oldValue, volumeScaling);
    }

    @UsingKey(type=EXCHCODE)
    public String getExchangeCode() {
        return exchangeCode;
    }

    public static final String EXCHANGE_CODE = "exchangeCode";

    @RFAType(type=MarketPriceConstants.EXCHCODE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setExchangeCode(String exchangeCode) {

        String oldValue = this.exchangeCode;

        this.exchangeCode = exchangeCode;

        firePropertyChange(MarketPrice.EXCHANGE_CODE, oldValue, exchangeCode);
    }

    @UsingKey(type=ODD_ASK)
    public BigDecimal getOddBestAsk() {
        return oddBestAsk;
    }

    public static final String ODD_BEST_ASK = "oddBestAsk";

    @RFAType(type=ODD_ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestAsk(BigDecimal oddBestAsk) {

        BigDecimal oldValue = this.oddBestAsk;

        this.oddBestAsk = oddBestAsk;

        firePropertyChange(ODD_BEST_ASK, oldValue, oddBestAsk);
    }

    @UsingKey(type=ODD_ASKSIZ)
    public BigInteger getOddBestAskSize() {
        return oddBestAskSize;
    }

    public static final String ODD_BEST_ASK_SIZE = "oddBestAskSize";

    @RFAType(type=ODD_ASKSIZ)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestAskSize(BigInteger oddBestAskSize) {

        BigInteger oldValue = this.oddBestAskSize;

        this.oddBestAskSize = oddBestAskSize;

        firePropertyChange(ODD_BEST_ASK_SIZE, oldValue, oddBestAskSize);
    }

    @UsingKey(type=ODD_BID)
    public BigDecimal getOddBestBid() {
        return oddBestBid;
    }

    public static final String ODD_BEST_BID = "oddBestBid";

    @RFAType(type=ODD_BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestBid(BigDecimal oddBestBid) {

        BigDecimal oldValue = this.oddBestBid;

        this.oddBestBid = oddBestBid;

        firePropertyChange(ODD_BEST_BID, oldValue, oddBestBid);
    }

    @UsingKey(type=ODD_BIDSIZ)
    public BigInteger getOddBestBidSize() {
        return oddBestBidSize;
    }

    public static final String ODD_BEST_BID_SIZE = "oddBestBidSize";

    @RFAType(type=ODD_BIDSIZ)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestBidSize(BigInteger oddBestBidSize) {

        BigInteger oldValue = this.oddBestBidSize;

        this.oddBestBidSize = oddBestBidSize;

        firePropertyChange(ODD_BEST_BID_SIZE, oldValue, oddBestBidSize);
    }

    @UsingKey(type=ROUND_VOL)
    public BigInteger getRoundVolume() {
        return roundVolume;
    }

    public static final String ROUND_VOLUME = "roundVolume";

    @RFAType(type=ROUND_VOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRoundVolume(BigInteger roundVolume) {

        BigInteger oldValue = this.roundVolume;

        this.roundVolume = roundVolume;

        firePropertyChange(ROUND_VOLUME, oldValue, roundVolume);
    }

    @UsingKey(type=ORGID)
    public BigInteger getOrganizationId() {
        return organizationId;
    }

    public static final String ORGANIZATION_ID = "organizationId";

    @RFAType(type=ORGID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOrganizationId(BigInteger organizationId) {

        BigInteger oldValue = this.organizationId;

        this.organizationId = organizationId;

        firePropertyChange(ORGANIZATION_ID, oldValue, organizationId);
    }

    @UsingKey(type=PR_FREQ)
    public String getPriceUpdateFrequency() {
        return priceUpdateFrequency;
    }

    public static final String PRICE_UPDATE_FREQUENCY = "priceUpdateFrequency";

    @RFAType(type=PR_FREQ)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceUpdateFrequency(String priceUpdateFrequency) {

        String oldValue = this.priceUpdateFrequency;

        this.priceUpdateFrequency = priceUpdateFrequency;

        firePropertyChange(PRICE_UPDATE_FREQUENCY, oldValue, priceUpdateFrequency);
    }

    @UsingKey(type=RCS_AS_CLA)
    public String getRcsAssetClassification() {
        return rcsAssetClassification;
    }

    public static final String RCS_ASSET_CLASSIFICATION = "rcsAssetClassification";

    @RFAType(type=RCS_AS_CLA)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRcsAssetClassification(String rcsAssetClassification) {

        String oldValue = this.rcsAssetClassification;

        this.rcsAssetClassification = rcsAssetClassification;

        firePropertyChange(RCS_ASSET_CLASSIFICATION, oldValue, rcsAssetClassification);
    }

    @UsingKey(type=UNDR_INDEX)
    public String getUnderlyingIndex() {
        return underlyingIndex;
    }

    public static final String UNDERLYING_INDEX = "underlyingIndex";

    @RFAType(type=UNDR_INDEX)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setUnderlyingIndex(String underlyingIndex) {

        String oldValue = this.underlyingIndex;

        this.underlyingIndex = underlyingIndex;

        firePropertyChange(UNDERLYING_INDEX, oldValue, underlyingIndex);
    }

    @UsingKey(type=FUTURES)
    public String getFuturesChainRic() {
        return futuresChainRic;
    }

    public static final String FUTURES_CHAIN_RIC = "futuresChainRic";

    @RFAType(type=FUTURES)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setFuturesChainRic(String futuresChainRic) {

        String oldValue = this.futuresChainRic;

        this.futuresChainRic = futuresChainRic;

        firePropertyChange(FUTURES_CHAIN_RIC, oldValue, futuresChainRic);
    }

    @UsingKey(type=OPTIONS)
    public String getOptionsChainRic() {
        return optionsChainRic;
    }

    public static final String OPTIONS_CHAIN_RIC = "optionsChainRic";

    @RFAType(type=OPTIONS)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOptionsChainRic(String optionsChainRic) {

        String oldValue = this.optionsChainRic;

        this.optionsChainRic = optionsChainRic;

        firePropertyChange(OPTIONS_CHAIN_RIC, oldValue, optionsChainRic);
    }

    @UsingKey(type=STRIKES)
    public String getStrikesCoverage() {
        return strikesCoverage;
    }

    public static final String STRIKES_COVERAGE = "strikesCoverage";

    @RFAType(type=STRIKES)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setStrikesCoverage(String strikesCoverage) {

        String oldValue = this.strikesCoverage;

        this.strikesCoverage = strikesCoverage;

        firePropertyChange(STRIKES_COVERAGE, oldValue, strikesCoverage);
    }

    @UsingKey(type=NEWSTM_MS)
    public BigInteger getNewsTimeMillis() {
        return newsTimeMillis;
    }

    public static final String NEWS_TIME_MILLIS = "newsTimeMillis";

    @RFAType(type=NEWSTM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setNewsTimeMillis(BigInteger newsTimeMillis) {

        BigInteger oldValue = this.newsTimeMillis;

        this.newsTimeMillis = newsTimeMillis;

        firePropertyChange(NEWS_TIME_MILLIS, oldValue, newsTimeMillis);
    }

    @UsingKey(type=TRD_THRU_X)
    public String getTradeThroughExemptFlags() {
        return tradeThroughExemptFlags;
    }

    public static final String TRADE_THROUGH_EXEMPT_FLAGS = "tradeThroughExemptFlags";

    @RFAType(type=TRD_THRU_X)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setTradeThroughExemptFlags(String tradeThroughExemptFlags) {

        String oldValue = this.tradeThroughExemptFlags;

        this.tradeThroughExemptFlags = tradeThroughExemptFlags;

        firePropertyChange(TRADE_THROUGH_EXEMPT_FLAGS, oldValue, tradeThroughExemptFlags);
    }

    @UsingKey(type=FIN_ST_IND)
    public String getCompanyComplianceStatus() {
        return companyComplianceStatus;
    }

    public static final String COMPANY_COMPLIANCE_STATUS = "companyComplianceStatus";

    @RFAType(type=FIN_ST_IND)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setCompanyComplianceStatus(String companyComplianceStatus) {

        String oldValue = this.companyComplianceStatus;

        this.companyComplianceStatus = companyComplianceStatus;

        firePropertyChange(COMPANY_COMPLIANCE_STATUS, oldValue, companyComplianceStatus);
    }

    @UsingKey(type=IRG_SMKTID)
    public String getIrgSubMarketCenterId() {
        return irgSubMarketCenterId;
    }

    public static final String IRG_SUB_MARKET_CENTER_ID = "irgSubMarketCenterId";

    @RFAType(type=IRG_SMKTID)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setIrgSubMarketCenterId(String irgSubMarketCenterId) {

        String oldValue = this.irgSubMarketCenterId;

        this.irgSubMarketCenterId = irgSubMarketCenterId;

        firePropertyChange(IRG_SUB_MARKET_CENTER_ID, oldValue, irgSubMarketCenterId);
    }

    @UsingKey(type=SUB_MKT_ID)
    public String getSubMarketCenterId() {
        return subMarketCenterId;
    }

    public static final String SUB_MARKET_CENTER_ID = "subMarketCenterId";

    @RFAType(type=SUB_MKT_ID)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setSubMarketCenterId(String subMarketCenterId) {

        String oldValue = this.subMarketCenterId;

        this.subMarketCenterId = subMarketCenterId;

        firePropertyChange(SUB_MARKET_CENTER_ID, oldValue, subMarketCenterId);
    }

    @UsingKey(type=ACT_DOM_EX)
    public String getActiveDomesticExchangeIds() {
        return activeDomesticExchangeIds;
    }

    public static final String ACTIVE_DOMESTIC_EXCHANGE_IDS = "activeDomesticExchangeIds";

    @RFAType(type=ACT_DOM_EX)
    @Adapt(using=OMMEnumAdapter.class)
    public void setActiveDomesticExchangeIds(String activeDomesticExchangeIds) {

        String oldValue = this.activeDomesticExchangeIds;

        this.activeDomesticExchangeIds = activeDomesticExchangeIds;

        firePropertyChange(ACTIVE_DOMESTIC_EXCHANGE_IDS, oldValue, activeDomesticExchangeIds);
    }

    @UsingKey(type=ACT_OTH_EX)
    public String getActiveOtherExchangeIds() {
        return activeOtherExchangeIds;
    }

    public static final String ACTIVE_OTHER_EXCHANGE_IDS = "activeOtherExchangeIds";

    @RFAType(type=ACT_OTH_EX)
    @Adapt(using=OMMEnumAdapter.class)
    public void setActiveOtherExchangeIds(String activeOtherExchangeIds) {

        String oldValue = this.activeOtherExchangeIds;

        this.activeOtherExchangeIds = activeOtherExchangeIds;

        firePropertyChange(ACTIVE_OTHER_EXCHANGE_IDS, oldValue, activeOtherExchangeIds);
    }

    @UsingKey(type=TRD_QUAL_2)
    public String getTradePriceQualifier2() {
        return tradePriceQualifier2;
    }

    public static final String TRADE_PRICE_QUALIFIER2 = "tradePriceQualifier2";

    @RFAType(type=TRD_QUAL_2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradePriceQualifier2(String tradePriceQualifier2) {

        String oldValue = this.tradePriceQualifier2;

        this.tradePriceQualifier2 = tradePriceQualifier2;

        firePropertyChange(TRADE_PRICE_QUALIFIER2, oldValue, tradePriceQualifier2);
    }

    @UsingKey(type=CP_EFF_DAT)
    public Long getLatestCapitalChangeEffectiveDate() {
        return latestCapitalChangeEffectiveDate;
    }

    public static final String LATEST_CAPITAL_CHANGE_EFFECTIVE_DATE = "latestCapitalChangeEffectiveDate";

    @RFAType(type=CP_EFF_DAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLatestCapitalChangeEffectiveDate(
        Long latestCapitalChangeEffectiveDate) {

        Long oldValue = this.latestCapitalChangeEffectiveDate;

        this.latestCapitalChangeEffectiveDate =
            latestCapitalChangeEffectiveDate;

        firePropertyChange(LATEST_CAPITAL_CHANGE_EFFECTIVE_DATE, oldValue, latestCapitalChangeEffectiveDate);
    }

    @UsingKey(type=ROW64_1)
    public String getRow64_1() {
        return row64_1;
    }

    @RFAType(type=ROW64_1)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_1(String row64_1) {

        String oldValue = this.row64_1;

        this.row64_1 = row64_1;

        firePropertyChange(ROW64_1, oldValue, row64_1);
    }

    @UsingKey(type=ROW64_2)
    public String getRow64_2() {
        return row64_2;
    }

    @RFAType(type=ROW64_2)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_2(String row64_2) {

        String oldValue = this.row64_2;

        this.row64_2 = row64_2;

        firePropertyChange(ROW64_2, oldValue, row64_2);
    }

    @UsingKey(type=ROW64_3)
    public String getRow64_3() {
        return row64_3;
    }

    @RFAType(type=ROW64_3)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_3(String row64_3) {

        String oldValue = this.row64_3;

        this.row64_3 = row64_3;

        firePropertyChange(ROW64_3, oldValue, row64_3);
    }

    @UsingKey(type=ROW64_4)
    public String getRow64_4() {
        return row64_4;
    }

    @RFAType(type=ROW64_4)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_4(String row64_4) {

        String oldValue = this.row64_4;

        this.row64_4 = row64_4;

        firePropertyChange(ROW64_4, oldValue, row64_4);
    }

    @UsingKey(type=ROW64_5)
    public String getRow64_5() {
        return row64_5;
    }

    @RFAType(type=ROW64_5)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_5(String row64_5) {

        String oldValue = this.row64_5;

        this.row64_5 = row64_5;

        firePropertyChange(ROW64_5, oldValue, row64_5);
    }

    @UsingKey(type=ROW64_6)
    public String getRow64_6() {
        return row64_6;
    }

    @RFAType(type=ROW64_6)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_6(String row64_6) {

        String oldValue = this.row64_6;

        this.row64_6 = row64_6;

        firePropertyChange(ROW64_6, oldValue, row64_6);
    }

    @UsingKey(type=ROW64_7)
    public String getRow64_7() {
        return row64_7;
    }

    public static final String ROW_64_7 = MarketPriceConstants.ROW64_7;

    @RFAType(type=MarketPriceConstants.ROW64_7)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_7(String row64_7) {

        String oldValue = this.row64_7;

        this.row64_7 = row64_7;

        firePropertyChange(MarketPrice.ROW64_7, oldValue, row64_7);
    }

    @UsingKey(type=ROW64_8)
    public String getRow64_8() {
        return row64_8;
    }

    public static final String ROW64_8 = MarketPriceConstants.ROW64_8;

    @RFAType(type=MarketPriceConstants.ROW64_8)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_8(String row64_8) {

        String oldValue = this.row64_8;

        this.row64_8 = row64_8;

        firePropertyChange(MarketPrice.ROW64_8, oldValue, row64_8);
    }

    @UsingKey(type=ROW64_9)
    public String getRow64_9() {
        return row64_9;
    }

    public static final String ROW64_9 = MarketPriceConstants.ROW64_9;

    @RFAType(type=MarketPriceConstants.ROW64_9)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_9(String row64_9) {

        String oldValue = this.row64_9;

        this.row64_9 = row64_9;

        firePropertyChange(MarketPrice.ROW64_9, oldValue, row64_9);
    }

    @UsingKey(type=ROW64_10)
    public String getRow64_10() {
        return row64_10;
    }

    public static final String ROW64_10 = MarketPriceConstants.ROW64_10;

    @RFAType(type=MarketPriceConstants.ROW64_10)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_10(String row64_10) {

        String oldValue = this.row64_10;

        this.row64_10 = row64_10;

        firePropertyChange(MarketPrice.ROW64_10, oldValue, row64_10);
    }

    @UsingKey(type=ROW64_11)
    public String getRow64_11() {
        return row64_11;
    }

    public static final String ROW64_11 = MarketPriceConstants.ROW64_11;

    @RFAType(type=MarketPriceConstants.ROW64_11)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_11(String row64_11) {

        String oldValue = this.row64_11;

        this.row64_11 = row64_11;

        firePropertyChange(MarketPrice.ROW64_11, oldValue, row64_11);
    }

    @UsingKey(type=ROW64_12)
    public String getRow64_12() {
        return row64_12;
    }

    public static final String ROW64_12 = MarketPriceConstants.ROW64_12;

    @RFAType(type=MarketPriceConstants.ROW64_12)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_12(String row64_12) {

        String oldValue = this.row64_12;

        this.row64_12 = row64_12;

        firePropertyChange(MarketPrice.ROW64_12, oldValue, row64_12);
    }

    @UsingKey(type=ROW64_13)
    public String getRow64_13() {
        return row64_13;
    }

    public static final String ROW64_13 = MarketPriceConstants.ROW64_13;

    @RFAType(type=ROW64_13)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_13(String row64_13) {

        String oldValue = this.row64_13;

        this.row64_13 = row64_13;

        firePropertyChange(MarketPrice.ROW64_13, oldValue, row64_13);
    }

    @UsingKey(type=ROW64_14)
    public String getRow64_14() {
        return row64_14;
    }

    public static final String ROW64_14 = MarketPriceConstants.ROW64_14;

    @RFAType(type=MarketPriceConstants.ROW64_14)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_14(String row64_14) {

        String oldValue = this.row64_14;

        this.row64_14 = row64_14;

        firePropertyChange(MarketPrice.ROW64_14, oldValue, row64_14);
    }

    /**
     * Getter method for the {@link MarketPriceConstants#PUT_CALL}.
     */
    @UsingKey(type=PUT_CALL)
    public String getPutCall() {
        return PUT_CALL_NOT_ALLOCATED.equals(putCall) ? NOT_ALLOCATED : putCall;
    }

    /**
     * Setter method for the {@link MarketPriceConstants#PUT_CALL}.
     */
    public static final String PUT_CALL = "putCall";

    @RFAType(type=MarketPriceConstants.PUT_CALL)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPutCall(String putCall) {

        String oldValue = this.putCall;

        this.putCall = putCall;

        firePropertyChange(MarketPrice.PUT_CALL, oldValue, putCall);
    }

    /**
     * Getter method for the {@link MarketPriceConstants#IMP_VOLTA}.
     */
    @UsingKey(type=IMP_VOLTA)
    public BigDecimal getImpliedVolatilitytOfAskPrice() {
        return impliedVolatilitytOfAskPrice;
    }

    public static final String IMPLIED_VOLATILITY_OF_ASK_PRICE = "impliedVolatilitytOfAskPrice";

    /**
     * Setter method for the {@link MarketPriceConstants#IMP_VOLTA}.
     */
    @RFAType(type=MarketPriceConstants.IMP_VOLTA)
    @Adapt(using=OMMNumericAdapter.class)
    public void setImpliedVolatilitytOfAskPrice(
        BigDecimal impliedVolatilitytOfAskPrice
    ) {
        BigDecimal oldValue = this.impliedVolatilitytOfAskPrice;

        this.impliedVolatilitytOfAskPrice = impliedVolatilitytOfAskPrice;

        firePropertyChange(MarketPrice.IMPLIED_VOLATILITY_OF_ASK_PRICE, oldValue, impliedVolatilitytOfAskPrice);
    }

    /**
     * Setter method for the {@link MarketPriceConstants#IMP_VOLTB}.
     */
    @UsingKey(type=IMP_VOLTB)
    public BigDecimal getImpliedVolatilitytOfBidPrice() {
        return impliedVolatilitytOfBidPrice;
    }

    public static final String IMPLIED_VOLATILITY_OF_BID_PRICE = "impliedVolatilitytOfBidPrice";

    /**
     * Getter method for the {@link MarketPriceConstants#IMP_VOLTB}.
     */
    @RFAType(type=MarketPriceConstants.IMP_VOLTB)
    @Adapt(using=OMMNumericAdapter.class)
    public void setImpliedVolatilitytOfBidPrice(
        BigDecimal impliedVolatilitytOfBidPrice
    ) {

        BigDecimal oldValue = this.impliedVolatilitytOfBidPrice;

        this.impliedVolatilitytOfBidPrice = impliedVolatilitytOfBidPrice;

        firePropertyChange(MarketPrice.IMPLIED_VOLATILITY_OF_BID_PRICE, oldValue, impliedVolatilitytOfBidPrice);
    }

    /**
     * Getter method for the {@link MarketPriceConstants#OPINT_1}.
     */
    @UsingKey(type=OPINT_1)
    public BigInteger getOpenInterest() {
        return openInterest;
    }

    public static final String OPEN_INTEREST = "openInterest";

    /**
     * Setter method for the {@link MarketPriceConstants#OPINT_1}.
     */
    @RFAType(type=MarketPriceConstants.OPINT_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenInterest(BigInteger openInterest) {

        BigInteger oldValue = this.openInterest;

        this.openInterest = openInterest;

        firePropertyChange(MarketPrice.OPEN_INTEREST, oldValue, openInterest);
    }

    /**
     * Getter method for the {@link MarketPriceConstants#OPINTNC}.
     */
    @UsingKey(type=OPINTNC)
    public BigInteger getOpenInterestNetChange() {
        return openInterestNetChange;
    }

    public static final String OPEN_INTEREST_NET_CHANGE = "openInterestNetChange";

    /**
     * Setter method for the {@link MarketPriceConstants#OPINTNC}.
     */
    @RFAType(type=OPINTNC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenInterestNetChange(BigInteger openInterestNetChange) {

        BigInteger oldValue = this.openInterestNetChange;

        this.openInterestNetChange = openInterestNetChange;

        firePropertyChange(OPEN_INTEREST_NET_CHANGE, oldValue, openInterestNetChange);
    }

    /**
     * Getter method for the {@link MarketPriceConstants#STRIKE_PRC}.
     */
    @UsingKey(type=STRIKE_PRC)
    public BigDecimal getStrikePrice() {
        return strikePrice;
    }

    /**
     * Getter method for the {@link MarketPriceConstants#STRIKE_PRC}.
     */
    public static final String STRIKE_PRICE = "strikePrice";

    @RFAType(type=STRIKE_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setStrikePrice(BigDecimal strikePrice) {

        BigDecimal oldValue = this.strikePrice;

        this.strikePrice = strikePrice;

        firePropertyChange(STRIKE_PRICE, oldValue, strikePrice);
    }

    @UsingKey(type=CONTR_MNTH)
    public String getContractMonth() {
        return contractMonth;
    }

    public static final String CONTRACT_MONTH = "contractMonth";

    @RFAType(type=CONTR_MNTH)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setContractMonth(String contractMonth) {

        String oldValue = this.contractMonth;

        this.contractMonth = contractMonth;

        firePropertyChange(CONTRACT_MONTH, oldValue, contractMonth);
    }

    @UsingKey(type=LOTSZUNITS)
    public String getLotSizeUnits() {
        return lotSizeUnits;
    }

    public static final String LOT_SIZE_UNITS = "lotSizeUnits";

    @RFAType(type=LOTSZUNITS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setLotSizeUnits(String lotSizeUnits) {

        String oldValue = this.lotSizeUnits;

        this.lotSizeUnits = lotSizeUnits;

        firePropertyChange(LOT_SIZE_UNITS, oldValue, lotSizeUnits);
    }

    @UsingKey(type=OPEN_ASK)
    public BigDecimal getOpenAskPrice() {
        return openAskPrice;
    }

    public static final String OPEN_ASK_PRICE = "openAskPrice";

    @RFAType(type=OPEN_ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenAskPrice(BigDecimal openAskPrice) {

        BigDecimal oldValue = this.openAskPrice;

        this.openAskPrice = openAskPrice;

        firePropertyChange(OPEN_ASK_PRICE, oldValue, openAskPrice);
    }

    @UsingKey(type=EXPIR_DATE)
    public Long getExpiryDate() {
        return expiryDate;
    }

    public static final String EXPIRY_DATE = "expiryDate";

    @RFAType(type=EXPIR_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExpiryDate(Long expiryDate) {

        Long oldValue = this.expiryDate;

        this.expiryDate = expiryDate;

        firePropertyChange(EXPIRY_DATE, oldValue, expiryDate);
    }

    @UsingKey(type=SETTLE)
    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public static final String SETTLEMENT_PRICE = "settlementPrice";

    @RFAType(type=SETTLE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSettlementPrice(BigDecimal settlementPrice) {

        BigDecimal oldValue = this.settlementPrice;

        this.settlementPrice = settlementPrice;

        firePropertyChange(SETTLEMENT_PRICE, oldValue, settlementPrice);
    }

    @UsingKey(type=SETTLEDATE)
    public Long getSettleDate() {
        return settleDate;
    }

    public static final String SETTLE_DATE = "settleDate";

    @RFAType(type=SETTLEDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setSettleDate(Long settleDate) {

        Long oldValue = this.settleDate;

        this.settleDate = settleDate;

        firePropertyChange(SETTLE_DATE, oldValue, settleDate);
    }
}
