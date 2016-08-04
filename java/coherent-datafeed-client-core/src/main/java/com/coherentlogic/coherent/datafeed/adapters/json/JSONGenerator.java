package com.coherentlogic.coherent.datafeed.adapters.json;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.coherentlogic.coherent.datafeed.domain.RFABean;

/**
 * This class converts a domain object S into a String. This is accomplished by using a NestedAdapter which takes the
 * domain object and converts it into a Map<String, String> and then this map is converted into JSON.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <S> The type of {@link RFABean}.
 *
 * @deprecated Deprecated in favor of GJSONGenerator and associated classes.
 */
public class JSONGenerator<S extends RFABean>
    implements BasicAdapter<S, String> {

    private final NestedAdapter<S, Map<String, String>, String> nestedAdapter;

    public JSONGenerator (
        Class<S> targetClass,
        MapToJSONAdapter mapToJSONAdapter
    ) {
        this (
            new RFABeanToMapAdapter<S> (targetClass),
            mapToJSONAdapter
        );
    }

    public JSONGenerator (
        RFABeanToMapAdapter<S> mapAdapter,
        MapToJSONAdapter mapToJSONAdapter
    ) {
        this (
            new NestedAdapter<S, Map<String, String>, String>(
                mapAdapter,
                mapToJSONAdapter
            )
        );
    }

    public JSONGenerator(
        NestedAdapter<S, Map<String, String>, String> nestedAdapter
    ) {
        super();
        this.nestedAdapter = nestedAdapter;
    }

    @Override
    public String adapt(S source) {
        return nestedAdapter.adapt(source);
    }
}
