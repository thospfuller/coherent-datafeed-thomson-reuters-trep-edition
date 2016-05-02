package com.coherentlogic.coherent.datafeed.services;

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

    public CompletionEventHandlingService(
        AuthenticationService authenticationService) {
        super();
        this.authenticationService = authenticationService;
    }

    /**
     * We want to issue a non-blocking login call to TR and then end the
     * workflow.
     */
    public void process () {

        throw new SessionCreationFailedException("The process method has had its implementation gutted!");

//        String dacsId = userBean.getDacsId ();
//
//        if (dacsId == null)
//            throw new SessionCreationFailedException("The DACS id is null!");
//
//        authenticationService.login(dacsId);
    }
}
