package com.coherentlogic.coherent.datafeed.builders;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_APP_CTX_PATH;
import static com.coherentlogic.coherent.datafeed.misc.Constants.REQUEST_MSG_BUILDER_ID;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNullOrEmpty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.coherentlogic.coherent.datafeed.misc.Constants;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.EventQueue;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMPool;
import com.reuters.rfa.rdm.RDMInstrument;
import com.reuters.rfa.rdm.RDMMsgTypes;
import com.reuters.rfa.session.omm.OMMConsumer;
import com.reuters.rfa.session.omm.OMMItemIntSpec;

/**
 * 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RequestMessageBuilder
    extends OMMMsgBuilder<RequestMessageBuilder> {

    private final OMMConsumer consumer;
    private final EventQueue eventQueue;

    public RequestMessageBuilder(
        OMMConsumer consumer,
        EventQueue eventQueue,
        OMMPool pool,
        OMMEncoder encoder
    ) {
        super(pool, encoder);
        this.consumer = consumer;
        this.eventQueue = eventQueue;
    }

    public List<Handle> register (
        Client client,
        String serviceName,
        String... itemNames
    ) {
        assertNotNullOrEmpty ("itemNames", itemNames);

        List<Handle> handles = new ArrayList<Handle> ();

        OMMMsg ommMsg = getUnencodedMessage();

        for (String itemName : itemNames) {

            if (serviceName != null)
                setAttribInfo(
                    serviceName, RDMInstrument.NameType.RIC, itemName);

            OMMItemIntSpec ommItemIntSpec = new OMMItemIntSpec();

            ommItemIntSpec.setMsg(ommMsg);

            Handle handle = consumer.registerClient(
                eventQueue,
                ommItemIntSpec,
                client,
                null
            );
            handles.add(handle);
        }
        return handles;
    }

    public List<Handle> register (
        Client client,
        String serviceName,
        Integer filter,
        String... itemNames
    ) {
        assertNotNullOrEmpty ("itemNames", itemNames);

        List<Handle> handles = new ArrayList<Handle> ();

        OMMMsg ommMsg = getUnencodedMessage();

        for (String itemName : itemNames) {

            setAttribInfo(serviceName, RDMInstrument.NameType.RIC, itemName);

            OMMItemIntSpec ommItemIntSpec = new OMMItemIntSpec();

            ommItemIntSpec.setMsg(ommMsg);

            Handle handle = consumer.registerClient(
                eventQueue,
                ommItemIntSpec,
                client,
                null
            );

            handles.add(handle);
        }
        return handles;
    }

    public OMMConsumer getConsumer() {
        return consumer;
    }

    public EventQueue getEventQueue() {
        return eventQueue;
    }

    public static void main (String[] unused) {

        AbstractApplicationContext applicationContext =
            new ClassPathXmlApplicationContext (
                DEFAULT_APP_CTX_PATH);

        applicationContext.registerShutdownHook();

        RequestMessageBuilder builder =
            (RequestMessageBuilder) applicationContext.getBean(
                REQUEST_MSG_BUILDER_ID);

        Client client = null;

        Handle loginHandle = null;

        builder
            .createOMMMsg()
            .setMsgType(OMMMsg.MsgType.REQUEST)
            .setMsgModelType(RDMMsgTypes.MARKET_PRICE)
            .setIndicationFlags(OMMMsg.Indication.REFRESH)
            .setPriority((byte) 1, 1) // what are these values?
            .setAssociatedMetaInfo(loginHandle)
            .register(client, Constants.dIDN_RDF, "TRI.N", "MSFT.O");

    }
}
