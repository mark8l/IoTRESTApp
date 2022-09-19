package ru.sharipov.springcourse.IoTRESTApp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sharipov.springcourse.IoTRESTApp.models.Sensor;

public interface SensorsRepository extends JpaRepository<Sensor, Integer> {
//	public Sensor findByName(String name);
	public Optional<Sensor> findByName(String name);
}
