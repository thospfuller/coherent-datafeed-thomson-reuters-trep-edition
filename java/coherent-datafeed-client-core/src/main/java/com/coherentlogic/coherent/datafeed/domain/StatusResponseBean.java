package com.coherentlogic.coherent.datafeed.domain;

import com.coherentlogic.coherent.data.model.core.annotations.Changeable;

/**
 * A base class for beans which have a status response.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class StatusResponseBean extends RFABean {

    private StatusResponse statusResponse;

    public StatusResponse getStatusResponse() {
        return statusResponse;
    }

    public static final String STATUS_RESPONSE = "statusResponse";

    public void setStatusResponse(@Changeable(STATUS_RESPONSE) StatusResponse statusResponse) {
        this.statusResponse = statusResponse;
    }

    @Override
    public String toString() {
        return "StatusResponseBean [statusResponse=" + statusResponse + ", toString()=" + super.toString() + "]";
    }
}
