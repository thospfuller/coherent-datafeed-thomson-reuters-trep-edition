package com.coherentlogic.coherent.datafeed.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

/**
 * A service that is used to log the end of a workflow. Note that this service
 * is used to indicate a graceful end and should not be called when, for example
 * and exception is thrown.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class WorkflowEndsService {

    private static final Logger log =
        LoggerFactory.getLogger(WorkflowEndsService.class);

    public static final String BEAN_NAME = "workflowEndsService";

    private final String note;

    public WorkflowEndsService(String note) {
        this.note = note;
    }

    public WorkflowEndsService() {
        this.note = "No comments";
    }

    public void end (Message<?> message) {

        Object payload = message.getPayload();

        if (log.isDebugEnabled()) {
            log.debug("The workflow has ended; note: "+ note + ", payload: " + payload);
        }
    }
}
