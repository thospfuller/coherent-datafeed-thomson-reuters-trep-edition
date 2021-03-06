package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DIRECTORY_ENTRIES;

import java.util.Set;

import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@XStreamAlias(DIRECTORY_ENTRIES)
public class DirectoryEntries extends SerializableBean {

    private static final long serialVersionUID = 1657930759501402190L;

    public static final String DIRECTORIES = "directories";

    @XStreamAlias(DIRECTORY_ENTRIES)
    @XStreamImplicit
    private final Set<DirectoryEntry> directoryEntryList;

    public DirectoryEntries(Set<DirectoryEntry> directoryEntryList) {
        this.directoryEntryList = directoryEntryList;
    }

    public Set<DirectoryEntry> getDirectoryEntries () {
        return directoryEntryList;
    }
}
