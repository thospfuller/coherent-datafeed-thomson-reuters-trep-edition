package com.coherentlogic.coherent.datafeed.beans;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.reuters.rfa.common.Handle;

/**
 * This bean is used specifically in the Spring Integration workflow so that
 * parameters can be passed from a gateway to a message processor.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefQueryParameters implements Serializable {

    private static final long serialVersionUID = 439055904590413504L;

    private final Handle loginHandle;

    private final String[] rics;

    public TS1DefQueryParameters(Handle loginHandle) {
        this (loginHandle, (String[]) null);
    }

    public TS1DefQueryParameters(Handle loginHandle, String... rics) {
        super();
        this.loginHandle = loginHandle;
        this.rics = rics;
    }

    public Handle getLoginHandle() {
        return loginHandle;
    }

    public String[] getRics() {
        return rics;
    }

    public String toString () {
        return ToStringBuilder.reflectionToString(this);
    }
}
