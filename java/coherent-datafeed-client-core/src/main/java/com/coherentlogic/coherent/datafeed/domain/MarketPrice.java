package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_PRICE;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.coherentlogic.coherent.data.model.core.annotations.Changeable;
import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeListener;
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
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=MARKET_PRICE)
@XStreamAlias(MARKET_PRICE)
public class MarketPrice extends AbstractCommonProperties
    implements RICBeanSpecification {

    private static final long serialVersionUID = -8330990635265356088L;

    private String ric;

    /**
     * Display information for the IDN terminal device.
     *
     * RDNDISPLAY: UINT32 though treat this as a UINT as UINT32 has been
     *             deprecated.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.RDNDISPLAY)
    private BigInteger displayTemplate = null;

    /**
     * Identifier for the market on which the instrument trades.
     *
     * @deprecated (From the RDMFieldDictionary) Use field RDN_EXCHD2 #1709.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.RDN_EXCHID)
    private String idnExchangeId = null;

    /**
     * Full or abbreviated text instrument name.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.DSPLY_NAME)
    private String displayName = null;

    /**
     * BID: REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.BID)
    private BigDecimal bid = null;

    /**
     * BID: REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.BID_1)
    private BigDecimal bid1 = null;

    /**
     * BID: REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.BID_2)
    private BigDecimal bid2 = null;

    /**
     * ASK: REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.ASK)
    private BigDecimal ask = null;

    /**
     * ASK: REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.ASK_1)
    private BigDecimal ask1 = null;

    /**
     * ASK: REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.ASK_2)
    private BigDecimal ask2 = null;

    /**
     * BIDSIZE: REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.BIDSIZE)
    private BigInteger bidSize = null;

    /**
     * ASKSIZE: REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.ASKSIZE)
    private BigInteger askSize = null;

    /**
     * Trade price 1
     *
     * LAST: REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.TRDPRC_1)
    private BigDecimal last = null;

    /**
     * Trade price 2
     *
     * LAST: REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.TRDPRC_2)
    private BigDecimal last1 = null;

    /**
     * Trade price 3
     *
     * REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.TRDPRC_3)
    private BigDecimal last2 = null;

    /**
     * Trade price 4
     *
     * REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.TRDPRC_4)
    private BigDecimal last3 = null;

    /**
     * Trade price 5
     *
     * REAL
     */
    @XStreamAlias(RDMFieldDictionaryConstants.TRDPRC_5)
    private BigDecimal last4 = null;

    /**
     * Net change.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.NETCHNG_1)
    private BigDecimal netChange = null;

    @XStreamAlias(RDMFieldDictionaryConstants.HIGH_1)
    private BigDecimal todaysHigh = null;

    @XStreamAlias(RDMFieldDictionaryConstants.LOW_1)
    private BigDecimal todaysLow = null;

    /**
     * Tick up/down arrow.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.PRCTCK_1)
    private Integer tickArrow = null;

//    @XStreamAlias(CURRENCY)
//    private String currency = null;

    @XStreamAlias(RDMFieldDictionaryConstants.TRADE_DATE)
    private Long tradeDateMillis = null;

    @XStreamAlias(RDMFieldDictionaryConstants.TRDTIM_1)
    private Long tradeTimeMillis = null;

    @XStreamAlias(RDMFieldDictionaryConstants.OPEN_PRC)
    private BigDecimal openPrice = null;

    @XStreamAlias(RDMFieldDictionaryConstants.HST_CLOSE)
    private BigDecimal historicClose = null;

    @XStreamAlias(RDMFieldDictionaryConstants.NEWS)
    private String news = null;

    @XStreamAlias(RDMFieldDictionaryConstants.NEWS_TIME)
    private Long newsTime = null;

    @XStreamAlias(RDMFieldDictionaryConstants.ACVOL_1)
    private BigInteger volumeAccumulated = null;

    @XStreamAlias(RDMFieldDictionaryConstants.EARNINGS)
    private BigDecimal earnings = null;

    @XStreamAlias(RDMFieldDictionaryConstants.YIELD)
    private BigDecimal yield = null;

    @XStreamAlias(RDMFieldDictionaryConstants.PERATIO)
    private BigDecimal priceToEarningsRatio = null;

    @XStreamAlias(RDMFieldDictionaryConstants.DIVIDENDTP)
    private String dividendType = null;

    @XStreamAlias(RDMFieldDictionaryConstants.DIVPAYDATE)
    private Long dividendPayDate = null;

    @XStreamAlias(RDMFieldDictionaryConstants.EXDIVDATE)
    private Long exDividendDate = null;

    @XStreamAlias(RDMFieldDictionaryConstants.CTS_QUAL)
    private String tradePriceQualifier = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BLKCOUNT)
    private BigInteger blockCount = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BLKVOLUM)
    private BigInteger blockVolume = null;

    @XStreamAlias(RDMFieldDictionaryConstants.TRDXID_1)
    private String tradeExchangeId = null;

    @XStreamAlias(RDMFieldDictionaryConstants.TRD_UNITS)
    private String tradingUnits = null;

    @XStreamAlias(RDMFieldDictionaryConstants.LOT_SIZE)
    private BigInteger lotSize = null;

    @XStreamAlias(RDMFieldDictionaryConstants.PCTCHNG)
    private BigDecimal percentChange = null;

    @XStreamAlias(RDMFieldDictionaryConstants.OPEN_BID)
    private BigDecimal openBid = null;

    @XStreamAlias(RDMFieldDictionaryConstants.DJTIME)
    private Long latestDowJonesNewsStoryTime = null;

    @XStreamAlias(RDMFieldDictionaryConstants.CLOSE_BID)
    private BigDecimal closeBid = null;

    @XStreamAlias(RDMFieldDictionaryConstants.CLOSE_ASK)
    private BigDecimal closeAsk = null;

    @XStreamAlias(RDMFieldDictionaryConstants.DIVIDEND)
    private BigDecimal dividend = null;

    @XStreamAlias(RDMFieldDictionaryConstants.NUM_MOVES)
    private BigInteger totalTradesToday = null;

    @XStreamAlias(RDMFieldDictionaryConstants.OFFCL_CODE)
    private String officialCode = null;

    @XStreamAlias(RDMFieldDictionaryConstants.HSTCLSDATE)
    private Long historicCloseDate = null;

    @XStreamAlias(RDMFieldDictionaryConstants.YRHIGH)
    private BigDecimal yearHigh = null;

    @XStreamAlias(RDMFieldDictionaryConstants.YRLOW)
    private BigDecimal yearLow = null;

    @XStreamAlias(RDMFieldDictionaryConstants.TURNOVER)
    private BigDecimal turnover = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BOND_TYPE)
    private String bondType = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BCKGRNDPAG)
    private String backgroundPage = null;

    @XStreamAlias(RDMFieldDictionaryConstants.YCHIGH_IND)
    private String yearOrContractHighIndicator = null;

    @XStreamAlias(RDMFieldDictionaryConstants.YCLOW_IND)
    private String yearOrContractLowIndicator = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BID_NET_CH)
    private BigDecimal bidNetChange = null;

    /**
     * @todo Rename this property as the name is ugly.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.BID_TICK_1)
    private String bidTick1 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.CUM_EX_MKR)
    private String cumExMarker = null;

    @XStreamAlias(RDMFieldDictionaryConstants.PRC_QL_CD)
    private String priceCode = null;

    @XStreamAlias(RDMFieldDictionaryConstants.NASDSTATUS)
    private String nasdStatus = null;

    @XStreamAlias(RDMFieldDictionaryConstants.PRC_QL2)
    private String priceCode2 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.TRDVOL_1)
    private BigInteger tradeVolume = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BID_HIGH_1)
    private BigDecimal todaysHighBid = null;
    
    @XStreamAlias(RDMFieldDictionaryConstants.BID_LOW_1)
    private BigDecimal todaysLowBid = null;
    
    @XStreamAlias(RDMFieldDictionaryConstants.YRBIDHIGH)
    private BigDecimal yearHighBid = null;

    @XStreamAlias(RDMFieldDictionaryConstants.YRBIDLOW)
    private BigDecimal yearLowBid = null;

    @XStreamAlias(RDMFieldDictionaryConstants.HST_CLSBID)
    private BigDecimal historicalClosingBid = null;

    @XStreamAlias(RDMFieldDictionaryConstants.HSTCLBDDAT)
    private Long historicalClosingBidDate = null;

    @XStreamAlias(RDMFieldDictionaryConstants.YRBDHI_IND)
    private String yearBidHigh = null;

    @XStreamAlias(RDMFieldDictionaryConstants.YRBDLO_IND)
    private String yearBidLow = null;

    @XStreamAlias(RDMFieldDictionaryConstants.NUM_BIDS)
    private BigInteger numberOfBids = null;

//    @XStreamAlias(RECORDTYPE)
//    private BigInteger recordType = null;

    @XStreamAlias(RDMFieldDictionaryConstants.OPTION_XID)
    private String optionExchangeId = null;

    @XStreamAlias(RDMFieldDictionaryConstants.YRHIGHDAT)
    private Long yearHighDate = null;

    @XStreamAlias(RDMFieldDictionaryConstants.YRLOWDAT)
    private Long yearLowDate = null;

    @XStreamAlias(RDMFieldDictionaryConstants.IRGPRC)
    private BigDecimal irgPrice = null;

    @XStreamAlias(RDMFieldDictionaryConstants.IRGVOL)
    private BigInteger irgVolume = null;

    @XStreamAlias(RDMFieldDictionaryConstants.IRGCOND)
    private String irgPriceType = null;

    @XStreamAlias(RDMFieldDictionaryConstants.TIMCOR)
    private Long priceCorrectionTime = null;

    @XStreamAlias(RDMFieldDictionaryConstants.INSPRC)
    private BigDecimal insertPrice = null;

    @XStreamAlias(RDMFieldDictionaryConstants.INSVOL)
    private BigInteger insertVolume = null;

    @XStreamAlias(RDMFieldDictionaryConstants.INSCOND)
    private String insertPriceType = null;

    @XStreamAlias(RDMFieldDictionaryConstants.SALTIM)
    private Long lastTime = null;

    @XStreamAlias(RDMFieldDictionaryConstants.TNOVER_SC)
    private String turnoverScale = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BCAST_REF)
    private String broadcastXRef = null;

    @XStreamAlias(RDMFieldDictionaryConstants.CROSS_SC)
    private String crossRateScale = null;

    @XStreamAlias(RDMFieldDictionaryConstants.AMT_OS)
    private BigDecimal amountOutstanding = null;

    @XStreamAlias(RDMFieldDictionaryConstants.AMT_OS_SC)
    private String amountOutstandingScale = null;

    @XStreamAlias(RDMFieldDictionaryConstants.OFF_CD_IND)
    private String officialCodeIndicator = null;

    @XStreamAlias(RDMFieldDictionaryConstants.PRC_VOLTY)
    private BigDecimal priceVolatility = null;

    /**
     * The date when the shares outstanding was reported.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.AMT_OS_DAT)
    private Long amountOutstandingDate = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BKGD_REF)
    private String backgroundReference = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GEN_VAL1)
    private BigDecimal generalPurposeValue1 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GV1_TEXT)
    private String generalPurposeValue1Description = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GEN_VAL2)
    private BigDecimal generalPurposeValue2 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GV2_TEXT)
    private String generalPurposeValue2Description = null;
    
    @XStreamAlias(RDMFieldDictionaryConstants.GEN_VAL3)
    private BigDecimal generalPurposeValue3 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GV3_TEXT)
    private String generalPurposeValue3Description = null;
    
    @XStreamAlias(RDMFieldDictionaryConstants.GEN_VAL4)
    private BigDecimal generalPurposeValue4 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GV4_TEXT)
    private String generalPurposeValue4Description = null;

    @XStreamAlias(RDMFieldDictionaryConstants.SEQNUM)
    private BigInteger sequenceNumber = null;

    @XStreamAlias(RDMFieldDictionaryConstants.PRNTYP)
    private String printType = null;

    @XStreamAlias(RDMFieldDictionaryConstants.PRNTBCK)
    private BigInteger alteredTradeEventSequenceNumber = null;

    @XStreamAlias(RDMFieldDictionaryConstants.QUOTIM)
    private Long quoteTimeSeconds = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GV1_FLAG)
    private String genericFlag1 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GV2_FLAG)
    private String genericFlag2 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GV3_FLAG)
    private String genericFlag3 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GV4_FLAG)
    private String genericFlag4 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.OFF_CD_IN2)
    private String uniqueInstrumentId2Source = null;

    @XStreamAlias(RDMFieldDictionaryConstants.OFFC_CODE2)
    private String uniqueInstrumentId2 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GV1_TIME)
    private Long timeInSeconds1 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GV2_TIME)
    private Long timeInSeconds2 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.EXCHTIM)
    private Long exchangeTime = null;

    @XStreamAlias(RDMFieldDictionaryConstants.YRHI_IND)
    private String yearHighIndicator = null;

    @XStreamAlias(RDMFieldDictionaryConstants.YRLO_IND)
    private String yearLowIndicator = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BETA_VAL)
    private BigDecimal betaValue = null;

    /**
     * This is a UINT32 / binary so I'm marking this as a int for the moment.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.PREF_DISP)
    private Integer preferredDisplayTemplateNumber = null;

    @XStreamAlias(RDMFieldDictionaryConstants.DSPLY_NMLL)
    private String localLanguageInstrumentName = null;

    @XStreamAlias(RDMFieldDictionaryConstants.VOL_X_PRC1)
    private BigDecimal latestTradeOrTradeTurnoverValue = null;

    @XStreamAlias(RDMFieldDictionaryConstants.DSO_ID)
    private Integer dataSourceOwnerId = null;

    @XStreamAlias(RDMFieldDictionaryConstants.AVERG_PRC)
    private BigDecimal averagePrice = null;

    @XStreamAlias(RDMFieldDictionaryConstants.UPC71_REST)
    private String upc71RestrictedFlag = null;
    
    @XStreamAlias(RDMFieldDictionaryConstants.ADJUST_CLS)
    private BigDecimal adjustedClose = null;

    @XStreamAlias(RDMFieldDictionaryConstants.WEIGHTING)
    private BigDecimal weighting = null;

    @XStreamAlias(RDMFieldDictionaryConstants.STOCK_TYPE)
    private String stockType = null;

    @XStreamAlias(RDMFieldDictionaryConstants.IMP_VOLT)
    private BigDecimal impliedVolatility = null;

//    @XStreamAlias(RDN_EXCHD2)
//    private String exchangeId2 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.CP_ADJ_FCT)
    private BigDecimal capitalAdjustmentFactor = null;

    @XStreamAlias(RDMFieldDictionaryConstants.CP_ADJ_DAT)
    private Long capitalAdjustmentDate = null;

    @XStreamAlias(RDMFieldDictionaryConstants.AMT_ISSUE)
    private BigInteger sharesIssuedTotal = null;

    @XStreamAlias(RDMFieldDictionaryConstants.MKT_VALUE)
    private BigDecimal marketValue = null;

    @XStreamAlias(RDMFieldDictionaryConstants.SPEC_TRADE)
    private Integer specialTermsTradingFlag = null;

    @XStreamAlias(RDMFieldDictionaryConstants.FCAST_EARN)
    private BigDecimal forecastedEarnings = null;

    @XStreamAlias(RDMFieldDictionaryConstants.EARANK_RAT)
    private BigDecimal earningsRankRatio = null;

    @XStreamAlias(RDMFieldDictionaryConstants.FCAST_DATE)
    private Long forecastDate = null;

    /**
     * Data buffer
     */
    @XStreamAlias(RDMFieldDictionaryConstants.YEAR_FCAST)
    private String forecastYear = null;

    /**
     * Enum
     */
    @XStreamAlias(RDMFieldDictionaryConstants.IRGMOD)
    private String irgPriceTypeModifier = null;

    @XStreamAlias(RDMFieldDictionaryConstants.INSMOD)
    private String insertPriceTypeModifier = null;

    @XStreamAlias(RDMFieldDictionaryConstants.A_NPLRS_1)
    private BigInteger askPlayersLevel1 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.B_NPLRS_1)
    private BigInteger bidPlayersLevel1 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GV3_TIME)
    private Long genericTime3 = null;

    @XStreamAlias(RDMFieldDictionaryConstants.GV4_TIME)
    private Long genericTime4 = null;

    /**
     * Deprecated in favor of MKT_VAL (note this is available in this api
     * already).
     */
    @XStreamAlias(RDMFieldDictionaryConstants.MKT_CAP)
    private BigInteger marketCapitalisation = null;

    @XStreamAlias(RDMFieldDictionaryConstants.IRGFID)
    private BigInteger irgCorrectionValueFid = null;

    @XStreamAlias(RDMFieldDictionaryConstants.IRGVAL)
    private BigInteger irgCorrectionValue = null;

    @XStreamAlias(RDMFieldDictionaryConstants.PCT_ABNVOL)
    private BigDecimal abnormalVolumeIncreasePercentage = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BC_10_50K)
    private BigInteger blockTransactionsBetween10KAnd50KShares = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BC_50_100K)
    private BigInteger blockTransactionsBetween50KAnd100KShares = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BC_100K)
    private BigInteger blockTransactionsAbove100KShares = null;

    @XStreamAlias(RDMFieldDictionaryConstants.PMA_50D)
    private BigDecimal priceMovingAverages50D = null;

    @XStreamAlias(RDMFieldDictionaryConstants.PMA_150D)
    private BigDecimal priceMovingAverages150D = null;

    @XStreamAlias(RDMFieldDictionaryConstants.PMA_200D)
    private BigDecimal priceMovingAverages200D = null;

    @XStreamAlias(RDMFieldDictionaryConstants.VMA_10D)
    private BigInteger volumeMovingAverages10D = null;

    @XStreamAlias(RDMFieldDictionaryConstants.VMA_25D)
    private BigInteger volumeMovingAverages25D = null;

    @XStreamAlias(RDMFieldDictionaryConstants.VMA_50D)
    private BigInteger volumeMovingAverages50D = null;

    @XStreamAlias(RDMFieldDictionaryConstants.OPN_NETCH)
    private BigDecimal openPriceNetChange = null;

    @XStreamAlias(RDMFieldDictionaryConstants.CASH_EXDIV)
    private BigDecimal latestReportedCashDividend = null;

    /**
     * Enum
     */
    @XStreamAlias(RDMFieldDictionaryConstants.MKT_VAL_SC)
    private String marketValueScalingFactor = null;

    @XStreamAlias(RDMFieldDictionaryConstants.CASH_EXDAT)
    private Long exDividendTradeDate = null;

    /**
     * Binary
     */
    @XStreamAlias(RDMFieldDictionaryConstants.PREV_DISP)
    private Integer previousDisplayTemplate = null;

    /**
     * Enum
     */
    @XStreamAlias(RDMFieldDictionaryConstants.PRC_QL3)
    private String extendedPriceQualifierFid = null;

    /**
     * Enum
     */
    @XStreamAlias(RDMFieldDictionaryConstants.MPV)
    private String minimumPriceMovement = null;

    @XStreamAlias(RDMFieldDictionaryConstants.OFF_CLOSE)
    private BigDecimal officialClosingPrice = null;

    @XStreamAlias(RDMFieldDictionaryConstants.QUOTE_DATE)
    private Long quoteDate = null;

    @XStreamAlias(RDMFieldDictionaryConstants.VWAP)
    private BigDecimal volumeWeightedAveragePrice = null;

