package com.coherentlogic.coherent.datafeed.configuration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.domain.RFAVersionInfoBean;
import com.coherentlogic.coherent.datafeed.exceptions.ApplicationInitializationFailedException;

/**
 * Unit test for the {@link ContextInitializer} class.
 *
 * @todo Not all of these tests have been completed, so the implementation
 *  details need to be filled in.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class ContextInitializerTest {

    private ContextInitializer contextInitializer = null;

    @Before
    public void setUp() throws Exception {
        contextInitializer = new ContextInitializer ();
    }

    @After
    public void tearDown() throws Exception {
        contextInitializer = null;
        System.clearProperty (ContextInitializer.CONFIGURATION_FILE);
    }

    /**
     * In this example we invoke stop in order to ensure that no exceptions are
     * thrown when start has not been called.
     *
     * @TODO This test seems to be failing with the upgrade to RFA 7.4+ whereas
     *  previously this was working fine.
     *
     * @see {@link ContextInitializer#stop()} documentation for comments about
     *  the behaviour of this method.
     */
    @Test
    public void testStop() {
        contextInitializer.stop();
    }

    /**
     * This is more of an integration test since this test will fail if the
     * preferences have not been set in the database.
     * 
     * @see rfaj7.2.1.L1.all.rrg/Tools/config_editor.bat to configure these
     *  values.
     */
    @Test
    public void startStopWithConfigurationInTheDatabase() {

        contextInitializer.start();

        assertTrue (contextInitializer.isRunning());

        contextInitializer.stop();

        assertFalse (contextInitializer.isRunning());
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void startPreferencesFromMissingExternalFile() {
        System.setProperty(ContextInitializer.CONFIGURATION_FILE,
            "./this_file_should_not_exist.xml");

        contextInitializer.start();
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void startFromExistingAndCorruptExternalFile() {
        System.setProperty(ContextInitializer.CONFIGURATION_FILE,
            "./corruptConfiguration.xml");

        contextInitializer.start();
    }

    @Test
    public void startFromExistingAndValidExternalFile() {
        System.setProperty(ContextInitializer.CONFIGURATION_FILE,
            "src/main/resources/external-configuration.xml");

        contextInitializer.start();
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void loadPreferencesFromNullExternalFileString() {
        contextInitializer.loadPreferencesFromExternalFile((String)null);
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void loadPreferencesFromNullExternalFileUsingEmptyString() {
        contextInitializer.loadPreferencesFromExternalFile("");
    }

    @Test(expected=ApplicationInitializationFailedException.class)
    public void loadPreferencesFromNullExternalFile() {
        contextInitializer.loadPreferencesFromExternalFile((File)null);
    }

    /**
     * This is more of an integration test since this test will fail if the
     * preferences have not been set in the database.
     */
    @Test
    public void testLoadPreferencesFromDatabase() {
        boolean result = contextInitializer.loadPreferencesFromDatabase();

        assertTrue (result);
    }

    @Test
    public void getRFAVersionInfo () {

        // NOTE: I'm not sure how this was working before as the line below was
        // not present; adding this fixes the failing tests, and it makes sense
        // as the prefs do need to be loaded.
        contextInitializer.loadPreferencesFromDatabase();

        RFAVersionInfoBean result = contextInitializer.getRFAVersionInfo();

        assertNotNull (result);

        String productVersion = result.getProductVersion();

        // This should actually be set to "RFAJava product version is
        // unavailable until Session package is loaded".
        assertNotNull (productVersion);
    }
}
