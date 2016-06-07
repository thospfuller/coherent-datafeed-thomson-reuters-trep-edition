package com.coherentlogic.coherent.datafeed.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coherentlogic.coherent.datafeed.adapters.TimeSeriesAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDataBufferAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMDateTimeAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMEnumAdapter;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.builders.LoginMessageBuilder;
import com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder;
import com.coherentlogic.coherent.datafeed.caches.DictionaryEntryCache;
import com.coherentlogic.coherent.datafeed.caches.DirectoryEntryCache;
import com.coherentlogic.coherent.datafeed.caches.TS1DefEntryCache;
import com.coherentlogic.coherent.datafeed.caches.TimeSeriesEntriesCache;
import com.coherentlogic.coherent.datafeed.factories.DefaultMarketByOrderFactory;
import com.coherentlogic.coherent.datafeed.factories.DefaultMarketByOrderFactory.DefaultOrderFactory;
import com.coherentlogic.coherent.datafeed.factories.DefaultMarketMakerFactory;
import com.coherentlogic.coherent.datafeed.factories.DefaultStatusResponseFactory;
import com.coherentlogic.coherent.datafeed.factories.LoginMessageBuilderFactory;
import com.coherentlogic.coherent.datafeed.factories.MarketMakerFactory;
import com.coherentlogic.coherent.datafeed.factories.MarketPriceFactory;
import com.coherentlogic.coherent.datafeed.factories.OMMEncoderFactory;
import com.coherentlogic.coherent.datafeed.factories.OMMPoolFactory;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.coherentlogic.coherent.datafeed.factories.rfa.EventQueueFactory;
import com.coherentlogic.coherent.datafeed.integration.endpoints.StatusInterpreter;
import com.coherentlogic.coherent.datafeed.integration.transformers.OMMSeriesTransformer;
import com.coherentlogic.coherent.datafeed.integration.transformers.OMMStateTransformer;
import com.coherentlogic.coherent.datafeed.misc.TS1DefDbHelper;
import com.coherentlogic.coherent.datafeed.services.DictionaryLoadCompleteService;
import com.coherentlogic.coherent.datafeed.services.LoggingService;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesHelper;
import com.coherentlogic.coherent.datafeed.services.WorkflowEndsService;
import com.coherentlogic.coherent.datafeed.services.message.processors.TransformTimeSeriesMessageProcessor;
import com.reuters.rfa.common.EventQueue;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMPool;
import com.reuters.rfa.session.omm.OMMConsumer;
import com.reuters.ts1.TS1DefDb;

/**
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Configuration
public class GlobalConfiguration {

    static final String
        FIELD_DICTIONARY = "fieldDictionary",
        SPRING_CACHE_MANAGER = "springCacheManager",
        POOL = "pool",
        ENCODER = "encoder",
        EVENT_QUEUE = "eventQueue",
        OMM_CONSUMER = "defaultOMMConsumer",
        TS1_DEF_CACHE = "ts1DefCache",
        MARKET_MAKER_MESSAGE_CONSUMER ="marketMakerEventDrivenEndpoint";

    public static final String DEFAULT_EVENT_QUEUE_NAME = "myEventQueue";

    @Bean(name=SPRING_CACHE_MANAGER)
    public ConcurrentMapCacheManager getConcurrentMapCacheManager () {
        return new ConcurrentMapCacheManager ();
    }

    @Bean(name=LoggingService.BEAN_NAME)
    public LoggingService getLoggingService () {

        LoggingService loggingService = new LoggingService ();

        return loggingService;
    }

    @Bean(name=ContextInitializer.BEAN_NAME)
    public ContextInitializer getContextInitializer () {

        ContextInitializer contextInitializer = new ContextInitializer ();

        return contextInitializer;
    }

    @Bean(name=OMMPoolFactory.BEAN_NAME)
    public OMMPoolFactory getOMMPoolFactory () {

        OMMPoolFactory ommPoolFactory = new OMMPoolFactory ();

        return ommPoolFactory;
    }

    @Bean(name=POOL)
    public OMMPool getOMMPool (@Qualifier(OMMPoolFactory.BEAN_NAME) OMMPoolFactory ommPoolFactory) {
        return ommPoolFactory.getInstance();
    }

    /**
     * <bean id="encoderFactory" class="com.coherentlogic.coherent.datafeed.factories.OMMEncoderFactory">
     *  <constructor-arg name="pool" ref="pool"/>
     *  <constructor-arg name="msgType">
     *      <util:constant static-field="com.reuters.rfa.omm.OMMTypes.MSG"/>
     *  </constructor-arg>
     *  <constructor-arg name="size" value="500"/>
     * </bean>
     */
    @Bean(name=OMMEncoderFactory.BEAN_NAME)
    public OMMEncoderFactory getOMMEncoderFactory (@Qualifier(POOL) OMMPool pool) {
        return new OMMEncoderFactory (pool, com.reuters.rfa.omm.OMMTypes.MSG, 500);
    }

    /**
     * <bean id="encoder" class="com.reuters.rfa.omm.OMMEncoder"
     *  factory-bean="encoderFactory" factory-method="getInstance">
     * </bean>
     */
    @Bean(name=ENCODER)
    public OMMEncoder getOMMEncoder (@Qualifier(OMMEncoderFactory.BEAN_NAME) OMMEncoderFactory encoderFactory) {
        return encoderFactory.getInstance();
    }

