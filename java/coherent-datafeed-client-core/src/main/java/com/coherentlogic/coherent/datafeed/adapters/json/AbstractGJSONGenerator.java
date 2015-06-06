package com.coherentlogic.coherent.datafeed.adapters.json;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.google.gson.Gson;

/**
 * 
 * @author thospfuller
 *
 * @param <S>
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
