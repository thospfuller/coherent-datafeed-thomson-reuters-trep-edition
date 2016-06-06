package com.coherentlogic.coherent.datafeed.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;

/**
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class SessionBeanFactory implements TypedFactory<SessionBean> {

    @Autowired
    private ApplicationContext applicationContext;

    public SessionBeanFactory() {
    }

    @Override
    public SessionBean getInstance() {
        return applicationContext.getBean(SessionBean.class);
    }
}
