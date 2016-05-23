package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DACS_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.client.Client;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntries;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.reuters.rfa.common.QualityOfService;
import com.reuters.rfa.rdm.RDMMsgTypes;

/**
 * Integration test for the {@link DirectoryService} class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 *
 * @todo Ignored for now until the service is starting properly.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="/spring/application-context.xml")
@Ignore
public class DirectoryServiceTest {

    private static final Logger log = LoggerFactory
        .getLogger(DirectoryServiceTest.class);

    private DirectoryGatewaySpecification directoryService = null;

    private Client client = null;

    @Before
    public void setUp() throws Exception {

        client = new Client ();

        client.start();

        // This needs to be set in the operating system environment variables.
        String dacsId = System.getenv(DACS_ID);

        client.login(dacsId);

        log.info("waitForInitialisationToComplete: method invoked.");

        client.waitForInitialisationToComplete();

        log.info("waitForInitialisationToComplete: method returns.");

        directoryService = client.getDirectoryService();

        log.info("directoryService: obtained; value: " + directoryService);
    }

    @After
    public void tearDown() throws Exception {
        client.logout();
        client.stop();
        client = null;
        directoryService = null;
    }

    @Test
    public void getDirectories () {
        DirectoryEntries directoryEntries =
            directoryService.getDirectoryEntries();

        Set<DirectoryEntry> directoryEntrySet =
            directoryEntries.getDirectoryEntries();

        assertNotNull (directoryEntrySet);

        for (DirectoryEntry entry : directoryEntrySet) {
            log.info("entry: " + ToStringBuilder.reflectionToString(entry));
        }

        assertNotNull (directoryEntrySet);

        assertEquals (2, directoryEntrySet.size());

        reviewDIDN_RDFDirectoryEntry (directoryEntrySet);

        
    }

    private void reviewDIDN_RDFDirectoryEntry (
        Set<DirectoryEntry> directoryEntrySet
    ) {
        DirectoryEntry actual = find ("dELEKTRON_DD", directoryEntrySet);

        List<String> expectedDictionariesUsed = new ArrayList<String> ();
        expectedDictionariesUsed.add("RWFFld");
        expectedDictionariesUsed.add("RWFEnum");

        assertEquals(expectedDictionariesUsed, actual.getDictionariesUsed());

        checkCapabilities (actual);
        checkDictionaries(actual.getDictionariesProvided());
        checkDictionaries(actual.getDictionariesUsed());
//        checkQoS (actual.getQualityOfService());

        assertEquals (0, actual.getSupportsQoSRange());
        assertEquals (8000, actual.getServiceID());
        assertEquals (Long.valueOf(1L), actual.getServiceState());
        assertEquals (true, actual.isAcceptingRequests());
    }

    private void checkCapabilities (DirectoryEntry actual) {
        List<String> capabilities = new ArrayList<String> ();
        capabilities.add(RDMMsgTypes.toString(RDMMsgTypes.DICTIONARY));
        capabilities.add(RDMMsgTypes.toString(RDMMsgTypes.MARKET_PRICE));
        capabilities.add(RDMMsgTypes.toString(RDMMsgTypes.SYMBOL_LIST));

        assertEquals (capabilities, actual.getCapabilities());
    }

    private void checkDictionaries (List<String> actualDictionaries) {
        List<String> expectedDictionaries = new ArrayList<String> ();
        expectedDictionaries.add("RWFFld");
        expectedDictionaries.add("RWFEnum");

        assertEquals(expectedDictionaries, actualDictionaries);
    }

    private void checkQoS (List<QualityOfService> actualQoSList) {
        throw new RuntimeException ("This method has not been implemented!");
//        List<QualityOfService> expectedQoSList =
//            new ArrayList<QualityOfService> ();
//
//        // Delayed  Unspecified Delayed Timeliness, TbT
//        
//        new QualityOfService().
//        
//        expectedQoSList.add(QualityOfService.QOS_UNSPECIFIED);
//        expectedQoSList.add(QualityOfService.QOS_REALTIME_TICK_BY_TICK);
////        expectedQoSList.add(QualityOfService.TICK_BY_TICK);
//
//        assertEquals(expectedQoSList, actualQoSList);
    }

    DirectoryEntry find (
        String name,
        Set<DirectoryEntry> directoryEntrySet
    ) {
        DirectoryEntry result = null;

        for (DirectoryEntry entry : directoryEntrySet) {
            if (name.equals(entry.getName())) {
                result = entry;
                break;
            }
        }
        return result;
    }
}

/*
ELEMENT_LIST
        ELEMENT_ENTRY Name: dIDN_RDF
        ELEMENT_ENTRY Capabilities: 
            ARRAY
                ARRAY_ENTRY: 5
                ARRAY_ENTRY: 6
                ARRAY_ENTRY: 10
        ELEMENT_ENTRY DictionariesProvided: 
            ARRAY
                ARRAY_ENTRY: RWFFld
                ARRAY_ENTRY: RWFEnum
        ELEMENT_ENTRY DictionariesUsed: 
            ARRAY
                ARRAY_ENTRY: RWFFld
                ARRAY_ENTRY: RWFEnum
        ELEMENT_ENTRY QoS: 
            ARRAY
                ARRAY_ENTRY: (Delayed  Unspecified Delayed Timeliness, TbT)
        ELEMENT_ENTRY SupportsQoSRange: 0
        ELEMENT_ENTRY ServiceID: 8000
FILTER_ENTRY 2 (Action.SET) : 
flags: 
    ELEMENT_LIST
        ELEMENT_ENTRY ServiceState: 1
        ELEMENT_ENTRY AcceptingRequests: 1
 */













//@Autowired
//protected AbstractApplicationContext applicationContext = null;
//
//@Autowired
//protected AuthenticationServiceSpecification authenticationService = null;
//
//@Autowired
//private ConcurrentDictionaryService dictionaryService = null;
//
//@Autowired
//private TimeSeriesService timeSeriesService = null;
//
//private Handle loginHandle = null;
//
//@Before
//public void setUp() throws Exception {
//  applicationContext.registerShutdownHook();
//  String dacsId = System.getenv(DACS_ID);
//
//  loginHandle = authenticationService.login(dacsId);
//
//  List<Handle> dictionaryHandles = dictionaryService.loadDictionaries(
//      loginHandle, ServiceName.IDN_RDF, "RWFFld", "RWFEnum");
//}
//
//@After
//public void tearDown() throws Exception {
//  authenticationService.logout();
//  applicationContext.close();
//}
//
//@Test
//public void query () throws InterruptedException {
//    DirectoryService directoryService =
//      (DirectoryService)
//          applicationContext.getBean(DIRECTORY_SERVICE);
//
//  StatusResponseService statusResponseService = (StatusResponseService)
//      applicationContext.getBean(STATUS_RESPONSE_SERVICE);
//
//  String dacsId = System.getenv(DACS_ID);
//
//  List<Handle> handles =
//      directoryService.query(loginHandle);
//
//  Thread.sleep(5000L);
//}