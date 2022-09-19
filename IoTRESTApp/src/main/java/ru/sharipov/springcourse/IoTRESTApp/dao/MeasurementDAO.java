package ru.sharipov.springcourse.IoTRESTApp.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.sharipov.springcourse.IoTRESTApp.models.Measurement;
import ru.sharipov.springcourse.IoTRESTApp.models.Sensor;
import ru.sharipov.springcourse.IoTRESTApp.services.MeasurementsService;
import ru.sharipov.springcourse.IoTRESTApp.services.SensorsService;

@Component
public class MeasurementDAO {
	
	private final JdbcTemplate jdbcTemplate;
	private final SensorsService sensorsService;

	@Autowired
	public MeasurementDAO(JdbcTemplate jdbcTemplate, SensorsService sensorsService, MeasurementsService measurementsService) {
		this.jdbcTemplate = jdbcTemplate;
		this.sensorsService = sensorsService;
	}
	
	public void batchUpdate() {
		List<Measurement> measurements = generate1000Measurements();
		long before = System.currentTimeMillis();
		
		jdbcTemplate.batchUpdate("INSERT INTO u520564_restappdb.Measurements (sensor_id, value, raining, created_at) VALUES(?, ?, ?, ?)",
								 new BatchPreparedStatementSetter() {
									
									@Override
									public void setValues(PreparedStatement ps, int i) throws SQLException {
										ps.setInt(1, measurements.get(i).getSensor().getSensorId());
										ps.setDouble(2, measurements.get(i).getValue());
										ps.setBoolean(3, measurements.get(i).isRaining());
										ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
										
									}
									
									@Override
									public int getBatchSize() {
										return measurements.size();
									}
								});
		
		long after = System.currentTimeMillis();
		
		System.out.println("Time: " + (after - before) + "ms.");
	}
	
	private List<Measurement> generate1000Measurements(){
		List<Sensor> sensors = sensorsService.findAll();
		List<Measurement> measurements = new ArrayList<>();
		Measurement measurement;
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			measurement = new Measurement();
			measurement.setSensor(sensors.get(random.nextInt(sensors.size())));
			measurement.setRaining(random.nextBoolean());
			measurement.setValue(ThreadLocalRandom.current().nextDouble(-100.0, 100.0));
//			measurement.setCreatedAt(LocalDateTime.now());
			measurements.add(measurement);
			
		}
		return measurements;
	}
	
}
