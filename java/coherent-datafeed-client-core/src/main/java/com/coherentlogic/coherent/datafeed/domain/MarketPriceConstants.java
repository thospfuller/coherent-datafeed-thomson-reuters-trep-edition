package com.coherentlogic.coherent.datafeed.domain;

/**
 * Constants that describe characteristics of market price.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface MarketPriceConstants {

    static final String
        DSPLY_NAME = "DSPLY_NAME";

    static final String ASK = "ASK";
    static final String ASK_1 = "ASK_1";
    static final String ASK_2 = "ASK_2";

    static final String PROD_PERM = "PROD_PERM";

    static final String RDNDISPLAY = "RDNDISPLAY";

    /**
     * ENUM
     */
    static final String RDN_EXCHID = "RDN_EXCHID";

    static final String NETCHNG_1 = "NETCHNG_1";

    static final String HIGH_1 = "HIGH_1";

    static final String LOW_1 = "LOW_1";

  /**
   * Up/down arrow
   *
   * ENUM
   */
    static final String PRCTCK_1 = "PRCTCK_1";

    /**
     * ENUM
     */
    static final String CURRENCY = "CURRENCY";

    static final String TRADE_DATE = "TRADE_DATE", TRDTIM_1 = "TRDTIM_1";

    static final String SVC_NAME = "SVC_NAME",
        SET_SERVICE_NAME = "setServiceName";

    static final String SVC_ID = "SVC_ID";

    static final String NAME_TYPE = "NAME_TYPE";

    static final String
        NAME = "NAME",
        SET_NAME = "setName",
        BID = "BID",
        BID_1 = "BID_1",
        BID_2 = "BID_2",
        BIDSIZE = "BIDSIZE",
        ASKSIZE = "ASKSIZE",
        TRDPRC_1 = "TRDPRC_1",
        TRDPRC_2 = "TRDPRC_2",
        TRDPRC_3 = "TRDPRC_3",
        TRDPRC_4 = "TRDPRC_4",
        TRDPRC_5 = "TRDPRC_5";

    static final String OPEN_PRC = "OPEN_PRC",
        HST_CLOSE = "HST_CLOSE",
        NEWS = "NEWS",
        NEWS_TIME = "NEWS_TIME",
        ACVOL_1 = "ACVOL_1",
        EARNINGS = "EARNINGS",
        YIELD = "YIELD",
        PERATIO = "PERATIO",
        DIVIDENDTP = "DIVIDENDTP",
        DIVPAYDATE = "DIVPAYDATE",
        EXDIVDATE = "EXDIVDATE",
        /**
         * @deprecated See TRD_QUAL_2 which will replace this.
         */
        CTS_QUAL = "CTS_QUAL",
        BLKCOUNT = "BLKCOUNT",
        BLKVOLUM = "BLKVOLUM",
        TRDXID_1 = "TRDXID_1",
        TRD_UNITS = "TRD_UNITS",
        LOT_SIZE = "LOT_SIZE",
        PCTCHNG = "PCTCHNG",
        OPEN_BID = "OPEN_BID",
        DJTIME = "DJTIME",
        CLOSE_BID = "CLOSE_BID",
        CLOSE_ASK = "CLOSE_ASK",
        DIVIDEND = "DIVIDEND",
        NUM_MOVES = "NUM_MOVES",
        OFFCL_CODE = "OFFCL_CODE",
        HSTCLSDATE = "HSTCLSDATE",
        YRHIGH = "YRHIGH",
        YRLOW = "YRLOW",
        TURNOVER = "TURNOVER",
        BOND_TYPE = "BOND_TYPE",
        BCKGRNDPAG = "BCKGRNDPAG",
        YCHIGH_IND = "YCHIGH_IND",
        YCLOW_IND = "YCLOW_IND",
        BID_NET_CH = "BID_NET_CH",
        BID_TICK_1 = "BID_TICK_1",
        CUM_EX_MKR = "CUM_EX_MKR",
        PRC_QL_CD = "PRC_QL_CD",
        NASDSTATUS = "NASDSTATUS",
        PRC_QL2 = "PRC_QL2",
        TRDVOL_1 = "TRDVOL_1",
        BID_HIGH_1 = "BID_HIGH_1",
        BID_LOW_1 = "BID_LOW_1",
        YRBIDHIGH = "YRBIDHIGH",
        YRBIDLOW = "YRBIDLOW",
        HST_CLSBID = "HST_CLSBID",
        HSTCLBDDAT = "HSTCLBDDAT",
        YRBDHI_IND = "YRBDHI_IND",
        YRBDLO_IND = "YRBDLO_IND",
        NUM_BIDS = "NUM_BIDS",
        RECORDTYPE = "RECORDTYPE",
        BID_MMID1 = "BID_MMID1",
        ASK_MMID1 = "ASK_MMID1",
        OPTION_XID = "OPTION_XID",
        YRHIGHDAT = "YRHIGHDAT",
        YRLOWDAT = "YRLOWDAT",
        IRGPRC = "IRGPRC",
        IRGVOL = "IRGVOL",
        IRGCOND = "IRGCOND",
        TIMCOR = "TIMCOR",
        INSPRC = "INSPRC",
        INSVOL = "INSVOL",
        INSCOND = "INSCOND",
        SALTIM = "SALTIM",
        TNOVER_SC = "TNOVER_SC",
        BCAST_REF = "BCAST_REF",
        CROSS_SC = "CROSS_SC",
        AMT_OS = "AMT_OS",
        AMT_OS_SC = "AMT_OS_SC",
        OFF_CD_IND = "OFF_CD_IND",
        PRC_VOLTY = "PRC_VOLTY",
        AMT_OS_DAT = "AMT_OS_DAT",
        BKGD_REF = "BKGD_REF",
        GEN_VAL1 = "GEN_VAL1",
        GEN_VAL2 = "GEN_VAL2",
        GEN_VAL3 = "GEN_VAL3",
        GEN_VAL4 = "GEN_VAL4",
        GV1_TEXT = "GV1_TEXT",
        GV2_TEXT = "GV2_TEXT",
        GV3_TEXT = "GV3_TEXT",
        GV4_TEXT = "GV4_TEXT",
        SEQNUM = "SEQNUM",
        PRNTYP = "PRNTYP",
        PRNTBCK = "PRNTBCK",
        QUOTIM = "QUOTIM",
        GV1_FLAG = "GV1_FLAG",
        GV2_FLAG = "GV2_FLAG",
        GV3_FLAG = "GV3_FLAG",
        GV4_FLAG = "GV4_FLAG",
        OFF_CD_IN2 = "OFF_CD_IN2",
        OFFC_CODE2 = "OFFC_CODE2",
        /**
         * Indicates whether option is a put or a call.  Standard usage will use
         * values that also specify the style of the option.
         *
         * Enum
         */
        PUT_CALL = "PUT_CALL",
        PUT_CALL_NOT_ALLOCATED = "    ",
        /**
         * Substituted for the "    " put/call value.
         */
        NOT_ALLOCATED = "Not allocated",
        /**
         * Implied volatility of ASK price.
         *
         * Real64
         */
        IMP_VOLTA = "IMP_VOLTA",
        /**
         * Implied volatility of BID price.
         *
         * Real64
         */
        IMP_VOLTB = "IMP_VOLTB",
        /**
         * Open interest. The total number of option or futures contracts that
         * have not been closed or in the case of commodities  liquidated or
         * offset by delivery.
         *
         * Real64
         */
        OPINT_1 = "OPINT_1",
        /**
         * Open interest net change. The difference between the current and
         * previous trading day open interest.
         *
         * INTEGER/REAL64
         */
        OPINTNC = "OPINTNC",
        /**
         * Strike price; the price at which an option may be exercised.
         *
         * REAL64
         */
        STRIKE_PRC = "STRIKE_PRC",
        /**
         * Contract month
         *
         * ALPHANUMERIC/RMTES_STRING
         */
        CONTR_MNTH = "CONTR_MNTH",
        /**
         * Lot size units.
         * 
         * ENUMERATED/ENUM
         */
        LOTSZUNITS = "LOTSZUNITS",
        /**
         * Open ask price.
         *
         * PRICE/REAL64
         */
        OPEN_ASK = "OPEN_ASK",
        /**
         * The date on which the future, option or warrant expires.
         *
         * DATE/DATE
         */
        EXPIR_DATE = "EXPIR_DATE",
        /**
         * Settlement price. The official closing price of a futures or option
         * contract set by the clearing house at the end of the trading day.
         *
         * PRICE/REAL64
         */
        SETTLE = "SETTLE",
        /**
         * The date of the settlement price held in the SETTLE field.
         *
         * DATE/DATE
         */
        SETTLEDATE = "SETTLEDATE";

    static final String
        GV1_TIME = "GV1_TIME",
        GV2_TIME = "GV2_TIME",
        EXCHTIM = "EXCHTIM",
        YRHI_IND = "YRHI_IND",
        YRLO_IND = "YRLO_IND",
        BETA_VAL = "BETA_VAL",
        PREF_DISP = "PREF_DISP",
        DSPLY_NMLL = "DSPLY_NMLL",
        VOL_X_PRC1 = "VOL_X_PRC1",
        DSO_ID = "DSO_ID",
        AVERG_PRC = "AVERG_PRC",
        UPC71_REST = "UPC71_REST",
        ADJUST_CLS = "ADJUST_CLS",
        WEIGHTING = "WEIGHTING",
        STOCK_TYPE = "STOCK_TYPE",
        IMP_VOLT = "IMP_VOLT",
        RDN_EXCHD2 = "RDN_EXCHD2",
        CP_ADJ_FCT = "CP_ADJ_FCT",
        CP_ADJ_DAT = "CP_ADJ_DAT",
        AMT_ISSUE = "AMT_ISSUE",
        MKT_VALUE = "MKT_VALUE",
        SPEC_TRADE = "SPEC_TRADE",
        FCAST_EARN = "FCAST_EARN",
        EARANK_RAT = "EARANK_RAT",
        FCAST_DATE = "FCAST_DATE";

    static final String
        YEAR_FCAST = "YEAR_FCAST",
        IRGMOD = "IRGMOD",
        INSMOD = "INSMOD",
        A_NPLRS_1 = "A_NPLRS_1",
        B_NPLRS_1 = "B_NPLRS_1",
        GV3_TIME = "GV3_TIME",
        GV4_TIME = "GV4_TIME",
        MKT_CAP = "MKT_CAP",
        IRGFID = "IRGFID",
        IRGVAL = "IRGVAL",
        PCT_ABNVOL = "PCT_ABNVOL",
        BC_10_50K = "BC_10_50K",
        BC_50_100K = "BC_50_100K",
        BC_100K = "BC_100K",
        PMA_50D = "PMA_50D",
        PMA_150D = "PMA_150D";

    static final String
        PMA_200D = "PMA_200D",
        VMA_10D = "VMA_10D",
        VMA_25D = "VMA_25D",
        VMA_50D = "VMA_50D",
        OPN_NETCH = "OPN_NETCH",
        CASH_EXDIV = "CASH_EXDIV",
        MKT_VAL_SC = "MKT_VAL_SC",
        CASH_EXDAT = "CASH_EXDAT",
        PREV_DISP = "PREV_DISP",
        PRC_QL3 = "PRC_QL3",
        MPV = "MPV",
        OFF_CLOSE = "OFF_CLOSE",
        QUOTE_DATE = "QUOTE_DATE";

    static final String
        VWAP = "VWAP",
        PROV_SYMB = "PROV_SYMB",
        BID_ASK_DT = "BID_ASK_DT",
        ISIN_CODE = "ISIN_CODE",
        MNEMONIC = "MNEMONIC",
        RTR_OPN_PR = "RTR_OPN_PR",
        SEDOL = "SEDOL",
        MKT_SEGMNT = "MKT_SEGMNT",
        TRDTIM_MS = "TRDTIM_MS",
        SALTIM_MS = "SALTIM_MS",
        QUOTIM_MS = "QUOTIM_MS";

    static final String
        TIMCOR_MS = "TIMCOR_MS",
        FIN_STATUS = "FIN_STATUS",
        LS_SUBIND = "LS_SUBIND",
        IRG_SUBIND = "IRG_SUBIND",
        ACVOL_SC = "ACVOL_SC",
        EXCHCODE = "EXCHCODE",
        ODD_ASK = "ODD_ASK",
        ODD_ASKSIZ = "ODD_ASKSIZ";

    static final String
        ODD_BID = "ODD_BID",
        ODD_BIDSIZ = "ODD_BIDSIZ",
        ROUND_VOL = "ROUND_VOL",
        ORGID = "ORGID",
        PR_FREQ = "PR_FREQ",
        RCS_AS_CLA = "RCS_AS_CLA",
        UNDR_INDEX = "UNDR_INDEX",
        FUTURES = "FUTURES",
        OPTIONS = "OPTIONS",
        STRIKES = "STRIKES",
        NEWSTM_MS = "NEWSTM_MS",
        TRD_THRU_X = "TRD_THRU_X";
    
    static final String
        FIN_ST_IND = "FIN_ST_IND",
        IRG_SMKTID = "IRG_SMKTID",
        SUB_MKT_ID = "SUB_MKT_ID",
        ACT_DOM_EX = "ACT_DOM_EX",
        ACT_OTH_EX = "ACT_OTH_EX",
        TRD_QUAL_2 = "TRD_QUAL_2",
        CP_EFF_DAT = "CP_EFF_DAT";

    static final String
        TIME_SERIES_DATA = "TIME_SERIES_DATA",
        ROW64_1 = "ROW64_1",
        ROW64_2 = "ROW64_2",
        ROW64_3 = "ROW64_3",
        ROW64_4 = "ROW64_4",
        ROW64_5 = "ROW64_5",
        ROW64_6 = "ROW64_6",
        ROW64_7 = "ROW64_7",
        ROW64_8 = "ROW64_8",
        ROW64_9 = "ROW64_9",
        ROW64_10 = "ROW64_10",
        ROW64_11 = "ROW64_11",
        ROW64_12 = "ROW64_12",
        ROW64_13 = "ROW64_13",
        ROW64_14 = "ROW64_14";

    static final String
        ORDER = "order",
        ORDERS = "orders",
        ORDER_ID = "ORDER_ID",
        ORDER_PRC = "ORDER_PRC",
        ORDER_SIDE = "ORDER_SIDE",
        ORDER_SIZE = "ORDER_SIZE",
        ORDER_TONE = "ORDER_TONE",
        PR_TIM_MS = "PR_TIM_MS";

    static final String
        MKT_MKR_NM = "MKT_MKR_NM",
        MMID = "MMID",
        ASK_TIM_MS = "ASK_TIM_MS",
        TIMACT_MS = "TIMACT_MS",
        BID_TIM_MS = "BID_TIM_MS",
        PRIMARY_MM = "PRIMARY_MM",
        MM_MODE = "MM_MODE",
        MM_STATE = "MM_STATE",
        PR_DATE = "PR_DATE";
    
    static final String ELEMENTS = "elements";

    static final String
        ACTIV_DATE_KEY = "ACTIV_DATE_KEY",
        CONTEXT_ID_KEY = "CONTEXT_ID_KEY",
        DDS_DSO_ID = "DDS_DSO_ID",
        SPS_SP_RIC = "SPS_SP_RIC",
        BOOK_STATE = "BOOK_STATE",
        HALT_REASN = "HALT_REASN",
        TRD_STATUS = "TRD_STATUS",
        HALT_RSN   = "HALT_RSN",
        PR_RNK_RUL = "PR_RNK_RUL",
        OR_RNK_RUL = "OR_RNK_RUL",
        LOT_SIZE_A = "LOT_SIZE_A",
        PERIOD_CDE = "PERIOD_CDE",
        MKT_STATUS = "MKT_STATUS",
        LIST_MKT   = "LIST_MKT",
        CLASS_CODE = "CLASS_CODE";

    static final String
        NO_ORD = "NO_ORD",
        ACC_SIZE = "ACC_SIZE",
        LV_TIM_MS = "LV_TIM_MS",
        LV_DATE = "LV_DATE";
}
