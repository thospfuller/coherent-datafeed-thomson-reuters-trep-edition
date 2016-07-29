package com.coherentlogic.coherent.datafeed.domain;

import com.coherentlogic.coherent.datafeed.exceptions.FatalRuntimeException;

/**
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 *
 * @param <T>
 */
public interface AggregatePropertyChangeGeneratorSpecification<T> {

    static class MethodNotImplementedException extends FatalRuntimeException {

        private static final long serialVersionUID = 5529414003900204395L;

        public MethodNotImplementedException() {
            super("This method is not yet available however if you need this functionality you can vote for it here:"
                + "http://bit.ly/1WDA5NR");
        }
    }

    void addAggregatePropertyChangeListener (AggregatePropertyChangeListener<T> aggregatePropertyChangeListener);

    void removeAggregatePropertyChangeListener (AggregatePropertyChangeListener<T> aggregatePropertyChangeListener);
}
