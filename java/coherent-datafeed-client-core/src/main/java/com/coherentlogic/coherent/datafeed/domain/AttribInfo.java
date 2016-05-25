package com.coherentlogic.coherent.datafeed.domain;

import java.util.HashMap;
import java.util.Map;

import com.coherentlogic.coherent.data.model.core.domain.IdentitySpecification;
import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;
import com.coherentlogic.coherent.datafeed.annotations.Changeable;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @TODO: Add firePropertyChangeListener logic.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AttribInfo extends SerializableBean implements IdentitySpecification<Integer>, Clearable {

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

    public void setElements(@Changeable(ELEMENTS) Map<String, String> elements) {
        this.elements = elements;
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

    public void setFilter(@Changeable(FILTER) Integer filter) {
        this.filter = filter;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public static final String ID = "id";

    @Override
    public void setId(@Changeable(ID) Integer id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public static final String SERVICE_NAME = "serviceName";

    public void setServiceName(@Changeable(SERVICE_NAME) String serviceName) {
        this.serviceName = serviceName;
    }

    public String getName() {
        return name;
    }

    public static final String NAME = "name";

    public void setName(@Changeable(NAME) String name) {
        this.name = name;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public static final String SERVICE_ID = "serviceId";

    public void setServiceId(@Changeable(SERVICE_ID) Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getNameType() {
        return nameType;
    }

    public static final String NAME_TYPE = "nameType";

    public void setNameType(@Changeable(NAME_TYPE) String nameType) {
        this.nameType = nameType;
    }

    @Override
	public void clear() {
        // id = null;
        filter = null;
        serviceName = null;
        name = null;
        serviceId = null;
        nameType = null;
        elements.clear();
	}

	@Override
    public String toString() {
        return "AttribInfo [id=" + id + ", filter=" + filter + ", serviceName=" + serviceName + ", name=" + name
                + ", serviceId=" + serviceId + ", nameType=" + nameType + ", elements=" + elements + "]";
    }
}
