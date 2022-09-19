package ru.sharipov.springcourse.IoTRESTApp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MeasurementDTO {
	
	@Min(value = -100, message = "Value should be greater than -100 and lower than 100")
	@Max(value = 100, message = "Value should be greater than -100 and lower than 100")
	@NotNull(message = "Value shouldn't be empty")
	private double value;

	@NotNull(message = "Raining shouldn't be empty")
	private boolean raining;
	
	private SensorDTO sensor;
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isRaining() {
		return raining;
	}

	public void setRaining(boolean raining) {
		this.raining = raining;
	}

	public SensorDTO getSensor() {
		return sensor;
	}

	public void setSensor(SensorDTO sensor) {
		this.sensor = sensor;
	}
	
	
}
