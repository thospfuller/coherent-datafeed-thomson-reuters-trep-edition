package com.coherentlogic.coherent.datafeed.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.EventQueue;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMPriority;
import com.reuters.rfa.rdm.RDMInstrument;
import com.reuters.rfa.session.omm.OMMConsumer;
import com.reuters.rfa.session.omm.OMMItemIntSpec;

/**
 * @todo Unit test this class.
 *
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class CommonRequestExecutor {

    private static final Logger log =
        LoggerFactory.getLogger(CommonRequestExecutor.class);

    private final Client client;

    private final RequestMessageBuilderFactory requestMessageBuilderFactory;

    public CommonRequestExecutor(
        Client client,
        RequestMessageBuilderFactory requestMessageBuilderFactory
    ) {
        super();
        this.client = client;
        this.requestMessageBuilderFactory = requestMessageBuilderFactory;
    }

    public Client getClient() {
        return client;
    }

    public RequestMessageBuilderFactory getRequestMessageBuilderFactory() {
        return requestMessageBuilderFactory;
    }

    /**
     * 
     * TR Example Output:
     * 
     * serviceName: IDN_RDF, itemName: QQCN, streaming: false, msgModelType: 6,
     * priorityClass: 0, priorityCount: 0
     * 
     * ME: method begins; loginHandle: OMMSubHandleImpl@3105e269, serviceName:
     * IDN_RDF, msgModelType: 6, itemName:
     * [Ljava.lang.String;@60c20fb6[{dTRI.Nd}]
     * 
     * @see OMMSubAppContext in the Examples project.
     * @see OMMSubAppContext.register (~ line 590)
     */
    protected List<Handle> executeRequest(
        String serviceName,
        short msgModelType,
        SessionBean sessionBean,
        String... itemNames
    ) {
        log.info("executeRequest: method begins; sessionBean: " + sessionBean
            + ", serviceName: " + serviceName + ", msgModelType: "
            + msgModelType + ", itemName: "
            + ToStringBuilder.reflectionToString(itemNames));

        RequestMessageBuilderFactory factory = getRequestMessageBuilderFactory();
        Client client = getClient();

        RequestMessageBuilder requestMessageBuilder = factory.getInstance();

        requestMessageBuilder.createOMMMsg();

        OMMMsg msg = requestMessageBuilder.getUnencodedMessage();

        msg.setMsgType(OMMMsg.MsgType.REQUEST);
        msg.setIndicationFlags(OMMMsg.Indication.NONSTREAMING
            | OMMMsg.Indication.REFRESH);
        msg.setMsgModelType(msgModelType);
        msg.setAttribInfo(serviceName.toString(), itemNames[0],
            RDMInstrument.NameType.RIC);

        msg.setPriority(OMMPriority.DEFAULT);

        OMMItemIntSpec spec = new OMMItemIntSpec();
        spec.setMsg(msg);

        OMMConsumer consumer = requestMessageBuilder.getConsumer();
        EventQueue eventQueue = requestMessageBuilder.getEventQueue();

        List<Handle> results = new ArrayList<Handle>();

        Handle handle = consumer.registerClient(eventQueue, spec, client, sessionBean);

        results.add(handle);

        requestMessageBuilder.releaseMsg();

        return results;
    }
}
