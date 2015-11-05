package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import javax.jms.MessageConsumer;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
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
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class MarketPriceService
    extends AsynchronousService<MarketPrice>
    implements MarketPriceServiceSpecification {

    private static final Logger log =
        LoggerFactory.getLogger(MarketPriceService.class);

    public MarketPriceService(
        RequestMessageBuilderFactory factory,
        Client client,
        MessageConsumer messageConsumer,
        BasicAdapter<MarketPrice, String> jsonGenerator
    ) {
        super(
            Constants.dELEKTRON_DD,
            RDMMsgTypes.MARKET_PRICE,
            factory,
            client,
            messageConsumer,
            jsonGenerator
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

    /**
     * @todo Do we need the cachedEntries below?
     */
    @Override
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

    @Override
    public String getNextUpdateAsJSON(String timeout) {
        return super.getNextUpdateAsJSON(timeout);
    }

    @Override
    public String getNextUpdateAsJSON(Long timeout) {
        return super.getNextUpdateAsJSON(timeout);
    }
}
