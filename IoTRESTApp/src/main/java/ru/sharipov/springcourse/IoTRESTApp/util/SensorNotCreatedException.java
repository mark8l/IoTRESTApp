package ru.sharipov.springcourse.IoTRESTApp.util;

public class SensorNotCreatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4079296259252679821L;

	public SensorNotCreatedException(String msg) {
		super(msg);
	}
}
