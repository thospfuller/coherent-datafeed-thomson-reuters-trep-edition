package com.coherentlogic.coherent.datafeed.adapters.json;

import com.coherentlogic.coherent.datafeed.adapters.BasicAdapter;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 * An adapter which converts {@link MarketPrice} objects into JSON text.
 *
 * @param T An instance of a domain class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @deprecated Deprecated in favor of GJSONGenerator and associated classes.
 */
public abstract class AbstractJSONAdapter<S>
    implements BasicAdapter<S, String> {

    private final XStream xStream;

    /**
     * 
     * @param target Passed to XStream so that it can scan the annotations.
     */
    public AbstractJSONAdapter() {
        this (new JettisonMappedXmlDriver ());
    }

    /**
     * 
     * @param target Passed to XStream so that it can scan the annotations.
     */
    public AbstractJSONAdapter (
        HierarchicalStreamDriver hierarchicalStreamDriver
    ) {
        this (new XStream (hierarchicalStreamDriver));
    }

    public AbstractJSONAdapter (XStream xStream) {
        this.xStream = xStream;
    }

    protected XStream getXStream() {
        return xStream;
    }

    /**
     * Method converts the target into a string and returns this value.
     */
    @Override
    public String adapt(S source) {

        String result = (source == null) ? null : xStream.toXML(source);

        return result;
    }
}
