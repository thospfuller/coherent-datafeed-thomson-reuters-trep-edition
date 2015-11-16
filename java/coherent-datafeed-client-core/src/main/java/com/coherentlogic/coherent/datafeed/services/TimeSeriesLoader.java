package com.coherentlogic.coherent.datafeed.services;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_BUFFER_SIZE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.MAX_ROW_VALUE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.ROW64_1;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntries;
import com.coherentlogic.coherent.datafeed.beans.TimeSeriesEntry;
import com.coherentlogic.coherent.datafeed.exceptions.ConversionFailedException;
import com.coherentlogic.coherent.datafeed.exceptions.MissingDataException;
import com.reuters.rfa.common.Handle;
import com.reuters.rfa.dictionary.FidDef;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMAttribInfo;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMFieldEntry;
import com.reuters.rfa.omm.OMMFieldList;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMState;
import com.reuters.rfa.omm.OMMTypes;
import com.reuters.rfa.session.omm.OMMItemEvent;
import com.reuters.ts1.TS1DefDb;
import com.reuters.ts1.TS1Exception;
import com.reuters.ts1.TS1Series;

/**
 * Takes an Event and loads it into an instance of {@link TimeSeriesEntry}.
 * 
 * Note that we only want to do this for REFRESH_RESP events, however we use
 * routers prior to this to ensure that this is only called when the event has
 * the appropriate type.
 * 
 * Once the data has been loaded and a COMPLETION_EVENT has been received from
 * TR then the TimeSeries will be created and added to the queue.
 *
 * @deprecated Combine the TimeSeriesLoader with the TS1DefDbLoader OR remove
 *  header load-related logic from this class as I do not believe it belongs
 *  here since when we load the time series data, no headers exist (though
 *  double-check this before just removing the logic).
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesLoader {

    private static final Logger log =
        LoggerFactory.getLogger(TimeSeriesLoader.class);

    private final FieldDictionary fieldDictionary;

    private final TS1DefDb ts1DefDb;

    public TimeSeriesLoader(
        FieldDictionary fieldDictionary,
        TS1DefDb ts1DefDb
    ) {
        this.fieldDictionary = fieldDictionary;
        this.ts1DefDb = ts1DefDb;
    }

    /**
     * 
     * @param itemEvent
     *
     * @see TS1TimeSeries.processItemEvent
     */
    public void load (
        OMMItemEvent itemEvent,
        TimeSeriesEntries timeSeriesEntries
    ) {
        assertNotNull ("itemEvent", itemEvent);
        assertNotNull ("timeSeriesEntries", timeSeriesEntries);

        Handle handle = itemEvent.getHandle();

        log.info("handle: " + handle + ", ts1DefDb: " + ts1DefDb +
            ", ts1DefDb.size: " + ts1DefDb.size());

        TimeSeriesEntry timeSeriesEntry =
            timeSeriesEntries.getTimeSeriesEntry(handle);

        load (itemEvent, ts1DefDb, timeSeriesEntry);

        TS1Series ts1Series = timeSeriesEntry.getTS1Series();

        String[] rics = ts1Series.getRics();

        // We do NOT want to request the first RIC again because this is the
        // original value (ie. TRI.N); the other rics, however, we do need.
        rics = (String[]) ArrayUtils.remove(rics, 0);

        if (!timeSeriesEntries.ricsHaveBeenAdded())
            timeSeriesEntries.addRics(rics);
    }

    /**
     * 
     * @param itemEvent
     *
     * @see TS1TimeSeries.processItemEvent
     */
    void load (
        OMMItemEvent itemEvent,
        TS1DefDb defDb,
        TimeSeriesEntry timeSeriesEntry
    ) {
        String name = getName(itemEvent);

        TS1Series series = timeSeriesEntry.getTS1Series();

        byte[] rowBytes = getBytes(itemEvent);

        log.info("Decode begins for name " + name + " and rowBytes.length: " +
            rowBytes.length + ", and rowBytes: " +
            ToStringBuilder.reflectionToString(rowBytes));

        try {
            // The TR example does have different names each time this is called
            // (ie. TRI.Nd1, TRI.Nd2, ...
            log.info("decode: begins; name: " + name + ", rowBytes: " +
            ToStringBuilder.reflectionToString(rowBytes));

            series.decode(name, rowBytes);
        } catch (TS1Exception ts1Exception) {
            throw new ConversionFailedException("Unable to decode the " +
                "rowBytes for the name " + name +
                "; details follow.", ts1Exception);
        }

        String[] rics = series.getRics();

        log.info("rics: " + ToStringBuilder.reflectionToString(rics));
// THIS LINE BELOW SHOULD NOT BE EXECUTED AS IT'S FOR LOADIG HEADERS AND THIS
// CLASS IS USED TO LOAD SAMPLES -- KEEPING THIS LINE WILL RESULT IN rowBytes
// THAT CANNOT BE PARSED. SEE ALSO TS1DefDbLoader.
//        loadHeadersIntoDefDb (itemEvent, defDb);
    }

    /**
     * This loads all 14 of the ROW64_N byte array values into a single array of
     * bytes of size 64 * 14.
     *
     * In this case this iterates over the ids  
     *
     * @param itemEvent
     * @return
     * 
     * @see OMMNormalizedEvent
     * @see TS1TimeSeries#getBytes & processTimeSeries
     * @see RefChain.addRefs
     * 
     * @todo This method is likely the culprit and review RefChain.addRefs for
     *  comparison purposes.
     */
    byte[] getBytes(OMMItemEvent itemEvent) {

        byte[] rowBytes = new byte[DEFAULT_BUFFER_SIZE * MAX_ROW_VALUE];

        // Removed since this class currently does not extend from BasicLoader.
        // FieldDictionary fieldDictionary = getFieldDictionary();

        FidDef fidDef = fieldDictionary.getFidDef(ROW64_1);

        if (fidDef == null)
            throw new MissingDataException("The ROW64_1 fidDef is missing, " +
                "which probably indicates the dictionary has not been " +
                "loaded (fieldDictionary size: " + fieldDictionary.size() +
                ").");

        final short fieldId = fidDef.getFieldId();

        int offset = 0;

        for (short ctr = 0; ctr < MAX_ROW_VALUE; ctr++) {

            short next = (short) (fieldId + ctr);

            OMMMsg msg = itemEvent.getMsg();

            OMMData data = getFieldData (msg, next);

            if (data != null) {
                data.getBytes(rowBytes, offset); // equivalent of the call to
                    // OMMNormalizedEvent.getFieldBytes.
            }
            offset += DEFAULT_BUFFER_SIZE;
        }
        return rowBytes;
    }

    /**
     * Getter method that extracts the name from the {@link OMMItemEvent}
     * parameter.
     */
    String getName(OMMItemEvent itemEvent) {

        OMMMsg msg = itemEvent.getMsg();

        return getName (msg);
    }

    /**
     * Getter method that extracts the name from the {@link OMMItemEvent}
     * parameter.
     */
    String getName(OMMMsg msg) {

        String result = null;

        if (msg.has(OMMMsg.HAS_ATTRIB_INFO)){
            OMMAttribInfo attribInfo = msg.getAttribInfo();

            result = getName (attribInfo);
        }
        return result;
    }

    /**
     * Getter method that extracts the name from the {@link OMMAttribInfo}
     * parameter.
     */
    String getName(OMMAttribInfo attribInfo) {
        String result = null;

        if (attribInfo.has(OMMAttribInfo.HAS_NAME))
            result = attribInfo.getName();

        return result;
    }

    /**
     * Getter method that extracts the name from the {@link OMMMsg}
     * parameter.
     */
    String getStatus (OMMMsg msg) {

        String result = null;

        if (msg.has(OMMMsg.HAS_STATE)) {
            OMMState state = msg.getState();
            result = state.getText();
        }
        return result;
    }

    /**
     * Method retrieves the bytes for the {@link TS1DefDb} using the itemEvent
     * and row provided.
     *
     * @param itemEvent The {@link OMMItemEvent} to extract the bytes from.
     *
     * @param row The row to obtain the bytes from (ie. row64_3).
     *
     * @return An array of bytes of size {@link DEFAULT_SIZE}.
     */
    protected byte[] getDefDbBytes(OMMItemEvent itemEvent, short row) {
        OMMMsg msg = itemEvent.getMsg();

        return getDefDbBytes (msg, row);
    }

    /**
     * Method retrieves the bytes for the {@link TS1DefDb} using the msg and row
     * provided.
     *
     * @param msg The message to extract the bytes from.
     *
     * @param row The row to obtain the bytes from (ie. row64_3).
     *
     * @return An array of bytes of size {@link DEFAULT_SIZE}.
     */
    protected byte[] getDefDbBytes(OMMMsg msg, short row) {

        byte[] rowBytes = new byte[DEFAULT_BUFFER_SIZE];

        getFieldBytes(msg, row, rowBytes, 0);

        return rowBytes;
    }

    /**
     * Method copies the field data into the destination array parameter.
     */
    protected int getFieldBytes(
        OMMMsg msg,
        short fieldId,
        byte[] destination,
        int offset
    ) {
        OMMData data = getFieldData(msg, fieldId);

        int result = 0;

        if (data != null)
            result = data.getBytes(destination, offset);

        return result;
    }

    /**
     * Method finds the instance of {@link OMMData} using the specified msg and
     * fieldId.
     */
    protected OMMData getFieldData(OMMMsg msg, short fieldId) {

        log.debug("getFieldData: method begins; msg: " + msg);

        short dataType = msg.getDataType();

        OMMData result = null;

        if (dataType == OMMTypes.FIELD_LIST) {

            OMMFieldList fieldList = (OMMFieldList) msg.getPayload();

            OMMFieldEntry fieldEntry = fieldList.find(fieldId);

            result = getFieldData (fieldEntry, fieldId);
        } else {
            log.warn("OMMMsg payload must be field list -- instead it is of " +
                "type " + OMMTypes.toString(dataType) + ", (value: " +
                dataType + ").");
                //throw new IllegalArgumentException("OMMMsg payload must be
                // field list");
        }
        return result;
    }

    /**
     * Method finds the instance of {@link OMMData} using the specified
     * fieldEntry and fieldId.
     */
    protected OMMData getFieldData(OMMFieldEntry fieldEntry, short fieldId) {

        log.debug("getFieldData: method begins; msg: " + fieldEntry);

        OMMData result = null;

        if (fieldEntry != null) {

            FidDef fidDef = fieldDictionary.getFidDef(fieldId);

            short type = fidDef.getOMMType();

            result = fieldEntry.getData(type);
        }
        return result;
    }

    /**
     * Getter method for the {@link FieldDictionary} property.
     */
    public FieldDictionary getFieldDictionary() {
        return fieldDictionary;
    }
}