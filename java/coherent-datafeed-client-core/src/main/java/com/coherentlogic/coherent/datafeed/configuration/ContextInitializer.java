package com.coherentlogic.coherent.datafeed.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.Lifecycle;

import com.coherentlogic.coherent.datafeed.domain.RFAVersionInfoBean;
import com.coherentlogic.coherent.datafeed.exceptions.ApplicationInitializationFailedException;
import com.coherentlogic.coherent.datafeed.factories.RFASessionFactory;
import com.reuters.rfa.common.Context;
import com.reuters.rfa.common.RFAVersionInfo;

/**
 * A context initializer which loads the preferences from either the Java
 * Preferences database or from an external preferences file.
 *
 * External configuration is set as a VM parameter, for example:
 *
 * -DCDATAFEED_CONFIGURATION_FILE=
 *  C:/development/projects/CDATAFEED-SVN-CO/docs/rfa-preferences.xml
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class ContextInitializer implements Lifecycle {

    public static final String CONFIGURATION_FILE =
        "CDATAFEED_CONFIGURATION_FILE";

    private static final Logger log =
        LoggerFactory.getLogger(ContextInitializer.class);

    private boolean running = false;

    /**
     * Method looks for the {@value #CONFIGURATION_FILE} setting first in the
     * system properties settings and then (if the value is null) in the
     * environment properties settings and then returns this value.
     */
    String getConfigurationFilePath () {
        String result = System.getProperty(CONFIGURATION_FILE);

        if (result == null)
            result = System.getenv(CONFIGURATION_FILE);

        log.info("result: " + result);

        return result;
    }

    @Override
    public void start() {

        String configurationFilePath = getConfigurationFilePath ();

        log.info("configurationFilePath: " + configurationFilePath);

        boolean success = false;

        if (configurationFilePath != null)
            success = loadPreferencesFromExternalFile (configurationFilePath);
        else
            success = loadPreferencesFromDatabase ();

        if (!success)
            throw new ApplicationInitializationFailedException (
                "Application preferences could not be loaded (context " +
                "initialization was unsuccessful).");

        running = true;
    }

    /**
     * Method loads the configuration using the configurationFilePath provided.
     *
     * @param configurationFilePath The path to the configuration file.
     *
     * @return The result of the call to Context.initialize.
     */
    boolean loadPreferencesFromExternalFile (String configurationFilePath) {

        log.info("Loading preferences externally using the " +
            "configurationFilePath: " + configurationFilePath);

        if (configurationFilePath == null || "".equals(configurationFilePath))
            throw new ApplicationInitializationFailedException(
                "The configurationFilePath '" + configurationFilePath +
                "' is invalid.");

        File file = new File (configurationFilePath);

        return loadPreferencesFromExternalFile (file);
    }

    /**
     * Method loads the configuration using the configurationFilePath provided.
     *
     * @param file The configuration file.
     *
     * @return The result of the call to Context.initialize.
     */
    boolean loadPreferencesFromExternalFile (File file) {

        if (file == null || !file.exists())
            throw new ApplicationInitializationFailedException(
                "The configuration file '" + file +
                "' does not exist.");

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ApplicationInitializationFailedException(
                "Configuration failed using the configuration file path '" +
                file.getAbsolutePath() + "'.");
        }

        try {
            Preferences.importPreferences(inputStream);
        } catch (IOException | InvalidPreferencesFormatException e) {
            throw new ApplicationInitializationFailedException(
                "Unable to import the preferences using the configuration " +
                "file path '" + file.getAbsolutePath() + "'.");
        }

        return Context.initialize();
    }

    /**
     * The default is to load the preferences the user has configured in the
     * preferences database.
     */
    boolean loadPreferencesFromDatabase () {

        log.info("Loading the configuration from the preferences database.");

        return Context.initialize();
    }

    /**
     * Uninitialize will return false whenever the initialized count is
     * initially zero or greater than zero after being decremented by one,
     * therefore we no longer throw an exception if the result is false, we
     * simply log a warning message and move on.
     */
    @Override
    public void stop() {

        boolean result = false;

         result = Context.uninitialize();

        long initializedCount = Context.getInitializedCount();

        if (!result)
            log.warn(
                "The context could not be uninitialized (initializedCount: " +
                initializedCount + ").");

        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    /**
     * Getter method for the bean that contains the RFA version information;
     * note that this method should only be called once the
     * {@link RFASessionFactory} has been created, otherwise the productVersion
     * will be set to "RFAJava product version is unavailable until Session
     * package is loaded".
     * 
     * Precondition:
     * <code>
     * Session session = new RFASessionFactory ("mySession").getInstance(); 
     * </code>
     * @return An instance of {@link RFAVersionInfoBean} with the product
     *  version set to something like "7.4.0.L1.all.rrg".
     */
    public RFAVersionInfoBean getRFAVersionInfo () {

        RFAVersionInfo rfaVersionInfo = Context.getRFAVersionInfo();

        String productVersion = rfaVersionInfo.getProductVersion();

        RFAVersionInfoBean result = new RFAVersionInfoBean ();

        result.setProductVersion(productVersion);

        return result;
    }
}
