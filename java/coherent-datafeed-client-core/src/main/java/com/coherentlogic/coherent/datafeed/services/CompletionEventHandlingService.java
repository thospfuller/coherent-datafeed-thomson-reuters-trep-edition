package com.coherentlogic.coherent.datafeed.services;

import com.coherentlogic.coherent.datafeed.beans.UserBean;
import com.coherentlogic.coherent.datafeed.exceptions.SessionCreationFailedException;

/**
 * A completion event indicates that the stream was closed by RFA, so we need
 * to login again, however we don't want this to be a blocking call. The code
 * below may do the trick, however note that the handle is maintained in the
 * AuthenticationService and is not thread-safe.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class CompletionEventHandlingService {

    private final AuthenticationService authenticationService;

    private final UserBean userBean;

    public CompletionEventHandlingService(
        AuthenticationService authenticationService, UserBean userBean) {
        super();
        this.authenticationService = authenticationService;
        this.userBean = userBean;
    }

    /**
     * We want to issue a non-blocking login call to TR and then end the
     * workflow.
     */
    public void process () {
        String dacsId = userBean.getDacsId ();

        if (dacsId == null)
            throw new SessionCreationFailedException("The DACS id is null!");

        authenticationService.login(dacsId);
    }
}
