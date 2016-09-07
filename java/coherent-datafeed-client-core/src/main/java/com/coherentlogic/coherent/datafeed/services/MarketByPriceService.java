package com.coherentlogic.coherent.datafeed.services;

import java.util.List;
import java.util.Map;

import com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder;
import com.coherentlogic.coherent.datafeed.domain.MarketByPrice;
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
 * A service that can query market-by-price updates.
 *
 * @see RFAJ_ConfigLoggingGuide.pdf page 17 for configuration details.
 *
 * @TODO Combine this with the MarketByPrice and MarketMaker and possibly MarketPrice services.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketByPriceService
    extends CacheableQueryableService<MarketByPrice>
    implements MarketByPriceServiceSpecification {

    public MarketByPriceService(
        RequestMessageBuilderFactory factory,
        Client client,
        Map<Handle, String> ricCache,
        Map<String, MarketByPrice> marketByPriceCache
    ) {
        super(
            Constants.dELEKTRON_DD,
            RDMMsgTypes.MARKET_BY_PRICE,
            factory,
            client,
            ricCache,
            marketByPriceCache
        );
    }

    /**
     * @TODO: Copied verbatim from the MarketPriceService so we need to refactor this so it appears in a base class
     * only.
     */
    @Override
    protected List<Handle> executeRequest(
        String serviceName,
        short msgModelType,
        SessionBean sessionBean,
        String... itemNames
    ) {
        RequestMessageBuilderFactory factory =
            getRequestMessageBuilderFactory();

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
