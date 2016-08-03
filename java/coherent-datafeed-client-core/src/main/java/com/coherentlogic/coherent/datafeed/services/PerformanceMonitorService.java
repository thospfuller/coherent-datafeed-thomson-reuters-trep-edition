package com.coherentlogic.coherent.datafeed.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

/**
 * 
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class PerformanceMonitorService {

    private static final Logger log = LoggerFactory.getLogger(PerformanceMonitorService.class);

    private final String label;
    
    private Monitor monitor;

    public PerformanceMonitorService (String label) {
        this.label = label;
    }

    public void start () {
        monitor = MonitorFactory.start(label);
    }

    public void stopAndLog () {

        String result = monitor.stop ().toString();

        log.info("result: " + result);

        monitor = null;
    }

    public void setEnabled (boolean flag) {
        if (flag)
            MonitorFactory.enable();
        else
            MonitorFactory.disable();
    }

    public boolean isEnabled () {
        return MonitorFactory.isEnabled();
    }

    public void setActivityTracking (boolean trackActivity) {
        monitor.setActivityTracking(trackActivity);
    }

    public String toString () {
        return (monitor == null) ? "(monitor is null)" : monitor.toString();
    }
}
