package ru.sharipov.springcourse.IoTRESTApp.util;

public class SensorErrorResponse {
	private String message;
	private long timestamp;
	
	public SensorErrorResponse(String message, long timestamp) {
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
}
