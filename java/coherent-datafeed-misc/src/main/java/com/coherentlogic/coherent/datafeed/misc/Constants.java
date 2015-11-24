package com.coherentlogic.coherent.datafeed.misc;

/**
 * Interface contains constant values which are used throughout this project.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public interface Constants {

    static final String dIDN_RDF = "dIDN_RDF", IDN_RDF = "IDN_RDF",
        ELEKTRON_DD = "ELEKTRON_DD", dELEKTRON_DD = "dELEKTRON_DD";

    static final int DEFAULT_INT_RETURN_VALUE = -9999, ZERO = 0, ONE = 1;

    static final long THREE_MINUTES = 1000 * 60 * 3;

    static final String RDM = "rdm", ENUM = "enum";

    static final String DACS_ID = "DACS_ID";

    static final String DEFAULT_APP_CTX_PATH = "spring/application-context.xml";

    static final String DIRECTORY_SERVICE = "directoryService",
        DICTIONARY_SERVICE = "dictionaryService";

    static final String RPACKAGE_PATH_KEY = "rpackagePath";

    static final String AUTHENTICATION_ENTRY_POINT = "authenticationEntryPoint",
        AUTHENTICATION_SERVICE = "authenticationService",
        LICENSE_GENERATION_SERVICE = "licenseGenerationService",
        LICENSE_CONTENT = "licenseContent",
        LICENSE_VERIFICATION_SERVICE = "licenseVerificationService",
        LICENSE_INSTALLATION_SERVICE = "licenseInstallationService";

    static final String REFRESH_MARKET_PRICE_TRANSFORMER_BEAN_ID =
        "refreshMarketPriceTransformer";

    static final String METHOD_NOT_IMPLEMENTED = "Method not yet implemented!";

    static final String CACHE_BEAN_ID = "cache";

    /**
     * @todo Some of these constants should be changed.
     */
    static final String MARKET_PRICE_SERVICE = "marketPriceService",
        STATUS_RESPONSE_SERVICE_GATEWAY = "statusResponseServiceGateway",
        TS1_DEF_SERVICE_GATEWAY = "ts1DefServiceGateway",
        MARKET_PRICE_SERVICE_GATEWAY = "queryMarketPriceUpdates",
        MARKET_BY_ORDER_SERVICE = "marketByOrderService",
        MARKET_BY_ORDER_SERVICE_GATEWAY = "queryMarketByOrderUpdates",
        MARKET_MAKER_SERVICE = "marketMakerService",
        TIME_SERIES_SERVICE_GATEWAY = "timeSeriesServiceGateway",
        TIME_SERIES_SERVICE = "timeSeriesService",
        STATUS_RESPONSE_SERVICE = "statusResponseService",
        INTEGRATION_ENDPOINT_ADAPTER = "integrationEndpointAdapter";

    static final String MARKET_PRICE = "marketPrice",
        MARKET_BY_ORDER = "marketByOrder",
        MARKET_MAKER = "marketMaker",
        TIME_SERIES = "timeSeries",
        STATUS_RESPONSE = "statusResponse",
        DICTIONARY_ENTRIES = "dictionaryEntries",
        DICTIONARY_ENTRY = "dictionaryEntry",
        DIRECTORY_ENTRIES = "directoryEntries",
        DIRECTORY_ENTRY = "directoryEntry",
        DICTIONARY_SERVICE_GATEWAY = "dictionaryServiceGateway",
        DIRECTORY_SERVICE_GATEWAY = "directoryServiceGateway";

    static final String
        CODE = "code",
        DATA_STATE = "dataState",
        STREAM_STATE = "streamState",
        TEXT = "text";

    static final String REQUEST_MSG_BUILDER_ID = "requestMessageBuilder";

    static final String DEFAULT_ABSOLUTE_PATH =
        "C:/development/projects/CDATAFEED-SVN-CO/java/" +
        "coherent-datafeed-client-core/src/main/resources/data/dictionaries/";

    /* When we run unit tests in Eclipse the path is set to:
     * C:\development\projects\CDATAFEED-SVN-CO\java\
     * coherent-datafeed-client-core\. -- which explains why using this path
     * works:
     *
     * src/main/resources/data/dictionaries/
     *
     * But this path does not work when we run the application.
     */
    static final String DEFAULT_RELATIVE_PATH = "data/";

    static final String DEFAULT_PATH = DEFAULT_RELATIVE_PATH;

    static final String FIELD_DICTIONARY = "fieldDictionary";

    static final String RDM_FIELD_DICTIONARY = "RDMFieldDictionary",
        ENUM_TYPE_DEF = "enumtype.def";

    static final String DEFAULT_RDM_FIELD_DICTIONARY_PATH =
        DEFAULT_PATH + RDM_FIELD_DICTIONARY;

    static final String DEFAULT_ENUM_FIELD_DICTIONARY_PATH
        = DEFAULT_PATH + ENUM_TYPE_DEF;

    static final String CLIENT_ID = "client";

    /**
     * Message header key that is used to look up the value of the event's type.
     */
    static final String EVENT_TYPE = "eventType",
        /**
         * @see Event.COMPLETION_EVENT -- same as REFRESH_COMPLETE -- refer to
         *  page 23 of the Reuters Domain Model Usage Guide.
         */
        COMPLETION_EVENT = "completionEvent",
        OMM_ITEM_EVENT = "ommItemEvent",
        REFRESH_RESP = "refreshResp";

    static final String QUOTE = "quote";

    static final String JSON_GENERATOR = "jsonGenerator";

    static final String ADD = "add", UPDATE = "update", DELETE = "delete";

    static final String DATE = "date",
        BIG_DATE = "DATE",
        POINTS = "points",
        POINT = "point",
        SAMPLE = "sample",
        SAMPLES = "samples",
        HEADERS = "headers",
        HEADER = "header";

    static final String UNUSED = "unused";

    static final String ROW64_1 = "ROW64_1", ROW64_3 = "ROW64_3";

    static final String TS1_DEF_SERVICE =
        "ts1DefService";

    static final String EMPTY_STRING = "";

    static final int DEFAULT_BUFFER_SIZE = 64, HEADERS_SIZE = 9,
        MAX_ROW_VALUE = 14, DEFAULT_TIMEOUT = 20; // seconds

    static final String DAILY = "daily", WEEKLY = "weekly", MONTHLY = "monthly",
        YEARLY = "yearly";

    static final String SESSION = "session",
        TIME_SERIES_RIC = "timeSeriesRic",
        HANDLE = "handle",
        // Apparently we can't have a null payload in Spring Integration.
        NULL_PAYLOAD = "Null Payload";

    static final String FRAMEWORK_EVENT_LISTENER_ADAPTER =
        "frameworkEventListenerAdapter";

    static final String ANONYMOUS = "anonymous";

    static final String DIRECTORY = "directory",
        DICTIONARY = "dictionary",
        TS1_DEF = "ts1Def";

    static final String MARKET_PRICE_DAO = "marketPriceDAO",
        TIME_SERIES_DAO = "timeSeriesDAO";
}
