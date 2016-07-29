package com.coherentlogic.coherent.datafeed.adapters.json;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.google.gson.Gson;

/**
 * An adapter that using the <a href="https://google.github.io/gson/apidocs/">Google GSON API</a> to convert a source
 * object of type S into a JSON String.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="support@coherentlogic.com">Support</a>
 *
 * @param <S> The source object which will be converted into a result of type String.
 */
public abstract class AbstractGJSONGenerator<S>
    implements BasicAdapter<S, String> {

    private final Gson gson;

    AbstractGJSONGenerator (Gson gson) {
        this.gson = gson;
    }

    protected Gson getGson() {
        return gson;
    }
}
