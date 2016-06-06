package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.CACHE_BEAN_ID;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.infinispan.Cache;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.coherentlogic.coherent.datafeed.beans.CachedEntry;
import com.coherentlogic.coherent.datafeed.beans.TS1DefEntry;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.common.Handle;

/**
 * Unit test for the {@link TS1DefService} class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    locations="/spring/application-context.xml")
@Ignore
public class TS1DefServiceTest {

    private static final String DACS_ID_KEY = "DACS_ID";

    private static final String DACS_ID = System.getenv(DACS_ID_KEY);

    @Autowired
    protected AbstractApplicationContext applicationContext = null;

    @Autowired
    protected AuthenticationServiceSpecification authenticationService = null;

    @Autowired
    protected TS1DefService ts1DefService = null;

    private Cache<Handle, ? extends CachedEntry> cache = null;

    @Before
    public void setUp () {
        applicationContext.registerShutdownHook();
        cache = (Cache) applicationContext.getBean(CACHE_BEAN_ID);
    }

    @After
    public void tearDown () {
        authenticationService.logout();
        applicationContext.close();
        cache = null;
    }

    @Test
    public void testInitializeHandleStringArray() throws InterruptedException {

        String dacsId = System.getenv(DACS_ID);

        SessionBean sessionBean = new SessionBean ();

        sessionBean.setDacsId(dacsId);

        Handle loginHandle = authenticationService.login(sessionBean);

        List<Handle> handles = ts1DefService.initialize(loginHandle, sessionBean, "QQCN", "QQCO", "QQCP");

        assertEquals(3, handles.size());

        Handle firstHandle = handles.get(0);

        Object result = cache.get(firstHandle);

        assertTrue (result instanceof TS1DefEntry);
    }
}
