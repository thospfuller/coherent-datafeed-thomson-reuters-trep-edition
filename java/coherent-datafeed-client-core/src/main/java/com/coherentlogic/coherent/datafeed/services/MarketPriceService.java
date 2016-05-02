package com.coherentlogic.coherent.datafeed.services;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
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
public class MarketPriceService
    extends CacheableQueryableService<MarketPrice>
    implements MarketPriceServiceSpecification {

    private static final Logger log =
        LoggerFactory.getLogger(MarketPriceService.class);

//    private final Map<Handle, String> ricCache;
//
//    private final Map<String, MarketPrice> marketPriceCache;

    public MarketPriceService(
        RequestMessageBuilderFactory factory,
        Client client,
        Map<Handle, String> ricCache,
        Map<String, MarketPrice> marketPriceCache,
        TypedFactory<MarketPrice> marketPriceFactory
    ) {
        super(
            Constants.dELEKTRON_DD,
            RDMMsgTypes.MARKET_PRICE,
            factory,
            client,
            ricCache,
            marketPriceCache,
            marketPriceFactory
        );

//        this.ricCache = ricCache;
//        this.marketPriceCache = marketPriceCache;
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

    public List<Handle> query(
        String serviceName,
        Handle loginHandle,
        String... items
    ) {
        log.info("query: method begins; serviceName: " + serviceName +
            ", loginHandle: " + loginHandle + ", items: " +
            ToStringBuilder.reflectionToString(items));

        List<Handle> handles = super.query(serviceName, loginHandle, items);

        log.info("query: method ends; handles: " + handles);

        return handles;
    }

    /**
     * R does not find the String... items method when passing a single RIC from
     * the command line -- adding this method addresses this problem.
     */
    @Override
    public List<Handle> query(
        String serviceName,
        Handle loginHandle,
        String item
    ) {
        return query (serviceName, loginHandle, new String[] {item});
    }

//    @Override
//    public Map<String, MarketPrice> query(ServiceName serviceName, Handle loginHandle, String... rics) {
//
//        Utils.assertNotNull("serviceName", serviceName);
//        Utils.assertNotNull("rics", rics);
//
//        Map<String, MarketPrice> result = new HashMap<String, MarketPrice> (rics.length);
//
//        for (String ric : rics) {
//
//            MarketPrice marketPrice = marketPriceCache.get(ric);
//
//            Handle handle = findHandle (ric);
//
//            if (marketPrice != null && handle == null)
//                throw new InvalidStateException ("Invalid state detected: the marketPrice is not null however there " +
//                    "is no handle associated with the ric " + ric);
//            else if (marketPrice == null && handle != null)
//                throw new InvalidStateException ("Invalid state detected: the marketPrice is null however there is " +
//                    "a non-null handle associated with the ric " + ric);
//
//            if (marketPrice == null) {
//
//                marketPrice = new MarketPrice ();
//
//                marketPriceCache.put(ric, marketPrice);
//
//                List<Handle> handleList = query(serviceName.toString(), loginHandle, ric);
//
//                ricCache.put(handleList.get(0), ric);
//            }
//            result.put(ric, marketPrice);
//        }
//
//        return result;
//    }
//
//    Handle findHandle (String ric) {
//
//        Handle result = null;
//
//        for (Entry<Handle, String> nextEntry : ricCache.entrySet()) {
//            if (nextEntry.getValue().equals(ric)) {
//                result = nextEntry.getKey();
//                break;
//            }
//        }
//
//        return result;
//    }
}
