package com.coherentlogic.coherent.datafeed.listeners;

import java.util.EventListener;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;

/**
 * An interface that is used to listen for events that are sent from the
 * framework.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public interface FrameworkEventListener extends EventListener {

    void onEventReceived (SessionBean sessionBean);
}
