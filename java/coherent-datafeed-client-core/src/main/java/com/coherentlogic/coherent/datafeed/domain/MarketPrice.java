package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_PRICE;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Qualifier;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDateTimeAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.Changeable;
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
public class MarketPrice extends AbstractCommonProperties {

    private static final long serialVersionUID = -8330990635265356088L;

    /**
     * Display information for the IDN terminal device.
     *
     * RDNDISPLAY: UINT32 though treat this as a UINT as UINT32 has been
     *             deprecated.
     */
    @XStreamAlias(MarketPriceConstants.RDNDISPLAY)
    private BigInteger displayTemplate = null;

    /**
     * Identifier for the market on which the instrument trades.
     *
     * @deprecated (From the RDMFieldDictionary) Use field RDN_EXCHD2 #1709.
     */
    @XStreamAlias(MarketPriceConstants.RDN_EXCHID)
    private String idnExchangeId = null;

    /**
     * Full or abbreviated text instrument name.
     */
    @XStreamAlias(MarketPriceConstants.DSPLY_NAME)
    private String displayName = null;

    /**
     * BID: REAL
     */
    @XStreamAlias(MarketPriceConstants.BID)
    private BigDecimal bid = null;

    /**
     * BID: REAL
     */
    @XStreamAlias(MarketPriceConstants.BID_1)
    private BigDecimal bid1 = null;

    /**
     * BID: REAL
     */
    @XStreamAlias(MarketPriceConstants.BID_2)
    private BigDecimal bid2 = null;

    /**
     * ASK: REAL
     */
    @XStreamAlias(MarketPriceConstants.ASK)
    private BigDecimal ask = null;

    /**
     * ASK: REAL
     */
    @XStreamAlias(MarketPriceConstants.ASK_1)
    private BigDecimal ask1 = null;

    /**
     * ASK: REAL
     */
    @XStreamAlias(MarketPriceConstants.ASK_2)
    private BigDecimal ask2 = null;

    /**
     * BIDSIZE: REAL
     */
    @XStreamAlias(MarketPriceConstants.BIDSIZE)
    private BigInteger bidSize = null;

    /**
     * ASKSIZE: REAL
     */
    @XStreamAlias(MarketPriceConstants.ASKSIZE)
    private BigInteger askSize = null;

    /**
     * Trade price 1
     *
     * LAST: REAL
     */
    @XStreamAlias(MarketPriceConstants.TRDPRC_1)
    private BigDecimal last = null;

    /**
     * Trade price 2
     *
     * LAST: REAL
     */
    @XStreamAlias(MarketPriceConstants.TRDPRC_2)
    private BigDecimal last1 = null;

    /**
     * Trade price 3
     *
     * REAL
     */
    @XStreamAlias(MarketPriceConstants.TRDPRC_3)
    private BigDecimal last2 = null;

    /**
     * Trade price 4
     *
     * REAL
     */
    @XStreamAlias(MarketPriceConstants.TRDPRC_4)
    private BigDecimal last3 = null;

    /**
     * Trade price 5
     *
     * REAL
     */
    @XStreamAlias(MarketPriceConstants.TRDPRC_5)
    private BigDecimal last4 = null;

    /**
     * Net change.
     */
    @XStreamAlias(MarketPriceConstants.NETCHNG_1)
    private BigDecimal netChange = null;

    @XStreamAlias(MarketPriceConstants.HIGH_1)
    private BigDecimal todaysHigh = null;

    @XStreamAlias(MarketPriceConstants.LOW_1)
    private BigDecimal todaysLow = null;

    /**
     * Tick up/down arrow.
     */
    @XStreamAlias(MarketPriceConstants.PRCTCK_1)
    private Integer tickArrow = null;

//    @XStreamAlias(CURRENCY)
//    private String currency = null;

    @XStreamAlias(MarketPriceConstants.TRADE_DATE)
    private Long tradeDateMillis = null;

    @XStreamAlias(MarketPriceConstants.TRDTIM_1)
    private Long tradeTimeMillis = null;

    @XStreamAlias(MarketPriceConstants.OPEN_PRC)
    private BigDecimal openPrice = null;

    @XStreamAlias(MarketPriceConstants.HST_CLOSE)
    private BigDecimal historicClose = null;

    @XStreamAlias(MarketPriceConstants.NEWS)
    private String news = null;

    @XStreamAlias(MarketPriceConstants.NEWS_TIME)
    private Long newsTime = null;

    @XStreamAlias(MarketPriceConstants.ACVOL_1)
    private BigInteger volumeAccumulated = null;

    @XStreamAlias(MarketPriceConstants.EARNINGS)
    private BigDecimal earnings = null;

    @XStreamAlias(MarketPriceConstants.YIELD)
    private BigDecimal yield = null;

    @XStreamAlias(MarketPriceConstants.PERATIO)
    private BigDecimal priceToEarningsRatio = null;

    @XStreamAlias(MarketPriceConstants.DIVIDENDTP)
    private String dividendType = null;

    @XStreamAlias(MarketPriceConstants.DIVPAYDATE)
    private Long dividendPayDate = null;

    @XStreamAlias(MarketPriceConstants.EXDIVDATE)
    private Long exDividendDate = null;

    @XStreamAlias(MarketPriceConstants.CTS_QUAL)
    private String tradePriceQualifier = null;

    @XStreamAlias(MarketPriceConstants.BLKCOUNT)
    private BigInteger blockCount = null;

    @XStreamAlias(MarketPriceConstants.BLKVOLUM)
    private BigInteger blockVolume = null;

    @XStreamAlias(MarketPriceConstants.TRDXID_1)
    private String tradeExchangeId = null;

    @XStreamAlias(MarketPriceConstants.TRD_UNITS)
    private String tradingUnits = null;

    @XStreamAlias(MarketPriceConstants.LOT_SIZE)
    private BigInteger lotSize = null;

    @XStreamAlias(MarketPriceConstants.PCTCHNG)
    private BigDecimal percentChange = null;

    @XStreamAlias(MarketPriceConstants.OPEN_BID)
    private BigDecimal openBid = null;

    @XStreamAlias(MarketPriceConstants.DJTIME)
    private Long latestDowJonesNewsStoryTime = null;

    @XStreamAlias(MarketPriceConstants.CLOSE_BID)
    private BigDecimal closeBid = null;

    @XStreamAlias(MarketPriceConstants.CLOSE_ASK)
    private BigDecimal closeAsk = null;

    @XStreamAlias(MarketPriceConstants.DIVIDEND)
    private BigDecimal dividend = null;

    @XStreamAlias(MarketPriceConstants.NUM_MOVES)
    private BigInteger totalTradesToday = null;

    @XStreamAlias(MarketPriceConstants.OFFCL_CODE)
    private String officialCode = null;

    @XStreamAlias(MarketPriceConstants.HSTCLSDATE)
    private Long historicCloseDate = null;

    @XStreamAlias(MarketPriceConstants.YRHIGH)
    private BigDecimal yearHigh = null;

    @XStreamAlias(MarketPriceConstants.YRLOW)
    private BigDecimal yearLow = null;

    @XStreamAlias(MarketPriceConstants.TURNOVER)
    private BigDecimal turnover = null;

    @XStreamAlias(MarketPriceConstants.BOND_TYPE)
    private String bondType = null;

    @XStreamAlias(MarketPriceConstants.BCKGRNDPAG)
    private String backgroundPage = null;

    @XStreamAlias(MarketPriceConstants.YCHIGH_IND)
    private String yearOrContractHighIndicator = null;

    @XStreamAlias(MarketPriceConstants.YCLOW_IND)
    private String yearOrContractLowIndicator = null;

    @XStreamAlias(MarketPriceConstants.BID_NET_CH)
    private BigDecimal bidNetChange = null;

    /**
     * @todo Rename this property as the name is ugly.
     */
    @XStreamAlias(MarketPriceConstants.BID_TICK_1)
    private String bidTick1 = null;

    @XStreamAlias(MarketPriceConstants.CUM_EX_MKR)
    private String cumExMarker = null;

    @XStreamAlias(MarketPriceConstants.PRC_QL_CD)
    private String priceCode = null;

    @XStreamAlias(MarketPriceConstants.NASDSTATUS)
    private String nasdStatus = null;

    @XStreamAlias(MarketPriceConstants.PRC_QL2)
    private String priceCode2 = null;

    @XStreamAlias(MarketPriceConstants.TRDVOL_1)
    private BigInteger tradeVolume = null;

    @XStreamAlias(MarketPriceConstants.BID_HIGH_1)
    private BigDecimal todaysHighBid = null;
    
    @XStreamAlias(MarketPriceConstants.BID_LOW_1)
    private BigDecimal todaysLowBid = null;
    
    @XStreamAlias(MarketPriceConstants.YRBIDHIGH)
    private BigDecimal yearHighBid = null;

    @XStreamAlias(MarketPriceConstants.YRBIDLOW)
    private BigDecimal yearLowBid = null;

    @XStreamAlias(MarketPriceConstants.HST_CLSBID)
    private BigDecimal historicalClosingBid = null;

    @XStreamAlias(MarketPriceConstants.HSTCLBDDAT)
    private Long historicalClosingBidDate = null;

    @XStreamAlias(MarketPriceConstants.YRBDHI_IND)
    private String yearBidHigh = null;

    @XStreamAlias(MarketPriceConstants.YRBDLO_IND)
    private String yearBidLow = null;

    @XStreamAlias(MarketPriceConstants.NUM_BIDS)
    private BigInteger numberOfBids = null;

//    @XStreamAlias(RECORDTYPE)
//    private BigInteger recordType = null;

    @XStreamAlias(MarketPriceConstants.OPTION_XID)
    private String optionExchangeId = null;

    @XStreamAlias(MarketPriceConstants.YRHIGHDAT)
    private Long yearHighDate = null;

    @XStreamAlias(MarketPriceConstants.YRLOWDAT)
    private Long yearLowDate = null;

    @XStreamAlias(MarketPriceConstants.IRGPRC)
    private BigDecimal irgPrice = null;

    @XStreamAlias(MarketPriceConstants.IRGVOL)
    private BigInteger irgVolume = null;

    @XStreamAlias(MarketPriceConstants.IRGCOND)
    private String irgPriceType = null;

    @XStreamAlias(MarketPriceConstants.TIMCOR)
    private Long priceCorrectionTime = null;

    @XStreamAlias(MarketPriceConstants.INSPRC)
    private BigDecimal insertPrice = null;

    @XStreamAlias(MarketPriceConstants.INSVOL)
    private BigInteger insertVolume = null;

    @XStreamAlias(MarketPriceConstants.INSCOND)
    private String insertPriceType = null;

    @XStreamAlias(MarketPriceConstants.SALTIM)
    private Long lastTime = null;

    @XStreamAlias(MarketPriceConstants.TNOVER_SC)
    private String turnoverScale = null;

    @XStreamAlias(MarketPriceConstants.BCAST_REF)
    private String broadcastXRef = null;

    @XStreamAlias(MarketPriceConstants.CROSS_SC)
    private String crossRateScale = null;

    @XStreamAlias(MarketPriceConstants.AMT_OS)
    private BigDecimal amountOutstanding = null;

    @XStreamAlias(MarketPriceConstants.AMT_OS_SC)
    private String amountOutstandingScale = null;

    @XStreamAlias(MarketPriceConstants.OFF_CD_IND)
    private String officialCodeIndicator = null;

    @XStreamAlias(MarketPriceConstants.PRC_VOLTY)
    private BigDecimal priceVolatility = null;

    /**
     * The date when the shares outstanding was reported.
     */
    @XStreamAlias(MarketPriceConstants.AMT_OS_DAT)
    private Long amountOutstandingDate = null;

    @XStreamAlias(MarketPriceConstants.BKGD_REF)
    private String backgroundReference = null;

    @XStreamAlias(MarketPriceConstants.GEN_VAL1)
    private BigDecimal generalPurposeValue1 = null;

    @XStreamAlias(MarketPriceConstants.GV1_TEXT)
    private String generalPurposeValue1Description = null;

    @XStreamAlias(MarketPriceConstants.GEN_VAL2)
    private BigDecimal generalPurposeValue2 = null;

    @XStreamAlias(MarketPriceConstants.GV2_TEXT)
    private String generalPurposeValue2Description = null;
    
    @XStreamAlias(MarketPriceConstants.GEN_VAL3)
    private BigDecimal generalPurposeValue3 = null;

    @XStreamAlias(MarketPriceConstants.GV3_TEXT)
    private String generalPurposeValue3Description = null;
    
    @XStreamAlias(MarketPriceConstants.GEN_VAL4)
    private BigDecimal generalPurposeValue4 = null;

    @XStreamAlias(MarketPriceConstants.GV4_TEXT)
    private String generalPurposeValue4Description = null;

    @XStreamAlias(MarketPriceConstants.SEQNUM)
    private BigInteger sequenceNumber = null;

    @XStreamAlias(MarketPriceConstants.PRNTYP)
    private String printType = null;

    @XStreamAlias(MarketPriceConstants.PRNTBCK)
    private BigInteger alteredTradeEventSequenceNumber = null;

    @XStreamAlias(MarketPriceConstants.QUOTIM)
    private Long quoteTimeSeconds = null;

    @XStreamAlias(MarketPriceConstants.GV1_FLAG)
    private String genericFlag1 = null;

    @XStreamAlias(MarketPriceConstants.GV2_FLAG)
    private String genericFlag2 = null;

    @XStreamAlias(MarketPriceConstants.GV3_FLAG)
    private String genericFlag3 = null;

    @XStreamAlias(MarketPriceConstants.GV4_FLAG)
    private String genericFlag4 = null;

    @XStreamAlias(MarketPriceConstants.OFF_CD_IN2)
    private String uniqueInstrumentId2Source = null;

    @XStreamAlias(MarketPriceConstants.OFFC_CODE2)
    private String uniqueInstrumentId2 = null;

    @XStreamAlias(MarketPriceConstants.GV1_TIME)
    private Long timeInSeconds1 = null;

    @XStreamAlias(MarketPriceConstants.GV2_TIME)
    private Long timeInSeconds2 = null;

    @XStreamAlias(MarketPriceConstants.EXCHTIM)
    private Long exchangeTime = null;

    @XStreamAlias(MarketPriceConstants.YRHI_IND)
    private String yearHighIndicator = null;

    @XStreamAlias(MarketPriceConstants.YRLO_IND)
    private String yearLowIndicator = null;

    @XStreamAlias(MarketPriceConstants.BETA_VAL)
    private BigDecimal betaValue = null;

    /**
     * This is a UINT32 / binary so I'm marking this as a int for the moment.
     */
    @XStreamAlias(MarketPriceConstants.PREF_DISP)
    private Integer preferredDisplayTemplateNumber = null;

    @XStreamAlias(MarketPriceConstants.DSPLY_NMLL)
    private String localLanguageInstrumentName = null;

    @XStreamAlias(MarketPriceConstants.VOL_X_PRC1)
    private BigDecimal latestTradeOrTradeTurnoverValue = null;

    @XStreamAlias(MarketPriceConstants.DSO_ID)
    private Integer dataSourceOwnerId = null;

    @XStreamAlias(MarketPriceConstants.AVERG_PRC)
    private BigDecimal averagePrice = null;

    @XStreamAlias(MarketPriceConstants.UPC71_REST)
    private String upc71RestrictedFlag = null;
    
    @XStreamAlias(MarketPriceConstants.ADJUST_CLS)
    private BigDecimal adjustedClose = null;

    @XStreamAlias(MarketPriceConstants.WEIGHTING)
    private BigDecimal weighting = null;

    @XStreamAlias(MarketPriceConstants.STOCK_TYPE)
    private String stockType = null;

    @XStreamAlias(MarketPriceConstants.IMP_VOLT)
    private BigDecimal impliedVolatility = null;

//    @XStreamAlias(RDN_EXCHD2)
//    private String exchangeId2 = null;

    @XStreamAlias(MarketPriceConstants.CP_ADJ_FCT)
    private BigDecimal capitalAdjustmentFactor = null;

    @XStreamAlias(MarketPriceConstants.CP_ADJ_DAT)
    private Long capitalAdjustmentDate = null;

    @XStreamAlias(MarketPriceConstants.AMT_ISSUE)
    private BigInteger sharesIssuedTotal = null;

    @XStreamAlias(MarketPriceConstants.MKT_VALUE)
    private BigDecimal marketValue = null;

    @XStreamAlias(MarketPriceConstants.SPEC_TRADE)
    private Integer specialTermsTradingFlag = null;

    @XStreamAlias(MarketPriceConstants.FCAST_EARN)
    private BigDecimal forecastedEarnings = null;

    @XStreamAlias(MarketPriceConstants.EARANK_RAT)
    private BigDecimal earningsRankRatio = null;

    @XStreamAlias(MarketPriceConstants.FCAST_DATE)
    private Long forecastDate = null;

