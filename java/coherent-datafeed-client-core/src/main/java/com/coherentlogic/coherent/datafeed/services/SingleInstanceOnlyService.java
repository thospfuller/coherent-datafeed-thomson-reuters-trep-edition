package com.coherentlogic.coherent.datafeed.services;

import java.io.IOException;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.Stoppable;
import com.coherentlogic.coherent.datafeed.exceptions.ApplicationAlreadyRunningException;
import com.coherentlogic.coherent.datafeed.exceptions.FatalRuntimeException;

/**
 * @ComponentScan(basePackages=\"com.coherentlogic.coherent.datafeed\").
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class SingleInstanceOnlyService implements Stoppable {

    private static final Logger log = LoggerFactory.getLogger(SingleInstanceOnlyService.class);

    private final ServerSocket socket;

    public SingleInstanceOnlyService(int port) {

        ServerSocket serverSocket = null;

        log.info("Attempting to reserve port: " + port + ".");

        try {
            serverSocket = new ServerSocket (port);
        } catch (IOException ioException) {
            throw new ApplicationAlreadyRunningException("There's already an instance of this application running "
                    + "-- see notes in this class for further details regarding what may be going on here.", ioException);
        }

        log.info("The application has successfully reserved port: " + port + ".");

        this.socket = serverSocket;
    }

    @Override
    public void stop() {
        try {
            socket.close();
        } catch (IOException ioException) {
            throw new FatalRuntimeException("Unable to close the socket " + socket, ioException);
        }
    }

    public static void main (String[] unused) throws IOException {
        SingleInstanceOnlyService firstInstance = new SingleInstanceOnlyService (19999);
        firstInstance.stop ();
//        SingleInstanceOnlyService secondInstance = new SingleInstanceOnlyService (19999);
    }
}
