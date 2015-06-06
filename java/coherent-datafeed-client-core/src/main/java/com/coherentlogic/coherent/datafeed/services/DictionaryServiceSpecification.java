package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import com.reuters.rfa.common.Handle;

/**
 * This interface represents the specification for implementations of the
 * FieldDictionaryHolder.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public interface DictionaryServiceSpecification {

    List<Handle> loadDictionaries (
        String serviceName,
        Handle loginHandle,
        String... dictionaryIds
    );
}
