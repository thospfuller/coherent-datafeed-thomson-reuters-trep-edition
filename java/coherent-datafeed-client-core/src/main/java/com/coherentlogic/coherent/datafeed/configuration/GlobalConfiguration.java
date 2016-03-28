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
import com.coherentlogic.coherent.datafeed.beans.UserBean;
import com.coherentlogic.coherent.datafeed.factories.DefaultMarketByOrderFactory;
import com.coherentlogic.coherent.datafeed.factories.DefaultMarketByOrderFactory.DefaultOrderFactory;
import com.coherentlogic.coherent.datafeed.factories.DefaultMarketMakerFactory;
import com.coherentlogic.coherent.datafeed.factories.DefaultMarketPriceFactory;
import com.coherentlogic.coherent.datafeed.factories.DefaultStatusResponseFactory;
import com.coherentlogic.coherent.datafeed.integration.endpoints.StatusInterpreter;
import com.coherentlogic.coherent.datafeed.integration.transformers.OMMSeriesTransformer;
import com.coherentlogic.coherent.datafeed.integration.transformers.OMMStateTransformer;
import com.coherentlogic.coherent.datafeed.misc.TS1DefDbHelper;
import com.coherentlogic.coherent.datafeed.services.DictionaryLoadCompleteService;
import com.coherentlogic.coherent.datafeed.services.LoggingService;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesHelper;
import com.coherentlogic.coherent.datafeed.services.WorkflowEndsService;
import com.coherentlogic.coherent.datafeed.services.message.processors.TransformTimeSeriesMessageProcessor;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.ts1.TS1DefDb;

/**
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Configuration
public class GlobalConfiguration {

    static final String FIELD_DICTIONARY = "fieldDictionary", SPRING_CACHE_MANAGER = "springCacheManager";

    @Bean(name=SPRING_CACHE_MANAGER)
    public ConcurrentMapCacheManager getConcurrentMapCacheManager () {
        return new ConcurrentMapCacheManager ();
    }

    @Bean(name=UserBean.BEAN_NAME)
    public UserBean getUserBean () {
        return new UserBean ();
    }

    @Bean(name=LoggingService.BEAN_NAME)
    public LoggingService getLoggingService () {

        LoggingService loggingService = new LoggingService ();

        loggingService.initialize();

        return loggingService;
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
        @Qualifier (TimeSeriesAdapter.BEAN_NAME) TimeSeriesAdapter timeSeriesAdapter
    ) {
        return new TransformTimeSeriesMessageProcessor (timeSeriesAdapter);
    }

    @Bean(name=StatusInterpreter.BEAN_NAME)
    public StatusInterpreter getStatusInterpreter () {
        return new StatusInterpreter ();
    }

    @Bean(name=DefaultStatusResponseFactory.BEAN_NAME)
    public DefaultStatusResponseFactory getDefaultStatusResponseFactory () {
        return new DefaultStatusResponseFactory ();
    }

    @Bean(name=DefaultMarketPriceFactory.BEAN_NAME)
    public DefaultMarketPriceFactory getDefaultMarketPriceFactory () {
        return new DefaultMarketPriceFactory ();
    }

    @Bean(name=DefaultOrderFactory.BEAN_NAME)
    public DefaultOrderFactory getDefaultOrderFactory () {
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

    @Bean(name=DictionaryLoadCompleteService.BEAN_NAME)
    public DictionaryLoadCompleteService getDictionaryLoadCompleteService () {
        return new DictionaryLoadCompleteService ();
    }

    @Bean(name=WorkflowEndsService.BEAN_NAME)
    public WorkflowEndsService getWorkflowEndsService () {
        return new WorkflowEndsService ();
    }

    @Bean(name=TS1DefDbHelper.BEAN_NAME)
    public TS1DefDbHelper getTS1DefDbHelper () {
        return new TS1DefDbHelper ();
    }

    @Bean(name=TimeSeriesHelper.BEAN_NAME)
    public TimeSeriesHelper getTimeSeriesHelper () {
        return new TimeSeriesHelper ();
    }

//
//    @Bean(name="authentication")
//    public Authentication getAuthentication () {
//        return new Authentication ();
//    }
}
