package com.coherentlogic.coherent.datafeed.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.StatusResponse;

/**
 * Factory class for creating {@link StatusResponse} objects.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class DefaultStatusResponseFactory implements TypedFactory<StatusResponse> {

    public static final String BEAN_NAME = "statusResponseFactory";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public StatusResponse getInstance() {
        return applicationContext.getBean(StatusResponse.class);
    }
}
