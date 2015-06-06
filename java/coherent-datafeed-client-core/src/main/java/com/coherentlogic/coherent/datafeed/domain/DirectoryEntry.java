package com.coherentlogic.coherent.datafeed.domain;

import static com.coherentlogic.coherent.datafeed.misc.Constants.DEFAULT_INT_RETURN_VALUE;
import static com.coherentlogic.coherent.datafeed.misc.Constants.DIRECTORY_ENTRY;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.coherentlogic.coherent.datafeed.annotations.OMMType;
import com.reuters.rfa.common.QualityOfService;
import com.reuters.rfa.omm.OMMMapEntry.Action;
import com.reuters.rfa.omm.OMMTypes;
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
public class DirectoryEntry implements Serializable {

    private static final long serialVersionUID = -2217926361746701263L;

    private static final Map<Byte, ActionType> actionTypeMap =
        new HashMap<Byte, ActionType> ();

    public static final String
        NAME = "Name",
        CAPABILITIES = "Capabilities",
        DICTIONARIES_PROVIDED = "DictionariesProvided",
        DICTIONARIES_USED = "DictionariesUsed",
        QUALITY_OF_SERVICE = "QualityOfService",
        SUPPORTS_QOS_RANGE = "SupportsQoSRange",
        SERVICE_ID = "ServiceID",
        SERVICE_STATE = "ServiceState",
        ACCEPTING_REQUESTS = "AcceptingRequests";

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

    // No annotation is necessary.
    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
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
        this.actionType = actionType;
    }

    public boolean isLoaded() {
      return loaded;
    }

    // No annotation is necessary.
    public void setLoaded(boolean loaded) {
      this.loaded = loaded;
    }

    public Map<String, String> getStates() {
        return stateMap;
    }

    // No annotation is necessary.
    public void setStates(Map<String, String> stateMap) {
        this.stateMap = stateMap;
    }

    public String getName() {
        return name;
    }

    @OMMType(named=NAME, value=OMMTypes.ASCII_STRING)
    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    /**
     * @param capabilities The list of capabilities which has been converted
     *  from numeric values into their string equivalents.
     *
     * @see RDMMsgTypes
     */
    @OMMType(named=CAPABILITIES, value=OMMTypes.ARRAY)
    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }

    public List<String> getDictionariesProvided() {
        return dictionariesProvided;
    }

    @OMMType(named=DICTIONARIES_PROVIDED, value=OMMTypes.ARRAY)
    public void setDictionariesProvided(List<String> dictionariesProvided) {
        this.dictionariesProvided = dictionariesProvided;
    }

    public List<String> getDictionariesUsed() {
        return dictionariesUsed;
    }

    @OMMType(named=DICTIONARIES_USED, value=OMMTypes.ARRAY)
    public void setDictionariesUsed(List<String> dictionariesUsed) {
        this.dictionariesUsed = dictionariesUsed;
    }

    public List<QualityOfService> getQualityOfService() {
        return qualityOfService;
    }

    @OMMType(named=QUALITY_OF_SERVICE, value=OMMTypes.ARRAY)
    public void setQualityOfService(List<QualityOfService> qualityOfService) {
        this.qualityOfService = qualityOfService;
    }

    public int getSupportsQoSRange() {
        return supportsQoSRange;
    }

    @OMMType(named=SUPPORTS_QOS_RANGE, value=OMMTypes.INT)
    public void setSupportsQoSRange(int supportsQoSRange) {
        this.supportsQoSRange = supportsQoSRange;
    }

    public int getServiceID() {
        return serviceID;
    }

    @OMMType(named=SERVICE_ID, value=OMMTypes.INT)
    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public Long getServiceState() {
        return serviceState;
    }

    @OMMType(named=SERVICE_STATE, value=OMMTypes.UINT)
    public void setServiceState(Long serviceState) {
        this.serviceState = serviceState;
    }

    public boolean isAcceptingRequests() {
        return acceptingRequests;
    }

    @OMMType(named=ACCEPTING_REQUESTS, value=OMMTypes.UINT)
    public void setAcceptingRequests(boolean acceptingRequests) {
        this.acceptingRequests = acceptingRequests;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
//
//public Map<String, String> getAttributes() {
//  return attributeMap;
//}
//
//public int getData() {
//return data;
//}
//
//@FilterId(value=RDMService.FilterId.DATA)
//public void setData(int data) {
//this.data = data;
//}
//
//public int getGroup() {
//return group;
//}
//
//@FilterId(value=RDMService.FilterId.GROUP)
//public void setGroup(int group) {
//this.group = group;
//}
//
//public int getInfo() {
//return info;
//}
//
//@FilterId(value=RDMService.FilterId.INFO)
//public void setInfo(int info) {
//this.info = info;
//}
//
//public int getLink() {
//return link;
//}
//
//@FilterId(value=RDMService.FilterId.LINK)
//public void setLink(int link) {
//this.link = link;
//}
//
//public int getLoad() {
//return load;
//}
//
//@FilterId(value=RDMService.FilterId.LOAD)
//public void setLoad(int load) {
//this.load = load;
//}
