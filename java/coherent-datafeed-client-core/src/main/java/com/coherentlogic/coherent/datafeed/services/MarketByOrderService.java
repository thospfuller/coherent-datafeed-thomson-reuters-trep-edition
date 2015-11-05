package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import javax.jms.MessageConsumer;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketByOrder;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.rdm.RDMMsgTypes;

/**
 * A service that can query market-by-order updates.
 *
 * @see RFAJ_ConfigLoggingGuide.pdf page 17 for configuration details.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class MarketByOrderService
    extends AsynchronousService<MarketByOrder> {

    public MarketByOrderService(
        RequestMessageBuilderFactory factory,
        Client client,
        MessageConsumer messageConsumer,
        BasicAdapter<MarketByOrder, String> jsonGenerator
    ) {
        // Should this be dIDN_RDF?
        super(
            Constants.dELEKTRON_DD,
            RDMMsgTypes.MARKET_BY_ORDER,
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


//encoder.initialize(OMMTypes.MSG, 5000);
//OMMItemIntSpec ommItemIntSpec = new OMMItemIntSpec();
//
//// Preparing to send item request message
//OMMMsg ommmsg = pool.acquireMsg();
//
//ommmsg.setMsgType(OMMMsg.MsgType.REQUEST);
//ommmsg.setMsgModelType(RDMMsgTypes.MARKET_BY_ORDER);
//ommmsg.setIndicationFlags(OMMMsg.Indication.REFRESH);
//
//ommmsg.setAttribInfo(svcName, itemName, RDMInstrument.NameType.RIC);
//
//// Set the message into interest spec
//ommItemIntSpec.setMsg(ommmsg);
//mboHandle = _ommConsumer.registerClient(_eventQueue, ommItemIntSpec, this, null);
//
//pool.releaseMsg(ommmsg);
//String str = "Sent MarketByOrder request for: " + svcName + " : " + itemName;
//myCallback.notifyStatus(str);
//
//m_bPopulateSummaryData = true;
//
//return true;
//}
//else
//{
//if ((_dictLocation == 2) && !m_bSubscribedToDictionary)
//{
//    sendRequest(RDMMsgTypes.DICTIONARY, svcName);
//    m_bSubscribedToDictionary = true;
//}
//}