package com.coherentlogic.coherent.datafeed.generators;

import static com.coherentlogic.coherent.datafeed.misc.Constants.ANONYMOUS;

/**
 * A factory that always returns a unique name. This is accomplished by simply
 * appending a number to the end of the name.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class IdGenerator {

    private long ctr = 0;

    private final String name;

    public IdGenerator () {
        this (ANONYMOUS);
    }

    public IdGenerator(String name) {
        super();
        this.name = name;
    }

    public IdGenerator(long ctr, String name) {
        super();
        this.ctr = ctr;
        this.name = name;
    }

    public String generate () {
        return generate (name, ctr++);
    }

    public String generate(String name) {
        return generate (name, ctr++);
    }

    public String generate(String name, long ctr) {
        return name + ctr;
    }
}
