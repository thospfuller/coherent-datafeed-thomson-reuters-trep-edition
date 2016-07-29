package com.coherentlogic.coherent.datafeed.services;

import com.coherentlogic.coherent.datafeed.domain.DirectoryEntries;

/**
 * This interface is used in the Spring Integration work flow and defines the
 * methods that are available for users to invoke specific to the directory
 * service.
 *
 * @todo Combine this spec with the DictionaryGatewaySpecification.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface DirectoryGatewaySpecification {

    DirectoryEntries getDirectoryEntries ();
}
