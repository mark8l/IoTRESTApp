package ru.sharipov.springcourse.IoTRESTApp.util;

public class MeasurementNotCreatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1650877606859487217L;

	public MeasurementNotCreatedException(String msg) {
		super(msg);
	}
}
