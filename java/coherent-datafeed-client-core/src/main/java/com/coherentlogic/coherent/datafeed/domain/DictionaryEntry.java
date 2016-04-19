package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DICTIONARY_ENTRY;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Class represents an entry in the session that is associated with a handle to
 * a dictionary.
 *
 * @todo Should this be named simply DictionaryEntry?
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@XStreamAlias(DICTIONARY_ENTRY)
public class DictionaryEntry implements Serializable {

    private static final long serialVersionUID = 6257192235261348047L;

    private final String name;

    @XStreamOmitField
    private final String directoryName;

    @XStreamOmitField
    private boolean loaded = false;

    public DictionaryEntry(String directoryName, String name) {
        super();
        this.directoryName = directoryName;
        this.name = name;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public String getName() {
        return name;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
