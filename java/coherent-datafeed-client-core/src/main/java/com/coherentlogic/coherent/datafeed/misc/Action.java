package com.coherentlogic.coherent.datafeed.misc;

/**
 * An interface that acts as a closure. 
 *
 * @param <T> The type of object this action applies to.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public interface Action<T> {

    T execute (T t);
}
