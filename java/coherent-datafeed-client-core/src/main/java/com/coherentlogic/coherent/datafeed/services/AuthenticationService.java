package com.coherentlogic.coherent.datafeed.services;

import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.datafeed.beans.UserBean;
import com.coherentlogic.coherent.datafeed.builders.LoginMessageBuilder;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidApplicationIdException;
import com.coherentlogic.coherent.datafeed.exceptions.InvalidDacsIdException;
import com.coherentlogic.coherent.datafeed.exceptions.SessionFinalizationFailedException;
import com.coherentlogic.coherent.datafeed.factories.PositionFactory;
import com.coherentlogic.coherent.datafeed.factories.SessionFactory;
import com.coherentlogic.coherent.datafeed.integration.endpoints.EventDrivenEndpoint;
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
@Transactional
public class AuthenticationService
    implements AuthenticationServiceSpecification {

    private static final Logger log =
        LoggerFactory.getLogger(AuthenticationService.class);

    private final LoginMessageBuilder loginMessageBuilder;

    private final EventQueue eventQueue;

    private final OMMConsumer ommConsumer;

    private final PositionFactory positionFactory;

    private final EventDrivenEndpoint eventDrivenEndpoint;

    private final Cache<Handle, Session> sessionCache;

    private final SessionFactory sessionFactory;

    private final UserBean userBean;

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
        EventDrivenEndpoint client,
        Cache<Handle, Session> sessionCache,
        SessionFactory sessionFactory,
        UserBean userBean
    ) {
        super();
        this.loginMessageBuilder = loginMessageBuilder;
        this.eventQueue = eventQueue;
        this.ommConsumer = ommConsumer;
        this.positionFactory = positionFactory;
        this.eventDrivenEndpoint = client;
        this.sessionCache = sessionCache;
        this.sessionFactory = sessionFactory;
        this.userBean = userBean;
    }

    public Handle login (String dacsId) {
        return login (dacsId, DEFAULT_APPLICATION_ID);
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
    public Handle login (String dacsId, String applicationId) {
        if (dacsId == null || "".equals(dacsId))
            throw new InvalidDacsIdException ("The dacs id cannot be null " +
                "or an empty string (dacsId: '" + dacsId + "').");

        if (applicationId == null || "".equals(applicationId))
            throw new InvalidApplicationIdException ("The application id "
                + "cannot be null or an empty string (applicationId: '"
                + applicationId + "').");

        log.info("dacsId: " + dacsId + ", applicationId: " + applicationId);

        try {
            userBean.setDacsId(dacsId);

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
                null
            );

            Session session = sessionFactory.getInstance(handle);

            session.setDacsId(dacsId);
            session.setLoginHandle(handle);

            sessionCache.put(handle, session);

            log.info("handle: " + handle);
        } catch (RuntimeException runtimeException) {
            log.error("The call to login using the dacsId " + dacsId +
                " failed.", runtimeException);
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
