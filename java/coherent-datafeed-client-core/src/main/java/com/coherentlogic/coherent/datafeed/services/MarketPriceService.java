package com.coherentlogic.coherent.datafeed.services;

import java.util.List;
import java.util.Map;

import com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMPriority;
import com.reuters.rfa.rdm.RDMInstrument;
import com.reuters.rfa.rdm.RDMMsgTypes;

/**
 * Front-end for the Thomson Reuters market price service.
 *
 * @todo Move the queryCache into the session.
 *
 * @todo This service should not export data to the JMS queue -- it should put the time series entry into the map and
 *  return it.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class MarketPriceService extends CacheableQueryableService<MarketPrice>
    implements MarketPriceServiceSpecification {

    public MarketPriceService(
        RequestMessageBuilderFactory factory,
        Client client,
        Map<Handle, String> ricCache,
        Map<String, MarketPrice> marketPriceCache
    ) {
        super(
            Constants.dELEKTRON_DD,
            RDMMsgTypes.MARKET_PRICE,
            factory,
            client,
            ricCache,
            marketPriceCache
        );
    }

    /**
     * 
     * @param loginHandle
     * @param msgModelType RDMMsgTypes#MARKET_PRICE, MARKET_BY_ORDER, etc.
     * @param itemNames
     * @return
     */
    protected List<Handle> executeRequest (
        String serviceName,
        Handle loginHandle,
        short msgModelType,
        SessionBean sessionBean,
        String... itemNames
    ) {
        RequestMessageBuilderFactory factory = getRequestMessageBuilderFactory();

        Client client = getClient();

        RequestMessageBuilder builder = factory.getInstance();

        List<Handle> handles = 
            builder
                .createOMMMsg()
                .setMsgType(OMMMsg.MsgType.REQUEST)
                .setMsgModelType(msgModelType)
                .setIndicationFlags(OMMMsg.Indication.REFRESH)
                .setAttribInfo(serviceName, RDMInstrument.NameType.RIC, itemNames)
                .setPriority(OMMPriority.DEFAULT)
                .setAssociatedMetaInfo(loginHandle)
                .register(client, serviceName, sessionBean, itemNames);

        return handles;
    }
}
