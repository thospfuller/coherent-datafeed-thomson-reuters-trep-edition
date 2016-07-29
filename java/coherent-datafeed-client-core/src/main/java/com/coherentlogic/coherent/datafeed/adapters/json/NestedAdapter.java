package com.coherentlogic.coherent.datafeed.adapters.json;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;

/**
 * An adapter that facilitates the transformation of an object of type S into
 * and object of type U by passing it first through the {@link #firstAdapter}
 * and then through the {{@link #secondAdapter}.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="support@coherentlogic.com">Support</a>
 *
 * @param <S> The source object type.
 * @param <X> The transitional object type.
 * @param <T> The target object type.
 *
 * @deprecated Deprecated in favor of GJSONGenerator and associated classes.
 */
public class NestedAdapter<S, X, T> implements BasicAdapter<S, T> {

    private BasicAdapter<S, X> firstAdapter;

    private BasicAdapter<X, T> secondAdapter;

    public NestedAdapter(
        BasicAdapter<S, X> firstAdapter,
        BasicAdapter<X, T> secondAdapter
    ) {
        super();
        this.firstAdapter = firstAdapter;
        this.secondAdapter = secondAdapter;
    }

    @Override
    public T adapt(S source) {

        X temp = firstAdapter.adapt(source);

        T result = secondAdapter.adapt(temp);

        return result;
    }
}
