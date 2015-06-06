package com.coherentlogic.coherent.datafeed.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.services.PauseResumeService;

/**
 * This class is used when, for example, the user invokes the authenticate
 * method. Authenticate is event driven so the framework will receive a
 * notification at some point that indicates whether the authentication was a
 * success or failure. So what we do is this:
 *
 * * Send authentication
 * * Wait for "hasCompleted" to change
 * * Check the success flag
 *
 * Once this happens we know the authentication process has completed and we can
 * check the 
 *
 * examples and for integration testing and the purpose
 * is so that it can be added to the end of an integration workflow and then be
 * notified once the process has completed.
 *
 * In the application, the end of a process, such as loading the directories,
 * results in an event that causes the next step of the integration workflow to
 * take place (such as loading the dictionaries).
 *
 * @todo Unit test this class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class IntegrationEndpointAdapter {

    private static final Logger log =
        LoggerFactory.getLogger(IntegrationEndpointAdapter.class);

    private final PauseResumeService pauseResumeService;

    public IntegrationEndpointAdapter(PauseResumeService pauseResumeService) {
        super();
        this.pauseResumeService = pauseResumeService;
    }

    /**
     * Method is called by the integration workflow and will set the completed
     * flag to true and then notify all waiting threads.
     */
    public void initialisationSuccessful (Object ommItemEvent) {

        log.info("initialisationSuccessful: method invoked; ommItemEvent: " +
            ommItemEvent);

        pauseResumeService.resume(true);

        log.info("initialisationSuccessful: method returns.");
    }

    /**
     * Method is called by the integration workflow and will set the completed
     * flag to false and then notify all waiting threads.
     */
    public void initialisationFailed (Object ommItemEvent) {

        /* This should probably be a message passed in as a param since if this
         * method is called an exception will probably have been thrown.
         */

        log.info("initialisationFailed: method invoked; ommItemEvent: " +
            ommItemEvent);

        pauseResumeService.resume(false);

        log.info("initialisationFailed: method returns.");
    }

    public boolean waitForInitialisationToComplete () {
        return pauseResumeService.pause();
    }
}
