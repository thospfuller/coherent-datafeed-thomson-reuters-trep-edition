package com.coherentlogic.coherent.datafeed.domain;

import java.util.HashMap;
import java.util.Map;

import com.coherentlogic.coherent.data.model.core.domain.IdentitySpecification;
import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @TODO: Add firePropertyChangeListener logic.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AttribInfo extends SerializableBean implements IdentitySpecification<Integer> {

//    ServiceName: dELEKTRON_DD
//    ServiceId: 8008
//    Name: ANZ.AX
//    NameType: 1 (RIC)

    private Integer id = null;

    private Integer filter = null;

    @XStreamAlias(MarketPriceConstants.SVC_NAME)
    private String serviceName = null;

    @XStreamAlias(MarketPriceConstants.NAME)
    private String name = null;

    @XStreamAlias(MarketPriceConstants.SVC_ID)
    private Integer serviceId = null;

    /**
     * @TODO: Convert this to a string.
     *
     * RDMUser.NameType.toString(nameType)
     */
    @XStreamAlias(MarketPriceConstants.NAME_TYPE)
    private String nameType = null;

    @XStreamAlias(MarketPriceConstants.ELEMENTS)
    private Map<String, String> elements;

    public AttribInfo() {
        this (new HashMap<String, String> ());
    }

    public AttribInfo(Map<String, String> elements) {
        this.elements = elements;
    }

    public static final String ELEMENTS = "elements";

    public void setElements(Map<String, String> elements) {

        Map<String, String> oldValue = this.elements;

        this.elements = elements;

        firePropertyChange(ELEMENTS, oldValue, elements);

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

    public static final String FILTER = "filter";

    public void setFilter(Integer filter) {

        Integer oldValue = this.filter;

        this.filter = filter;

        firePropertyChange(FILTER, oldValue, filter);
    }

    public AttribInfo withFilter(Integer filter) {

        setFilter (filter);

        return this;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public static final String ID = "id";

    @Override
    public void setId(Integer id) {

        Integer oldValue = id;

        this.id = id;

        firePropertyChange(ID, oldValue, id);
    }

    public AttribInfo withId(Integer id) {

        setId (id);

        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public static final String SERVICE_NAME = "serviceName";

    public void setServiceName(String serviceName) {

        String oldValue = this.serviceName;

        this.serviceName = serviceName;

        firePropertyChange(SERVICE_NAME, oldValue, serviceName);
    }

    public AttribInfo withServiceName(String serviceName) {

        setServiceName(serviceName);

        return this;
    }

    public String getName() {
        return name;
    }

    public static final String NAME = "name";

    public void setName(String name) {

        String oldValue = this.name;

        this.name = name;

        firePropertyChange(NAME, oldValue, name);
    }

    public AttribInfo withName(String name) {

        setName (name);

        return this;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public static final String SERVICE_ID = "serviceId";

    public void setServiceId(Integer serviceId) {

        Integer oldValue = this.serviceId;

        this.serviceId = serviceId;

        firePropertyChange(SERVICE_ID, oldValue, serviceId);
    }

    public AttribInfo withServiceId(Integer serviceId) {

        setServiceId (serviceId);

        return this;
    }

    public String getNameType() {
        return nameType;
    }

    public static final String NAME_TYPE = "nameType";

    public void setNameType(String nameType) {

        String oldValue = this.nameType;

        this.nameType = nameType;

        firePropertyChange(NAME_TYPE, oldValue, nameType);
    }

    public AttribInfo withNameType(String nameType) {

        setNameType(nameType);

        return this;
    }
}
