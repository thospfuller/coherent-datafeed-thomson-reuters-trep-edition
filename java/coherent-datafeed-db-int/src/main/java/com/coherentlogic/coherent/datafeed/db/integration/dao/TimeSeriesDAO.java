package com.coherentlogic.coherent.datafeed.db.integration.dao;

import static com.coherentlogic.coherent.datafeed.misc.Constants.TIME_SERIES_DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.data.model.core.db.integration.dao.SerializableDAO;
import com.coherentlogic.coherent.datafeed.domain.TimeSeries;

/**
 * Data access implementation for {@link TimeSeries} objects.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Repository(TIME_SERIES_DAO)
@Transactional
public class TimeSeriesDAO extends SerializableDAO<TimeSeries> {

    public TimeSeriesDAO () {
    }

    @Override
    public TimeSeries find (long primaryKey) {
        return find(TimeSeries.class, primaryKey);
    }
}
