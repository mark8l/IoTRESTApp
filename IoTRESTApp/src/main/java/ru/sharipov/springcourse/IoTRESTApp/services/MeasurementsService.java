package ru.sharipov.springcourse.IoTRESTApp.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.sharipov.springcourse.IoTRESTApp.models.Measurement;
import ru.sharipov.springcourse.IoTRESTApp.models.Sensor;
import ru.sharipov.springcourse.IoTRESTApp.repositories.MeasurementsRepository;
import ru.sharipov.springcourse.IoTRESTApp.util.MeasurementNotFoundException;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
	
	private MeasurementsRepository measurementsRepository;
	private SensorsService sensorsService;

	public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
		this.measurementsRepository = measurementsRepository;
		this.sensorsService = sensorsService;
	}
	
	public List<Measurement> findAll(){
		return measurementsRepository.findAll();
	}
	
	public Measurement findById(int id) {
		Optional<Measurement> measurement = measurementsRepository.findById(id);
		System.out.println();
		return measurement.orElseThrow(MeasurementNotFoundException::new);
	}
	
	public List<Measurement> findRainingDays() {
		return measurementsRepository.findByRaining(true);
	}
	
	@Transactional
	public void save(Measurement measurement) {
		enrichMeasurement(measurement);
		measurementsRepository.save(measurement);
	}

	private void enrichMeasurement(Measurement measurement) {
		
		Sensor sensor = measurement.getSensor();
		sensor.setSensorId(sensorsService.findByName(sensor.getName()).getSensorId());
		measurement.setCreatedAt(LocalDateTime.now());		
	}

}
