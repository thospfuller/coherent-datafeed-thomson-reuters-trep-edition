package com.coherentlogic.coherent.datafeed.client;

import com.reuters.rfa.common.Handle;

/**
 * 
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface ClientBootstrapSpecification {

//    public void start();
//
//    public void stop();
//
//    boolean isStarted();
//
//    void setStarted(boolean started);

    Handle login (String dacsId);
}
