package ru.sharipov.springcourse.IoTRESTApp.models;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Sensors")
public class Sensor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sensor_id")
	private int sensorId;
	
	@NotEmpty(message = "Name shouldn't be empty")
	@Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "sensor")
	private List<Measurement> measurements;
	
	public Sensor() {}

	public Sensor(String name) {
		this.name = name;
	}

	public int getSensorId() {
		return sensorId;
	}

	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Measurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<Measurement> measurements) {
		this.measurements = measurements;
	}

	@Override
	public String toString() {
		return "Sensor [sensorId=" + sensorId + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Sensor other = (Sensor) obj;
		return Objects.equals(name, other.name);
	}
	
	

}
