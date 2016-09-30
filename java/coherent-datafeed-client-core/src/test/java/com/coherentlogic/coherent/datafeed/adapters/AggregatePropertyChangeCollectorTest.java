package com.coherentlogic.coherent.datafeed.adapters;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.data.model.core.listeners.AggregatePropertyChangeEvent.UpdateType;
import com.coherentlogic.coherent.datafeed.domain.RFABean;

/**
 * Unit test for the {@link AggregatePropertyChangeCollector} class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AggregatePropertyChangeCollectorTest {

    private AggregatePropertyChangeCollector<TestRFABean> aggregatePropertyChangeCollector = null;

    private TestRFABean testRFABean = null;

    @Before
    public void setUp() throws Exception {
        testRFABean = new TestRFABean ();

        // See Issue #25 -- the update type needs to be set properly.
        aggregatePropertyChangeCollector =
            new AggregatePropertyChangeCollector<TestRFABean> (testRFABean, UpdateType.full);
    }

    @After
    public void tearDown() throws Exception {
        aggregatePropertyChangeCollector = null;
        testRFABean = null;
    }

    @Test
    public void testDone() {

        AtomicInteger ctr = new AtomicInteger(0);

        testRFABean.addAggregatePropertyChangeListener(
            event -> {
                ctr.set(event.getPropertyChangeEventMap().size());
            }
        );

        testRFABean.setFoo(TestRFABean.FOO);
        testRFABean.setBar(TestRFABean.BAR);

        aggregatePropertyChangeCollector.done();

        assertEquals (2, ctr.get());
    }

    @Test
    public void testDoneWithNoListenersRegistered() {

        testRFABean.setFoo(TestRFABean.FOO);
        testRFABean.setBar(TestRFABean.BAR);

        // No exception should be thrown.
        aggregatePropertyChangeCollector.done();
    }
}

class TestRFABean extends RFABean {

    public static final String FOO = "foo", BAR = "bar";

    private String foo, bar;

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {

        String oldValue = this.foo;

        this.foo = foo;

        firePropertyChange(FOO, oldValue, foo);
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {

        String oldValue = this.bar;

        this.bar = bar;

        firePropertyChange(BAR, oldValue, bar);
    }
}