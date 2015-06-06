package com.coherentlogic.coherent.datafeed.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.reuters.rfa.common.Handle;
import com.reuters.rfa.session.omm.OMMItemIntSpec;

/**
 * Integration test for the AuthenticationService class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    locations="/spring/application-context.xml")
public class AuthenticationServiceTest {

    private static final String DACS_ID_KEY = "DACS_ID";

    private static final String DACS_ID = System.getenv(DACS_ID_KEY);

    @Autowired
    protected AbstractApplicationContext applicationContext = null;

    @Autowired
    protected AuthenticationService authenticationService = null;

    @Before
    public void setUp() throws Exception {
        applicationContext.registerShutdownHook();
    }

    @After
    public void tearDown() throws Exception {
        applicationContext.close();
        authenticationService = null;
    }

    /**
     * This test performs a login and then checks the state of the
     * authenticationService -- it then calls logout and checks the state again.
     */
    @Test
    public void testLoginLogout() {
        authenticationService.login(DACS_ID);

        Handle handle = authenticationService.getHandle();
        OMMItemIntSpec ommIntSpec = authenticationService.getOmmItemIntSpec();

        assertNotNull(handle);
        assertNotNull(ommIntSpec);
        assertTrue (handle.isActive());

        authenticationService.logout();

        // @todo: Do we want to check that the hanld is active? It's not clear
        // at this time when this becomes inactive though the documentation
        // suggests this may be once a ComplEvent has been received.
        // assertFalse (handle.isActive());

        handle = authenticationService.getHandle();
        ommIntSpec = authenticationService.getOmmItemIntSpec();

        assertNull(handle);
        assertNull(ommIntSpec);
    }
}
