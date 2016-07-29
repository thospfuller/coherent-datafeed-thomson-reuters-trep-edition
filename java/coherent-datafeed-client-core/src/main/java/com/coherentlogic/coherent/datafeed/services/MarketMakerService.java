package com.coherentlogic.coherent.datafeed.services;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
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
 * Front-end for the Thomson Reuters market maker service.
 *
 * @todo Remove the jsonGenerator from here.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketMakerService
    extends CacheableQueryableService<MarketMaker>
    implements MarketMakerServiceSpecification {

    public static final String BEAN_NAME = "marketMakerService";

    private static final Logger log = LoggerFactory.getLogger(MarketMakerService.class);

    /**
     * @deprecated The jsonGenerator should be removed from this class.
     */
    public MarketMakerService(
        RequestMessageBuilderFactory factory,
        Client client,
        Map<Handle, String> ricCache,
        Map<String, MarketMaker> marketMakerCache
    ) {
        super(
            Constants.dELEKTRON_DD,
            RDMMsgTypes.MARKET_MAKER,
            factory,
            client,
            ricCache,
            marketMakerCache
        );
    }

    /**
     * @TODO: Move this method into the base class.
     */
    @Override
    protected List<Handle> executeRequest(
        String serviceName,
        short msgModelType,
        SessionBean sessionBean,
        String... itemNames
    ) {
        RequestMessageBuilderFactory factory = getRequestMessageBuilderFactory();

        Client client = getClient();

        RequestMessageBuilder builder = factory.getInstance();

        Handle loginHandle = sessionBean.getHandle();

        List<Handle> handles = builder
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
