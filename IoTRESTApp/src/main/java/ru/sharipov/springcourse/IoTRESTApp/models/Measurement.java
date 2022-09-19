package ru.sharipov.springcourse.IoTRESTApp.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Measurements")
public class Measurement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "measurement_id")
	private int measurementId;
	
	@Min(value = -100, message = "Value should be greater than -100 and lower than 100")
	@Max(value = 100, message = "Value should be greater than -100 and lower than 100")
	@NotNull(message = "Value shouldn't be empty")
	@Column(name = "value")
	private double value;

	@NotNull(message = "Raining shouldn't be empty")
	@Column(name = "raining")
	private boolean raining;

	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "sensor_id", referencedColumnName = "sensor_id")
	private Sensor sensor;
	
	public Measurement() {}

	public Measurement(double value,boolean raining) {
		this.value = value;
		this.raining = raining;
	}

	public int getMeasurementId() {
		return measurementId;
	}

	public void setMeasurementId(int measurementId) {
		this.measurementId = measurementId;
	}

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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	
	@Override
	public String toString() {
		return "Measurement [measurementId=" + measurementId + ", value=" + value + ", raining=" + raining
				+ ", createdAt=" + createdAt + ", sensor=" + sensor + "]";
	}
}
