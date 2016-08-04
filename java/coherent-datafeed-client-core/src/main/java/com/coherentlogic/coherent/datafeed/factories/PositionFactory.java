package com.coherentlogic.coherent.datafeed.factories;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.exceptions.FatalRuntimeException;

/**
 * Factory that takes care of creating the position string which is sent to
 * Thomson Reuters. A position looks like:
 * 
 *     192.168.0.4/GODZILLA
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class PositionFactory implements TypedFactory<String> {

    private static final Logger log =
        LoggerFactory.getLogger(PositionFactory.class);

    public String getInstance () {

        String position = null;

        try {
            position =
                InetAddress.getLocalHost().getHostAddress() + "/" +
                InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new FatalRuntimeException("Unable to construct the " +
                "position string.", e);
        }

        log.info("position: " + position);

        return position;
    }
}
