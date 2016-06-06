package com.coherentlogic.coherent.datafeed.services;

import java.util.Map;

import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;

/**
 * This interface is used in the Spring Integration work flow and defines the
 * methods that are available for users to invoke specific to the directory
 * service.
 *
 * @todo Combine this spec with the DictionaryGatewaySpecification.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface DirectoryServiceGatewaySpecification {

    Map<String, DirectoryEntry> query (SessionBean sessionBean);
}
