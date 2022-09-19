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

import ru.sharipov.springcourse.IoTRESTApp.dao.MeasurementDAO;
import ru.sharipov.springcourse.IoTRESTApp.dto.MeasurementDTO;
import ru.sharipov.springcourse.IoTRESTApp.dto.MeasurementsResponse;
import ru.sharipov.springcourse.IoTRESTApp.dto.SensorDTO;
import ru.sharipov.springcourse.IoTRESTApp.models.Measurement;
import ru.sharipov.springcourse.IoTRESTApp.services.MeasurementsService;
import ru.sharipov.springcourse.IoTRESTApp.util.MeasurementErrorResponse;
import ru.sharipov.springcourse.IoTRESTApp.util.MeasurementNotCreatedException;
import ru.sharipov.springcourse.IoTRESTApp.util.MeasurementValidator;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

	private final MeasurementsService measurementsService;
	private final MeasurementValidator measurementValidator;
	private final MeasurementDAO measurementDAO;
	private final ModelMapper modelMapper;

	@Autowired
	public MeasurementController(MeasurementsService measurementsService,
								 MeasurementValidator measurementValidator,
								 ModelMapper modelMapper,
								 MeasurementDAO measurementDAO) {
		this.measurementsService = measurementsService;
		this.measurementValidator = measurementValidator;
		this.measurementDAO = measurementDAO;
		this.modelMapper = modelMapper;
	}

//	@GetMapping
//	public List<MeasurementDTO> showMeasurements() {
//
//		return measurementsService.findAll()
//								  .stream()
//								  .map(this::convertToMeasurementDTO)
//								  .collect(Collectors.toList());
//	}
	
	@GetMapping
	public MeasurementsResponse showMeasurements() {

		return new MeasurementsResponse(measurementsService.findAll()
				  										   .stream()
				  										   .map(this::convertToMeasurementDTO)
				  										   .collect(Collectors.toList()));
	}
	
	@PostMapping("/add1000")
	public void create1000() {
		measurementDAO.batchUpdate();
	}

	@PostMapping("/add")
	public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
																 BindingResult bindingResult) {
		measurementValidator.validate(measurementDTO, bindingResult);
		
		if (bindingResult.hasErrors()) {
			StringBuilder errorsMsg = new StringBuilder();
			List<FieldError> errors = bindingResult.getFieldErrors();
			for (FieldError error : errors) {
				errorsMsg.append(error.getField())
						 .append(" - ")
						 .append(error.getDefaultMessage())
						 .append(";");
			}
			throw new MeasurementNotCreatedException(errorsMsg.toString());
		}

		measurementsService.save(convertToMeasurement(measurementDTO));
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@GetMapping("/rainyDaysCount")
	public List<MeasurementDTO> rainyDays() {
		return measurementsService.findRainingDays()
								  .stream().map(this::convertToMeasurementDTO)
								  .collect(Collectors.toList());
	}

	@ExceptionHandler
	public ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
		MeasurementErrorResponse response = new MeasurementErrorResponse(e.getMessage(),
																		 System.currentTimeMillis());
		return new ResponseEntity<MeasurementErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}

	private Measurement convertToMeasurement(@Valid MeasurementDTO measurementDTO) {
		return modelMapper.map(measurementDTO, Measurement.class);
	}

	private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
		MeasurementDTO measurementDTO = modelMapper.map(measurement, MeasurementDTO.class);
		measurementDTO.setSensor(modelMapper.map(measurementDTO.getSensor(), SensorDTO.class));
		return measurementDTO;
	}
}
