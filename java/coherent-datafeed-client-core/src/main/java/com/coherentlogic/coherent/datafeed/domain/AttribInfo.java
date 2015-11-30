package com.coherentlogic.coherent.datafeed.domain;

import java.util.HashMap;
import java.util.Map;

import com.coherentlogic.coherent.data.model.core.domain.IdentitySpecification;
import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicitCollection;

/**
 * @TODO: Add firePropertyChangeListener logic.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AttribInfo extends SerializableBean
    implements MarketPriceConstants, IdentitySpecification<Integer> {

//    ServiceName: dELEKTRON_DD
//    ServiceId: 8008
//    Name: ANZ.AX
//    NameType: 1 (RIC)

    private Integer id = null;

    private Integer filter = null;

    @XStreamAlias(SVC_NAME)
    private String serviceName = null;

    @XStreamAlias(NAME)
    private String name = null;

    @XStreamAlias(SVC_ID)
    private Integer serviceId = null;

    @XStreamAlias(NAME_TYPE)
    private Short nameType = null;

    @XStreamAlias(ELEMENTS)
    private Map<String, String> elements;

    public AttribInfo() {
        this (new HashMap<String, String> ());
    }

    public AttribInfo(Map<String, String> elements) {
        this.elements = elements;
    }

    public void setElements(Map<String, String> elements) {
        this.elements = elements;
    }

    public AttribInfo withElements (Map<String, String> elements) {

        setElements (elements);

        return this;
    }

    public Map<String, String> getElements() {
        return elements;
    }

    public String getElement (String key) {
        return elements.get(key);
    }

    public void putElement (String key, String value) {
        elements.put (key, value);
    }

    public Integer getFilter() {
        return filter;
    }

    public void setFilter(Integer filter) {
        this.filter = filter;
    }

    public AttribInfo withFilter(Integer filter) {

        setFilter (filter);

        return this;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public AttribInfo withId(Integer id) {

        setId (id);

        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public AttribInfo withServiceName(String serviceName) {

        setServiceName(serviceName);

        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttribInfo withName(String name) {

        setName (name);

        return this;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public AttribInfo withServiceId(Integer serviceId) {

        setServiceId (serviceId);

        return this;
    }

    public Short getNameType() {
        return nameType;
    }

    public void setNameType(Short nameType) {
        this.nameType = nameType;
    }

    public AttribInfo withNameType(Short nameType) {

        setNameType(nameType);

        return this;
    }

    @Override
    public String toString() {
        return "AttribInfo [id=" + id + ", filter=" + filter + ", serviceName="
            + serviceName + ", name=" + name + ", serviceId=" + serviceId
            + ", nameType=" + nameType + ", elements=" + elements + "]";
    }
}
