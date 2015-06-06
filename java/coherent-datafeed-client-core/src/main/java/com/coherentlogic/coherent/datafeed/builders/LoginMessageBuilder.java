package com.coherentlogic.coherent.datafeed.builders;

import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMPool;
import com.reuters.rfa.rdm.RDMUser;

/**
 * Builder for login-related messages.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class LoginMessageBuilder extends OMMMsgBuilder<LoginMessageBuilder> {

    public LoginMessageBuilder(OMMPool pool, OMMEncoder encoder) {
        super(pool, encoder);
    }

    public LoginMessageBuilder setUserName (String userName) {
        setAttribInfo((String)null, RDMUser.NameType.USER_NAME, userName);
        return this;
    }
}
