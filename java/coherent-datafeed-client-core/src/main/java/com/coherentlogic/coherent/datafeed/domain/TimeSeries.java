package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.HEADERS;
import static com.coherentlogic.coherent.datafeed.misc.Constants.SAMPLES;
import static com.coherentlogic.coherent.datafeed.misc.Constants.TIME_SERIES;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.sound.sampled.Line;

import com.coherentlogic.coherent.datafeed.adapters.omm.OMMNumericAdapter;
import com.coherentlogic.coherent.datafeed.annotations.Adapt;
import com.coherentlogic.coherent.datafeed.annotations.RFAType;
import com.coherentlogic.coherent.datafeed.annotations.UsingKey;
import com.coherentlogic.coherent.datafeed.exceptions.MissingDataException;
import com.coherentlogic.coherent.datafeed.exceptions.TimeSeriesConversionFailedException;
import com.coherentlogic.coherent.datafeed.misc.Constants;

/**
 * A domain class representation of time series data.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Entity
@Table(name=TIME_SERIES)
//@XStreamAlias(TIME_SERIES)
//@XStreamConverter(TimeSeriesConverter.class)
public class TimeSeries extends StatusResponseBean implements RDMFieldDictionaryConstants {

    private static final long serialVersionUID = -3354510584636604673L;

    /**
     * SHARED
     */
    private BigInteger permission = null;

    /**
     * SHARED
     *
     * Display information for the IDN terminal device.
     *
     * RDNDISPLAY: UINT32 though treat this as a UINT as UINT32 has been
     *             deprecated.
     */
    private BigInteger displayTemplate = null;

    private List<String> headers = null;

    /**
     * @todo Samples are created by date so we might be able to change this data
     *  structure to a map.
     */
    private List<Sample> samples = null;

    public TimeSeries() {
        this (new ArrayList<String>(), new ArrayList<Sample>());
    }

    public TimeSeries (List<String> headers, List<Sample> samples) {
        this.headers = headers;
        this.samples = samples;
    }

    @UsingKey(type=PROD_PERM)
    public BigInteger getPermission() {
        return permission;
    }

    /**
     *
     */
    @RFAType(type=PROD_PERM)
    @Adapt(using=OMMNumericAdapter.class)
    public void setPermission(BigInteger permission) {
        this.permission = permission;
    }

    @UsingKey(type=RDNDISPLAY)
    public BigInteger getDisplayTemplate() {
        return displayTemplate;
    }

    /**
     * UINT32
     *
     * @param rdnDisplay
     */
    @RFAType(type=RDNDISPLAY)
    @Adapt(using=OMMNumericAdapter.class)
    public void setDisplayTemplate(BigInteger rdnDisplay) {
        this.displayTemplate = rdnDisplay;
    }

    /**
     * @todo The setter method has no annotation so this may be unnecessary.
     */
    @UsingKey(type=HEADERS)
    @ElementCollection
    public List<String> getHeaders() {
        return headers;
    }

    /**
     * @see Line 90 in ConsoleTSClient.java.
     *
     * @param headers
     */
    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public TimeSeries addHeader(String header) {

        headers.add(header);
        return this;
    }

    public TimeSeries addHeader(String... headers) {
        for(String next : headers) {
            this.headers.add(next);
        }
        return this;
    }

    /**
     * @todo The setter method has no annotation so this may be unnecessary.
     */
    @UsingKey(type=SAMPLES)
    @OneToMany(cascade=CascadeType.ALL)
    public List<Sample> getSamples() {
        return samples;
    }

    /**
     * Method returns all values for the column specified by the header name.
     *
     * @param header The column header name -- ie. DATE, HIGH, LOW.
     *
     * @return All values for the specified column.
     *
     * @TODO Unit test this method.
     */
    @Transient
    public List<String> getValuesForHeader (String header) {

        List<String> values = new ArrayList<String> ();

        if (Constants.BIG_DATE.equals(header)) {
            for (Sample sample : samples)
                values.add(sample.getDate().toString());
        } else {
            // Remove one as there is no date in this list (see sample.date) and so the headers will be pushed rightward
            // by one.
            int index = (headers.indexOf(header) - 1);

            for (Sample sample : samples) {

                List<String> points = sample.getPoints();

                if (!points.isEmpty()) {

                    String value = points.get(index);

                    if (value == null)
                        value = Constants.NA;

                    values.add(value);

                } else {
                    throw new TimeSeriesConversionFailedException("The sample points is empty.");
                }
            }
        }

        return values;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public TimeSeries addSample(Sample sample) {
        samples.add(sample);
        return this;
    }

    public TimeSeries addSample(Sample... samples) {
        for(Sample next : samples) {
            this.samples.add(next);
        }
        return this;
    }

    public Sample findOrCreateSample (long date) {

        Sample result = null;

        for (Sample next : samples)
            if (next.getDate() == date) {
                result = next;
                break;
            }

        if (result == null) {
            result = new Sample (date);
            addSample(result);
        }

        return result;
    }

    public void sortSamplesBy (Comparator<Sample> byComparator) {
        samples.sort(byComparator);
    }

    public void sortSamplesByDate () {

        Comparator<Sample> byDate = (Sample lhs, Sample rhs) -> {

            if (lhs == null || rhs == null)
                throw new MissingDataException ("Unable to compare the lhs vs. rhs because one of these references "
                    + "is null; lhs: " + lhs + ", rhs: " + rhs);

            return lhs.getDate().compareTo(rhs.getDate ());
        };

        samples.sort(byDate);
    }
}
