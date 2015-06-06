package com.coherentlogic.coherent.datafeed.services;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration test for the {@link FieldDictionaryHolder} class.
 *
 * @todo Ignoring for now until we have the service running properly (meaning
 *  the application is starting after the service loads the dictionary.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    locations="/spring/services-config/application-context.xml")
@Ignore
public class DictionaryServiceTest {

//    @Autowired
//    protected AbstractApplicationContext applicationContext = null;
//
//    @Autowired
//    protected AuthenticationServiceSpecification authenticationService = null;
//
//    @Autowired
//    private ConcurrentDictionaryService dictionaryService = null;
//
//    private Cache<Handle, ? extends CachedEntry> cache = null;
//
//    @Autowired
//    private FieldDictionary fieldDictionary = null;
//
//    @Before
//    public void setUp () {
//        applicationContext.registerShutdownHook();
//        cache = (Cache) applicationContext.getBean(CACHE_BEAN_ID);
//    }
//
//    @After
//    public void tearDown () {
//        authenticationService.logout();
//        applicationContext.close();
//        cache = null;
//        fieldDictionary = null;
//    }
//
//    /**
//     * @todo Remove the sleep and wait for a reply on an object.
//     */
//    @Test
//    public void loadDictionary () throws InterruptedException {
//
//        String dacsId = System.getenv(DACS_ID);
//
//        authenticationService.login(dacsId);
//
//        Handle handle = authenticationService.getHandle();
//
//        List<Handle> dictionaryHandles = dictionaryService.loadDictionaries(
//            handle, ServiceName.IDN_RDF, "RWFFld", "RWFEnum");
//
//        assertNotNull (dictionaryHandles);
//
//        int size = fieldDictionary.size();
//
//        // This is the correct number as of 14 Dec 2012 however it may change so
//        // if this test starts failing, just review the number that's returned
//        // from TR and adjust accordingly.
//        assertEquals (6239, size);
//    }
}
