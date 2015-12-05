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

    /**
     * @TODO: Convert this to a string.
     *
     * RDMUser.NameType.toString(nameType)
     */
    @XStreamAlias(NAME_TYPE)
    private String nameType = null;

    @XStreamAlias(ELEMENTS)
    private Map<String, String> elements;

    public AttribInfo() {
        this (new HashMap<String, String> ());
    }

    public AttribInfo(Map<String, String> elements) {
        this.elements = elements;
    }

    public void setElements(Map<String, String> elements) {

        Map<String, String> oldValue = this.elements;

        if (RFABean.differs (oldValue, elements)) {

            this.elements = elements;

            firePropertyChange("elements", oldValue, elements);
        }
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

        Integer oldValue = this.filter;

        if (RFABean.differs(oldValue, filter)) {

            this.filter = filter;

            firePropertyChange("filter", oldValue, filter);
        }
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

        Integer oldValue = id;

        if (RFABean.differs(oldValue, id)) {

            this.id = id;

            firePropertyChange("id", oldValue, id);
        }
    }

    public AttribInfo withId(Integer id) {

        setId (id);

        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {

        String oldValue = this.serviceName;

        if (RFABean.differs (oldValue, serviceName)) {

            this.serviceName = serviceName;

            firePropertyChange("serviceName", oldValue, serviceName);
        }
    }

    public AttribInfo withServiceName(String serviceName) {

        setServiceName(serviceName);

        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        String oldValue = this.name;

        if (RFABean.differs (oldValue, name)) {

            this.name = name;

            firePropertyChange("name", oldValue, name);
        }
    }

    public AttribInfo withName(String name) {

        setName (name);

        return this;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {

        Integer oldValue = this.serviceId;

        if (RFABean.differs (oldValue, serviceId)) {

            this.serviceId = serviceId;

            firePropertyChange("serviceId", oldValue, serviceId);
        }
    }

    public AttribInfo withServiceId(Integer serviceId) {

        setServiceId (serviceId);

        return this;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {

        String oldValue = this.nameType;

        if (RFABean.differs (oldValue, nameType)) {

            this.nameType = nameType;

            firePropertyChange("nameType", oldValue, nameType);
        }
    }

    public AttribInfo withNameType(String nameType) {

        setNameType(nameType);

        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
            + ((elements == null) ? 0 : elements.hashCode());
        result = prime * result + ((filter == null) ? 0 : filter.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result
            + ((nameType == null) ? 0 : nameType.hashCode());
        result = prime * result
            + ((serviceId == null) ? 0 : serviceId.hashCode());
        result = prime * result
            + ((serviceName == null) ? 0 : serviceName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AttribInfo other = (AttribInfo) obj;
        if (elements == null) {
            if (other.elements != null)
                return false;
        } else if (!elements.equals(other.elements))
            return false;
        if (filter == null) {
            if (other.filter != null)
                return false;
        } else if (!filter.equals(other.filter))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (nameType == null) {
            if (other.nameType != null)
                return false;
        } else if (!nameType.equals(other.nameType))
            return false;
        if (serviceId == null) {
            if (other.serviceId != null)
                return false;
        } else if (!serviceId.equals(other.serviceId))
            return false;
        if (serviceName == null) {
            if (other.serviceName != null)
                return false;
        } else if (!serviceName.equals(other.serviceName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AttribInfo [id=" + id + ", filter=" + filter + ", serviceName="
            + serviceName + ", name=" + name + ", serviceId=" + serviceId
            + ", nameType=" + nameType + ", elements=" + elements + "]";
    }
}
