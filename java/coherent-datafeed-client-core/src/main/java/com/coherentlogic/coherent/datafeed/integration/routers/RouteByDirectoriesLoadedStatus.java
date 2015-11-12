package com.coherentlogic.coherent.datafeed.integration.routers;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.exceptions.SessionNotFoundException;
import com.coherentlogic.coherent.datafeed.services.Session;

/**
 * This router determines if all the directories have been loaded and, if so,
 * returns the allDirectoriesAreLoadedChannel otherwise the
 * directoriesAreStillPendingChannel is returned.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class RouteByDirectoriesLoadedStatus extends AbstractMessageRouter {

    private static final Logger log =
        LoggerFactory.getLogger(RouteByDirectoriesLoadedStatus.class);

    private final MessageChannel
        allDirectoriesAreLoadedChannel,
        directoriesAreStillPendingChannel;

    private RouteByDirectoriesLoadedStatus(
        MessageChannel allDirectoriesAreLoadedChannel,
        MessageChannel directoriesAreStillPendingChannel
    ) {
        super();
        this.allDirectoriesAreLoadedChannel = allDirectoriesAreLoadedChannel;
        this.directoriesAreStillPendingChannel =
            directoriesAreStillPendingChannel;
    }

    @Override
    protected Collection<MessageChannel> determineTargetChannels(
        Message<?> message) {

        MessageHeaders headers = message.getHeaders();

        Session session = (Session) headers.get(SESSION);

        if (session == null)
            throw new SessionNotFoundException ("The session returned from " +
                "the message headers was null.");

        boolean allDirectoriesAreLoaded = session.allDirectoriesAreLoaded();

        log.info("allDirectoriesAreLoaded: " + allDirectoriesAreLoaded);

        MessageChannel result = allDirectoriesAreLoaded ?
            allDirectoriesAreLoadedChannel :
            directoriesAreStillPendingChannel;

        log.info("Next message channel: " + result);

        List<MessageChannel> results = new ArrayList<MessageChannel> ();

        results.add(result);

        return results;
    }
}
