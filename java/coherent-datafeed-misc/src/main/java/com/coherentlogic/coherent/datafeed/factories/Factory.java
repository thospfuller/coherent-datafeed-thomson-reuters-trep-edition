package com.coherentlogic.coherent.datafeed.factories;

/**
 * Generic interface for classes that follow the factory pattern.
 *
 * @param <T> The type of object the getInstance method returns.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public interface Factory<T> {

    T getInstance ();
}
