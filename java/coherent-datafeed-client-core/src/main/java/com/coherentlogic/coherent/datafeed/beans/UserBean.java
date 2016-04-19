package com.coherentlogic.coherent.datafeed.beans;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.coherentlogic.coherent.datafeed.domain.RFABean;

/**
 * A bean that contains the DACS id. We need to keep track of the DACS id in the
 * event that the system needs to perform a login again in the background --
 * this can happen when, for example, TR sends a COMPLETION_EVENT.
 *
 * The reasoning behind this class is suspect and so it may be removed.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class UserBean extends RFABean {

    private static final long serialVersionUID = -8695566861166579215L;

    public static final String BEAN_NAME = "userBean";

    private String dacsId;

    /**
     * @todo Remove.
     */
    public UserBean () {
        super ();
    }

    public void setDacsId(String dacsId) {
        this.dacsId = dacsId;
    }

    public String getDacsId() {
        return dacsId;
    }

    @Override
    public boolean equals(Object target) {
        return EqualsBuilder.reflectionEquals(this, target);
    }
}