//    <bean id="eventQueueFactory" class="com.coherentlogic.coherent.datafeed.factories.rfa.EventQueueFactory">
//        <constructor-arg name="eventQueueName" value="myEventQueue"/>
//    </bean>
//    
//    <bean id="eventQueue" class="com.reuters.rfa.common.EventQueue"
//     factory-bean="eventQueueFactory" factory-method="getInstance"/>

    @Bean(name=EventQueueFactory.BEAN_NAME)
    public EventQueueFactory getEventQueueFactory () {
        return new EventQueueFactory (DEFAULT_EVENT_QUEUE_NAME);
    };

    @Bean(name=EVENT_QUEUE)
    public EventQueue getEventQueue (@Qualifier(EventQueueFactory.BEAN_NAME) EventQueueFactory eventQueueFactory) {
        return eventQueueFactory.getInstance();
    }

    /*
    <bean id="defaultRequestMessageBuilderFactory"
     class="com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory">
        <constructor-arg name="consumer" ref="defaultOMMConsumer"/>
        <constructor-arg name="eventQueue" ref="eventQueue"/>
        <constructor-arg name="pool" ref="pool"/>
        <constructor-arg name="encoder" ref="encoder"/>
    </bean>

    <bean id="defaultRequestMessageBuilder" class="com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder"
     factory-bean="defaultRequestMessageBuilderFactory" factory-method="getInstance"/>
     */

    @Bean(name=RequestMessageBuilderFactory.BEAN_NAME)
    public RequestMessageBuilderFactory getRequestMessageBuilderFactory (
        @Qualifier(OMM_CONSUMER) OMMConsumer defaultOMMConsumer,
        @Qualifier(EVENT_QUEUE) EventQueue eventQueue,
        @Qualifier(POOL) OMMPool pool,
        @Qualifier(ENCODER) OMMEncoder encoder
    ) {
        return new RequestMessageBuilderFactory (defaultOMMConsumer, eventQueue, pool, encoder);
    }

    @Bean(name=RequestMessageBuilder.BEAN_NAME)
    public RequestMessageBuilder getRequestMessageBuilder (
        @Qualifier(RequestMessageBuilderFactory.BEAN_NAME) RequestMessageBuilderFactory requestMessageBuilderFactory
    ) {
        return requestMessageBuilderFactory.getInstance();
    }

    @Bean(name=LoginMessageBuilderFactory.BEAN_NAME)
    public LoginMessageBuilderFactory getLoginMessageBuilderFactory (
        @Qualifier(POOL) OMMPool pool,
        @Qualifier(ENCODER) OMMEncoder encoder
    ) {
        return new LoginMessageBuilderFactory (pool, encoder);
    }

    @Bean(name=LoginMessageBuilder.BEAN_NAME)
    public LoginMessageBuilder getLoginMessageBuilder (
        @Qualifier(LoginMessageBuilderFactory.BEAN_NAME) LoginMessageBuilderFactory loginMessageBuilderFactory
    ) {
        return loginMessageBuilderFactory.getInstance();
    }

    @Bean(name=OMMDataBufferAdapter.BEAN_NAME)
    public OMMDataBufferAdapter getOMMDataBufferAdapter (
        FieldDictionary fieldDictionary,
        @Qualifier(OMMDataAdapter.BEAN_NAME) OMMDataAdapter dataAdapter
    ) {
        return new OMMDataBufferAdapter (fieldDictionary, dataAdapter);
    }

    @Bean(name=OMMEnumAdapter.BEAN_NAME)
    public OMMEnumAdapter getOMMEnumAdapter (
        FieldDictionary fieldDictionary,
        @Qualifier(OMMDataAdapter.BEAN_NAME) OMMDataAdapter dataAdapter
    ) {
        return new OMMEnumAdapter (fieldDictionary, dataAdapter);
    }

    @Bean(name=OMMNumericAdapter.BEAN_NAME)
    public OMMNumericAdapter getOMMNumericAdapter (
        FieldDictionary fieldDictionary,
        @Qualifier(OMMDataAdapter.BEAN_NAME) OMMDataAdapter dataAdapter
    ) {
        return new OMMNumericAdapter (fieldDictionary, dataAdapter);
    }

    @Bean(name=OMMDateTimeAdapter.BEAN_NAME)
    public OMMDateTimeAdapter getOMMDateTimeAdapter (
        FieldDictionary fieldDictionary,
        @Qualifier(OMMDataAdapter.BEAN_NAME) OMMDataAdapter dataAdapter
    ) {
        return new OMMDateTimeAdapter (fieldDictionary, dataAdapter);
    }

    @Bean(name=OMMDataAdapter.BEAN_NAME)
    public OMMDataAdapter getOMMDataAdapter (@Qualifier (FIELD_DICTIONARY) FieldDictionary fieldDictionary) {
        return new OMMDataAdapter (fieldDictionary);
    }

    @Bean(name=TransformTimeSeriesMessageProcessor.BEAN_NAME)
    public TransformTimeSeriesMessageProcessor getTransformTimeSeriesMessageProcessor (
        @Qualifier (TimeSeriesAdapter.BEAN_NAME) TimeSeriesAdapter timeSeriesAdapter,
        @Qualifier(TimeSeriesEntriesCache.BEAN_NAME) TimeSeriesEntriesCache timeSeriesEntriesCache
    ) {
        return new TransformTimeSeriesMessageProcessor (timeSeriesAdapter, timeSeriesEntriesCache);
    }

    @Bean(name=StatusInterpreter.BEAN_NAME)
    public StatusInterpreter getStatusInterpreter () {
        return new StatusInterpreter ();
    }

    @Bean(name=DefaultStatusResponseFactory.BEAN_NAME)
    public DefaultStatusResponseFactory getDefaultStatusResponseFactory () {
        return new DefaultStatusResponseFactory ();
    }

