package com.coherentlogic.coherent.datafeed.misc;

import static com.coherentlogic.coherent.datafeed.misc.Utils.assertEquals;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertBetween;
import static com.coherentlogic.coherent.datafeed.misc.Utils.assertNotNull;

import org.junit.Test;

import com.coherentlogic.coherent.datafeed.exceptions.
    ValueOutOfBoundsException;
import com.coherentlogic.coherent.datafeed.exceptions.
    NullPointerRuntimeException;

/**
 * Unit test for the Utils class.
 *
 * @author <a href="support@coherentlogic.com">Support</a>
 */
public class UtilsTest {

    @Test
    public void assertEqualsLHSRHSPass(){
        assertEquals("foo", "BAR", 5, 5);
    }

    @Test(expected=ValueOutOfBoundsException.class)
    public void assertEqualsLHSRHSFail(){
        assertEquals("bar", "foo", 4, 5);
    }

    @Test
    public void assertEqualsPass(){
        assertEquals("foo", 5, 5);
    }

    @Test(expected=ValueOutOfBoundsException.class)
    public void assertEqualsFail(){
        assertEquals("bar", 4, 5);
    }

    @Test
    public void testAssertBetweenValid1() {
        assertBetween ("foo", 5, 0, 10);
    }

    @Test
    public void testAssertBetweenValid2() {
        assertBetween ("foo", 0, 0, 10);
    }

    @Test
    public void testAssertBetweenValid3() {
        assertBetween ("foo", 0, -5, 10);
    }

    @Test(expected=ValueOutOfBoundsException.class)
    public void testAssertBetweenInvalid1() {
        assertBetween ("foo", 0, 1, 10);
    }

    @Test(expected=ValueOutOfBoundsException.class)
    public void testAssertBetweenInvalid2() {
        assertBetween ("foo", -6, -5, 10);
    }

    @Test(expected=ValueOutOfBoundsException.class)
    public void testAssertBetweenInvalid3() {
        assertBetween ("foo", 20, -5, 10);
    }

    public void testAssertNotNullValid() {
        assertNotNull ("foo", "foo");
    }

    @Test(expected=NullPointerRuntimeException.class)
    public void testAssertNotNullInvalid() {
        assertNotNull ("foo", null);
    }
}
