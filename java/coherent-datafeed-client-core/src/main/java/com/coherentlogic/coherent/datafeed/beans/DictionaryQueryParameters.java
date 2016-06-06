package com.coherentlogic.coherent.datafeed.beans;

import java.io.Serializable;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;

/**
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DictionaryQueryParameters implements Serializable {

    private static final long serialVersionUID = -7766942585722094306L;

    private final SessionBean sessionBean;

    public DictionaryQueryParameters(SessionBean sessionBean) {
        super();
        this.sessionBean = sessionBean;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    @Override
    public String toString() {
        return "LoginMessageParameters [sessionBean=" + sessionBean + "]";
    }
}