//    @Bean(name=DefaultMarketPriceFactory.BEAN_NAME)
//    public DefaultMarketPriceFactory getDefaultMarketPriceFactory () {
//        return new DefaultMarketPriceFactory ();
//    }

    @Bean(name=DefaultOrderFactory.BEAN_NAME)
    public DefaultMarketByOrderFactory.DefaultOrderFactory getMarketByOrderOrderFactory () {
        return new DefaultMarketByOrderFactory.DefaultOrderFactory ();
    }

    @Bean(name=DefaultMarketMakerFactory.DefaultOrderFactory.BEAN_NAME)
    public DefaultOrderFactory getDefaulMarketMakertOrderFactory () {
        return new DefaultOrderFactory ();
    }

    @Bean(name=DefaultMarketByOrderFactory.BEAN_NAME)
    public DefaultMarketByOrderFactory getDefaultMarketByOrderFactory () {
        return new DefaultMarketByOrderFactory ();
    }

    @Bean(name=DefaultMarketMakerFactory.BEAN_NAME)
    public DefaultMarketMakerFactory getDefaultMarketMakerFactory () {
        return new DefaultMarketMakerFactory ();
    }

    @Bean(name=OMMStateTransformer.BEAN_NAME)
    public OMMStateTransformer getOMMStateTransformer () {
        return new OMMStateTransformer ();
    }

    @Bean(name="ts1DefDb")
    public TS1DefDb getTS1DefDb () {
        return new TS1DefDb ();
    }

    @Bean(name=TimeSeriesAdapter.BEAN_NAME)
    public TimeSeriesAdapter getTimeSeriesAdapter (TS1DefDb ts1DefDb) {
        return new TimeSeriesAdapter (ts1DefDb);
    }

    @Bean(name=OMMSeriesTransformer.BEAN_NAME)
    public OMMSeriesTransformer getOMMSeriesTransformer () {
        return new OMMSeriesTransformer ();
    }

    public static final String DIRECTORY_ENTRY_CACHE = "directoryEntryCache",
        DICTIONARY_ENTRY_CACHE = "dictionaryEntryCache";

    @Bean(name=DictionaryLoadCompleteService.BEAN_NAME)
    public DictionaryLoadCompleteService getDictionaryLoadCompleteService (
        @Qualifier (DIRECTORY_ENTRY_CACHE) DirectoryEntryCache directoryEntryCache,
        @Qualifier (DICTIONARY_ENTRY_CACHE) DictionaryEntryCache dictionaryEntryCache
    ) {
        return new DictionaryLoadCompleteService (directoryEntryCache, dictionaryEntryCache);
    }

    @Bean(name=WorkflowEndsService.BEAN_NAME)
    public WorkflowEndsService getWorkflowEndsService () {
        return new WorkflowEndsService ();
    }

    @Bean(name=TS1DefDbHelper.BEAN_NAME)
    public TS1DefDbHelper getTS1DefDbHelper (@Qualifier(TS1DefEntryCache.BEAN_NAME) TS1DefEntryCache ts1DefEntryCache) {
        return new TS1DefDbHelper (ts1DefEntryCache);
    }

    @Bean(name=TimeSeriesHelper.BEAN_NAME)
    public TimeSeriesHelper getTimeSeriesHelper (@Qualifier(TimeSeriesEntriesCache.BEAN_NAME)
        TimeSeriesEntriesCache timeSeriesEntriesCache) {
        return new TimeSeriesHelper (timeSeriesEntriesCache);
    }

    @Bean(name=MarketPriceFactory.BEAN_NAME)
    public MarketPriceFactory getMarketPriceFactory () {
        return new MarketPriceFactory ();
    }

    @Bean(name=MarketMakerFactory.BEAN_NAME)
    public MarketMakerFactory getMarketMakerFactory () {
        return new MarketMakerFactory ();
    }

