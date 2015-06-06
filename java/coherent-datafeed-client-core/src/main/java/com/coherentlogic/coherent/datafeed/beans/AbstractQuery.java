package com.coherentlogic.coherent.datafeed.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.reuters.rfa.common.Handle;

/**
 * The abstract query contains the properties and associated methods that are
 * used by queries.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractQuery<T> implements Serializable {

    private static final long serialVersionUID = 3285092779893560459L;

    private final String serviceName;

    private final Handle loginHandle;

    private final T item;

    public AbstractQuery(String serviceName, Handle loginHandle, T item) {
        super();
        this.serviceName = serviceName;
        this.loginHandle = loginHandle;
        this.item = item;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Handle getLoginHandle() {
        return loginHandle;
    }

    public T getItem() {
        return item;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
