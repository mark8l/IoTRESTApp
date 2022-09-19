package ru.sharipov.springcourse.IoTRESTApp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.sharipov.springcourse.IoTRESTApp.dto.SensorDTO;
import ru.sharipov.springcourse.IoTRESTApp.models.Sensor;
import ru.sharipov.springcourse.IoTRESTApp.services.SensorsService;
import ru.sharipov.springcourse.IoTRESTApp.util.SensorErrorResponse;
import ru.sharipov.springcourse.IoTRESTApp.util.SensorNotCreatedException;
import ru.sharipov.springcourse.IoTRESTApp.util.SensorNotFoundException;
import ru.sharipov.springcourse.IoTRESTApp.util.SensorValidator;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
	
	private final SensorsService sensorsService;
	private final ModelMapper modelMapper;
	@SuppressWarnings("unused")
	private final SensorValidator sensorValidator;
	
	@Autowired
	public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator, ModelMapper modelMapper) {
		this.sensorsService = sensorsService;
		this.modelMapper = modelMapper;
		this.sensorValidator = sensorValidator;
	}
	
	@GetMapping
	public List<SensorDTO> showAllSensors() {
		return sensorsService.findAll()
							 .stream()
							 .map(this::convertToSensorDTO)
							 .collect(Collectors.toList());
	}
	
	@PostMapping("/registration")
	public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
																 BindingResult bindingResult) {
		sensorValidator.validate(convertToSensor(sensorDTO), bindingResult);
		if (bindingResult.hasErrors()) {
			StringBuilder errorsMsg = new StringBuilder();
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError fieldError : errors) {
				errorsMsg.append(fieldError.getField())
						 .append(" - ")
						 .append(fieldError.getDefaultMessage())
						 .append(";");
			}
			throw new SensorNotCreatedException(errorsMsg.toString());
		}
		
		sensorsService.save(convertToSensor(sensorDTO));
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@ExceptionHandler
	private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
		SensorErrorResponse response = new SensorErrorResponse(
									   "Sensor with this name wasn't found",
									   System.currentTimeMillis());
		//In HTTP response body (response) and status in header
		return new ResponseEntity<SensorErrorResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
		SensorErrorResponse response = new SensorErrorResponse(
									   e.getMessage(),
									   System.currentTimeMillis());
		//In HTTP response body (response) and status in header
		return new ResponseEntity<SensorErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}
	
	private Sensor convertToSensor(@Valid SensorDTO sensorDTO) {
		return modelMapper.map(sensorDTO, Sensor.class);
	}
	
	private SensorDTO convertToSensorDTO(Sensor sensor) {
		return modelMapper.map(sensor, SensorDTO.class);
	}
}
