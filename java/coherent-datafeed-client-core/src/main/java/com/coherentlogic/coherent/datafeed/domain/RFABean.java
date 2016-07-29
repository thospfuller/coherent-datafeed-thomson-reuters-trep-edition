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
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @deprecated Rename this to OMMBean.
 *
 * @TODO: Refactor this class and make each property Changeable.
 *
 * @TODO: Consider adding StatusResponseEventListener capabilities.
 */
public class RFABean extends SerializableBean
    implements IdentitySpecification<Integer>, Clearable {

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
     * @TODO: Move this method to the parent class.
     */
    protected static boolean differs (Object oldValue, Object newValue) {

        boolean result = false;

        if (
            (oldValue == null && newValue !=null)
            ||
            (oldValue != null && !oldValue.equals(newValue))
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

        Integer oldValue = this.id;

        if (differs (oldValue, id)) {

            this.id = id;

            firePropertyChange("id", oldValue, id);
        }
    }

    /**
     * @deprecated Should be removed once this object extends from IdentityBean.
     */
    public void setUniqueId(Long uniqueId) {

        Long oldValue = this.uniqueId;

        if (differs(oldValue, uniqueId)) {

            this.uniqueId = uniqueId;

            firePropertyChange("uniqueId", oldValue, uniqueId);
        }
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

        AttribInfo oldValue = this.attribInfo;

        if (RFABean.differs (oldValue, attribInfo)) {

            this.attribInfo = attribInfo;

            firePropertyChange("attribInfo", oldValue, attribInfo);
        }
    }

    public Handle getHandle() {
        return handle;
    }

    public void setHandle(Handle handle) {

        Handle oldValue = this.handle;

        if (RFABean.differs (oldValue, handle)) {

            this.handle = handle;

            firePropertyChange("handle", oldValue, handle);
        }
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {

        Date oldValue = this.timestamp;

        if (RFABean.differs (oldValue, timestamp)) {

            this.timestamp = timestamp;

            firePropertyChange("timestamp", oldValue, timestamp);
        }
    }

    @Override
	public void clear() {
//        timestamp = null;
//        id = null;
//        uniqueId = null;
        attribInfo.clear ();
        //handle = null;
	}

	@Override
    public String toString() {
        return "RFABean [timestamp=" + timestamp + ", id=" + id + ", uniqueId=" + uniqueId + ", attribInfo="
            + attribInfo + ", handle=" + handle + "]";
    }
}
