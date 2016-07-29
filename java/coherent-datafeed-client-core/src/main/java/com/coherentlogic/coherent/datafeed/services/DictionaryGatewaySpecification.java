package com.coherentlogic.coherent.datafeed.services;

import com.coherentlogic.coherent.datafeed.domain.DictionaryEntries;

/**
 * This interface is used in the Spring Integration work flow and defines the
 * methods that are available for users to invoke specific to the Dictionary
 * service.
 *
 * @todo Combine this spec with the DirectoryGatewaySpecification.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface DictionaryGatewaySpecification {

    DictionaryEntries getDictionaryEntries ();

//    String getDictionaryEntriesAsJSON ();
}
