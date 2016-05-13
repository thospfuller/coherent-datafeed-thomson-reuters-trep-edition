package com.coherentlogic.coherent.datafeed.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.AttribInfo;

@Component(AttribInfoFactory.BEAN_NAME)
public class AttribInfoFactory implements TypedFactory<AttribInfo> {

    public static final String BEAN_NAME = "attribInfoFactory";

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public AttribInfo getInstance() {
        return applicationContext.getBean(AttribInfo.class);
    }
}
