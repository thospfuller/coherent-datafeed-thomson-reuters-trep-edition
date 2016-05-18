package com.coherentlogic.coherent.datafeed.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

import javax.annotation.PostConstruct;

import com.coherentlogic.coherent.datafeed.exceptions.
    ApplicationInitializationFailedException;

/**
 * The Java Logging framework does not (apparently) load the configuration file
 * from the jar without some work, which is performed in this class.
 *
 * This bean should be one of the first beans configured.
 *
 * Stream.OPEN, Data.OK, Code.NONE -- The Login has been accepted by the
 *                                    provider. The consumer application
 *                                    established the Login event stream.
 *
 * Stream.OPEN, Data.SUSPECT -- The connection is down or login is pending on
 *                              all connections.
 *
 *
 * Stream.CLOSED, Code. NOT_ENTITLED -- The Login failed. If the provider did
 *                                      not accept Login credentials, it will
 *                                      set the Code to NOT_ENTITLED.
 *
 * Stream.CLOSED Code.[TBD] -- The Login can fail for other reasons, for
 *                             example, a timeout occurred on the network while
 *                             processing the request. In this case, the Code
 *                             will be set accordingly. An application can
 *                             determine how to proceed based on the Code. Refer
 *                             to Table 35 for the Code definitions.
 *
 * @see RFA-DevGuide72.pdf section 10.2.6.3.3
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class LoggingService {

    private static final String
        ROOT_PROPERTIES_FILE_PATH = "/logging.properties",
        LOGGING_CONFIGURATION_FILE = "CDATAFEED_LOGGING_CONFIGURATION_FILE";

    public static final String BEAN_NAME = "loggingService";

    @PostConstruct
    public void initialize () {

        String result = System.getProperty(LOGGING_CONFIGURATION_FILE);

        if (result == null)
            result = System.getenv(LOGGING_CONFIGURATION_FILE);

        if (result == null)
            System.out.println("The logging properties will be read from the " + ROOT_PROPERTIES_FILE_PATH +
                " included in this jar file. This can be overridden by setting the " + LOGGING_CONFIGURATION_FILE +
                " system properties settings -- for example -D" + LOGGING_CONFIGURATION_FILE +
                "=C:/Temp/logging.properties");
                // In Eclipse this is a VM arg.
        else
            System.out.println("The logging properties will be read from the file " + result);

        InputStream inputStream = null;

        if (result == null)
            inputStream = LoggingService.class.getResourceAsStream(ROOT_PROPERTIES_FILE_PATH);
        else
            try {
                inputStream = new FileInputStream (new File (result));
            } catch (final IOException ioException) {
                throw new ApplicationInitializationFailedException ("Unable to read the logging configuration file: " +
                    result + " (note that if this value is null then the file is being read from the jar and is " +
                    "likely missing).", ioException);
            }

        try {
            LogManager.getLogManager().readConfiguration(inputStream);
        } catch (final IOException ioException) {
            throw new ApplicationInitializationFailedException (
                "Unable to read the properties file located here: "
                + ROOT_PROPERTIES_FILE_PATH + ".", ioException);
        }
    }
}
