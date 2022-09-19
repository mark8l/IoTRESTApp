package ru.sharipov.springcourse.IoTRESTApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.sharipov.springcourse.IoTRESTApp.models.Sensor;
import ru.sharipov.springcourse.IoTRESTApp.repositories.SensorsRepository;
import ru.sharipov.springcourse.IoTRESTApp.util.SensorNotFoundException;

@Service
@Transactional(readOnly = true)
public class SensorsService {
	private final SensorsRepository sensorsRepository;

	public SensorsService(SensorsRepository sensorsRepository) {
		this.sensorsRepository = sensorsRepository;
	}
	
	public List<Sensor> findAll(){
		return sensorsRepository.findAll();
	}
	
	public Sensor findById(int id) {
		Optional<Sensor> sensor = sensorsRepository.findById(id);
		return sensor.orElseThrow(SensorNotFoundException::new);
	}
	
	public Sensor findByName(String name) {
		Optional<Sensor> sensor = sensorsRepository.findByName(name);
		return sensor.orElseThrow(SensorNotFoundException::new);
	}
	
	public Optional<Sensor> isSensorExist(String name) {
		return sensorsRepository.findByName(name);
	}
	
	@Transactional
	public void save(Sensor sensor) {
		enrichSensor(sensor);
		sensorsRepository.save(sensor);
	}
	
	private void enrichSensor(Sensor sensor) {
	}
}
