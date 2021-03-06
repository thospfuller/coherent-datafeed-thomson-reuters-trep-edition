package com.coherentlogic.coherent.datafeed.factories;

import com.coherentlogic.coherent.data.model.core.factories.TypedFactory;
import com.coherentlogic.coherent.datafeed.builders.LoginMessageBuilder;
import com.reuters.rfa.omm.OMMEncoder;
import com.reuters.rfa.omm.OMMPool;

/**
 * A factory for creating instances of {@link LoginMessageBuilder}.
 *
 * Note that the {@link LoginMessageBuilder} is not thread-safe since state is
 * maintained in each instance of this class, which explains why we use a
 * factory to create this class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class LoginMessageBuilderFactory
    implements TypedFactory<LoginMessageBuilder> {

    public static final String BEAN_NAME = "loginMessageBuilderFactory";

    private final OMMPool pool;

    private final OMMEncoder encoder;

    public LoginMessageBuilderFactory (OMMPool pool, OMMEncoder encoder) {
        this.pool = pool;
        this.encoder = encoder;
    }

    @Override
    public LoginMessageBuilder getInstance() {
        return new LoginMessageBuilder (pool, encoder);
    }
}
