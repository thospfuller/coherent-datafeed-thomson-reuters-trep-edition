package com.coherentlogic.coherent.datafeed.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Component(DirectoryEntryFactory.BEAN_NAME)
public class DirectoryEntryFactory implements TypedFactory<DirectoryEntry> {

    public static final String BEAN_NAME = "directoryEntryFactory";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public DirectoryEntry getInstance() {
        return applicationContext.getBean(DirectoryEntry.class);
    }
}
