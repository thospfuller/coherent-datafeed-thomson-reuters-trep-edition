package com.coherentlogic.coherent.datafeed.services;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.coherentlogic.coherent.data.model.core.util.WelcomeMessage;
import com.coherentlogic.coherent.datafeed.builders.LoginMessageBuilder;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidApplicationIdException;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidDacsIdException;
import com.coherentlogic.coherent.datafeed.exceptions.SessionFinalizationFailedException;
import com.coherentlogic.coherent.datafeed.factories.PositionFactory;
import com.coherentlogic.coherent.datafeed.integration.endpoints.EventDrivenEndpoint;
import com.coherentlogic.coherent.datafeed.misc.Utils;
import com.reuters.rfa.common.EventQueue;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.omm.OMMElementList;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMTypes;
import com.reuters.rfa.rdm.RDMMsgTypes;
import com.reuters.rfa.rdm.RDMUser;
import com.reuters.rfa.session.omm.OMMConsumer;
import com.reuters.rfa.session.omm.OMMItemIntSpec;

/**
 * Service that handles authentication / authorization with Thomson Reuters.
 *
 * Note that this class is stateful and is not thread-safe.
 *
 * @see com.reuters.rfa.common#Client
 * @see com.reuters.rfa.example.quickstart.QuickStartConsumer#LoginClient
 *
 * @todo We really need a RFASessionFactory as it will simplify the ctor here,
 *  which has too many params at the moment; adding a sessionFactory would
 *  reduce the amount of params by ~ 5.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class AuthenticationService implements AuthenticationServiceSpecification {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    static final String[] WELCOME_MESSAGE = {
        "*************************************************************************************************************",
        "***                                                                                                       ***",
        "***              Welcome to the Coherent Datafeed: Thomson Reuters Elektron Edition version               ***",
        "***                                                                                                       ***",
        "***                                         Version 1.0.7-RELEASE                                         ***",
        "***                                                                                                       ***",
        "***                              Please take a moment to follow us on Twitter:                            ***",
        "***                                                                                                       ***",
        "***                                    www.twitter.com/CoherentMktData                                    ***",
        "***                                                                                                       ***",
        "***                                          or on LinkedIn:                                              ***",
        "***                                                                                                       ***",
        "***                            www.linkedin.com/company/coherent-logic-limited                            ***",
        "***                                                                                                       ***",
        "***                            The project and issue tracker can be found here:                           ***",
        "***                                                                                                       ***",
        "***           https://bitbucket.org/CoherentLogic/coherent-datafeed-thomson-reuters-trep-edition          ***",
        "***                                                                                                       ***",
        "*** ----------------------------------------------------------------------------------------------------- ***",
        "***                                                                                                       ***",
        "*** When running the example application, an RFA session entry with the name \"mySession\" must exist in    ***",
        "*** your configuration OR you must override this value — see below for an example of the configuration we ***",
        "*** use. You may override this value using the following VM parameter:                                    ***",
        "***                                                                                                       ***",
        "***     -DCDATAFEED_SESSION_NAME=\"[Add your session name here]\"                                           ***",
        "***                                                                                                       ***",
        "*** See also:                                                                                             ***",
        "***                                                                                                       ***",
        "***     http://bit.ly/1Oc0HTn                                                                             ***",
        "***                                                                                                       ***",
        "*** The logging properties will be read from the /logging.properties included in this jar file. This can  ***",
        "*** be overridden by setting the CDATAFEED_LOGGING_CONFIGURATION_FILE VM parameter -- for example:        ***",
        "***                                                                                                       ***",
        "***     -DCDATAFEED_LOGGING_CONFIGURATION_FILE=C:/Temp/logging.properties                                 ***",
        "***                                                                                                       ***",
        "*** ----------------------------------------------------------------------------------------------------- ***",
        "***                                                                                                       ***",
        "*** Be Advised:                                                                                           ***",
        "***                                                                                                       ***",
        "*** This framework uses the Google Analytics Measurement API to track that the framework is being used.   ***",
        "*** At this time data sent to Google includes information indicating that the framework has been started, ***",
        "*** the framework name, and version.                                                                      ***",
        "***                                                                                                       ***",
        "*** We do not recommend disabling this feature however we offer the option below, just add the following  ***",
        "*** VM parameter:                                                                                         ***",
        "***                                                                                                       ***",
        "*** -DGOOGLE_ANALYTICS_TRACKING=false                                                                     ***",
        "***                                                                                                       ***",
        "*** ----------------------------------------------------------------------------------------------------- ***",
        "***                                                                                                       ***",
        "*** We offer support and consulting services to businesses that utilize this framework or that have       ***",
        "*** custom projects that are based on the Reuters Foundation API for Java (RFA / RFAJ) and the Thomson    ***",
        "*** Reuters Elektron Platform. Inquiries can be directed to:                                              ***",
        "***                                                                                                       ***",
        "*** [M] sales@coherentlogic.com                                                                           ***",
        "*** [T] +1.571.306.3403 (GMT-5)                                                                           ***",
        "***                                                                                                       ***",
        "*************************************************************************************************************"
    };

//    static final String GOOGLE_ANALYTICS_TRACKING_KEY = "GOOGLE_ANALYTICS_TRACKING";
//
//    /**
//     * @see <a href="https://developers.google.com/analytics/devguides/collection/protocol/v1/devguide">Working with the
//     * Measurement Protocol</a>
//     *
//     * @see <a href="https://ga-dev-tools.appspot.com/hit-builder/">Hit Builder</a>
//     */
//    static final void fireGAFrameworkUsageEvent () {
//
//        log.debug("fireGAFrameworkUsageEvent: method begins.");
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://www.google-analytics.com/collect")
//            .queryParam("v", "1")
//            .queryParam("tid", "UA-1434183-1")
//            .queryParam("cid", UUID.randomUUID().toString())
//            .queryParam("t", "event")
//            .queryParam("ec", "Framework Usage") // event category
//            .queryParam("an", "CDTREP4J") // application name
//            .queryParam("ea", "Framework Started") // event action
//            .queryParam("av", "Version 1.0.7-RELEASE") // Application version.
//            .queryParam("el", "Version 1.0.7-RELEASE");
//
//        HttpHeaders headers = new HttpHeaders();
//
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//
//        RestTemplate restTemplate = new RestTemplate ();
//
//        HttpEntity<String> response = restTemplate.exchange(
//            builder.build().encode().toUri(),
//            HttpMethod.POST,
//            entity,
//            String.class
//        );
//
//        log.debug("fireGAFrameworkUsageEvent: method ends; response: " + response);
//    }

    static {

        GoogleAnalyticsMeasurementService googleAnalyticsMeasurementService = new GoogleAnalyticsMeasurementService ();

        if (googleAnalyticsMeasurementService.shouldTrack()) {
            try {
                googleAnalyticsMeasurementService.fireGAFrameworkUsageEvent ();
            } catch (Throwable thrown) {
                log.warn("fireGAFrameworkUsageEvent: method call failed.", thrown);
            }
        }

        WelcomeMessage welcomeMessage = new WelcomeMessage();

        for (String next : WELCOME_MESSAGE) {
            welcomeMessage.addText(next);
        }

        welcomeMessage.display();
    }

    private final LoginMessageBuilder loginMessageBuilder;

    private final EventQueue eventQueue;

    private final OMMConsumer ommConsumer;

    private final PositionFactory positionFactory;

    private final EventDrivenEndpoint eventDrivenEndpoint;

    private Handle handle = null;

    private OMMItemIntSpec ommItemIntSpec = null;

    // Was 257
    static final String DEFAULT_APPLICATION_ID = "257";
        //"Coherent Datafeed: Thomson Reuters Elektron Edition";

    public AuthenticationService(
        LoginMessageBuilder loginMessageBuilder,
        EventQueue eventQueue,
        OMMConsumer ommConsumer,
        PositionFactory positionFactory,
        EventDrivenEndpoint client
    ) {
        super();
        this.loginMessageBuilder = loginMessageBuilder;
        this.eventQueue = eventQueue;
        this.ommConsumer = ommConsumer;
        this.positionFactory = positionFactory;
        this.eventDrivenEndpoint = client;
    }

    @Override
    public Handle login (SessionBean sessionBean) {
        return login (sessionBean.getDacsId(), DEFAULT_APPLICATION_ID, sessionBean);
    }

    public Handle login (String dacsId) {
        return login (dacsId, DEFAULT_APPLICATION_ID, new SessionBean ());
    }

    public Handle login (String dacsId, SessionBean sessionBean) {
        return login (dacsId, DEFAULT_APPLICATION_ID, sessionBean);
    }

    /**
     * Call this method to authenticate with Thomson Reuters.
     *
     * @param dacsId The Thomson Reuters data access control system (DACS) id.
     *
     * @throws AuthenicationRuntimeException When the login fails.
     *
     * @todo Do we want to return the handle and hence keep this class
     *  stateless?
     */
    public Handle login (String dacsId, String applicationId, SessionBean sessionBean) {
        if (dacsId == null || "".equals(dacsId))
            throw new InvalidDacsIdException ("The dacs id cannot be null " +
                "or an empty string (dacsId: '" + dacsId + "').");

        if (applicationId == null || "".equals(applicationId))
            throw new InvalidApplicationIdException ("The application id "
                + "cannot be null or an empty string (applicationId: '"
                + applicationId + "').");

        Utils.assertNotNull("sessionBean", sessionBean);

        log.info("dacsId: " + dacsId + ", applicationId: " + applicationId + ", sessionBean: " + sessionBean);

        try {

            String position = positionFactory.getInstance();

            OMMMsg encodedMsg = 
                loginMessageBuilder
                    .createOMMMsg()
                    .setMsgType(OMMMsg.MsgType.REQUEST)
                    .setMsgModelType(RDMMsgTypes.LOGIN)
                    .setIndicationFlags(OMMMsg.Indication.REFRESH)
                    .setUserName(dacsId)
                    .encodeMsgInit(OMMTypes.ELEMENT_LIST, OMMTypes.NO_DATA)
                    .encodeElementListInit(
                        OMMElementList.HAS_STANDARD_DATA, (short) 0, (short) 0)
                    .encodeElementEntryInit(
                        RDMUser.Attrib.ApplicationId, OMMTypes.ASCII_STRING)
                    .encodeAsASCIIString(applicationId)
                    .encodeElementEntryInit(
                        RDMUser.Attrib.Position, OMMTypes.ASCII_STRING)
                    .encodeAsASCIIString(position)
                    .encodeElementEntryInit(RDMUser.Attrib.Role, OMMTypes.UINT)
                    .encodeUInt(RDMUser.Role.CONSUMER)
                    .encodeAggregateComplete()
                    .getMessage();

            ommItemIntSpec = new OMMItemIntSpec();
            ommItemIntSpec.setMsg(encodedMsg);

            handle = ommConsumer.registerClient(
                eventQueue,
                ommItemIntSpec,
                eventDrivenEndpoint,
                sessionBean
            );

            log.info("handle: " + handle);

        } catch (RuntimeException runtimeException) {
            log.error("The call to login using the dacsId " + dacsId + " failed.", runtimeException);
            throw runtimeException;
        }
        return handle;
    }

    /**
     * @todo We need to make the handle a param so that this class can then
     *  become completely stateless.
     *
     * Terminates the session with Thomson Reuters.
     */
    public void logout () {
        try {
            if (handle == null)
                throw new SessionFinalizationFailedException (
                    "Logout has failed -- are you sure login was ever called?");

            ommConsumer.unregisterClient(handle, ommItemIntSpec);

            handle = null;
            ommItemIntSpec = null;
        } catch (RuntimeException runtimeException) {
            log.error("The call to logout failed.", runtimeException);
            throw runtimeException;
        }
    }

    /**
     * Getter method for the handle.
     *
     * @return A reference to the Handle or null if this has not been set.
     *
     * @see {@link com.reuters.rfa.common.Handle}
     */
    public Handle getHandle() {
        return handle;
    }

    /**
     * Getter method for the OMMItemIntSpec instance which is passed to
     * Thomson Reuters when this API establishes a session with their server.
     *
     * @return A reference to the OMMItemIntSpec or null if this has not been
     *  set.
     *
     * @see {@link com.reuters.rfa.session.omm.OMMItemIntSpec}
     */
    OMMItemIntSpec getOmmItemIntSpec() {
        return ommItemIntSpec;
    }
}
