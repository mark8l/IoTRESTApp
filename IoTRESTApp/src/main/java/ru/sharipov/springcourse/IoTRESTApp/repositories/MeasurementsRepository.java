package ru.sharipov.springcourse.IoTRESTApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sharipov.springcourse.IoTRESTApp.models.Measurement;

public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
	public List<Measurement> findByRaining(boolean raining);
}