//    @XStreamAlias(PROV_SYMB)
//    private String providerSymbol = null;

    @XStreamAlias(RDMFieldDictionaryConstants.BID_ASK_DT)
    private Long bidAskDate = null;

    /**
     * International Security Identification Number code (ISIN).
     */
    @XStreamAlias(RDMFieldDictionaryConstants.ISIN_CODE)
    private String isinCode = null;

//    @XStreamAlias(MNEMONIC)
//    private String exchangeId = null;

    @XStreamAlias(RDMFieldDictionaryConstants.RTR_OPN_PR)
    private BigDecimal rtrsOpeningPrice = null;

    @XStreamAlias(RDMFieldDictionaryConstants.SEDOL)
    private String sedolCode = null;

    @XStreamAlias(RDMFieldDictionaryConstants.MKT_SEGMNT)
    private String marketSegment = null;

    /**
     * TODO: This is an Int/UINT64 -- need to review the conversion.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.TRDTIM_MS)
    private Long regularTradesTimeMillis = null;

    /**
     * TODO: This is an Int/UINT64 -- need to review the conversion.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.SALTIM_MS)
    private Long allTradesTimeMillis = null;

    /**
     * TODO: This is an Int/UINT64 -- need to review the conversion.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.QUOTIM_MS)
    private Long quoteTimeMillis = null;

    /**
     * @deprecated Convert type to Long.
     * @TODO: Convert type to Long.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.TIMCOR_MS)
    private BigInteger correctionTimeMillis = null;

    @XStreamAlias(RDMFieldDictionaryConstants.FIN_STATUS)
    private String financialStatusIndicator = null;

    @XStreamAlias(RDMFieldDictionaryConstants.LS_SUBIND)
    private String lastTradeSubMarketIndicator = null;

    @XStreamAlias(RDMFieldDictionaryConstants.IRG_SUBIND)
    private String irgPriceSubmarketIndicator = null;

    @XStreamAlias(RDMFieldDictionaryConstants.ACVOL_SC)
    private String volumeScaling = null;

    @XStreamAlias(RDMFieldDictionaryConstants.EXCHCODE)
    private String exchangeCode = null;

    @XStreamAlias(RDMFieldDictionaryConstants.ODD_ASK)
    private BigDecimal oddBestAsk = null;

    @XStreamAlias(RDMFieldDictionaryConstants.ODD_ASKSIZ)
    private BigInteger oddBestAskSize = null;

    @XStreamAlias(RDMFieldDictionaryConstants.ODD_BID)
    private BigDecimal oddBestBid = null;

    @XStreamAlias(RDMFieldDictionaryConstants.ODD_BIDSIZ)
    private BigInteger oddBestBidSize = null;

    @XStreamAlias(RDMFieldDictionaryConstants.ROUND_VOL)
    private BigInteger roundVolume = null;

    @XStreamAlias(RDMFieldDictionaryConstants.ORGID)
    private BigInteger organizationId = null;

    @XStreamAlias(RDMFieldDictionaryConstants.PR_FREQ)
    private String priceUpdateFrequency = null;

    /**
     * Reuters Classification Scheme
     */
    @XStreamAlias(RDMFieldDictionaryConstants.RCS_AS_CLA)
    private String rcsAssetClassification = null;

    @XStreamAlias(RDMFieldDictionaryConstants.UNDR_INDEX)
    private String underlyingIndex = null;

    @XStreamAlias(RDMFieldDictionaryConstants.FUTURES)
    private String futuresChainRic = null;

    @XStreamAlias(RDMFieldDictionaryConstants.OPTIONS)
    private String optionsChainRic = null;

    @XStreamAlias(RDMFieldDictionaryConstants.STRIKES)
    private String strikesCoverage = null;

    @XStreamAlias(RDMFieldDictionaryConstants.NEWSTM_MS)
    private BigInteger newsTimeMillis = null;

    @XStreamAlias(RDMFieldDictionaryConstants.TRD_THRU_X)
    private String tradeThroughExemptFlags = null;

    @XStreamAlias(RDMFieldDictionaryConstants.FIN_ST_IND)
    private String companyComplianceStatus = null;
    
    @XStreamAlias(RDMFieldDictionaryConstants.IRG_SMKTID)
    private String irgSubMarketCenterId = null;
    
    @XStreamAlias(RDMFieldDictionaryConstants.SUB_MKT_ID)
    private String subMarketCenterId = null;

    /**
     * Docs say markets -- so we're using ids.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.ACT_DOM_EX)
    private String activeDomesticExchangeIds = null;

    @XStreamAlias(RDMFieldDictionaryConstants.ACT_OTH_EX)
    private String activeOtherExchangeIds = null;
    
    @XStreamAlias(RDMFieldDictionaryConstants.TRD_QUAL_2)
    private String tradePriceQualifier2 = null;
    
    @XStreamAlias(RDMFieldDictionaryConstants.CP_EFF_DAT)
    private Long latestCapitalChangeEffectiveDate = null;

    /**
     * @deprecated The name of this property is a bit vague as this is not the
     *  bid id, it is the id of the market participant who is making the bid.
     */
    @XStreamAlias(RDMFieldDictionaryConstants.BID_MMID1)
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
    @XStreamAlias(RDMFieldDictionaryConstants.ASK_MMID1)
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
    @XStreamAlias(RDMFieldDictionaryConstants.PUT_CALL)
    private String putCall = null;

    /**
     * Implied volatility of ASK price.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(RDMFieldDictionaryConstants.IMP_VOLTA)
    private BigDecimal impliedVolatilitytOfAskPrice = null;

    /**
     * Implied volatility of BID price.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(RDMFieldDictionaryConstants.IMP_VOLTB)
    private BigDecimal impliedVolatilitytOfBidPrice = null;

    /**
     * Open interest. The total number of option or futures contracts that
     * have not been closed or in the case of commodities liquidated or
     * offset by delivery.
     *
     * INTEGER/REAL64
     */
    @XStreamAlias(RDMFieldDictionaryConstants.OPINT_1)
    private BigInteger openInterest = null;

    /**
     * Open interest net change. The difference between the current and
     * previous trading day open interest.
     *
     * REAL64
     */
    @XStreamAlias(RDMFieldDictionaryConstants.OPINTNC)
    private BigInteger openInterestNetChange = null;

    /**
     * Strike price; the price at which an option may be exercised.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(RDMFieldDictionaryConstants.STRIKE_PRC)
    private BigDecimal strikePrice = null;

    /**
     * Contract month
     *
     * ALPHANUMERIC/RMTES_STRING
     */
    @XStreamAlias(RDMFieldDictionaryConstants.CONTR_MNTH)
    private String contractMonth = null;

    /**
     * Lot size units.
     * 
     * ENUMERATED/ENUM
     */
    @XStreamAlias(RDMFieldDictionaryConstants.LOTSZUNITS)
    private String lotSizeUnits = null;

    /**
     * Open ask price.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(RDMFieldDictionaryConstants.OPEN_ASK)
    private BigDecimal openAskPrice = null;

    /**
     * The date on which the future, option or warrant expires.
     *
     * DATE/DATE
     */
    @XStreamAlias(RDMFieldDictionaryConstants.EXPIR_DATE)
    private Long expiryDate = null;

    /**
     * Settlement price. The official closing price of a futures or option
     * contract set by the clearing house at the end of the trading day.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(RDMFieldDictionaryConstants.SETTLE)
    private BigDecimal settlementPrice = null;

    @Override
    public String getRic() {
        return ric;
    }

    public void setRic(String ric) {
        this.ric = ric;
    }

    /**
     * The date of the settlement price held in the SETTLE field.
     *
     * DATE/DATE
     */
    @XStreamAlias(RDMFieldDictionaryConstants.SETTLEDATE)
    private Long settleDate = null;

    @UsingKey(type=RDMFieldDictionaryConstants.RDN_EXCHID)
    public String getIdnExchangeId() {
        return idnExchangeId;
    }

    public static final String IDN_EXCHANGE_ID = "idnExchangeId";

    /**
     * @todo Test this.
     */
    @RFAType(type=RDMFieldDictionaryConstants.RDN_EXCHID)
    @Adapt(using=OMMEnumAdapter.class)
    public void setIdnExchangeId(@Changeable(MarketPrice.IDN_EXCHANGE_ID) String idnExchangeId) {
        this.idnExchangeId = idnExchangeId;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BID)
    public BigDecimal getBid() {
        return bid;
    }

    public static final String BID = "bid";

    @RFAType(type=RDMFieldDictionaryConstants.BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid(@Changeable(BID) BigDecimal bid) {
        this.bid = bid;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BID_1)
    public BigDecimal getBid1() {
        return bid1;
    }

    public static final String BID_1 = "bid1";

    @RFAType(type=RDMFieldDictionaryConstants.BID_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid1(@Changeable(MarketPrice.BID_1) BigDecimal bid1) {
        this.bid1 = bid1;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BID_2)
    public BigDecimal getBid2() {
        return bid2;
    }

    public static final String BID_2 = "bid2";

    @RFAType(type=RDMFieldDictionaryConstants.BID_2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid2(@Changeable(MarketPrice.BID_2) BigDecimal bid2) {
        this.bid2 = bid2;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ASK)
    public BigDecimal getAsk() {
        return ask;
    }

    public static final String ASK = "ask";

    /**
     * REAL64
     *
     * @param ask
     */
    @RFAType(type=RDMFieldDictionaryConstants.ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAsk(@Changeable(MarketPrice.ASK) BigDecimal ask) {
        this.ask = ask;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ASK_1)
    public BigDecimal getAsk1() {
        return ask1;
    }

    public static final String ASK1 = "ask1";

    /**
     * REAL64
     *
     * @param ask1
     */
    @RFAType(type=RDMFieldDictionaryConstants.ASK_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAsk1(@Changeable(MarketPrice.ASK1) BigDecimal ask1) {
        this.ask1 = ask1;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ASK_2)
    public BigDecimal getAsk2() {
        return ask2;
    }

    public static final String ASK2 = "ask2";

    /**
     * REAL64
     *
     * @param ask2
     */
    @RFAType(type=RDMFieldDictionaryConstants.ASK_2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAsk2(@Changeable(MarketPrice.ASK2) BigDecimal ask2) {
        this.ask2 = ask2;
    }

    public BigInteger getBidSize() {
        return bidSize;
    }

    public static final String BID_SIZE = "bidSize";

    /**
     * REAL64
     *
     * @param bidSize
     */
    @RFAType(type=RDMFieldDictionaryConstants.BIDSIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBidSize(@Changeable(MarketPrice.BID_SIZE) BigInteger bidSize) {
        this.bidSize = bidSize;
    }

    public BigInteger getAskSize() {
        return askSize;
    }

    public static final String ASK_SIZE = "askSize";

    /**
     * REAL64
     *
     * @param askSize
     */
    @RFAType(type=RDMFieldDictionaryConstants.ASKSIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAskSize(@Changeable(MarketPrice.ASK_SIZE) BigInteger askSize) {
        this.askSize = askSize;
    }

    public BigDecimal getLast() {
        return last;
    }

    public static final String LAST = "last";

    /**
     * REAL64
     *
     * @param last
     */
    @RFAType(type=RDMFieldDictionaryConstants.TRDPRC_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast(@Changeable(MarketPrice.LAST) BigDecimal last) {
        this.last = last;
    }

    public BigDecimal getLast1() {
        return last1;
    }

    public static final String LAST_1 = "last1";

    /**
     * REAL64
     *
     * @param last1
     */
    @RFAType(type=RDMFieldDictionaryConstants.TRDPRC_2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast1(@Changeable(MarketPrice.LAST_1) BigDecimal last1) {
        this.last1 = last1;
    }

    public BigDecimal getLast2() {
        return last2;
    }

    public static final String LAST_2 = "last2";

    /**
     * REAL64
     *
     * @param last2
     * 
     * @todo Verify that TRDPRC_3 actually is for last2 and assuming it is, add clarifying documentation.
     */
    @RFAType(type=RDMFieldDictionaryConstants.TRDPRC_3)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast2(@Changeable(MarketPrice.LAST_2) BigDecimal last2) {
        this.last2 = last2;
    }

    public BigDecimal getLast3() {
        return last3;
    }

    public static final String LAST_3 = "last3";

    /**
     * REAL64
     *
     * @param last3
     */
    @RFAType(type=RDMFieldDictionaryConstants.TRDPRC_4)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast3(@Changeable(MarketPrice.LAST_3) BigDecimal last3) {
        this.last3 = last3;
    }

    public BigDecimal getLast4() {
        return last4;
    }

    public static final String LAST_4 = "last4";

    /**
     * REAL64
     *
     * @param last4
     */
    @RFAType(type=RDMFieldDictionaryConstants.TRDPRC_5)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast4(@Changeable(MarketPrice.LAST_4) BigDecimal last4) {
        this.last4 = last4;
    }

    public BigInteger getDisplayTemplate() {
        return displayTemplate;
    }

    public static final String DISPLAY_TEMPLATE = "displayTemplate";

    /**
     * UINT32
     *
     * @param displayTemplate aka rdnDisplay
     */
    @RFAType(type=RDMFieldDictionaryConstants.RDNDISPLAY)
    @Adapt(using=OMMNumericAdapter.class)
    public void setDisplayTemplate(@Changeable(MarketPrice.DISPLAY_TEMPLATE) BigInteger displayTemplate) {
        this.displayTemplate = displayTemplate;
    }

    public BigDecimal getNetChange() {
        return netChange;
    }

    public static final String NET_CHANGE = "netChange";

    @RFAType(type=RDMFieldDictionaryConstants.NETCHNG_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setNetChange(@Changeable(MarketPrice.NET_CHANGE) BigDecimal netChange) {
        this.netChange = netChange;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.HIGH_1)
    public BigDecimal getTodaysHigh() {
        return todaysHigh;
    }

    public static final String TODAYS_HIGH = "todaysHigh";

    @RFAType(type=RDMFieldDictionaryConstants.HIGH_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysHigh(@Changeable(MarketPrice.TODAYS_HIGH) BigDecimal todaysHigh) {
        this.todaysHigh = todaysHigh;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.LOW_1)
    public BigDecimal getTodaysLow() {
        return todaysLow;
    }

    public static final String TODAYS_LOW = "todaysLow";

    /**
     * REAL64
     *
     * @param todaysLow
     */
    @RFAType(type=RDMFieldDictionaryConstants.LOW_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysLow(@Changeable(MarketPrice.TODAYS_LOW) BigDecimal todaysLow) {
        this.todaysLow = todaysLow;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PRCTCK_1)
    public Integer getTickArrow() {
        return tickArrow;
    }

    public static final String TICK_ARROW = "tickArrow";

    @RFAType(type=RDMFieldDictionaryConstants.PRCTCK_1)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTickArrow(@Changeable(MarketPrice.TICK_ARROW) Integer tickArrow) {
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

    @UsingKey(type=RDMFieldDictionaryConstants.TRADE_DATE)
    public Long getTradeDateMillis() {
        return tradeDateMillis;
    }

    public static final String TRADE_DATE_MILLIS = "tradeDateMillis";

    @RFAType(type=RDMFieldDictionaryConstants.TRADE_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTradeDateMillis(@Changeable(MarketPrice.TRADE_DATE_MILLIS) Long tradeDateMillis) {
        this.tradeDateMillis = tradeDateMillis;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TRDTIM_1)
    public Long getTradeTimeMillis() {
        return tradeTimeMillis;
    }

    public static final String TRADE_TIME_MILLIS = "tradeTimeMillis";

    @RFAType(type=RDMFieldDictionaryConstants.TRDTIM_1)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTradeTimeMillis(@Changeable(MarketPrice.TRADE_TIME_MILLIS) Long tradeTimeMillis) {
        this.tradeTimeMillis = tradeTimeMillis;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.OPEN_PRC)
    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public static final String OPEN_PRICE = "openPrice";

    @RFAType(type=RDMFieldDictionaryConstants.OPEN_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenPrice(@Changeable(MarketPrice.OPEN_PRICE) BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.HST_CLOSE)
    public BigDecimal getHistoricClose() {
        return historicClose;
    }

    public static final String HISTORIC_CLOSE = "historicClose";

    @RFAType(type=RDMFieldDictionaryConstants.HST_CLOSE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setHistoricClose(@Changeable(MarketPrice.HISTORIC_CLOSE) BigDecimal historicClose) {
        this.historicClose = historicClose;
    }

    @UsingKey(type=NEWS)
    public String getNews() {
        return news;
    }

    public static final String NEWS = "news";

    @RFAType(type=RDMFieldDictionaryConstants.NEWS)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setNews(@Changeable(MarketPrice.NEWS) String news) {
        this.news = news;
    }

    @UsingKey(type=NEWS_TIME)
    public Long getNewsTime() {
        return newsTime;
    }

    public static final String NEWS_TIME = "newsTime";

    @RFAType(type=RDMFieldDictionaryConstants.NEWS_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setNewsTime(@Changeable(MarketPrice.NEWS_TIME) Long newsTime) {
        this.newsTime = newsTime;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ACVOL_1)
    public BigInteger getVolumeAccumulated() {
        return volumeAccumulated;
    }

    public static final String VOLUME_ACCUMULATED = "volumeAccumulated";

    @RFAType(type=RDMFieldDictionaryConstants.ACVOL_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeAccumulated(@Changeable(MarketPrice.VOLUME_ACCUMULATED) BigInteger volumeAccumulated) {
        this.volumeAccumulated = volumeAccumulated;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.EARNINGS)
    public BigDecimal getEarnings() {
        return earnings;
    }

    public static final String EARNINGS = "earnings";

    @RFAType(type=RDMFieldDictionaryConstants.EARNINGS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setEarnings(@Changeable(MarketPrice.EARNINGS) BigDecimal earnings) {
        this.earnings = earnings;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YIELD)
    public BigDecimal getYield() {
        return yield;
    }

    public static final String YIELD = "yield";

    @RFAType(type=RDMFieldDictionaryConstants.YIELD)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYield(@Changeable(MarketPrice.YIELD) BigDecimal yield) {
        this.yield = yield;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PERATIO)
    public BigDecimal getPriceToEarningsRatio() {
        return priceToEarningsRatio;
    }

    public static final String PRICE_TO_EARNINGS_RATIO = "priceToEarningsRatio";

    @RFAType(type=RDMFieldDictionaryConstants.PERATIO)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceToEarningsRatio(
        @Changeable(MarketPrice.PRICE_TO_EARNINGS_RATIO) BigDecimal priceToEarningsRatio) {
        this.priceToEarningsRatio = priceToEarningsRatio;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.DIVIDENDTP)
    public String getDividendType() {
        return dividendType;
    }

    public static final String DIVIDEND_TYPE = "dividendType";

    @RFAType(type=RDMFieldDictionaryConstants.DIVIDENDTP)
    @Adapt(using=OMMEnumAdapter.class)
    public void setDividendType(@Changeable(MarketPrice.DIVIDEND_TYPE) String dividendType) {
        this.dividendType = dividendType;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.DIVPAYDATE)
    public Long getDividendPayDate() {
        return dividendPayDate;
    }

    public static final String DIVIDEND_PAY_DATE = "dividendPayDate";

    @RFAType(type=RDMFieldDictionaryConstants.DIVPAYDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setDividendPayDate(@Changeable(MarketPrice.DIVIDEND_PAY_DATE) Long dividendPayDate) {
        this.dividendPayDate = dividendPayDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.EXDIVDATE)
    public Long getExDividendDate() {
        return exDividendDate;
    }

    public static final String EX_DIVIDEND_DATE = "exDividendDate";

    @RFAType(type=RDMFieldDictionaryConstants.EXDIVDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExDividendDate(@Changeable(MarketPrice.EX_DIVIDEND_DATE) Long exDividendDate) {
        this.exDividendDate = exDividendDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.CTS_QUAL)
    public String getTradePriceQualifier() {
        return tradePriceQualifier;
    }

    public static final String TRADE_PRICE_QUALIFIER = "tradePriceQualifier";

    @RFAType(type=RDMFieldDictionaryConstants.CTS_QUAL)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradePriceQualifier(@Changeable(MarketPrice.TRADE_PRICE_QUALIFIER) String tradePriceQualifier) {
        this.tradePriceQualifier = tradePriceQualifier;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BLKCOUNT)
    public BigInteger getBlockCount() {
        return blockCount;
    }

    public static final String BLOCK_COUNT = "blockCount";

    @RFAType(type=RDMFieldDictionaryConstants.BLKCOUNT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockCount(@Changeable(MarketPrice.BLOCK_COUNT) BigInteger blockCount) {
        this.blockCount = blockCount;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BLKVOLUM)
    public BigInteger getBlockVolume() {
        return blockVolume;
    }

    public static final String BLOCK_VOLUME = "blockVolume";

    @RFAType(type=RDMFieldDictionaryConstants.BLKVOLUM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockVolume(@Changeable(MarketPrice.BLOCK_VOLUME) BigInteger blockVolume) {
        this.blockVolume = blockVolume;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TRDXID_1)
    public String getTradeExchangeId() {
        return tradeExchangeId;
    }

    public static final String TRADE_EXCHANGE_ID = "tradeExchangeId";

    @RFAType(type=RDMFieldDictionaryConstants.TRDXID_1)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradeExchangeId(@Changeable(MarketPrice.TRADE_EXCHANGE_ID) String tradeExchangeId) {
        this.tradeExchangeId = tradeExchangeId;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TRD_UNITS)
    public String getTradingUnits() {
        return tradingUnits;
    }

    public static final String TRADING_UNITS = "tradingUnits";

    @RFAType(type=RDMFieldDictionaryConstants.TRD_UNITS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradingUnits(@Changeable(MarketPrice.TRADING_UNITS) String tradingUnits) {
        this.tradingUnits = tradingUnits;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.LOT_SIZE)
    public BigInteger getLotSize() {
        return lotSize;
    }

    public static final String LOT_SIZE = "lotSize";

    @RFAType(type=RDMFieldDictionaryConstants.LOT_SIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLotSize(@Changeable(MarketPrice.LOT_SIZE) BigInteger lotSize) {
        this.lotSize = lotSize;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PCTCHNG)
    public BigDecimal getPercentChange() {
        return percentChange;
    }

    public static final String PERCENTAGE_CHANGE = "percentChange";

    @RFAType(type=RDMFieldDictionaryConstants.PCTCHNG)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPercentChange(@Changeable(MarketPrice.PERCENTAGE_CHANGE) BigDecimal percentChange) {
        this.percentChange = percentChange;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.OPEN_BID)
    public BigDecimal getOpenBid() {
        return openBid;
    }

    public static final String OPEN_BID = "openBid";

    @RFAType(type=RDMFieldDictionaryConstants.OPEN_BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenBid(@Changeable(MarketPrice.OPEN_BID) BigDecimal openBid) {
        this.openBid = openBid;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.DJTIME)
    public Long getLatestDowJonesNewsStoryTime() {
        return latestDowJonesNewsStoryTime;
    }

    public static final String LATEST_DOW_JONES_NEWS_STORY_TIME = "latestDowJonesNewsStoryTime";

    @RFAType(type=RDMFieldDictionaryConstants.DJTIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLatestDowJonesNewsStoryTime(
        @Changeable(MarketPrice.LATEST_DOW_JONES_NEWS_STORY_TIME) Long latestDowJonesNewsStoryTime) {
        this.latestDowJonesNewsStoryTime = latestDowJonesNewsStoryTime;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.CLOSE_BID)
    public BigDecimal getCloseBid() {
        return closeBid;
    }

    public static final String CLOSE_BID = "closeBid";

    @RFAType(type=RDMFieldDictionaryConstants.CLOSE_BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCloseBid(@Changeable(MarketPrice.CLOSE_BID) BigDecimal closeBid) {
        this.closeBid = closeBid;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.CLOSE_ASK)
    public BigDecimal getCloseAsk() {
        return closeAsk;
    }

    public static final String CLOSE_ASK = "closeAsk";

    @RFAType(type=RDMFieldDictionaryConstants.CLOSE_ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCloseAsk(@Changeable(MarketPrice.CLOSE_ASK) BigDecimal closeAsk) {
        this.closeAsk = closeAsk;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.DIVIDEND)
    public BigDecimal getDividend() {
        return dividend;
    }

    public static final String DIVIDEND = "dividend";

    @RFAType(type=RDMFieldDictionaryConstants.DIVIDEND)
    @Adapt(using=OMMNumericAdapter.class)
    public void setDividend(@Changeable(MarketPrice.DIVIDEND) BigDecimal dividend) {
        this.dividend = dividend;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.NUM_MOVES)
    public BigInteger getTotalTradesToday() {
        return totalTradesToday;
    }

    public static final String TOTAL_TRADES_TODAY = "totalTradesToday";

    @RFAType(type=RDMFieldDictionaryConstants.NUM_MOVES)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTotalTradesToday(@Changeable(MarketPrice.TOTAL_TRADES_TODAY) BigInteger totalTradesToday) {
        this.totalTradesToday = totalTradesToday;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.OFFCL_CODE)
    public String getOfficialCode() {
        return officialCode;
    }

    public static final String OFFICIAL_CODE = "officialCode";

    @RFAType(type=RDMFieldDictionaryConstants.OFFCL_CODE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOfficialCode(@Changeable(MarketPrice.OFFICIAL_CODE) String officialCode) {
        this.officialCode = officialCode;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.HSTCLSDATE)
    public Long getHistoricCloseDate() {
        return historicCloseDate;
    }

    public static final String HISTORIC_CLOSE_DATE = "historicCloseDate";

    @RFAType(type=RDMFieldDictionaryConstants.HSTCLSDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setHistoricCloseDate(@Changeable(MarketPrice.HISTORIC_CLOSE_DATE) Long historicCloseDate) {
        this.historicCloseDate = historicCloseDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YRHIGH)
    public BigDecimal getYearHigh() {
        return yearHigh;
    }

    public static final String YEAR_HIGH = "yearHigh";

    @RFAType(type=RDMFieldDictionaryConstants.YRHIGH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearHigh(@Changeable(MarketPrice.YEAR_HIGH) BigDecimal yearHigh) {
        this.yearHigh = yearHigh;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YRLOW)
    public BigDecimal getYearLow() {
        return yearLow;
    }

    public static final String YEAR_LOW = "yearLow";

    @RFAType(type=RDMFieldDictionaryConstants.YRLOW)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearLow(@Changeable(MarketPrice.YEAR_LOW) BigDecimal yearLow) {
        this.yearLow = yearLow;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TURNOVER)
    public BigDecimal getTurnover() {
        return turnover;
    }

    public static final String TURNOVER = "turnover";

    @RFAType(type=RDMFieldDictionaryConstants.TURNOVER)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTurnover(@Changeable(MarketPrice.TURNOVER) BigDecimal turnover) {
        this.turnover = turnover;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BOND_TYPE)
    public String getBondType() {
        return bondType;
    }

    public static final String BOND_TYPE = "bondType";

    @RFAType(type=RDMFieldDictionaryConstants.BOND_TYPE)
    @Adapt(using=OMMEnumAdapter.class)
    public void setBondType(@Changeable(MarketPrice.BOND_TYPE) String bondType) {
        this.bondType = bondType;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BCKGRNDPAG)
    public String getBackgroundPage() {
        return backgroundPage;
    }

    public static final String BACKGROUND_PAGE = "backgroundPage";

    @RFAType(type=RDMFieldDictionaryConstants.BCKGRNDPAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setBackgroundPage(@Changeable(MarketPrice.BACKGROUND_PAGE) String backgroundPage) {
        this.backgroundPage = backgroundPage;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YCHIGH_IND)
    public String getYearOrContractHighIndicator() {
        return yearOrContractHighIndicator;
    }

    public static final String YEAR_OR_CONTRACT_HIGH_INDICATOR = "yearOrContractHighIndicator";

    /**
     * Requires visual inspection still as I have not seen this value set to
     * anything other than "   ".
     *
     * @param yearOrContractHighIndicator
     */
    @RFAType(type=RDMFieldDictionaryConstants.YCHIGH_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearOrContractHighIndicator(
        @Changeable(MarketPrice.YEAR_OR_CONTRACT_HIGH_INDICATOR) String yearOrContractHighIndicator) {
        this.yearOrContractHighIndicator = yearOrContractHighIndicator;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YCLOW_IND)
    public String getYearOrContractLowIndicator() {
        return yearOrContractLowIndicator;
    }

    public static final String YEAR_OR_CONTRACT_LOW_INDICATOR = "yearOrContractLowIndicator";

    /**
     * Requires visual inspection still as I have not seen this value set to
     * anything other than "   ".
     *
     * @param yearOrContractLowIndicator
     */
    @RFAType(type=RDMFieldDictionaryConstants.YCLOW_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearOrContractLowIndicator(
        @Changeable(MarketPrice.YEAR_OR_CONTRACT_LOW_INDICATOR) String yearOrContractLowIndicator) {
        this.yearOrContractLowIndicator = yearOrContractLowIndicator;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BID_NET_CH)
    public BigDecimal getBidNetChange() {
        return bidNetChange;
    }

    public static final String BID_NET_CHANGE = "bidNetChange";

    @RFAType(type=RDMFieldDictionaryConstants.BID_NET_CH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBidNetChange(@Changeable(MarketPrice.BID_NET_CHANGE) BigDecimal bidNetChange) {
        this.bidNetChange = bidNetChange;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BID_TICK_1)
    public String getBidTick1() {
        return bidTick1;
    }

    public static final String BID_TICK_1 = "bidTick1";

    @RFAType(type=RDMFieldDictionaryConstants.BID_TICK_1)
    @Adapt(using=OMMEnumAdapter.class)
    public void setBidTick1(@Changeable(MarketPrice.BID_TICK_1) String bidTick1) {
        this.bidTick1 = bidTick1;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.CUM_EX_MKR)
    public String getCumExMarker() {
        return cumExMarker;
    }

    public static final String CUM_EX_MARKER = "cumExMarker";

    @RFAType(type=RDMFieldDictionaryConstants.CUM_EX_MKR)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCumExMarker(@Changeable(CUM_EX_MARKER) String cumExMarker) {
        this.cumExMarker = cumExMarker;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PRC_QL_CD)
    public String getPriceCode() {
        return priceCode;
    }

    public static final String PRICE_CODE = "priceCode";

    @RFAType(type=RDMFieldDictionaryConstants.PRC_QL_CD)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceCode(@Changeable(PRICE_CODE) String priceCode) {
        this.priceCode = priceCode;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.NASDSTATUS)
    public String getNasdStatus() {
        return nasdStatus;
    }

    public static final String NASD_STATUS = "nasdStatus";

    @RFAType(type=RDMFieldDictionaryConstants.NASDSTATUS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setNasdStatus(@Changeable(NASD_STATUS) String nasdStatus) {
        this.nasdStatus = nasdStatus;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PRC_QL2)
    public String getPriceCode2() {
        return priceCode2;
    }

    public static final String PRICE_CODE_2 = "priceCode2";

    @RFAType(type=RDMFieldDictionaryConstants.PRC_QL2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceCode2(@Changeable(PRICE_CODE_2) String priceCode2) {
        this.priceCode2 = priceCode2;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TRDVOL_1)
    public BigInteger getTradeVolume() {
        return tradeVolume;
    }

    public static final String TRADE_VOLUME = "tradeVolume";

    @RFAType(type=RDMFieldDictionaryConstants.TRDVOL_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTradeVolume(@Changeable(TRADE_VOLUME) BigInteger tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BID_HIGH_1)
    public BigDecimal getTodaysHighBid() {
        return todaysHighBid;
    }

    public static final String TODAYS_HIGH_BID = "todaysHighBid";

    @RFAType(type=RDMFieldDictionaryConstants.BID_HIGH_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysHighBid(@Changeable(TODAYS_HIGH_BID) BigDecimal todaysHighBid) {
        this.todaysHighBid = todaysHighBid;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BID_LOW_1)
    public BigDecimal getTodaysLowBid() {
        return todaysLowBid;
    }

    public static final String TODAYS_LOW_BID = "todaysLowBid";

    @RFAType(type=RDMFieldDictionaryConstants.BID_LOW_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysLowBid(@Changeable(TODAYS_LOW_BID) BigDecimal todaysLowBid) {
        this.todaysLowBid = todaysLowBid;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YRBIDHIGH)
    public BigDecimal getYearHighBid() {
        return yearHighBid;
    }

    public static final String YEAR_HIGH_BID = "yearHighBid";

    @RFAType(type=RDMFieldDictionaryConstants.YRBIDHIGH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearHighBid(@Changeable(YEAR_HIGH_BID) BigDecimal yearHighBid) {
        this.yearHighBid = yearHighBid;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YRBIDLOW)
    public BigDecimal getYearLowBid() {
        return yearLowBid;
    }

    public static final String YEAR_LOW_BID = "yearLowBid";

    @RFAType(type=RDMFieldDictionaryConstants.YRBIDLOW)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearLowBid(@Changeable(YEAR_LOW_BID) BigDecimal yearLowBid) {
        this.yearLowBid = yearLowBid;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.HST_CLSBID)
    public BigDecimal getHistoricalClosingBid() {
        return historicalClosingBid;
    }

    public static final String HISTORICAL_CLOSING_BID = "historicalClosingBid";

    @RFAType(type=RDMFieldDictionaryConstants.HST_CLSBID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setHistoricalClosingBid(@Changeable(HISTORICAL_CLOSING_BID) BigDecimal historicalClosingBid) {
        this.historicalClosingBid = historicalClosingBid;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.HSTCLBDDAT)
    public Long getHistoricalClosingBidDate() {
        return historicalClosingBidDate;
    }

    public static final String HISTORIC_CLOSING_BID_DATE = "historicalClosingBidDate";

    @RFAType(type=RDMFieldDictionaryConstants.HSTCLBDDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setHistoricalClosingBidDate(
        @Changeable(MarketPrice.HISTORIC_CLOSING_BID_DATE) Long historicalClosingBidDate) {
        this.historicalClosingBidDate = historicalClosingBidDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YRBDHI_IND)
    public String getYearBidHigh() {
        return yearBidHigh;
    }

    public static final String YEAR_BID_HIGH = "yearBidHigh";

    @RFAType(type=RDMFieldDictionaryConstants.YRBDHI_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearBidHigh(@Changeable(MarketPrice.YEAR_BID_HIGH) String yearBidHigh) {
        this.yearBidHigh = yearBidHigh;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YRBDLO_IND)
    public String getYearBidLow() {
        return yearBidLow;
    }

    public static final String YEAR_BID_LOW = "yearBidLow";

    @RFAType(type=RDMFieldDictionaryConstants.YRBDLO_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearBidLow(@Changeable(MarketPrice.YEAR_BID_LOW) String yearBidLow) {
        this.yearBidLow = yearBidLow;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.NUM_BIDS)
    public BigInteger getNumberOfBids() {
        return numberOfBids;
    }

    public static final String NUMBER_OF_BIDS = "numberOfBids";

    @RFAType(type=RDMFieldDictionaryConstants.NUM_BIDS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setNumberOfBids(@Changeable(MarketPrice.NUMBER_OF_BIDS) BigInteger numberOfBids) {
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

    @UsingKey(type=RDMFieldDictionaryConstants.BID_MMID1)
    public String getMarketParticipantBidId() {
        return marketParticipantBidId;
    }

    public static final String MARKET_PARTICIPANT_BID_ID = "marketParticipantBidId";

    @RFAType(type=RDMFieldDictionaryConstants.BID_MMID1)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setMarketParticipantBidId(@Changeable(MARKET_PARTICIPANT_BID_ID) String marketParticipantBidId) {
        this.marketParticipantBidId = marketParticipantBidId;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ASK_MMID1)
    public String getMarketParticipantAskId() {
        return marketParticipantAskId;
    }

    public static final String MARKET_PARTICIPANT_ASK_ID = "marketParticipantAskId";

    @RFAType(type=RDMFieldDictionaryConstants.ASK_MMID1)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setMarketParticipantAskId(@Changeable(MARKET_PARTICIPANT_ASK_ID) String marketParticipantAskId) {
        this.marketParticipantAskId = marketParticipantAskId;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.OPTION_XID)
    public String getOptionExchangeId() {
        return optionExchangeId;
    }

    public static final String OPTION_EXCHANGE_ID = "optionExchangeId";

    @RFAType(type=RDMFieldDictionaryConstants.OPTION_XID)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOptionExchangeId(@Changeable(OPTION_EXCHANGE_ID) String optionExchangeId) {
        this.optionExchangeId = optionExchangeId;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YRHIGHDAT)
    public Long getYearHighDate() {
        return yearHighDate;
    }

    public static final String YEAR_HIGH_DATE = "yearHighDate";

    @RFAType(type=RDMFieldDictionaryConstants.YRHIGHDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setYearHighDate(@Changeable(YEAR_HIGH_DATE) Long yearHighDate) {
        this.yearHighDate = yearHighDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YRLOWDAT)
    public Long getYearLowDate() {
        return yearLowDate;
    }

    public static final String YEAR_LOW_DATE = "yearLowDate";

    @RFAType(type=RDMFieldDictionaryConstants.YRLOWDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setYearLowDate(@Changeable(YEAR_LOW_DATE) Long yearLowDate) {
        this.yearLowDate = yearLowDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.IRGPRC)
    public BigDecimal getIrgPrice() {
        return irgPrice;
    }

    public static final String IRG_PRICE = "irgPrice";

    @RFAType(type=RDMFieldDictionaryConstants.IRGPRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgPrice(@Changeable(IRG_PRICE) BigDecimal irgPrice) {
        this.irgPrice = irgPrice;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.IRGVOL)
    public BigInteger getIrgVolume() {
        return irgVolume;
    }

    public static final String IRG_VOLUME = "irgVolume";

    @RFAType(type=RDMFieldDictionaryConstants.IRGVOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgVolume(@Changeable(IRG_VOLUME) BigInteger irgVolume) {
        this.irgVolume = irgVolume;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.IRGCOND)
    public String getIrgPriceType() {
        return irgPriceType;
    }

    public static final String IRG_PRICE_TYPE = "irgPriceType";

    @RFAType(type=RDMFieldDictionaryConstants.IRGCOND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setIrgPriceType(@Changeable(IRG_PRICE_TYPE) String irgPriceType) {
        this.irgPriceType = irgPriceType;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TIMCOR)
    public Long getPriceCorrectionTime() {
        return priceCorrectionTime;
    }

    public static final String PRICE_CORRECTION_TIME = "priceCorrectionTime";

    @RFAType(type=RDMFieldDictionaryConstants.TIMCOR)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setPriceCorrectionTime(@Changeable(PRICE_CORRECTION_TIME) Long priceCorrectionTime) {
        this.priceCorrectionTime = priceCorrectionTime;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.INSPRC)
    public BigDecimal getInsertPrice() {
        return insertPrice;
    }

    public static final String INSERT_PRICE = "insertPrice";

    @RFAType(type=RDMFieldDictionaryConstants.INSPRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setInsertPrice(@Changeable(INSERT_PRICE) BigDecimal insertPrice) {
        this.insertPrice = insertPrice;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.INSVOL)
    public BigInteger getInsertVolume() {
        return insertVolume;
    }

    public static final String INSERT_VOLUME = "insertVolume";

    @RFAType(type=RDMFieldDictionaryConstants.INSVOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setInsertVolume(@Changeable(INSERT_VOLUME) BigInteger insertVolume) {
        this.insertVolume = insertVolume;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.INSCOND)
    public String getInsertPriceType() {
        return insertPriceType;
    }

    public static final String INSERT_PRICE_TYPE = "insertPriceType";

    @RFAType(type=RDMFieldDictionaryConstants.INSCOND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setInsertPriceType(@Changeable(INSERT_PRICE_TYPE) String insertPriceType) {
        this.insertPriceType = insertPriceType;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.SALTIM)
    public Long getLastTime() {
        return lastTime;
    }

    public static final String LAST_TIME = "lastTime";

    @RFAType(type=RDMFieldDictionaryConstants.SALTIM)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLastTime(@Changeable(LAST_TIME) Long lastTime) {
        this.lastTime = lastTime;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TNOVER_SC)
    public String getTurnoverScale() {
        return turnoverScale;
    }

    public static final String TURNOVER_SCALE = "turnoverScale";

    @RFAType(type=RDMFieldDictionaryConstants.TNOVER_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTurnoverScale(@Changeable(TURNOVER_SCALE) String turnoverScale) {
        this.turnoverScale = turnoverScale;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BCAST_REF)
    public String getBroadcastXRef() {
        return broadcastXRef;
    }

    public static final String BROADCAST_X_REF = "broadcastXRef";

    @RFAType(type=RDMFieldDictionaryConstants.BCAST_REF)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setBroadcastXRef(@Changeable(BROADCAST_X_REF) String broadcastXRef) {
        this.broadcastXRef = broadcastXRef;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.CROSS_SC)
    public String getCrossRateScale() {
        return crossRateScale;
    }

    public static final String CROSS_RATE_SCALE = "crossRateScale";

    @RFAType(type=RDMFieldDictionaryConstants.CROSS_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCrossRateScale(@Changeable(CROSS_RATE_SCALE) String crossRateScale) {
        this.crossRateScale = crossRateScale;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.AMT_OS)
    public BigDecimal getAmountOutstanding() {
        return amountOutstanding;
    }

    public static final String AMOUNT_OUTSTANDING = "amountOutstanding";

    @RFAType(type=RDMFieldDictionaryConstants.AMT_OS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAmountOutstanding(@Changeable(AMOUNT_OUTSTANDING) BigDecimal amountOutstanding) {
        this.amountOutstanding = amountOutstanding;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.AMT_OS_SC)
    public String getAmountOutstandingScale() {
        return amountOutstandingScale;
    }

    public static final String AMOUNT_OUTSTANDING_SCALE = "amountOutstandingScale";

    @RFAType(type=RDMFieldDictionaryConstants.AMT_OS_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setAmountOutstandingScale(@Changeable(AMOUNT_OUTSTANDING_SCALE) String amountOutstandingScale) {
        this.amountOutstandingScale = amountOutstandingScale;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.OFF_CD_IND)
    public String getOfficialCodeIndicator() {
        return officialCodeIndicator;
    }

    public static final String OFFICIAL_CODE_INDICATOR = "officialCodeIndicator";

    @RFAType(type=RDMFieldDictionaryConstants.OFF_CD_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setOfficialCodeIndicator(@Changeable(OFFICIAL_CODE_INDICATOR) String officialCodeIndicator) {
        this.officialCodeIndicator = officialCodeIndicator;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PRC_VOLTY)
    public BigDecimal getPriceVolatility() {
        return priceVolatility;
    }

    public static final String PRICE_VOLATILITY = "priceVolatility";

    @RFAType(type=RDMFieldDictionaryConstants.PRC_VOLTY)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceVolatility(@Changeable(PRICE_VOLATILITY) BigDecimal priceVolatility) {
        this.priceVolatility = priceVolatility;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.AMT_OS_DAT)
    public Long getAmountOutstandingDate() {
        return amountOutstandingDate;
    }

    public static final String AMOUNT_OUTSTANDING_DATE = "amountOutstandingDate";

    @RFAType(type=RDMFieldDictionaryConstants.AMT_OS_DAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setAmountOutstandingDate(@Changeable(AMOUNT_OUTSTANDING_DATE) Long amountOutstandingDate) {
        this.amountOutstandingDate = amountOutstandingDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BKGD_REF)
    public String getBackgroundReference() {
        return backgroundReference;
    }

    public static final String BACKGROUND_REFERENCE = "backgroundReference";

    @RFAType(type=RDMFieldDictionaryConstants.BKGD_REF)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setBackgroundReference(@Changeable(BACKGROUND_REFERENCE) String backgroundReference) {
        this.backgroundReference = backgroundReference;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GEN_VAL1)
    public BigDecimal getGeneralPurposeValue1() {
        return generalPurposeValue1;
    }

    public static final String GENERAL_PURPOSE_VALUE_1 = "generalPurposeValue1";

    @RFAType(type=RDMFieldDictionaryConstants.GEN_VAL1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue1(@Changeable(GENERAL_PURPOSE_VALUE_1) BigDecimal generalPurposeValue1) {
        this.generalPurposeValue1 = generalPurposeValue1;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GV1_TEXT)
    public String getGeneralPurposeValue1Description() {
        return generalPurposeValue1Description;
    }

    public static final String GENERAL_PURPOSE_VALUE_1_DESCRIPTION = "generalPurposeValue1Description";

    @RFAType(type=RDMFieldDictionaryConstants.GV1_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue1Description(
        @Changeable(GENERAL_PURPOSE_VALUE_1_DESCRIPTION) String generalPurposeValue1Description) {
        this.generalPurposeValue1Description = generalPurposeValue1Description;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GEN_VAL2)
    public BigDecimal getGeneralPurposeValue2() {
        return generalPurposeValue2;
    }

    public static final String GENERAL_PURPOSE_VALUE_2 = "generalPurposeValue2";

    @RFAType(type=RDMFieldDictionaryConstants.GEN_VAL2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue2(@Changeable(GENERAL_PURPOSE_VALUE_2) BigDecimal generalPurposeValue2) {
        this.generalPurposeValue2 = generalPurposeValue2;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GV2_TEXT)
    public String getGeneralPurposeValue2Description() {
        return generalPurposeValue2Description;
    }

    public static final String GENERAL_PURPOSE_VALUE_2_DESCRIPTION = "generalPurposeValue2Description";

    @RFAType(type=RDMFieldDictionaryConstants.GV2_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue2Description(
        @Changeable(GENERAL_PURPOSE_VALUE_2_DESCRIPTION) String generalPurposeValue2Description) {
        this.generalPurposeValue2Description = generalPurposeValue2Description;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GEN_VAL3)
    public BigDecimal getGeneralPurposeValue3() {
        return generalPurposeValue3;
    }

    public static final String GENERAL_PURPOSE_VALUE_3 = "generalPurposeValue3";

    @RFAType(type=RDMFieldDictionaryConstants.GEN_VAL3)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue3(@Changeable(GENERAL_PURPOSE_VALUE_3) BigDecimal generalPurposeValue3) {
        this.generalPurposeValue3 = generalPurposeValue3;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GV3_TEXT)
    public String getGeneralPurposeValue3Description() {
        return generalPurposeValue3Description;
    }

    public static final String GENERAL_PURPOSE_VALUE_3_DESCRIPTION = "generalPurposeValue3Description";

    @RFAType(type=RDMFieldDictionaryConstants.GV3_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue3Description(
        @Changeable(GENERAL_PURPOSE_VALUE_3_DESCRIPTION) String generalPurposeValue3Description) {
        this.generalPurposeValue3Description = generalPurposeValue3Description;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GEN_VAL4)
    public BigDecimal getGeneralPurposeValue4() {
        return generalPurposeValue4;
    }

    public static final String GENERAL_PURPOSE_VALUE_4 = "generalPurposeValue4";

    @RFAType(type=RDMFieldDictionaryConstants.GEN_VAL4)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue4(@Changeable(GENERAL_PURPOSE_VALUE_4) BigDecimal generalPurposeValue4) {
        this.generalPurposeValue4 = generalPurposeValue4;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GV4_TEXT)
    public String getGeneralPurposeValue4Description() {
        return generalPurposeValue4Description;
    }

    public static final String GENERAL_PURPOSE_VALUE_4_DESCRIPTION = "generalPurposeValue4Description";

    @RFAType(type=RDMFieldDictionaryConstants.GV4_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue4Description(
        @Changeable(GENERAL_PURPOSE_VALUE_4_DESCRIPTION) String generalPurposeValue4Description) {
        this.generalPurposeValue4Description = generalPurposeValue4Description;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.SEQNUM)
    public BigInteger getSequenceNumber() {
        return sequenceNumber;
    }

    public static final String SEQUENCE_NUMBER = "sequenceNumber";

    @RFAType(type=RDMFieldDictionaryConstants.SEQNUM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSequenceNumber(@Changeable(SEQUENCE_NUMBER) BigInteger sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PRNTYP)
    public String getPrintType() {
        return printType;
    }

    public static final String PRINT_TYPE = "printType";

    @RFAType(type=RDMFieldDictionaryConstants.PRNTYP)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setPrintType(@Changeable(PRINT_TYPE) String printType) {
        this.printType = printType;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PRNTBCK)
    public BigInteger getAlteredTradeEventSequenceNumber() {
        return alteredTradeEventSequenceNumber;
    }

    public static final String ALTERED_TRADE_EVENTS_SEQUENCE_NUMBER = "alteredTradeEventSequenceNumber";

    @RFAType(type=RDMFieldDictionaryConstants.PRNTBCK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAlteredTradeEventSequenceNumber(
        @Changeable(ALTERED_TRADE_EVENTS_SEQUENCE_NUMBER) BigInteger alteredTradeEventSequenceNumber) {
        this.alteredTradeEventSequenceNumber = alteredTradeEventSequenceNumber;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.QUOTIM)
    public Long getQuoteTimeSeconds() {
        return quoteTimeSeconds;
    }

    public static final String QUOTE_TIME_SECONDS = "quoteTimeSeconds";

    @RFAType(type=RDMFieldDictionaryConstants.QUOTIM)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setQuoteTimeSeconds(@Changeable(QUOTE_TIME_SECONDS) Long quoteTimeSeconds) {
        this.quoteTimeSeconds = quoteTimeSeconds;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GV1_FLAG)
    public String getGenericFlag1() {
        return genericFlag1;
    }

    public static final String GENERIC_FLAG_1 = "genericFlag1";

    @RFAType(type=RDMFieldDictionaryConstants.GV1_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag1(@Changeable(GENERIC_FLAG_1) String genericFlag1) {
        this.genericFlag1 = genericFlag1;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GV2_FLAG)
    public String getGenericFlag2() {
        return genericFlag2;
    }

    public static final String GENERIC_FLAG_2 = "genericFlag2";

    @RFAType(type=RDMFieldDictionaryConstants.GV2_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag2(@Changeable(GENERIC_FLAG_2) String genericFlag2) {
        this.genericFlag2 = genericFlag2;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GV3_FLAG)
    public String getGenericFlag3() {
        return genericFlag3;
    }

    public static final String GENERIC_FLAG_3 = "genericFlag3";

    @RFAType(type=RDMFieldDictionaryConstants.GV3_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag3(@Changeable(GENERIC_FLAG_3) String genericFlag3) {
        this.genericFlag3 = genericFlag3;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GV4_FLAG)
    public String getGenericFlag4() {
        return genericFlag4;
    }

    public static final String GENERIC_FLAG_4 = "genericFlag4";

    @RFAType(type=RDMFieldDictionaryConstants.GV4_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag4(@Changeable(GENERIC_FLAG_4) String genericFlag4) {
        this.genericFlag4 = genericFlag4;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.OFF_CD_IN2)
    public String getUniqueInstrumentId2Source() {
        return uniqueInstrumentId2Source;
    }

    public static final String UNIQUE_INSTRUMENT_ID_2_SOURCE = "uniqueInstrumentId2Source";

    @RFAType(type=RDMFieldDictionaryConstants.OFF_CD_IN2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setUniqueInstrumentId2Source(
        @Changeable(UNIQUE_INSTRUMENT_ID_2_SOURCE) String uniqueInstrumentId2Source) {
        this.uniqueInstrumentId2Source = uniqueInstrumentId2Source;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.OFFC_CODE2)
    public String getUniqueInstrumentId2() {
        return uniqueInstrumentId2;
    }

    public static final String UNIQUE_INSTRUMENT_ID_2 = "uniqueInstrumentId2";

    @RFAType(type=RDMFieldDictionaryConstants.OFFC_CODE2)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setUniqueInstrumentId2(@Changeable(UNIQUE_INSTRUMENT_ID_2) String uniqueInstrumentId2) {
        this.uniqueInstrumentId2 = uniqueInstrumentId2;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GV1_TIME)
    public Long getTimeInSeconds1() {
        return timeInSeconds1;
    }

    public static final String TIME_IN_SECONDS_1 = "timeInSeconds1";

    @RFAType(type=RDMFieldDictionaryConstants.GV1_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTimeInSeconds1(@Changeable(TIME_IN_SECONDS_1) Long timeInSeconds1) {
        this.timeInSeconds1 = timeInSeconds1;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GV2_TIME)
    public Long getTimeInSeconds2() {
        return timeInSeconds2;
    }

    public static final String TIME_IN_SECONDS_2 = "timeInSeconds2";

    @RFAType(type=RDMFieldDictionaryConstants.GV2_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTimeInSeconds2(@Changeable(TIME_IN_SECONDS_2) Long timeInSeconds2) {
        this.timeInSeconds2 = timeInSeconds2;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.EXCHTIM)
    public Long getExchangeTime() {
        return exchangeTime;
    }

    public static final String EXCHANGE_TIME = "exchangeTime";

    @RFAType(type=RDMFieldDictionaryConstants.EXCHTIM)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExchangeTime(@Changeable(EXCHANGE_TIME) Long exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YRHI_IND)
    public String getYearHighIndicator() {
        return yearHighIndicator;
    }

    public static final String YEAR_HIGH_INDICATOR = "yearHighIndicator";

    @RFAType(type=RDMFieldDictionaryConstants.YRHI_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearHighIndicator(@Changeable(YEAR_HIGH_INDICATOR) String yearHighIndicator) {
        this.yearHighIndicator = yearHighIndicator;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YRLO_IND)
    public String getYearLowIndicator() {
        return yearLowIndicator;
    }

    public static final String YEAR_LOW_INDICATOR = "yearLowIndicator";

    @RFAType(type=RDMFieldDictionaryConstants.YRLO_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearLowIndicator(@Changeable(YEAR_LOW_INDICATOR) String yearLowIndicator) {
        this.yearLowIndicator = yearLowIndicator;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BETA_VAL)
    public BigDecimal getBetaValue() {
        return betaValue;
    }

    public static final String BETA_VALUE = "betaValue";

    @RFAType(type=RDMFieldDictionaryConstants.BETA_VAL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBetaValue(@Changeable(BETA_VALUE) BigDecimal betaValue) {
        this.betaValue = betaValue;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PREF_DISP)
    public Integer getPreferredDisplayTemplateNumber() {
        return preferredDisplayTemplateNumber;
    }

    public static final String PREFERRED_DISPLAY_TEMPLATE_NUMBER = "preferredDisplayTemplateNumber";

    @RFAType(type=RDMFieldDictionaryConstants.PREF_DISP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPreferredDisplayTemplateNumber(
        @Changeable(PREFERRED_DISPLAY_TEMPLATE_NUMBER) Integer preferredDisplayTemplateNumber) {
        this.preferredDisplayTemplateNumber = preferredDisplayTemplateNumber;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.DSPLY_NMLL)
    public String getLocalLanguageInstrumentName() {
        return localLanguageInstrumentName;
    }

    public static final String LOCAL_LANGUAGE_INSTRUMENT_NAME = "localLanguageInstrumentName";

    @RFAType(type=RDMFieldDictionaryConstants.DSPLY_NMLL)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setLocalLanguageInstrumentName(
        @Changeable(LOCAL_LANGUAGE_INSTRUMENT_NAME) String localLanguageInstrumentName) {
        this.localLanguageInstrumentName = localLanguageInstrumentName;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.VOL_X_PRC1)
    public BigDecimal getLatestTradeOrTradeTurnoverValue() {
        return latestTradeOrTradeTurnoverValue;
    }

    public static final String LATEST_TRADE_OR_TRADE_TURNOVER_VALUE = "latestTradeOrTradeTurnoverValue";

    @RFAType(type=RDMFieldDictionaryConstants.VOL_X_PRC1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLatestTradeOrTradeTurnoverValue(
        @Changeable(LATEST_TRADE_OR_TRADE_TURNOVER_VALUE) BigDecimal latestTradeOrTradeTurnoverValue) {
        this.latestTradeOrTradeTurnoverValue = latestTradeOrTradeTurnoverValue;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.DSO_ID)
    public Integer getDataSourceOwnerId() {
        return dataSourceOwnerId;
    }

    public static final String DATA_SOURCE_OWNER_ID = "dataSourceOwnerId";

    @RFAType(type=RDMFieldDictionaryConstants.DSO_ID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setDataSourceOwnerId(@Changeable(DATA_SOURCE_OWNER_ID) Integer dataSourceOwnerId) {
        this.dataSourceOwnerId = dataSourceOwnerId;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.AVERG_PRC)
    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public static final String AVERAGE_PRICE = "averagePrice";

    @RFAType(type=RDMFieldDictionaryConstants.AVERG_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAveragePrice(@Changeable(AVERAGE_PRICE) BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.UPC71_REST)
    public String getUpc71RestrictedFlag() {
        return upc71RestrictedFlag;
    }

    public static final String UPC_71_RESTRICTED_FLAG = "upc71RestrictedFlag";

    @RFAType(type=RDMFieldDictionaryConstants.UPC71_REST)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setUpc71RestrictedFlag(@Changeable(UPC_71_RESTRICTED_FLAG) String upc71RestrictedFlag) {
        this.upc71RestrictedFlag = upc71RestrictedFlag;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ADJUST_CLS)
    public BigDecimal getAdjustedClose() {
        return adjustedClose;
    }

    public static final String ADJUSTED_CLOSE = "adjustedClose";

    @RFAType(type=RDMFieldDictionaryConstants.ADJUST_CLS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAdjustedClose(@Changeable(ADJUSTED_CLOSE) BigDecimal adjustedClose) {
        this.adjustedClose = adjustedClose;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.WEIGHTING)
    public BigDecimal getWeighting() {
        return weighting;
    }

    public static final String WEIGHTING = "weighting";

    @RFAType(type=RDMFieldDictionaryConstants.WEIGHTING)
    @Adapt(using=OMMNumericAdapter.class)
    public void setWeighting(@Changeable(MarketPrice.WEIGHTING) BigDecimal weighting) {
        this.weighting = weighting;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.STOCK_TYPE)
    public String getStockType() {
        return stockType;
    }

    public static final String STOCK_TYPE = "stockType";

    @RFAType(type=RDMFieldDictionaryConstants.STOCK_TYPE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setStockType(@Changeable(MarketPrice.STOCK_TYPE) String stockType) {
        this.stockType = stockType;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.IMP_VOLT)
    public BigDecimal getImpliedVolatility() {
        return impliedVolatility;
    }

    public static final String IMPLIED_VOLATILITY = "impliedVolatility";

    @RFAType(type=RDMFieldDictionaryConstants.IMP_VOLT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setImpliedVolatility(@Changeable(MarketPrice.IMPLIED_VOLATILITY) BigDecimal impliedVolatility) {
        this.impliedVolatility = impliedVolatility;
    }

//    @UsingKey(type=MarketPriceConstants.RDN_EXCHD2)
//    public String getExchangeId2() {
//        return exchangeId2;
//    }
//
//    @RFAType(type=MarketPriceConstants.RDN_EXCHD2)
//    @Adapt(using=OMMEnumAdapter.class)
//    public void setExchangeId2(String exchangeId2) {
//        this.exchangeId2 = exchangeId2;
//    }

    @UsingKey(type=RDMFieldDictionaryConstants.CP_ADJ_FCT)
    public BigDecimal getCapitalAdjustmentFactor() {
        return capitalAdjustmentFactor;
    }

    public static final String CAPITAL_ADJUSTMENT_FACTOR = "capitalAdjustmentFactor";

    @RFAType(type=RDMFieldDictionaryConstants.CP_ADJ_FCT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCapitalAdjustmentFactor(
        @Changeable(MarketPrice.CAPITAL_ADJUSTMENT_FACTOR) BigDecimal capitalAdjustmentFactor) {
        this.capitalAdjustmentFactor = capitalAdjustmentFactor;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.CP_ADJ_DAT)
    public Long getCapitalAdjustmentDate() {
        return capitalAdjustmentDate;
    }

    public static final String CAPITAL_ADJUSTMENT_DATE = "capitalAdjustmentDate";

    @RFAType(type=RDMFieldDictionaryConstants.CP_ADJ_DAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setCapitalAdjustmentDate(@Changeable(MarketPrice.CAPITAL_ADJUSTMENT_DATE) Long capitalAdjustmentDate) {
        this.capitalAdjustmentDate = capitalAdjustmentDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.AMT_ISSUE)
    public BigInteger getSharesIssuedTotal() {
        return sharesIssuedTotal;
    }

    public static final String SHARES_ISSUED_TOTAL = "sharesIssuedTotal";

    @RFAType(type=RDMFieldDictionaryConstants.AMT_ISSUE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSharesIssuedTotal(@Changeable(MarketPrice.SHARES_ISSUED_TOTAL) BigInteger sharesIssuedTotal) {
        this.sharesIssuedTotal = sharesIssuedTotal;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.MKT_VALUE)
    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public static final String MARKET_VALUE = "marketValue";

    @RFAType(type=RDMFieldDictionaryConstants.MKT_VALUE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setMarketValue(@Changeable(MarketPrice.MARKET_VALUE) BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.SPEC_TRADE)
    public Integer getSpecialTermsTradingFlag() {
        return specialTermsTradingFlag;
    }

    public static final String SPECIAL_TERMS_TRADING_FLAG = "specialTermsTradingFlag";

    @RFAType(type=RDMFieldDictionaryConstants.SPEC_TRADE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSpecialTermsTradingFlag(
        @Changeable(MarketPrice.SPECIAL_TERMS_TRADING_FLAG) Integer specialTermsTradingFlag) {
        this.specialTermsTradingFlag = specialTermsTradingFlag;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.FCAST_EARN)
    public BigDecimal getForecastedEarnings() {
        return forecastedEarnings;
    }

    public static final String FORECASTED_EARNINGS = "forecastedEarnings";

    @RFAType(type=RDMFieldDictionaryConstants.FCAST_EARN)
    @Adapt(using=OMMNumericAdapter.class)
    public void setForecastedEarnings(@Changeable(MarketPrice.FORECASTED_EARNINGS) BigDecimal forecastedEarnings) {
        this.forecastedEarnings = forecastedEarnings;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.EARANK_RAT)
    public BigDecimal getEarningsRankRatio() {
        return earningsRankRatio;
    }

    public static final String EARNINGS_RANK_RATIO = "earningsRankRatio";

    @RFAType(type=RDMFieldDictionaryConstants.EARANK_RAT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setEarningsRankRatio(@Changeable(MarketPrice.EARNINGS_RANK_RATIO) BigDecimal earningsRankRatio) {
        this.earningsRankRatio = earningsRankRatio;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.FCAST_DATE)
    public Long getForecastDate() {
        return forecastDate;
    }

    public static final String FORECAST_DATE = "forecastDate";

    @RFAType(type=RDMFieldDictionaryConstants.FCAST_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setForecastDate(@Changeable(MarketPrice.FORECAST_DATE) Long forecastDate) {
        this.forecastDate = forecastDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.YEAR_FCAST)
    public String getForecastYear() {
        return forecastYear;
    }

    public static final String FORECAST_YEAR = "forecastYear";

    @RFAType(type=RDMFieldDictionaryConstants.YEAR_FCAST)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setForecastYear(@Changeable(MarketPrice.FORECAST_YEAR) String forecastYear) {
        this.forecastYear = forecastYear;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.IRGMOD)
    public String getIrgPriceTypeModifier() {
        return irgPriceTypeModifier;
    }

    public static final String IRG_PRICE_TYPE_MODIFIER = "irgPriceTypeModifier";

    @RFAType(type=RDMFieldDictionaryConstants.IRGMOD)
    @Adapt(using=OMMEnumAdapter.class)
    public void setIrgPriceTypeModifier(@Changeable(MarketPrice.IRG_PRICE_TYPE_MODIFIER) String irgPriceTypeModifier) {
        this.irgPriceTypeModifier = irgPriceTypeModifier;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.INSMOD)
    public String getInsertPriceTypeModifier() {
        return insertPriceTypeModifier;
    }

    public static final String INSERT_PRICE_TYPE_MODIFIER = "insertPriceTypeModifier";

    @RFAType(type=RDMFieldDictionaryConstants.INSMOD)
    @Adapt(using=OMMEnumAdapter.class)
    public void setInsertPriceTypeModifier(
        @Changeable(MarketPrice.INSERT_PRICE_TYPE_MODIFIER) String insertPriceTypeModifier) {
        this.insertPriceTypeModifier = insertPriceTypeModifier;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.A_NPLRS_1)
    public BigInteger getAskPlayersLevel1() {
        return askPlayersLevel1;
    }

    public static final String ASK_PLAYERS_LEVEL_1 = "askPlayersLevel1";

    @RFAType(type=RDMFieldDictionaryConstants.A_NPLRS_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAskPlayersLevel1(@Changeable(MarketPrice.ASK_PLAYERS_LEVEL_1) BigInteger askPlayersLevel1) {
        this.askPlayersLevel1 = askPlayersLevel1;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.B_NPLRS_1)
    public BigInteger getBidPlayersLevel1() {
        return bidPlayersLevel1;
    }

    public static final String BID_PLAYERS_LEVEL_1 = "bidPlayersLevel1";

    @RFAType(type=RDMFieldDictionaryConstants.B_NPLRS_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBidPlayersLevel1(@Changeable(BID_PLAYERS_LEVEL_1) BigInteger bidPlayersLevel1) {
        this.bidPlayersLevel1 = bidPlayersLevel1;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GV3_TIME)
    public Long getGenericTime3() {
        return genericTime3;
    }

    public static final String GENERIC_TIME_3 = "genericTime3";

    @RFAType(type=RDMFieldDictionaryConstants.GV3_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setGenericTime3(@Changeable(GENERIC_TIME_3) Long genericTime3) {
        this.genericTime3 = genericTime3;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.GV4_TIME)
    public Long getGenericTime4() {
        return genericTime4;
    }

    public static final String GENERIC_TIME_4 = "genericTime4";

    @RFAType(type=RDMFieldDictionaryConstants.GV4_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setGenericTime4(@Changeable(GENERIC_TIME_4) Long genericTime4) {
        this.genericTime4 = genericTime4;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.MKT_CAP)
    public BigInteger getMarketCapitalisation() {
        return marketCapitalisation;
    }

    public static final String MARKET_CAPITALISATION = "marketCapitalisation";

    @RFAType(type=RDMFieldDictionaryConstants.MKT_CAP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setMarketCapitalisation(@Changeable(MARKET_CAPITALISATION) BigInteger marketCapitalisation) {
        this.marketCapitalisation = marketCapitalisation;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.IRGFID)
    public BigInteger getIrgCorrectionValueFid() {
        return irgCorrectionValueFid;
    }

    public static final String IRG_CORRECTION_VALUE_FID = "irgCorrectionValueFid";

    @RFAType(type=RDMFieldDictionaryConstants.IRGFID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgCorrectionValueFid(@Changeable(IRG_CORRECTION_VALUE_FID) BigInteger irgCorrectionValueFid) {
        this.irgCorrectionValueFid = irgCorrectionValueFid;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.IRGVAL)
    public BigInteger getIrgCorrectionValue() {
        return irgCorrectionValue;
    }

    public static final String IRG_CORRECTION_VALUE = "irgCorrectionValue";

    @RFAType(type=RDMFieldDictionaryConstants.IRGVAL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgCorrectionValue(@Changeable(IRG_CORRECTION_VALUE) BigInteger irgCorrectionValue) {
        this.irgCorrectionValue = irgCorrectionValue;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PCT_ABNVOL)
    public BigDecimal getAbnormalVolumeIncreasePercentage() {
        return abnormalVolumeIncreasePercentage;
    }

    public static final String ABNORMAL_VOLUME_INCREASE_PERCENTAGE = "abnormalVolumeIncreasePercentage";

    @RFAType(type=RDMFieldDictionaryConstants.PCT_ABNVOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAbnormalVolumeIncreasePercentage(
        @Changeable(ABNORMAL_VOLUME_INCREASE_PERCENTAGE) BigDecimal abnormalVolumeIncreasePercentage) {
        this.abnormalVolumeIncreasePercentage = abnormalVolumeIncreasePercentage;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BC_10_50K)
    public BigInteger getBlockTransactionsBetween10KAnd50KShares() {
        return blockTransactionsBetween10KAnd50KShares;
    }

    public static final String BLOCK_TRANSACTION_BETWEEN_10K_AND_50K_SHARES
        = "blockTransactionsBetween10KAnd50KShares";

    @RFAType(type=RDMFieldDictionaryConstants.BC_10_50K)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockTransactionsBetween10KAnd50KShares(@Changeable(BLOCK_TRANSACTION_BETWEEN_10K_AND_50K_SHARES)
        BigInteger blockTransactionsBetween10KAnd50KShares) {
        this.blockTransactionsBetween10KAnd50KShares = blockTransactionsBetween10KAnd50KShares;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BC_50_100K)
    public BigInteger getBlockTransactionsBetween50KAnd100KShares() {
        return blockTransactionsBetween50KAnd100KShares;
    }

    public static final String BLOCK_TRANSACTION_BETWEEN_50K_AND_100K_SHARES
        = "blockTransactionsBetween50KAnd100KShares";

    @RFAType(type=RDMFieldDictionaryConstants.BC_50_100K)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockTransactionsBetween50KAnd100KShares(@Changeable(BLOCK_TRANSACTION_BETWEEN_50K_AND_100K_SHARES)
        BigInteger blockTransactionsBetween50KAnd100KShares) {
        this.blockTransactionsBetween50KAnd100KShares = blockTransactionsBetween50KAnd100KShares;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.BC_100K)
    public BigInteger getBlockTransactionsAbove100KShares() {
        return blockTransactionsAbove100KShares;
    }

    public static final String BLOCK_TRANSACTION_ABOVE_100K_SHARES = "blockTransactionsAbove100KShares";

    @RFAType(type=RDMFieldDictionaryConstants.BC_100K)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockTransactionsAbove100KShares(@Changeable(BLOCK_TRANSACTION_ABOVE_100K_SHARES)
        BigInteger blockTransactionsAbove100KShares) {
        this.blockTransactionsAbove100KShares = blockTransactionsAbove100KShares;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PMA_50D)
    public BigDecimal getPriceMovingAverages50D() {
        return priceMovingAverages50D;
    }

    public static final String PRICE_MOVING_AVERAGE_50D = "priceMovingAverages50D";

    @RFAType(type=RDMFieldDictionaryConstants.PMA_50D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceMovingAverages50D(@Changeable(PRICE_MOVING_AVERAGE_50D) BigDecimal priceMovingAverages50D) {
        this.priceMovingAverages50D = priceMovingAverages50D;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PMA_150D)
    public BigDecimal getPriceMovingAverages150D() {
        return priceMovingAverages150D;
    }

    public static final String PRICE_MOVING_AVERAGES_150D = "priceMovingAverages150D";

    @RFAType(type=RDMFieldDictionaryConstants.PMA_150D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceMovingAverages150D(@Changeable(PRICE_MOVING_AVERAGES_150D) BigDecimal priceMovingAverages150D) {
        this.priceMovingAverages150D = priceMovingAverages150D;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PMA_200D)
    public BigDecimal getPriceMovingAverages200D() {
        return priceMovingAverages200D;
    }

    public static final String PRICE_MOVING_AVERAGES_200D = "priceMovingAverages200D";

    @RFAType(type=RDMFieldDictionaryConstants.PMA_200D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceMovingAverages200D(@Changeable(PRICE_MOVING_AVERAGES_200D) BigDecimal priceMovingAverages200D) {
        this.priceMovingAverages200D = priceMovingAverages200D;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.VMA_10D)
    public BigInteger getVolumeMovingAverages10D() {
        return volumeMovingAverages10D;
    }

    public static final String VOLUME_MOVING_AVERAGES_10D = "volumeMovingAverages10D";

    @RFAType(type=RDMFieldDictionaryConstants.VMA_10D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeMovingAverages10D(@Changeable(VOLUME_MOVING_AVERAGES_10D) BigInteger volumeMovingAverages10D) {
        this.volumeMovingAverages10D = volumeMovingAverages10D;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.VMA_25D)
    public BigInteger getVolumeMovingAverages25D() {
        return volumeMovingAverages25D;
    }

    public static final String VOLUME_MOVING_AVERAGES_25D = "volumeMovingAverages25D";

    @RFAType(type=RDMFieldDictionaryConstants.VMA_25D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeMovingAverages25D(@Changeable(VOLUME_MOVING_AVERAGES_25D) BigInteger volumeMovingAverages25D) {
        this.volumeMovingAverages25D = volumeMovingAverages25D;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.VMA_50D)
    public BigInteger getVolumeMovingAverages50D() {
        return volumeMovingAverages50D;
    }

    public static final String VOLUME_MOVING_AVERAGES_50D = "volumeMovingAverages50D";

    @RFAType(type=RDMFieldDictionaryConstants.VMA_50D)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeMovingAverages50D(@Changeable(VOLUME_MOVING_AVERAGES_50D) BigInteger volumeMovingAverages50D) {
        this.volumeMovingAverages50D = volumeMovingAverages50D;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.OPN_NETCH)
    public BigDecimal getOpenPriceNetChange() {
        return openPriceNetChange;
    }

    public static final String OPEN_PRICE_NET_CHANGE = "openPriceNetChange";

    @RFAType(type=RDMFieldDictionaryConstants.OPN_NETCH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenPriceNetChange(@Changeable(OPEN_PRICE_NET_CHANGE) BigDecimal openPriceNetChange) {
        this.openPriceNetChange = openPriceNetChange;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.CASH_EXDIV)
    public BigDecimal getLatestReportedCashDividend() {
        return latestReportedCashDividend;
    }

    public static final String LATEST_REPORTED_CASH_DIVIDEND = "latestReportedCashDividend";

    @RFAType(type=RDMFieldDictionaryConstants.CASH_EXDIV)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLatestReportedCashDividend(
        @Changeable(LATEST_REPORTED_CASH_DIVIDEND) BigDecimal latestReportedCashDividend
    ) {
        this.latestReportedCashDividend = latestReportedCashDividend;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.MKT_VAL_SC)
    public String getMarketValueScalingFactor() {
        return marketValueScalingFactor;
    }

    public static final String MARKET_VALUE_SCALING_FACTOR = "marketValueScalingFactor";

    @RFAType(type=RDMFieldDictionaryConstants.MKT_VAL_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setMarketValueScalingFactor(@Changeable(MARKET_VALUE_SCALING_FACTOR) String marketValueScalingFactor) {
        this.marketValueScalingFactor = marketValueScalingFactor;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.CASH_EXDAT)
    public Long getExDividendTradeDate() {
        return exDividendTradeDate;
    }

    public static final String EX_DIVIDEND_TRADE_DATE = "exDividendTradeDate";

    @RFAType(type=RDMFieldDictionaryConstants.CASH_EXDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExDividendTradeDate(@Changeable(EX_DIVIDEND_TRADE_DATE) Long exDividendTradeDate) {
        this.exDividendTradeDate = exDividendTradeDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PREV_DISP)
    public Integer getPreviousDisplayTemplate() {
        return previousDisplayTemplate;
    }

    public static final String PREVIOUS_DISPLAY_TEMPLATE = "previousDisplayTemplate";

    @RFAType(type=RDMFieldDictionaryConstants.PREV_DISP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPreviousDisplayTemplate(@Changeable(PREVIOUS_DISPLAY_TEMPLATE) Integer previousDisplayTemplate) {
        this.previousDisplayTemplate = previousDisplayTemplate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PRC_QL3)
    public String getExtendedPriceQualifierFid() {
        return extendedPriceQualifierFid;
    }

    public static final String EXTENDED_PRICE_QUALIFIER_FID = "extendedPriceQualifierFid";

    @RFAType(type=RDMFieldDictionaryConstants.PRC_QL3)
    @Adapt(using=OMMEnumAdapter.class)
    public void setExtendedPriceQualifierFid(
        @Changeable(EXTENDED_PRICE_QUALIFIER_FID) String extendedPriceQualifierFid
    ) {
        this.extendedPriceQualifierFid = extendedPriceQualifierFid;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.MPV)
    public String getMinimumPriceMovement() {
        return minimumPriceMovement;
    }

    public static final String MINIMUM_PRICE_MOVEMENT = "minimumPriceMovement";

    @RFAType(type=RDMFieldDictionaryConstants.MPV)
    @Adapt(using=OMMEnumAdapter.class)
    public void setMinimumPriceMovement(@Changeable(MINIMUM_PRICE_MOVEMENT) String minimumPriceMovement) {
        this.minimumPriceMovement = minimumPriceMovement;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.OFF_CLOSE)
    public BigDecimal getOfficialClosingPrice() {
        return officialClosingPrice;
    }

    public static final String OFFICIAL_CLOSING_PRICE = "officialClosingPrice";

    @RFAType(type=RDMFieldDictionaryConstants.OFF_CLOSE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOfficialClosingPrice(@Changeable(OFFICIAL_CLOSING_PRICE) BigDecimal officialClosingPrice) {
        this.officialClosingPrice = officialClosingPrice;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.QUOTE_DATE)
    public Long getQuoteDate() {
        return quoteDate;
    }

    public static final String QUOTE_DATE = "quoteDate";

    @RFAType(type=RDMFieldDictionaryConstants.QUOTE_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setQuoteDate(@Changeable(MarketPrice.QUOTE_DATE) Long quoteDate) {
        this.quoteDate = quoteDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.VWAP)
    public BigDecimal getVolumeWeightedAveragePrice() {
        return volumeWeightedAveragePrice;
    }

    public static final String VOLUME_WEIGHTED_AVERAGE_PRICE = "volumeWeightedAveragePrice";

    @RFAType(type=RDMFieldDictionaryConstants.VWAP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeWeightedAveragePrice(
        @Changeable(MarketPrice.VOLUME_WEIGHTED_AVERAGE_PRICE) BigDecimal volumeWeightedAveragePrice) {
        this.volumeWeightedAveragePrice = volumeWeightedAveragePrice;
    }

//    @UsingKey(type=MarketPriceConstants.PROV_SYMB)
//    public String getProviderSymbol() {
//        return providerSymbol;
//    }
//
//    @RFAType(type=MarketPriceConstants.PROV_SYMB)
//    @Adapt(using=OMMDataBufferAdapter.class)
//    public void setProviderSymbol(String providerSymbol) {
//        this.providerSymbol = providerSymbol;
//    }

    @UsingKey(type=RDMFieldDictionaryConstants.BID_ASK_DT)
    public Long getBidAskDate() {
        return bidAskDate;
    }

    public static final String BID_ASK_DATE = "bidAskDate";

    @RFAType(type=RDMFieldDictionaryConstants.BID_ASK_DT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setBidAskDate(@Changeable(MarketPrice.BID_ASK_DATE) Long bidAskDate) {
        this.bidAskDate = bidAskDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ISIN_CODE)
    public String getIsinCode() {
        return isinCode;
    }

    public static final String ISIN_CODE = "isinCode";

    @RFAType(type=RDMFieldDictionaryConstants.ISIN_CODE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setIsinCode(@Changeable(MarketPrice.ISIN_CODE) String isinCode) {
        this.isinCode = isinCode;
    }

//    @UsingKey(type=MarketPriceConstants.MNEMONIC)
//    public String getExchangeId() {
//        return exchangeId;
//    }
//
//    @RFAType(type=MarketPriceConstants.MNEMONIC)
//    @Adapt(using=OMMDataBufferAdapter.class)
//    public void setExchangeId(String exchangeId) {
//        this.exchangeId = exchangeId;
//    }

    @UsingKey(type=RDMFieldDictionaryConstants.RTR_OPN_PR)
    public BigDecimal getRtrsOpeningPrice() {
        return rtrsOpeningPrice;
    }

    public static final String RTRS_OPENING_PRICE = "rtrsOpeningPrice";

    @RFAType(type=RDMFieldDictionaryConstants.RTR_OPN_PR)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRtrsOpeningPrice(@Changeable(MarketPrice.RTRS_OPENING_PRICE) BigDecimal rtrsOpeningPrice) {
        this.rtrsOpeningPrice = rtrsOpeningPrice;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.SEDOL)
    public String getSedolCode() {
        return sedolCode;
    }

    public static final String SEDOL_CODE = "sedolCode";

    @RFAType(type=RDMFieldDictionaryConstants.SEDOL)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setSedolCode(@Changeable(MarketPrice.SEDOL_CODE) String sedolCode) {
        this.sedolCode = sedolCode;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.MKT_SEGMNT)
    public String getMarketSegment() {
        return marketSegment;
    }

    public static final String MARKET_SEGMENT = "marketSegment";

    @RFAType(type=RDMFieldDictionaryConstants.MKT_SEGMNT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setMarketSegment(@Changeable(MarketPrice.MARKET_SEGMENT) String marketSegment) {
        this.marketSegment = marketSegment;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TRDTIM_MS)
    public Long getRegularTradesTimeMillis() {
        return regularTradesTimeMillis;
    }

    public static final String REGULAR_TRADE_TIME_MILLIS = "regularTradesTimeMillis";

    @RFAType(type=RDMFieldDictionaryConstants.TRDTIM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRegularTradesTimeMillis(
        @Changeable(MarketPrice.REGULAR_TRADE_TIME_MILLIS) Long regularTradesTimeMillis
    ) {
        this.regularTradesTimeMillis = regularTradesTimeMillis;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.SALTIM_MS)
    public Long getAllTradesTimeMillis() {
        return allTradesTimeMillis;
    }

    public static final String ALL_TRADES_TIME_MILLIS = "allTradesTimeMillis";

    @RFAType(type=RDMFieldDictionaryConstants.SALTIM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAllTradesTimeMillis(@Changeable(MarketPrice.ALL_TRADES_TIME_MILLIS) Long allTradesTimeMillis) {
        this.allTradesTimeMillis = allTradesTimeMillis;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.QUOTIM_MS)
    public Long getQuoteTimeMillis() {
        return quoteTimeMillis;
    }

    public static final String QUOTE_TIME_MILLIS = "quoteTimeMillis";

    @RFAType(type=RDMFieldDictionaryConstants.QUOTIM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setQuoteTimeMillis(@Changeable(QUOTE_TIME_MILLIS) Long quoteTimeMillis) {
        this.quoteTimeMillis = quoteTimeMillis;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TIMCOR_MS)
    public BigInteger getCorrectionTimeMillis() {
        return correctionTimeMillis;
    }

    public static final String CORRECTION_TIME_MILLIS = "correctionTimeMillis";

    @RFAType(type=RDMFieldDictionaryConstants.TIMCOR_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCorrectionTimeMillis(@Changeable(CORRECTION_TIME_MILLIS) BigInteger correctionTimeMillis) {
        this.correctionTimeMillis = correctionTimeMillis;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.FIN_STATUS)
    public String getFinancialStatusIndicator() {
        return financialStatusIndicator;
    }

    public static final String FINANCIAL_STATUS_INDICATOR = "financialStatusIndicator";

    @RFAType(type=RDMFieldDictionaryConstants.FIN_STATUS)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setFinancialStatusIndicator(@Changeable(FINANCIAL_STATUS_INDICATOR) String financialStatusIndicator) {
        this.financialStatusIndicator = financialStatusIndicator;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.LS_SUBIND)
    public String getLastTradeSubMarketIndicator() {
        return lastTradeSubMarketIndicator;
    }

    public static final String LAST_TRADE_SUB_MARKET_INDICATOR = "lastTradeSubMarketIndicator";

    @RFAType(type=RDMFieldDictionaryConstants.LS_SUBIND)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setLastTradeSubMarketIndicator(
        @Changeable(LAST_TRADE_SUB_MARKET_INDICATOR) String lastTradeSubMarketIndicator) {
        this.lastTradeSubMarketIndicator = lastTradeSubMarketIndicator;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.IRG_SUBIND)
    public String getIrgPriceSubmarketIndicator() {
        return irgPriceSubmarketIndicator;
    }

    public static final String IRG_PRICE_SUBMARKET_INDICATOR = "irgPriceSubmarketIndicator";

    @RFAType(type=RDMFieldDictionaryConstants.IRG_SUBIND)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setIrgPriceSubmarketIndicator(
        @Changeable(IRG_PRICE_SUBMARKET_INDICATOR) String irgPriceSubmarketIndicator) {
        this.irgPriceSubmarketIndicator = irgPriceSubmarketIndicator;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ACVOL_SC)
    public String getVolumeScaling() {
        return volumeScaling;
    }

    public static final String VOLUME_SCALING = "volumeScaling";

    @RFAType(type=RDMFieldDictionaryConstants.ACVOL_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setVolumeScaling(@Changeable(VOLUME_SCALING) String volumeScaling) {
        this.volumeScaling = volumeScaling;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.EXCHCODE)
    public String getExchangeCode() {
        return exchangeCode;
    }

    public static final String EXCHANGE_CODE = "exchangeCode";

    @RFAType(type=RDMFieldDictionaryConstants.EXCHCODE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setExchangeCode(@Changeable(EXCHANGE_CODE) String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ODD_ASK)
    public BigDecimal getOddBestAsk() {
        return oddBestAsk;
    }

    public static final String ODD_BEST_ASK = "oddBestAsk";

    @RFAType(type=RDMFieldDictionaryConstants.ODD_ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestAsk(@Changeable(ODD_BEST_ASK) BigDecimal oddBestAsk) {
        this.oddBestAsk = oddBestAsk;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ODD_ASKSIZ)
    public BigInteger getOddBestAskSize() {
        return oddBestAskSize;
    }

    public static final String ODD_BEST_ASK_SIZE = "oddBestAskSize";

    @RFAType(type=RDMFieldDictionaryConstants.ODD_ASKSIZ)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestAskSize(@Changeable(ODD_BEST_ASK_SIZE) BigInteger oddBestAskSize) {
        this.oddBestAskSize = oddBestAskSize;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ODD_BID)
    public BigDecimal getOddBestBid() {
        return oddBestBid;
    }

    public static final String ODD_BEST_BID = "oddBestBid";

    @RFAType(type=RDMFieldDictionaryConstants.ODD_BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestBid(@Changeable(ODD_BEST_BID) BigDecimal oddBestBid) {
        this.oddBestBid = oddBestBid;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ODD_BIDSIZ)
    public BigInteger getOddBestBidSize() {
        return oddBestBidSize;
    }

    public static final String ODD_BEST_BID_SIZE = "oddBestBidSize";

    @RFAType(type=RDMFieldDictionaryConstants.ODD_BIDSIZ)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestBidSize(@Changeable(ODD_BEST_BID_SIZE) BigInteger oddBestBidSize) {
        this.oddBestBidSize = oddBestBidSize;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROUND_VOL)
    public BigInteger getRoundVolume() {
        return roundVolume;
    }

    public static final String ROUND_VOLUME = "roundVolume";

    @RFAType(type=RDMFieldDictionaryConstants.ROUND_VOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRoundVolume(@Changeable(ROUND_VOLUME) BigInteger roundVolume) {
        this.roundVolume = roundVolume;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ORGID)
    public BigInteger getOrganizationId() {
        return organizationId;
    }

    public static final String ORGANIZATION_ID = "organizationId";

    @RFAType(type=RDMFieldDictionaryConstants.ORGID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOrganizationId(@Changeable(ORGANIZATION_ID) BigInteger organizationId) {
        this.organizationId = organizationId;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.PR_FREQ)
    public String getPriceUpdateFrequency() {
        return priceUpdateFrequency;
    }

    public static final String PRICE_UPDATE_FREQUENCY = "priceUpdateFrequency";

    @RFAType(type=RDMFieldDictionaryConstants.PR_FREQ)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceUpdateFrequency(@Changeable(PRICE_UPDATE_FREQUENCY) String priceUpdateFrequency) {
        this.priceUpdateFrequency = priceUpdateFrequency;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.RCS_AS_CLA)
    public String getRcsAssetClassification() {
        return rcsAssetClassification;
    }

    public static final String RCS_ASSET_CLASSIFICATION = "rcsAssetClassification";

    @RFAType(type=RDMFieldDictionaryConstants.RCS_AS_CLA)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRcsAssetClassification(@Changeable(RCS_ASSET_CLASSIFICATION) String rcsAssetClassification) {
        this.rcsAssetClassification = rcsAssetClassification;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.UNDR_INDEX)
    public String getUnderlyingIndex() {
        return underlyingIndex;
    }

    public static final String UNDERLYING_INDEX = "underlyingIndex";

    @RFAType(type=RDMFieldDictionaryConstants.UNDR_INDEX)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setUnderlyingIndex(@Changeable(UNDERLYING_INDEX) String underlyingIndex) {
        this.underlyingIndex = underlyingIndex;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.FUTURES)
    public String getFuturesChainRic() {
        return futuresChainRic;
    }

    public static final String FUTURES_CHAIN_RIC = "futuresChainRic";

    @RFAType(type=RDMFieldDictionaryConstants.FUTURES)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setFuturesChainRic(@Changeable(FUTURES_CHAIN_RIC) String futuresChainRic) {
        this.futuresChainRic = futuresChainRic;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.OPTIONS)
    public String getOptionsChainRic() {
        return optionsChainRic;
    }

    public static final String OPTIONS_CHAIN_RIC = "optionsChainRic";

    @RFAType(type=RDMFieldDictionaryConstants.OPTIONS)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOptionsChainRic(@Changeable(OPTIONS_CHAIN_RIC) String optionsChainRic) {
        this.optionsChainRic = optionsChainRic;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.STRIKES)
    public String getStrikesCoverage() {
        return strikesCoverage;
    }

    public static final String STRIKES_COVERAGE = "strikesCoverage";

    @RFAType(type=RDMFieldDictionaryConstants.STRIKES)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setStrikesCoverage(@Changeable(STRIKES_COVERAGE) String strikesCoverage) {
        this.strikesCoverage = strikesCoverage;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.NEWSTM_MS)
    public BigInteger getNewsTimeMillis() {
        return newsTimeMillis;
    }

    public static final String NEWS_TIME_MILLIS = "newsTimeMillis";

    @RFAType(type=RDMFieldDictionaryConstants.NEWSTM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setNewsTimeMillis(@Changeable(NEWS_TIME_MILLIS) BigInteger newsTimeMillis) {
        this.newsTimeMillis = newsTimeMillis;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TRD_THRU_X)
    public String getTradeThroughExemptFlags() {
        return tradeThroughExemptFlags;
    }

    public static final String TRADE_THROUGH_EXEMPT_FLAGS = "tradeThroughExemptFlags";

    @RFAType(type=RDMFieldDictionaryConstants.TRD_THRU_X)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setTradeThroughExemptFlags(@Changeable(TRADE_THROUGH_EXEMPT_FLAGS) String tradeThroughExemptFlags) {
        this.tradeThroughExemptFlags = tradeThroughExemptFlags;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.FIN_ST_IND)
    public String getCompanyComplianceStatus() {
        return companyComplianceStatus;
    }

    public static final String COMPANY_COMPLIANCE_STATUS = "companyComplianceStatus";

    @RFAType(type=RDMFieldDictionaryConstants.FIN_ST_IND)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setCompanyComplianceStatus(@Changeable(COMPANY_COMPLIANCE_STATUS) String companyComplianceStatus) {
        this.companyComplianceStatus = companyComplianceStatus;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.IRG_SMKTID)
    public String getIrgSubMarketCenterId() {
        return irgSubMarketCenterId;
    }

    public static final String IRG_SUB_MARKET_CENTER_ID = "irgSubMarketCenterId";

    @RFAType(type=RDMFieldDictionaryConstants.IRG_SMKTID)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setIrgSubMarketCenterId(@Changeable(IRG_SUB_MARKET_CENTER_ID) String irgSubMarketCenterId) {
        this.irgSubMarketCenterId = irgSubMarketCenterId;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.SUB_MKT_ID)
    public String getSubMarketCenterId() {
        return subMarketCenterId;
    }

    public static final String SUB_MARKET_CENTER_ID = "subMarketCenterId";

    @RFAType(type=RDMFieldDictionaryConstants.SUB_MKT_ID)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setSubMarketCenterId(@Changeable(SUB_MARKET_CENTER_ID) String subMarketCenterId) {
        this.subMarketCenterId = subMarketCenterId;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ACT_DOM_EX)
    public String getActiveDomesticExchangeIds() {
        return activeDomesticExchangeIds;
    }

    public static final String ACTIVE_DOMESTIC_EXCHANGE_IDS = "activeDomesticExchangeIds";

    @RFAType(type=RDMFieldDictionaryConstants.ACT_DOM_EX)
    @Adapt(using=OMMEnumAdapter.class)
    public void setActiveDomesticExchangeIds(
        @Changeable(ACTIVE_DOMESTIC_EXCHANGE_IDS) String activeDomesticExchangeIds) {
        this.activeDomesticExchangeIds = activeDomesticExchangeIds;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ACT_OTH_EX)
    public String getActiveOtherExchangeIds() {
        return activeOtherExchangeIds;
    }

    public static final String ACTIVE_OTHER_EXCHANGE_IDS = "activeOtherExchangeIds";

    @RFAType(type=RDMFieldDictionaryConstants.ACT_OTH_EX)
    @Adapt(using=OMMEnumAdapter.class)
    public void setActiveOtherExchangeIds(@Changeable(ACTIVE_OTHER_EXCHANGE_IDS) String activeOtherExchangeIds) {
        this.activeOtherExchangeIds = activeOtherExchangeIds;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.TRD_QUAL_2)
    public String getTradePriceQualifier2() {
        return tradePriceQualifier2;
    }

    public static final String TRADE_PRICE_QUALIFIER2 = "tradePriceQualifier2";

    @RFAType(type=RDMFieldDictionaryConstants.TRD_QUAL_2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradePriceQualifier2(@Changeable(TRADE_PRICE_QUALIFIER2) String tradePriceQualifier2) {
        this.tradePriceQualifier2 = tradePriceQualifier2;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.CP_EFF_DAT)
    public Long getLatestCapitalChangeEffectiveDate() {
        return latestCapitalChangeEffectiveDate;
    }

    public static final String LATEST_CAPITAL_CHANGE_EFFECTIVE_DATE = "latestCapitalChangeEffectiveDate";

    @RFAType(type=RDMFieldDictionaryConstants.CP_EFF_DAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLatestCapitalChangeEffectiveDate(@Changeable(LATEST_CAPITAL_CHANGE_EFFECTIVE_DATE)
        Long latestCapitalChangeEffectiveDate) {

        this.latestCapitalChangeEffectiveDate = latestCapitalChangeEffectiveDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_1)
    public String getRow64_1() {
        return row64_1;
    }

    public static final String ROW64_1 = "row64_1";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_1)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_1(@Changeable(MarketPrice.ROW64_1) String row64_1) {
        this.row64_1 = row64_1;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_2)
    public String getRow64_2() {
        return row64_2;
    }

    public static final String ROW64_2 = "row64_2";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_2)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_2(@Changeable(MarketPrice.ROW64_2) String row64_2) {
        this.row64_2 = row64_2;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_3)
    public String getRow64_3() {
        return row64_3;
    }

    public static final String ROW64_3 = "row64_3";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_3)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_3(@Changeable(MarketPrice.ROW64_3) String row64_3) {
        this.row64_3 = row64_3;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_4)
    public String getRow64_4() {
        return row64_4;
    }

    public static final String ROW64_4 = "row64_4";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_4)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_4(@Changeable(MarketPrice.ROW64_4) String row64_4) {
        this.row64_4 = row64_4;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_5)
    public String getRow64_5() {
        return row64_5;
    }

    public static final String ROW64_5 = "row64_5";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_5)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_5(@Changeable(MarketPrice.ROW64_5) String row64_5) {
        this.row64_5 = row64_5;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_6)
    public String getRow64_6() {
        return row64_6;
    }

    public static final String ROW64_6 = "row64_6";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_6)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_6(@Changeable(MarketPrice.ROW64_6) String row64_6) {
        this.row64_6 = row64_6;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_7)
    public String getRow64_7() {
        return row64_7;
    }

    public static final String ROW64_7 = "row64_7";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_7)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_7(@Changeable(MarketPrice.ROW64_7) String row64_7) {
        this.row64_7 = row64_7;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_8)
    public String getRow64_8() {
        return row64_8;
    }

    public static final String ROW64_8 = "row64_8";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_8)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_8(@Changeable(MarketPrice.ROW64_8) String row64_8) {
        this.row64_8 = row64_8;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_9)
    public String getRow64_9() {
        return row64_9;
    }

    public static final String ROW64_9 = "row64_9";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_9)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_9(@Changeable(MarketPrice.ROW64_9) String row64_9) {
        this.row64_9 = row64_9;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_10)
    public String getRow64_10() {
        return row64_10;
    }

    public static final String ROW64_10 = "row64_10";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_10)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_10(@Changeable(MarketPrice.ROW64_10) String row64_10) {
        this.row64_10 = row64_10;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_11)
    public String getRow64_11() {
        return row64_11;
    }

    public static final String ROW64_11 = "row64_11";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_11)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_11(@Changeable(MarketPrice.ROW64_11) String row64_11) {
        this.row64_11 = row64_11;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_12)
    public String getRow64_12() {
        return row64_12;
    }

    public static final String ROW64_12 = "row64_12";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_12)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_12(@Changeable(MarketPrice.ROW64_12) String row64_12) {
        this.row64_12 = row64_12;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_13)
    public String getRow64_13() {
        return row64_13;
    }

    public static final String ROW64_13 = "row64_13";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_13)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_13(@Changeable(MarketPrice.ROW64_13) String row64_13) {
        this.row64_13 = row64_13;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.ROW64_14)
    public String getRow64_14() {
        return row64_14;
    }

    public static final String ROW64_14 = "row64_14";

    @RFAType(type=RDMFieldDictionaryConstants.ROW64_14)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_14(@Changeable(MarketPrice.ROW64_14) String row64_14) {
        this.row64_14 = row64_14;
    }

    /**
     * Getter method for the {@link RDMFieldDictionaryConstants#PUT_CALL}.
     */
    @UsingKey(type=RDMFieldDictionaryConstants.PUT_CALL)
    public String getPutCall() {
        return RDMFieldDictionaryConstants.PUT_CALL_NOT_ALLOCATED.equals(putCall)
            ? RDMFieldDictionaryConstants.NOT_ALLOCATED : putCall;
    }

    public static final String PUT_CALL = "putCall";

    /**
     * Setter method for the {@link RDMFieldDictionaryConstants#PUT_CALL}.
     */
    @RFAType(type=RDMFieldDictionaryConstants.PUT_CALL)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPutCall(@Changeable(MarketPrice.PUT_CALL) String putCall) {
        this.putCall = putCall;
    }

    /**
     * Getter method for the {@link RDMFieldDictionaryConstants#IMP_VOLTA}.
     */
    @UsingKey(type=RDMFieldDictionaryConstants.IMP_VOLTA)
    public BigDecimal getImpliedVolatilitytOfAskPrice() {
        return impliedVolatilitytOfAskPrice;
    }

    public static final String IMPLIED_VOLATILITY_OF_ASK_PRICE = "impliedVolatilitytOfAskPrice";

    /**
     * Setter method for the {@link RDMFieldDictionaryConstants#IMP_VOLTA}.
     */
    @RFAType(type=RDMFieldDictionaryConstants.IMP_VOLTA)
    @Adapt(using=OMMNumericAdapter.class)
    public void setImpliedVolatilitytOfAskPrice(@Changeable(MarketPrice.IMPLIED_VOLATILITY_OF_ASK_PRICE)
        BigDecimal impliedVolatilitytOfAskPrice
    ) {
        this.impliedVolatilitytOfAskPrice = impliedVolatilitytOfAskPrice;
    }

    /**
     * Setter method for the {@link RDMFieldDictionaryConstants#IMP_VOLTB}.
     */
    @UsingKey(type=RDMFieldDictionaryConstants.IMP_VOLTB)
    public BigDecimal getImpliedVolatilitytOfBidPrice() {
        return impliedVolatilitytOfBidPrice;
    }

    public static final String IMPLIED_VOLATILITY_OF_BID_PRICE = "impliedVolatilitytOfBidPrice";

    /**
     * Getter method for the {@link RDMFieldDictionaryConstants#IMP_VOLTB}.
     */
    @RFAType(type=RDMFieldDictionaryConstants.IMP_VOLTB)
    @Adapt(using=OMMNumericAdapter.class)
    public void setImpliedVolatilitytOfBidPrice(@Changeable(MarketPrice.IMPLIED_VOLATILITY_OF_BID_PRICE)
        BigDecimal impliedVolatilitytOfBidPrice
    ) {
        this.impliedVolatilitytOfBidPrice = impliedVolatilitytOfBidPrice;
    }

    /**
     * Getter method for the {@link RDMFieldDictionaryConstants#OPINT_1}.
     */
    @UsingKey(type=RDMFieldDictionaryConstants.OPINT_1)
    public BigInteger getOpenInterest() {
        return openInterest;
    }

    public static final String OPEN_INTEREST = "openInterest";

    /**
     * Setter method for the {@link RDMFieldDictionaryConstants#OPINT_1}.
     */
    @RFAType(type=RDMFieldDictionaryConstants.OPINT_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenInterest(@Changeable(MarketPrice.OPEN_INTEREST) BigInteger openInterest) {
        this.openInterest = openInterest;
    }

    /**
     * Getter method for the {@link RDMFieldDictionaryConstants#OPINTNC}.
     */
    @UsingKey(type=RDMFieldDictionaryConstants.OPINTNC)
    public BigInteger getOpenInterestNetChange() {
        return openInterestNetChange;
    }

    public static final String OPEN_INTEREST_NET_CHANGE = "openInterestNetChange";

    /**
     * Setter method for the {@link RDMFieldDictionaryConstants#OPINTNC}.
     */
    @RFAType(type=RDMFieldDictionaryConstants.OPINTNC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenInterestNetChange(@Changeable(OPEN_INTEREST_NET_CHANGE) BigInteger openInterestNetChange) {
        this.openInterestNetChange = openInterestNetChange;
    }

    /**
     * Getter method for the {@link RDMFieldDictionaryConstants#STRIKE_PRC}.
     */
    @UsingKey(type=RDMFieldDictionaryConstants.STRIKE_PRC)
    public BigDecimal getStrikePrice() {
        return strikePrice;
    }

    public static final String STRIKE_PRICE = "strikePrice";

    @RFAType(type=RDMFieldDictionaryConstants.STRIKE_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setStrikePrice(@Changeable(STRIKE_PRICE) BigDecimal strikePrice) {
        this.strikePrice = strikePrice;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.CONTR_MNTH)
    public String getContractMonth() {
        return contractMonth;
    }

    public static final String CONTRACT_MONTH = "contractMonth";

    @RFAType(type=RDMFieldDictionaryConstants.CONTR_MNTH)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setContractMonth(@Changeable(CONTRACT_MONTH) String contractMonth) {
        this.contractMonth = contractMonth;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.LOTSZUNITS)
    public String getLotSizeUnits() {
        return lotSizeUnits;
    }

    public static final String LOT_SIZE_UNITS = "lotSizeUnits";

    @RFAType(type=RDMFieldDictionaryConstants.LOTSZUNITS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setLotSizeUnits(@Changeable(LOT_SIZE_UNITS) String lotSizeUnits) {
        this.lotSizeUnits = lotSizeUnits;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.OPEN_ASK)
    public BigDecimal getOpenAskPrice() {
        return openAskPrice;
    }

    public static final String OPEN_ASK_PRICE = "openAskPrice";

    @RFAType(type=RDMFieldDictionaryConstants.OPEN_ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenAskPrice(@Changeable(OPEN_ASK_PRICE) BigDecimal openAskPrice) {
        this.openAskPrice = openAskPrice;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.EXPIR_DATE)
    public Long getExpiryDate() {
        return expiryDate;
    }

    public static final String EXPIRY_DATE = "expiryDate";

    @RFAType(type=RDMFieldDictionaryConstants.EXPIR_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExpiryDate(@Changeable(EXPIRY_DATE) Long expiryDate) {
        this.expiryDate = expiryDate;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.SETTLE)
    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public static final String SETTLEMENT_PRICE = "settlementPrice";

    @RFAType(type=RDMFieldDictionaryConstants.SETTLE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSettlementPrice(@Changeable(SETTLEMENT_PRICE) BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    @UsingKey(type=RDMFieldDictionaryConstants.SETTLEDATE)
    public Long getSettleDate() {
        return settleDate;
    }

    public static final String SETTLE_DATE = "settleDate";

    @RFAType(type=RDMFieldDictionaryConstants.SETTLEDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setSettleDate(@Changeable(SETTLE_DATE) Long settleDate) {
        this.settleDate = settleDate;
    }

    @Override
    public String toString() {
        return "MarketPrice [displayTemplate=" + displayTemplate + ", idnExchangeId=" + idnExchangeId + ", displayName="
            + displayName + ", bid=" + bid + ", bid1=" + bid1 + ", bid2=" + bid2 + ", ask=" + ask + ", ask1=" + ask1
            + ", ask2=" + ask2 + ", bidSize=" + bidSize + ", askSize=" + askSize + ", last=" + last + ", last1="
            + last1 + ", last2=" + last2 + ", last3=" + last3 + ", last4=" + last4 + ", netChange=" + netChange
            + ", todaysHigh=" + todaysHigh + ", todaysLow=" + todaysLow + ", tickArrow=" + tickArrow
            + ", tradeDateMillis=" + tradeDateMillis + ", tradeTimeMillis=" + tradeTimeMillis + ", openPrice="
            + openPrice + ", historicClose=" + historicClose + ", news=" + news + ", newsTime=" + newsTime
            + ", volumeAccumulated=" + volumeAccumulated + ", earnings=" + earnings + ", yield=" + yield
            + ", priceToEarningsRatio=" + priceToEarningsRatio + ", dividendType=" + dividendType
            + ", dividendPayDate=" + dividendPayDate + ", exDividendDate=" + exDividendDate
            + ", tradePriceQualifier=" + tradePriceQualifier + ", blockCount=" + blockCount + ", blockVolume="
            + blockVolume + ", tradeExchangeId=" + tradeExchangeId + ", tradingUnits=" + tradingUnits + ", lotSize="
            + lotSize + ", percentChange=" + percentChange + ", openBid=" + openBid
            + ", latestDowJonesNewsStoryTime=" + latestDowJonesNewsStoryTime + ", closeBid=" + closeBid
            + ", closeAsk=" + closeAsk + ", dividend=" + dividend + ", totalTradesToday=" + totalTradesToday
            + ", officialCode=" + officialCode + ", historicCloseDate=" + historicCloseDate + ", yearHigh="
            + yearHigh + ", yearLow=" + yearLow + ", turnover=" + turnover + ", bondType=" + bondType
            + ", backgroundPage=" + backgroundPage + ", yearOrContractHighIndicator=" + yearOrContractHighIndicator
            + ", yearOrContractLowIndicator=" + yearOrContractLowIndicator + ", bidNetChange=" + bidNetChange
            + ", bidTick1=" + bidTick1 + ", cumExMarker=" + cumExMarker + ", priceCode=" + priceCode
            + ", nasdStatus=" + nasdStatus + ", priceCode2=" + priceCode2 + ", tradeVolume=" + tradeVolume
            + ", todaysHighBid=" + todaysHighBid + ", todaysLowBid=" + todaysLowBid + ", yearHighBid=" + yearHighBid
            + ", yearLowBid=" + yearLowBid + ", historicalClosingBid=" + historicalClosingBid
            + ", historicalClosingBidDate=" + historicalClosingBidDate + ", yearBidHigh=" + yearBidHigh
            + ", yearBidLow=" + yearBidLow + ", numberOfBids=" + numberOfBids + ", optionExchangeId="
            + optionExchangeId + ", yearHighDate=" + yearHighDate + ", yearLowDate=" + yearLowDate + ", irgPrice="
            + irgPrice + ", irgVolume=" + irgVolume + ", irgPriceType=" + irgPriceType + ", priceCorrectionTime="
            + priceCorrectionTime + ", insertPrice=" + insertPrice + ", insertVolume=" + insertVolume
            + ", insertPriceType=" + insertPriceType + ", lastTime=" + lastTime + ", turnoverScale=" + turnoverScale
            + ", broadcastXRef=" + broadcastXRef + ", crossRateScale=" + crossRateScale + ", amountOutstanding="
            + amountOutstanding + ", amountOutstandingScale=" + amountOutstandingScale + ", officialCodeIndicator="
            + officialCodeIndicator + ", priceVolatility=" + priceVolatility + ", amountOutstandingDate="
            + amountOutstandingDate + ", backgroundReference=" + backgroundReference + ", generalPurposeValue1="
            + generalPurposeValue1 + ", generalPurposeValue1Description=" + generalPurposeValue1Description
            + ", generalPurposeValue2=" + generalPurposeValue2 + ", generalPurposeValue2Description="
            + generalPurposeValue2Description + ", generalPurposeValue3=" + generalPurposeValue3
            + ", generalPurposeValue3Description=" + generalPurposeValue3Description + ", generalPurposeValue4="
            + generalPurposeValue4 + ", generalPurposeValue4Description=" + generalPurposeValue4Description
            + ", sequenceNumber=" + sequenceNumber + ", printType=" + printType
            + ", alteredTradeEventSequenceNumber=" + alteredTradeEventSequenceNumber + ", quoteTimeSeconds="
            + quoteTimeSeconds + ", genericFlag1=" + genericFlag1 + ", genericFlag2=" + genericFlag2
            + ", genericFlag3=" + genericFlag3 + ", genericFlag4=" + genericFlag4 + ", uniqueInstrumentId2Source="
            + uniqueInstrumentId2Source + ", uniqueInstrumentId2=" + uniqueInstrumentId2 + ", timeInSeconds1="
            + timeInSeconds1 + ", timeInSeconds2=" + timeInSeconds2 + ", exchangeTime=" + exchangeTime
            + ", yearHighIndicator=" + yearHighIndicator + ", yearLowIndicator=" + yearLowIndicator + ", betaValue="
            + betaValue + ", preferredDisplayTemplateNumber=" + preferredDisplayTemplateNumber
            + ", localLanguageInstrumentName=" + localLanguageInstrumentName + ", latestTradeOrTradeTurnoverValue="
            + latestTradeOrTradeTurnoverValue + ", dataSourceOwnerId=" + dataSourceOwnerId + ", averagePrice="
            + averagePrice + ", upc71RestrictedFlag=" + upc71RestrictedFlag + ", adjustedClose=" + adjustedClose
            + ", weighting=" + weighting + ", stockType=" + stockType + ", impliedVolatility=" + impliedVolatility
            + ", capitalAdjustmentFactor=" + capitalAdjustmentFactor + ", capitalAdjustmentDate="
            + capitalAdjustmentDate + ", sharesIssuedTotal=" + sharesIssuedTotal + ", marketValue=" + marketValue
            + ", specialTermsTradingFlag=" + specialTermsTradingFlag + ", forecastedEarnings=" + forecastedEarnings
            + ", earningsRankRatio=" + earningsRankRatio + ", forecastDate=" + forecastDate + ", forecastYear="
            + forecastYear + ", irgPriceTypeModifier=" + irgPriceTypeModifier + ", insertPriceTypeModifier="
            + insertPriceTypeModifier + ", askPlayersLevel1=" + askPlayersLevel1 + ", bidPlayersLevel1="
            + bidPlayersLevel1 + ", genericTime3=" + genericTime3 + ", genericTime4=" + genericTime4
            + ", marketCapitalisation=" + marketCapitalisation + ", irgCorrectionValueFid=" + irgCorrectionValueFid
            + ", irgCorrectionValue=" + irgCorrectionValue + ", abnormalVolumeIncreasePercentage="
            + abnormalVolumeIncreasePercentage + ", blockTransactionsBetween10KAnd50KShares="
            + blockTransactionsBetween10KAnd50KShares + ", blockTransactionsBetween50KAnd100KShares="
            + blockTransactionsBetween50KAnd100KShares + ", blockTransactionsAbove100KShares="
            + blockTransactionsAbove100KShares + ", priceMovingAverages50D=" + priceMovingAverages50D
            + ", priceMovingAverages150D=" + priceMovingAverages150D + ", priceMovingAverages200D="
            + priceMovingAverages200D + ", volumeMovingAverages10D=" + volumeMovingAverages10D
            + ", volumeMovingAverages25D=" + volumeMovingAverages25D + ", volumeMovingAverages50D="
            + volumeMovingAverages50D + ", openPriceNetChange=" + openPriceNetChange
            + ", latestReportedCashDividend=" + latestReportedCashDividend + ", marketValueScalingFactor="
            + marketValueScalingFactor + ", exDividendTradeDate=" + exDividendTradeDate
            + ", previousDisplayTemplate=" + previousDisplayTemplate + ", extendedPriceQualifierFid="
            + extendedPriceQualifierFid + ", minimumPriceMovement=" + minimumPriceMovement
            + ", officialClosingPrice=" + officialClosingPrice + ", quoteDate=" + quoteDate
            + ", volumeWeightedAveragePrice=" + volumeWeightedAveragePrice + ", bidAskDate=" + bidAskDate
            + ", isinCode=" + isinCode + ", rtrsOpeningPrice=" + rtrsOpeningPrice + ", sedolCode=" + sedolCode
            + ", marketSegment=" + marketSegment + ", regularTradesTimeMillis=" + regularTradesTimeMillis
            + ", allTradesTimeMillis=" + allTradesTimeMillis + ", quoteTimeMillis=" + quoteTimeMillis
            + ", correctionTimeMillis=" + correctionTimeMillis + ", financialStatusIndicator="
            + financialStatusIndicator + ", lastTradeSubMarketIndicator=" + lastTradeSubMarketIndicator
            + ", irgPriceSubmarketIndicator=" + irgPriceSubmarketIndicator + ", volumeScaling=" + volumeScaling
            + ", exchangeCode=" + exchangeCode + ", oddBestAsk=" + oddBestAsk + ", oddBestAskSize=" + oddBestAskSize
            + ", oddBestBid=" + oddBestBid + ", oddBestBidSize=" + oddBestBidSize + ", roundVolume=" + roundVolume
            + ", organizationId=" + organizationId + ", priceUpdateFrequency=" + priceUpdateFrequency
            + ", rcsAssetClassification=" + rcsAssetClassification + ", underlyingIndex=" + underlyingIndex
            + ", futuresChainRic=" + futuresChainRic + ", optionsChainRic=" + optionsChainRic + ", strikesCoverage="
            + strikesCoverage + ", newsTimeMillis=" + newsTimeMillis + ", tradeThroughExemptFlags="
            + tradeThroughExemptFlags + ", companyComplianceStatus=" + companyComplianceStatus
            + ", irgSubMarketCenterId=" + irgSubMarketCenterId + ", subMarketCenterId=" + subMarketCenterId
            + ", activeDomesticExchangeIds=" + activeDomesticExchangeIds + ", activeOtherExchangeIds="
            + activeOtherExchangeIds + ", tradePriceQualifier2=" + tradePriceQualifier2
            + ", latestCapitalChangeEffectiveDate=" + latestCapitalChangeEffectiveDate + ", marketParticipantBidId="
            + marketParticipantBidId + ", row64_1=" + row64_1 + ", row64_2=" + row64_2 + ", row64_3=" + row64_3
            + ", row64_4=" + row64_4 + ", row64_5=" + row64_5 + ", row64_6=" + row64_6 + ", row64_7=" + row64_7
            + ", row64_8=" + row64_8 + ", row64_9=" + row64_9 + ", row64_10=" + row64_10 + ", row64_11=" + row64_11
            + ", row64_12=" + row64_12 + ", row64_13=" + row64_13 + ", row64_14=" + row64_14
            + ", marketParticipantAskId=" + marketParticipantAskId + ", putCall=" + putCall
            + ", impliedVolatilitytOfAskPrice=" + impliedVolatilitytOfAskPrice + ", impliedVolatilitytOfBidPrice="
            + impliedVolatilitytOfBidPrice + ", openInterest=" + openInterest + ", openInterestNetChange="
            + openInterestNetChange + ", strikePrice=" + strikePrice + ", contractMonth=" + contractMonth
            + ", lotSizeUnits=" + lotSizeUnits + ", openAskPrice=" + openAskPrice + ", expiryDate=" + expiryDate
            + ", settlementPrice=" + settlementPrice + ", settleDate=" + settleDate + ", toString()="
            + super.toString() + "]";
    }
}
