package com.coherentlogic.coherent.datafeed.exceptions;

/**
 * An exception that is thrown when the conversion from TR RFA-specific logic to the CL time series fails.
 *
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class TimeSeriesConversionFailedException extends
		ConversionFailedException {

	public TimeSeriesConversionFailedException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	public TimeSeriesConversionFailedException(String msg, Throwable cause) {
		super(msg, cause);
		// TODO Auto-generated constructor stub
	}

}
