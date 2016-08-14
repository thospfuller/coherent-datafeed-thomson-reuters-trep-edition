package com.coherentlogic.coherent.datafeed.db.integration.dao;

import static com.coherentlogic.coherent.datafeed.misc.Constants.MARKET_PRICE_DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.coherentlogic.coherent.data.model.core.db.integration.dao.SerializableDAO;
import com.coherentlogic.coherent.datafeed.domain.MarketPrice;

/**
 * Data access implementation for {@link MarketPrice} objects.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@Repository(MARKET_PRICE_DAO)
@Transactional
public class MarketPriceDAO extends SerializableDAO<MarketPrice> {

    public MarketPriceDAO () {
    }

    @Override
    public MarketPrice find (long primaryKey) {
        return find(MarketPrice.class, primaryKey);
    }
}
