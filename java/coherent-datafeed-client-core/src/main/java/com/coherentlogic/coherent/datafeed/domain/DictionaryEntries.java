package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DICTIONARY_ENTRIES;

import java.io.Serializable;
import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@XStreamAlias(DICTIONARY_ENTRIES)
public class DictionaryEntries implements Serializable {

    private static final long serialVersionUID = -7009670010791348760L;

    @XStreamAlias(DICTIONARY_ENTRIES)
    @XStreamImplicit
    private final Set<DictionaryEntry> dictionaryEntryList;

    public DictionaryEntries(
        Set<DictionaryEntry> dictionaryEntryList) {
        super();
        this.dictionaryEntryList = dictionaryEntryList;
    }

    public Set<DictionaryEntry> getDictionaryServiceEntries () {
        return dictionaryEntryList;
    }
}
