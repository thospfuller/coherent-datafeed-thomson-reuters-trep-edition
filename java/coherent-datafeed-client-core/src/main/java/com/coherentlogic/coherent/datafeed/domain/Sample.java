package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DATE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.POINT;
import static com.coherentlogic.coherent.datafeed.misc.Constants.POINTS;
import static com.coherentlogic.coherent.datafeed.misc.Constants.SAMPLE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.coherentlogic.coherent.data.model.core.domain.IdentityBean;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.coherentlogic.coherent.datafeed.exceptions.NullPointerRuntimeException;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * An implementation of a container to hold sample data, which consists of a
 * date and some data points.
 * 
 * @see ConsoleTSClient.java # 104
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=SAMPLE)
@XStreamAlias(SAMPLE)
public class Sample extends IdentityBean {

    private static final long serialVersionUID = -7056240901518741993L;

    /**
     * 2012 Dec 17
     */
    @XStreamAlias(DATE)
    private Long date = null;

    /**
     * Values for:
     *
     * OPEN, CLOSE, HIGH, LOW, VOLUME, VWAP
     */
    @XStreamImplicit(itemFieldName=POINT)
    private List<String> points = null;

    public Sample() {
        this (null, new ArrayList<String> ());
    }

    public Sample(Long date) {
        this (date, new ArrayList<String> ());
    }

    public Sample(Long date, List<String> points) {
        super();
        this.date = date;
        this.points = points;
    }

    @UsingKey(type=DATE)
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setDate(Date date) {

        if (date == null)
            throw new NullPointerRuntimeException(
                "The setDate method was called with a null date.");

        this.date = date.getTime();
    }

    @UsingKey(type=POINTS)
    @ElementCollection
    public List<String> getPoints() {
        return points;
    }

    public void setPoints(List<String> points) {
        this.points = points;
    }

    public Sample addPoint(String point) {
        points.add(point);
        return this;
    }

    public Sample addPoint(String... points) {
        for(String nextPoint : points) {
            this.points.add(nextPoint);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Sample [date=" + date + ", points=" + points + ", toString()="
            + super.toString() + "]";
    }
}
