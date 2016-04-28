package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMPriority;
import com.reuters.rfa.rdm.RDMInstrument;
import com.reuters.rfa.rdm.RDMMsgTypes;

/**
 * A service that can query market-by-order updates.
 *
 * @see RFAJ_ConfigLoggingGuide.pdf page 17 for configuration details.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketByOrderService
    extends QueryableService
    implements MarketByOrderServiceSpecification {

    public MarketByOrderService(
        RequestMessageBuilderFactory factory,
        Client client
    ) {
        super(
            Constants.dELEKTRON_DD,
            RDMMsgTypes.MARKET_BY_ORDER,
            factory,
            client
        );
    }

    /**
     * @TODO: Copied verbatim from the MarketPriceService so we need to refactor
     *  this.
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
}
