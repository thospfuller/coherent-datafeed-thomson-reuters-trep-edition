package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.common.Handle;

/**
 * This interface represents the specification for implementations of the
 * FieldDictionaryHolder.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface DictionaryServiceSpecification {

    List<Handle> loadDictionaries (
        String serviceName,
        SessionBean sessionBean,
        String... dictionaryIds
    );
}
