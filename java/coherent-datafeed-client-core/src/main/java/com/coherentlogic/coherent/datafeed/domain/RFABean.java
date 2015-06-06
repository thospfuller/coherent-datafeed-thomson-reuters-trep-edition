package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants.NAME;
import static com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants.NAME_TYPE;
import static com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants.SVC_ID;
import static com.coherentlogic.coherent.datafeed.domain.MarketPriceConstants.SVC_NAME;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.coherentlogic.coherent.data.model.core.domain.IdentityBean;
import com.coherentlogic.coherent.data.model.core.domain.IdentitySpecification;
import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;
import com.reuters.rfa.common.Handle;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * Note that this class does not extend from {@link IdentityBean} as the id that
 * the {@link IdentityBean} uses is a string whereas this class requires an
 * integer; also note that the id here is assigned from attribInfo -- it is not
 * a primary key.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @todo Extend from IdentityBean.
 */
public class RFABean extends SerializableBean
    implements IdentitySpecification<Integer> {

    private static final long serialVersionUID = 7776545263194460567L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(
        name="TIMESTAMP",
        columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
        insertable=false,
        updatable=false)
    private Date timestamp = null;

    private Integer id = null;

    /**
     * @deprecated Should be removed once this object extends from IdentityBean.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long uniqueId = null;

    private Integer filter = null;

    @XStreamAlias(SVC_NAME)
    private String serviceName = null;

    /**
     * The ommMsg.attribInfo.name -- see page 7 of the GenericOMMParser
     * (HAS_ATTRIB_INFO).
     */
    @XStreamAlias(NAME)
    private String name = null;

    @XStreamAlias(SVC_ID)
    private Integer serviceId = null;

    @XStreamAlias(NAME_TYPE)
    private Short nameType = null;

    private transient Handle handle = null;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @deprecated Should be removed once this object extends from IdentityBean.
     */
    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * @deprecated Should be removed once this object extends from IdentityBean.
     */
    public long getUniqueId() {
        return uniqueId;
    }

    public Integer getFilter() {
        return filter;
    }

    public void setFilter(Integer filter) {
        this.filter = filter;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Short getNameType() {
        return nameType;
    }

    public void setNameType(Short nameType) {
        this.nameType = nameType;
    }

    public Handle getHandle() {
        return handle;
    }

    public void setHandle(Handle handle) {
        this.handle = handle;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
