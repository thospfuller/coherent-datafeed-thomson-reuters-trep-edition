package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import javax.jms.MessageConsumer;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketMaker;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.rdm.RDMMsgTypes;

/**
 * Front-end for the Thomson Reuters market price service.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class MarketMakerService
    extends AsynchronousService<MarketMaker> {

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

    @Override
    protected List<Handle> executeRequest(
        String serviceName,
        Handle loginHandle,
        short msgModelType,
        String... itemNames
    ) {
        throw new RuntimeException("Method not implemented.");
    }
}
