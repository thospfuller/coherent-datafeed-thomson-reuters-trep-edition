package com.coherentlogic.coherent.datafeed.adapters.json;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.coherentlogic.coherent.datafeed.exceptions.InstantiationException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 * An adapter which converts objects of type T directly into JSON text.
 *
 * @param T An instance of a domain class.
 *
 * @todo We need to sort out what's up with the ctor.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @deprecated Deprecated in favor of GJSONGenerator and associated classes.
 */
public class RFABeanToJSONAdapter<T extends Serializable>
    extends AbstractJSONAdapter<T> {

    private final XStream xStream;

    /**
     * 
     * @param target Passed to XStream so that it can scan the annotations.
     */
    public RFABeanToJSONAdapter(Class<T>... targets) {
        this (new JettisonMappedXmlDriver (), targets);
    }

    /**
     * 
     * @param target Passed to XStream so that it can scan the annotations.
     */
    public RFABeanToJSONAdapter(
        HierarchicalStreamDriver hierarchicalStreamDriver,
        Class<T>... targets
    ) {
        this (new XStream (hierarchicalStreamDriver), targets);
    }

    public RFABeanToJSONAdapter(XStream xStream, Class<T>... targets) {

        this.xStream = xStream;

        if (targets != null && 0 < targets.length)
            xStream.processAnnotations(targets);
        else
            throw new InstantiationException("The targets array was null " +
                "or empty (targets: " +
                ToStringBuilder.reflectionToString(targets) + ").");
    }

    public XStream getXStream() {
        return xStream;
    }

    /**
     * Method converts the target into a string and returns this value.
     */
    @Override
    public String adapt(T target) {

        String result = (target == null) ? "null" : xStream.toXML(target);

        return result;
    }
}
