package com.coherentlogic.coherent.datafeed.adapters;

import java.util.Map;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.adapters.omm.OMMFieldEntryAdapter;
import com.coherentlogic.coherent.datafeed.domain.AttribInfo;
import com.coherentlogic.coherent.datafeed.domain.RFABean;
import com.coherentlogic.coherent.datafeed.domain.SessionBean;
import com.reuters.rfa.dictionary.FieldDictionary;
import com.reuters.rfa.omm.OMMData;

public class SessionBeanAdapter extends RFABeanAdapter<SessionBean> {//implements InOutAdapterSpecification<OMMItemEvent, SessionBean> {

//    private final Cache<Handle, String> dacsIdCache;
//
//    private final Cache<String, SessionBean> sessionBeanCache;

//    private final TypedFactory<AttribInfo> attribInfoFactory;

//    private final TypedFactory<StatusResponse> statusResponseFactory;

//    public SessionBeanAdapter(
//        Cache<Handle, String> dacsIdCache,
//        Cache<String, SessionBean> sessionBeanCache,
//        TypedFactory<AttribInfo> attribInfoFactory,
//        TypedFactory<StatusResponse> statusResponseFactory
//    )  {
//        this.dacsIdCache = dacsIdCache;
//        this.sessionBeanCache = sessionBeanCache;
//        this.attribInfoFactory = attribInfoFactory;
//        this.statusResponseFactory = statusResponseFactory;
//    }

    public SessionBeanAdapter(
        TypedFactory<SessionBean> rfaBeanFactory,
        TypedFactory<AttribInfo> attribInfoFactory
    ) throws SecurityException, NoSuchMethodException {
        super(rfaBeanFactory, attribInfoFactory, (FieldDictionary) null, null, SessionBean.class);
    }

//    @Override
//    public SessionBean adapt(OMMItemEvent source) {
//
//        Handle handle = source.getHandle();
//
//        String dacsId = dacsIdCache.get(handle);
//
//        Utils.assertNotNull ("dacsId", dacsId);
//
//        SessionBean sessionBean = sessionBeanCache.get(dacsId);
//
//        adapt(source, sessionBean);
//
//        return sessionBean;
//    }
//
//    @Override
//    public void adapt(OMMItemEvent source, SessionBean target) {
//        // TODO Auto-generated method stub
//        
//    }
}
