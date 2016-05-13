package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_INT_RETURN_VALUE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DIRECTORY_ENTRY;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coherentlogic.coherent.data.model.core.domain.SerializableBean;
import com.coherentlogic.coherent.datafeed.annotations.Changeable;
import com.coherentlogic.coherent.datafeed.annotations.OMMType;
import com.reuters.rfa.common.QualityOfService;
import com.reuters.rfa.omm.OMMMapEntry.Action;
import com.reuters.rfa.omm.OMMTypes;
import com.reuters.rfa.rdm.RDMMsgTypes;
import com.reuters.rfa.rdm.RDMService;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * A domain object representation of a directory service entry.
 *
 * @todo Rename this class to DirectoryEntry.
 *
 * @todo See DiectoryServiceEntryAdapter as it looks to me like the annotations
 *  below may not be used and hence we can get rid of them (if this is the
 *  case).
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
@XStreamAlias(DIRECTORY_ENTRY)
public class DirectoryEntry extends SerializableBean {

    private static final long serialVersionUID = -7839342136372860963L;

    private static final Map<Byte, ActionType> actionTypeMap =
        new HashMap<Byte, ActionType> ();

    static final String
        BIG_NAME = "Name",
        BIG_CAPABILITIES = "Capabilities",
        BIG_DICTIONARIES_PROVIDED = "DictionariesProvided",
        BIG_DICTIONARIES_USED = "DictionariesUsed",
        BIG_QUALITY_OF_SERVICE = "QualityOfService",
        BIG_SUPPORTS_QOS_RANGE = "SupportsQoSRange",
        BIG_SERVICE_ID = "ServiceID",
        BIG_SERVICE_STATE = "ServiceState",
        BIG_ACCEPTING_REQUESTS = "AcceptingRequests";

    static {
        actionTypeMap.put(Action.ADD, ActionType.ADD); // SET
        actionTypeMap.put(Action.DELETE, ActionType.DELETE); // CLEAR
        actionTypeMap.put(Action.UPDATE, ActionType.UPDATE); // UPDATE
    }

    private Map<String, String> stateMap;

    private String name = null;

    /**
     * Array of valid message model types that the service can provide.
     *
     * @see RDMService.Info.Capabilities
     * @see 
     */
    private List<String> capabilities = null;

    private List<String> dictionariesProvided = null;

    private List<String> dictionariesUsed = null;

    private transient List<QualityOfService> qualityOfService = null;

    private int supportsQoSRange = DEFAULT_INT_RETURN_VALUE;

    private int serviceID = DEFAULT_INT_RETURN_VALUE;

    private Long serviceState = null;

    //
    private boolean acceptingRequests = false;

    /**
     * This is used to indicate if the event was an add, delete, or update
     * event.
     */
    private ActionType actionType = null;

    private final Map<String, Long> attributeMap =
        new HashMap<String, Long> ();

    /**
     * A flag that indicates that the directory has been loaded -- this is set
     * when RFA sends a REFRESH_COMPLETE message with the handle that points to
     * this directory.
     */
    private boolean loaded = false;

    public ActionType getActionType() {
        return actionType;
    }

    public static final String ACTION_TYPE = "actionType";

    // No annotation is necessary.
    public void setActionType(@Changeable(ACTION_TYPE) ActionType actionType) {

//        ActionType oldValue = this.actionType;

        this.actionType = actionType;

//        firePropertyChange(ACTION_TYPE, oldValue, actionType);
    }

    /**
     * Setter method that assigns one of the {@link ActionType} values in place
     * of the value.
     *
     * @param value See OMMMapEntry.Action.[ADD, UPDATE, DELETE].
     */
    // No annotation is necessary.
    public void setActionType(byte value) {

        ActionType actionType = actionTypeMap.get(value);

        setActionType (actionType);
    }

    public boolean isLoaded() {
      return loaded;
    }

    public static final String LOADED = "loaded";

    // No annotation is necessary.
    public void setLoaded(@Changeable(LOADED) boolean loaded) {

//        boolean oldValue = this.loaded;

        this.loaded = loaded;

//        firePropertyChange(LOADED, oldValue, loaded);
    }

    public Map<String, String> getStates() {
        return stateMap;
    }

    public static final String STATE_MAP = "stateMap";

    // No annotation is necessary.
    public void setStates(@Changeable(STATE_MAP) Map<String, String> stateMap) {

//        Map<String, String> oldValue = this.stateMap;

        this.stateMap = stateMap;

//        firePropertyChange(STATE_MAP, oldValue, stateMap);
    }

    public String getName() {
        return name;
    }

    public static final String NAME = "name";

