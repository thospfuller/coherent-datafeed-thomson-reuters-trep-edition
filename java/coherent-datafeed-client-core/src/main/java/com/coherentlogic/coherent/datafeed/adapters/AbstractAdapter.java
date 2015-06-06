package com.coherentlogic.coherent.datafeed.adapters;

/**
 * An adapter that contains a default implementation of the
 * {@link #adapt(Object, Class)} method.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <S> The source type.
 * @param <T> The target type.
 */
public abstract class AbstractAdapter<S, T>
    implements BasicAdapter<S, T>, FlexibleAdapter<S, T> {

    @Override
    public <X> X adapt(S s, Class<X> type) {
        T result = adapt(s);
        return type.cast(result);
    }
}
