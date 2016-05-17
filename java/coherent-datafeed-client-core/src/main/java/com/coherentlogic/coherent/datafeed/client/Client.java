package com.coherentlogic.coherent.datafeed.client;

import static com.coherentlogic.coherent.datafeed.misc.Constants.AUTHENTICATION_ENTRY_POINT;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_INT_RETURN_VALUE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DICTIONARY_SERVICE_GATEWAY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DIRECTORY_SERVICE_GATEWAY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.FRAMEWORK_EVENT_LISTENER_ADAPTER;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_PRICE_SERVICE_GATEWAY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.STATUS_RESPONSE_SERVICE_GATEWAY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.TIME_SERIES_SERVICE_GATEWAY;
import static com.coherentlogic.coherent.datafeed.misc.Constants.TS1_DEF_SERVICE;

import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.coherentlogic.coherent.datafeed.adapters.FrameworkEventListenerAdapterSpecification;
import com.coherentlogic.coherent.datafeed.client.ui.MainUI;
import com.coherentlogic.coherent.datafeed.exceptions.ApplicationInitializationFailedException;
import com.coherentlogic.coherent.datafeed.exceptions.ClientNotInitializedException;
import com.coherentlogic.coherent.datafeed.listeners.FrameworkEventListener;
import com.coherentlogic.coherent.datafeed.services.AuthenticationServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.DictionaryGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.DirectoryGatewaySpecification;
import com.coherentlogic.coherent.datafeed.services.MarketPriceServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.PauseResumeService;
import com.coherentlogic.coherent.datafeed.services.Session;
import com.coherentlogic.coherent.datafeed.services.StatusResponseServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.TS1DefServiceSpecification;
import com.coherentlogic.coherent.datafeed.services.TimeSeriesGatewaySpecification;
import com.reuters.rfa.common.Handle;

