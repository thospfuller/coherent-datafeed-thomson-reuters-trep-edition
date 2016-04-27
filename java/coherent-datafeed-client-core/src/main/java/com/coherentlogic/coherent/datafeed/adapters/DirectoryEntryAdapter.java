package com.coherentlogic.coherent.datafeed.adapters;

import static com.reuters.rfa.rdm.RDMService.Info.Capabilities;
import static com.reuters.rfa.rdm.RDMService.Info.DictionariesProvided;
import static com.reuters.rfa.rdm.RDMService.Info.DictionariesUsed;
import static com.reuters.rfa.rdm.RDMService.Info.QoS;
import static com.reuters.rfa.rdm.RDMService.Info.ServiceID;
import static com.reuters.rfa.rdm.RDMService.Info.SupportsQoSRange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coherentlogic.coherent.data.model.core.exceptions.ConversionFailedException;
import com.coherentlogic.coherent.datafeed.domain.DirectoryEntry;
import com.reuters.rfa.common.QualityOfService;
import com.reuters.rfa.internal.rwf.RwfArrayEntry;
import com.reuters.rfa.omm.OMMArray;
import com.reuters.rfa.omm.OMMData;
import com.reuters.rfa.omm.OMMDataBuffer;
import com.reuters.rfa.omm.OMMElementEntry;
import com.reuters.rfa.omm.OMMElementList;
import com.reuters.rfa.omm.OMMEntry;
import com.reuters.rfa.omm.OMMFilterEntry;
import com.reuters.rfa.omm.OMMFilterList;
import com.reuters.rfa.omm.OMMMap;
import com.reuters.rfa.omm.OMMMapEntry;
import com.reuters.rfa.omm.OMMMsg;
import com.reuters.rfa.omm.OMMNumeric;
import com.reuters.rfa.omm.OMMQos;
import com.reuters.rfa.omm.OMMTypes;
import com.reuters.rfa.rdm.RDMMsgTypes;
import com.reuters.rfa.rdm.RDMService;

