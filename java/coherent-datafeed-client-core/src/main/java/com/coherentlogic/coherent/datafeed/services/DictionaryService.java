package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNullOrEmpty;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.EventQueue;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMPool;
import com.reuters.rfa.rdm.RDMDictionary;
import com.reuters.rfa.rdm.RDMMsgTypes;
import com.reuters.rfa.session.omm.OMMConsumer;
import com.reuters.rfa.session.omm.OMMItemIntSpec;

/**
 * A service which downloads the dictionaries via the RFA API.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DictionaryService
    extends QueryableService
    implements DictionaryServiceSpecification {

    private static final Logger log =
        LoggerFactory.getLogger(DictionaryService.class);

    public DictionaryService(
        String serviceName,
        short msgModelType,
        RequestMessageBuilderFactory factory,
        Client client
    ) {
        super(serviceName, msgModelType, factory, client);
    }

    /**
     * 
     * @param loginHandle
     * @param dictionaryIds
     * @return
     */
    public List<Handle> loadDictionaries (
        String serviceName,
        Handle loginHandle,
        String... dictionaryIds
    ) {
        assertNotNullOrEmpty("dictionaryIds", dictionaryIds);

        short msgModelType = getMsgModelType();

        List<Handle> results = executeRequest(
            serviceName,
            loginHandle,
            msgModelType,
            dictionaryIds
        );

        return results;
    }

    /**
     * @see openFullDictionary in OMMSubAppContext.java
     *
     * @todo We need to be able to pass only one dictionary at a time, so either
     *  we create a new method, or refactor the API so that you can only execute
     *  a request by passing a single name.
     */
    @Override
//    @Transactional
    protected List<Handle> executeRequest(
        String serviceName,
        Handle loginHandle,
        short msgModelType,
        String... dictionaryNames
    ) {
        List<Handle> results = new ArrayList<Handle> ();

        /* Note that it is possible that this method is paused prior and then
         * the DictionaryMessageEnricher.enrich method is executed, which
         * can result in an NPE progresses. The solution is to sync here and in
         * the DictionaryMessageEnricher.enrich method.
         *
         * @TODO: Investigate using transactions and the cache lock method as an
         *  alternative.
         */
//        synchronized (dictionaryCache) {
            for (String dictionaryName : dictionaryNames) {
                Handle handle = executeSingleRequest(
                    serviceName, loginHandle, msgModelType, dictionaryName);
                results.add(handle);
            }
//        }
        return results;
    }

    /**
     * @see openFullDictionary in OMMSubAppContext.java
     *
     * @todo We need to be able to pass only one dictionary at a time, so either
     *  we create a new method, or refactor the API so that you can only execute
     *  a request by passing a single name.
     */
    protected Handle executeSingleRequest(
        String serviceName,
        Handle loginHandle,
        short msgModelType,
        String dictionaryName
    ) {
        log.info("executeRequest: method begins: serviceName: " + serviceName +
            ", dictionaryName: " +
            ToStringBuilder.reflectionToString(dictionaryName));

        RequestMessageBuilderFactory factory =
            getRequestMessageBuilderFactory();

        Client client = getClient();

        RequestMessageBuilder requestMessageBuilder = factory.getInstance();

        requestMessageBuilder
            .createOMMMsg()
            .setMsgType(OMMMsg.MsgType.REQUEST)
            .setMsgModelType(RDMMsgTypes.DICTIONARY)
            .setIndicationFlags(
                OMMMsg.Indication.REFRESH | OMMMsg.Indication.NONSTREAMING);

        OMMPool pool = requestMessageBuilder.getOMMPool();

        OMMAttribInfo attribInfo = pool.acquireAttribInfo();

        attribInfo.setServiceName(serviceName.toString());
        attribInfo.setName(dictionaryName);
        attribInfo.setFilter(RDMDictionary.Filter.NORMAL);

        OMMMsg msg = requestMessageBuilder.getUnencodedMessage();

        msg.setAttribInfo(attribInfo);

        OMMItemIntSpec spec = new OMMItemIntSpec ();

        spec.setMsg(msg);

        OMMConsumer consumer = requestMessageBuilder.getConsumer ();
        EventQueue eventQueue = requestMessageBuilder.getEventQueue();

        Handle result = consumer.registerClient(eventQueue, spec, client, null);

        requestMessageBuilder.releaseMsg();

        return result;
    }
}