//    <bean id="marketPriceEventDrivenEndpoint"
//     class="com.coherentlogic.coherent.datafeed.integration.endpoints.EventDrivenEndpoint">
//        <property name="requestChannel" ref="onMarketPriceAddSessionToHeadersChannel"/>
//    </bean>
//    @Bean(name=MARKET_MAKER_MESSAGE_CONSUMER)
//    public MessageConsumer getMarketMakerMessageConsumer () {
//        return new MessageConsumer ();
//    }

//    <bean id="marketPriceService" class="com.coherentlogic.coherent.datafeed.services.MarketPriceService"
//     depends-on="loggingService">
//        <constructor-arg name="factory" ref="defaultRequestMessageBuilderFactory"/>
//        <constructor-arg name="client" ref="marketPriceEventDrivenEndpoint"/>
//        <constructor-arg name="messageConsumer" ref="marketPriceConsumer"/>
//        <constructor-arg name="jsonGenerator" ref="jsonAdapter"/>
//    </bean>
//
//    @Bean(name=MarketMakerService.BEAN_NAME)
//    public MarketMakerService getMarketMakerService (
//        @Qualifier(RequestMessageBuilderFactory.BEAN_NAME) RequestMessageBuilderFactory
//            defaultRequestMessageBuilderFactory,
//        Client client,
//        @Qualifier("onMarketMakerAddSessionToHeadersChannel") MessageConsumer messageConsumer,
//        BasicAdapter<MarketMaker, String> jsonGenerator
//    ) {
//        return new MarketMakerService (
//            defaultRequestMessageBuilderFactory,
//            client,
//            messageConsumer,
//            jsonGenerator
//        );
//    }


    /*
         <bean id="ts1DefMessageProcessor"
     class="com.coherentlogic.coherent.datafeed.services.message.processors.TS1DefMessageProcessor">
        <constructor-arg name="sessionCache" ref="sessionCache"/>
        <constructor-arg name="ts1DefCache" ref="ts1DefCache"/>
        <constructor-arg name="ts1DefService" ref="ts1DefService"/>
    </bean>
     */
//    @Bean(name=TS1DefMessageProcessor.BEAN_NAME)
//    public TS1DefMessageProcessor getTS1DefMessageProcessor (
//        @Qualifier(SESSION_CACHE) Map<Handle, Session> sessionCache,
//        @Qualifier(TS1_DEF_CACHE) Map<Handle, Session> ts1DefCache,
//        @Qualifier(TS1DefService.BEAN_NAME) TS1DefService ts1DefService
//    ) {
//        return new TS1DefMessageProcessor (sessionCache, ts1DefCache, ts1DefService);
//    }

//
//    @Bean(name="authentication")
//    public Authentication getAuthentication () {
//        return new Authentication ();
//    }
}