/**
 * An instance of this class is called from R and exposes several methods that
 * can be called in order to establish a session with Thomson Reuters, query
 * data, get updates, and terminate the session.
 *
 * @todo Harmonize the names of the specification which are returned to the user
 *  so that they're uniform (ie. *ServiceGateway).
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class Client {

    private static final Logger log =
        LoggerFactory.getLogger(Client.class);

    private AbstractApplicationContext applicationContext;

    private final PauseResumeService pauseResumeService =
        new PauseResumeService ();

    private boolean started = false;

    public Client (){
    }

    public MainUI getMainUI () {
        return applicationContext.getBean(MainUI.class);
    }

    /**
     * @todo Figure out why calling this CTOR causes the integration tests to
     *  fail.
     */
    public Client (AbstractApplicationContext applicationContext){
        log.warn("Calling this ctor from an integration test will likely " +
            "cause the tests to not start properly.");
        this.applicationContext = applicationContext;
    }

    protected ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    protected void setApplicationContext(
        AbstractApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    public void start(){

        if (!isStarted()) {//applicationContext == null) {
//            applicationContext = new ClassPathXmlApplicationContext(
//                DEFAULT_APP_CTX_PATH);
//            applicationContext.registerShutdownHook();
//            applicationContext.start();

            FrameworkEventListenerAdapterSpecification frameworkEventListenerAdapter =
                getFrameworkEventListenerAdapter ();

            frameworkEventListenerAdapter.addInitialisationSuccessfulListeners (
                Arrays.asList(
                    new FrameworkEventListener() {
                        @Override
                        public void onEventReceived(Session session) {
                            pauseResumeService.resume(true);
                        }
                    }
                )
            );

            frameworkEventListenerAdapter.addInitialisationFailedListeners (
                Arrays.asList(
                    new FrameworkEventListener () {
                        @Override
                        public void onEventReceived(Session session) {
                            pauseResumeService.resume(false);
                        }
                    }
                )
            );

            setStarted(true);

        } else {
            log.warn ("The start method was called however the " +
                "applicationContext pointed to a non-null application " +
                "context reference.");
        }
    }

    /**
     * Method invokes the applicationContext.stop method.
     *
     * Note: When unit testing, invoking stop can cause the test to fail because
     *       the event processing may not have completed.
     */
    public void stop(){
        if (applicationContext != null) {
            applicationContext.close();
            applicationContext = null;
        } else {
            log.warn ("The stop method was called however the " +
                "applicationContext pointed to a null reference.");
        }

        setStarted(false);
    }

    boolean isStarted(){
        return started;
    }

    void setStarted(boolean started){
        this.started = started;
    }

    void assertHasStarted(){
        if (!isStarted ()){
            throw new ClientNotInitializedException(
                "Did you forget to invoke the Initialize function (from R)?");
        }
    }

    public FrameworkEventListenerAdapterSpecification getFrameworkEventListenerAdapter () {
        // The app will not have been started when this method is invoked so
        // we don't want to check as is below.
        //assertHasStarted();

        return (FrameworkEventListenerAdapterSpecification)
            applicationContext.getBean(FRAMEWORK_EVENT_LISTENER_ADAPTER);
    }

    public AuthenticationServiceSpecification getAuthenticationService() {

        assertHasStarted();

        return (AuthenticationServiceSpecification)
            applicationContext.getBean(AUTHENTICATION_ENTRY_POINT);
    }

    public MarketPriceServiceSpecification getMarketPriceService(){

        assertHasStarted();

        return (MarketPriceServiceSpecification)
            applicationContext.getBean(MARKET_PRICE_SERVICE_GATEWAY);
    }

    public TimeSeriesGatewaySpecification getTimeSeriesService(){

        assertHasStarted();

        return (TimeSeriesGatewaySpecification)
            applicationContext.getBean(TIME_SERIES_SERVICE_GATEWAY);
    }

    public DictionaryGatewaySpecification getDictionaryService () {
        return (DictionaryGatewaySpecification)
            applicationContext.getBean(DICTIONARY_SERVICE_GATEWAY);
    }

    public DirectoryGatewaySpecification getDirectoryService () {
        return (DirectoryGatewaySpecification)
            applicationContext.getBean(DIRECTORY_SERVICE_GATEWAY);
    }

    /**
     * @todo As this is loaded internally, it may make sense to not expose the
     *  service altogether.
     */
    protected TS1DefServiceSpecification getTS1DefService () {
        return (TS1DefServiceSpecification)
            applicationContext.getBean(TS1_DEF_SERVICE);
    }

    public StatusResponseServiceSpecification getStatusResponseService () {
        return (StatusResponseServiceSpecification)
            applicationContext.getBean(STATUS_RESPONSE_SERVICE_GATEWAY);
    }

    public void loadTS1Definitions (Handle loginHandle) {

        TS1DefServiceSpecification ts1DefService = getTS1DefService();

        ts1DefService.initialize(loginHandle);
    }

    public Handle login (String dacsId) {
        assertHasStarted();

        log.info("login: method begins; dacsId: " + dacsId);

        AuthenticationServiceSpecification authenticationService =
            getAuthenticationService();

        Handle loginHandle = authenticationService.login(dacsId);

        log.info("login: method returned; loginHandle: " + loginHandle +
            "; loadDictionaries: method begins.");

        log.info("loadTS1Definitions: method returned; login: method ends.");

        return loginHandle;
    }

    public void waitForInitialisationToComplete () {

        boolean initialisationSucceeded = pauseResumeService.pause();

        String exceptionText = "The application initialisation has failed, " +
            "please review the log file for more information. Note also that " +
            "the login method should be called prior to invoking this method.";

        if (!initialisationSucceeded) {
            log.error(exceptionText);
                throw new ApplicationInitializationFailedException(
                    exceptionText);
        }
        log.info("waitForInitialisationToComplete: method ends; " +
            "initialisationSucceeded: " + initialisationSucceeded);
    }

    public void logout () {
        assertHasStarted();

        AuthenticationServiceSpecification authenticationService =
            getAuthenticationService();

        authenticationService.logout();
    }

    public static void main (String[] unused) {
        Client client = new Client ();

        client.start ();

        // client.installLicense("C:/temp/testKeyFile.lic");

        client.login("CoherentLogic_Fuller");

        client.waitForInitialisationToComplete();

        client.logout();

        client.stop ();

        System.exit (DEFAULT_INT_RETURN_VALUE);
    }
}
