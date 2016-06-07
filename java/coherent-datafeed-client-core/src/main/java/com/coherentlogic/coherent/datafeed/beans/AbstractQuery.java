package com.coherentlogic.coherent.datafeed.beans;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.coherentlogic.coherent.datafeed.domain.SessionBean;

/**
 * The abstract query contains the properties and associated methods that are
 * used by queries.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractQuery<T> implements Serializable {

    private static final long serialVersionUID = 3285092779893560459L;

    private final String serviceName;

    private final SessionBean sessionBean;

    private final T item;

    public AbstractQuery(String serviceName, SessionBean sessionBean, T item) {
        super();
        this.serviceName = serviceName;
        this.sessionBean = sessionBean;
        this.item = item;
    }

    public String getServiceName() {
        return serviceName;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public T getItem() {
        return item;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
