package com.coherentlogic.coherent.datafeed.domain;

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

/**
 *
 * Note that this class does not extend from {@link IdentityBean} as the id that
 * the {@link IdentityBean} uses is a string whereas this class requires an
 * integer; also note that the id here is assigned from attribInfo -- it is not
 * a primary key.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @deprecated Rename this to OMMBean.
 *
 * @TODO: Consider adding StatusResponseEventListener capabilities.
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

    private AttribInfo attribInfo;

    private transient Handle handle = null;

    /**
     * Returns true if the oldValue and newValue differ with respect to what is
     * required for a bean's property change event to be fired.
     *
     * @TODO: Unit test this method.
     */
    protected static boolean differs (Object oldValue, Object newValue) {

        boolean result = false;

        if (oldValue == null
            || (oldValue != null && !oldValue.equals(newValue))
        ) {
            result = true;
        }

        return result;
    }

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

    public AttribInfo getAttribInfo() {
        return attribInfo;
    }

    public void setAttribInfo(AttribInfo attribInfo) {
        this.attribInfo = attribInfo;
    }

    public RFABean withAttribInfo (AttribInfo attribInfo) {

        setAttribInfo(attribInfo);

        return this;
    }

    public Handle getHandle() {
        return handle;
    }

    public void setHandle(Handle handle) {
        this.handle = handle;
    }

    public RFABean withHandle (Handle handle) {

        setHandle (handle);

        return this;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public RFABean withTimestamp (Date timestamp) {

        setTimestamp(timestamp);

        return this;
    }
}
