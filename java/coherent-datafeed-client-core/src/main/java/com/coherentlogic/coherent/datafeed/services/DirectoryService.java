package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.builders.RequestMessageBuilder;
import com.coherentlogic.coherent.datafeed.factories.RequestMessageBuilderFactory;
import com.reuters.rfa.common.Client;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.rdm.RDMMsgTypes;
import com.reuters.rfa.rdm.RDMService;

/**
 * Service that exposes logic for querying the directory service.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @todo Unit test this class.
 * @todo Should the session be in the payload? We already put it in the headers.
 * @todo Move the message processor out of this class.
 * @todo We don't need a serviceName for this class (I believe) as we're
 *  requesting the directories available.
 */
public class DirectoryService
    extends QueryableService
    implements DirectoryServiceSpecification {

    private static final Logger log =
        LoggerFactory.getLogger(DirectoryService.class);

    public DirectoryService(
        String serviceName,
        short msgModelType,
        RequestMessageBuilderFactory factory,
        Client client
    ) {
        super(serviceName, msgModelType, factory, client);
    }

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

        RequestMessageBuilder requestMessageBuilder = factory.getInstance();

        OMMAttribInfo attribInfo = requestMessageBuilder.acquireAttribInfo();

        // SERVICE NAME DOES NOT APPLY HERE
        //attribInfo.setServiceName(serviceName.toString());
        attribInfo.setFilter(RDMService.Filter.INFO | RDMService.Filter.STATE);

        return requestMessageBuilder
            .createOMMMsg()
            .setMsgType(OMMMsg.MsgType.REQUEST)
            .setMsgModelType(RDMMsgTypes.DIRECTORY)
            .setIndicationFlags(
                OMMMsg.Indication.REFRESH | OMMMsg.Indication.NONSTREAMING)
            .setAttribInfo(attribInfo)
            .register(
                client,
                null,// We do not include a service name for directory requests.
                (String) null
            );
    }
}
