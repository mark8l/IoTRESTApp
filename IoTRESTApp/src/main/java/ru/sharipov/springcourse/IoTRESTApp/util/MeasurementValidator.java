package ru.sharipov.springcourse.IoTRESTApp.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.sharipov.springcourse.IoTRESTApp.dto.MeasurementDTO;
import ru.sharipov.springcourse.IoTRESTApp.dto.SensorDTO;
import ru.sharipov.springcourse.IoTRESTApp.services.SensorsService;

@Component
public class MeasurementValidator implements Validator {
	
	private final SensorsService sensorsService;
	
	
	
	public MeasurementValidator(SensorsService sensorsService) {
		this.sensorsService = sensorsService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return MeasurementDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MeasurementDTO measurementDTO = (MeasurementDTO) target;
		SensorDTO sensor = measurementDTO.getSensor();
		if (!sensorsService.isSensorExist(sensor.getName()).isPresent()) {
			errors.rejectValue("sensor", "", "There is no sensor with this name, please register new one and repeat action");
		}
	}

}
