package com.coherentlogic.coherent.datafeed.factories;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.datafeed.annotations.FilterId;
import com.reuters.rfa.rdm.RDMService;

/**
 * Unit test for the {@link FilterIdAnnotationProcessor} class.
 * 
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class FilterIdAnnotationProcessorTest {

    private Map<Integer, Method> methodMap = null;

    private FilterIdAnnotationProcessor processor = null;

    @Before
    public void setUp() throws Exception {

        methodMap = new HashMap<Integer, Method>();

        processor = new FilterIdAnnotationProcessor(
            TestPojo.class,
            new FilterIdAnnotationFoundCallback(), methodMap);
    }

    @After
    public void tearDown() throws Exception {
        methodMap = null;
        processor = null;
    }

    @Test
    public void testAnalyze() {
        Map<Integer, Method> methodMap = processor.getInstance();

        assertEquals(3, methodMap.size());
    }

}

class FilterIdAnnotationFoundCallback implements
        AnnotationFoundCallback<FilterId, Integer> {

    @Override
    public void annotationFound(final FilterId annotation, final Method method,
            final Map<Integer, Method> methodMap) {
        Integer value = annotation.value();
        methodMap.put(value, method);
    }
}

class TestPojo implements Serializable {

    private static final long serialVersionUID = 5621054450058239498L;

    private int state, data, group;

    public int getState() {
        return state;
    }

    @FilterId(value = RDMService.FilterId.STATE)
    public void setState(int serviceState) {
        this.state = serviceState;
    }

    public int getData() {
        return data;
    }

    @FilterId(value = RDMService.FilterId.DATA)
    public void setData(int data) {
        this.data = data;
    }

    public int getGroup() {
        return group;
    }

    @FilterId(value = RDMService.FilterId.GROUP)
    public void setGroup(int group) {
        this.group = group;
    }
}
