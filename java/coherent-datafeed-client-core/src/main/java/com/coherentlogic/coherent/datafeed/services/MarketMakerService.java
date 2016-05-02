package com.coherentlogic.coherent.datafeed.services;

import java.util.List;
import java.util.Map;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMPriority;
import com.reuters.rfa.rdm.RDMInstrument;
import com.reuters.rfa.rdm.RDMMsgTypes;

/**
 * Front-end for the Thomson Reuters market maker service.
 *
 * @todo Remove the jsonGenerator from here.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class MarketMakerService
    extends CacheableQueryableService<MarketMaker>
    implements MarketMakerServiceSpecification {

    public static final String BEAN_NAME = "marketMakerService";

//    private final Map<Handle, String> ricCache;
//
//    private final Map<String, MarketMaker> marketMakerCache;

    /**
     * @deprecated The jsonGenerator should be removed from this class.
     */
    public MarketMakerService(
        RequestMessageBuilderFactory factory,
        Client client,
        Map<Handle, String> ricCache,
        Map<String, MarketMaker> marketMakerCache,
        TypedFactory<MarketMaker> marketMakerFactory
    ) {
        super(
            Constants.dELEKTRON_DD,
            RDMMsgTypes.MARKET_MAKER,
            factory,
            client,
            ricCache,
            marketMakerCache,
            marketMakerFactory
        );

//        this.ricCache = ricCache;
//        this.marketMakerCache = marketMakerCache;
    }

    /**
     * @TODO: Move this method into the base class.
     */
    @Override
    protected List<Handle> executeRequest(
        String serviceName,
        Handle loginHandle,
        short msgModelType,
        String... itemNames
    ) {
        RequestMessageBuilderFactory factory =
            getRequestMessageBuilderFactory();

        Client client = getClient();

        RequestMessageBuilder builder = factory.getInstance();

        List<Handle> handles = builder
            .createOMMMsg()
            .setMsgType(OMMMsg.MsgType.REQUEST)
            .setMsgModelType(msgModelType)
            .setIndicationFlags(OMMMsg.Indication.REFRESH)
            .setAttribInfo(serviceName, RDMInstrument.NameType.RIC, itemNames)
            .setPriority(OMMPriority.DEFAULT)
            .setAssociatedMetaInfo(loginHandle)
            .register(client, serviceName, itemNames);

        return handles;
    }

//    @Override
//    public Map<String, MarketMaker> query(ServiceName serviceName, Handle loginHandle, String... rics) {
//
//    }
}
