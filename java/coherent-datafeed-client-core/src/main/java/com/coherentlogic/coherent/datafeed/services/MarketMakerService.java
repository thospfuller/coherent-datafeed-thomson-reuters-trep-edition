package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import javax.jms.MessageConsumer;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder;
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
    extends AsynchronousService<MarketMaker>
    implements MarketMakerServiceSpecification {

	public static final String BEAN_NAME = "marketMakerService";

	/**
	 * @deprecated The jsonGenerator should be removed from this class.
	 */
    public MarketMakerService(
        RequestMessageBuilderFactory factory,
        Client client,
        MessageConsumer messageConsumer,
        BasicAdapter<MarketMaker, String> jsonGenerator
    ) {
        super(
            Constants.dELEKTRON_DD,
            RDMMsgTypes.MARKET_MAKER,
            factory,
            client,
            messageConsumer,
            jsonGenerator
        );
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
}
