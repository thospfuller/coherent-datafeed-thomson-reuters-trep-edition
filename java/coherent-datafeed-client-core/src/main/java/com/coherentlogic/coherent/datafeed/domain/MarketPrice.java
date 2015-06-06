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
 * @author <a href="support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=MARKET_PRICE)
@XStreamAlias(MARKET_PRICE)
public class MarketPrice
    extends RFABean
    implements MarketPriceConstants {

    private static final long serialVersionUID = -8330990635265356088L;

    /**
     * Product permissions information.
     *
     * PROD_PERM: UINT
     */
    @XStreamAlias(PROD_PERM)
    private BigInteger permission = null;

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

    @XStreamAlias(CURRENCY)
    private String currency = null;

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

    @XStreamAlias(RECORDTYPE)
    private BigInteger recordType = null;

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

    @XStreamAlias(RDN_EXCHD2)
    private String exchangeId2 = null;

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

    @XStreamAlias(PROV_SYMB)
    private String providerSymbol = null;

    @XStreamAlias(BID_ASK_DT)
    private Long bidAskDate = null;

    /**
     * International Security Identification Number code (ISIN).
     */
    @XStreamAlias(ISIN_CODE)
    private String isinCode = null;

    @XStreamAlias(MNEMONIC)
    private String exchangeId = null;

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

    @UsingKey(type=PROD_PERM)
    public BigInteger getPermission() {
        return permission;
    }

    /**
     *
     */
    @RFAType(type=PROD_PERM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPermission(BigInteger permission) {
        this.permission = permission;
    }

    @UsingKey(type=RDN_EXCHID)
    public String getIdnExchangeId() {
        return idnExchangeId;
    }

    /**
     * @todo Test this.
     */
    @RFAType(type=RDN_EXCHID)
    @Adapt(using=OMMEnumAdapter.class)
    public void setIdnExchangeId(String idnExchangeId) {
        this.idnExchangeId = idnExchangeId;
    }

    @UsingKey(type=DSPLY_NAME)
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @todo Test this.
     */
    @RFAType(type=DSPLY_NAME)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @UsingKey(type=BID)
    public BigDecimal getBid() {
        return bid;
    }

    @RFAType(type=BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    @UsingKey(type=BID_1)
    public BigDecimal getBid1() {
        return bid1;
    }

    @RFAType(type=BID_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid1(BigDecimal bid1) {
        this.bid1 = bid1;
    }

    @UsingKey(type=BID_2)
    public BigDecimal getBid2() {
        return bid2;
    }

    @RFAType(type=BID_2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid2(BigDecimal bid2) {
        this.bid2 = bid2;
    }

    @UsingKey(type=ASK)
    public BigDecimal getAsk() {
        return ask;
    }

    /**
     * REAL64
     *
     * @param ask
     */
    @RFAType(type=ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    @UsingKey(type=ASK_1)
    public BigDecimal getAsk1() {
        return ask1;
    }

    /**
     * REAL64
     *
     * @param ask1
     */
    @RFAType(type=ASK_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAsk1(BigDecimal ask1) {
        this.ask1 = ask1;
    }

    @UsingKey(type=ASK_2)
    public BigDecimal getAsk2() {
        return ask2;
    }

    /**
     * REAL64
     *
     * @param ask2
     */
    @RFAType(type=ASK_2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAsk2(BigDecimal ask2) {
        this.ask2 = ask2;
    }

    public BigInteger getBidSize() {
        return bidSize;
    }

    /**
     * REAL64
     *
     * @param bidSize
     */
    @RFAType(type=BIDSIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBidSize(BigInteger bidSize) {
        this.bidSize = bidSize;
    }

    public BigInteger getAskSize() {
        return askSize;
    }

    /**
     * REAL64
     *
     * @param askSize
     */
    @RFAType(type=ASKSIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAskSize(BigInteger askSize) {
        this.askSize = askSize;
    }

    public BigDecimal getLast() {
        return last;
    }

    /**
     * REAL64
     *
     * @param last
     */
    @RFAType(type=TRDPRC_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast(BigDecimal last) {
        this.last = last;
    }

    public BigDecimal getLast1() {
        return last1;
    }

    /**
     * REAL64
     *
     * @param last1
     */
    @RFAType(type=TRDPRC_2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast1(BigDecimal last1) {
        this.last1 = last1;
    }

    public BigDecimal getLast2() {
        return last2;
    }

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

    @RFAType(type=NETCHNG_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setNetChange(BigDecimal netChange) {
        this.netChange = netChange;
    }

    @UsingKey(type=HIGH_1)
    public BigDecimal getTodaysHigh() {
        return todaysHigh;
    }

    @RFAType(type=HIGH_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysHigh(BigDecimal todaysHigh) {
        this.todaysHigh = todaysHigh;
    }

    @UsingKey(type=LOW_1)
    public BigDecimal getTodaysLow() {
        return todaysLow;
    }

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

    @RFAType(type=PRCTCK_1)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTickArrow(Integer tickArrow) {
        this.tickArrow = tickArrow;
    }

    @UsingKey(type=CURRENCY)
    public String getCurrency() {
        return currency;
    }

    @RFAType(type=CURRENCY)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @UsingKey(type=TRADE_DATE)
    public Long getTradeDateMillis() {
        return tradeDateMillis;
    }

    @RFAType(type=TRADE_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTradeDateMillis(Long tradeDateMillis) {
        this.tradeDateMillis = tradeDateMillis;
    }

    @UsingKey(type=TRDTIM_1)
    public Long getTradeTimeMillis() {
        return tradeTimeMillis;
    }

    @RFAType(type=TRDTIM_1)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTradeTimeMillis(Long tradeTimeMillis) {
        this.tradeTimeMillis = tradeTimeMillis;
    }

    @UsingKey(type=OPEN_PRC)
    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    @RFAType(type=OPEN_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    @UsingKey(type=HST_CLOSE)
    public BigDecimal getHistoricClose() {
        return historicClose;
    }

    @RFAType(type=HST_CLOSE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setHistoricClose(BigDecimal historicClose) {
        this.historicClose = historicClose;
    }

    @UsingKey(type=NEWS)
    public String getNews() {
        return news;
    }

    @RFAType(type=NEWS)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setNews(String news) {
        this.news = news;
    }

    @UsingKey(type=NEWS_TIME)
    public Long getNewsTime() {
        return newsTime;
    }

    @RFAType(type=NEWS_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setNewsTime(Long newsTime) {
        this.newsTime = newsTime;
    }

    @UsingKey(type=ACVOL_1)
    public BigInteger getVolumeAccumulated() {
        return volumeAccumulated;
    }

    @RFAType(type=ACVOL_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeAccumulated(BigInteger volumeAccumulated) {
        this.volumeAccumulated = volumeAccumulated;
    }

    @UsingKey(type=EARNINGS)
    public BigDecimal getEarnings() {
        return earnings;
    }

    @RFAType(type=EARNINGS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setEarnings(BigDecimal earnings) {
        this.earnings = earnings;
    }

    @UsingKey(type=YIELD)
    public BigDecimal getYield() {
        return yield;
    }

    @RFAType(type=YIELD)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    @UsingKey(type=PERATIO)
    public BigDecimal getPriceToEarningsRatio() {
        return priceToEarningsRatio;
    }

    @RFAType(type=PERATIO)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceToEarningsRatio(BigDecimal priceToEarningsRatio) {
        this.priceToEarningsRatio = priceToEarningsRatio;
    }

    @UsingKey(type=DIVIDENDTP)
    public String getDividendType() {
        return dividendType;
    }

    @RFAType(type=DIVIDENDTP)
    @Adapt(using=OMMEnumAdapter.class)
    public void setDividendType(String dividendType) {
        this.dividendType = dividendType;
    }

    @UsingKey(type=DIVPAYDATE)
    public Long getDividendPayDate() {
        return dividendPayDate;
    }

    @RFAType(type=DIVPAYDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setDividendPayDate(Long dividendPayDate) {
        this.dividendPayDate = dividendPayDate;
    }

    @UsingKey(type=EXDIVDATE)
    public Long getExDividendDate() {
        return exDividendDate;
    }

    @RFAType(type=EXDIVDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExDividendDate(Long exDividendDate) {
        this.exDividendDate = exDividendDate;
    }

    @UsingKey(type=CTS_QUAL)
    public String getTradePriceQualifier() {
        return tradePriceQualifier;
    }

    @RFAType(type=CTS_QUAL)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradePriceQualifier(String tradePriceQualifier) {
        this.tradePriceQualifier = tradePriceQualifier;
    }

    @UsingKey(type=BLKCOUNT)
    public BigInteger getBlockCount() {
        return blockCount;
    }

    @RFAType(type=BLKCOUNT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockCount(BigInteger blockCount) {
        this.blockCount = blockCount;
    }

    @UsingKey(type=BLKVOLUM)
    public BigInteger getBlockVolume() {
        return blockVolume;
    }

    @RFAType(type=BLKVOLUM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockVolume(BigInteger blockVolume) {
        this.blockVolume = blockVolume;
    }

    @UsingKey(type=TRDXID_1)
    public String getTradeExchangeId() {
        return tradeExchangeId;
    }

    @RFAType(type=TRDXID_1)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradeExchangeId(String tradeExchangeId) {
        this.tradeExchangeId = tradeExchangeId;
    }

    @UsingKey(type=TRD_UNITS)
    public String getTradingUnits() {
        return tradingUnits;
    }

    @RFAType(type=TRD_UNITS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradingUnits(String tradingUnits) {
        this.tradingUnits = tradingUnits;
    }

    @UsingKey(type=LOT_SIZE)
    public BigInteger getLotSize() {
        return lotSize;
    }

    @RFAType(type=LOT_SIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLotSize(BigInteger lotSize) {
        this.lotSize = lotSize;
    }

    @UsingKey(type=PCTCHNG)
    public BigDecimal getPercentChange() {
        return percentChange;
    }

    @RFAType(type=PCTCHNG)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPercentChange(BigDecimal setPercentChange) {
        this.percentChange = setPercentChange;
    }

    @UsingKey(type=OPEN_BID)
    public BigDecimal getOpenBid() {
        return openBid;
    }

    @RFAType(type=OPEN_BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenBid(BigDecimal openBid) {
        this.openBid = openBid;
    }

    @UsingKey(type=DJTIME)
    public Long getLatestDowJonesNewsStoryTime() {
        return latestDowJonesNewsStoryTime;
    }

    @RFAType(type=DJTIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLatestDowJonesNewsStoryTime(Long time) {
        this.latestDowJonesNewsStoryTime = time;
    }

    @UsingKey(type=CLOSE_BID)
    public BigDecimal getCloseBid() {
        return closeBid;
    }

    @RFAType(type=CLOSE_BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCloseBid(BigDecimal closeBid) {
        this.closeBid = closeBid;
    }

    @UsingKey(type=CLOSE_ASK)
    public BigDecimal getCloseAsk() {
        return closeAsk;
    }

    @RFAType(type=CLOSE_ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCloseAsk(BigDecimal closeAsk) {
        this.closeAsk = closeAsk;
    }

    @UsingKey(type=DIVIDEND)
    public BigDecimal getDividend() {
        return dividend;
    }

    @RFAType(type=DIVIDEND)
    @Adapt(using=OMMNumericAdapter.class)
    public void setDividend(BigDecimal dividend) {
        this.dividend = dividend;
    }

    @UsingKey(type=NUM_MOVES)
    public BigInteger getTotalTradesToday() {
        return totalTradesToday;
    }

    @RFAType(type=NUM_MOVES)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTotalTradesToday(BigInteger totalTradesToday) {
        this.totalTradesToday = totalTradesToday;
    }

    @UsingKey(type=OFFCL_CODE)
    public String getOfficialCode() {
        return officialCode;
    }

    @RFAType(type=OFFCL_CODE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOfficialCode(String officialCode) {
        this.officialCode = officialCode;
    }

    @UsingKey(type=HSTCLSDATE)
    public Long getHistoricCloseDate() {
        return historicCloseDate;
    }

    @RFAType(type=HSTCLSDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setHistoricCloseDate(Long historicCloseDate) {
        this.historicCloseDate = historicCloseDate;
    }

    @UsingKey(type=YRHIGH)
    public BigDecimal getYearHigh() {
        return yearHigh;
    }

    @RFAType(type=YRHIGH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearHigh(BigDecimal yearHigh) {
        this.yearHigh = yearHigh;
    }

    @UsingKey(type=YRLOW)
    public BigDecimal getYearLow() {
        return yearLow;
    }

    @RFAType(type=YRLOW)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearLow(BigDecimal yearLow) {
        this.yearLow = yearLow;
    }

    @UsingKey(type=TURNOVER)
    public BigDecimal getTurnover() {
        return turnover;
    }

    @RFAType(type=TURNOVER)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    @UsingKey(type=BOND_TYPE)
    public String getBondType() {
        return bondType;
    }

    @RFAType(type=BOND_TYPE)
    @Adapt(using=OMMEnumAdapter.class)
    public void setBondType(String bondType) {
        this.bondType = bondType;
    }

    @UsingKey(type=BCKGRNDPAG)
    public String getBackgroundPage() {
        return backgroundPage;
    }

    @RFAType(type=BCKGRNDPAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setBackgroundPage(String backgroundPage) {
        this.backgroundPage = backgroundPage;
    }

    @UsingKey(type=YCHIGH_IND)
    public String getYearOrContractHighIndicator() {
        return yearOrContractHighIndicator;
    }

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

    @RFAType(type=BID_NET_CH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBidNetChange(BigDecimal bidNetChange) {
        this.bidNetChange = bidNetChange;
    }

    @UsingKey(type=BID_TICK_1)
    public String getBidTick1() {
        return bidTick1;
    }

    @RFAType(type=BID_TICK_1)
    @Adapt(using=OMMEnumAdapter.class)
    public void setBidTick1(String bidTick1) {
        this.bidTick1 = bidTick1;
    }

    @UsingKey(type=CUM_EX_MKR)
    public String getCumExMarker() {
        return cumExMarker;
    }

    @RFAType(type=CUM_EX_MKR)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCumExMarker(String cumExMarker) {
        this.cumExMarker = cumExMarker;
    }

    @UsingKey(type=PRC_QL_CD)
    public String getPriceCode() {
        return priceCode;
    }

    @RFAType(type=PRC_QL_CD)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
    }

    @UsingKey(type=NASDSTATUS)
    public String getNasdStatus() {
        return nasdStatus;
    }

    @RFAType(type=NASDSTATUS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setNasdStatus(String nasdStatus) {
        this.nasdStatus = nasdStatus;
    }

    @UsingKey(type=PRC_QL2)
    public String getPriceCode2() {
        return priceCode2;
    }

    @RFAType(type=PRC_QL2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceCode2(String priceCode2) {
        this.priceCode2 = priceCode2;
    }

    @UsingKey(type=TRDVOL_1)
    public BigInteger getTradeVolume() {
        return tradeVolume;
    }

    @RFAType(type=TRDVOL_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTradeVolume(BigInteger tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    @UsingKey(type=BID_HIGH_1)
    public BigDecimal getTodaysHighBid() {
        return todaysHighBid;
    }

    @RFAType(type=BID_HIGH_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysHighBid(BigDecimal todaysHighBid) {
        this.todaysHighBid = todaysHighBid;
    }

    @UsingKey(type=BID_LOW_1)
    public BigDecimal getTodaysLowBid() {
        return todaysLowBid;
    }

    @RFAType(type=BID_LOW_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysLowBid(BigDecimal todaysLowBid) {
        this.todaysLowBid = todaysLowBid;
    }

    @UsingKey(type=YRBIDHIGH)
    public BigDecimal getYearHighBid() {
        return yearHighBid;
    }

    @RFAType(type=YRBIDHIGH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearHighBid(BigDecimal yearHighBid) {
        this.yearHighBid = yearHighBid;
    }

    @UsingKey(type=YRBIDLOW)
    public BigDecimal getYearLowBid() {
        return yearLowBid;
    }

    @RFAType(type=YRBIDLOW)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearLowBid(BigDecimal yearLowBid) {
        this.yearLowBid = yearLowBid;
    }

    @UsingKey(type=HST_CLSBID)
    public BigDecimal getHistoricalClosingBid() {
        return historicalClosingBid;
    }

    @RFAType(type=HST_CLSBID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setHistoricalClosingBid(BigDecimal historicalClosingBid) {
        this.historicalClosingBid = historicalClosingBid;
    }

    @UsingKey(type=HSTCLBDDAT)
    public Long getHistoricalClosingBidDate() {
        return historicalClosingBidDate;
    }

    @RFAType(type=HSTCLBDDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setHistoricalClosingBidDate(Long historicalClosingBidDate) {
        this.historicalClosingBidDate = historicalClosingBidDate;
    }

    @UsingKey(type=YRBDHI_IND)
    public String getYearBidHigh() {
        return yearBidHigh;
    }

    @RFAType(type=YRBDHI_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearBidHigh(String yearBidHigh) {
        this.yearBidHigh = yearBidHigh;
    }

    @UsingKey(type=YRBDLO_IND)
    public String getYearBidLow() {
        return yearBidLow;
    }

    @RFAType(type=YRBDLO_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearBidLow(String yearBidLow) {
        this.yearBidLow = yearBidLow;
    }

    @UsingKey(type=NUM_BIDS)
    public BigInteger getNumberOfBids() {
        return numberOfBids;
    }

    @RFAType(type=NUM_BIDS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setNumberOfBids(BigInteger numberOfBids) {
        this.numberOfBids = numberOfBids;
    }

    @UsingKey(type=RECORDTYPE)
    public BigInteger getRecordType() {
        return recordType;
    }

    @RFAType(type=RECORDTYPE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRecordType(BigInteger recordType) {
        this.recordType = recordType;
    }

    @UsingKey(type=BID_MMID1)
    public String getMarketParticipantBidId() {
        return marketParticipantBidId;
    }

    @RFAType(type=BID_MMID1)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setMarketParticipantBidId(String marketParticipantBidId) {
        this.marketParticipantBidId = marketParticipantBidId;
    }

    @UsingKey(type=ASK_MMID1)
    public String getMarketParticipantAskId() {
        return marketParticipantAskId;
    }

    @RFAType(type=ASK_MMID1)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setMarketParticipantAskId(String marketParticipantAskId) {
        this.marketParticipantAskId = marketParticipantAskId;
    }

    @UsingKey(type=OPTION_XID)
    public String getOptionExchangeId() {
        return optionExchangeId;
    }

    @RFAType(type=OPTION_XID)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOptionExchangeId(String optionExchangeId) {
        this.optionExchangeId = optionExchangeId;
    }

    @UsingKey(type=YRHIGHDAT)
    public Long getYearHighDate() {
        return yearHighDate;
    }

    @RFAType(type=YRHIGHDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setYearHighDate(Long yearHighDate) {
        this.yearHighDate = yearHighDate;
    }

    @UsingKey(type=YRLOWDAT)
    public Long getYearLowDate() {
        return yearLowDate;
    }

    @RFAType(type=YRLOWDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setYearLowDate(Long yearLowDate) {
        this.yearLowDate = yearLowDate;
    }

    @UsingKey(type=IRGPRC)
    public BigDecimal getIrgPrice() {
        return irgPrice;
    }

    @RFAType(type=IRGPRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgPrice(BigDecimal irgPrice) {
        this.irgPrice = irgPrice;
    }

    @UsingKey(type=IRGVOL)
    public BigInteger getIrgVolume() {
        return irgVolume;
    }

    @RFAType(type=IRGVOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgVolume(BigInteger irgVolume) {
        this.irgVolume = irgVolume;
    }

    @UsingKey(type=IRGCOND)
    public String getIrgPriceType() {
        return irgPriceType;
    }

    @RFAType(type=IRGCOND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setIrgPriceType(String irgPriceType) {
        this.irgPriceType = irgPriceType;
    }

    @UsingKey(type=TIMCOR)
    public Long getPriceCorrectionTime() {
        return priceCorrectionTime;
    }

    @RFAType(type=TIMCOR)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setPriceCorrectionTime(Long priceCorrectionTime) {
        this.priceCorrectionTime = priceCorrectionTime;
    }

    @UsingKey(type=INSPRC)
    public BigDecimal getInsertPrice() {
        return insertPrice;
    }

    @RFAType(type=INSPRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setInsertPrice(BigDecimal insertPrice) {
        this.insertPrice = insertPrice;
    }

    @UsingKey(type=INSVOL)
    public BigInteger getInsertVolume() {
        return insertVolume;
    }

    @RFAType(type=INSVOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setInsertVolume(BigInteger insertVolume) {
        this.insertVolume = insertVolume;
    }

    @UsingKey(type=INSCOND)
    public String getInsertPriceType() {
        return insertPriceType;
    }

    @RFAType(type=INSCOND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setInsertPriceType(String insertPriceType) {
        this.insertPriceType = insertPriceType;
    }

    @UsingKey(type=SALTIM)
    public Long getLastTime() {
        return lastTime;
    }

    @RFAType(type=SALTIM)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    @UsingKey(type=TNOVER_SC)
    public String getTurnoverScale() {
        return turnoverScale;
    }

    @RFAType(type=TNOVER_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTurnoverScale(String turnoverScale) {
        this.turnoverScale = turnoverScale;
    }

    @UsingKey(type=BCAST_REF)
    public String getBroadcastXRef() {
        return broadcastXRef;
    }

    @RFAType(type=BCAST_REF)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setBroadcastXRef(String broadcastXRef) {
        this.broadcastXRef = broadcastXRef;
    }

    @UsingKey(type=CROSS_SC)
    public String getCrossRateScale() {
        return crossRateScale;
    }

    @RFAType(type=CROSS_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCrossRateScale(String crossRateScale) {
        this.crossRateScale = crossRateScale;
    }

    @UsingKey(type=AMT_OS)
    public BigDecimal getAmountOutstanding() {
        return amountOutstanding;
    }

    @RFAType(type=AMT_OS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAmountOutstanding(BigDecimal amountOutstanding) {
        this.amountOutstanding = amountOutstanding;
    }

    @UsingKey(type=AMT_OS_SC)
    public String getAmountOutstandingScale() {
        return amountOutstandingScale;
    }

    @RFAType(type=AMT_OS_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setAmountOutstandingScale(String amountOutstandingScale) {
        this.amountOutstandingScale = amountOutstandingScale;
    }

    @UsingKey(type=OFF_CD_IND)
    public String getOfficialCodeIndicator() {
        return officialCodeIndicator;
    }

    @RFAType(type=OFF_CD_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setOfficialCodeIndicator(String officialCodeIndicator) {
        this.officialCodeIndicator = officialCodeIndicator;
    }

    @UsingKey(type=PRC_VOLTY)
    public BigDecimal getPriceVolatility() {
        return priceVolatility;
    }

    @RFAType(type=PRC_VOLTY)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceVolatility(BigDecimal priceVolatility) {
        this.priceVolatility = priceVolatility;
    }

    @UsingKey(type=AMT_OS_DAT)
    public Long getAmountOutstandingDate() {
        return amountOutstandingDate;
    }

    @RFAType(type=AMT_OS_DAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setAmountOutstandingDate(Long amountOutstandingDate) {
        this.amountOutstandingDate = amountOutstandingDate;
    }

    @UsingKey(type=BKGD_REF)
    public String getBackgroundReference() {
        return backgroundReference;
    }

    @RFAType(type=BKGD_REF)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setBackgroundReference(String backgroundReference) {
        this.backgroundReference = backgroundReference;
    }

    @UsingKey(type=GEN_VAL1)
    public BigDecimal getGeneralPurposeValue1() {
        return generalPurposeValue1;
    }

    @RFAType(type=GEN_VAL1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue1(BigDecimal generalPurposeValue1) {
        this.generalPurposeValue1 = generalPurposeValue1;
    }

    @UsingKey(type=GV1_TEXT)
    public String getGeneralPurposeValue1Description() {
        return generalPurposeValue1Description;
    }

    @RFAType(type=GV1_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue1Description(
        String generalPurposeValue1Description) {
        this.generalPurposeValue1Description = generalPurposeValue1Description;
    }

    @UsingKey(type=GEN_VAL2)
    public BigDecimal getGeneralPurposeValue2() {
        return generalPurposeValue2;
    }

    @RFAType(type=GEN_VAL2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue2(BigDecimal generalPurposeValue2) {
        this.generalPurposeValue2 = generalPurposeValue2;
    }

    @UsingKey(type=GV2_TEXT)
    public String getGeneralPurposeValue2Description() {
        return generalPurposeValue2Description;
    }

    @RFAType(type=GV2_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue2Description(
        String generalPurposeValue2Description) {
        this.generalPurposeValue2Description = generalPurposeValue2Description;
    }

    @UsingKey(type=GEN_VAL3)
    public BigDecimal getGeneralPurposeValue3() {
        return generalPurposeValue3;
    }

    @RFAType(type=GEN_VAL3)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue3(BigDecimal generalPurposeValue3) {
        this.generalPurposeValue3 = generalPurposeValue3;
    }

    @UsingKey(type=GV3_TEXT)
    public String getGeneralPurposeValue3Description() {
        return generalPurposeValue3Description;
    }

    @RFAType(type=GV3_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue3Description(
        String generalPurposeValue3Description) {
        this.generalPurposeValue3Description = generalPurposeValue3Description;
    }

    @UsingKey(type=GEN_VAL4)
    public BigDecimal getGeneralPurposeValue4() {
        return generalPurposeValue4;
    }

    @RFAType(type=GEN_VAL4)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue4(BigDecimal generalPurposeValue4) {
        this.generalPurposeValue4 = generalPurposeValue4;
    }

    @UsingKey(type=GV4_TEXT)
    public String getGeneralPurposeValue4Description() {
        return generalPurposeValue4Description;
    }

    @RFAType(type=GV4_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue4Description(
        String generalPurposeValue4Description) {
        this.generalPurposeValue4Description = generalPurposeValue4Description;
    }

    @UsingKey(type=SEQNUM)
    public BigInteger getSequenceNumber() {
        return sequenceNumber;
    }

    @RFAType(type=SEQNUM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSequenceNumber(BigInteger sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @UsingKey(type=PRNTYP)
    public String getPrintType() {
        return printType;
    }

    @RFAType(type=PRNTYP)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setPrintType(String printType) {
        this.printType = printType;
    }

    @UsingKey(type=PRNTBCK)
    public BigInteger getAlteredTradeEventSequenceNumber() {
        return alteredTradeEventSequenceNumber;
    }

    @RFAType(type=PRNTBCK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAlteredTradeEventSequenceNumber(
        BigInteger alteredTradeEventSequenceNumber) {
        this.alteredTradeEventSequenceNumber = alteredTradeEventSequenceNumber;
    }

    @UsingKey(type=QUOTIM)
    public Long getQuoteTimeSeconds() {
        return quoteTimeSeconds;
    }

    @RFAType(type=QUOTIM)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setQuoteTimeSeconds(Long quoteTimeSeconds) {
        this.quoteTimeSeconds = quoteTimeSeconds;
    }

    @UsingKey(type=GV1_FLAG)
    public String getGenericFlag1() {
        return genericFlag1;
    }

    @RFAType(type=GV1_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag1(String genericFlag1) {
        this.genericFlag1 = genericFlag1;
    }

    @UsingKey(type=GV2_FLAG)
    public String getGenericFlag2() {
        return genericFlag2;
    }

    @RFAType(type=GV2_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag2(String genericFlag2) {
        this.genericFlag2 = genericFlag2;
    }

    @UsingKey(type=GV3_FLAG)
    public String getGenericFlag3() {
        return genericFlag3;
    }

    @RFAType(type=GV3_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag3(String genericFlag3) {
        this.genericFlag3 = genericFlag3;
    }

    @UsingKey(type=GV4_FLAG)
    public String getGenericFlag4() {
        return genericFlag4;
    }

    @RFAType(type=GV4_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag4(String genericFlag4) {
        this.genericFlag4 = genericFlag4;
    }

    @UsingKey(type=OFF_CD_IN2)
    public String getUniqueInstrumentId2Source() {
        return uniqueInstrumentId2Source;
    }

    @RFAType(type=OFF_CD_IN2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setUniqueInstrumentId2Source(String uniqueInstrumentId2Source) {
        this.uniqueInstrumentId2Source = uniqueInstrumentId2Source;
    }

    @UsingKey(type=OFFC_CODE2)
    public String getUniqueInstrumentId2() {
        return uniqueInstrumentId2;
    }

    @RFAType(type=OFFC_CODE2)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setUniqueInstrumentId2(String uniqueInstrumentId2) {
        this.uniqueInstrumentId2 = uniqueInstrumentId2;
    }

    @UsingKey(type=GV1_TIME)
    public Long getTimeInSeconds1() {
        return timeInSeconds1;
    }

    @RFAType(type=GV1_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTimeInSeconds1(Long timeInSeconds1) {
        this.timeInSeconds1 = timeInSeconds1;
    }

    @UsingKey(type=GV2_TIME)
    public Long getTimeInSeconds2() {
        return timeInSeconds2;
    }

    @RFAType(type=GV2_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTimeInSeconds2(Long timeInSeconds2) {
        this.timeInSeconds2 = timeInSeconds2;
    }

    @UsingKey(type=EXCHTIM)
    public Long getExchangeTime() {
        return exchangeTime;
    }

    @RFAType(type=EXCHTIM)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExchangeTime(Long exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    @UsingKey(type=YRHI_IND)
    public String getYearHighIndicator() {
        return yearHighIndicator;
    }

    @RFAType(type=YRHI_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearHighIndicator(String yearHighIndicator) {
        this.yearHighIndicator = yearHighIndicator;
    }

    @UsingKey(type=YRLO_IND)
    public String getYearLowIndicator() {
        return yearLowIndicator;
    }

    @RFAType(type=YRLO_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearLowIndicator(String yearLowIndicator) {
        this.yearLowIndicator = yearLowIndicator;
    }

    @UsingKey(type=BETA_VAL)
    public BigDecimal getBetaValue() {
        return betaValue;
    }

    @RFAType(type=BETA_VAL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBetaValue(BigDecimal betaValue) {
        this.betaValue = betaValue;
    }

    @UsingKey(type=PREF_DISP)
    public Integer getPreferredDisplayTemplateNumber() {
        return preferredDisplayTemplateNumber;
    }

    @RFAType(type=PREF_DISP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPreferredDisplayTemplateNumber(
        Integer preferredDisplayTemplateNumber) {
        this.preferredDisplayTemplateNumber = preferredDisplayTemplateNumber;
    }

    @UsingKey(type=DSPLY_NMLL)
    public String getLocalLanguageInstrumentName() {
        return localLanguageInstrumentName;
    }

    @RFAType(type=DSPLY_NMLL)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setLocalLanguageInstrumentName(
        String localLanguageInstrumentName) {
        this.localLanguageInstrumentName = localLanguageInstrumentName;
    }

    @UsingKey(type=VOL_X_PRC1)
    public BigDecimal getLatestTradeOrTradeTurnoverValue() {
        return latestTradeOrTradeTurnoverValue;
    }

    @RFAType(type=VOL_X_PRC1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLatestTradeOrTradeTurnoverValue(
        BigDecimal latestTradeOrTradeTurnoverValue) {
        this.latestTradeOrTradeTurnoverValue = latestTradeOrTradeTurnoverValue;
    }

    @UsingKey(type=DSO_ID)
    public Integer getDataSourceOwnerId() {
        return dataSourceOwnerId;
    }

    @RFAType(type=DSO_ID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setDataSourceOwnerId(Integer dataSourceOwnerId) {
        this.dataSourceOwnerId = dataSourceOwnerId;
    }

    @UsingKey(type=AVERG_PRC)
    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    @RFAType(type=AVERG_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    @UsingKey(type=UPC71_REST)
    public String getUpc71RestrictedFlag() {
        return upc71RestrictedFlag;
    }

    @RFAType(type=UPC71_REST)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setUpc71RestrictedFlag(String upc71RestrictedFlag) {
        this.upc71RestrictedFlag = upc71RestrictedFlag;
    }

    @UsingKey(type=ADJUST_CLS)
    public BigDecimal getAdjustedClose() {
        return adjustedClose;
    }

    @RFAType(type=ADJUST_CLS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAdjustedClose(BigDecimal adjustedClose) {
        this.adjustedClose = adjustedClose;
    }

    @UsingKey(type=WEIGHTING)
    public BigDecimal getWeighting() {
        return weighting;
    }

    @RFAType(type=WEIGHTING)
    @Adapt(using=OMMNumericAdapter.class)
    public void setWeighting(BigDecimal weighting) {
        this.weighting = weighting;
    }

    @UsingKey(type=STOCK_TYPE)
    public String getStockType() {
        return stockType;
    }

    @RFAType(type=STOCK_TYPE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    @UsingKey(type=IMP_VOLT)
    public BigDecimal getImpliedVolatility() {
        return impliedVolatility;
    }

    @RFAType(type=IMP_VOLT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setImpliedVolatility(BigDecimal impliedVolatility) {
        this.impliedVolatility = impliedVolatility;
    }

    @UsingKey(type=RDN_EXCHD2)
    public String getExchangeId2() {
        return exchangeId2;
    }

    @RFAType(type=RDN_EXCHD2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setExchangeId2(String exchangeId2) {
        this.exchangeId2 = exchangeId2;
    }

    @UsingKey(type=CP_ADJ_FCT)
    public BigDecimal getCapitalAdjustmentFactor() {
        return capitalAdjustmentFactor;
    }

    @RFAType(type=CP_ADJ_FCT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCapitalAdjustmentFactor(BigDecimal capitalAdjustmentFactor) {
        this.capitalAdjustmentFactor = capitalAdjustmentFactor;
    }

    @UsingKey(type=CP_ADJ_DAT)
    public Long getCapitalAdjustmentDate() {
        return capitalAdjustmentDate;
    }

    @RFAType(type=CP_ADJ_DAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setCapitalAdjustmentDate(Long capitalAdjustmentDate) {
        this.capitalAdjustmentDate = capitalAdjustmentDate;
    }

    @UsingKey(type=AMT_ISSUE)
    public BigInteger getSharesIssuedTotal() {
        return sharesIssuedTotal;
    }

    @RFAType(type=AMT_ISSUE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSharesIssuedTotal(BigInteger sharesIssuedTotal) {
        this.sharesIssuedTotal = sharesIssuedTotal;
    }

    @UsingKey(type=MKT_VALUE)
    public BigDecimal getMarketValue() {
        return marketValue;
    }

    @RFAType(type=MKT_VALUE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    @UsingKey(type=SPEC_TRADE)
    public Integer getSpecialTermsTradingFlag() {
        return specialTermsTradingFlag;
    }

    @RFAType(type=SPEC_TRADE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSpecialTermsTradingFlag(Integer specialTermsTradingFlag) {
        this.specialTermsTradingFlag = specialTermsTradingFlag;
    }

    @UsingKey(type=FCAST_EARN)
    public BigDecimal getForecastedEarnings() {
        return forecastedEarnings;
    }

    @RFAType(type=FCAST_EARN)
    @Adapt(using=OMMNumericAdapter.class)
    public void setForecastedEarnings(BigDecimal forecastedEarnings) {
        this.forecastedEarnings = forecastedEarnings;
    }

    @UsingKey(type=EARANK_RAT)
    public BigDecimal getEarningsRankRatio() {
        return earningsRankRatio;
    }

    @RFAType(type=EARANK_RAT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setEarningsRankRatio(BigDecimal earningsRankRatio) {
        this.earningsRankRatio = earningsRankRatio;
    }

    @UsingKey(type=FCAST_DATE)
    public Long getForecastDate() {
        return forecastDate;
    }

    @RFAType(type=FCAST_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setForecastDate(Long forecastDate) {
        this.forecastDate = forecastDate;
    }

    @UsingKey(type=YEAR_FCAST)
    public String getForecastYear() {
        return forecastYear;
    }

    @RFAType(type=YEAR_FCAST)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setForecastYear(String forecastYear) {
        this.forecastYear = forecastYear;
    }

    @UsingKey(type=IRGMOD)
    public String getIrgPriceTypeModifier() {
        return irgPriceTypeModifier;
    }

    @RFAType(type=IRGMOD)
    @Adapt(using=OMMEnumAdapter.class)
    public void setIrgPriceTypeModifier(String irgPriceTypeModifier) {
        this.irgPriceTypeModifier = irgPriceTypeModifier;
    }

    @UsingKey(type=INSMOD)
    public String getInsertPriceTypeModifier() {
        return insertPriceTypeModifier;
    }

    @RFAType(type=INSMOD)
    @Adapt(using=OMMEnumAdapter.class)
    public void setInsertPriceTypeModifier(String insertPriceTypeModifier) {
        this.insertPriceTypeModifier = insertPriceTypeModifier;
    }

    @UsingKey(type=A_NPLRS_1)
    public BigInteger getAskPlayersLevel1() {
        return askPlayersLevel1;
    }

    @RFAType(type=A_NPLRS_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAskPlayersLevel1(BigInteger askPlayersLevel1) {
        this.askPlayersLevel1 = askPlayersLevel1;
    }

    @UsingKey(type=B_NPLRS_1)
    public BigInteger getBidPlayersLevel1() {
        return bidPlayersLevel1;
    }

    @RFAType(type=B_NPLRS_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBidPlayersLevel1(BigInteger bidPlayersLevel1) {
        this.bidPlayersLevel1 = bidPlayersLevel1;
    }

    @UsingKey(type=GV3_TIME)
    public Long getGenericTime3() {
        return genericTime3;
    }

    @RFAType(type=GV3_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setGenericTime3(Long genericTime3) {
        this.genericTime3 = genericTime3;
    }

    @UsingKey(type=GV4_TIME)
    public Long getGenericTime4() {
        return genericTime4;
    }

    @RFAType(type=GV4_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setGenericTime4(Long genericTime4) {
        this.genericTime4 = genericTime4;
    }

    @UsingKey(type=MKT_CAP)
    public BigInteger getMarketCapitalisation() {
        return marketCapitalisation;
    }

    @RFAType(type=MKT_CAP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setMarketCapitalisation(BigInteger marketCapitalisation) {
        this.marketCapitalisation = marketCapitalisation;
    }

    @UsingKey(type=IRGFID)
    public BigInteger getIrgCorrectionValueFid() {
        return irgCorrectionValueFid;
    }

    @RFAType(type=IRGFID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgCorrectionValueFid(BigInteger irgCorrectionValueFid) {
        this.irgCorrectionValueFid = irgCorrectionValueFid;
    }

    @UsingKey(type=IRGVAL)
    public BigInteger getIrgCorrectionValue() {
        return irgCorrectionValue;
    }

    @RFAType(type=IRGVAL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgCorrectionValue(BigInteger irgCorrectionValue) {
        this.irgCorrectionValue = irgCorrectionValue;
    }

    @UsingKey(type=PCT_ABNVOL)
    public BigDecimal getAbnormalVolumeIncreasePercentage() {
        return abnormalVolumeIncreasePercentage;
    }

    @RFAType(type=PCT_ABNVOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAbnormalVolumeIncreasePercentage(
        BigDecimal abnormalVolumeIncreasePercentage) {
        this.abnormalVolumeIncreasePercentage =
            abnormalVolumeIncreasePercentage;
    }

    @UsingKey(type=BC_10_50K)
    public BigInteger getBlockTransactionsBetween10KAnd50KShares() {
        return blockTransactionsBetween10KAnd50KShares;
    }

    @RFAType(type=BC_10_50K)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockTransactionsBetween10KAnd50KShares(
        BigInteger blockTransactionsBetween10KAnd50KShares) {
        this.blockTransactionsBetween10KAnd50KShares =
            blockTransactionsBetween10KAnd50KShares;
    }

    @UsingKey(type=BC_50_100K)
    public BigInteger getBlockTransactionsBetween50KAnd100KShares() {
        return blockTransactionsBetween50KAnd100KShares;
    }

    @RFAType(type=BC_50_100K)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockTransactionsBetween50KAnd100KShares(
        BigInteger blockTransactionsBetween50KAnd100KShares) {
        this.blockTransactionsBetween50KAnd100KShares =
            blockTransactionsBetween50KAnd100KShares;
    }

    @UsingKey(type=BC_100K)
    public BigInteger getBlockTransactionsAbove100KShares() {
        return blockTransactionsAbove100KShares;
    }

    @RFAType(type=BC_100K)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockTransactionsAbove100KShares(
        BigInteger blockTransactionsAbove100KShares) {
        this.blockTransactionsAbove100KShares =
            blockTransactionsAbove100KShares;
    }

    @UsingKey(type=PMA_50D)
    public BigDecimal getPriceMovingAverages50D() {
        return priceMovingAverages50D;
    }

    @RFAType(type=PMA_50D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceMovingAverages50D(BigDecimal priceMovingAverages50D) {
        this.priceMovingAverages50D = priceMovingAverages50D;
    }

    @UsingKey(type=PMA_150D)
    public BigDecimal getPriceMovingAverages150D() {
        return priceMovingAverages150D;
    }

    @RFAType(type=PMA_150D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceMovingAverages150D(BigDecimal priceMovingAverages150D) {
        this.priceMovingAverages150D = priceMovingAverages150D;
    }

    @UsingKey(type=PMA_200D)
    public BigDecimal getPriceMovingAverages200D() {
        return priceMovingAverages200D;
    }

    @RFAType(type=PMA_200D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceMovingAverages200D(BigDecimal priceMovingAverages200D) {
        this.priceMovingAverages200D = priceMovingAverages200D;
    }

    @UsingKey(type=VMA_10D)
    public BigInteger getVolumeMovingAverages10D() {
        return volumeMovingAverages10D;
    }

    @RFAType(type=VMA_10D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeMovingAverages10D(BigInteger volumeMovingAverages10D) {
        this.volumeMovingAverages10D = volumeMovingAverages10D;
    }

    @UsingKey(type=VMA_25D)
    public BigInteger getVolumeMovingAverages25D() {
        return volumeMovingAverages25D;
    }

    @RFAType(type=VMA_25D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeMovingAverages25D(BigInteger volumeMovingAverages25D) {
        this.volumeMovingAverages25D = volumeMovingAverages25D;
    }

    @UsingKey(type=VMA_50D)
    public BigInteger getVolumeMovingAverages50D() {
        return volumeMovingAverages50D;
    }

    @RFAType(type=VMA_50D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeMovingAverages50D(BigInteger volumeMovingAverages50D) {
        this.volumeMovingAverages50D = volumeMovingAverages50D;
    }

    @UsingKey(type=OPN_NETCH)
    public BigDecimal getOpenPriceNetChange() {
        return openPriceNetChange;
    }

    @RFAType(type=OPN_NETCH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenPriceNetChange(BigDecimal openPriceNetChange) {
        this.openPriceNetChange = openPriceNetChange;
    }

    @UsingKey(type=CASH_EXDIV)
    public BigDecimal getLatestReportedCashDividend() {
        return latestReportedCashDividend;
    }

    @RFAType(type=CASH_EXDIV)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLatestReportedCashDividend(BigDecimal latestReportedCashDividend) {
        this.latestReportedCashDividend = latestReportedCashDividend;
    }

    @UsingKey(type=MKT_VAL_SC)
    public String getMarketValueScalingFactor() {
        return marketValueScalingFactor;
    }

    @RFAType(type=MKT_VAL_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setMarketValueScalingFactor(String marketValueScalingFactor) {
        this.marketValueScalingFactor = marketValueScalingFactor;
    }

    @UsingKey(type=CASH_EXDAT)
    public Long getExDividendTradeDate() {
        return exDividendTradeDate;
    }

    @RFAType(type=CASH_EXDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExDividendTradeDate(Long exDividendTradeDate) {
        this.exDividendTradeDate = exDividendTradeDate;
    }

    @UsingKey(type=PREV_DISP)
    public Integer getPreviousDisplayTemplate() {
        return previousDisplayTemplate;
    }

    @RFAType(type=PREV_DISP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPreviousDisplayTemplate(Integer previousDisplayTemplate) {
        this.previousDisplayTemplate = previousDisplayTemplate;
    }

    @UsingKey(type=PRC_QL3)
    public String getExtendedPriceQualifierFid() {
        return extendedPriceQualifierFid;
    }

    @RFAType(type=PRC_QL3)
    @Adapt(using=OMMEnumAdapter.class)
    public void setExtendedPriceQualifierFid(String extendedPriceQualifierFid) {
        this.extendedPriceQualifierFid = extendedPriceQualifierFid;
    }

    @UsingKey(type=MPV)
    public String getMinimumPriceMovement() {
        return minimumPriceMovement;
    }

    @RFAType(type=MPV)
    @Adapt(using=OMMEnumAdapter.class)
    public void setMinimumPriceMovement(String minimumPriceMovement) {
        this.minimumPriceMovement = minimumPriceMovement;
    }

    @UsingKey(type=OFF_CLOSE)
    public BigDecimal getOfficialClosingPrice() {
        return officialClosingPrice;
    }

    @RFAType(type=OFF_CLOSE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOfficialClosingPrice(BigDecimal officialClosingPrice) {
        this.officialClosingPrice = officialClosingPrice;
    }

    @UsingKey(type=QUOTE_DATE)
    public Long getQuoteDate() {
        return quoteDate;
    }

    @RFAType(type=QUOTE_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setQuoteDate(Long quoteDate) {
        this.quoteDate = quoteDate;
    }

    @UsingKey(type=VWAP)
    public BigDecimal getVolumeWeightedAveragePrice() {
        return volumeWeightedAveragePrice;
    }

    @RFAType(type=VWAP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeWeightedAveragePrice(
        BigDecimal volumeWeightedAveragePrice) {
        this.volumeWeightedAveragePrice = volumeWeightedAveragePrice;
    }

    @UsingKey(type=PROV_SYMB)
    public String getProviderSymbol() {
        return providerSymbol;
    }

    @RFAType(type=PROV_SYMB)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setProviderSymbol(String providerSymbol) {
        this.providerSymbol = providerSymbol;
    }

    @UsingKey(type=BID_ASK_DT)
    public Long getBidAskDate() {
        return bidAskDate;
    }

    @RFAType(type=BID_ASK_DT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setBidAskDate(Long bidAskDate) {
        this.bidAskDate = bidAskDate;
    }

    @UsingKey(type=ISIN_CODE)
    public String getIsinCode() {
        return isinCode;
    }

    @RFAType(type=ISIN_CODE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    @UsingKey(type=MNEMONIC)
    public String getExchangeId() {
        return exchangeId;
    }

    @RFAType(type=MNEMONIC)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    @UsingKey(type=RTR_OPN_PR)
    public BigDecimal getRtrsOpeningPrice() {
        return rtrsOpeningPrice;
    }

    @RFAType(type=RTR_OPN_PR)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRtrsOpeningPrice(BigDecimal rtrsOpeningPrice) {
        this.rtrsOpeningPrice = rtrsOpeningPrice;
    }

    @UsingKey(type=SEDOL)
    public String getSedolCode() {
        return sedolCode;
    }

    @RFAType(type=SEDOL)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setSedolCode(String sedolCode) {
        this.sedolCode = sedolCode;
    }

    @UsingKey(type=MKT_SEGMNT)
    public String getMarketSegment() {
        return marketSegment;
    }

    @RFAType(type=MKT_SEGMNT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setMarketSegment(String marketSegment) {
        this.marketSegment = marketSegment;
    }

    @UsingKey(type=TRDTIM_MS)
    public Long getRegularTradesTimeMillis() {
        return regularTradesTimeMillis;
    }

    @RFAType(type=TRDTIM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRegularTradesTimeMillis(Long regularTradesTimeMillis) {
        this.regularTradesTimeMillis = regularTradesTimeMillis;
    }

    @UsingKey(type=SALTIM_MS)
    public Long getAllTradesTimeMillis() {
        return allTradesTimeMillis;
    }

    @RFAType(type=SALTIM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAllTradesTimeMillis(Long allTradesTimeMillis) {
        this.allTradesTimeMillis = allTradesTimeMillis;
    }

    @UsingKey(type=QUOTIM_MS)
    public Long getQuoteTimeMillis() {
        return quoteTimeMillis;
    }

    @RFAType(type=QUOTIM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setQuoteTimeMillis(Long quoteTimeMillis) {
        this.quoteTimeMillis = quoteTimeMillis;
    }

    @UsingKey(type=TIMCOR_MS)
    public BigInteger getCorrectionTimeMillis() {
        return correctionTimeMillis;
    }

    @RFAType(type=TIMCOR_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCorrectionTimeMillis(BigInteger correctionTimeMillis) {
        this.correctionTimeMillis = correctionTimeMillis;
    }

    @UsingKey(type=FIN_STATUS)
    public String getFinancialStatusIndicator() {
        return financialStatusIndicator;
    }

    @RFAType(type=FIN_STATUS)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setFinancialStatusIndicator(String financialStatusIndicator) {
        this.financialStatusIndicator = financialStatusIndicator;
    }

    @UsingKey(type=LS_SUBIND)
    public String getLastTradeSubMarketIndicator() {
        return lastTradeSubMarketIndicator;
    }

    @RFAType(type=LS_SUBIND)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setLastTradeSubMarketIndicator(
        String lastTradeSubMarketIndicator) {
        this.lastTradeSubMarketIndicator = lastTradeSubMarketIndicator;
    }

    @UsingKey(type=IRG_SUBIND)
    public String getIrgPriceSubmarketIndicator() {
        return irgPriceSubmarketIndicator;
    }

    @RFAType(type=IRG_SUBIND)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setIrgPriceSubmarketIndicator(
        String irgPriceSubmarketIndicator) {
        this.irgPriceSubmarketIndicator = irgPriceSubmarketIndicator;
    }

    @UsingKey(type=ACVOL_SC)
    public String getVolumeScaling() {
        return volumeScaling;
    }

    @RFAType(type=ACVOL_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setVolumeScaling(String volumeScaling) {
        this.volumeScaling = volumeScaling;
    }

    @UsingKey(type=EXCHCODE)
    public String getExchangeCode() {
        return exchangeCode;
    }

    @RFAType(type=EXCHCODE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    @UsingKey(type=ODD_ASK)
    public BigDecimal getOddBestAsk() {
        return oddBestAsk;
    }

    @RFAType(type=ODD_ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestAsk(BigDecimal oddBestAsk) {
        this.oddBestAsk = oddBestAsk;
    }

    @UsingKey(type=ODD_ASKSIZ)
    public BigInteger getOddBestAskSize() {
        return oddBestAskSize;
    }

    @RFAType(type=ODD_ASKSIZ)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestAskSize(BigInteger oddBestAskSize) {
        this.oddBestAskSize = oddBestAskSize;
    }

    @UsingKey(type=ODD_BID)
    public BigDecimal getOddBestBid() {
        return oddBestBid;
    }

    @RFAType(type=ODD_BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestBid(BigDecimal oddBestBid) {
        this.oddBestBid = oddBestBid;
    }

    @UsingKey(type=ODD_BIDSIZ)
    public BigInteger getOddBestBidSize() {
        return oddBestBidSize;
    }

    @RFAType(type=ODD_BIDSIZ)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestBidSize(BigInteger oddBestBidSize) {
        this.oddBestBidSize = oddBestBidSize;
    }

    @UsingKey(type=ROUND_VOL)
    public BigInteger getRoundVolume() {
        return roundVolume;
    }

    @RFAType(type=ROUND_VOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRoundVolume(BigInteger roundVolume) {
        this.roundVolume = roundVolume;
    }

    @UsingKey(type=ORGID)
    public BigInteger getOrganizationId() {
        return organizationId;
    }

    @RFAType(type=ORGID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOrganizationId(BigInteger organizationId) {
        this.organizationId = organizationId;
    }

    @UsingKey(type=PR_FREQ)
    public String getPriceUpdateFrequency() {
        return priceUpdateFrequency;
    }

    @RFAType(type=PR_FREQ)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceUpdateFrequency(String priceUpdateFrequency) {
        this.priceUpdateFrequency = priceUpdateFrequency;
    }

    @UsingKey(type=RCS_AS_CLA)
    public String getRcsAssetClassification() {
        return rcsAssetClassification;
    }

    @RFAType(type=RCS_AS_CLA)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRcsAssetClassification(String rcsAssetClassification) {
        this.rcsAssetClassification = rcsAssetClassification;
    }

    @UsingKey(type=UNDR_INDEX)
    public String getUnderlyingIndex() {
        return underlyingIndex;
    }

    @RFAType(type=UNDR_INDEX)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setUnderlyingIndex(String underlyingIndex) {
        this.underlyingIndex = underlyingIndex;
    }

    @UsingKey(type=FUTURES)
    public String getFuturesChainRic() {
        return futuresChainRic;
    }

    @RFAType(type=FUTURES)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setFuturesChainRic(String futuresChainRic) {
        this.futuresChainRic = futuresChainRic;
    }

    @UsingKey(type=OPTIONS)
    public String getOptionsChainRic() {
        return optionsChainRic;
    }

    @RFAType(type=OPTIONS)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOptionsChainRic(String optionsChainRic) {
        this.optionsChainRic = optionsChainRic;
    }

    @UsingKey(type=STRIKES)
    public String getStrikesCoverage() {
        return strikesCoverage;
    }

    @RFAType(type=STRIKES)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setStrikesCoverage(String strikesCoverage) {
        this.strikesCoverage = strikesCoverage;
    }

    @UsingKey(type=NEWSTM_MS)
    public BigInteger getNewsTimeMillis() {
        return newsTimeMillis;
    }

    @RFAType(type=NEWSTM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setNewsTimeMillis(BigInteger newsTimeMillis) {
        this.newsTimeMillis = newsTimeMillis;
    }

    @UsingKey(type=TRD_THRU_X)
    public String getTradeThroughExemptFlags() {
        return tradeThroughExemptFlags;
    }

    @RFAType(type=TRD_THRU_X)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setTradeThroughExemptFlags(String tradeThroughExemptFlags) {
        this.tradeThroughExemptFlags = tradeThroughExemptFlags;
    }

    @UsingKey(type=FIN_ST_IND)
    public String getCompanyComplianceStatus() {
        return companyComplianceStatus;
    }

    @RFAType(type=FIN_ST_IND)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setCompanyComplianceStatus(String companyComplianceStatus) {
        this.companyComplianceStatus = companyComplianceStatus;
    }

    @UsingKey(type=IRG_SMKTID)
    public String getIrgSubMarketCenterId() {
        return irgSubMarketCenterId;
    }

    @RFAType(type=IRG_SMKTID)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setIrgSubMarketCenterId(String irgSubMarketCenterId) {
        this.irgSubMarketCenterId = irgSubMarketCenterId;
    }

    @UsingKey(type=SUB_MKT_ID)
    public String getSubMarketCenterId() {
        return subMarketCenterId;
    }

    @RFAType(type=SUB_MKT_ID)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setSubMarketCenterId(String subMarketCenterId) {
        this.subMarketCenterId = subMarketCenterId;
    }

    @UsingKey(type=ACT_DOM_EX)
    public String getActiveDomesticExchangeIds() {
        return activeDomesticExchangeIds;
    }

    @RFAType(type=ACT_DOM_EX)
    @Adapt(using=OMMEnumAdapter.class)
    public void setActiveDomesticExchangeIds(String activeDomesticExchangeIds) {
        this.activeDomesticExchangeIds = activeDomesticExchangeIds;
    }

    @UsingKey(type=ACT_OTH_EX)
    public String getActiveOtherExchangeIds() {
        return activeOtherExchangeIds;
    }

    @RFAType(type=ACT_OTH_EX)
    @Adapt(using=OMMEnumAdapter.class)
    public void setActiveOtherExchangeIds(String activeOtherExchangeIds) {
        this.activeOtherExchangeIds = activeOtherExchangeIds;
    }

    @UsingKey(type=TRD_QUAL_2)
    public String getTradePriceQualifier2() {
        return tradePriceQualifier2;
    }

    @RFAType(type=TRD_QUAL_2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradePriceQualifier2(String tradePriceQualifier2) {
        this.tradePriceQualifier2 = tradePriceQualifier2;
    }

    @UsingKey(type=CP_EFF_DAT)
    public Long getLatestCapitalChangeEffectiveDate() {
        return latestCapitalChangeEffectiveDate;
    }

    @RFAType(type=CP_EFF_DAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLatestCapitalChangeEffectiveDate(
        Long latestCapitalChangeEffectiveDate) {
        this.latestCapitalChangeEffectiveDate =
            latestCapitalChangeEffectiveDate;
    }

    @UsingKey(type=ROW64_1)
    public String getRow64_1() {
        return row64_1;
    }

    @RFAType(type=ROW64_1)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_1(String row64_1) {
        this.row64_1 = row64_1;
    }

    @UsingKey(type=ROW64_2)
    public String getRow64_2() {
        return row64_2;
    }

    @RFAType(type=ROW64_2)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_2(String row64_2) {
        this.row64_2 = row64_2;
    }

    @UsingKey(type=ROW64_3)
    public String getRow64_3() {
        return row64_3;
    }

    @RFAType(type=ROW64_3)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_3(String row64_3) {
        this.row64_3 = row64_3;
    }

    @UsingKey(type=ROW64_4)
    public String getRow64_4() {
        return row64_4;
    }

    @RFAType(type=ROW64_4)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_4(String row64_4) {
        this.row64_4 = row64_4;
    }

    @UsingKey(type=ROW64_5)
    public String getRow64_5() {
        return row64_5;
    }

    @RFAType(type=ROW64_5)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_5(String row64_5) {
        this.row64_5 = row64_5;
    }

    @UsingKey(type=ROW64_6)
    public String getRow64_6() {
        return row64_6;
    }

    @RFAType(type=ROW64_6)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_6(String row64_6) {
        this.row64_6 = row64_6;
    }

    @UsingKey(type=ROW64_7)
    public String getRow64_7() {
        return row64_7;
    }

    @RFAType(type=ROW64_7)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_7(String row64_7) {
        this.row64_7 = row64_7;
    }

    @UsingKey(type=ROW64_8)
    public String getRow64_8() {
        return row64_8;
    }

    @RFAType(type=ROW64_8)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_8(String row64_8) {
        this.row64_8 = row64_8;
    }

    @UsingKey(type=ROW64_9)
    public String getRow64_9() {
        return row64_9;
    }

    @RFAType(type=ROW64_9)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_9(String row64_9) {
        this.row64_9 = row64_9;
    }

    @UsingKey(type=ROW64_10)
    public String getRow64_10() {
        return row64_10;
    }

    @RFAType(type=ROW64_10)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_10(String row64_10) {
        this.row64_10 = row64_10;
    }

    @UsingKey(type=ROW64_11)
    public String getRow64_11() {
        return row64_11;
    }

    @RFAType(type=ROW64_11)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_11(String row64_11) {
        this.row64_11 = row64_11;
    }

    @UsingKey(type=ROW64_12)
    public String getRow64_12() {
        return row64_12;
    }

    @RFAType(type=ROW64_12)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_12(String row64_12) {
        this.row64_12 = row64_12;
    }

    @UsingKey(type=ROW64_13)
    public String getRow64_13() {
        return row64_13;
    }

    @RFAType(type=ROW64_13)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_13(String row64_13) {
        this.row64_13 = row64_13;
    }

    @UsingKey(type=ROW64_14)
    public String getRow64_14() {
        return row64_14;
    }

    @RFAType(type=ROW64_14)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_14(String row64_14) {
        this.row64_14 = row64_14;
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
    @RFAType(type=PUT_CALL)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPutCall(String putCall) {
        this.putCall = putCall;
    }

    /**
     * Getter method for the {@link MarketPriceConstants#IMP_VOLTA}.
     */
    @UsingKey(type=IMP_VOLTA)
    public BigDecimal getImpliedVolatilitytOfAskPrice() {
        return impliedVolatilitytOfAskPrice;
    }

    /**
     * Setter method for the {@link MarketPriceConstants#IMP_VOLTA}.
     */
    @RFAType(type=IMP_VOLTA)
    @Adapt(using=OMMNumericAdapter.class)
    public void setImpliedVolatilitytOfAskPrice(
        BigDecimal impliedVolatilitytOfAskPrice
    ) {
        this.impliedVolatilitytOfAskPrice = impliedVolatilitytOfAskPrice;
    }

    /**
     * Setter method for the {@link MarketPriceConstants#IMP_VOLTB}.
     */
    @UsingKey(type=IMP_VOLTB)
    public BigDecimal getImpliedVolatilitytOfBidPrice() {
        return impliedVolatilitytOfBidPrice;
    }

    /**
     * Getter method for the {@link MarketPriceConstants#IMP_VOLTB}.
     */
    @RFAType(type=IMP_VOLTB)
    @Adapt(using=OMMNumericAdapter.class)
    public void setImpliedVolatilitytOfBidPrice(
        BigDecimal impliedVolatilitytOfBidPrice
    ) {
        this.impliedVolatilitytOfBidPrice = impliedVolatilitytOfBidPrice;
    }

    /**
     * Getter method for the {@link MarketPriceConstants#OPINT_1}.
     */
    @UsingKey(type=OPINT_1)
    public BigInteger getOpenInterest() {
        return openInterest;
    }

    /**
     * Setter method for the {@link MarketPriceConstants#OPINT_1}.
     */
    @RFAType(type=OPINT_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenInterest(BigInteger openInterest) {
        this.openInterest = openInterest;
    }

    /**
     * Getter method for the {@link MarketPriceConstants#OPINTNC}.
     */
    @UsingKey(type=OPINTNC)
    public BigInteger getOpenInterestNetChange() {
        return openInterestNetChange;
    }

    /**
     * Setter method for the {@link MarketPriceConstants#OPINTNC}.
     */
    @RFAType(type=OPINTNC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenInterestNetChange(BigInteger openInterestNetChange) {
        this.openInterestNetChange = openInterestNetChange;
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
    @RFAType(type=STRIKE_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setStrikePrice(BigDecimal strikePrice) {
        this.strikePrice = strikePrice;
    }

    @UsingKey(type=CONTR_MNTH)
    public String getContractMonth() {
        return contractMonth;
    }

    @RFAType(type=CONTR_MNTH)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setContractMonth(String contractMonth) {
        this.contractMonth = contractMonth;
    }

    @UsingKey(type=LOTSZUNITS)
    public String getLotSizeUnits() {
        return lotSizeUnits;
    }

    @RFAType(type=LOTSZUNITS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setLotSizeUnits(String lotSizeUnits) {
        this.lotSizeUnits = lotSizeUnits;
    }

    @UsingKey(type=OPEN_ASK)
    public BigDecimal getOpenAskPrice() {
        return openAskPrice;
    }

    @RFAType(type=OPEN_ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenAskPrice(BigDecimal openAskPrice) {
        this.openAskPrice = openAskPrice;
    }

    @UsingKey(type=EXPIR_DATE)
    public Long getExpiryDate() {
        return expiryDate;
    }

    @RFAType(type=EXPIR_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }

    @UsingKey(type=SETTLE)
    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    @RFAType(type=SETTLE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSettlementPrice(BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    @UsingKey(type=SETTLEDATE)
    public Long getSettleDate() {
        return settleDate;
    }

    @RFAType(type=SETTLEDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setSettleDate(Long settleDate) {
        this.settleDate = settleDate;
    }
}