/**
 * An adapter class that converts a directory value (which is an OMMMsg /
 * OMMMap) into an individual {@link DirectoryEntry} object.
 *
 * setServiceState();
 * setAcceptingRequests();
 *
 * MAP_ENTRY (Action.ADD) / MAP_ENTRY (Action.UPDATE)
 *
 * @todo Rename this class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DirectoryEntryAdapter
    implements BasicAdapter<OMMMsg, List<DirectoryEntry>> {

    static final String
        SERVICE_STATE = "ServiceState",
        ACCEPTING_REQUESTS = "AcceptingRequests";

    static final Long FALSE = Long.valueOf (0);

    private static final Logger log =
        LoggerFactory.getLogger(DirectoryEntryAdapter.class);

    /**
     * Method converts the {@link OMMMsg} into a list of
     * {@link DirectoryEntry} objects.
     */
    @Override
    public List<DirectoryEntry> adapt(OMMMsg msg) {

        List<DirectoryEntry> directoryEntries =
            new ArrayList<DirectoryEntry> ();

        OMMMap map = (OMMMap) msg.getPayload();

        adapt (map, directoryEntries);

        return directoryEntries;
    }

    /**
     * Method converts the {@link OMMMap} into a list of
     * {@link DirectoryEntry} objects.
     *
     * @see OMMSubAppContext #344 / update, delete, add.
     */
    public void adapt(
        OMMMap map,
        List<DirectoryEntry> directoryEntries
    ) {
        Iterator<OMMEntry> iterator = (Iterator<OMMEntry>) map.iterator();

        while (iterator.hasNext()) {

            DirectoryEntry directoryEntry =
                new DirectoryEntry ();

            directoryEntries.add(directoryEntry);

            OMMEntry nextEntry = (OMMEntry) iterator.next();

            short type = nextEntry.getType();
 
            if (type == OMMTypes.MAP_ENTRY) {
                OMMMapEntry mapEntry = (OMMMapEntry) nextEntry;

                OMMData key = mapEntry.getKey();
                String name = key.toString();

                directoryEntry.setName(name);

                // Unit test this logic.
                // SET, CLEAR, UPDATE (double-check this though as it may be
                // ADD, UPDATE, DELETE).
                byte action = mapEntry.getAction();

                directoryEntry.setActionType(action);

                OMMFilterList filterList = (OMMFilterList) nextEntry.getData();

                adapt (filterList, directoryEntry);
            } else {
                log.warn("adapt invoked with an unknown type: " + type);
            }
        }
    }

    /**
     * Method extracts the values in the filterList and assigns them to the
     * appropriate directoryServiceEntry property.
     */
    void adapt (
        OMMFilterList filterList,
        DirectoryEntry directoryEntry
    ) {
        /* Review this logic as the method is iterating over a list whereas
         * this list should only be of size two (the first entry contains all of
         * the properties such as Capabilities and DictionariesProvided, whereas
         * the second contains the ServiceState and AcceptingRequests.
         */
        Iterator<?> iterator = filterList.iterator();

        while (iterator.hasNext()) {
            OMMFilterEntry nextFilterEntry = (OMMFilterEntry) iterator.next();

            adapt (nextFilterEntry, directoryEntry);
        }
    }

    /**
     * Method extracts state or info data from the filterEntry and assigns it to
     * the appropriate directoryServiceEntry property.
     */
    void adapt (
        OMMFilterEntry filterEntry,
        DirectoryEntry directoryEntry
    ) {

        int filterId = filterEntry.getFilterId();

        if (filterId == RDMService.FilterId.STATE) {
            // State = serviceUp, accepting requests
            Map<String, Long> stateMap = new HashMap<String, Long> ();

            OMMElementList elements = (OMMElementList) filterEntry.getData();

            assignStates (elements, stateMap);
            assignStates (stateMap, directoryEntry);

        } else if (filterId == RDMService.FilterId.INFO) {

            OMMElementList elementList = (OMMElementList) filterEntry.getData();

            assignInfo (elementList, directoryEntry);
        }
    }

    /**
     * Method assigns the state values in the elements param to the map param.
     *
     * Note that a single state value would be, for example, ServiceState: 1
     */
    void assignStates (OMMElementList elements, Map<String, Long> map) {

        Iterator<?> iterator = elements.iterator();

        while (iterator.hasNext()) {
            OMMElementEntry elementEntry = (OMMElementEntry) iterator.next();
            assignStates (elementEntry, map);
        }
    }

    /**
     * Method iterates over the elements in the elementList and sets the
     * corresponding properties on the directoryServiceEntry.
     */
    void assignInfo (
        OMMElementList elementList,
        DirectoryEntry directoryEntry
    ) {
        setCapabilities(elementList, directoryEntry);
        setDictionariesProvided (elementList, directoryEntry);
        setDictionariesUsed(elementList, directoryEntry);
        setQualityOfService(elementList, directoryEntry);
        setSupportsQualityOfServiceRange(elementList, directoryEntry);
        setServiceID(elementList, directoryEntry);
    }

    /**
     * Method finds the "Capabilities" values on the element list and then sets
     * this value on the appropriate directoryServiceEntry property.
     */
    void setCapabilities (
        OMMElementList elementList,
        DirectoryEntry directoryEntry
    ) {
        List<String> capabilities =
            convert (elementList, Capabilities);

        directoryEntry.setCapabilities(capabilities);
    }

    /**
     * Method finds the "DictionariesProvided" values on the element list and
     * then sets this value on the appropriate directoryServiceEntry property.
     */
    void setDictionariesProvided (
        OMMElementList elementList,
        DirectoryEntry directoryEntry
    ) {
        List<String> dictionariesProvided =
            convert (elementList, DictionariesProvided);

        directoryEntry.setDictionariesProvided(dictionariesProvided);
    }

    /**
     * Method finds the "DictionariesUsed" values on the element list and then
     * sets this value on the appropriate directoryServiceEntry property.
     */
    void setDictionariesUsed (
        OMMElementList elementList,
        DirectoryEntry directoryEntry
    ) {
        List<String> dictionariesUsed =
            convert (elementList, DictionariesUsed);

        directoryEntry.setDictionariesUsed(dictionariesUsed);
    }

    /**
     * Method finds the "QoS" values on the element list and then sets
     * this value on the appropriate directoryServiceEntry property.
     */
    void setQualityOfService (
        OMMElementList elementList,
        DirectoryEntry directoryEntry
    ) {
        List<QualityOfService> qualityOfServices =
                new ArrayList<QualityOfService> ();

        OMMElementEntry elementEntry =
            elementList.find(QoS);

        OMMArray array =
            (OMMArray) elementEntry.getData();

        Iterator<?> iterator = array.iterator();

        while (iterator.hasNext()) {

            OMMEntry entry = (OMMEntry) iterator.next();

            OMMQos ommQos = (OMMQos) entry.getData();

            QualityOfService qualityOfService = ommQos.toQos();

            qualityOfServices.add(qualityOfService);
        }
        directoryEntry.setQualityOfService(qualityOfServices);
    }

    /**
     * Method finds the "SupportsQoSRange" on the element list and then sets
     * this value on the appropriate directoryServiceEntry property.
     */
    void setSupportsQualityOfServiceRange (
        OMMElementList elementList,
        DirectoryEntry directoryEntry
    ) {
        OMMElementEntry elementEntry = elementList.find(SupportsQoSRange);

        OMMNumeric number = (OMMNumeric) elementEntry.getData();

        String text = number.toString();

        int supportsQoSRange = Integer.parseInt(text);

        log.debug ("supportsQoSRange: " + supportsQoSRange);

        directoryEntry.setSupportsQoSRange(supportsQoSRange);
    }

    /**
     * Method finds the "SupportsQoSRange" on the element list and then sets
     * this value on the appropriate directoryServiceEntry property.
     */
    void setServiceID (
        OMMElementList elementList,
        DirectoryEntry directoryEntry
    ) {
        OMMElementEntry elementEntry = elementList.find(ServiceID);

        OMMNumeric number = (OMMNumeric) elementEntry.getData();

        String text = number.toString();

        int serviceID = Integer.parseInt(text);

        log.debug ("serviceID: " + serviceID);

        directoryEntry.setServiceID(serviceID);
    }

    /**
     * Method finds the target on the elementList, converts it to a list of
     * strings, and returns this list.
     *
     * This method is used for finding a particular entry in an elementList,
     * for example the Capabilities.
     */
    List<String> convert (OMMElementList elementList, String target) {

        log.info("convert: method begins; elementList: " + elementList +
            ", target: " + target);

        OMMElementEntry entry =
            elementList.find(target);

        OMMArray array =
            (OMMArray) entry.getData();

        List<String> result =
            convert (array);

        return result;
    }

    /**
     * Method converts the array into a list of string values.
     */
    List<String> convert (OMMArray array) {
        List<String> result = new ArrayList<String> ();

        Iterator<?> iterator = array.iterator();

        while (iterator.hasNext()) {

            RwfArrayEntry arrayEntry = (RwfArrayEntry) iterator.next();

            String next = toString(arrayEntry);

            result.add(next);
        }
        return result;
    }

    /**
     * Method converts the arrayEntry into a single string value. Note that this
     * method can convert UINT and ASCII_STRING types -- other types will be
     * ignored for now, however a warning message will be logged.
     */
    String toString (RwfArrayEntry arrayEntry) {

        String result = null;

        OMMData data = arrayEntry.getData();

        short dataType = data.getType();

        if (dataType == OMMTypes.UINT) {

            OMMNumeric number = (OMMNumeric) data;

            String numericText = number.toString ();

            short shortValue = Short.valueOf(numericText);

            String value = RDMMsgTypes.toString(shortValue);

            result = value;
        } else if (dataType == OMMTypes.ASCII_STRING) {

            OMMDataBuffer value = (OMMDataBuffer) arrayEntry.getData();

            byte[] bytes = value.getBytes();

            result = new String (bytes);
        } else {
            log.warn("Unsure how to convert the following arrayEntry type: " +
                OMMTypes.toString(dataType) + " so this will be ignored.");
        }

        log.info ("result: " + result);

        return result;
    }

    /**
     * Method takes a single state value from the elementEntry param and assigns
     * it to the map param.
     */
    void assignStates (OMMElementEntry elementEntry, Map<String, Long> map) {
        /*
         * Comment: Should this be set as a map? I think so but it would be good
         * if we could set this as properties -- the problem is that we don't
         * know all of the properties in advance and cannot predict what order
         * they'll be in (though not knowing is the bigger problem).
         */
        String name = elementEntry.getName();
        OMMData data = elementEntry.getData();
        String value = data.toString();

        log.info(
            "name: " + name +
            ", value: " + value +
            ", type: " + OMMTypes.toString(data.getType()));

        if (!(data instanceof OMMNumeric))
            throw new ConversionFailedException("The type of element with " +
                "the name '" + name + "' should have been an OMMNumeric " +
                "however the following type was returned: " +
                OMMTypes.toString(data.getType()));

        OMMNumeric iValue = (OMMNumeric) data;

        Long result = iValue.getLongValue();

        map.put(name, result);
    }

    void assignStates (Map<String, Long> map, DirectoryEntry directoryEntry) {
        // UINT
        Long serviceState = map.get(SERVICE_STATE);
        // UINT
        Long acceptingRequests = map.get(ACCEPTING_REQUESTS);

        directoryEntry.setServiceState(serviceState);

        boolean acceptingRequestsFlag = FALSE.equals(acceptingRequests)
            ? false : true;

        directoryEntry.setAcceptingRequests(acceptingRequestsFlag);
    }
}
/*
Starting DomainServer...
*****************************************************************************
*               Begin RFA Java Dictionary Program                           *
*****************************************************************************
RFA Version:  7.4.1.L1.all.rrg
LoginClient: Sending login request...
LoginClient.processEvent: Received Login Response... 
LoginClient: Received Login Response - MsgType.REFRESH_RESP
MESSAGE
    Msg Type: MsgType.REFRESH_RESP
    Msg Model Type: LOGIN
    Indication Flags: REFRESH_COMPLETE
    Hint Flags: HAS_ATTRIB_INFO | HAS_ITEM_GROUP | HAS_RESP_TYPE_NUM | HAS_STATE
    State: OPEN, SUSPECT, NONE,  "All connections pending"
    Group: 0000
    RespTypeNum: 0 (RespType.SOLICITED)
    AttribInfo
        Name: CoherentLogic_Fuller
        NameType: 1 (USER_NAME)
        Attrib
            ELEMENT_LIST
                ELEMENT_ENTRY ApplicationId: 256
                ELEMENT_ENTRY Position: 192.168.10.7/tron
                ELEMENT_ENTRY AllowSuspectData: 1
                ELEMENT_ENTRY SingleOpen: 1
                ELEMENT_ENTRY SupportBatchRequests: 1
                ELEMENT_ENTRY SupportOptimizedPauseResume: 1
                ELEMENT_ENTRY SupportPauseResume: 1
                ELEMENT_ENTRY SupportViewRequests: 1
                ELEMENT_ENTRY SupportOMMPost: 1
    Payload: None
LoginClient.processEvent: Received Login Response... 
LoginClient: Received Login STATUS OK Response
MESSAGE
    Msg Type: MsgType.STATUS_RESP
    Msg Model Type: LOGIN
    Indication Flags: 
    Hint Flags: HAS_ATTRIB_INFO | HAS_STATE
    State: OPEN, OK, NONE,  "Login accepted by host stlimsp2pse."
    AttribInfo
        Name: CoherentLogic_Fuller
        NameType: 1 (USER_NAME)
        Attrib
            ELEMENT_LIST
                ELEMENT_ENTRY ApplicationId: 256
                ELEMENT_ENTRY ApplicationName: P2PS
                ELEMENT_ENTRY Position: 192.168.10.7/tron
                ELEMENT_ENTRY ProvidePermissionExpressions: 1
                ELEMENT_ENTRY ProvidePermissionProfile: 0
                ELEMENT_ENTRY AllowSuspectData: 1
                ELEMENT_ENTRY SingleOpen: 1
                ELEMENT_ENTRY SupportBatchRequests: 1
                ELEMENT_ENTRY SupportOptimizedPauseResume: 1
                ELEMENT_ENTRY SupportPauseResume: 1
                ELEMENT_ENTRY SupportViewRequests: 1
                ELEMENT_ENTRY SupportOMMPost: 1
    Payload: None
DictionaryDemo Login successful...
DirectoryClient.processEvent: Received Directory Response... 
MESSAGE
    Msg Type: MsgType.REFRESH_RESP
    Msg Model Type: DIRECTORY
    Indication Flags: REFRESH_COMPLETE
    Hint Flags: HAS_ATTRIB_INFO | HAS_ITEM_GROUP | HAS_RESP_TYPE_NUM | HAS_STATE
    State: NONSTREAMING, OK, NONE,  ""
    Group: 0000
    RespTypeNum: 0 (RespType.SOLICITED)
    AttribInfo
        Filter: 3 (INFO | STATE)
    Payload: 483 bytes
        MAP
        flags: 
            MAP_ENTRY (Action.ADD) : 
            flags: 
            Key: IDN_RDF
            Value: 
                FILTER_LIST
                flags: 
                    FILTER_ENTRY 1 (Action.SET) : 
                    flags: 
                        ELEMENT_LIST
                            ELEMENT_ENTRY Name: IDN_RDF
                            ELEMENT_ENTRY Capabilities: 
                                ARRAY
                                    ARRAY_ENTRY: 6
                                    ARRAY_ENTRY: 10
                                    ARRAY_ENTRY: 5
                            ELEMENT_ENTRY DictionariesProvided: 
                                ARRAY
                                    ARRAY_ENTRY: RWFFld
                                    ARRAY_ENTRY: RWFEnum
                            ELEMENT_ENTRY DictionariesUsed: 
                                ARRAY
                                    ARRAY_ENTRY: RWFFld
                                    ARRAY_ENTRY: RWFEnum
                            ELEMENT_ENTRY QoS: 
                                ARRAY
                                    ARRAY_ENTRY: (RT, TbT)
                            ELEMENT_ENTRY SupportsQoSRange: 0
                            ELEMENT_ENTRY ServiceID: 257
                    FILTER_ENTRY 2 (Action.SET) : 
                    flags: 
                        ELEMENT_LIST
                            ELEMENT_ENTRY ServiceState: 1
                            ELEMENT_ENTRY AcceptingRequests: 1
            MAP_ENTRY (Action.ADD) : 
            flags: 
            Key: dIDN_RDF
            Value: 
                FILTER_LIST
                flags: 
                    FILTER_ENTRY 1 (Action.SET) : 
                    flags: 
                        ELEMENT_LIST
                            ELEMENT_ENTRY Name: dIDN_RDF
                            ELEMENT_ENTRY Capabilities: 
                                ARRAY
                                    ARRAY_ENTRY: 6
                                    ARRAY_ENTRY: 10
                                    ARRAY_ENTRY: 5
                            ELEMENT_ENTRY DictionariesProvided: 
                                ARRAY
                                    ARRAY_ENTRY: RWFFld
                                    ARRAY_ENTRY: RWFEnum
                            ELEMENT_ENTRY DictionariesUsed: 
                                ARRAY
                                    ARRAY_ENTRY: RWFFld
                                    ARRAY_ENTRY: RWFEnum
                            ELEMENT_ENTRY QoS: 
                                ARRAY
                                    ARRAY_ENTRY: (RT, TbT)
                            ELEMENT_ENTRY SupportsQoSRange: 0
                            ELEMENT_ENTRY ServiceID: 2000
                    FILTER_ENTRY 2 (Action.SET) : 
                    flags: 
                        ELEMENT_LIST
                            ELEMENT_ENTRY ServiceState: 1
                            ELEMENT_ENTRY AcceptingRequests: 1
DirectoryClient: Decoding service IDN_RDF
DirectoryClient: IDN_RDF DictionariesProvided [RWFFld, RWFEnum]
DirectoryClient: Decoding service dIDN_RDF
DirectoryClient: dIDN_RDF DictionariesProvided [RWFFld, RWFEnum]
DictionaryClient.openFullDictionary: Downloading full RWFFld from IDN_RDF
DictionaryClient.openFullDictionary: Downloading full RWFEnum from IDN_RDF
DirectoryClient: Receive a COMPLETION_EVENT, com.reuters.rfa.internal.session.omm.OMMSubHandleImpl@1fa78298
DictionaryClient.processFullDictionary: Receive full dictionary of RWFFld
DictionaryClient.processFullDictionary: Field Dictionary Refresh is complete
DictionaryClient: Receive a COMPLETION_EVENT, com.reuters.rfa.internal.session.omm.OMMSubHandleImpl@70b8b8d3
DictionaryClient.processFullDictionary: Receive full dictionary of RWFEnum
DictionaryClient.processFullDictionary: EnumTable Dictionary Refresh is complete
DictionaryClient.processFullDictionary: Printing Field Dictionary
!__________________________
!   RDM Field Dictionary   
!__________________________
!tag Version    4.10
!tag Id    1
!
!ACRONYM    DDE ACRONYM          FID  RIPPLES TO  FIELD TYPE     LENGTH  RWF TYPE   RWF LEN
!-------    -----------          ---  ----------  ----------     ------  --------   -------
PROD_PERM  "PERMISSION"            1   NULL        INTEGER            5   UINT            2
RDNDISPLAY "DISPLAYTEMPLATE"       2   NULL        INTEGER            3   UINT32          1
DSPLY_NAME "DISPLAY NAME"          3   NULL        ALPHANUMERIC      16   RMTES_STRING   16
RDN_EXCHID "IDN EXCHANGE ID"       4   NULL        ENUMERATED   3 ( 3 )   ENUM            1
TIMACT     "TIME OF UPDATE"        5   NULL        TIME               5   TIME            5
TRDPRC_1   "LAST"                  6   TRDPRC_2    PRICE             17   REAL            7
TRDPRC_2   "LAST 1"                7   TRDPRC_3    PRICE             17   REAL            7
TRDPRC_3   "LAST 2"                8   TRDPRC_4    PRICE             17   REAL            7

.....

!
! EnumIndex  [205]
! ACRONYM    FID
! -------    ---
!
RPT_HLT_DR  6616
!
! VALUE      DISPLAY
! -----      -------
      0         "   "
      1         "30M"
      2         "1H "
      3         "2H "
      4         "NR "
!
! EnumIndex  [206]
! ACRONYM    FID
! -------    ---
!
SVC_STATE   6633
!
! VALUE      DISPLAY
! -----      -------
      0         "        "
      1         "Active  "
      2         "Inactive"
      3         "Suspend "
DictionaryClient: Finishing data dictionary display, prepare to clean up and exit
RWF: 14.0
RFA: Version = 7.4.1.L1.all.rrg, Date = Mon Mar 25 18:11:52 EDT 2013, Jar Path = C:\development\software\rfaj7.4.1.L1.all\setup\rfaj7.4.1.L1.all.rrg\Libs\rfa.jar, Jar Size = 1910996

class com.reuters.rfa.example.omm.dictionary.DictionaryDemo exiting.
DictionaryClient: Receive a COMPLETION_EVENT, com.reuters.rfa.internal.session.omm.OMMSubHandleImpl@6f6e5e75
LoginClient: Receive a COMPLETION_EVENT, com.reuters.rfa.internal.session.omm.OMMSubHandleImpl@1341cac
EventQueue has been deactivated.

*/