    /**
     * Data buffer
     */
    @XStreamAlias(MarketPriceConstants.YEAR_FCAST)
    private String forecastYear = null;

    /**
     * Enum
     */
    @XStreamAlias(MarketPriceConstants.IRGMOD)
    private String irgPriceTypeModifier = null;

    @XStreamAlias(MarketPriceConstants.INSMOD)
    private String insertPriceTypeModifier = null;

    @XStreamAlias(MarketPriceConstants.A_NPLRS_1)
    private BigInteger askPlayersLevel1 = null;

    @XStreamAlias(MarketPriceConstants.B_NPLRS_1)
    private BigInteger bidPlayersLevel1 = null;

    @XStreamAlias(MarketPriceConstants.GV3_TIME)
    private Long genericTime3 = null;

    @XStreamAlias(MarketPriceConstants.GV4_TIME)
    private Long genericTime4 = null;

    /**
     * Deprecated in favor of MKT_VAL (note this is available in this api
     * already).
     */
    @XStreamAlias(MarketPriceConstants.MKT_CAP)
    private BigInteger marketCapitalisation = null;

    @XStreamAlias(MarketPriceConstants.IRGFID)
    private BigInteger irgCorrectionValueFid = null;

    @XStreamAlias(MarketPriceConstants.IRGVAL)
    private BigInteger irgCorrectionValue = null;

    @XStreamAlias(MarketPriceConstants.PCT_ABNVOL)
    private BigDecimal abnormalVolumeIncreasePercentage = null;

    @XStreamAlias(MarketPriceConstants.BC_10_50K)
    private BigInteger blockTransactionsBetween10KAnd50KShares = null;

    @XStreamAlias(MarketPriceConstants.BC_50_100K)
    private BigInteger blockTransactionsBetween50KAnd100KShares = null;

    @XStreamAlias(MarketPriceConstants.BC_100K)
    private BigInteger blockTransactionsAbove100KShares = null;

    @XStreamAlias(MarketPriceConstants.PMA_50D)
    private BigDecimal priceMovingAverages50D = null;

    @XStreamAlias(MarketPriceConstants.PMA_150D)
    private BigDecimal priceMovingAverages150D = null;

    @XStreamAlias(MarketPriceConstants.PMA_200D)
    private BigDecimal priceMovingAverages200D = null;

    @XStreamAlias(MarketPriceConstants.VMA_10D)
    private BigInteger volumeMovingAverages10D = null;

    @XStreamAlias(MarketPriceConstants.VMA_25D)
    private BigInteger volumeMovingAverages25D = null;

    @XStreamAlias(MarketPriceConstants.VMA_50D)
    private BigInteger volumeMovingAverages50D = null;

    @XStreamAlias(MarketPriceConstants.OPN_NETCH)
    private BigDecimal openPriceNetChange = null;

    @XStreamAlias(MarketPriceConstants.CASH_EXDIV)
    private BigDecimal latestReportedCashDividend = null;

    /**
     * Enum
     */
    @XStreamAlias(MarketPriceConstants.MKT_VAL_SC)
    private String marketValueScalingFactor = null;

    @XStreamAlias(MarketPriceConstants.CASH_EXDAT)
    private Long exDividendTradeDate = null;

    /**
     * Binary
     */
    @XStreamAlias(MarketPriceConstants.PREV_DISP)
    private Integer previousDisplayTemplate = null;

    /**
     * Enum
     */
    @XStreamAlias(MarketPriceConstants.PRC_QL3)
    private String extendedPriceQualifierFid = null;

    /**
     * Enum
     */
    @XStreamAlias(MarketPriceConstants.MPV)
    private String minimumPriceMovement = null;

    @XStreamAlias(MarketPriceConstants.OFF_CLOSE)
    private BigDecimal officialClosingPrice = null;

    @XStreamAlias(MarketPriceConstants.QUOTE_DATE)
    private Long quoteDate = null;

    @XStreamAlias(MarketPriceConstants.VWAP)
    private BigDecimal volumeWeightedAveragePrice = null;

//    @XStreamAlias(PROV_SYMB)
//    private String providerSymbol = null;

    @XStreamAlias(MarketPriceConstants.BID_ASK_DT)
    private Long bidAskDate = null;

    /**
     * International Security Identification Number code (ISIN).
     */
    @XStreamAlias(MarketPriceConstants.ISIN_CODE)
    private String isinCode = null;

//    @XStreamAlias(MNEMONIC)
//    private String exchangeId = null;

    @XStreamAlias(MarketPriceConstants.RTR_OPN_PR)
    private BigDecimal rtrsOpeningPrice = null;

    @XStreamAlias(MarketPriceConstants.SEDOL)
    private String sedolCode = null;

    @XStreamAlias(MarketPriceConstants.MKT_SEGMNT)
    private String marketSegment = null;

    /**
     * TODO: This is an Int/UINT64 -- need to review the conversion.
     */
    @XStreamAlias(MarketPriceConstants.TRDTIM_MS)
    private Long regularTradesTimeMillis = null;

    /**
     * TODO: This is an Int/UINT64 -- need to review the conversion.
     */
    @XStreamAlias(MarketPriceConstants.SALTIM_MS)
    private Long allTradesTimeMillis = null;

    /**
     * TODO: This is an Int/UINT64 -- need to review the conversion.
     */
    @XStreamAlias(MarketPriceConstants.QUOTIM_MS)
    private Long quoteTimeMillis = null;

    /**
     * @deprecated Convert type to Long.
     * @TODO: Convert type to Long.
     */
    @XStreamAlias(MarketPriceConstants.TIMCOR_MS)
    private BigInteger correctionTimeMillis = null;

    @XStreamAlias(MarketPriceConstants.FIN_STATUS)
    private String financialStatusIndicator = null;

    @XStreamAlias(MarketPriceConstants.LS_SUBIND)
    private String lastTradeSubMarketIndicator = null;

    @XStreamAlias(MarketPriceConstants.IRG_SUBIND)
    private String irgPriceSubmarketIndicator = null;

    @XStreamAlias(MarketPriceConstants.ACVOL_SC)
    private String volumeScaling = null;

    @XStreamAlias(MarketPriceConstants.EXCHCODE)
    private String exchangeCode = null;

    @XStreamAlias(MarketPriceConstants.ODD_ASK)
    private BigDecimal oddBestAsk = null;

    @XStreamAlias(MarketPriceConstants.ODD_ASKSIZ)
    private BigInteger oddBestAskSize = null;

    @XStreamAlias(MarketPriceConstants.ODD_BID)
    private BigDecimal oddBestBid = null;

    @XStreamAlias(MarketPriceConstants.ODD_BIDSIZ)
    private BigInteger oddBestBidSize = null;

    @XStreamAlias(MarketPriceConstants.ROUND_VOL)
    private BigInteger roundVolume = null;

    @XStreamAlias(MarketPriceConstants.ORGID)
    private BigInteger organizationId = null;

    @XStreamAlias(MarketPriceConstants.PR_FREQ)
    private String priceUpdateFrequency = null;

    /**
     * Reuters Classification Scheme
     */
    @XStreamAlias(MarketPriceConstants.RCS_AS_CLA)
    private String rcsAssetClassification = null;

    @XStreamAlias(MarketPriceConstants.UNDR_INDEX)
    private String underlyingIndex = null;

    @XStreamAlias(MarketPriceConstants.FUTURES)
    private String futuresChainRic = null;

    @XStreamAlias(MarketPriceConstants.OPTIONS)
    private String optionsChainRic = null;

    @XStreamAlias(MarketPriceConstants.STRIKES)
    private String strikesCoverage = null;

    @XStreamAlias(MarketPriceConstants.NEWSTM_MS)
    private BigInteger newsTimeMillis = null;

    @XStreamAlias(MarketPriceConstants.TRD_THRU_X)
    private String tradeThroughExemptFlags = null;

    @XStreamAlias(MarketPriceConstants.FIN_ST_IND)
    private String companyComplianceStatus = null;
    
    @XStreamAlias(MarketPriceConstants.IRG_SMKTID)
    private String irgSubMarketCenterId = null;
    
    @XStreamAlias(MarketPriceConstants.SUB_MKT_ID)
    private String subMarketCenterId = null;

    /**
     * Docs say markets -- so we're using ids.
     */
    @XStreamAlias(MarketPriceConstants.ACT_DOM_EX)
    private String activeDomesticExchangeIds = null;

    @XStreamAlias(MarketPriceConstants.ACT_OTH_EX)
    private String activeOtherExchangeIds = null;
    
    @XStreamAlias(MarketPriceConstants.TRD_QUAL_2)
    private String tradePriceQualifier2 = null;
    
    @XStreamAlias(MarketPriceConstants.CP_EFF_DAT)
    private Long latestCapitalChangeEffectiveDate = null;

    /**
     * @deprecated The name of this property is a bit vague as this is not the
     *  bid id, it is the id of the market participant who is making the bid.
     */
    @XStreamAlias(MarketPriceConstants.BID_MMID1)
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
    @XStreamAlias(MarketPriceConstants.ASK_MMID1)
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
    @XStreamAlias(MarketPriceConstants.PUT_CALL)
    private String putCall = null;

    /**
     * Implied volatility of ASK price.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(MarketPriceConstants.IMP_VOLTA)
    private BigDecimal impliedVolatilitytOfAskPrice = null;

    /**
     * Implied volatility of BID price.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(MarketPriceConstants.IMP_VOLTB)
    private BigDecimal impliedVolatilitytOfBidPrice = null;

    /**
     * Open interest. The total number of option or futures contracts that
     * have not been closed or in the case of commodities liquidated or
     * offset by delivery.
     *
     * INTEGER/REAL64
     */
    @XStreamAlias(MarketPriceConstants.OPINT_1)
    private BigInteger openInterest = null;

    /**
     * Open interest net change. The difference between the current and
     * previous trading day open interest.
     *
     * REAL64
     */
    @XStreamAlias(MarketPriceConstants.OPINTNC)
    private BigInteger openInterestNetChange = null;

    /**
     * Strike price; the price at which an option may be exercised.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(MarketPriceConstants.STRIKE_PRC)
    private BigDecimal strikePrice = null;

    /**
     * Contract month
     *
     * ALPHANUMERIC/RMTES_STRING
     */
    @XStreamAlias(MarketPriceConstants.CONTR_MNTH)
    private String contractMonth = null;

    /**
     * Lot size units.
     * 
     * ENUMERATED/ENUM
     */
    @XStreamAlias(MarketPriceConstants.LOTSZUNITS)
    private String lotSizeUnits = null;

    /**
     * Open ask price.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(MarketPriceConstants.OPEN_ASK)
    private BigDecimal openAskPrice = null;

    /**
     * The date on which the future, option or warrant expires.
     *
     * DATE/DATE
     */
    @XStreamAlias(MarketPriceConstants.EXPIR_DATE)
    private Long expiryDate = null;

    /**
     * Settlement price. The official closing price of a futures or option
     * contract set by the clearing house at the end of the trading day.
     *
     * PRICE/REAL64
     */
    @XStreamAlias(MarketPriceConstants.SETTLE)
    private BigDecimal settlementPrice = null;

    /**
     * The date of the settlement price held in the SETTLE field.
     *
     * DATE/DATE
     */
    @XStreamAlias(MarketPriceConstants.SETTLEDATE)
    private Long settleDate = null;

    @UsingKey(type=MarketPriceConstants.RDN_EXCHID)
    public String getIdnExchangeId() {
        return idnExchangeId;
    }

    public static final String IDN_EXCHANGE_ID = "idnExchangeId";

    /**
     * @todo Test this.
     */
    @RFAType(type=MarketPriceConstants.RDN_EXCHID)
    @Adapt(using=OMMEnumAdapter.class)
    public void setIdnExchangeId(String idnExchangeId) {

        String oldValue = this.idnExchangeId;

        this.idnExchangeId = idnExchangeId;

        firePropertyChange(MarketPrice.IDN_EXCHANGE_ID, oldValue, idnExchangeId);
    }

    @UsingKey(type=MarketPriceConstants.BID)
    public BigDecimal getBid() {
        return bid;
    }

    public static final String BID = "bid";

