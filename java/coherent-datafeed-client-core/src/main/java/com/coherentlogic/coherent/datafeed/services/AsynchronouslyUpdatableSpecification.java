package com.coherentlogic.coherent.datafeed.services;

/**
 * This specification describes the methods that a class must implement when it
 * converts an update of any data into JSON.
 *
 * @todo This interface should be renamed to something better as it hosts
 *  methods that are not strictly returning JSON -- that or move that method to
 *  a base class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface AsynchronouslyUpdatableSpecification<R> {

    String getNextUpdateAsJSON (Long timeout);

    /**
     * Method attempts to get the next market price update and convert the
     * result to JSON within the given timeout.
     *
     * @param timeout Milliseconds to wait for the next message, if no message
     *  is returned an exception should be thrown. Note that a timeout of zero
     *  indicates an infinite wait.
     *
     * @return The next market price update as JSON.
     */
    String getNextUpdateAsJSON (String timeout);

    R getNextUpdate (Long timeout);
}
