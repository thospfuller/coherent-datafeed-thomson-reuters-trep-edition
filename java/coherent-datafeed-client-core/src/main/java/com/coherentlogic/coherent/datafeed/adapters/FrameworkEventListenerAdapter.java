package com.coherentlogic.coherent.datafeed.adapters;

import static com.coherentlogic.coherent.datafeed.misc.Constants.SESSION;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import com.coherentlogic.coherent.datafeed.listeners.FrameworkEventListener;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.reuters.rfa.common.Event;

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
public class FrameworkEventListenerAdapter implements FrameworkEventListenerAdapterSpecification {

    private static final Logger log =
        LoggerFactory.getLogger(FrameworkEventListenerAdapter.class);

    private final List<FrameworkEventListener>
        initialisationSuccessfulListenerList;

    private final List<FrameworkEventListener>
        initialisationFailedListenerList;

    public FrameworkEventListenerAdapter (
    ) {
        this (
            new ArrayList<FrameworkEventListener> (),
            new ArrayList<FrameworkEventListener> ()
        );
    }

    public FrameworkEventListenerAdapter (
        List<FrameworkEventListener> initialisationSuccessfulListenerList,
        List<FrameworkEventListener> initialisationFailedListenerList
    ) {
        super();
        this.initialisationSuccessfulListenerList =
            initialisationSuccessfulListenerList;
        this.initialisationFailedListenerList =
            initialisationFailedListenerList;
    }

    protected void notifyListeners (
        List<FrameworkEventListener> frameworkEventListeners, Session session) {

        for (FrameworkEventListener nextListener : frameworkEventListeners)
            nextListener.onEventReceived(session);
    }

//    public void addInitialisationSuccessfulListeners (
//        FrameworkEventListener... frameworkEventListeners) {
//
//        for (FrameworkEventListener next : frameworkEventListeners)
//            initialisationSuccessfulListenerList.add(next);
//    }

    /* (non-Javadoc)
	 * @see com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapterSpecification#addInitialisationSuccessfulListeners(java.util.List)
	 */
    @Override
	public void addInitialisationSuccessfulListeners (List<FrameworkEventListener> frameworkEventListeners) {
        initialisationSuccessfulListenerList.addAll(frameworkEventListeners);
    }

//    public void removeInitialisationSuccessfulListeners (
//        FrameworkEventListener... frameworkEventListeners) {
//
//        for (FrameworkEventListener next : frameworkEventListeners)
//            initialisationSuccessfulListenerList.remove(next);
//    }

//    public void addInitialisationFailedListeners (
//        FrameworkEventListener... frameworkEventListeners) {
//
//        for (FrameworkEventListener next : frameworkEventListeners)
//            initialisationFailedListenerList.add(next);
//    }

    /* (non-Javadoc)
	 * @see com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapterSpecification#addInitialisationFailedListeners(java.util.List)
	 */
    @Override
	public void addInitialisationFailedListeners (List<FrameworkEventListener> frameworkEventListeners) {
        initialisationFailedListenerList.addAll(frameworkEventListeners);
    }

//    public void removeInitialisationFailedListeners (
//        FrameworkEventListener... frameworkEventListeners) {
//
//        for (FrameworkEventListener next : frameworkEventListeners)
//            initialisationFailedListenerList.remove(next);
//    }

    /* (non-Javadoc)
	 * @see com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapterSpecification#initialisationSuccessful(org.springframework.messaging.Message)
	 */
    @Override
	public Message<?> initialisationSuccessful (Message<Event> message) {

        Event payload = message.getPayload();

        log.info("initialisationSuccessful: method invoked; message: " +
            message + ", payload: " + payload + ", handle: " + payload.getHandle());

        MessageHeaders headers = message.getHeaders();

        Session session = (Session) headers.get(SESSION);

        assertNotNull ("session", session);

        log.info("session: " + session);

        notifyListeners (initialisationSuccessfulListenerList, session);

        log.info("initialisationSuccessful: method returns.");

        return message;
    }

    /* (non-Javadoc)
	 * @see com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapterSpecification#initialisationFailed(org.springframework.messaging.Message)
	 */
    @Override
	public Message<?> initialisationFailed (Message<?> message) {

        /* This should probably be a message passed in as a param since if this
         * method is called an exception will probably have been thrown.
         */

        log.info("initialisationFailed: method invoked; session: " + message);

        MessageHeaders headers = message.getHeaders();

        Session session = (Session) headers.get(SESSION);

        assertNotNull ("session", session);

        notifyListeners (initialisationFailedListenerList, session);

        log.info("initialisationFailed: method returns.");

        return message;
    }

    /* (non-Javadoc)
	 * @see com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapterSpecification#getInitialisationSuccessfulListenerList()
	 */
    @Override
	public List<FrameworkEventListener> getInitialisationSuccessfulListenerList () {
        return initialisationSuccessfulListenerList;
    }

    /* (non-Javadoc)
	 * @see com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapterSpecification#getInitialisationFailedListenerList()
	 */
    @Override
	public List<FrameworkEventListener> getInitialisationFailedListenerList () {
        return initialisationFailedListenerList;
    }
}
