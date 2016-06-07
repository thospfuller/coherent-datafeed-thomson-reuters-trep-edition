package com.coherentlogic.coherent.datafeed.beans;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.common.Handle;

/**
 * This bean is used specifically in the Spring Integration workflow so that
 * parameters can be passed from a gateway to a message processor.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TS1DefQueryParameters implements Serializable {

    private static final long serialVersionUID = 439055904590413504L;

    private final SessionBean sessionBean;

    private final String[] rics;

    public TS1DefQueryParameters(SessionBean sessionBean) {
        this (sessionBean, (String[]) null);
    }

    public TS1DefQueryParameters(SessionBean sessionBean, String... rics) {
        this.sessionBean = sessionBean;
        this.rics = rics;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public String[] getRics() {
        return rics;
    }

    @Override
    public String toString() {
        return "TS1DefQueryParameters [sessionBean=" + sessionBean + ", rics=" + Arrays.toString(rics) + "]";
    }
}