    @RFAType(type=MarketPriceConstants.BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid(@Changeable(BID) BigDecimal bid) {
        this.bid = bid;
    }

    @UsingKey(type=MarketPriceConstants.BID_1)
    public BigDecimal getBid1() {
        return bid1;
    }

    public static final String BID_1 = "bid1";

    @RFAType(type=MarketPriceConstants.BID_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid1(BigDecimal bid1) {

        BigDecimal oldValue = this.bid1;

        this.bid1 = bid1;

        firePropertyChange(MarketPrice.BID_1, oldValue, bid1);
    }

    @UsingKey(type=MarketPriceConstants.BID_2)
    public BigDecimal getBid2() {
        return bid2;
    }

    public static final String BID_2 = "bid2";

    @RFAType(type=MarketPriceConstants.BID_2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBid2(BigDecimal bid2) {

        BigDecimal oldValue = this.bid2;

        this.bid2 = bid2;

        firePropertyChange(MarketPrice.BID_2, oldValue, bid2);
    }

    @UsingKey(type=MarketPriceConstants.ASK)
    public BigDecimal getAsk() {
        return ask;
    }

    public static final String ASK = "ask";

    /**
     * REAL64
     *
     * @param ask
     */
    @RFAType(type=MarketPriceConstants.ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAsk(@Changeable("ask") BigDecimal ask) {

        BigDecimal oldValue = this.ask;

        this.ask = ask;

        firePropertyChange(MarketPrice.ASK, oldValue, ask);
    }

    @UsingKey(type=MarketPriceConstants.ASK_1)
    public BigDecimal getAsk1() {
        return ask1;
    }

    public static final String ASK1 = "ask1";

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

    @UsingKey(type=MarketPriceConstants.ASK_2)
    public BigDecimal getAsk2() {
        return ask2;
    }

    public static final String ASK2 = "ask2";

    /**
     * REAL64
     *
     * @param ask2
     */
    @RFAType(type=MarketPriceConstants.ASK_2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAsk2(BigDecimal ask2) {

        BigDecimal oldValue = this.ask2;

        this.ask2 = ask2;

        firePropertyChange(MarketPrice.ASK2, oldValue, ask2);
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

    public static final String ASK_SIZE = "askSize";

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

    public static final String LAST = "last";

    /**
     * REAL64
     *
     * @param last
     */
    @RFAType(type=MarketPriceConstants.TRDPRC_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast(@Changeable("last") BigDecimal last) {

        BigDecimal oldValue = this.last;

        this.last = last;

        firePropertyChange(MarketPrice.LAST, oldValue, last);
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

    public static final String LAST_2 = "last2";

    /**
     * REAL64
     *
     * @param last2
     * 
     * @todo Verify that TRDPRC_3 actually is for last2 and assuming it is, add clarifying documentation.
     */
    @RFAType(type=MarketPriceConstants.TRDPRC_3)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast2(BigDecimal last2) {

        BigDecimal oldValue = this.last2;

        this.last2 = last2;

        firePropertyChange(MarketPrice.LAST_2, oldValue, last2);
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
    @RFAType(type=MarketPriceConstants.TRDPRC_4)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast3(BigDecimal last3) {

        BigDecimal oldValue = this.last3;

        this.last3 = last3;

        firePropertyChange(MarketPrice.LAST_3, oldValue, last3);
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
    @RFAType(type=MarketPriceConstants.TRDPRC_5)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLast4(BigDecimal last4) {

        BigDecimal oldValue = this.last4;

        this.last4 = last4;

        firePropertyChange(MarketPrice.LAST_4, oldValue, last4);
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
    @RFAType(type=MarketPriceConstants.RDNDISPLAY)
    @Adapt(using=OMMNumericAdapter.class)
    public void setDisplayTemplate(BigInteger displayTemplate) {

        BigInteger oldValue = this.displayTemplate;

        this.displayTemplate = displayTemplate;

        firePropertyChange(MarketPrice.DISPLAY_TEMPLATE, oldValue, displayTemplate);
    }

    public BigDecimal getNetChange() {
        return netChange;
    }

    public static final String NET_CHANGE = "netChange";

    @RFAType(type=MarketPriceConstants.NETCHNG_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setNetChange(BigDecimal netChange) {

        BigDecimal oldValue = this.netChange;

        this.netChange = netChange;

        firePropertyChange(MarketPrice.NET_CHANGE, oldValue, netChange);
    }

    @UsingKey(type=MarketPriceConstants.HIGH_1)
    public BigDecimal getTodaysHigh() {
        return todaysHigh;
    }

    public static final String TODAYS_HIGH = "todaysHigh";

    @RFAType(type=MarketPriceConstants.HIGH_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysHigh(BigDecimal todaysHigh) {

        BigDecimal oldValue = this.todaysHigh;

        this.todaysHigh = todaysHigh;

        firePropertyChange(MarketPrice.TODAYS_HIGH, oldValue, todaysHigh);
    }

    @UsingKey(type=MarketPriceConstants.LOW_1)
    public BigDecimal getTodaysLow() {
        return todaysLow;
    }

    public static final String TODAYS_LOW = "todaysLow";

    /**
     * REAL64
     *
     * @param todaysLow
     */
    @RFAType(type=MarketPriceConstants.LOW_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysLow(BigDecimal todaysLow) {

        BigDecimal oldValue = this.todaysLow;

        this.todaysLow = todaysLow;

        firePropertyChange(MarketPrice.TODAYS_LOW, oldValue, todaysLow);
    }

    @UsingKey(type=MarketPriceConstants.PRCTCK_1)
    public Integer getTickArrow() {
        return tickArrow;
    }

    public static final String TICK_ARROW = "tickArrow";

    @RFAType(type=MarketPriceConstants.PRCTCK_1)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTickArrow(Integer tickArrow) {

        Integer oldValue = this.tickArrow;

        this.tickArrow = tickArrow;

        firePropertyChange(MarketPrice.TICK_ARROW, oldValue, tickArrow);
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

    @UsingKey(type=MarketPriceConstants.TRADE_DATE)
    public Long getTradeDateMillis() {
        return tradeDateMillis;
    }

    public static final String TRADE_DATE_MILLIS = "tradeDateMillis";

    @RFAType(type=MarketPriceConstants.TRADE_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTradeDateMillis(Long tradeDateMillis) {

        Long oldValue = this.tradeDateMillis;

        this.tradeDateMillis = tradeDateMillis;

        firePropertyChange(MarketPrice.TRADE_DATE_MILLIS, oldValue, tradeDateMillis);
    }

    @UsingKey(type=MarketPriceConstants.TRDTIM_1)
    public Long getTradeTimeMillis() {
        return tradeTimeMillis;
    }

    public static final String TRADE_TIME_MILLIS = "tradeTimeMillis";

    @RFAType(type=MarketPriceConstants.TRDTIM_1)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTradeTimeMillis(Long tradeTimeMillis) {

        Long oldValue = this.tradeTimeMillis;

        this.tradeTimeMillis = tradeTimeMillis;

        firePropertyChange(MarketPrice.TRADE_TIME_MILLIS, oldValue, tradeTimeMillis);
    }

    @UsingKey(type=MarketPriceConstants.OPEN_PRC)
    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public static final String OPEN_PRICE = "openPrice";

    @RFAType(type=MarketPriceConstants.OPEN_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenPrice(BigDecimal openPrice) {

        BigDecimal oldValue = this.openPrice;

        this.openPrice = openPrice;

        firePropertyChange(MarketPrice.OPEN_PRICE, oldValue, openPrice);
    }

    @UsingKey(type=MarketPriceConstants.HST_CLOSE)
    public BigDecimal getHistoricClose() {
        return historicClose;
    }

    public static final String HISTORIC_CLOSE = "historicClose";

    @RFAType(type=MarketPriceConstants.HST_CLOSE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setHistoricClose(BigDecimal historicClose) {

        BigDecimal oldValue = this.historicClose;

        this.historicClose = historicClose;

        firePropertyChange(MarketPrice.HISTORIC_CLOSE, oldValue, historicClose);
    }

    @UsingKey(type=NEWS)
    public String getNews() {
        return news;
    }

    public static final String NEWS = "news";

    @RFAType(type=MarketPriceConstants.NEWS)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setNews(String news) {

        String oldValue = this.news;

        this.news = news;

        firePropertyChange(MarketPrice.NEWS, oldValue, news);
    }

    @UsingKey(type=NEWS_TIME)
    public Long getNewsTime() {
        return newsTime;
    }

    public static final String NEWS_TIME = "newsTime";

    @RFAType(type=MarketPriceConstants.NEWS_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setNewsTime(Long newsTime) {

        Long oldValue = this.newsTime;

        this.newsTime = newsTime;

        firePropertyChange(MarketPrice.NEWS_TIME, oldValue, newsTime);
    }

    @UsingKey(type=MarketPriceConstants.ACVOL_1)
    public BigInteger getVolumeAccumulated() {
        return volumeAccumulated;
    }

    public static final String VOLUME_ACCUMULATED = "volumeAccumulated";

    @RFAType(type=MarketPriceConstants.ACVOL_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setVolumeAccumulated(BigInteger volumeAccumulated) {

        BigInteger oldValue = this.volumeAccumulated;

        this.volumeAccumulated = volumeAccumulated;

        firePropertyChange(MarketPrice.VOLUME_ACCUMULATED, oldValue, volumeAccumulated);
    }

    @UsingKey(type=MarketPriceConstants.EARNINGS)
    public BigDecimal getEarnings() {
        return earnings;
    }

    public static final String EARNINGS = "earnings";

    @RFAType(type=MarketPriceConstants.EARNINGS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setEarnings(BigDecimal earnings) {

        BigDecimal oldValue = this.earnings;

        this.earnings = earnings;

        firePropertyChange(MarketPrice.EARNINGS, oldValue, earnings);
    }

    @UsingKey(type=MarketPriceConstants.YIELD)
    public BigDecimal getYield() {
        return yield;
    }

    public static final String YIELD = "yield";

    @RFAType(type=MarketPriceConstants.YIELD)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYield(BigDecimal yield) {

        BigDecimal oldValue = this.yield;

        this.yield = yield;

        firePropertyChange(MarketPrice.YIELD, oldValue, yield);
    }

    @UsingKey(type=MarketPriceConstants.PERATIO)
    public BigDecimal getPriceToEarningsRatio() {
        return priceToEarningsRatio;
    }

    public static final String PRICE_TO_EARNINGS_RATIO = "priceToEarningsRatio";

    @RFAType(type=MarketPriceConstants.PERATIO)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceToEarningsRatio(BigDecimal priceToEarningsRatio) {

        BigDecimal oldValue = this.priceToEarningsRatio;

        this.priceToEarningsRatio = priceToEarningsRatio;

        firePropertyChange(MarketPrice.PRICE_TO_EARNINGS_RATIO, oldValue, priceToEarningsRatio);
    }

    @UsingKey(type=MarketPriceConstants.DIVIDENDTP)
    public String getDividendType() {
        return dividendType;
    }

    public static final String DIVIDEND_TYPE = "dividendType";

    @RFAType(type=MarketPriceConstants.DIVIDENDTP)
    @Adapt(using=OMMEnumAdapter.class)
    public void setDividendType(String dividendType) {

        String oldValue = this.dividendType;

        this.dividendType = dividendType;

        firePropertyChange(MarketPrice.DIVIDEND_TYPE, oldValue, dividendType);
    }

    @UsingKey(type=MarketPriceConstants.DIVPAYDATE)
    public Long getDividendPayDate() {
        return dividendPayDate;
    }

    public static final String DIVIDEND_PAY_DATE = "dividendPayDate";

    @RFAType(type=MarketPriceConstants.DIVPAYDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setDividendPayDate(Long dividendPayDate) {

        Long oldValue = this.dividendPayDate;

        this.dividendPayDate = dividendPayDate;

        firePropertyChange(MarketPrice.DIVIDEND_PAY_DATE, oldValue, dividendPayDate);
    }

    @UsingKey(type=MarketPriceConstants.EXDIVDATE)
    public Long getExDividendDate() {
        return exDividendDate;
    }

    public static final String EX_DIVIDEND_DATE = "exDividendDate";

    @RFAType(type=MarketPriceConstants.EXDIVDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExDividendDate(Long exDividendDate) {

        Long oldValue = this.exDividendDate;

        this.exDividendDate = exDividendDate;

        firePropertyChange(MarketPrice.EX_DIVIDEND_DATE, oldValue, exDividendDate);
    }

    @UsingKey(type=MarketPriceConstants.CTS_QUAL)
    public String getTradePriceQualifier() {
        return tradePriceQualifier;
    }

    public static final String TRADE_PRICE_QUALIFIER = "tradePriceQualifier";

    @RFAType(type=MarketPriceConstants.CTS_QUAL)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradePriceQualifier(String tradePriceQualifier) {

        String oldValue = this.tradePriceQualifier;

        this.tradePriceQualifier = tradePriceQualifier;

        firePropertyChange(MarketPrice.TRADE_PRICE_QUALIFIER, oldValue, tradePriceQualifier);
    }

    @UsingKey(type=MarketPriceConstants.BLKCOUNT)
    public BigInteger getBlockCount() {
        return blockCount;
    }

    public static final String BLOCK_COUNT = "blockCount";

    @RFAType(type=MarketPriceConstants.BLKCOUNT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockCount(BigInteger blockCount) {

        BigInteger oldValue = this.blockCount;

        this.blockCount = blockCount;

        firePropertyChange(MarketPrice.BLOCK_COUNT, oldValue, blockCount);
    }

    @UsingKey(type=MarketPriceConstants.BLKVOLUM)
    public BigInteger getBlockVolume() {
        return blockVolume;
    }

    public static final String BLOCK_VOLUME = "blockVolume";

    @RFAType(type=MarketPriceConstants.BLKVOLUM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBlockVolume(BigInteger blockVolume) {

        BigInteger oldValue = this.blockVolume;

        this.blockVolume = blockVolume;

        firePropertyChange(MarketPrice.BLOCK_VOLUME, oldValue, blockVolume);
    }

    @UsingKey(type=MarketPriceConstants.TRDXID_1)
    public String getTradeExchangeId() {
        return tradeExchangeId;
    }

    public static final String TRADE_EXCHANGE_ID = "tradeExchangeId";

    @RFAType(type=MarketPriceConstants.TRDXID_1)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradeExchangeId(String tradeExchangeId) {

        String oldValue = this.tradeExchangeId;

        this.tradeExchangeId = tradeExchangeId;

        firePropertyChange(MarketPrice.TRADE_EXCHANGE_ID, oldValue, tradeExchangeId);
    }

    @UsingKey(type=MarketPriceConstants.TRD_UNITS)
    public String getTradingUnits() {
        return tradingUnits;
    }

    public static final String TRADING_UNITS = "tradingUnits";

    @RFAType(type=MarketPriceConstants.TRD_UNITS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradingUnits(String tradingUnits) {

        String oldValue = this.tradingUnits;

        this.tradingUnits = tradingUnits;

        firePropertyChange(MarketPrice.TRADING_UNITS, oldValue, tradingUnits);
    }

    @UsingKey(type=MarketPriceConstants.LOT_SIZE)
    public BigInteger getLotSize() {
        return lotSize;
    }

    public static final String LOT_SIZE = "lotSize";

    @RFAType(type=MarketPriceConstants.LOT_SIZE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLotSize(BigInteger lotSize) {

        BigInteger oldValue = this.lotSize;

        this.lotSize = lotSize;

        firePropertyChange(MarketPrice.LOT_SIZE, oldValue, lotSize);
    }

    @UsingKey(type=MarketPriceConstants.PCTCHNG)
    public BigDecimal getPercentChange() {
        return percentChange;
    }

    public static final String PERCENTAGE_CHANGE = "percentChange";

    @RFAType(type=MarketPriceConstants.PCTCHNG)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPercentChange(BigDecimal percentChange) {

        BigDecimal oldValue = this.percentChange;

        this.percentChange = percentChange;

        firePropertyChange(MarketPrice.PERCENTAGE_CHANGE, oldValue, percentChange);
    }

    @UsingKey(type=MarketPriceConstants.OPEN_BID)
    public BigDecimal getOpenBid() {
        return openBid;
    }

    public static final String OPEN_BID = "openBid";

    @RFAType(type=MarketPriceConstants.OPEN_BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenBid(BigDecimal openBid) {

        BigDecimal oldValue = this.openBid;

        this.openBid = openBid;

        firePropertyChange(MarketPrice.OPEN_BID, oldValue, openBid);
    }

    @UsingKey(type=MarketPriceConstants.DJTIME)
    public Long getLatestDowJonesNewsStoryTime() {
        return latestDowJonesNewsStoryTime;
    }

    public static final String LATEST_DOW_JONES_NEWS_STORY_TIME = "latestDowJonesNewsStoryTime";

    @RFAType(type=MarketPriceConstants.DJTIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLatestDowJonesNewsStoryTime(Long latestDowJonesNewsStoryTime) {

        Long oldValue = this.latestDowJonesNewsStoryTime;

        this.latestDowJonesNewsStoryTime = latestDowJonesNewsStoryTime;

        firePropertyChange(MarketPrice.LATEST_DOW_JONES_NEWS_STORY_TIME, oldValue, latestDowJonesNewsStoryTime);
    }

    @UsingKey(type=MarketPriceConstants.CLOSE_BID)
    public BigDecimal getCloseBid() {
        return closeBid;
    }

    public static final String CLOSE_BID = "closeBid";

    @RFAType(type=MarketPriceConstants.CLOSE_BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCloseBid(BigDecimal closeBid) {

        BigDecimal oldValue = this.closeBid;

        this.closeBid = closeBid;

        firePropertyChange(MarketPrice.CLOSE_BID, oldValue, closeBid);
    }

    @UsingKey(type=MarketPriceConstants.CLOSE_ASK)
    public BigDecimal getCloseAsk() {
        return closeAsk;
    }

    public static final String CLOSE_ASK = "closeAsk";

    @RFAType(type=MarketPriceConstants.CLOSE_ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCloseAsk(BigDecimal closeAsk) {

        BigDecimal oldValue = this.closeAsk;

        this.closeAsk = closeAsk;

        firePropertyChange(MarketPrice.CLOSE_ASK, oldValue, closeAsk);
    }

    @UsingKey(type=MarketPriceConstants.DIVIDEND)
    public BigDecimal getDividend() {
        return dividend;
    }

    public static final String DIVIDEND = "dividend";

    @RFAType(type=MarketPriceConstants.DIVIDEND)
    @Adapt(using=OMMNumericAdapter.class)
    public void setDividend(BigDecimal dividend) {

        BigDecimal oldValue = this.dividend;

        this.dividend = dividend;

        firePropertyChange(MarketPrice.DIVIDEND, oldValue, dividend);
    }

    @UsingKey(type=MarketPriceConstants.NUM_MOVES)
    public BigInteger getTotalTradesToday() {
        return totalTradesToday;
    }

    public static final String TOTAL_TRADES_TODAY = "totalTradesToday";

    @RFAType(type=MarketPriceConstants.NUM_MOVES)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTotalTradesToday(BigInteger totalTradesToday) {

        BigInteger oldValue = this.totalTradesToday;

        this.totalTradesToday = totalTradesToday;

        firePropertyChange(MarketPrice.TOTAL_TRADES_TODAY, oldValue, totalTradesToday);
    }

    @UsingKey(type=MarketPriceConstants.OFFCL_CODE)
    public String getOfficialCode() {
        return officialCode;
    }

    public static final String OFFICIAL_CODE = "officialCode";

    @RFAType(type=MarketPriceConstants.OFFCL_CODE)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOfficialCode(String officialCode) {

        String oldValue = this.officialCode;

        this.officialCode = officialCode;

        firePropertyChange(MarketPrice.OFFICIAL_CODE, oldValue, officialCode);
    }

    @UsingKey(type=MarketPriceConstants.HSTCLSDATE)
    public Long getHistoricCloseDate() {
        return historicCloseDate;
    }

    public static final String HISTORIC_CLOSE_DATE = "historicCloseDate";

    @RFAType(type=MarketPriceConstants.HSTCLSDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setHistoricCloseDate(Long historicCloseDate) {

        Long oldValue = this.historicCloseDate;

        this.historicCloseDate = historicCloseDate;

        firePropertyChange(MarketPrice.HISTORIC_CLOSE_DATE, oldValue, historicCloseDate);
    }

    @UsingKey(type=MarketPriceConstants.YRHIGH)
    public BigDecimal getYearHigh() {
        return yearHigh;
    }

    public static final String YEAR_HIGH = "yearHigh";

    @RFAType(type=MarketPriceConstants.YRHIGH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearHigh(BigDecimal yearHigh) {

        BigDecimal oldValue = this.yearHigh;

        this.yearHigh = yearHigh;

        firePropertyChange(MarketPrice.YEAR_HIGH, oldValue, yearHigh);
    }

    @UsingKey(type=MarketPriceConstants.YRLOW)
    public BigDecimal getYearLow() {
        return yearLow;
    }

    public static final String YEAR_LOW = "yearLow";

    @RFAType(type=MarketPriceConstants.YRLOW)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearLow(BigDecimal yearLow) {

        BigDecimal oldValue = this.yearLow;

        this.yearLow = yearLow;

        firePropertyChange(MarketPrice.YEAR_LOW, oldValue, yearLow);
    }

    @UsingKey(type=MarketPriceConstants.TURNOVER)
    public BigDecimal getTurnover() {
        return turnover;
    }

    public static final String TURNOVER = "turnover";

    @RFAType(type=MarketPriceConstants.TURNOVER)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTurnover(BigDecimal turnover) {

        BigDecimal oldValue = this.turnover;

        this.turnover = turnover;

        firePropertyChange(MarketPrice.TURNOVER, oldValue, turnover);
    }

    @UsingKey(type=MarketPriceConstants.BOND_TYPE)
    public String getBondType() {
        return bondType;
    }

    public static final String BOND_TYPE = "bondType";

    @RFAType(type=MarketPriceConstants.BOND_TYPE)
    @Adapt(using=OMMEnumAdapter.class)
    public void setBondType(String bondType) {

        String oldValue = this.bondType;

        this.bondType = bondType;

        firePropertyChange(MarketPrice.BOND_TYPE, oldValue, bondType);
    }

    @UsingKey(type=MarketPriceConstants.BCKGRNDPAG)
    public String getBackgroundPage() {
        return backgroundPage;
    }

    public static final String BACKGROUND_PAGE = "backgroundPage";

    @RFAType(type=MarketPriceConstants.BCKGRNDPAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setBackgroundPage(String backgroundPage) {

        String oldValue = this.backgroundPage;

        this.backgroundPage = backgroundPage;

        firePropertyChange(MarketPrice.BACKGROUND_PAGE, oldValue, backgroundPage);
    }

    @UsingKey(type=MarketPriceConstants.YCHIGH_IND)
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
    @RFAType(type=MarketPriceConstants.YCHIGH_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearOrContractHighIndicator(
        String yearOrContractHighIndicator) {

        String oldValue = this.yearOrContractHighIndicator;

        this.yearOrContractHighIndicator = yearOrContractHighIndicator;

        firePropertyChange(MarketPrice.YEAR_OR_CONTRACT_HIGH_INDICATOR, oldValue, yearOrContractHighIndicator);
    }

    @UsingKey(type=MarketPriceConstants.YCLOW_IND)
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
    @RFAType(type=MarketPriceConstants.YCLOW_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearOrContractLowIndicator(
        String yearOrContractLowIndicator) {

        String oldValue = this.yearOrContractLowIndicator;

        this.yearOrContractLowIndicator = yearOrContractLowIndicator;

        firePropertyChange(MarketPrice.YEAR_OR_CONTRACT_LOW_INDICATOR, oldValue, yearOrContractLowIndicator);
    }

    @UsingKey(type=MarketPriceConstants.BID_NET_CH)
    public BigDecimal getBidNetChange() {
        return bidNetChange;
    }

    public static final String BID_NET_CHANGE = "bidNetChange";

    @RFAType(type=MarketPriceConstants.BID_NET_CH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBidNetChange(BigDecimal bidNetChange) {

        BigDecimal oldValue = this.bidNetChange;

        this.bidNetChange = bidNetChange;

        firePropertyChange(MarketPrice.BID_NET_CHANGE, oldValue, bidNetChange);
    }

    @UsingKey(type=MarketPriceConstants.BID_TICK_1)
    public String getBidTick1() {
        return bidTick1;
    }

    public static final String BID_TICK_1 = "bidTick1";

    @RFAType(type=MarketPriceConstants.BID_TICK_1)
    @Adapt(using=OMMEnumAdapter.class)
    public void setBidTick1(String bidTick1) {

        String oldValue = this.bidTick1;

        this.bidTick1 = bidTick1;

        firePropertyChange(MarketPrice.BID_TICK_1, oldValue, bidTick1);
    }

    @UsingKey(type=MarketPriceConstants.CUM_EX_MKR)
    public String getCumExMarker() {
        return cumExMarker;
    }

    public static final String CUM_EX_MARKER = "cumExMarker";

    @RFAType(type=MarketPriceConstants.CUM_EX_MKR)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCumExMarker(@Changeable(CUM_EX_MARKER) String cumExMarker) {
        this.cumExMarker = cumExMarker;
    }

    @UsingKey(type=MarketPriceConstants.PRC_QL_CD)
    public String getPriceCode() {
        return priceCode;
    }

    public static final String PRICE_CODE = "priceCode";

    @RFAType(type=MarketPriceConstants.PRC_QL_CD)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceCode(@Changeable(PRICE_CODE) String priceCode) {
        this.priceCode = priceCode;
    }

    @UsingKey(type=MarketPriceConstants.NASDSTATUS)
    public String getNasdStatus() {
        return nasdStatus;
    }

    public static final String NASD_STATUS = "nasdStatus";

    @RFAType(type=MarketPriceConstants.NASDSTATUS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setNasdStatus(@Changeable(NASD_STATUS) String nasdStatus) {
        this.nasdStatus = nasdStatus;
    }

    @UsingKey(type=MarketPriceConstants.PRC_QL2)
    public String getPriceCode2() {
        return priceCode2;
    }

    public static final String PRICE_CODE_2 = "priceCode2";

    @RFAType(type=MarketPriceConstants.PRC_QL2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceCode2(@Changeable(PRICE_CODE_2) String priceCode2) {
        this.priceCode2 = priceCode2;
    }

    @UsingKey(type=MarketPriceConstants.TRDVOL_1)
    public BigInteger getTradeVolume() {
        return tradeVolume;
    }

    public static final String TRADE_VOLUME = "tradeVolume";

    @RFAType(type=MarketPriceConstants.TRDVOL_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTradeVolume(@Changeable(TRADE_VOLUME) BigInteger tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    @UsingKey(type=MarketPriceConstants.BID_HIGH_1)
    public BigDecimal getTodaysHighBid() {
        return todaysHighBid;
    }

    public static final String TODAYS_HIGH_BID = "todaysHighBid";

    @RFAType(type=MarketPriceConstants.BID_HIGH_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysHighBid(@Changeable(TODAYS_HIGH_BID) BigDecimal todaysHighBid) {
        this.todaysHighBid = todaysHighBid;
    }

    @UsingKey(type=MarketPriceConstants.BID_LOW_1)
    public BigDecimal getTodaysLowBid() {
        return todaysLowBid;
    }

    public static final String TODAYS_LOW_BID = "todaysLowBid";

    @RFAType(type=MarketPriceConstants.BID_LOW_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setTodaysLowBid(@Changeable(TODAYS_LOW_BID) BigDecimal todaysLowBid) {
        this.todaysLowBid = todaysLowBid;
    }

    @UsingKey(type=MarketPriceConstants.YRBIDHIGH)
    public BigDecimal getYearHighBid() {
        return yearHighBid;
    }

    public static final String YEAR_HIGH_BID = "yearHighBid";

    @RFAType(type=MarketPriceConstants.YRBIDHIGH)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearHighBid(@Changeable(YEAR_HIGH_BID) BigDecimal yearHighBid) {
        this.yearHighBid = yearHighBid;
    }

    @UsingKey(type=MarketPriceConstants.YRBIDLOW)
    public BigDecimal getYearLowBid() {
        return yearLowBid;
    }

    public static final String YEAR_LOW_BID = "yearLowBid";

    @RFAType(type=MarketPriceConstants.YRBIDLOW)
    @Adapt(using=OMMNumericAdapter.class)
    public void setYearLowBid(@Changeable(YEAR_LOW_BID) BigDecimal yearLowBid) {
        this.yearLowBid = yearLowBid;
    }

    @UsingKey(type=MarketPriceConstants.HST_CLSBID)
    public BigDecimal getHistoricalClosingBid() {
        return historicalClosingBid;
    }

    public static final String HISTORICAL_CLOSING_BID = "historicalClosingBid";

    @RFAType(type=MarketPriceConstants.HST_CLSBID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setHistoricalClosingBid(@Changeable(HISTORICAL_CLOSING_BID) BigDecimal historicalClosingBid) {
        this.historicalClosingBid = historicalClosingBid;
    }

    @UsingKey(type=MarketPriceConstants.HSTCLBDDAT)
    public Long getHistoricalClosingBidDate() {
        return historicalClosingBidDate;
    }

    public static final String HISTORIC_CLOSING_BID_DATE = "historicalClosingBidDate";

    @RFAType(type=MarketPriceConstants.HSTCLBDDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setHistoricalClosingBidDate(Long historicalClosingBidDate) {

        Long oldValue = this.historicalClosingBidDate;

        this.historicalClosingBidDate = historicalClosingBidDate;

        firePropertyChange(MarketPrice.HISTORIC_CLOSING_BID_DATE, oldValue, historicalClosingBidDate);
    }

    @UsingKey(type=MarketPriceConstants.YRBDHI_IND)
    public String getYearBidHigh() {
        return yearBidHigh;
    }

    public static final String YEAR_BID_HIGH = "yearBidHigh";

    @RFAType(type=MarketPriceConstants.YRBDHI_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearBidHigh(String yearBidHigh) {

        String oldValue = this.yearBidHigh;

        this.yearBidHigh = yearBidHigh;

        firePropertyChange(MarketPrice.YEAR_BID_HIGH, oldValue, yearBidHigh);
    }

    @UsingKey(type=MarketPriceConstants.YRBDLO_IND)
    public String getYearBidLow() {
        return yearBidLow;
    }

    public static final String YEAR_BID_LOW = "yearBidLow";

    @RFAType(type=MarketPriceConstants.YRBDLO_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearBidLow(String yearBidLow) {

        String oldValue = this.yearBidLow;

        this.yearBidLow = yearBidLow;

        firePropertyChange(MarketPrice.YEAR_BID_LOW, oldValue, yearBidLow);
    }

    @UsingKey(type=MarketPriceConstants.NUM_BIDS)
    public BigInteger getNumberOfBids() {
        return numberOfBids;
    }

    public static final String NUMBER_OF_BIDS = "numberOfBids";

    @RFAType(type=MarketPriceConstants.NUM_BIDS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setNumberOfBids(BigInteger numberOfBids) {

        BigInteger oldValue = this.numberOfBids;

        this.numberOfBids = numberOfBids;

        firePropertyChange(MarketPrice.NUMBER_OF_BIDS, oldValue, numberOfBids);
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

    @UsingKey(type=MarketPriceConstants.BID_MMID1)
    public String getMarketParticipantBidId() {
        return marketParticipantBidId;
    }

    public static final String MARKET_PARTICIPANT_BID_ID = "marketParticipantBidId";

    @RFAType(type=MarketPriceConstants.BID_MMID1)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setMarketParticipantBidId(@Changeable(MARKET_PARTICIPANT_BID_ID) String marketParticipantBidId) {
        this.marketParticipantBidId = marketParticipantBidId;
    }

    @UsingKey(type=MarketPriceConstants.ASK_MMID1)
    public String getMarketParticipantAskId() {
        return marketParticipantAskId;
    }

    public static final String MARKET_PARTICIPANT_ASK_ID = "marketParticipantAskId";

    @RFAType(type=MarketPriceConstants.ASK_MMID1)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setMarketParticipantAskId(@Changeable(MARKET_PARTICIPANT_ASK_ID) String marketParticipantAskId) {
        this.marketParticipantAskId = marketParticipantAskId;
    }

    @UsingKey(type=MarketPriceConstants.OPTION_XID)
    public String getOptionExchangeId() {
        return optionExchangeId;
    }

    public static final String OPTION_EXCHANGE_ID = "optionExchangeId";

    @RFAType(type=MarketPriceConstants.OPTION_XID)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOptionExchangeId(@Changeable(OPTION_EXCHANGE_ID) String optionExchangeId) {
        this.optionExchangeId = optionExchangeId;
    }

    @UsingKey(type=MarketPriceConstants.YRHIGHDAT)
    public Long getYearHighDate() {
        return yearHighDate;
    }

    public static final String YEAR_HIGH_DATE = "yearHighDate";

    @RFAType(type=MarketPriceConstants.YRHIGHDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setYearHighDate(@Changeable(YEAR_HIGH_DATE) Long yearHighDate) {
        this.yearHighDate = yearHighDate;
    }

    @UsingKey(type=MarketPriceConstants.YRLOWDAT)
    public Long getYearLowDate() {
        return yearLowDate;
    }

    public static final String YEAR_LOW_DATE = "yearLowDate";

    @RFAType(type=MarketPriceConstants.YRLOWDAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setYearLowDate(@Changeable(YEAR_LOW_DATE) Long yearLowDate) {
        this.yearLowDate = yearLowDate;
    }

    @UsingKey(type=MarketPriceConstants.IRGPRC)
    public BigDecimal getIrgPrice() {
        return irgPrice;
    }

    public static final String IRG_PRICE = "irgPrice";

    @RFAType(type=MarketPriceConstants.IRGPRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgPrice(@Changeable(IRG_PRICE) BigDecimal irgPrice) {
        this.irgPrice = irgPrice;
    }

    @UsingKey(type=MarketPriceConstants.IRGVOL)
    public BigInteger getIrgVolume() {
        return irgVolume;
    }

    public static final String IRG_VOLUME = "irgVolume";

    @RFAType(type=MarketPriceConstants.IRGVOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgVolume(@Changeable(IRG_VOLUME) BigInteger irgVolume) {
        this.irgVolume = irgVolume;
    }

    @UsingKey(type=MarketPriceConstants.IRGCOND)
    public String getIrgPriceType() {
        return irgPriceType;
    }

    public static final String IRG_PRICE_TYPE = "irgPriceType";

    @RFAType(type=MarketPriceConstants.IRGCOND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setIrgPriceType(@Changeable(IRG_PRICE_TYPE) String irgPriceType) {
        this.irgPriceType = irgPriceType;
    }

    @UsingKey(type=MarketPriceConstants.TIMCOR)
    public Long getPriceCorrectionTime() {
        return priceCorrectionTime;
    }

    public static final String PRICE_CORRECTION_TIME = "priceCorrectionTime";

    @RFAType(type=MarketPriceConstants.TIMCOR)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setPriceCorrectionTime(@Changeable(PRICE_CORRECTION_TIME) Long priceCorrectionTime) {
        this.priceCorrectionTime = priceCorrectionTime;
    }

    @UsingKey(type=MarketPriceConstants.INSPRC)
    public BigDecimal getInsertPrice() {
        return insertPrice;
    }

    public static final String INSERT_PRICE = "insertPrice";

    @RFAType(type=MarketPriceConstants.INSPRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setInsertPrice(@Changeable(INSERT_PRICE) BigDecimal insertPrice) {
        this.insertPrice = insertPrice;
    }

    @UsingKey(type=MarketPriceConstants.INSVOL)
    public BigInteger getInsertVolume() {
        return insertVolume;
    }

    public static final String INSERT_VOLUME = "insertVolume";

    @RFAType(type=MarketPriceConstants.INSVOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setInsertVolume(@Changeable(INSERT_VOLUME) BigInteger insertVolume) {
        this.insertVolume = insertVolume;
    }

    @UsingKey(type=MarketPriceConstants.INSCOND)
    public String getInsertPriceType() {
        return insertPriceType;
    }

    public static final String INSERT_PRICE_TYPE = "insertPriceType";

    @RFAType(type=MarketPriceConstants.INSCOND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setInsertPriceType(@Changeable(INSERT_PRICE_TYPE) String insertPriceType) {
        this.insertPriceType = insertPriceType;
    }

    @UsingKey(type=MarketPriceConstants.SALTIM)
    public Long getLastTime() {
        return lastTime;
    }

    public static final String LAST_TIME = "lastTime";

    @RFAType(type=MarketPriceConstants.SALTIM)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLastTime(@Changeable(LAST_TIME) Long lastTime) {
        this.lastTime = lastTime;
    }

    @UsingKey(type=MarketPriceConstants.TNOVER_SC)
    public String getTurnoverScale() {
        return turnoverScale;
    }

    public static final String TURNOVER_SCALE = "turnoverScale";

    @RFAType(type=MarketPriceConstants.TNOVER_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTurnoverScale(@Changeable(TURNOVER_SCALE) String turnoverScale) {
        this.turnoverScale = turnoverScale;
    }

    @UsingKey(type=MarketPriceConstants.BCAST_REF)
    public String getBroadcastXRef() {
        return broadcastXRef;
    }

    public static final String BROADCAST_X_REF = "broadcastXRef";

    @RFAType(type=MarketPriceConstants.BCAST_REF)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setBroadcastXRef(@Changeable(BROADCAST_X_REF) String broadcastXRef) {
        this.broadcastXRef = broadcastXRef;
    }

    @UsingKey(type=MarketPriceConstants.CROSS_SC)
    public String getCrossRateScale() {
        return crossRateScale;
    }

    public static final String CROSS_RATE_SCALE = "crossRateScale";

    @RFAType(type=MarketPriceConstants.CROSS_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setCrossRateScale(@Changeable(CROSS_RATE_SCALE) String crossRateScale) {
        this.crossRateScale = crossRateScale;
    }

    @UsingKey(type=MarketPriceConstants.AMT_OS)
    public BigDecimal getAmountOutstanding() {
        return amountOutstanding;
    }

    public static final String AMOUNT_OUTSTANDING = "amountOutstanding";

    @RFAType(type=MarketPriceConstants.AMT_OS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAmountOutstanding(@Changeable(AMOUNT_OUTSTANDING) BigDecimal amountOutstanding) {
        this.amountOutstanding = amountOutstanding;
    }

    @UsingKey(type=MarketPriceConstants.AMT_OS_SC)
    public String getAmountOutstandingScale() {
        return amountOutstandingScale;
    }

    public static final String AMOUNT_OUTSTANDING_SCALE = "amountOutstandingScale";

    @RFAType(type=MarketPriceConstants.AMT_OS_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setAmountOutstandingScale(@Changeable(AMOUNT_OUTSTANDING_SCALE) String amountOutstandingScale) {
        this.amountOutstandingScale = amountOutstandingScale;
    }

    @UsingKey(type=MarketPriceConstants.OFF_CD_IND)
    public String getOfficialCodeIndicator() {
        return officialCodeIndicator;
    }

    public static final String OFFICIAL_CODE_INDICATOR = "officialCodeIndicator";

    @RFAType(type=MarketPriceConstants.OFF_CD_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setOfficialCodeIndicator(@Changeable(OFFICIAL_CODE_INDICATOR) String officialCodeIndicator) {
        this.officialCodeIndicator = officialCodeIndicator;
    }

    @UsingKey(type=MarketPriceConstants.PRC_VOLTY)
    public BigDecimal getPriceVolatility() {
        return priceVolatility;
    }

    public static final String PRICE_VOLATILITY = "priceVolatility";

    @RFAType(type=MarketPriceConstants.PRC_VOLTY)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPriceVolatility(@Changeable(PRICE_VOLATILITY) BigDecimal priceVolatility) {
        this.priceVolatility = priceVolatility;
    }

    @UsingKey(type=MarketPriceConstants.AMT_OS_DAT)
    public Long getAmountOutstandingDate() {
        return amountOutstandingDate;
    }

    public static final String AMOUNT_OUTSTANDING_DATE = "amountOutstandingDate";

    @RFAType(type=MarketPriceConstants.AMT_OS_DAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setAmountOutstandingDate(@Changeable(AMOUNT_OUTSTANDING_DATE) Long amountOutstandingDate) {
        this.amountOutstandingDate = amountOutstandingDate;
    }

    @UsingKey(type=MarketPriceConstants.BKGD_REF)
    public String getBackgroundReference() {
        return backgroundReference;
    }

    public static final String BACKGROUND_REFERENCE = "backgroundReference";

    @RFAType(type=MarketPriceConstants.BKGD_REF)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setBackgroundReference(@Changeable(BACKGROUND_REFERENCE) String backgroundReference) {
        this.backgroundReference = backgroundReference;
    }

    @UsingKey(type=MarketPriceConstants.GEN_VAL1)
    public BigDecimal getGeneralPurposeValue1() {
        return generalPurposeValue1;
    }

    public static final String GENERAL_PURPOSE_VALUE_1 = "generalPurposeValue1";

    @RFAType(type=MarketPriceConstants.GEN_VAL1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue1(BigDecimal generalPurposeValue1) {

        BigDecimal oldValue = this.generalPurposeValue1;

        this.generalPurposeValue1 = generalPurposeValue1;

        firePropertyChange(GENERAL_PURPOSE_VALUE_1, oldValue, generalPurposeValue1);
    }

    @UsingKey(type=MarketPriceConstants.GV1_TEXT)
    public String getGeneralPurposeValue1Description() {
        return generalPurposeValue1Description;
    }

    public static final String GENERAL_PURPOSE_VALUE_1_DESCRIPTION = "generalPurposeValue1Description";

    @RFAType(type=MarketPriceConstants.GV1_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue1Description(
        String generalPurposeValue1Description) {

        String oldValue = this.generalPurposeValue1Description;

        this.generalPurposeValue1Description = generalPurposeValue1Description;

        firePropertyChange(GENERAL_PURPOSE_VALUE_1_DESCRIPTION, oldValue, generalPurposeValue1Description);
    }

    @UsingKey(type=MarketPriceConstants.GEN_VAL2)
    public BigDecimal getGeneralPurposeValue2() {
        return generalPurposeValue2;
    }

    public static final String GENERAL_PURPOSE_VALUE_2 = "generalPurposeValue2";

    @RFAType(type=MarketPriceConstants.GEN_VAL2)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue2(BigDecimal generalPurposeValue2) {

        BigDecimal oldValue = this.generalPurposeValue2;

        this.generalPurposeValue2 = generalPurposeValue2;

        firePropertyChange(GENERAL_PURPOSE_VALUE_2, oldValue, generalPurposeValue2);
    }

    @UsingKey(type=MarketPriceConstants.GV2_TEXT)
    public String getGeneralPurposeValue2Description() {
        return generalPurposeValue2Description;
    }

    public static final String GENERAL_PURPOSE_VALUE_2_DESCRIPTION = "generalPurposeValue2Description";

    @RFAType(type=MarketPriceConstants.GV2_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue2Description(
        String generalPurposeValue2Description) {

        String oldValue = this.generalPurposeValue2Description;

        this.generalPurposeValue2Description = generalPurposeValue2Description;

        firePropertyChange(GENERAL_PURPOSE_VALUE_2_DESCRIPTION, oldValue, generalPurposeValue2Description);
    }

    @UsingKey(type=MarketPriceConstants.GEN_VAL3)
    public BigDecimal getGeneralPurposeValue3() {
        return generalPurposeValue3;
    }

    public static final String GENERAL_PURPOSE_VALUE_3 = "generalPurposeValue3";

    @RFAType(type=MarketPriceConstants.GEN_VAL3)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue3(BigDecimal generalPurposeValue3) {

        BigDecimal oldValue = this.generalPurposeValue3;

        this.generalPurposeValue3 = generalPurposeValue3;

        firePropertyChange(GENERAL_PURPOSE_VALUE_3, oldValue, generalPurposeValue3);
    }

    @UsingKey(type=MarketPriceConstants.GV3_TEXT)
    public String getGeneralPurposeValue3Description() {
        return generalPurposeValue3Description;
    }

    public static final String GENERAL_PURPOSE_VALUE_3_DESCRIPTION = "generalPurposeValue3Description";

    @RFAType(type=MarketPriceConstants.GV3_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue3Description(
        String generalPurposeValue3Description) {

        String oldValue = this.generalPurposeValue3Description;

        this.generalPurposeValue3Description = generalPurposeValue3Description;

        firePropertyChange(GENERAL_PURPOSE_VALUE_3_DESCRIPTION, oldValue, generalPurposeValue3Description);
    }

    @UsingKey(type=MarketPriceConstants.GEN_VAL4)
    public BigDecimal getGeneralPurposeValue4() {
        return generalPurposeValue4;
    }

    public static final String GENERAL_PURPOSE_VALUE_4 = "generalPurposeValue4";

    @RFAType(type=MarketPriceConstants.GEN_VAL4)
    @Adapt(using=OMMNumericAdapter.class)
    public void setGeneralPurposeValue4(BigDecimal generalPurposeValue4) {

        BigDecimal oldValue = this.generalPurposeValue4;

        this.generalPurposeValue4 = generalPurposeValue4;

        firePropertyChange(GENERAL_PURPOSE_VALUE_4, oldValue, generalPurposeValue4);
    }

    @UsingKey(type=MarketPriceConstants.GV4_TEXT)
    public String getGeneralPurposeValue4Description() {
        return generalPurposeValue4Description;
    }

    public static final String GENERAL_PURPOSE_VALUE_4_DESCRIPTION = "generalPurposeValue4Description";

    @RFAType(type=MarketPriceConstants.GV4_TEXT)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGeneralPurposeValue4Description(
        String generalPurposeValue4Description) {

        String oldValue = this.generalPurposeValue4Description;

        this.generalPurposeValue4Description = generalPurposeValue4Description;

        firePropertyChange(GENERAL_PURPOSE_VALUE_4_DESCRIPTION, oldValue, generalPurposeValue4Description);
    }

    @UsingKey(type=MarketPriceConstants.SEQNUM)
    public BigInteger getSequenceNumber() {
        return sequenceNumber;
    }

    public static final String SEQUENCE_NUMBER = "sequenceNumber";

    @RFAType(type=MarketPriceConstants.SEQNUM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSequenceNumber(BigInteger sequenceNumber) {

        BigInteger oldValue = this.sequenceNumber;

        this.sequenceNumber = sequenceNumber;

        firePropertyChange(SEQUENCE_NUMBER, oldValue, sequenceNumber);
    }

    @UsingKey(type=MarketPriceConstants.PRNTYP)
    public String getPrintType() {
        return printType;
    }

    public static final String PRINT_TYPE = "printType";

    @RFAType(type=MarketPriceConstants.PRNTYP)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setPrintType(String printType) {

        String oldValue = this.printType;

        this.printType = printType;

        firePropertyChange(PRINT_TYPE, oldValue, printType);
    }

    @UsingKey(type=MarketPriceConstants.PRNTBCK)
    public BigInteger getAlteredTradeEventSequenceNumber() {
        return alteredTradeEventSequenceNumber;
    }

    public static final String ALTERED_TRADE_EVENTS_SEQUENCE_NUMBER = "alteredTradeEventSequenceNumber";

    @RFAType(type=MarketPriceConstants.PRNTBCK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAlteredTradeEventSequenceNumber(
        BigInteger alteredTradeEventSequenceNumber) {

        BigInteger oldValue = alteredTradeEventSequenceNumber;

        this.alteredTradeEventSequenceNumber = alteredTradeEventSequenceNumber;

        firePropertyChange(ALTERED_TRADE_EVENTS_SEQUENCE_NUMBER, oldValue, alteredTradeEventSequenceNumber);
    }

    @UsingKey(type=MarketPriceConstants.QUOTIM)
    public Long getQuoteTimeSeconds() {
        return quoteTimeSeconds;
    }

    public static final String QUOTE_TIME_SECONDS = "quoteTimeSeconds";

    @RFAType(type=MarketPriceConstants.QUOTIM)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setQuoteTimeSeconds(Long quoteTimeSeconds) {

        Long oldValue = this.quoteTimeSeconds;

        this.quoteTimeSeconds = quoteTimeSeconds;

        firePropertyChange(QUOTE_TIME_SECONDS, oldValue, quoteTimeSeconds);
    }

    @UsingKey(type=MarketPriceConstants.GV1_FLAG)
    public String getGenericFlag1() {
        return genericFlag1;
    }

    public static final String GENERIC_FLAG_1 = "genericFlag1";

    @RFAType(type=MarketPriceConstants.GV1_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag1(String genericFlag1) {

        String oldValue = this.genericFlag1;

        this.genericFlag1 = genericFlag1;

        firePropertyChange(GENERIC_FLAG_1, oldValue, genericFlag1);
    }

    @UsingKey(type=MarketPriceConstants.GV2_FLAG)
    public String getGenericFlag2() {
        return genericFlag2;
    }

    public static final String GENERIC_FLAG_2 = "genericFlag2";

    @RFAType(type=MarketPriceConstants.GV2_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag2(String genericFlag2) {

        String oldValue = this.genericFlag2;

        this.genericFlag2 = genericFlag2;

        firePropertyChange(GENERIC_FLAG_2, oldValue, genericFlag2);
    }

    @UsingKey(type=MarketPriceConstants.GV3_FLAG)
    public String getGenericFlag3() {
        return genericFlag3;
    }

    public static final String GENERIC_FLAG_3 = "genericFlag3";

    @RFAType(type=MarketPriceConstants.GV3_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag3(String genericFlag3) {

        String oldValue = this.genericFlag3;

        this.genericFlag3 = genericFlag3;

        firePropertyChange(GENERIC_FLAG_3, oldValue, genericFlag3);
    }

    @UsingKey(type=MarketPriceConstants.GV4_FLAG)
    public String getGenericFlag4() {
        return genericFlag4;
    }

    public static final String GENERIC_FLAG_4 = "genericFlag4";

    @RFAType(type=MarketPriceConstants.GV4_FLAG)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setGenericFlag4(String genericFlag4) {

        String oldValue = this.genericFlag4;

        this.genericFlag4 = genericFlag4;

        firePropertyChange(GENERIC_FLAG_4, oldValue, genericFlag4);
    }

    @UsingKey(type=MarketPriceConstants.OFF_CD_IN2)
    public String getUniqueInstrumentId2Source() {
        return uniqueInstrumentId2Source;
    }

    public static final String UNIQUE_INSTRUMENT_ID_2_SOURCE = "uniqueInstrumentId2Source";

    @RFAType(type=MarketPriceConstants.OFF_CD_IN2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setUniqueInstrumentId2Source(String uniqueInstrumentId2Source) {
        
        String oldValue = this.uniqueInstrumentId2Source;

        this.uniqueInstrumentId2Source = uniqueInstrumentId2Source;

        firePropertyChange(UNIQUE_INSTRUMENT_ID_2_SOURCE, oldValue, uniqueInstrumentId2Source);
    }

    @UsingKey(type=MarketPriceConstants.OFFC_CODE2)
    public String getUniqueInstrumentId2() {
        return uniqueInstrumentId2;
    }

    public static final String UNIQUE_INSTRUMENT_ID_2 = "uniqueInstrumentId2";

    @RFAType(type=MarketPriceConstants.OFFC_CODE2)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setUniqueInstrumentId2(String uniqueInstrumentId2) {

        String oldValue = this.uniqueInstrumentId2;

        this.uniqueInstrumentId2 = uniqueInstrumentId2;

        firePropertyChange(UNIQUE_INSTRUMENT_ID_2, oldValue, uniqueInstrumentId2);
    }

    @UsingKey(type=MarketPriceConstants.GV1_TIME)
    public Long getTimeInSeconds1() {
        return timeInSeconds1;
    }

    public static final String TIME_IN_SECONDS_1 = "timeInSeconds1";

    @RFAType(type=MarketPriceConstants.GV1_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTimeInSeconds1(Long timeInSeconds1) {

        Long oldValue = this.timeInSeconds1;

        this.timeInSeconds1 = timeInSeconds1;

        firePropertyChange(TIME_IN_SECONDS_1, oldValue, timeInSeconds1);
    }

    @UsingKey(type=MarketPriceConstants.GV2_TIME)
    public Long getTimeInSeconds2() {
        return timeInSeconds2;
    }

    public static final String TIME_IN_SECONDS_2 = "timeInSeconds2";

    @RFAType(type=MarketPriceConstants.GV2_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setTimeInSeconds2(Long timeInSeconds2) {

        Long oldValue = this.timeInSeconds2;

        this.timeInSeconds2 = timeInSeconds2;

        firePropertyChange(TIME_IN_SECONDS_2, oldValue, timeInSeconds2);
    }

    @UsingKey(type=MarketPriceConstants.EXCHTIM)
    public Long getExchangeTime() {
        return exchangeTime;
    }

    public static final String EXCHANGE_TIME = "exchangeTime";

    @RFAType(type=MarketPriceConstants.EXCHTIM)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExchangeTime(Long exchangeTime) {

        Long oldValue = this.exchangeTime;

        this.exchangeTime = exchangeTime;

        firePropertyChange(EXCHANGE_TIME, oldValue, exchangeTime);
    }

    @UsingKey(type=MarketPriceConstants.YRHI_IND)
    public String getYearHighIndicator() {
        return yearHighIndicator;
    }

    public static final String YEAR_HIGH_INDICATOR = "yearHighIndicator";

    @RFAType(type=MarketPriceConstants.YRHI_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearHighIndicator(String yearHighIndicator) {

        String oldValue = this.yearHighIndicator;

        this.yearHighIndicator = yearHighIndicator;

        firePropertyChange(YEAR_HIGH_INDICATOR, oldValue, yearHighIndicator);
    }

    @UsingKey(type=MarketPriceConstants.YRLO_IND)
    public String getYearLowIndicator() {
        return yearLowIndicator;
    }

    public static final String YEAR_LOW_INDICATOR = "yearLowIndicator";

    @RFAType(type=MarketPriceConstants.YRLO_IND)
    @Adapt(using=OMMEnumAdapter.class)
    public void setYearLowIndicator(String yearLowIndicator) {

        String oldValue = this.yearLowIndicator;

        this.yearLowIndicator = yearLowIndicator;

        firePropertyChange(YEAR_LOW_INDICATOR, oldValue, yearLowIndicator);
    }

    @UsingKey(type=MarketPriceConstants.BETA_VAL)
    public BigDecimal getBetaValue() {
        return betaValue;
    }

    public static final String BETA_VALUE = "betaValue";

    @RFAType(type=MarketPriceConstants.BETA_VAL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBetaValue(BigDecimal betaValue) {

        BigDecimal oldValue = this.betaValue;

        this.betaValue = betaValue;

        firePropertyChange(BETA_VALUE, oldValue, betaValue);
    }

    @UsingKey(type=MarketPriceConstants.PREF_DISP)
    public Integer getPreferredDisplayTemplateNumber() {
        return preferredDisplayTemplateNumber;
    }

    public static final String PREFERRED_DISPLAY_TEMPLATE_NUMBER = "preferredDisplayTemplateNumber";

    @RFAType(type=MarketPriceConstants.PREF_DISP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPreferredDisplayTemplateNumber(
        Integer preferredDisplayTemplateNumber) {

        Integer oldValue = this.preferredDisplayTemplateNumber;

        this.preferredDisplayTemplateNumber = preferredDisplayTemplateNumber;

        firePropertyChange(PREFERRED_DISPLAY_TEMPLATE_NUMBER, oldValue, preferredDisplayTemplateNumber);
    }

    @UsingKey(type=MarketPriceConstants.DSPLY_NMLL)
    public String getLocalLanguageInstrumentName() {
        return localLanguageInstrumentName;
    }

    public static final String LOCAL_LANGUAGE_INSTRUMENT_NAME = "localLanguageInstrumentName";

    @RFAType(type=MarketPriceConstants.DSPLY_NMLL)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setLocalLanguageInstrumentName(
        String localLanguageInstrumentName) {

        String oldValue = this.localLanguageInstrumentName;

        this.localLanguageInstrumentName = localLanguageInstrumentName;

        firePropertyChange(LOCAL_LANGUAGE_INSTRUMENT_NAME, oldValue, localLanguageInstrumentName);
    }

    @UsingKey(type=MarketPriceConstants.VOL_X_PRC1)
    public BigDecimal getLatestTradeOrTradeTurnoverValue() {
        return latestTradeOrTradeTurnoverValue;
    }

    public static final String LATEST_TRADE_OR_TRADE_TURNOVER_VALUE = "latestTradeOrTradeTurnoverValue";

    @RFAType(type=MarketPriceConstants.VOL_X_PRC1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setLatestTradeOrTradeTurnoverValue(
        BigDecimal latestTradeOrTradeTurnoverValue) {

        BigDecimal oldValue = this.latestTradeOrTradeTurnoverValue;

        this.latestTradeOrTradeTurnoverValue = latestTradeOrTradeTurnoverValue;

        firePropertyChange(LATEST_TRADE_OR_TRADE_TURNOVER_VALUE, oldValue, latestTradeOrTradeTurnoverValue);
    }

    @UsingKey(type=MarketPriceConstants.DSO_ID)
    public Integer getDataSourceOwnerId() {
        return dataSourceOwnerId;
    }

    public static final String DATA_SOURCE_OWNER_ID = "dataSourceOwnerId";

    @RFAType(type=MarketPriceConstants.DSO_ID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setDataSourceOwnerId(Integer dataSourceOwnerId) {

        Integer oldValue = this.dataSourceOwnerId;

        this.dataSourceOwnerId = dataSourceOwnerId;

        firePropertyChange(DATA_SOURCE_OWNER_ID, oldValue, dataSourceOwnerId);
    }

    @UsingKey(type=MarketPriceConstants.AVERG_PRC)
    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public static final String AVERAGE_PRICE = "averagePrice";

    @RFAType(type=MarketPriceConstants.AVERG_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAveragePrice(BigDecimal averagePrice) {

        BigDecimal oldValue = this.averagePrice;

        this.averagePrice = averagePrice;

        firePropertyChange(AVERAGE_PRICE, oldValue, averagePrice);
    }

    @UsingKey(type=MarketPriceConstants.UPC71_REST)
    public String getUpc71RestrictedFlag() {
        return upc71RestrictedFlag;
    }

    public static final String UPC_71_RESTRICTED_FLAG = "upc71RestrictedFlag";

    @RFAType(type=MarketPriceConstants.UPC71_REST)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setUpc71RestrictedFlag(String upc71RestrictedFlag) {

        String oldValue = this.upc71RestrictedFlag;

        this.upc71RestrictedFlag = upc71RestrictedFlag;

        firePropertyChange(UPC_71_RESTRICTED_FLAG, oldValue, upc71RestrictedFlag);
    }

    @UsingKey(type=MarketPriceConstants.ADJUST_CLS)
    public BigDecimal getAdjustedClose() {
        return adjustedClose;
    }

    public static final String ADJUSTED_CLOSE = "adjustedClose";

    @RFAType(type=MarketPriceConstants.ADJUST_CLS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAdjustedClose(BigDecimal adjustedClose) {

        BigDecimal oldValue = this.adjustedClose;

        this.adjustedClose = adjustedClose;

        firePropertyChange(ADJUSTED_CLOSE, oldValue, adjustedClose);
    }

    @UsingKey(type=MarketPriceConstants.WEIGHTING)
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

    @UsingKey(type=MarketPriceConstants.STOCK_TYPE)
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

    @UsingKey(type=MarketPriceConstants.IMP_VOLT)
    public BigDecimal getImpliedVolatility() {
        return impliedVolatility;
    }

    public static final String IMPLIED_VOLATILITY = "impliedVolatility";

    @RFAType(type=MarketPriceConstants.IMP_VOLT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setImpliedVolatility(BigDecimal impliedVolatility) {

        BigDecimal oldValue = this.impliedVolatility;

        this.impliedVolatility = impliedVolatility;

        firePropertyChange(MarketPrice.IMPLIED_VOLATILITY, oldValue, impliedVolatility);
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

    @UsingKey(type=MarketPriceConstants.CP_ADJ_FCT)
    public BigDecimal getCapitalAdjustmentFactor() {
        return capitalAdjustmentFactor;
    }

    public static final String CAPITAL_ADJUSTMENT_FACTOR = "capitalAdjustmentFactor";

    @RFAType(type=MarketPriceConstants.CP_ADJ_FCT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCapitalAdjustmentFactor(BigDecimal capitalAdjustmentFactor) {

        BigDecimal oldValue = this.capitalAdjustmentFactor;

        this.capitalAdjustmentFactor = capitalAdjustmentFactor;

        firePropertyChange(MarketPrice.CAPITAL_ADJUSTMENT_FACTOR, oldValue, capitalAdjustmentFactor);
    }

    @UsingKey(type=MarketPriceConstants.CP_ADJ_DAT)
    public Long getCapitalAdjustmentDate() {
        return capitalAdjustmentDate;
    }

    public static final String CAPITAL_ADJUSTMENT_DATE = "capitalAdjustmentDate";

    @RFAType(type=MarketPriceConstants.CP_ADJ_DAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setCapitalAdjustmentDate(Long capitalAdjustmentDate) {

        Long oldValue = this.capitalAdjustmentDate;

        this.capitalAdjustmentDate = capitalAdjustmentDate;

        firePropertyChange(MarketPrice.CAPITAL_ADJUSTMENT_DATE, oldValue, capitalAdjustmentDate);
    }

    @UsingKey(type=MarketPriceConstants.AMT_ISSUE)
    public BigInteger getSharesIssuedTotal() {
        return sharesIssuedTotal;
    }

    public static final String SHARES_ISSUED_TOTAL = "sharesIssuedTotal";

    @RFAType(type=MarketPriceConstants.AMT_ISSUE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSharesIssuedTotal(BigInteger sharesIssuedTotal) {

        BigInteger oldValue = this.sharesIssuedTotal;

        this.sharesIssuedTotal = sharesIssuedTotal;

        firePropertyChange(MarketPrice.SHARES_ISSUED_TOTAL, oldValue, sharesIssuedTotal);
    }

    @UsingKey(type=MarketPriceConstants.MKT_VALUE)
    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public static final String MARKET_VALUE = "marketValue";

    @RFAType(type=MarketPriceConstants.MKT_VALUE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setMarketValue(BigDecimal marketValue) {

        BigDecimal oldValue = this.marketValue;

        this.marketValue = marketValue;

        firePropertyChange(MarketPrice.MARKET_VALUE, oldValue, marketValue);
    }

    @UsingKey(type=MarketPriceConstants.SPEC_TRADE)
    public Integer getSpecialTermsTradingFlag() {
        return specialTermsTradingFlag;
    }

    public static final String SPECIAL_TERMS_TRADING_FLAG = "specialTermsTradingFlag";

    @RFAType(type=MarketPriceConstants.SPEC_TRADE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSpecialTermsTradingFlag(Integer specialTermsTradingFlag) {

        Integer oldValue = this.specialTermsTradingFlag;

        this.specialTermsTradingFlag = specialTermsTradingFlag;

        firePropertyChange(MarketPrice.SPECIAL_TERMS_TRADING_FLAG, oldValue, specialTermsTradingFlag);
    }

    @UsingKey(type=MarketPriceConstants.FCAST_EARN)
    public BigDecimal getForecastedEarnings() {
        return forecastedEarnings;
    }

    public static final String FORECASTED_EARNINGS = "forecastedEarnings";

    @RFAType(type=MarketPriceConstants.FCAST_EARN)
    @Adapt(using=OMMNumericAdapter.class)
    public void setForecastedEarnings(BigDecimal forecastedEarnings) {

        BigDecimal oldValue = this.forecastedEarnings;

        this.forecastedEarnings = forecastedEarnings;

        firePropertyChange(MarketPrice.FORECASTED_EARNINGS, oldValue, forecastedEarnings);
    }

    @UsingKey(type=MarketPriceConstants.EARANK_RAT)
    public BigDecimal getEarningsRankRatio() {
        return earningsRankRatio;
    }

    public static final String EARNINGS_RANK_RATIO = "earningsRankRatio";

    @RFAType(type=MarketPriceConstants.EARANK_RAT)
    @Adapt(using=OMMNumericAdapter.class)
    public void setEarningsRankRatio(BigDecimal earningsRankRatio) {

        BigDecimal oldValue = this.earningsRankRatio;

        this.earningsRankRatio = earningsRankRatio;

        firePropertyChange(MarketPrice.EARNINGS_RANK_RATIO, oldValue, earningsRankRatio);
    }

    @UsingKey(type=MarketPriceConstants.FCAST_DATE)
    public Long getForecastDate() {
        return forecastDate;
    }

    public static final String FORECAST_DATE = "forecastDate";

    @RFAType(type=MarketPriceConstants.FCAST_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setForecastDate(Long forecastDate) {

        Long oldValue = this.forecastDate;

        this.forecastDate = forecastDate;

        firePropertyChange(MarketPrice.FORECAST_DATE, oldValue, forecastDate);
    }

    @UsingKey(type=MarketPriceConstants.YEAR_FCAST)
    public String getForecastYear() {
        return forecastYear;
    }

    public static final String FORECAST_YEAR = "forecastYear";

    @RFAType(type=MarketPriceConstants.YEAR_FCAST)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setForecastYear(String forecastYear) {

        String oldValue = this.forecastYear;

        this.forecastYear = forecastYear;

        firePropertyChange(MarketPrice.FORECAST_YEAR, oldValue, forecastYear);
    }

    @UsingKey(type=MarketPriceConstants.IRGMOD)
    public String getIrgPriceTypeModifier() {
        return irgPriceTypeModifier;
    }

    public static final String IRG_PRICE_TYPE_MODIFIER = "irgPriceTypeModifier";

    @RFAType(type=MarketPriceConstants.IRGMOD)
    @Adapt(using=OMMEnumAdapter.class)
    public void setIrgPriceTypeModifier(String irgPriceTypeModifier) {

        String oldValue = this.irgPriceTypeModifier;

        this.irgPriceTypeModifier = irgPriceTypeModifier;

        firePropertyChange(MarketPrice.IRG_PRICE_TYPE_MODIFIER, oldValue, irgPriceTypeModifier);
    }

    @UsingKey(type=MarketPriceConstants.INSMOD)
    public String getInsertPriceTypeModifier() {
        return insertPriceTypeModifier;
    }

    public static final String INSERT_PRICE_TYPE_MODIFIER = "insertPriceTypeModifier";

    @RFAType(type=MarketPriceConstants.INSMOD)
    @Adapt(using=OMMEnumAdapter.class)
    public void setInsertPriceTypeModifier(String insertPriceTypeModifier) {

        String oldValue = this.insertPriceTypeModifier;

        this.insertPriceTypeModifier = insertPriceTypeModifier;

        firePropertyChange(MarketPrice.INSERT_PRICE_TYPE_MODIFIER, oldValue, insertPriceTypeModifier);
    }

    @UsingKey(type=MarketPriceConstants.A_NPLRS_1)
    public BigInteger getAskPlayersLevel1() {
        return askPlayersLevel1;
    }

    public static final String ASK_PLAYERS_LEVEL_1 = "askPlayersLevel1";

    @RFAType(type=MarketPriceConstants.A_NPLRS_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAskPlayersLevel1(BigInteger askPlayersLevel1) {

        BigInteger oldValue = this.askPlayersLevel1;

        this.askPlayersLevel1 = askPlayersLevel1;

        firePropertyChange(MarketPrice.ASK_PLAYERS_LEVEL_1, oldValue, askPlayersLevel1);
    }

    @UsingKey(type=MarketPriceConstants.B_NPLRS_1)
    public BigInteger getBidPlayersLevel1() {
        return bidPlayersLevel1;
    }

    public static final String BID_PLAYERS_LEVEL_1 = "bidPlayersLevel1";

    @RFAType(type=MarketPriceConstants.B_NPLRS_1)
    @Adapt(using=OMMNumericAdapter.class)
    public void setBidPlayersLevel1(BigInteger bidPlayersLevel1) {

        BigInteger oldValue = this.bidPlayersLevel1;

        this.bidPlayersLevel1 = bidPlayersLevel1;

        firePropertyChange(BID_PLAYERS_LEVEL_1, oldValue, bidPlayersLevel1);
    }

    @UsingKey(type=MarketPriceConstants.GV3_TIME)
    public Long getGenericTime3() {
        return genericTime3;
    }

    public static final String GENERIC_TIME_3 = "genericTime3";

    @RFAType(type=MarketPriceConstants.GV3_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setGenericTime3(Long genericTime3) {

        Long oldValue = this.genericTime3;

        this.genericTime3 = genericTime3;

        firePropertyChange(GENERIC_TIME_3, oldValue, genericTime3);
    }

    @UsingKey(type=MarketPriceConstants.GV4_TIME)
    public Long getGenericTime4() {
        return genericTime4;
    }

    public static final String GENERIC_TIME_4 = "genericTime4";

    @RFAType(type=MarketPriceConstants.GV4_TIME)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setGenericTime4(Long genericTime4) {

        Long oldValue = this.genericTime4;

        this.genericTime4 = genericTime4;

        firePropertyChange(GENERIC_TIME_4, oldValue, genericTime4);
    }

    @UsingKey(type=MarketPriceConstants.MKT_CAP)
    public BigInteger getMarketCapitalisation() {
        return marketCapitalisation;
    }

    public static final String MARKET_CAPITALISATION = "marketCapitalisation";

    @RFAType(type=MarketPriceConstants.MKT_CAP)
    @Adapt(using=OMMNumericAdapter.class)
    public void setMarketCapitalisation(BigInteger marketCapitalisation) {

        BigInteger oldValue = this.marketCapitalisation;

        this.marketCapitalisation = marketCapitalisation;

        firePropertyChange(MARKET_CAPITALISATION, oldValue, marketCapitalisation);
    }

    @UsingKey(type=MarketPriceConstants.IRGFID)
    public BigInteger getIrgCorrectionValueFid() {
        return irgCorrectionValueFid;
    }

    public static final String IRG_CORRECTION_VALUE_FID = "irgCorrectionValueFid";

    @RFAType(type=MarketPriceConstants.IRGFID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgCorrectionValueFid(BigInteger irgCorrectionValueFid) {

        BigInteger oldValue = this.irgCorrectionValueFid;

        this.irgCorrectionValueFid = irgCorrectionValueFid;

        firePropertyChange(IRG_CORRECTION_VALUE_FID, oldValue, irgCorrectionValueFid);
    }

    @UsingKey(type=MarketPriceConstants.IRGVAL)
    public BigInteger getIrgCorrectionValue() {
        return irgCorrectionValue;
    }

    public static final String IRG_CORRECTION_VALUE = "irgCorrectionValue";

    @RFAType(type=MarketPriceConstants.IRGVAL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setIrgCorrectionValue(BigInteger irgCorrectionValue) {

        BigInteger oldValue = this.irgCorrectionValue;

        this.irgCorrectionValue = irgCorrectionValue;

        firePropertyChange(IRG_CORRECTION_VALUE, oldValue, irgCorrectionValue);
    }

    @UsingKey(type=MarketPriceConstants.PCT_ABNVOL)
    public BigDecimal getAbnormalVolumeIncreasePercentage() {
        return abnormalVolumeIncreasePercentage;
    }

    public static final String ABNORMAL_VOLUME_INCREASE_PERCENTAGE = "abnormalVolumeIncreasePercentage";

    @RFAType(type=MarketPriceConstants.PCT_ABNVOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setAbnormalVolumeIncreasePercentage(
        BigDecimal abnormalVolumeIncreasePercentage) {

        BigDecimal oldValue = this.abnormalVolumeIncreasePercentage;

        this.abnormalVolumeIncreasePercentage = abnormalVolumeIncreasePercentage;

        firePropertyChange(ABNORMAL_VOLUME_INCREASE_PERCENTAGE, oldValue, abnormalVolumeIncreasePercentage);
    }

    @UsingKey(type=MarketPriceConstants.BC_10_50K)
    public BigInteger getBlockTransactionsBetween10KAnd50KShares() {
        return blockTransactionsBetween10KAnd50KShares;
    }

    public static final String BLOCK_TRANSACTION_BETWEEN_10K_AND_50K_SHARES
        = "blockTransactionsBetween10KAnd50KShares";

    @RFAType(type=MarketPriceConstants.BC_10_50K)
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

    @UsingKey(type=MarketPriceConstants.BC_50_100K)
    public BigInteger getBlockTransactionsBetween50KAnd100KShares() {
        return blockTransactionsBetween50KAnd100KShares;
    }

    public static final String BLOCK_TRANSACTION_BETWEEN_50K_AND_100K_SHARES
        = "blockTransactionsBetween50KAnd100KShares";

    @RFAType(type=MarketPriceConstants.BC_50_100K)
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

    @UsingKey(type=MarketPriceConstants.BC_100K)
    public BigInteger getBlockTransactionsAbove100KShares() {
        return blockTransactionsAbove100KShares;
    }

    public static final String BLOCK_TRANSACTION_ABOVE_100K_SHARES = "blockTransactionsAbove100KShares";

    @RFAType(type=MarketPriceConstants.BC_100K)
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

    @UsingKey(type=MarketPriceConstants.PMA_50D)
    public BigDecimal getPriceMovingAverages50D() {
        return priceMovingAverages50D;
    }

    public static final String PRICE_MOVING_AVERAGE_50D = "priceMovingAverages50D";

    @RFAType(type=MarketPriceConstants.PMA_50D)
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

    @UsingKey(type=MarketPriceConstants.PMA_150D)
    public BigDecimal getPriceMovingAverages150D() {
        return priceMovingAverages150D;
    }

    public static final String PRICE_MOVING_AVERAGES_150D = "priceMovingAverages150D";

    @RFAType(type=MarketPriceConstants.PMA_150D)
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

    @UsingKey(type=MarketPriceConstants.PMA_200D)
    public BigDecimal getPriceMovingAverages200D() {
        return priceMovingAverages200D;
    }

    public static final String PRICE_MOVING_AVERAGES_200D = "priceMovingAverages200D";

    @RFAType(type=MarketPriceConstants.PMA_200D)
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

    @UsingKey(type=MarketPriceConstants.VMA_10D)
    public BigInteger getVolumeMovingAverages10D() {
        return volumeMovingAverages10D;
    }

    public static final String VOLUME_MOVING_AVERAGES_10D = "volumeMovingAverages10D";

    @RFAType(type=MarketPriceConstants.VMA_10D)
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

    @UsingKey(type=MarketPriceConstants.VMA_25D)
    public BigInteger getVolumeMovingAverages25D() {
        return volumeMovingAverages25D;
    }

    public static final String VOLUME_MOVING_AVERAGES_25D = "volumeMovingAverages25D";

    @RFAType(type=MarketPriceConstants.VMA_25D)
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

    @UsingKey(type=MarketPriceConstants.VMA_50D)
    public BigInteger getVolumeMovingAverages50D() {
        return volumeMovingAverages50D;
    }

    public static final String VOLUME_MOVING_AVERAGES_50D = "volumeMovingAverages50D";

    @RFAType(type=MarketPriceConstants.VMA_50D)
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

    @UsingKey(type=MarketPriceConstants.OPN_NETCH)
    public BigDecimal getOpenPriceNetChange() {
        return openPriceNetChange;
    }

    public static final String OPEN_PRICE_NET_CHANGE = "openPriceNetChange";

    @RFAType(type=MarketPriceConstants.OPN_NETCH)
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

    @UsingKey(type=MarketPriceConstants.CASH_EXDIV)
    public BigDecimal getLatestReportedCashDividend() {
        return latestReportedCashDividend;
    }

    public static final String LATEST_REPORTED_CASH_DIVIDEND = "latestReportedCashDividend";

    @RFAType(type=MarketPriceConstants.CASH_EXDIV)
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

    @UsingKey(type=MarketPriceConstants.MKT_VAL_SC)
    public String getMarketValueScalingFactor() {
        return marketValueScalingFactor;
    }

    public static final String MARKET_VALUE_SCALING_FACTOR = "marketValueScalingFactor";

    @RFAType(type=MarketPriceConstants.MKT_VAL_SC)
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

    @UsingKey(type=MarketPriceConstants.CASH_EXDAT)
    public Long getExDividendTradeDate() {
        return exDividendTradeDate;
    }

    public static final String EX_DIVIDEND_TRADE_DATE = "exDividendTradeDate";

    @RFAType(type=MarketPriceConstants.CASH_EXDAT)
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

    @UsingKey(type=MarketPriceConstants.PREV_DISP)
    public Integer getPreviousDisplayTemplate() {
        return previousDisplayTemplate;
    }

    public static final String PREVIOUS_DISPLAY_TEMPLATE = "previousDisplayTemplate";

    @RFAType(type=MarketPriceConstants.PREV_DISP)
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

    @UsingKey(type=MarketPriceConstants.PRC_QL3)
    public String getExtendedPriceQualifierFid() {
        return extendedPriceQualifierFid;
    }

    public static final String EXTENDED_PRICE_QUALIFIER_FID = "extendedPriceQualifierFid";

    @RFAType(type=MarketPriceConstants.PRC_QL3)
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

    @UsingKey(type=MarketPriceConstants.MPV)
    public String getMinimumPriceMovement() {
        return minimumPriceMovement;
    }

    public static final String MINIMUM_PRICE_MOVEMENT = "minimumPriceMovement";

    @RFAType(type=MarketPriceConstants.MPV)
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

    @UsingKey(type=MarketPriceConstants.OFF_CLOSE)
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

    @UsingKey(type=MarketPriceConstants.QUOTE_DATE)
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

    @UsingKey(type=MarketPriceConstants.VWAP)
    public BigDecimal getVolumeWeightedAveragePrice() {
        return volumeWeightedAveragePrice;
    }

    public static final String VOLUME_WEIGHTED_AVERAGE_PRICE = "volumeWeightedAveragePrice";

    @RFAType(type=MarketPriceConstants.VWAP)
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

    @UsingKey(type=MarketPriceConstants.BID_ASK_DT)
    public Long getBidAskDate() {
        return bidAskDate;
    }

    public static final String BID_ASK_DATE = "bidAskDate";

    @RFAType(type=MarketPriceConstants.BID_ASK_DT)
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

    @UsingKey(type=MarketPriceConstants.ISIN_CODE)
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

    @UsingKey(type=MarketPriceConstants.RTR_OPN_PR)
    public BigDecimal getRtrsOpeningPrice() {
        return rtrsOpeningPrice;
    }

    public static final String RTRS_OPENING_PRICE = "rtrsOpeningPrice";

    @RFAType(type=MarketPriceConstants.RTR_OPN_PR)
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

    @UsingKey(type=MarketPriceConstants.SEDOL)
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

    @UsingKey(type=MarketPriceConstants.MKT_SEGMNT)
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

    @UsingKey(type=MarketPriceConstants.TRDTIM_MS)
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

    @UsingKey(type=MarketPriceConstants.SALTIM_MS)
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

    @UsingKey(type=MarketPriceConstants.QUOTIM_MS)
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

    @UsingKey(type=MarketPriceConstants.TIMCOR_MS)
    public BigInteger getCorrectionTimeMillis() {
        return correctionTimeMillis;
    }

    public static final String CORRECTION_TIME_MILLIS = "correctionTimeMillis";

    @RFAType(type=MarketPriceConstants.TIMCOR_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setCorrectionTimeMillis(BigInteger correctionTimeMillis) {

        BigInteger oldValue = this.correctionTimeMillis;

        this.correctionTimeMillis = correctionTimeMillis;

        firePropertyChange(CORRECTION_TIME_MILLIS, oldValue, correctionTimeMillis);
    }

    @UsingKey(type=MarketPriceConstants.FIN_STATUS)
    public String getFinancialStatusIndicator() {
        return financialStatusIndicator;
    }

    public static final String FINANCIAL_STATUS_INDICATOR = "financialStatusIndicator";

    @RFAType(type=MarketPriceConstants.FIN_STATUS)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setFinancialStatusIndicator(String financialStatusIndicator) {

        String oldValue = this.financialStatusIndicator;

        this.financialStatusIndicator = financialStatusIndicator;

        firePropertyChange(FINANCIAL_STATUS_INDICATOR, oldValue, financialStatusIndicator);
    }

    @UsingKey(type=MarketPriceConstants.LS_SUBIND)
    public String getLastTradeSubMarketIndicator() {
        return lastTradeSubMarketIndicator;
    }

    public static final String LAST_TRADE_SUB_MARKET_INDICATOR = "lastTradeSubMarketIndicator";

    @RFAType(type=MarketPriceConstants.LS_SUBIND)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setLastTradeSubMarketIndicator(
        String lastTradeSubMarketIndicator) {

        String oldValue = this.lastTradeSubMarketIndicator;

        this.lastTradeSubMarketIndicator = lastTradeSubMarketIndicator;

        firePropertyChange(LAST_TRADE_SUB_MARKET_INDICATOR, oldValue, lastTradeSubMarketIndicator);
    }

    @UsingKey(type=MarketPriceConstants.IRG_SUBIND)
    public String getIrgPriceSubmarketIndicator() {
        return irgPriceSubmarketIndicator;
    }

    public static final String IRG_PRICE_SUBMARKET_INDICATOR = "irgPriceSubmarketIndicator";

    @RFAType(type=MarketPriceConstants.IRG_SUBIND)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setIrgPriceSubmarketIndicator(
        String irgPriceSubmarketIndicator) {

        String oldValue = this.irgPriceSubmarketIndicator;

        this.irgPriceSubmarketIndicator = irgPriceSubmarketIndicator;

        firePropertyChange(IRG_PRICE_SUBMARKET_INDICATOR, oldValue, irgPriceSubmarketIndicator);
    }

    @UsingKey(type=MarketPriceConstants.ACVOL_SC)
    public String getVolumeScaling() {
        return volumeScaling;
    }

    public static final String VOLUME_SCALING = "volumeScaling";

    @RFAType(type=MarketPriceConstants.ACVOL_SC)
    @Adapt(using=OMMEnumAdapter.class)
    public void setVolumeScaling(String volumeScaling) {

        String oldValue = volumeScaling;

        this.volumeScaling = volumeScaling;

        firePropertyChange(VOLUME_SCALING, oldValue, volumeScaling);
    }

    @UsingKey(type=MarketPriceConstants.EXCHCODE)
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

    @UsingKey(type=MarketPriceConstants.ODD_ASK)
    public BigDecimal getOddBestAsk() {
        return oddBestAsk;
    }

    public static final String ODD_BEST_ASK = "oddBestAsk";

    @RFAType(type=MarketPriceConstants.ODD_ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestAsk(BigDecimal oddBestAsk) {

        BigDecimal oldValue = this.oddBestAsk;

        this.oddBestAsk = oddBestAsk;

        firePropertyChange(ODD_BEST_ASK, oldValue, oddBestAsk);
    }

    @UsingKey(type=MarketPriceConstants.ODD_ASKSIZ)
    public BigInteger getOddBestAskSize() {
        return oddBestAskSize;
    }

    public static final String ODD_BEST_ASK_SIZE = "oddBestAskSize";

    @RFAType(type=MarketPriceConstants.ODD_ASKSIZ)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestAskSize(BigInteger oddBestAskSize) {

        BigInteger oldValue = this.oddBestAskSize;

        this.oddBestAskSize = oddBestAskSize;

        firePropertyChange(ODD_BEST_ASK_SIZE, oldValue, oddBestAskSize);
    }

    @UsingKey(type=MarketPriceConstants.ODD_BID)
    public BigDecimal getOddBestBid() {
        return oddBestBid;
    }

    public static final String ODD_BEST_BID = "oddBestBid";

    @RFAType(type=MarketPriceConstants.ODD_BID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestBid(BigDecimal oddBestBid) {

        BigDecimal oldValue = this.oddBestBid;

        this.oddBestBid = oddBestBid;

        firePropertyChange(ODD_BEST_BID, oldValue, oddBestBid);
    }

    @UsingKey(type=MarketPriceConstants.ODD_BIDSIZ)
    public BigInteger getOddBestBidSize() {
        return oddBestBidSize;
    }

    public static final String ODD_BEST_BID_SIZE = "oddBestBidSize";

    @RFAType(type=MarketPriceConstants.ODD_BIDSIZ)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOddBestBidSize(BigInteger oddBestBidSize) {

        BigInteger oldValue = this.oddBestBidSize;

        this.oddBestBidSize = oddBestBidSize;

        firePropertyChange(ODD_BEST_BID_SIZE, oldValue, oddBestBidSize);
    }

    @UsingKey(type=MarketPriceConstants.ROUND_VOL)
    public BigInteger getRoundVolume() {
        return roundVolume;
    }

    public static final String ROUND_VOLUME = "roundVolume";

    @RFAType(type=MarketPriceConstants.ROUND_VOL)
    @Adapt(using=OMMNumericAdapter.class)
    public void setRoundVolume(BigInteger roundVolume) {

        BigInteger oldValue = this.roundVolume;

        this.roundVolume = roundVolume;

        firePropertyChange(ROUND_VOLUME, oldValue, roundVolume);
    }

    @UsingKey(type=MarketPriceConstants.ORGID)
    public BigInteger getOrganizationId() {
        return organizationId;
    }

    public static final String ORGANIZATION_ID = "organizationId";

    @RFAType(type=MarketPriceConstants.ORGID)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOrganizationId(BigInteger organizationId) {

        BigInteger oldValue = this.organizationId;

        this.organizationId = organizationId;

        firePropertyChange(ORGANIZATION_ID, oldValue, organizationId);
    }

    @UsingKey(type=MarketPriceConstants.PR_FREQ)
    public String getPriceUpdateFrequency() {
        return priceUpdateFrequency;
    }

    public static final String PRICE_UPDATE_FREQUENCY = "priceUpdateFrequency";

    @RFAType(type=MarketPriceConstants.PR_FREQ)
    @Adapt(using=OMMEnumAdapter.class)
    public void setPriceUpdateFrequency(String priceUpdateFrequency) {

        String oldValue = this.priceUpdateFrequency;

        this.priceUpdateFrequency = priceUpdateFrequency;

        firePropertyChange(PRICE_UPDATE_FREQUENCY, oldValue, priceUpdateFrequency);
    }

    @UsingKey(type=MarketPriceConstants.RCS_AS_CLA)
    public String getRcsAssetClassification() {
        return rcsAssetClassification;
    }

    public static final String RCS_ASSET_CLASSIFICATION = "rcsAssetClassification";

    @RFAType(type=MarketPriceConstants.RCS_AS_CLA)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRcsAssetClassification(String rcsAssetClassification) {

        String oldValue = this.rcsAssetClassification;

        this.rcsAssetClassification = rcsAssetClassification;

        firePropertyChange(RCS_ASSET_CLASSIFICATION, oldValue, rcsAssetClassification);
    }

    @UsingKey(type=MarketPriceConstants.UNDR_INDEX)
    public String getUnderlyingIndex() {
        return underlyingIndex;
    }

    public static final String UNDERLYING_INDEX = "underlyingIndex";

    @RFAType(type=MarketPriceConstants.UNDR_INDEX)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setUnderlyingIndex(String underlyingIndex) {

        String oldValue = this.underlyingIndex;

        this.underlyingIndex = underlyingIndex;

        firePropertyChange(UNDERLYING_INDEX, oldValue, underlyingIndex);
    }

    @UsingKey(type=MarketPriceConstants.FUTURES)
    public String getFuturesChainRic() {
        return futuresChainRic;
    }

    public static final String FUTURES_CHAIN_RIC = "futuresChainRic";

    @RFAType(type=MarketPriceConstants.FUTURES)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setFuturesChainRic(String futuresChainRic) {

        String oldValue = this.futuresChainRic;

        this.futuresChainRic = futuresChainRic;

        firePropertyChange(FUTURES_CHAIN_RIC, oldValue, futuresChainRic);
    }

    @UsingKey(type=MarketPriceConstants.OPTIONS)
    public String getOptionsChainRic() {
        return optionsChainRic;
    }

    public static final String OPTIONS_CHAIN_RIC = "optionsChainRic";

    @RFAType(type=MarketPriceConstants.OPTIONS)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setOptionsChainRic(String optionsChainRic) {

        String oldValue = this.optionsChainRic;

        this.optionsChainRic = optionsChainRic;

        firePropertyChange(OPTIONS_CHAIN_RIC, oldValue, optionsChainRic);
    }

    @UsingKey(type=MarketPriceConstants.STRIKES)
    public String getStrikesCoverage() {
        return strikesCoverage;
    }

    public static final String STRIKES_COVERAGE = "strikesCoverage";

    @RFAType(type=MarketPriceConstants.STRIKES)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setStrikesCoverage(String strikesCoverage) {

        String oldValue = this.strikesCoverage;

        this.strikesCoverage = strikesCoverage;

        firePropertyChange(STRIKES_COVERAGE, oldValue, strikesCoverage);
    }

    @UsingKey(type=MarketPriceConstants.NEWSTM_MS)
    public BigInteger getNewsTimeMillis() {
        return newsTimeMillis;
    }

    public static final String NEWS_TIME_MILLIS = "newsTimeMillis";

    @RFAType(type=MarketPriceConstants.NEWSTM_MS)
    @Adapt(using=OMMNumericAdapter.class)
    public void setNewsTimeMillis(BigInteger newsTimeMillis) {

        BigInteger oldValue = this.newsTimeMillis;

        this.newsTimeMillis = newsTimeMillis;

        firePropertyChange(NEWS_TIME_MILLIS, oldValue, newsTimeMillis);
    }

    @UsingKey(type=MarketPriceConstants.TRD_THRU_X)
    public String getTradeThroughExemptFlags() {
        return tradeThroughExemptFlags;
    }

    public static final String TRADE_THROUGH_EXEMPT_FLAGS = "tradeThroughExemptFlags";

    @RFAType(type=MarketPriceConstants.TRD_THRU_X)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setTradeThroughExemptFlags(String tradeThroughExemptFlags) {

        String oldValue = this.tradeThroughExemptFlags;

        this.tradeThroughExemptFlags = tradeThroughExemptFlags;

        firePropertyChange(TRADE_THROUGH_EXEMPT_FLAGS, oldValue, tradeThroughExemptFlags);
    }

    @UsingKey(type=MarketPriceConstants.FIN_ST_IND)
    public String getCompanyComplianceStatus() {
        return companyComplianceStatus;
    }

    public static final String COMPANY_COMPLIANCE_STATUS = "companyComplianceStatus";

    @RFAType(type=MarketPriceConstants.FIN_ST_IND)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setCompanyComplianceStatus(String companyComplianceStatus) {

        String oldValue = this.companyComplianceStatus;

        this.companyComplianceStatus = companyComplianceStatus;

        firePropertyChange(COMPANY_COMPLIANCE_STATUS, oldValue, companyComplianceStatus);
    }

    @UsingKey(type=MarketPriceConstants.IRG_SMKTID)
    public String getIrgSubMarketCenterId() {
        return irgSubMarketCenterId;
    }

    public static final String IRG_SUB_MARKET_CENTER_ID = "irgSubMarketCenterId";

    @RFAType(type=MarketPriceConstants.IRG_SMKTID)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setIrgSubMarketCenterId(String irgSubMarketCenterId) {

        String oldValue = this.irgSubMarketCenterId;

        this.irgSubMarketCenterId = irgSubMarketCenterId;

        firePropertyChange(IRG_SUB_MARKET_CENTER_ID, oldValue, irgSubMarketCenterId);
    }

    @UsingKey(type=MarketPriceConstants.SUB_MKT_ID)
    public String getSubMarketCenterId() {
        return subMarketCenterId;
    }

    public static final String SUB_MARKET_CENTER_ID = "subMarketCenterId";

    @RFAType(type=MarketPriceConstants.SUB_MKT_ID)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setSubMarketCenterId(String subMarketCenterId) {

        String oldValue = this.subMarketCenterId;

        this.subMarketCenterId = subMarketCenterId;

        firePropertyChange(SUB_MARKET_CENTER_ID, oldValue, subMarketCenterId);
    }

    @UsingKey(type=MarketPriceConstants.ACT_DOM_EX)
    public String getActiveDomesticExchangeIds() {
        return activeDomesticExchangeIds;
    }

    public static final String ACTIVE_DOMESTIC_EXCHANGE_IDS = "activeDomesticExchangeIds";

    @RFAType(type=MarketPriceConstants.ACT_DOM_EX)
    @Adapt(using=OMMEnumAdapter.class)
    public void setActiveDomesticExchangeIds(String activeDomesticExchangeIds) {

        String oldValue = this.activeDomesticExchangeIds;

        this.activeDomesticExchangeIds = activeDomesticExchangeIds;

        firePropertyChange(ACTIVE_DOMESTIC_EXCHANGE_IDS, oldValue, activeDomesticExchangeIds);
    }

    @UsingKey(type=MarketPriceConstants.ACT_OTH_EX)
    public String getActiveOtherExchangeIds() {
        return activeOtherExchangeIds;
    }

    public static final String ACTIVE_OTHER_EXCHANGE_IDS = "activeOtherExchangeIds";

    @RFAType(type=MarketPriceConstants.ACT_OTH_EX)
    @Adapt(using=OMMEnumAdapter.class)
    public void setActiveOtherExchangeIds(String activeOtherExchangeIds) {

        String oldValue = this.activeOtherExchangeIds;

        this.activeOtherExchangeIds = activeOtherExchangeIds;

        firePropertyChange(ACTIVE_OTHER_EXCHANGE_IDS, oldValue, activeOtherExchangeIds);
    }

    @UsingKey(type=MarketPriceConstants.TRD_QUAL_2)
    public String getTradePriceQualifier2() {
        return tradePriceQualifier2;
    }

    public static final String TRADE_PRICE_QUALIFIER2 = "tradePriceQualifier2";

    @RFAType(type=MarketPriceConstants.TRD_QUAL_2)
    @Adapt(using=OMMEnumAdapter.class)
    public void setTradePriceQualifier2(String tradePriceQualifier2) {

        String oldValue = this.tradePriceQualifier2;

        this.tradePriceQualifier2 = tradePriceQualifier2;

        firePropertyChange(TRADE_PRICE_QUALIFIER2, oldValue, tradePriceQualifier2);
    }

    @UsingKey(type=MarketPriceConstants.CP_EFF_DAT)
    public Long getLatestCapitalChangeEffectiveDate() {
        return latestCapitalChangeEffectiveDate;
    }

    public static final String LATEST_CAPITAL_CHANGE_EFFECTIVE_DATE = "latestCapitalChangeEffectiveDate";

    @RFAType(type=MarketPriceConstants.CP_EFF_DAT)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setLatestCapitalChangeEffectiveDate(
        Long latestCapitalChangeEffectiveDate) {

        Long oldValue = this.latestCapitalChangeEffectiveDate;

        this.latestCapitalChangeEffectiveDate =
            latestCapitalChangeEffectiveDate;

        firePropertyChange(LATEST_CAPITAL_CHANGE_EFFECTIVE_DATE, oldValue, latestCapitalChangeEffectiveDate);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_1)
    public String getRow64_1() {
        return row64_1;
    }

    public static final String ROW64_1 = "row64_1";

    @RFAType(type=MarketPriceConstants.ROW64_1)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_1(String row64_1) {

        String oldValue = this.row64_1;

        this.row64_1 = row64_1;

        firePropertyChange(MarketPrice.ROW64_1, oldValue, row64_1);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_2)
    public String getRow64_2() {
        return row64_2;
    }

    public static final String ROW64_2 = "row64_2";

    @RFAType(type=MarketPriceConstants.ROW64_2)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_2(String row64_2) {

        String oldValue = this.row64_2;

        this.row64_2 = row64_2;

        firePropertyChange(MarketPrice.ROW64_2, oldValue, row64_2);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_3)
    public String getRow64_3() {
        return row64_3;
    }

    public static final String ROW64_3 = "row64_3";

    @RFAType(type=MarketPriceConstants.ROW64_3)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_3(String row64_3) {

        String oldValue = this.row64_3;

        this.row64_3 = row64_3;

        firePropertyChange(MarketPrice.ROW64_3, oldValue, row64_3);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_4)
    public String getRow64_4() {
        return row64_4;
    }

    public static final String ROW64_4 = "row64_4";

    @RFAType(type=MarketPriceConstants.ROW64_4)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_4(String row64_4) {

        String oldValue = this.row64_4;

        this.row64_4 = row64_4;

        firePropertyChange(MarketPrice.ROW64_4, oldValue, row64_4);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_5)
    public String getRow64_5() {
        return row64_5;
    }

    public static final String ROW64_5 = "row64_5";

    @RFAType(type=MarketPriceConstants.ROW64_5)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_5(String row64_5) {

        String oldValue = this.row64_5;

        this.row64_5 = row64_5;

        firePropertyChange(MarketPrice.ROW64_5, oldValue, row64_5);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_6)
    public String getRow64_6() {
        return row64_6;
    }

    public static final String ROW64_6 = "row64_6";

    @RFAType(type=MarketPriceConstants.ROW64_6)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_6(String row64_6) {

        String oldValue = this.row64_6;

        this.row64_6 = row64_6;

        firePropertyChange(MarketPrice.ROW64_6, oldValue, row64_6);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_7)
    public String getRow64_7() {
        return row64_7;
    }

    public static final String ROW64_7 = "row64_7";

    @RFAType(type=MarketPriceConstants.ROW64_7)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_7(String row64_7) {

        String oldValue = this.row64_7;

        this.row64_7 = row64_7;

        firePropertyChange(MarketPrice.ROW64_7, oldValue, row64_7);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_8)
    public String getRow64_8() {
        return row64_8;
    }

    public static final String ROW64_8 = "row64_8";

    @RFAType(type=MarketPriceConstants.ROW64_8)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_8(String row64_8) {

        String oldValue = this.row64_8;

        this.row64_8 = row64_8;

        firePropertyChange(MarketPrice.ROW64_8, oldValue, row64_8);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_9)
    public String getRow64_9() {
        return row64_9;
    }

    public static final String ROW64_9 = "row64_9";

    @RFAType(type=MarketPriceConstants.ROW64_9)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_9(String row64_9) {

        String oldValue = this.row64_9;

        this.row64_9 = row64_9;

        firePropertyChange(MarketPrice.ROW64_9, oldValue, row64_9);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_10)
    public String getRow64_10() {
        return row64_10;
    }

    public static final String ROW64_10 = "row64_10";

    @RFAType(type=MarketPriceConstants.ROW64_10)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_10(String row64_10) {

        String oldValue = this.row64_10;

        this.row64_10 = row64_10;

        firePropertyChange(MarketPrice.ROW64_10, oldValue, row64_10);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_11)
    public String getRow64_11() {
        return row64_11;
    }

    public static final String ROW64_11 = "row64_11";

    @RFAType(type=MarketPriceConstants.ROW64_11)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_11(String row64_11) {

        String oldValue = this.row64_11;

        this.row64_11 = row64_11;

        firePropertyChange(MarketPrice.ROW64_11, oldValue, row64_11);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_12)
    public String getRow64_12() {
        return row64_12;
    }

    public static final String ROW64_12 = "row64_12";

    @RFAType(type=MarketPriceConstants.ROW64_12)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_12(String row64_12) {

        String oldValue = this.row64_12;

        this.row64_12 = row64_12;

        firePropertyChange(MarketPrice.ROW64_12, oldValue, row64_12);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_13)
    public String getRow64_13() {
        return row64_13;
    }

    public static final String ROW64_13 = "row64_13";

    @RFAType(type=MarketPriceConstants.ROW64_13)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setRow64_13(String row64_13) {

        String oldValue = this.row64_13;

        this.row64_13 = row64_13;

        firePropertyChange(MarketPrice.ROW64_13, oldValue, row64_13);
    }

    @UsingKey(type=MarketPriceConstants.ROW64_14)
    public String getRow64_14() {
        return row64_14;
    }

    public static final String ROW64_14 = "row64_14";

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
    @UsingKey(type=MarketPriceConstants.PUT_CALL)
    public String getPutCall() {
        return MarketPriceConstants.PUT_CALL_NOT_ALLOCATED.equals(putCall)
            ? MarketPriceConstants.NOT_ALLOCATED : putCall;
    }

    public static final String PUT_CALL = "putCall";

    /**
     * Setter method for the {@link MarketPriceConstants#PUT_CALL}.
     */
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
    @UsingKey(type=MarketPriceConstants.IMP_VOLTA)
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
    @UsingKey(type=MarketPriceConstants.IMP_VOLTB)
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
    @UsingKey(type=MarketPriceConstants.OPINT_1)
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
    @UsingKey(type=MarketPriceConstants.OPINTNC)
    public BigInteger getOpenInterestNetChange() {
        return openInterestNetChange;
    }

    public static final String OPEN_INTEREST_NET_CHANGE = "openInterestNetChange";

    /**
     * Setter method for the {@link MarketPriceConstants#OPINTNC}.
     */
    @RFAType(type=MarketPriceConstants.OPINTNC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenInterestNetChange(BigInteger openInterestNetChange) {

        BigInteger oldValue = this.openInterestNetChange;

        this.openInterestNetChange = openInterestNetChange;

        firePropertyChange(OPEN_INTEREST_NET_CHANGE, oldValue, openInterestNetChange);
    }

    /**
     * Getter method for the {@link MarketPriceConstants#STRIKE_PRC}.
     */
    @UsingKey(type=MarketPriceConstants.STRIKE_PRC)
    public BigDecimal getStrikePrice() {
        return strikePrice;
    }

    public static final String STRIKE_PRICE = "strikePrice";

    @RFAType(type=MarketPriceConstants.STRIKE_PRC)
    @Adapt(using=OMMNumericAdapter.class)
    public void setStrikePrice(BigDecimal strikePrice) {

        BigDecimal oldValue = this.strikePrice;

        this.strikePrice = strikePrice;

        firePropertyChange(STRIKE_PRICE, oldValue, strikePrice);
    }

    @UsingKey(type=MarketPriceConstants.CONTR_MNTH)
    public String getContractMonth() {
        return contractMonth;
    }

    public static final String CONTRACT_MONTH = "contractMonth";

    @RFAType(type=MarketPriceConstants.CONTR_MNTH)
    @Adapt(using=OMMDataBufferAdapter.class)
    public void setContractMonth(String contractMonth) {

        String oldValue = this.contractMonth;

        this.contractMonth = contractMonth;

        firePropertyChange(CONTRACT_MONTH, oldValue, contractMonth);
    }

    @UsingKey(type=MarketPriceConstants.LOTSZUNITS)
    public String getLotSizeUnits() {
        return lotSizeUnits;
    }

    public static final String LOT_SIZE_UNITS = "lotSizeUnits";

    @RFAType(type=MarketPriceConstants.LOTSZUNITS)
    @Adapt(using=OMMEnumAdapter.class)
    public void setLotSizeUnits(String lotSizeUnits) {

        String oldValue = this.lotSizeUnits;

        this.lotSizeUnits = lotSizeUnits;

        firePropertyChange(LOT_SIZE_UNITS, oldValue, lotSizeUnits);
    }

    @UsingKey(type=MarketPriceConstants.OPEN_ASK)
    public BigDecimal getOpenAskPrice() {
        return openAskPrice;
    }

    public static final String OPEN_ASK_PRICE = "openAskPrice";

    @RFAType(type=MarketPriceConstants.OPEN_ASK)
    @Adapt(using=OMMNumericAdapter.class)
    public void setOpenAskPrice(BigDecimal openAskPrice) {

        BigDecimal oldValue = this.openAskPrice;

        this.openAskPrice = openAskPrice;

        firePropertyChange(OPEN_ASK_PRICE, oldValue, openAskPrice);
    }

    @UsingKey(type=MarketPriceConstants.EXPIR_DATE)
    public Long getExpiryDate() {
        return expiryDate;
    }

    public static final String EXPIRY_DATE = "expiryDate";

    @RFAType(type=MarketPriceConstants.EXPIR_DATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setExpiryDate(Long expiryDate) {

        Long oldValue = this.expiryDate;

        this.expiryDate = expiryDate;

        firePropertyChange(EXPIRY_DATE, oldValue, expiryDate);
    }

    @UsingKey(type=MarketPriceConstants.SETTLE)
    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public static final String SETTLEMENT_PRICE = "settlementPrice";

    @RFAType(type=MarketPriceConstants.SETTLE)
    @Adapt(using=OMMNumericAdapter.class)
    public void setSettlementPrice(BigDecimal settlementPrice) {

        BigDecimal oldValue = this.settlementPrice;

        this.settlementPrice = settlementPrice;

        firePropertyChange(SETTLEMENT_PRICE, oldValue, settlementPrice);
    }

    @UsingKey(type=MarketPriceConstants.SETTLEDATE)
    public Long getSettleDate() {
        return settleDate;
    }

    public static final String SETTLE_DATE = "settleDate";

    @RFAType(type=MarketPriceConstants.SETTLEDATE)
    @Adapt(using=OMMDateTimeAdapter.class)
    public void setSettleDate(Long settleDate) {

        Long oldValue = this.settleDate;

        this.settleDate = settleDate;

        firePropertyChange(SETTLE_DATE, oldValue, settleDate);
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
