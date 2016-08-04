package com.coherentlogic.coherent.datafeed.adapters;

/**
 * An interface that describes the contract an adapter must implement.
 *
 * @param <S> The source object which will be converted into a result of type T.
 * @param <T> The target object type.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface BasicAdapter<S, T> {

    /**
     * Method converts the object s into an object of type T and then returns
     * that object.
     *
     * @param s The source object.
     * @return An object of type T, which represents the target object.
     */
    T adapt (S s);
}
