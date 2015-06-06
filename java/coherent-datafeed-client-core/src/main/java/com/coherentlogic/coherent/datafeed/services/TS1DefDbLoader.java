package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.EMPTY_STRING;
import static com.coherentlogic.coherent.datafeed.misc.Constants.HEADERS_SIZE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.ROW64_3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.exceptions.ClientNotInitializedException;
import com.reuters.rfa.dictionary.FidDef;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.session.omm.OMMItemEvent;
import com.reuters.ts1.TS1DefDb;

/**
 * This class is used to load the {@link TS1DefDb} database from the
 * {@link OMMItemEvent} provided.
 *
 * NOTE: This class loads the header information, the actual time series data
 *       is loaded by the {@link TimeSeriesLoader} class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class TS1DefDbLoader extends BasicLoader<OMMItemEvent> {

    private static final Logger log =
        LoggerFactory.getLogger(TS1DefDbLoader.class);

    private final TS1DefDb ts1DefDb;

    public TS1DefDbLoader(
        FieldDictionary fieldDictionary,
        TS1DefDb ts1DefDb
    ) {
        super(fieldDictionary);
        this.ts1DefDb = ts1DefDb;
    }

    @Override
    public void load (OMMItemEvent itemEvent) {

        log.info("load: method begins; itemEvent: " + itemEvent);

        FieldDictionary fieldDictionary = getFieldDictionary();

        FidDef fidDef = fieldDictionary.getFidDef(ROW64_3);

        // If this exception is thrown then chances are the Spring configuration
        // files are set up such that the fieldDictionary is loaded from the
        // file but not completely configured completely.
        if (fidDef == null)
            throw new ClientNotInitializedException("The fieldDictionary " +
                "does not appear to have been loaded properly.");

        short fieldId = fidDef.getFieldId();

        for (short ctr = 0; ctr < HEADERS_SIZE; ctr++) {

            short nextRow = (short)(fieldId + ctr);

            byte[] defDbBytes = getDefDbBytes(itemEvent, nextRow);

            String defDbText = new String (defDbBytes).trim ();

            /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             * NOTE: This class loads the header information, the actual time
             * series data is loaded by the {@link TimeSeriesLoader} class.
             * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             */

            if (defDbBytes != null
                &&
                0 < defDbBytes.length
                && (!defDbText.equals(EMPTY_STRING))
            ) {
                // TS1DefDb - Invalid TS1 data will not be added: 37 22 2f 7b...
                // happens here.
                boolean parsed = ts1DefDb.add(defDbBytes);

                int size = ts1DefDb.size();

                if (!parsed)
                    log.warn("Adding the following bytes to the defDb " +
                        "failed: " + new String(defDbBytes));
                else log.info("The defDb was able to parse the bytes " +
                    "provided -- the defDb.size is now: " + size +
                    ", defDbBytes: " + new String(defDbBytes));

            } else {
                log.debug(
                    "The string '" + defDbText +
                    "' was not added to the defDb.");
            }
        }
    }
}