    @OMMType(named=BIG_NAME, value=OMMTypes.ASCII_STRING)
    public void setName(@Changeable(NAME) String name) {

//        String oldValue = this.name;

        this.name = name;

//        firePropertyChange(NAME, oldValue, name);
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public static final String CAPABILITIES = "capabilities";

    /**
     * @param capabilities The list of capabilities which has been converted
     *  from numeric values into their string equivalents.
     *
     * @see RDMMsgTypes
     *
     * @todo Check method name consistency.
     */
    @OMMType(named=BIG_CAPABILITIES, value=OMMTypes.ARRAY)
    public void setCapabilities(@Changeable(CAPABILITIES) List<String> capabilities) {

//        List<String> oldValue = this.capabilities;

        this.capabilities = capabilities;

//        firePropertyChange(CAPABILITIES, oldValue, capabilities);
    }

    public List<String> getDictionariesProvided() {
        return dictionariesProvided;
    }

    public static final String DICTIONARIES_PROVIDED = "dictionariesProvided";

    @OMMType(named=BIG_DICTIONARIES_PROVIDED, value=OMMTypes.ARRAY)
    public void setDictionariesProvided(@Changeable(DICTIONARIES_PROVIDED) List<String> dictionariesProvided) {

//        List<String> oldValue = this.dictionariesProvided;

        this.dictionariesProvided = dictionariesProvided;

//        firePropertyChange(DICTIONARIES_PROVIDED, oldValue, dictionariesProvided);
    }

    public List<String> getDictionariesUsed() {
        return dictionariesUsed;
    }

    public static final String DICTIONARIES_USED = "dictionariesUsed";

    @OMMType(named=BIG_DICTIONARIES_USED, value=OMMTypes.ARRAY)
    public void setDictionariesUsed(@Changeable(DICTIONARIES_USED) List<String> dictionariesUsed) {

//        List<String> oldValue = this.dictionariesUsed;

        this.dictionariesUsed = dictionariesUsed;

//        firePropertyChange(DICTIONARIES_USED, oldValue, dictionariesUsed);
    }

    public List<QualityOfService> getQualityOfService() {
        return qualityOfService;
    }

    public static final String QUALITY_OF_SERVICE = "qualityOfService";

    @OMMType(named=BIG_QUALITY_OF_SERVICE, value=OMMTypes.ARRAY)
    public void setQualityOfService(@Changeable(QUALITY_OF_SERVICE) List<QualityOfService> qualityOfService) {

//        List<QualityOfService> oldValue = this.qualityOfService;

        this.qualityOfService = qualityOfService;

//        firePropertyChange(QUALITY_OF_SERVICE, oldValue, qualityOfService);
    }

    public int getSupportsQoSRange() {
        return supportsQoSRange;
    }

    public static final String SUPPORTS_QOS_RANGE = "supportsQoSRange";

    @OMMType(named=BIG_SUPPORTS_QOS_RANGE, value=OMMTypes.INT)
    public void setSupportsQoSRange(@Changeable(SUPPORTS_QOS_RANGE) int supportsQoSRange) {

//        int oldValue = this.supportsQoSRange;

        this.supportsQoSRange = supportsQoSRange;

//        firePropertyChange(SUPPORTS_QOS_RANGE, oldValue, supportsQoSRange);
    }

    public int getServiceID() {
        return serviceID;
    }

    public static final String SERVICE_ID = "serviceID";

    /**
     * @todo Check method name consistency.
     */
    @OMMType(named=BIG_SERVICE_ID, value=OMMTypes.INT)
    public void setServiceID(@Changeable(SERVICE_ID) int serviceID) {

//        int oldValue = this.serviceID;

        this.serviceID = serviceID;

//        firePropertyChange(SERVICE_ID, oldValue, serviceID);
    }

    public Long getServiceState() {
        return serviceState;
    }

    public static final String SERVICE_STATE = "serviceState";

    @OMMType(named=BIG_SERVICE_STATE, value=OMMTypes.UINT)
    public void setServiceState(@Changeable(SERVICE_STATE) Long serviceState) {

//        Long oldValue = this.serviceState;

        this.serviceState = serviceState;

//        firePropertyChange(SERVICE_STATE, oldValue, serviceState);
    }

    public boolean isAcceptingRequests() {
        return acceptingRequests;
    }

    public static final String ACCEPTING_REQUESTS = "acceptingRequests";

    @OMMType(named=BIG_ACCEPTING_REQUESTS, value=OMMTypes.UINT)
    public void setAcceptingRequests(@Changeable(ACCEPTING_REQUESTS) boolean acceptingRequests) {

//        boolean oldValue = this.acceptingRequests;

        this.acceptingRequests = acceptingRequests;

//        firePropertyChange(ACCEPTING_REQUESTS, oldValue, acceptingRequests);
    }
}
