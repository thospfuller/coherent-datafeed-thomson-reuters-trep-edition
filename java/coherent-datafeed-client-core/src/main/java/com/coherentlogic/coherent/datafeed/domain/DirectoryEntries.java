package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DIRECTORY_ENTRIES;

import java.io.Serializable;
import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@XStreamAlias(DIRECTORY_ENTRIES)
public class DirectoryEntries implements Serializable {

    private static final long serialVersionUID = 2271484647334046161L;

    @XStreamAlias(DIRECTORY_ENTRIES)
    @XStreamImplicit
    private final Set<DirectoryEntry> directoryEntryList;

    public DirectoryEntries(
        Set<DirectoryEntry> directoryEntryList) {
        super();
        this.directoryEntryList = directoryEntryList;
    }

    public Set<DirectoryEntry> getDirectoryEntries () {
        return directoryEntryList;
    }
}
