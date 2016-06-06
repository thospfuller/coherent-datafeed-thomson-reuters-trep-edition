package com.coherentlogic.coherent.datafeed.services;

import java.util.List;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.common.Handle;

/**
 * A specification that defines the methods available to classes that need to
 * query the TREP directories.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface DirectoryServiceSpecification {

    List<Handle> query (SessionBean sessionBean);
}
