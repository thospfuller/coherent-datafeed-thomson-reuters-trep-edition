package com.coherentlogic.coherent.datafeed.adapters;

/**
 * This adapter allows the developer to specify what the return type should be.
 *
 * Note that the return type does not need to necessarily be cast to the given
 * type, but the type can be used as an indicator which method to call on the
 * source object. This makes sense when, for example, we're looking at
 * {@link OMMDateTime} objects -- in this case the return value can be a
 * {@link java.util.Date} or a {@link java.lang.Long}, and the type can help
 * determine which is returned.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <S> The source type.
 * @param <T> The target type.
 */
public interface FlexibleAdapter<S, T> {

    /**
     * Method converts the source s into an instance of T and returns this type.
     *
     * @param <T> The return value type.
     * @param s The source which will be converted.
     * @param type The return type.
     *
     * @return An instance of T.
     *
     * @throws UnsupportedOperationException if this method is not supported.
     */
    <X> X adapt (S s, Class<X> type);
}
