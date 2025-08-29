package com.example.AeratorIoTBackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.AeratorIoTBackend.model.SensorDataModel;
import com.example.AeratorIoTBackend.repository.SensorDataRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensors")
public class SensorDataController {

    @Autowired
    private SensorDataRepository repository;

    // Create new sensor data
    @PostMapping
    public SensorDataModel createSensorData(@RequestBody SensorDataModel sensorData) {
        return repository.save(sensorData);
    }

    // Get all sensor data
    @GetMapping
    public List<SensorDataModel> getAllSensorData() {
        return repository.findAll();
    }

    // Get single sensor data by ID
    @GetMapping("/{id}")
    public Optional<SensorDataModel> getSensorDataById(@PathVariable String id) {
        return repository.findById(id);
    }

    // Update sensor data
    @PutMapping("/{id}")
    public SensorDataModel updateSensorData(@PathVariable String id, @RequestBody SensorDataModel updatedData) {
        return repository.findById(id)
                .map(sensorData -> {
                    sensorData.setName(updatedData.getName());
                    sensorData.setOxygenLevel(updatedData.getOxygenLevel());
                    sensorData.setpHLevel(updatedData.getpHLevel());
                    sensorData.setTurbidity(updatedData.getTurbidity());
                    sensorData.setRunning(updatedData.isRunning());
                    return repository.save(sensorData);
                })
                .orElseGet(() -> {
                    updatedData.setId(id);
                    return repository.save(updatedData);
                });
    }

    // Delete sensor data
    @DeleteMapping("/{id}")
    public String deleteSensorData(@PathVariable String id) {
        repository.deleteById(id);
        return "Sensor data with ID " + id + " deleted successfully.";
    }
}
