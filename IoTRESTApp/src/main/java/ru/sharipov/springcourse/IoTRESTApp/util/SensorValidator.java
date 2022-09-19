package ru.sharipov.springcourse.IoTRESTApp.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.sharipov.springcourse.IoTRESTApp.dto.SensorDTO;
import ru.sharipov.springcourse.IoTRESTApp.models.Sensor;
import ru.sharipov.springcourse.IoTRESTApp.services.SensorsService;

@Component
public class SensorValidator implements Validator {

	private final SensorsService sensorsService;
	
	@Autowired
	public SensorValidator(SensorsService sensorsService) {
		this.sensorsService = sensorsService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return SensorDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Sensor sensor = (Sensor) target;
		Optional<Sensor> searchSensor = sensorsService.isSensorExist(sensor.getName());
		if (searchSensor.isPresent()) {
			if (sensor.equals(searchSensor.get())) {
				errors.rejectValue("name", "", "Sensor with this name is already exist");
			}
		}		
	}

}
